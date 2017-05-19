package zelda.generator.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import zelda.generator.Main;
import zelda.generator.model.Connection;
import zelda.generator.model.ExclusionOrder;
import zelda.generator.model.Game;
import zelda.generator.model.GameEnum;
import zelda.generator.model.NodeUtilities;
import zelda.generator.model.ZeldaTree;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameOverviewController {
    @FXML
    private TableView<Game> gameTable;
    @FXML
    private TableColumn<Game, String> gameTitleColumn;
    @FXML
    private TableColumn<Game, String> numConnectionsColumn;

    @FXML
    private TableView<Connection> connectionTable;
    @FXML
    private TableColumn<Connection, String> sourceGameTitleColumn;
    @FXML
    private TableColumn<Connection, String> orderColumn;
    @FXML
    private TableColumn<Connection, String> targetGameTitleColumn;
    @FXML
    private TableColumn<Connection, String> ratingColumn;
    @FXML
    private TableColumn<Connection, String> evidenceColumn;
    @FXML
    private Label sourceGameLabel;
    @FXML
    private Label orderLabel;
    @FXML
    private Label targetGameLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label evidenceLabel;

    // Reference to the main application.
    private Main main;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public GameOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        gameTitleColumn.setCellValueFactory(cellData -> cellData.getValue().gameTitleFormattedStringProperty());
        numConnectionsColumn.setCellValueFactory(cellData -> cellData.getValue().numConnectionsStringProperty());

        sourceGameTitleColumn.setCellValueFactory(cellData -> cellData.getValue().sourceGameTitleFormattedStringProperty());
        orderColumn.setCellValueFactory(cellData -> cellData.getValue().orderFormattedStringProperty());
        targetGameTitleColumn.setCellValueFactory(cellData -> cellData.getValue().targetGameTitleFormattedStringProperty());
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty());
        evidenceColumn.setCellValueFactory(cellData -> cellData.getValue().evidenceDescriptionTextProperty());

        // Listen for selection changes and show the game details when changes
        gameTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showGameDetails(newValue));

        // Listen for selection changes and show the connection details when changed.
        connectionTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showConnectionDetails(newValue));
    }

    public void setMain(Main main) {
        this.main = main;

        // Add observable list data to the table
        gameTable.setItems(main.getGameData());
    }

    private void showGameDetails(Game game) {
        if (game != null) {
            // Fill the labels with info
            sourceGameLabel.setText(game.gameTitleFormattedStringProperty().getValue().toString());
            connectionTable.setItems(game.getConnections());
        } else {
            // remove all the text.
            sourceGameLabel.setText("");
        }
    }

    private void showConnectionDetails(Connection connection) {
    	if (connection != null) {
            // Fill the labels with info from the person object.
            orderLabel.setText(ExclusionOrder.getStringFromEnum(connection.orderProperty().getValue()));
            targetGameLabel.setText(GameEnum.getStringFromEnum(connection.targetGameTitleProperty().getValue()));
            ratingLabel.setText(connection.getRating());
            evidenceLabel.setText(connection.getEvidenceDescriptionText());
        } else {
            // Person is null, remove all the text.
            orderLabel.setText("");
            targetGameLabel.setText("");
            ratingLabel.setText("");
            evidenceLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteGame() {
        int selectedIndex = gameTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            gameTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Game Selected");
            alert.setContentText("Please select a game in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteConnection() {
        int selectedIndex = connectionTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            connectionTable.getItems().remove(selectedIndex);
            main.getGameData().get(gameTable.getSelectionModel().getSelectedIndex()).setNumConnections(
                	String.valueOf(Integer.parseInt(main.getGameData()
                			.get(gameTable.getSelectionModel().getSelectedIndex()).getNumConnections()) - 1));
            // For forcing refresh
            connectionTable.setVisible(false);
            connectionTable.setVisible(true);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Connection Selected");
            alert.setContentText("Please select a connection in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewGame() {
        GameEnum okClicked = main.showNewGameDialog();
        if (okClicked != null) {
            Game tempGame = new Game(okClicked);
            main.getGameData().add(tempGame);
            // For forcing refresh
            gameTable.getColumns().get(1).setVisible(false);
            gameTable.getColumns().get(1).setVisible(true);
        }
        // Will need to add a new entry to the table
    }

    @FXML
    private void handleNewConnection() {
        int selectedIndex = gameTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Connection tempConnection =
            		new Connection(((Game) gameTable.getItems().toArray()[selectedIndex]).getGameTitle());
            boolean okClicked = main.showGameConnectionsEditDialog(tempConnection);
            if (okClicked) {
                main.getGameData().get(gameTable.getSelectionModel().getSelectedIndex())
                	.getConnections().add(tempConnection);
                main.getGameData().get(gameTable.getSelectionModel().getSelectedIndex()).setNumConnections(
                    	String.valueOf(Integer.parseInt(main.getGameData()
                    			.get(gameTable.getSelectionModel().getSelectedIndex()).getNumConnections()) + 1));
                // For forcing refresh
                connectionTable.getColumns().get(0).setVisible(false);
                connectionTable.getColumns().get(0).setVisible(true);
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Source Game selected in left table");
            alert.setContentText("Please select a game in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditConnection() {
        Connection selectedConnection = connectionTable.getSelectionModel().getSelectedItem();
        if (selectedConnection != null) {
            boolean okClicked = main.showGameConnectionsEditDialog(selectedConnection);
            if (okClicked) {
                showConnectionDetails(selectedConnection);
                connectionTable.getColumns().get(0).setVisible(false);
                connectionTable.getColumns().get(0).setVisible(true);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Game Selected");
            alert.setContentText("Please select a game in the table.");

            alert.showAndWait();
        }
    }

    // Not working quite yet.
    @FXML
    public void handleWrite() {
        try {
            // write object to file
            FileOutputStream fos = new FileOutputStream("GameSaveFile.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            long position = fos.getChannel().position();
            oos.writeObject(new Integer(2));
            oos.flush();
            oos.reset(); // added line
            fos.getChannel().position(position);
            for(Game game : main.getGameData()) {
            	game.prepareSerialize();
            }
            oos.writeObject(new ArrayList<Game>(main.getGameData()));
            oos.flush();
            oos.close();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Save Successful");
            alert.setHeaderText("Save Successful");
            alert.setContentText("Saved to file GameSaveFile.ser");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleGenerate() {
        // Create Window, ask user to specify root node
        main.setRootGame(main.showRootChooserDialog());
        List<GameEnum> gameTitles = new ArrayList();
        for(Game game : main.getGameData()) {
            gameTitles.add(game.getGameTitle());
        }

        List<Connection> connectionsList = new ArrayList();
        for (Game game: main.getGameData()) {
            connectionsList.addAll(game.getConnections());
        }
        for(Connection connection : connectionsList) {
            if(!gameTitles.contains(connection.getSourceGameTitle())) {
                gameTitles.add(connection.getSourceGameTitle());
                Game tempGame = new Game(connection.getSourceGameTitle());
                main.getGameData().add(tempGame);
            }
            else if (!gameTitles.contains((connection.getTargetGameTitle()))) {
                gameTitles.add(connection.getTargetGameTitle());
                Game tempGame = new Game(connection.getTargetGameTitle());
                main.getGameData().add(tempGame);
            }
        }

    	List<Game> leafGames = NodeUtilities.FindLeafGames(main.getGameData());
        List<Game> middleGames = new ArrayList(main.getGameData());
        for(Game game : leafGames) {
            middleGames.remove(game);
        }
        middleGames.remove(main.getRootGame());

        ZeldaTree tree = new ZeldaTree(main.getRootGame(), leafGames, middleGames);

        ShowGraphApplet(tree);
    }

    public void ShowGraphApplet(ZeldaTree tree) {
        ZeldaGraphWindow divd = new ZeldaGraphWindow(tree);
    }
}
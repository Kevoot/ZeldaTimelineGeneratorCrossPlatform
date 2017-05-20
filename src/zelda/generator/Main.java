package zelda.generator;

        import java.awt.*;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.util.ArrayList;
        import java.util.List;

        import edu.uci.ics.jung.algorithms.layout.CircleLayout;
        import edu.uci.ics.jung.algorithms.layout.Layout;
        import edu.uci.ics.jung.algorithms.layout.StaticLayout;
        import edu.uci.ics.jung.visualization.BasicVisualizationServer;
        import edu.uci.ics.jung.visualization.VisualizationViewer;
        import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
        import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
        import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
        import edu.uci.ics.jung.visualization.renderers.Renderer;
        import javafx.application.Application;
        import javafx.beans.property.SimpleListProperty;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.BorderPane;
        import javafx.stage.Modality;
        import javafx.stage.Stage;
        import zelda.generator.model.Connection;
        import zelda.generator.model.Game;
        import zelda.generator.model.GameEnum;
        import zelda.generator.view.GameConnectionsEditDialogController;
        import zelda.generator.view.GameOverviewController;
        import zelda.generator.view.NewGameDialogueController;
        import zelda.generator.view.RootChooserController;

        import javax.swing.*;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Game rootGame;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Game> gameData = FXCollections.observableArrayList();
    private ObservableList<Connection> connectionData = FXCollections.observableArrayList();
    public ObservableList<Game> getGameData() {
        return gameData;
    }
    public ObservableList<Connection> getConnectionData() {
        return connectionData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Game List");

        initRootLayout();

        // Attempt reading from local save data
        handleRead();

        showGameOverview();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setGameData(List<Game> gameList) {
        gameData = FXCollections.observableArrayList();
        for(Game game : gameList) {
            gameData.add(game);
        }

    }

    public Game getRootGame() {
        return rootGame;
    }

    public void setRootGame(Game game) {
        this.rootGame = game;
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGameOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GameOverview.fxml"));
            AnchorPane gameOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(gameOverview);

            // Give the controller access to the main app.
            GameOverviewController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showGameConnectionsEditDialog(Connection connection) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GameEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Connections");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            GameConnectionsEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setConnection(connection);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public GameEnum showNewGameDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/NewGameDialogue.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new game");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            NewGameDialogueController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Game showRootChooserDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootChooser.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new game");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            RootChooserController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setSourceGameComboBoxList();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Stage getPrimaryStage() {
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        return primaryStage;
    }

    private void handleRead() {
        try {
            FileInputStream fileIn = new FileInputStream("GameSaveFile.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<Game> gameList = (List<Game>) in.readObject();
            List<Game> deserializedGameList = new ArrayList<Game>();
            for(Game game : gameList) {
                deserializedGameList.add(new Game(game.serializableGameTitle, game.serializableConnections));
            }
            setGameData(deserializedGameList);
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("Not Game class not found");
            c.printStackTrace();
            return;
        }
    }
}

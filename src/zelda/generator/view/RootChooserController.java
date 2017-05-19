package zelda.generator.view;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import zelda.generator.Main;
import zelda.generator.model.Game;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import zelda.generator.model.GameEnum;


public class RootChooserController {
    @FXML
    public ComboBox<String> sourceGameComboBox;

    private Stage dialogStage;
    public List<Game> gameList;
    private Game okClicked;
    private Main main;

    public void setMain(Main main) {
        this.main = main;
        gameList = main.getGameData();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSourceGameComboBoxList() {
        this.gameList = gameList;
        ObservableList<String> availableGameList = new SimpleListProperty<String>(FXCollections.observableArrayList());
        for(Game game : gameList) {
            availableGameList.add(GameEnum.getStringFromEnum(game.getGameTitle()));
        }
        sourceGameComboBox.setItems(availableGameList);

        sourceGameComboBox.getSelectionModel().select(0);
    }

    public Game isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            for(Game game : gameList) {
                if(GameEnum.getStringFromEnum(game.getGameTitle()) == sourceGameComboBox.getSelectionModel().getSelectedItem()) {
                    okClicked = game;
                }
            }
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (sourceGameComboBox.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Game Invalid\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}

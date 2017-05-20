package zelda.generator.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import zelda.generator.model.GameEnum;

public class NewGameDialogueController {

    @FXML
    private ComboBox<String> sourceGameComboBox;


    private Stage dialogStage;
    private GameEnum okClicked = GameEnum.NoData;

    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        dialogStage.setResizable(false);
        dialogStage.sizeToScene();
        this.dialogStage = dialogStage;
        sourceGameComboBox.setItems(GameEnum.getFormattedTitleList());
    }

    public GameEnum isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	okClicked = (GameEnum.getEnumFromString(sourceGameComboBox.getValue()));
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        
        if (sourceGameComboBox.getValue() == null) {
            errorMessage += "Game Invalid\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Select a title or cancel");
            alert.setHeaderText("Please select a game or hit cancel");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
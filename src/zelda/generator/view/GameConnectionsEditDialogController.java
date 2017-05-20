package zelda.generator.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import zelda.generator.model.Connection;
import zelda.generator.model.ExclusionOrder;
import zelda.generator.model.GameEnum;

public class GameConnectionsEditDialogController {

    @FXML
    private ComboBox<String> sourceGameComboBox;
    @FXML
    private ComboBox<String> orderComboBox;
    @FXML
    private ComboBox<String> targetGameComboBox;
    @FXML
    private TextField evidenceField;
    @FXML
    private TextField ratingField;

    private Stage dialogStage;
    private Connection connection;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.setResizable(false);
        dialogStage.sizeToScene();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        sourceGameComboBox.setItems(GameEnum.getFormattedTitleList());
        orderComboBox.setItems(ExclusionOrder.getFormattedOrderList());
        targetGameComboBox.setItems(GameEnum.getFormattedTitleList());

        sourceGameComboBox.getSelectionModel().select(GameEnum.getStringFromEnum(connection.getSourceGameTitle()));
        orderComboBox.getSelectionModel().select(ExclusionOrder.getStringFromEnum(connection.getOrder()));
        targetGameComboBox.getSelectionModel().select(GameEnum.getStringFromEnum(connection.getTargetGameTitle()));
        ratingField.setText(String.valueOf(connection.getRating()).toString());
        evidenceField.setText(connection.getEvidenceDescriptionText());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            connection.setSourceGameTitle(GameEnum.getEnumFromString(sourceGameComboBox.getValue()));
            connection.setOrder(ExclusionOrder.getEnumFromString(orderComboBox.getValue()));
            connection.setTargetGameTitle(GameEnum.getEnumFromString(targetGameComboBox.getValue()));
            connection.setRating(ratingField.getText());
            connection.setEvidenceDescriptionText(evidenceField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (sourceGameComboBox.getValue() == null ||
        		!(GameEnum.getTitleList()).contains(GameEnum.getEnumFromString(sourceGameComboBox.getValue()))) {
        	GameEnum titleTest = GameEnum.getEnumFromString(targetGameComboBox.getValue());
        	ObservableList<GameEnum> resultTest = (GameEnum.getTitleList());
            errorMessage += "Game Invalid\n";
        }
        if (targetGameComboBox.getValue() == null ||
        		!(GameEnum.getTitleList()).contains(GameEnum.getEnumFromString(targetGameComboBox.getValue()))) {
            errorMessage += "Game Invalid\n";
        }
        if (orderComboBox.getValue() == null ||
        		!(ExclusionOrder.getOrderList()).contains(ExclusionOrder.getEnumFromString(orderComboBox.getValue()))){
            errorMessage += "Order invalid\n";
        }

        if (ratingField.getText() == null || ratingField.getText().length() == 0) {
            errorMessage += "You have to enter a rating!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(ratingField.getText());
                if(Integer.parseInt(ratingField.getText()) < 1 ||
                		Integer.parseInt(ratingField.getText()) > 10) {
                	errorMessage += "Rating must be between 1 and 10!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (evidenceField.getText() == null || evidenceField.getText().length() == 0) {
            errorMessage += "You didn't state a reason!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
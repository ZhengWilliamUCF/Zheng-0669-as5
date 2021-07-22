package ucf.assignments;

import javafx.scene.control.Alert;

public class ErrorMessageController {
    public void showErrorMessageSerialNumberExists(String SN){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The serial number " + SN + " already exists.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    public void showErrorMessageSerialNumberLength(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The serial number must be in the format XXXXXXXXXX.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    public void showErrorMessageSerialNumberInvalid(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The serial number must contain only letters or digits.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    public void showErrorMessageBadValue(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The value must be greater than $0.00.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }


    public void showErrorMessageNullValue() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The value must be a valid number.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    public void showErrorMessageInvalidNameLength(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String error = "Cannot add item. The name must be between 2 and 256 characters in length.";
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }
}

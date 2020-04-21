package jSONEditor.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class KeyController {

    @FXML
    private PasswordField keyBox;

    @FXML
    private void initialize() {
        // fill key
        if (EditorData.getInstance().key != null) {
            keyBox.setText(EditorData.getInstance().key);
        }
    }

    @FXML
    private void saveKey(ActionEvent event) {
        Stage stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());

        String newKey = keyBox.getText();
        if (newKey.length() == 16) {
            // same length as key
            // Check if in hexadecimal
            for (Character ch : newKey.toCharArray()) {
                if ((ch < '0') || (ch > '9' && ch < 'a') || (ch > 'z')) {
                    System.out.println("Key invalid!");
                    return;
                }
            }
        } else {
            System.out.println("Invalid key length!");
            return;
        }

        EditorData.getInstance().key = newKey;

        /*
        Serialize key save. . .
         */

        System.out.println("Saved key!");
        closeKey(stage);
    }

    @FXML
    private void reset(ActionEvent event) {
        Stage stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());

        // reset key
        keyBox.setText("");
        EditorData.getInstance().key = null;

        /*
        Serialize key save. . .
         */

        System.out.println("Reset key!");
        closeKey(stage);
    }


    public Stage closeKey(Stage keyStage) {
        System.out.println("Close Settings");

        keyStage.close();
        keyStage = null;
        return keyStage;
    }
}

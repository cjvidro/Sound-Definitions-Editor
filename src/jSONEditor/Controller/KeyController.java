package jSONEditor.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class KeyController {

    @FXML
    public PasswordField keyBox;

    @FXML
    public void initialize() {
        // fill key
        if (EditorData.getInstance().key != null) {
            keyBox.setText(EditorData.getInstance().key);
        }
    }

    public boolean saveKey(Stage stage) {
        String newKey = keyBox.getText();
        if (newKey.length() == 64) {
            // same length as key
            // Check if in hexadecimal
            for (Character ch : newKey.toCharArray()) {
                if ((ch < '0') || (ch > '9' && ch < 'a') || (ch > 'z')) {
                    System.out.println("Key invalid!");
                    return false;
                }
            }
        } else {
            System.out.println("Invalid key length!");
            return false;
        }

        EditorData.getInstance().key = newKey;
        saveKeyToDisk();

        System.out.println("Saved key!");
        closeKey(stage);
        return true;
    }

    @FXML
    private Boolean saveKey(ActionEvent event) {
       return saveKey(((Stage) ((Button) event.getSource()).getScene().getWindow()));
    }

    private void saveKeyToDisk() {
        try {
            EditorData.saveKey();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reset(ActionEvent event) {
        Stage stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());

        // reset key
        keyBox.setText("");
        EditorData.getInstance().key = null;
        saveKeyToDisk();

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

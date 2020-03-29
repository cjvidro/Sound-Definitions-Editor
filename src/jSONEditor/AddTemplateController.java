package jSONEditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AddTemplateController {
    /*****************************************************
     * Change Scenes
     *****************************************************/

    protected Stage cancelAddTemplate(Stage stage) {
        System.out.println("Cancel Add Template");

        /*
        INSERT CANCEL FUNCTIONALITY HERE
         */

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private void cancelAddTemplate(ActionEvent event) {
        // uses the above method for testing purposes
        cancelAddTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    protected Stage saveAddTemplate(Stage stage) {
        System.out.println("Save Add Template");

        /*
        INSERT SAVE FUNCTIONALITY HERE
         */

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    protected void saveAddTemplate(ActionEvent event) {
        // uses the above method for testing purposes
        saveAddTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

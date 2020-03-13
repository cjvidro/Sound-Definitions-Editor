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

    @FXML
    private void cancelAddTemplate(ActionEvent event) throws IOException {
        System.out.println("Cancel Add Template");

        /*
        INSERT CANCEL FUNCTIONALITY HERE
         */

        Stage exportStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        exportStage.close();
    }

    @FXML
    private void saveAddTemplate(ActionEvent event) throws IOException {
        System.out.println("Save Add Template");

        /*
        INSERT SAVE FUNCTIONALITY HERE
         */

        Stage templateStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        templateStage.close();
    }
}

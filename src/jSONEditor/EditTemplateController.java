package jSONEditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EditTemplateController {
    /*****************************************************
     * Change Scenes
     *****************************************************/

    @FXML
    private void cancelEditTemplate(ActionEvent event) throws IOException {
        System.out.println("Cancel Edit Template");

        /*
        INSERT CANCEL FUNCTIONALITY HERE
         */

        Stage exportStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        exportStage.close();
    }

    @FXML
    private void saveEditTemplate(ActionEvent event) throws IOException {
        System.out.println("Save Edit Template");

        /*
        INSERT EDIT FUNCTIONALITY HERE
         */

        Stage templateStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        templateStage.close();
    }

    @FXML
    private void deleteTemplate(ActionEvent event) throws IOException {
        System.out.println("Delete Template");

        /*
        INSERT DELETE FUNCTIONALITY HERE
         */

        Stage templateStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        templateStage.close();
    }
}

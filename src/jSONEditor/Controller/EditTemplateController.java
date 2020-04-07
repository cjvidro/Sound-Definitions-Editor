package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EditTemplateController {
    /*****************************************************
     * Change Scenes
     *****************************************************/

    public Stage cancelEditTemplate(Stage stage) {
        System.out.println("Cancel Edit Template");

        /*
        INSERT CANCEL FUNCTIONALITY HERE
         */

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private void cancelEditTemplate(ActionEvent event){
        // uses the above method for testing purposes
        cancelEditTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    public Stage saveEditTemplate(Stage stage) {
        System.out.println("Save Edit Template");

        /*
        INSERT SAVE EDIT FUNCTIONALITY HERE
         */

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private void saveEditTemplate(ActionEvent event){
        // uses the above method for testing purposes
        saveEditTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    public Stage deleteTemplate(Stage stage) {
        System.out.println("Delete Template");

        /*
        INSERT DELETE FUNCTIONALITY HERE
         */

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private void deleteTemplate(ActionEvent event) {
        // uses the above method for testing purposes
        deleteTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

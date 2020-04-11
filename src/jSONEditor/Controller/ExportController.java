package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ExportController {

    /*****************************************************
     * Change Scenes
     *****************************************************/

    public Stage closeExport(Stage stage) {
        System.out.println("Close Export");

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private Stage closeExport(ActionEvent event) {
        // Uses the above method for testing purposes
        return closeExport((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    public Stage export (Stage stage) {
        System.out.println("Export");

        /*
        INSERT EXPORT FUNCTIONALITY HERE
         */

        return closeExport(stage);
    }

    @FXML
    private void export(ActionEvent event) {
        // calls the above method for testing purposes
        export((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

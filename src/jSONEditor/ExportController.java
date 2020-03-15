package jSONEditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ExportController {

    /*****************************************************
     * Change Scenes
     *****************************************************/

    @FXML
    private void closeExport(ActionEvent event) throws IOException {
        System.out.println("Close Export");

        Stage exportStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        exportStage.close();
    }

    @FXML
    private void export(ActionEvent event) throws IOException {
        System.out.println("Export");

        /*
        INSERT EXPORT FUNCTIONALITY HERE
         */

        closeExport(event);
    }
}

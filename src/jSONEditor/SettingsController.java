package jSONEditor;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.stage.Stage;

        import java.io.IOException;

public class SettingsController {
    /*****************************************************
     * Change Scenes
     *****************************************************/

    @FXML
    private void closeSettings(ActionEvent event) throws IOException {
        System.out.println("Close Settings");

        Stage exportStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        exportStage.close();
    }
}

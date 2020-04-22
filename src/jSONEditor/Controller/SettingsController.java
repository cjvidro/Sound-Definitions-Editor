package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class SettingsController {
    /*****************************************************
     * Change Scenes
     *****************************************************/

    @FXML private ToggleButton saveOn;
    @FXML private ToggleButton saveOff;
    @FXML private ToggleButton webOn;
    @FXML private ToggleButton webOff;

    @FXML
    private void initialize() {
        // initialization
        EditorData editorData = EditorData.getInstance();
        if (editorData.autosave) {
            saveOn.setSelected(true);
            saveOff.setSelected(false);
        } else {
            saveOn.setSelected(false);
            saveOff.setSelected(true);
        }

        if (editorData.webBackup) {
            webOn.setSelected(true);
            webOff.setSelected(false);
        } else {
            webOn.setSelected(false);
            webOff.setSelected(true);
        }
    }

    public Stage closeSettings(Stage settingsStage) {
        EditorData editorData = EditorData.getInstance();

        // autosave
        if (!saveOn.isSelected()) {
            System.out.println("Autosave disabled");
            editorData.autosave = false;
        }  else {
            // default
            System.out.println("Autosave enabled");
            editorData.autosave = true;
        }

        // web backup
        if (!webOn.isSelected()) {
            System.out.println("Web backup disabled");
            editorData.webBackup = false;
        } else {
            // default
            System.out.println("Web backup enabled");
            editorData.webBackup = true;
        }

        editorData.serializeSettings();

        System.out.println("Close Settings");

        settingsStage.close();
        settingsStage = null;
        return settingsStage;
    }

    @FXML
    private void closeSettings(ActionEvent event) {
        // Calls above method for testing purposes
        closeSettings((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

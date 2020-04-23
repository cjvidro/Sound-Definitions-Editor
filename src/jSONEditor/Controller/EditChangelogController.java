package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class EditChangelogController {
	
	@FXML public TextField changelogField;
	
    /*****************************************************
     * Change Scenes
     *****************************************************/
	
    @FXML
    public void initialize() {
    	changelogField.setText(EditorData.getInstance().changelog);
    }

    public Stage closeEditChangelog(Stage stage) {
        System.out.println("Close Edit Changelog");

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private Stage closeEditChangelog(ActionEvent event) {
        // Uses the above method for testing purposes
        return closeEditChangelog((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    public void editChangelog(Stage stage) {
        System.out.println("Edit Changelog");
        
        EditorData.getInstance().changelog = changelogField.getText();
        
        closeEditChangelog(stage);
    }

    @FXML
    private void editChangelog(ActionEvent event) {
        // calls the above method for testing purposes
    	editChangelog((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

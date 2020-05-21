package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FolderController {
    private static final EditorData editorData = EditorData.getInstance();
    @FXML private TextField folderName;
    @FXML private Button cancelButton;

    @FXML
    public void initialize() {}

    @FXML
    /**
     * Saves a new folder
     */
    private void saveFolder() {
        String name = folderName.getText();

        // add the folder if it does not already exist
        if (editorData.folders.get(name) == null) {
            PlaysoundGroup folder = new PlaysoundGroup();
            folder.setName(name);
            editorData.folders.put(name, folder);

            showViewProject();
        } else {
            System.out.println("A folder with this name already exists!");
        }
    }

    @FXML
    /**
     * Exits the add Folder scene
     */
    private void showViewProject(ActionEvent event) {
        Stage stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
        stage.close();
        editorData.projectController.refresh();
        System.out.println("Exited Add Folder");
    }

    /**
     * Calls showViewProject when not called from FXML
     */
    private void showViewProject() {
        showViewProject(new ActionEvent(cancelButton, tail -> null));
    }
}

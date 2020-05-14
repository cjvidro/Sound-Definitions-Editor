package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DisplayController {
    EditorData editorData = EditorData.getInstance();
    @FXML protected TitledPane playsoundNode;
    @FXML protected VBox soundBoxContainer;
    @FXML protected Label soundNodeName;
    @FXML protected Label soundProperties;

    @FXML
    public void initialize() {
    }

    @FXML
    /**
     * FXML method to delete an existing playsound
     */
    public void deletePlaysound(ActionEvent event) {

        // Find the playsound
        Playsound playsound = null;
        for (Playsound p : editorData.playsounds) {
            if (p.getName().equals(playsoundNode.getText())) {
                playsound = p;
                break;
            }
        }

        deletePlaysound(playsound);
    }

    /**
     * Deletes a playsound
     * @param playsound to be deleted
     */
    private void deletePlaysound(Playsound playsound) {
        if (playsound != null) {
            editorData.playsounds.remove(playsound);
            playsound.getGroup().playsounds.remove(playsound);
            System.out.println("Removed playsound " + playsound.getName());
            refresh();
        }
    }

    private void refresh() {
        try {
            editorData.projectController.populateFolders();
            String curFolder = editorData.projectController.getCurrentFolder();
            editorData.projectController.showFolder(curFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

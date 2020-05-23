package jSONEditor.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DisplayController {
    private static final EditorData editorData = EditorData.getInstance();
    @FXML protected TitledPane playsoundNode;
    @FXML protected VBox soundBoxContainer;
    @FXML protected Label soundNodeName;
    @FXML protected Label soundProperties;
    @FXML protected Button deleteSoundButton;
    @FXML protected Button deletePlaysoundButton;

    @FXML
    public void initialize() {
    }

    @FXML
    public void editPlaysound() {
        String playsoundName = playsoundNode.getText();
        Playsound playsound = null;

        for (Playsound p : editorData.playsounds) {
            if (p.getName().equals(playsoundName)) {
                playsound = p;
                break;
            }
        }

        if (playsound != null) {
            ProjectController p = new ProjectController();
            try {
                p.showEditPlaysound(playsound);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to show edit playsound!");
        }
    }
}

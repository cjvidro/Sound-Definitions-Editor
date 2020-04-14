package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HomeScreenController {
    /*****************************************************
     * FXML fields
     *****************************************************/
    @FXML private Button newProjectButton;
    @FXML private Button openProjectButton;
    @FXML private Button project1Button;
    @FXML private Button project2Button;
    @FXML private Button project3Button;
    @FXML private Button project4Button;
    @FXML private Button project5Button;

    /*****************************************************
     * Change Scenes and Button Functionality
     *****************************************************/
    @FXML
    private void initialize() {
        // populate recent saves
        File[] saves = EditorData.getInstance().saves;

        if (project1Button != null && saves[0] != null) {
            project1Button.setDisable(false);
            project1Button.setText(saves[0].getName());
            System.out.println("Loaded first button");
        }
        if (project2Button != null && saves[1] != null) {
            project2Button.setDisable(false);
            project2Button.setText(saves[1].getName());
        }
        if (project3Button != null && saves[2] != null) {
            project3Button.setDisable(false);
            project3Button.setText(saves[2].getName());
        }
        if (project3Button != null && saves[3] != null) {
            project3Button.setDisable(false);
            project3Button.setText(saves[3].getName());
        }
        if (project4Button != null && saves[4] != null) {
            project4Button.setDisable(false);
            project4Button.setText(saves[4].getName());
        }
    }

    public Stage newProject(Stage stage) throws IOException {
        // load FXML and set the controllers
        ProjectController controller = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/viewProject.fxml")));
        loader.setController(controller); // view project controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage viewProject = new Stage();
        viewProject.setTitle("JSON Sound Definitions Editor");
        viewProject.setScene(new Scene(root, 1280, 720));
        viewProject.show();

        // close the current
        closeHomeScreen(stage);

        return viewProject;
    }

    @FXML
    private void newProject(ActionEvent event) throws IOException {
        // Calls the above helper function for testing purposes
        newProject((Stage) ((Button) event.getSource()).getScene().getWindow());

    }

    public Stage closeHomeScreen(Stage settingsStage) {
        System.out.println("Close Home Screen");

        settingsStage.close();
        settingsStage = null;
        return settingsStage;
    }
}

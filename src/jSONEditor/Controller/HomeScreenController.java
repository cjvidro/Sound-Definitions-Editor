package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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

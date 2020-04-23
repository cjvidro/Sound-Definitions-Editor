package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
            project1Button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadProject(saves[0], (Stage) project1Button.getScene().getWindow());
                }
            });
    }
        if (project2Button != null && saves[1] != null) {
            project2Button.setDisable(false);
            project2Button.setText(saves[1].getName());
            project2Button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadProject(saves[1], (Stage) project2Button.getScene().getWindow());
                }
            });
        }
        if (project3Button != null && saves[2] != null) {
            project3Button.setDisable(false);
            project3Button.setText(saves[2].getName());
            project3Button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadProject(saves[2], (Stage) project3Button.getScene().getWindow());
                }
            });
        }
        if (project4Button != null && saves[3] != null) {
            project4Button.setDisable(false);
            project4Button.setText(saves[3].getName());
            project4Button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadProject(saves[3], (Stage) project4Button.getScene().getWindow());
                }
            });
        }
        if (project5Button != null && saves[4] != null) {
            project5Button.setDisable(false);
            project5Button.setText(saves[4].getName());
            project5Button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadProject(saves[4], (Stage) project5Button.getScene().getWindow());
                }
            });
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
        viewProject.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        viewProject.show();

        // close the current
        closeHomeScreen(stage);

        System.out.println("Created new project!");
        return viewProject;
    }

    private Stage loadProject(File save, Stage stage){
        // load FXML and set the controllers
        ProjectController controller = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/viewProject.fxml")));
        loader.setController(controller); // view project controller
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        SoundIO.loadSoundDefinitions(save);
        controller.populatePlaysounds();
        EditorData.getInstance().currentDirectory = save;

        // set JavaFX stage details
        Stage viewProject = new Stage();
        viewProject.setTitle("JSON Sound Definitions Editor");
        viewProject.setScene(new Scene(root, 1280, 720));
        viewProject.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        viewProject.show();

        // close the current window
        closeHomeScreen(stage);

        System.out.println("Loaded project " + save.getName());
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

    @FXML
    private void importSoundDefinitions(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // load FXML and set the controllers
        ProjectController controller = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/viewProject.fxml")));
        loader.setController(controller); // view project controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage viewProject = new Stage();
        viewProject.setTitle("JSON Sound Definitions Editor");
        viewProject.setScene(new Scene(root, 1280, 720));
        viewProject.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        viewProject.show();

        // close the current
        closeHomeScreen(stage);

        controller.importSoundDefinitions();
    }
}

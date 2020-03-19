package jSONEditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectController {

    /*****************************************************
     * Change Scenes and Button Functionality
     *****************************************************/
    @FXML
    protected boolean quit(ActionEvent event) throws Exception {
        System.out.println("Quit");

        /*
        INSERT Prompt Save FUNCTIONALITY HERE
         */

        class ExpectedQuitException extends Exception {
            public ExpectedQuitException(String message) {
                super(message);
            }
        }
        throw new ExpectedQuitException("User exited");
    }

    @FXML
    protected Stage showExport(ActionEvent event) throws IOException {
        System.out.println("Show Export");

        // load FXML and set the controller
        ExportController controller = new ExportController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/export.fxml")));
        loader.setController(controller); // export controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage exportWindow = new Stage();
        exportWindow.setTitle("JSON Sound Definitions Editor - Export");
        exportWindow.setScene(new Scene(root, 350, 190));
        exportWindow.initModality(Modality.APPLICATION_MODAL);
        exportWindow.setResizable(false);
        exportWindow.show();

        return exportWindow;
    }

    @FXML
    protected Stage showSettings(ActionEvent event) throws IOException {
        System.out.println("Show Settings");

        // load FXML and set the controller
        SettingsController controller = new SettingsController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/settings.fxml")));
        loader.setController(controller); // export controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage settingsWindow = new Stage();
        settingsWindow.setTitle("JSON Sound Definitions Editor - Settings");
        settingsWindow.setScene(new Scene(root, 400, 200));
        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.setResizable(false);
        settingsWindow.show();

        return settingsWindow;
    }

    @FXML
    protected Stage showAddTemplate(ActionEvent event) throws IOException {
        System.out.println("Show Add Template");

        // load FXML and set the controller
        AddTemplateController controller = new AddTemplateController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/addTemplate.fxml")));
        loader.setController(controller); // addTemplate controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage addTemplateWindow = new Stage();
        addTemplateWindow.setTitle("JSON Sound Definitions Editor - Add Template");
        addTemplateWindow.setScene(new Scene(root, 325, 400));
        addTemplateWindow.initModality(Modality.APPLICATION_MODAL);
        addTemplateWindow.setResizable(false);
        addTemplateWindow.show();

        return addTemplateWindow;
    }

    @FXML
    protected Stage showEditTemplate(ActionEvent event) throws IOException {
        System.out.println("Show Edit Template");

        // load FXML and set the controller
        EditTemplateController controller = new EditTemplateController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/editTemplate.fxml")));
        loader.setController(controller); // addTemplate controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage editTemplateWindow = new Stage();
        editTemplateWindow.setTitle("JSON Sound Definitions Editor - Edit Template");
        editTemplateWindow.setScene(new Scene(root, 325, 400));
        editTemplateWindow.initModality(Modality.APPLICATION_MODAL);
        editTemplateWindow.setResizable(false);
        editTemplateWindow.show();

        return editTemplateWindow;
    }

    protected Stage showAddPlaysound(Stage addPlaysoundWindow) throws IOException {
        System.out.println("Show Add Playsound");

        // load FXML and set the controller
        ProjectController controller = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/addPlaysound.fxml")));
        loader.setController(controller); // addPlaysound/viewProject controller
        Parent root = loader.load();

        double width =  addPlaysoundWindow.getScene().getWidth();
        double height = addPlaysoundWindow.getScene().getHeight();

        // set JavaFX stage details
        addPlaysoundWindow.setScene(new Scene(root, width, height)); // swap scenes
        addPlaysoundWindow.setTitle("JSON Sound Definitions Editor - Add Playsound");

        return addPlaysoundWindow;
    }

    @FXML
    private void showAddPlaysound(ActionEvent event) throws IOException {
        // Calls the above helper method for testing purposes
        showAddPlaysound((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    protected Stage saveAddPlaysound(Stage viewProjectWindow) throws IOException {
        System.out.println("Save Add Playsound");

        // load FXML and set the controller
        ProjectController controller = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/viewProject.fxml")));
        loader.setController(controller); // addPlaysound/viewProject controller
        Parent root = loader.load();

        /*
        INSERT Save FUNCTIONALITY HERE
         */

        double width =  viewProjectWindow.getScene().getWidth();
        double height = viewProjectWindow.getScene().getHeight();

        // set JavaFX stage details
        viewProjectWindow.setScene(new Scene(root, width, height)); // swap scenes
        viewProjectWindow.setTitle("JSON Sound Definitions Editor");

        return viewProjectWindow;
    }

    @FXML
    private void saveAddPlaysound(ActionEvent event) throws IOException {
        // Calls the above helper method for testing purposes
        saveAddPlaysound((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    protected Stage cancelAddPlaysound(Stage viewProjectWindow) throws IOException {
        System.out.println("Cancel Add Playsound");

        // load FXML and set the controller
        ProjectController controller = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/viewProject.fxml")));
        loader.setController(controller); // addPlaysound/viewProject controller
        Parent root = loader.load();

         /*
        INSERT Cancel FUNCTIONALITY HERE
         */

        double width =  viewProjectWindow.getScene().getWidth();
        double height = viewProjectWindow.getScene().getHeight();

        // set JavaFX stage details
        viewProjectWindow.setScene(new Scene(root, width, height)); // swap scenes
        viewProjectWindow.setTitle("JSON Sound Definitions Editor");

        return viewProjectWindow;
    }


    @FXML
    private void cancelAddPlaysound(ActionEvent event) throws IOException {
        // Calls the above helper method for testing purposes
        cancelAddPlaysound((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

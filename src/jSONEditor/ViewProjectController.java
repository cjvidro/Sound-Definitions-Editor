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

public class ViewProjectController {

    /*****************************************************
     * Change Scenes and Button Functionality
     *****************************************************/
    @FXML
    private void quit(ActionEvent event) throws IOException {
        System.out.println("Quit");

        /*
        INSERT Prompt Save FUNCTIONALITY HERE
         */

        System.exit(0);
    }

    @FXML
    private void showExport(ActionEvent event) throws IOException {
        System.out.println("Show Export");

        // load FXML and set the controller
        ExportController controller = new ExportController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/export.fxml")));
        loader.setController(controller); // export controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage exportWindow = new Stage();
        exportWindow.setTitle("JSON Sound Definitions Editor");
        exportWindow.setScene(new Scene(root, 350, 190));
        exportWindow.initModality(Modality.APPLICATION_MODAL);
        exportWindow.setResizable(false);
        exportWindow.show();
    }

    @FXML
    private void showSettings(ActionEvent event) throws IOException {
        System.out.println("Show Settings");

        // load FXML and set the controller
        SettingsController controller = new SettingsController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/settings.fxml")));
        loader.setController(controller); // export controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage settingsWindow = new Stage();
        settingsWindow.setTitle("JSON Sound Definitions Editor");
        settingsWindow.setScene(new Scene(root, 400, 200));
        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.setResizable(false);
        settingsWindow.show();
    }

    @FXML
    private void showAddTemplate(ActionEvent event) throws IOException {
        System.out.println("Show Add Template");

        // load FXML and set the controller
        AddTemplateController controller = new AddTemplateController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/addTemplate.fxml")));
        loader.setController(controller); // addTemplate controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage addTemplateWindow = new Stage();
        addTemplateWindow.setTitle("JSON Sound Definitions Editor");
        addTemplateWindow.setScene(new Scene(root, 325, 400));
        addTemplateWindow.initModality(Modality.APPLICATION_MODAL);
        addTemplateWindow.setResizable(false);
        addTemplateWindow.show();
    }

    @FXML
    private void showEditTemplate(ActionEvent event) throws IOException {
        System.out.println("Show Edit Template");

        // load FXML and set the controller
        EditTemplateController controller = new EditTemplateController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/editTemplate.fxml")));
        loader.setController(controller); // addTemplate controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage addTemplateWindow = new Stage();
        addTemplateWindow.setTitle("JSON Sound Definitions Editor");
        addTemplateWindow.setScene(new Scene(root, 325, 400));
        addTemplateWindow.initModality(Modality.APPLICATION_MODAL);
        addTemplateWindow.setResizable(false);
        addTemplateWindow.show();
    }

    @FXML
    private void showAddPlaysound(ActionEvent event) throws IOException {
        System.out.println("Show Add Playsound");

        // load FXML and set the controller
        ViewProjectController controller = new ViewProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/addPlaysound.fxml")));
        loader.setController(controller); // addPlaysound/viewProject controller
        Parent root = loader.load();

        Stage addPlaysoundWindow = (Stage) ((Button) event.getSource()).getScene().getWindow();

        double width =  ((Button) event.getSource()).getScene().getWidth();
        double height = ((Button) event.getSource()).getScene().getHeight();

        // set JavaFX stage details
        addPlaysoundWindow.setScene(new Scene(root, width, height));
    }

    @FXML
    private void saveAddPlaysound(ActionEvent event) throws IOException {
        System.out.println("Save Add Playsound");

        // load FXML and set the controller
        ViewProjectController controller = new ViewProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/viewProject.fxml")));
        loader.setController(controller); // addPlaysound/viewProject controller
        Parent root = loader.load();

        /*
        INSERT Save FUNCTIONALITY HERE
         */

        Stage viewProjectWindow = (Stage) ((Button) event.getSource()).getScene().getWindow();

        double width =  ((Button) event.getSource()).getScene().getWidth();
        double height = ((Button) event.getSource()).getScene().getHeight();

        // set JavaFX stage details
        viewProjectWindow.setScene(new Scene(root, width, height));
    }

    @FXML
    private void cancelAddPlaysound(ActionEvent event) throws IOException {
        System.out.println("Cancel Add Playsound");

        // load FXML and set the controller
        ViewProjectController controller = new ViewProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/viewProject.fxml")));
        loader.setController(controller); // addPlaysound/viewProject controller
        Parent root = loader.load();

        /*
        INSERT Cancel FUNCTIONALITY HERE
         */

        Stage viewProjectWindow = (Stage) ((Button) event.getSource()).getScene().getWindow();

        double width =  ((Button) event.getSource()).getScene().getWidth();
        double height = ((Button) event.getSource()).getScene().getHeight();

        // set JavaFX stage details
        viewProjectWindow.setScene(new Scene(root, width, height));
    }
}

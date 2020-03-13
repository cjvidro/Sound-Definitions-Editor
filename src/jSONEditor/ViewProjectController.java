package jSONEditor;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewProjectController {

    /*****************************************************
     * FXML Elements
     *****************************************************/

    @FXML // Needed by FXML to initialize anything
    private void initialize() {
        // Perform any initializations here
    }

    /*****************************************************
     * Change Scenes
     *****************************************************/
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
        exportWindow.setScene(new Scene(root, 500, 190));
        exportWindow.initModality(Modality.APPLICATION_MODAL);
        exportWindow.setResizable(false);
        exportWindow.show();
    }

    /*****************************************************
     * Change Scenes
     *****************************************************/

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
    private void showSettings(Event event) throws IOException {
        showSettings(new ActionEvent());
    }
}

package jSONEditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ViewProjectController {

    /*****************************************************
     * FXML Elements
     *****************************************************/
    @FXML
    private Menu settingsButton;

    @FXML
    private VBox topVBox;

    @FXML // Needed by FXML to initialize anything
    private void initialize() {
        // Perform any initializations here
    }

    /*****************************************************
     * Change Scenes
     *****************************************************/

    // view Settings
    @FXML
    private void showSettings(ActionEvent event) throws IOException {
        event.consume();
        System.out.println("test");

//        // Setup view and controller
//        SettingsController controller = new SettingsController();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Settings.fxml"));
//        loader.setController(controller);
//        Parent loginView = loader.load();
//        Scene loginScene = new Scene(loginView);
//
//        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//        window.setWidth(((Node)event.getSource()).getScene().getWidth());
//        window.setHeight(((Node)event.getSource()).getScene().getHeight());
//        window.setScene(loginScene);
//        window.show();
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
        exportWindow.setScene(new Scene(root, 500, 190));
        exportWindow.initModality(Modality.APPLICATION_MODAL);
        exportWindow.setResizable(false);
        exportWindow.show();
    }
}

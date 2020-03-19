package jSONEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

    public class Main extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception{
            // load FXML and set the controller
            ViewProjectController controller = new ViewProjectController(); // the controller for the view project GUI
            FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/viewProject.fxml")));
            loader.setController(controller); // view project controller
            Parent root = loader.load();

            // set JavaFX stage details
            primaryStage.setTitle("JSON Sound Definitions Editor");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.show();
        }
    
    public static void main(String[] args) {
        launch(args);
    }
}
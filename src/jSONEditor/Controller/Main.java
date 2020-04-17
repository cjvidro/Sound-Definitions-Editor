package jSONEditor.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

    public class Main extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception{
            EditorData.getInstance(); // start internal initialization

            // load FXML and set the controller
            HomeScreenController controller = new HomeScreenController(); // the controller for the home screen
            FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/homeScreen.fxml")));
            loader.setController(controller); // home screen controller
            Parent root = loader.load();

            // set JavaFX stage details
            primaryStage.setTitle("JSON Sound Definitions Editor - Select Project");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.show();
        }
    
    public static void main(String[] args) {
        launch(args);
    }
}
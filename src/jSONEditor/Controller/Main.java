package jSONEditor.Controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception{
            EditorData.getInstance(); // start internal initialization

            // Auto save
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            Runnable autoSave = () -> {
                System.out.println("Starting auto save. . .");
                if (EditorData.getInstance().currentDirectory != null) {
                    if (SoundIO.saveProject()) {
                        System.out.println("Auto save success!");
                    } else {
                        System.out.println("Failed auto save!");
                    }
                } else {
                    System.out.println("Failed auto save! No current save directory.");
                }
            };
            executor.scheduleWithFixedDelay(autoSave, 1, 1, TimeUnit.MINUTES);


            // load FXML and set the controller
            HomeScreenController controller = new HomeScreenController(); // the controller for the home screen
            FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/homeScreen.fxml")));
            loader.setController(controller); // home screen controller
            Parent root = loader.load();

            // set JavaFX stage details
            primaryStage.setTitle("JSON Sound Definitions Editor - Select Project");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });
            primaryStage.show();
        }
    
    public static void main(String[] args) {
        launch(args);
    }
}
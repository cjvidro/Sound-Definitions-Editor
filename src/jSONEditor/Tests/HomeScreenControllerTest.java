package jSONEditor.Tests;

import jSONEditor.Controller.HomeScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class HomeScreenControllerTest {
    private Stage myStage;
    HomeScreenController controller;
    ActionEvent event;

    @Before
    public void start() throws Exception {
        event = new ActionEvent();

        controller = new HomeScreenController(); // the controller for home screen GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/homeScreen.fxml")));
        loader.setController(controller); // home screen Controller
        Parent root = loader.load();

        // set JavaFX stage details
        myStage = new Stage();
        myStage.setTitle("JSON Sound Definitions Editor - Select Project");
        myStage.setScene(new Scene(root, 1280, 720));
        myStage.show();
    }

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void testSetup() {
        assertNotNull(myStage);
        assertEquals("JSON Sound Definitions Editor - Select Project", myStage.getTitle());
    }

    @Test
    public void testCloseHomeScreen() {
        assertNull(controller.closeHomeScreen(myStage));
    }

    @Test public void testNewProject() {
        try {
            Stage viewProject = controller.newProject(myStage);
            assertNotNull(viewProject);
            assertEquals("JSON Sound Definitions Editor", viewProject.getTitle());
        } catch (
                IOException e) {
            fail();
        }
    }
}
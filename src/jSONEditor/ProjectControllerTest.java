package jSONEditor;

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

public class ProjectControllerTest {

    private Stage myStage;
    ProjectController controller;
    ActionEvent event;

    @Before
    public void start() throws Exception {
        event = new ActionEvent();

        controller = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/viewProject.fxml")));
        loader.setController(controller); // view project controller
        Parent root = loader.load();

        // set JavaFX stage details
        myStage = new Stage();
        myStage.setTitle("JSON Sound Definitions Editor");
        myStage.setScene(new Scene(root, 1280, 720));
        myStage.show();
    }

    @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    @Test
    public void testSetup() {
        assertNotNull(myStage);
        assertEquals("JSON Sound Definitions Editor", myStage.getTitle());
    }

    @Test
    public void testQuit() {
        try {
            controller.quit();
            fail();
        } catch (Exception e) {
            assertEquals("User exited", e.getMessage());
        }
    }

    @Test
    public void testShowExport() {
        try {
            Stage export = controller.showExport(event);
            assertNotNull(export);
            assertEquals("JSON Sound Definitions Editor - Export", export.getTitle());

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testShowSettings() {
        try {
            Stage settings = controller.showSettings(event);
            assertNotNull(settings);
            assertEquals("JSON Sound Definitions Editor - Settings", settings.getTitle());

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testShowAddTemplate() {
        try {
            Stage template = controller.showAddTemplate(event);
            assertNotNull(template);
            assertEquals("JSON Sound Definitions Editor - Add Template", template.getTitle());

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testShowEditTemplate() {
        try {
            Stage template = controller.showEditTemplate(event);
            assertNotNull(template);
            assertEquals("JSON Sound Definitions Editor - Edit Template", template.getTitle());

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testShowAddPlaysound() {
        try {
            Stage window = controller.showAddPlaysound(myStage);
            assertNotNull(window);
            assertEquals("JSON Sound Definitions Editor - Add Playsound", window.getTitle());

        } catch (IOException e) {
            fail();
        }
    }

    /*
    Need to rewrite according to new functionality. . .
     */
//    @Test
//    public void testSaveAddPlaysound() {
//        try {
//            Stage temp = controller.showAddPlaysound(myStage);
//            Stage window = controller.saveAddPlaysound(temp);
//            assertNotNull(window);
//            assertEquals("JSON Sound Definitions Editor", window.getTitle());
//
//        } catch (IOException e) {
//            fail();
//        }
//    }
}
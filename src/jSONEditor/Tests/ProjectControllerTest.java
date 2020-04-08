package jSONEditor.Tests;

import jSONEditor.Controller.EditorData;
import jSONEditor.Controller.Playsound;
import jSONEditor.Controller.PlaysoundGroup;
import jSONEditor.Controller.ProjectController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/viewProject.fxml")));
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

    @Test public void testGetSoundHBoxes() {
        HBox overlyingbox = null;
        HBox[] result = controller.getSoundHBoxes(overlyingbox);

        // bad input test
        for (HBox box : result) {
            assertNull(box);
        }

        overlyingbox = new HBox();
        VBox containingBox = new VBox();
        overlyingbox.getChildren().addAll(new Pane(), containingBox);

        HBox directoryBox = new HBox(new Pane());
        HBox streamBox = new HBox(new Pane());
        HBox volumeBox = new HBox(new Pane());
        HBox pitchBox = new HBox(new Pane());
        HBox lolmBox = new HBox(new Pane());

        containingBox.getChildren().addAll(directoryBox, streamBox, volumeBox, pitchBox, lolmBox);

        result = controller.getSoundHBoxes(overlyingbox);

        assertEquals(directoryBox, result[0]);
        assertEquals(streamBox, result[1]);
        assertEquals(volumeBox, result[2]);
        assertEquals(pitchBox, result[3]);
        assertEquals(lolmBox, result[4]);
    }

    @Test
    public void testValidateIncrement(){
        assertEquals(1, controller.validateIncrement("-2"));
        assertEquals(1, controller.validateIncrement("0"));
        assertEquals(1, controller.validateIncrement("1"));
        assertEquals(5, controller.validateIncrement("5"));
        assertEquals(5, controller.validateIncrement("5.4"));
    }

    @Test
    public void testValidateIncrementNames() {
        EditorData editorData = EditorData.getInstance();

        assertTrue(controller.validateIncrementNames("Hello", 3));

        Playsound playsound = new Playsound();
        playsound.setName("Hello2");
        editorData.playsounds.add(playsound);

        assertFalse(controller.validateIncrementNames("Hello", 3));
    }

    @Test public void testShowViewProject() {
        Stage stage = new Stage();
        try {
            stage = controller.showViewProject(myStage);
        } catch (IOException e) {
            fail();
        }

        assertNotNull(stage);
        assertEquals("JSON Sound Definitions Editor", stage.getTitle());
    }

    @Test
    public void testCheckDirectory() {
        assertFalse(controller.checkDirectory(""));
        assertTrue(controller.checkDirectory("abc"));
    }

    @Test
    public void checkIfNameExists() {
        assertTrue(controller.checkIfNameExists("rada"));
        EditorData editorData = EditorData.getInstance();
        Playsound playsound = new Playsound();
        playsound.setName("rada");
        editorData.playsounds.add(playsound);
        assertFalse(controller.checkIfNameExists("rada"));
    }

    @Test
    public void testIncrement() {
        assertFalse(controller.checkIncrement(new String()));
        assertFalse(controller.checkIncrement(""));
        assertTrue(controller.checkIncrement("4"));
        assertFalse(controller.checkIncrement("3.24"));
    }

    @Test
    public void checkDouble() {
        assertFalse(controller.checkDouble(null));
        assertTrue(controller.checkDouble(""));
        assertFalse(controller.checkDouble("abc"));
        assertTrue(controller.checkDouble("3.0"));
    }

    @Test public void testDeletePlaysound() {
        Playsound playsound = new Playsound();
        playsound.setName("testName");
        EditorData editorData = EditorData.getInstance();
        editorData.playsounds.add(playsound);
        assertEquals(1, editorData.playsounds.size());

        controller.deletePlaysound("testName", "", 1);
        assertEquals(0, editorData.playsounds.size());

        PlaysoundGroup group = new PlaysoundGroup();
        group.setName("testName");
        group.playsounds.add(playsound);

        playsound.setName("testName1");
        playsound.setGroup(group);
        Playsound playsoundTwo = new Playsound();
        playsoundTwo.setName("testName2");
        playsoundTwo.setGroup(group);
        group.playsounds.add(playsoundTwo);

        editorData.playsounds.add(playsound);
        editorData.playsounds.add(playsoundTwo);

        assertEquals(2, editorData.playsounds.size());

        controller.deletePlaysound("", "testName", 2);
        assertEquals(0, editorData.playsounds.size());

        assertFalse(controller.deletePlaysound("", "", 1));
    }

    @Test
    public void testSaveCall() {
        assertTrue(controller.savePlaysounds());
    }

}
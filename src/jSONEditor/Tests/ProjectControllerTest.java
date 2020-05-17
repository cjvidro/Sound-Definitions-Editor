package jSONEditor.Tests;

import jSONEditor.Controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ProjectControllerTest {

    private Stage myStage;
    ProjectController controller;
    ActionEvent event;

    @Before
    public void start() throws Exception {
        event = new ActionEvent();
        EditorData editorData = EditorData.getInstance();
        editorData.playsounds = new ArrayList<>();
        editorData.templates = new ArrayList<>();

        controller = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/viewProject.fxml")));
        loader.setController(controller); // view project controller
        Parent root = loader.load();

        // set JavaFX stage details
        myStage = new Stage();
        myStage.setTitle("JSON Sound Definitions Editor");
        myStage.setScene(new Scene(root, 1280, 720));
    }

    @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    @Test
    public void testSetup() {
        assertNotNull(myStage);
        assertEquals("JSON Sound Definitions Editor", myStage.getTitle());
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
        	EditorData instance = EditorData.getInstance();
        	
        	Template temp = new Template();
        	temp.setName("Test");
        	temp.setLOLMSetting(0);
        	instance.templates.add(temp);
        	
        	EditTemplateController edit = new EditTemplateController();
        	edit.setTemplate(temp);
        	
            Stage template = controller.showEditTemplate(event);
            assertNotNull(template);
            assertEquals("JSON Sound Definitions Editor - Edit Template", template.getTitle());

        } catch (IOException e) {
        	e.printStackTrace();
            fail();
        }
    }

//    @Test
//    public void testShowAddPlaysound() {
//        try {
//            Stage window = controller.showAddPlaysound(myStage);
//            assertNotNull(window);
//            assertEquals("JSON Sound Definitions Editor - Add Playsound", window.getTitle());
//
//        } catch (IOException e) {
//            fail();
//        }
//    }

//    @Test public void testGetSoundHBoxes() {
//        HBox overlyingbox = null;
//        HBox[] result = controller.getSoundHBoxes(overlyingbox);
//
//        // bad input test
//        for (HBox box : result) {
//            assertNull(box);
//        }
//
//        overlyingbox = new HBox();
//        VBox containingBox = new VBox();
//        overlyingbox.getChildren().addAll(new Pane(), containingBox);
//
//        HBox directoryBox = new HBox(new Pane());
//        HBox streamBox = new HBox(new Pane());
//        HBox volumeBox = new HBox(new Pane());
//        HBox lolmBox = new HBox(new Pane());
//
//        containingBox.getChildren().addAll(directoryBox, streamBox, volumeBox, lolmBox);
//
//        result = controller.getSoundHBoxes(overlyingbox);
//
//        assertEquals(directoryBox, result[0]);
//        assertEquals(streamBox, result[1]);
//        assertEquals(volumeBox, result[2]);
//        assertEquals(lolmBox, result[3]);
//    }
//
//    @Test
//    public void testValidateIncrement(){
//        assertEquals(1, controller.validateIncrement("-2"));
//        assertEquals(1, controller.validateIncrement("0"));
//        assertEquals(1, controller.validateIncrement("1"));
//        assertEquals(5, controller.validateIncrement("5"));
//        assertEquals(5, controller.validateIncrement("5.4"));
//    }
//
//    @Test
//    public void testValidateIncrementNames() {
//        EditorData editorData = EditorData.getInstance();
//
//        assertTrue(controller.validateIncrementNames("Hello", 3));
//
//        Playsound playsound = new Playsound();
//        playsound.setName("Hello2");
//        editorData.playsounds.add(playsound);
//
//        assertFalse(controller.validateIncrementNames("Hello", 3));
//    }
//
//    @Test public void testShowViewProject() {
//        Stage stage = new Stage();
//        try {
//            stage = controller.showViewProject(myStage);
//        } catch (IOException e) {
//            fail();
//        }
//
//        assertNotNull(stage);
//        assertEquals("JSON Sound Definitions Editor", stage.getTitle());
//    }
//
//    @Test
//    public void testCheckDirectory() {
//        assertFalse(controller.checkName(""));
//        assertTrue(controller.checkName("abc"));
//    }
//
//    @Test
//    public void checkIfNameExists() {
//        assertTrue(controller.checkIfNameExists("rada"));
//        EditorData editorData = EditorData.getInstance();
//        Playsound playsound = new Playsound();
//        playsound.setName("rada");
//        editorData.playsounds.add(playsound);
//        assertFalse(controller.checkIfNameExists("rada"));
//        assertFalse(controller.checkIfNameExists(""));
//    }
//
//    @Test
//    public void testIncrement() {
//        assertFalse(controller.checkIncrement(new String()));
//        assertFalse(controller.checkIncrement(""));
//        assertTrue(controller.checkIncrement("4"));
//        assertFalse(controller.checkIncrement("3.24"));
//    }
//
//    @Test
//    public void checkDouble() {
//        assertFalse(controller.checkDouble(null));
//        assertTrue(controller.checkDouble(""));
//        assertFalse(controller.checkDouble("abc"));
//        assertTrue(controller.checkDouble("3.0"));
//    }
//
//    @Test public void testDeletePlaysound() {
//        Playsound playsound = new Playsound();
//        playsound.setName("testName");
//        EditorData editorData = EditorData.getInstance();
//        editorData.playsounds.add(playsound);
//        assertEquals(1, editorData.playsounds.size());
//
//        controller.deletePlaysound("testName", "", 1);
//        assertEquals(0, editorData.playsounds.size());
//
//        PlaysoundGroup group = new PlaysoundGroup();
//        group.setName("testName");
//        group.playsounds.add(playsound);
//
//        playsound.setName("testName1");
//        playsound.setGroup(group);
//        Playsound playsoundTwo = new Playsound();
//        playsoundTwo.setName("testName2");
//        playsoundTwo.setGroup(group);
//        group.playsounds.add(playsoundTwo);
//
//        editorData.playsounds.add(playsound);
//        editorData.playsounds.add(playsoundTwo);
//
//        assertEquals(2, editorData.playsounds.size());
//
//        controller.deletePlaysound("", "testName", 2);
//        assertEquals(0, editorData.playsounds.size());
//
//        assertFalse(controller.deletePlaysound("", "", 1));
//    }
//
//    @Test
//    public void testValidateSounds() throws IOException {
//        controller.showAddPlaysound(myStage);
//        ProjectController playsoundController = controller.playsoundControllerReference;
//
//        assertFalse(controller.validateSounds()); // soundsVBox is null
//        assertFalse(playsoundController.validateSounds()); // empty directory
//
//        ((TextField)((HBox)((VBox)((HBox)playsoundController.soundsVBox.getChildren().get(0)).getChildren()
//                .get(1)).getChildren().get(0)).getChildren().get(2)).setText("testDirectory");
//
//        assertTrue(playsoundController.validateSounds());
//
//    }
//
//    @Test
//    public void testValidatePlaysound() throws IOException {
//        controller.showAddPlaysound(myStage);
//        ProjectController playsoundController = controller.playsoundControllerReference;
//
//        assertFalse(controller.validatePlaysound()); // no namefield is null
//        assertFalse(playsoundController.validatePlaysound()); // empty name
//
//        playsoundController.nameField.setText("testText");
//
//        assertFalse((playsoundController.validatePlaysound())); // no directory
//
//        ((TextField)((HBox)((VBox)((HBox)playsoundController.soundsVBox.getChildren().get(0)).getChildren()
//                .get(1)).getChildren().get(0)).getChildren().get(2)).setText("testDirectory");
//
//        assertTrue(playsoundController.validatePlaysound());
//    }
//
//
//    @Test
//    public void testCreatePlaysound() throws IOException {
//        controller.showAddPlaysound(myStage);
//        ProjectController playsoundController = controller.playsoundControllerReference;
//
//        assertFalse(controller.createPlaysound()); // fails validation
//
//        playsoundController.nameField.setText("testText");
//        ((TextField)((HBox)((VBox)((HBox)playsoundController.soundsVBox.getChildren().get(0)).getChildren()
//                .get(1)).getChildren().get(0)).getChildren().get(2)).setText("testDirectory");
//
//        assertTrue(playsoundController.createPlaysound());
//
//        playsoundController.incrementBox.setText("3");
//        assertFalse(playsoundController.createPlaysound());
//
//        playsoundController.nameField.setText("tester123");
//        assertTrue(playsoundController.createPlaysound());
//
//        playsoundController.nameField.setText("withMoreStuff");
//        assertTrue(playsoundController.createPlaysound());
//
//        ((TextField)((HBox)((VBox)((HBox)playsoundController.soundsVBox.getChildren().get(0)).getChildren()
//                .get(1)).getChildren().get(2)).getChildren().get(2)).setText("1.9");
//        ((CheckBox)((HBox)((VBox)((HBox)playsoundController.soundsVBox.getChildren().get(0)).getChildren()
//                .get(1)).getChildren().get(3)).getChildren().get(2)).setSelected(true);
//        playsoundController.nameField.setText("Final");
//        assertTrue(playsoundController.createPlaysound());
//    }
//
//    @Test
//    public void testShowEditSingle() throws IOException{
//        // add a playsound
//        controller.showAddPlaysound(myStage);
//        ProjectController playsoundController = controller.playsoundControllerReference;
//
//        playsoundController.nameField.setText("testText");
//        ((TextField)((HBox)((VBox)((HBox)playsoundController.soundsVBox.getChildren().get(0)).getChildren()
//                .get(1)).getChildren().get(0)).getChildren().get(2)).setText("testDirectory");
//        playsoundController.createPlaysound();
//
//        EditorData editorData = EditorData.getInstance();
//        Stage stage = controller.showEditSingle(editorData.playsounds.get(0), myStage);
//
//        assertNotNull(stage);
//        assertEquals("JSON Sound Definitions Editor - Edit Playsound", stage.getTitle());
//    }
//
//    @Test
//    public void testEditGroup() throws IOException {
//        // add a playsound
//        controller.showAddPlaysound(myStage);
//        ProjectController playsoundController = controller.playsoundControllerReference;
//
//        EditorData editorData = EditorData.getInstance();
//
//        PlaysoundGroup playsoundGroup = new PlaysoundGroup();
//        playsoundGroup.setName("abc");
//
//        Playsound playsound1 = new Playsound();
//        playsound1.setGroup(playsoundGroup);
//        playsound1.setName("abc1");
//        Sound sound1 = new Sound();
//        sound1.setName("rada1");
//        sound1.setStream(false);
//        sound1.setLOLM(true);
//        playsound1.sounds.add(sound1);
//
//        playsoundGroup.playsounds.add(playsound1);
//
//        Playsound playsound2 = new Playsound();
//        playsound2.setName("abc2");
//        Sound sound2 = new Sound();
//        sound2.setName("rada2");
//        playsound2.sounds.add(sound2);
//        playsoundGroup.playsounds.add(playsound2);
//
//        editorData.playsounds.add(playsound1);
//        editorData.playsounds.add(playsound2);
//
//        Stage stage = controller.showEditGroup(playsoundGroup, myStage);
//
//        assertNotNull(stage);
//        assertEquals("JSON Sound Definitions Editor - Edit Group", stage.getTitle());
//    }

}
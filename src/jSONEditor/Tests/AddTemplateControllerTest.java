package jSONEditor.Tests;

import jSONEditor.Controller.AddTemplateController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddTemplateControllerTest {

    private Stage myStage;
    AddTemplateController controller;
    ActionEvent event;

    @Before
    public void start() throws Exception {
        event = new ActionEvent();

        controller = new AddTemplateController(); // the controller for add Template GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/addTemplate.fxml")));
        loader.setController(controller); // settings controller
        Parent root = loader.load();

        // set JavaFX stage details
        myStage = new Stage();
        myStage.setTitle("JSON Sound Definitions Editor - Add Template");
        myStage.setScene(new Scene(root, 1280, 720));
        myStage.show();
    }

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void testSetup() {
        assertNotNull(myStage);
        assertEquals("JSON Sound Definitions Editor - Add Template", myStage.getTitle());
    }

    @Test
    public void testCancelAddTemplate() {
        assertNull(controller.cancelAddTemplate(myStage));
    }

    @Test
    public void testSaveAddTemplate() {
        assertNull(controller.saveAddTemplate(myStage));
    }
}
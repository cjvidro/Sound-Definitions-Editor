package jSONEditor.Tests;

import jSONEditor.Controller.EditTemplateController;
import jSONEditor.Controller.EditorData;
import jSONEditor.Controller.Template;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditTemplateControllerTest {
    private Stage myStage;
    EditTemplateController controller;
    ActionEvent event;

    @Before
    public void start() throws Exception {
        event = new ActionEvent();

        EditorData editorData = EditorData.getInstance();
        Template template = new Template();
        template.setName("testName");
        template.setLOLMSetting(2);
        editorData.templates.add(template);


        controller = new EditTemplateController(); // the controller for edit template GUI
        controller.setTemplate(template);


        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/editTemplate.fxml")));
        loader.setController(controller); // Edit Template Controller
        Parent root = loader.load();

        // set JavaFX stage details
        myStage = new Stage();
        myStage.setTitle("JSON Sound Definitions Editor - Edit Template");
        myStage.setScene(new Scene(root, 1280, 720));
        myStage.show();
    }

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void testSetup() {
        assertNotNull(myStage);
        assertEquals("JSON Sound Definitions Editor - Edit Template", myStage.getTitle());
    }

    @Test
    public void testCancelEditTemplate() {
        assertNull(controller.cancelEditTemplate(myStage));
    }

//    @Test
//    public void testSaveEditTemplate() {
//        assertNull(controller.saveEditTemplate(myStage));
//    }
//
//    @Test
//    public void testDeleteTemplate() {
//        assertNull(controller.deleteTemplate(myStage));
//    }
}
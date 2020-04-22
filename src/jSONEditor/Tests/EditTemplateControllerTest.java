package jSONEditor.Tests;

import jSONEditor.Controller.Category;
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
    Template template;
    EditorData edit;

    @Before
    public void start() throws Exception {
        event = new ActionEvent();

        template = new Template();
        template.setName("thing");
        template.setLOLMSetting(0);

        edit = EditorData.getInstance();

        edit.templates.add(template);

        controller = new EditTemplateController(); // the controller for edit template GUI
        controller.setTemplate(template);

        edit.templates.remove(edit.templates.size() - 1);

        System.out.println("size = " + edit.templates.size());

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

    @Test
    public void testCreateAndValidateTemplate() {
        controller.editTemplate.setText("thing");
        controller.editCategory.setValue(Category.master);
        controller.editMax.setText("1.0");
        controller.editMin.setText("1.0");
        controller.editVolume.setText("1.0");
        controller.editPitch.setText("1.0");
        controller.editStream.setSelected(true);
        controller.editLoad.setValue("All true");

        assertTrue(controller.validateTemplate());

        controller.createTemplate();

        assertEquals(controller.editTemplate.getText(), edit.templates.get(edit.templates.size() - 1).getName());
        assertEquals((Category) controller.editCategory.getValue(), edit.templates.get(edit.templates.size() - 1).getDefaultCategory());
        assertEquals((Double) Double.parseDouble(controller.editMax.getText()), edit.templates.get(edit.templates.size() - 1).getDefaultMax());
        assertEquals((Double) Double.parseDouble(controller.editMin.getText()), edit.templates.get(edit.templates.size() - 1).getDefaultMin());
        assertEquals((Double) Double.parseDouble(controller.editVolume.getText()), edit.templates.get(edit.templates.size() - 1).getDefaultVolume());
        assertEquals((Double) Double.parseDouble(controller.editPitch.getText()), edit.templates.get(edit.templates.size() - 1).getDefaultPitch());
        assertEquals(controller.editStream.isSelected(), edit.templates.get(edit.templates.size() - 1).getDefaultStream());

        assertEquals(controller.editLoad.getValue(), "All true");

        edit.templates.remove(edit.templates.size() - 1);
    }
}
package jSONEditor.Tests;

import jSONEditor.Controller.ExportController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

public class ExportControllerTest {
	
    private Stage myStage;
    ExportController controller;
    ActionEvent event;

    @Before
    public void start() throws Exception{
    	event = new ActionEvent();

        controller = new ExportController(); // the controller for export GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/export.fxml")));
        loader.setController(controller); // Export Controller
        Parent root = loader.load();

        // set JavaFX stage details
        myStage = new Stage();
        myStage.setTitle("JSON Sound Definitions Editor - Export");
        myStage.setScene(new Scene(root, 1280, 720));
        myStage.show();
    }

    
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    
    @Test
    public void testSetup() {
        assertNotNull(myStage);
        assertEquals("JSON Sound Definitions Editor - Export", myStage.getTitle());
    }

    @Test
    public void testCloseExport() {
        assertNull(controller.closeExport(myStage));
    }

    @Test
    public void testExport() throws IOException {
    	controller.directoryName = "Test";
    	controller.filePath.setText(null);
    	assertEquals("No Location Selected", controller.export(myStage));
    	
    	controller.filePath.setText("");
    	assertEquals("No Location Selected", controller.export(myStage));
        
    	controller.filePath.setText("Test");
    	assertEquals("No Checked Box", controller.export(myStage));
    	
    	controller.fileCheck.setSelected(true);
    	assertEquals("Wrote File", controller.export(myStage));
    	
    	controller.fileCheck.setSelected(false);
    	controller.logCheck.setSelected(true);
    	assertEquals("Wrote Changelog", controller.export(myStage));
    	
    	controller.fileCheck.setSelected(true);
    	assertEquals("Wrote Both", controller.export(myStage));  
    }
    
    /*@Test
    public void testSelectLocation() {
    	assertEquals("Location Selected", controller.selectLocation(myStage));
    }*/
}
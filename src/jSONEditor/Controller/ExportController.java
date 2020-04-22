package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ExportController {
	
	@FXML public TextField filePath;
	@FXML public CheckBox fileCheck;
	@FXML public CheckBox logCheck;
	public File selectedDirectory = null;
	public String directoryName;
	
    /*****************************************************
     * Change Scenes
     *****************************************************/

    public Stage closeExport(Stage stage) {
        System.out.println("Close Export");

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private Stage closeExport(ActionEvent event) {
        // Uses the above method for testing purposes
        return closeExport((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    public String export(Stage stage) {
        System.out.println("Export");
        
        boolean text = false;
        boolean written = false;
        String testingString = null;
        
        if(filePath.getText() == null || filePath.getText().compareTo("") == 0) {
        	System.out.println("You have not selected a location!");
        	testingString = "No Location Selected";
        }
        else {
        	text = true;
        }
        
        if(!fileCheck.isSelected() && !logCheck.isSelected() && text) {
        	System.out.println("You have not checked a box!");
        	testingString = "No Checked Box";
        }
        
        if(fileCheck.isSelected() && text){
        	SoundIO.exportPlaysounds(selectedDirectory);
        	System.out.println("Saved JSON file to " + directoryName);
        	selectedDirectory = null;
        	testingString = "Wrote File";
        	written = true;
        }
        
        if(logCheck.isSelected() && text){
        	System.out.println("Saved Changelog to " + directoryName);
        	selectedDirectory = null;
        	testingString = "Wrote Changelog";
        	if(written)
        		testingString = "Wrote Both";
        	written = true;      	
        }
        
        if(written) {
        	closeExport(stage);
        	return testingString;
        }
        
        return testingString;
    }

    @FXML
    private void export(ActionEvent event) {
        // calls the above method for testing purposes
        export((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
    
    public String selectLocation(Stage stage) {     
    	System.out.println("Select Location");
    	
    	selectedDirectory = SoundIO.selectedDirectoryName();
    	if (selectedDirectory != null) {
            directoryName = selectedDirectory.getName();
            filePath.setText(selectedDirectory.getAbsolutePath());
            return "Location Selected";
        }

        return "";
    }
    
    @FXML
    private void selectLocation(ActionEvent event) {
        // calls the above method for testing purposes
        selectLocation((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

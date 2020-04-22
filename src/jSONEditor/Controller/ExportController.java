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
	
	@FXML public TextField directoryName;
	@FXML public CheckBox fileCheck;
	@FXML public CheckBox logCheck;
	public File selectedDirectory = null;
	
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

    public void export(Stage stage) {
        System.out.println("Export");
        
        boolean written = false;
        boolean text = false;
        
        if(directoryName.getText() == null || directoryName.getText().compareTo("") == 0)
        	System.out.println("You have not selected a location!");
        else
        	text = true;
        
        if(!fileCheck.isSelected() && !logCheck.isSelected() && text) {
        	System.out.println("You have not checked a box!");
        }
        
        if(fileCheck.isSelected() && text){
        	SoundIO.exportPlaysounds(selectedDirectory);
        	System.out.println("Saved JSON file to " + directoryName.getText());
        	selectedDirectory = null;
        	written = true;
        }
        
        if(logCheck.isSelected() && text){
        	System.out.println("Saved Changelog to " + directoryName.getText());
        	selectedDirectory = null;
        	written = true;
        }
        
        if(written)
        	closeExport(stage);
    }

    @FXML
    private void export(ActionEvent event) {
        // calls the above method for testing purposes
        export((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
    
    public void selectLocation(Stage stage) {     
    	System.out.println("Select Location");
    	
    	selectedDirectory = SoundIO.selectedDirectoryName();
    	
    	directoryName.setText(selectedDirectory.getAbsolutePath());
    }
    
    @FXML
    private void selectLocation(ActionEvent event) {
        // calls the above method for testing purposes
        selectLocation((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

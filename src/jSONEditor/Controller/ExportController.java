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
        
        boolean writeFile = false;
        boolean writeLog = false;
        
        if(fileCheck.isSelected() && (directoryName.getText() != null && directoryName.getText().compareTo("") != 0)){
        	writeFile = SoundIO.writePlaysounds();
        }
        
        if(logCheck.isSelected() && (directoryName.getText() != null && directoryName.getText().compareTo("") != 0)){
        	writeLog = true;
        }
          
        if(directoryName.getText() == null || directoryName.getText().compareTo("") == 0) {
        	System.out.println("You have not selected a location!");
        }
        else if(!fileCheck.isSelected() && !logCheck.isSelected()) {
        	System.out.println("You have not checked a box!");
        }
        else if(!writeFile && !writeLog) {
        	System.out.println("There is already a JSON file and Changelog in " + directoryName.getText());
        	directoryName.setText(null);
        }
        else if(writeFile && writeLog) {
        	System.out.println("Saved JSON file and Changelog to " + directoryName.getText());
        	directoryName.setText(null);
        	closeExport(stage);
        }
        else if(writeFile && !logCheck.isSelected()) {
        	System.out.println("Saved JSON file to " + directoryName.getText());
        	directoryName.setText(null);
        	closeExport(stage);
        }
        else if(writeFile && !writeLog) {
        	System.out.println("Saved JSON file to " + directoryName.getText());
        	System.out.println("There is already a Changelog in " + directoryName.getText());
        	directoryName.setText(null);
        	closeExport(stage);
        }
        else if(!fileCheck.isSelected() && writeLog) {
        	System.out.println("Saved Changelog to " + directoryName.getText());
        	directoryName.setText(null);
        	closeExport(stage);
        }
        else if(!writeFile && writeLog) {
        	System.out.println("Saved Changelog to " + directoryName.getText());
        	System.out.println("There is already a JSON file in " + directoryName.getText());
        	directoryName.setText(null);
        	closeExport(stage);
        }
    }

    @FXML
    private void export(ActionEvent event) {
        // calls the above method for testing purposes
        export((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
    
    public void selectLocation(Stage stage) {     
    	System.out.println("Select Location");
    	
    	directoryName.setText(SoundIO.selectedDirectoryName());
    }
    
    @FXML
    private void selectLocation(ActionEvent event) {
        // calls the above method for testing purposes
        selectLocation((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

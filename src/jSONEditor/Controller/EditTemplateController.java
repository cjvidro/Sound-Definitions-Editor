package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditTemplateController {
	EditorData instance = EditorData.getInstance();
	
	ProjectController p = new ProjectController();
	
	private static Template template;
	
	/*****************************************************
     * FXML fields
     *****************************************************/
	
	//Template name
    @FXML private TextField editTemplate;
	
	// Used for playsound defaults
    @FXML private ComboBox editCategory;
    @FXML private TextField editMin;
    @FXML private TextField editMax;
    
    //Used for sound defaults
    @FXML private CheckBox editStream;
    @FXML private TextField editVolume;
    @FXML private TextField editPitch;
    @FXML private ComboBox editLoad;
    
    @FXML
    public void initialize() {
    	// populate categories
        if (editCategory != null) {
            for (Category category : Category.values()) {
                editCategory.getItems().addAll(category);
            }
        }
        
        // populate Load on low memory setting
        if(editLoad != null) {
        	editLoad.getItems().add("All true");
        	editLoad.getItems().add("All false");
        	editLoad.getItems().add("First true, remaining false");
        	editLoad.getItems().add("Alternate true and false");
        }
        
        setAllValues();
    }
    
    public void setTemplate(Template template) {
    	this.template = template;
    }
    
    private void setAllValues() {
    	editTemplate.setText(template.getName());
    	
    	if(template.getDefaultCategory() != null) {
    		editCategory.setValue(template.getDefaultCategory());
    	}
    	
    	if(template.getDefaultMin() != null) {
    		editMin.setText(template.getDefaultMin().toString());
    	}
    	
    	if(template.getDefaultMax() != null) {
    		editMax.setText(template.getDefaultMax().toString());
    	}
    	
    	if(template.getDefaultStream() != null) {
    		editStream.setSelected(template.getDefaultStream());
    	}
    	
    	if(template.getDefaultVolume() != null) {
    		editVolume.setText(template.getDefaultVolume().toString());
    	}
    	
    	if(template.getDefaultPitch() != null) {
    		editPitch.setText(template.getDefaultPitch().toString());
    	}
    	
    	if(template.getLOLMSetting() == 0) {
    		editLoad.setValue("All true");
    	}
    	else if(template.getLOLMSetting() == 1) {
    		editLoad.setValue("All false");
    	}
    	else if(template.getLOLMSetting() == 2) {
    		editLoad.setValue("First true, remaining false");
    	}
    	else if(template.getLOLMSetting() == 3) {
    		editLoad.setValue("Alternate true and false");
    	}
    }
	
    /*****************************************************
     * Change Scenes
     *****************************************************/

    public Stage cancelEditTemplate(Stage stage) {
        System.out.println("Cancel Edit Template");

        /*
        INSERT CANCEL FUNCTIONALITY HERE
         */

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private void cancelEditTemplate(ActionEvent event){
        // uses the above method for testing purposes
        cancelEditTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    public Stage saveEditTemplate(Stage stage) {
        System.out.println("Save Edit Template");

        /*
        INSERT SAVE EDIT FUNCTIONALITY HERE
         */

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private void saveEditTemplate(ActionEvent event){
        // uses the above method for testing purposes
        saveEditTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    public Stage deleteTemplate(Stage stage) {
        System.out.println("Delete Template");
        
        instance.templates.remove(template);
        System.out.println("Successfully removed Template " + template.getName());
        
        p.populateTemplate(template.getName());
        
        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private void deleteTemplate(ActionEvent event) {
        // uses the above method for testing purposes
        deleteTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

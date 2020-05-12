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
	ModifyPlaysoundController m = new ModifyPlaysoundController();
	
	private static Template template;
	
	/*****************************************************
     * FXML fields
     *****************************************************/
	
	//Template name
    @FXML public TextField editTemplate;
	
	// Used for playsound defaults
    @FXML public ComboBox editCategory;
    
    //Used for sound defaults
    @FXML public CheckBox editStream;
    @FXML public TextField editVolume;
    @FXML public ComboBox editLoad;
    
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
    	
    	if(template.getDefaultStream() != null) {
    		editStream.setSelected(template.getDefaultStream());
    	}
    	
    	if(template.getDefaultVolume() != null) {
    		editVolume.setText(template.getDefaultVolume().toString());
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
    
    public void createTemplate() {
    	Template template = new Template();
    	
    	template.setName(editTemplate.getText());
    	template.setDefaultCategory((Category) editCategory.getValue());

    	template.setDefaultStream(editStream.isSelected());

    	if(!editVolume.getText().equals("")) {
    		template.setDefaultVolume(Double.parseDouble(editVolume.getText()));
    	}
    	else {
    		template.setDefaultVolume(null);
    	}

    	if (editLoad.getValue() != null) {
    		if (editLoad.getValue().equals("All true")) {
    			template.setLOLMSetting(0);
			} else if (editLoad.getValue().equals("All false")) {
				template.setLOLMSetting(1);
			} else if (editLoad.getValue().equals("First true, remaining false")) {
				template.setLOLMSetting(2);
			} else if (editLoad.getValue().equals("Alternate true and false")) {
				template.setLOLMSetting(3);
			}
		}
    		
    	instance.templates.add(template);
		instance.serializeTemplateSaves();
    }
    
    public boolean validateTemplate() {
    	if(editTemplate != null) {
			if(editTemplate.getText().equals("")) {
				System.out.println("Template name was empty!");
				return false;
			}
			else {
				for(Template temp: instance.templates) {
					if(temp.getName().equals(editTemplate.getText()) && !editTemplate.getText().equals(template.getName())) {
						System.out.println("Template names must be unique!");
						return false;
					}
				}
			}
    		
    		if(editVolume != null && !m.checkDouble(editVolume.getText())) {
				System.out.println("Volume was invalid!");
    			return false;
    		} else {
				String text = editVolume.getText();
				if (!text.equals("") && Double.parseDouble(editVolume.getText()) < 0) {
					System.out.println("Volume was negative!");
					return false;
				}
			}
    		
    		if(editLoad.getValue() == null) {
    			System.out.println("Please select a load on low memory setting!");
    			return false;
    		}
    		
    		return true;
    	}

		System.out.println("Scene not valid");
    	return false;
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

        boolean success = validateTemplate();
        
        if (success) {
        	instance.templates.remove(template);
        	createTemplate();
        	p.populateTemplate();
        	System.out.println("Successfully edited template " + template.getName());
            
			stage.close();
			stage = null;
        }
        
        return null;
    }

    @FXML
    private void saveEditTemplate(ActionEvent event){
        // uses the above method for testing purposes
        saveEditTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    public Stage deleteTemplate(Stage stage) {
        System.out.println("Delete Template");
        
        instance.templates.remove(template);
        instance.serializeTemplateSaves();
        
        p.populateTemplate();
        
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

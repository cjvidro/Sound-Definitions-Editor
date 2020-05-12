package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AddTemplateController {
	EditorData instance = EditorData.getInstance();
	
	ProjectController p = new ProjectController();
	ModifyPlaysoundController m = new ModifyPlaysoundController();
	/*****************************************************
     * FXML fields
     *****************************************************/
	
	//Template name
    @FXML private TextField templateName;
	
	// Used for playsound defaults
    @FXML private ComboBox catBox;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    
    //Used for sound defaults
    @FXML private CheckBox streamBox;
    @FXML private TextField volumeField;
    @FXML private TextField pitchField;
    @FXML private ComboBox LOLMBox;
	
    @FXML
    public void initialize() {
    	// populate categories
        if (catBox != null) {
            for (Category category : Category.values()) {
                catBox.getItems().addAll(category);
            }
        }
        
        // populate Load on low memory setting
        if(LOLMBox != null) {
        	LOLMBox.getItems().add("All true");
        	LOLMBox.getItems().add("All false");
        	LOLMBox.getItems().add("First true, remaining false");
        	LOLMBox.getItems().add("Alternate true and false");
        }
    }
    
    public void createTemplate() {
    	Template template = new Template();
    	
    	template.setName(templateName.getText());
    	template.setDefaultCategory((Category) catBox.getValue());

    	template.setDefaultStream(streamBox.isSelected());

    	if(!volumeField.getText().equals("")) {
    		template.setDefaultVolume(Double.parseDouble(volumeField.getText()));
    	}
    	else {
    		template.setDefaultVolume(null);
    	}

    	if (LOLMBox.getValue() != null) {
    		if (LOLMBox.getValue().equals("All true")) {
    			template.setLOLMSetting(0);
			} else if (LOLMBox.getValue().equals("All false")) {
				template.setLOLMSetting(1);
			} else if (LOLMBox.getValue().equals("First true, remaining false")) {
				template.setLOLMSetting(2);
			} else if (LOLMBox.getValue().equals("Alternate true and false")) {
				template.setLOLMSetting(3);
			}
		}
    		
    	instance.templates.add(template);
    	instance.serializeTemplateSaves();
    }
    
    protected boolean validateTemplate() {
    	if(templateName != null) {
			if(templateName.getText().equals("")) {
				System.out.println("Template name was empty!");
				return false;
			}
			else {
				for(Template template: instance.templates) {
					if(template.getName().equals(templateName.getText())) {
						System.out.println("Template names must be unique!");
						return false;
					}
				}
			}
    			
    		if(minField != null &&  !m.checkDouble(minField.getText())) {
    			System.out.println("Min distance was invalid!");
    			return false;
    		} else {
    			String text = minField.getText();
				if (!text.equals("") && Double.parseDouble(text) < 0) {
					System.out.println("Min distance was negative!");
					return false;
				}
			}
    		
    		if(maxField != null && !m.checkDouble(maxField.getText())) {
    			System.out.println("Max distance was invalid!");
    			return false;
    		} else {
				String text = maxField.getText();
				if (!text.equals("") && Double.parseDouble(maxField.getText()) < 0) {
					System.out.println("Max distance was negative!");
					return false;
				}
			}
    		
    		if(pitchField != null && !m.checkDouble(pitchField.getText())) {
				System.out.println("Pitch was invalid!");
    			return false;
    		} else {
				String text = pitchField.getText();
				if (!text.equals("") && Double.parseDouble(pitchField.getText()) < 0) {
					System.out.println("Pitch was negative!");
					return false;
				}
			}
    		
    		if(volumeField != null && !m.checkDouble(volumeField.getText())) {
				System.out.println("Volume was invalid!");
    			return false;
    		} else {
				String text = pitchField.getText();
				if (!text.equals("") && Double.parseDouble(volumeField.getText()) < 0) {
					System.out.println("Volume was negative!");
					return false;
				}
			}
    		
    		if(LOLMBox.getValue() == null) {
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
	
    public Stage cancelAddTemplate(Stage stage) {
        System.out.println("Cancel Add Template");

        stage.close();
        stage = null;
        return stage;
    }

    @FXML
    private void cancelAddTemplate(ActionEvent event) {
        // uses the above method for testing purposes
        cancelAddTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    public Stage saveAddTemplate(Stage stage) {
        System.out.println("Save Add Template");

        boolean success = validateTemplate();

        if (success) {
        	createTemplate();
        	
        	p.populateTemplate();
        	
            System.out.print("Added template ");
            System.out.println(instance.templates.get(instance.templates.size() - 1).getName());

			stage.close();
			stage = null;
        }

        return null;
    }

    @FXML
    protected void saveAddTemplate(ActionEvent event) {
        // uses the above method for testing purposes
        saveAddTemplate((Stage) ((Button) event.getSource()).getScene().getWindow());
    }
}

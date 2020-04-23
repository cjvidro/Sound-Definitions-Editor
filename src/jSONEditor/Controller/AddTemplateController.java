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
    	
    	instance.changelog = instance.changelog + "Creating Template - Date: " + java.time.LocalDate.now() + "  Time: " + java.time.LocalTime.now() + "\n\n";
    	
    	template.setName(templateName.getText());
    	template.setDefaultCategory((Category) catBox.getValue());
    	
    	//Changelog Data
    	instance.changelog = instance.changelog + "\tSet Template Name to " + template.getName() + "\n\n";
    	if(template.getDefaultCategory() != null)
    		instance.changelog = instance.changelog + "\tSet Template Default Category to " + template.getDefaultCategory() + "\n\n";

    	if(!minField.getText().equals("")) {
    		template.setDefaultMin(Double.parseDouble(minField.getText()));
    		instance.changelog = instance.changelog + "\tSet Template Default Minimum Distance to " + template.getDefaultMin() + "\n\n";
    	}
    	else {
    		template.setDefaultMin(null);
    	}

    	if(!maxField.getText().equals("")) {
    		template.setDefaultMax(Double.parseDouble(maxField.getText()));
    		instance.changelog = instance.changelog + "\tSet Template Default Maximum Distance to " + template.getDefaultMax() + "\n\n";
    	}
    	else {
    		template.setDefaultMax(null);
    	}

    	template.setDefaultStream(streamBox.isSelected());
    	instance.changelog = instance.changelog + "\t\tSet Template Default Stream to " + template.getDefaultStream() + "\n\n";

    	if(!volumeField.getText().equals("")) {
    		template.setDefaultVolume(Double.parseDouble(volumeField.getText()));
    		instance.changelog = instance.changelog + "\t\tSet Template Default Volume to " + template.getDefaultVolume() + "\n\n";
    	}
    	else {
    		template.setDefaultVolume(null);
    	}

    	if(!pitchField.getText().equals("")) {
    		template.setDefaultPitch(Double.parseDouble(pitchField.getText()));
    		instance.changelog = instance.changelog + "\t\tSet Template Default Pitch to " + template.getDefaultPitch() + "\n\n";
    	}
    	else {
    		template.setDefaultPitch(null);
    	}

    	if (LOLMBox.getValue() != null) {
    		if (LOLMBox.getValue().equals("All true")) {
    			template.setLOLMSetting(0);
    			instance.changelog = instance.changelog + "\t\tSet Template Default Pitch to All True\n\n";
			} else if (LOLMBox.getValue().equals("All false")) {
				template.setLOLMSetting(1);
				instance.changelog = instance.changelog + "\t\tSet Template Default Pitch to All False\n\n";
			} else if (LOLMBox.getValue().equals("First true, remaining false")) {
				template.setLOLMSetting(2);
				EditorData.getInstance().changelog = instance.changelog + "\t\tSet Template Default Pitch to First True, Remaining False\n\n";
			} else if (LOLMBox.getValue().equals("Alternate true and false")) {
				template.setLOLMSetting(3);
				instance.changelog = instance.changelog + "\t\tSet Template Default Pitch to Alternate True and False\n\n";
			}
		}
    		
    	instance.templates.add(template);
    	instance.serializeTemplateSaves();
    	
    	instance.changelog = instance.changelog + "New Template Saved - Date: " + java.time.LocalDate.now() + "  Time: " + java.time.LocalTime.now() + "\n\n";
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
    			
    		if(minField != null &&  !p.checkDouble(minField.getText())) {
    			System.out.println("Min distance was invalid!");
    			return false;
    		} else {
    			String text = minField.getText();
				if (!text.equals("") && Double.parseDouble(text) < 0) {
					System.out.println("Min distance was negative!");
					return false;
				}
			}
    		
    		if(maxField != null && !p.checkDouble(maxField.getText())) {
    			System.out.println("Max distance was invalid!");
    			return false;
    		} else {
				String text = maxField.getText();
				if (!text.equals("") && Double.parseDouble(maxField.getText()) < 0) {
					System.out.println("Max distance was negative!");
					return false;
				}
			}
    		
    		if(pitchField != null && !p.checkDouble(pitchField.getText())) {
				System.out.println("Pitch was invalid!");
    			return false;
    		} else {
				String text = pitchField.getText();
				if (!text.equals("") && Double.parseDouble(pitchField.getText()) < 0) {
					System.out.println("Pitch was negative!");
					return false;
				}
			}
    		
    		if(volumeField != null && !p.checkDouble(volumeField.getText())) {
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

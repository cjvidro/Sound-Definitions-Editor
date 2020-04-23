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
    @FXML public TextField editTemplate;
	
	// Used for playsound defaults
    @FXML public ComboBox editCategory;
    @FXML public TextField editMin;
    @FXML public TextField editMax;
    
    //Used for sound defaults
    @FXML public CheckBox editStream;
    @FXML public TextField editVolume;
    @FXML public TextField editPitch;
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
    
    public void createTemplate() {
    	Template template = new Template();
    	
    	instance.changelog = instance.changelog + "Editing Template - Date: " + java.time.LocalDate.now() + "  Time: " + java.time.LocalTime.now() + "\n\n";
    	
    	template.setName(editTemplate.getText());
    	template.setDefaultCategory((Category) editCategory.getValue());
    	
    	//Changelog Data
    	instance.changelog = instance.changelog + "\tSet Template Name to " + template.getName() + "\n\n";
    	if(template.getDefaultCategory() != null)
    		instance.changelog = instance.changelog + "\tSet Template Default Category to " + template.getDefaultCategory() + "\n\n";

    	if(!editMin.getText().equals("")) {
    		template.setDefaultMin(Double.parseDouble(editMin.getText()));
    		instance.changelog = instance.changelog + "\tSet Template Default Minimum Distance to " + template.getDefaultMin() + "\n\n";
    	}
    	else {
    		template.setDefaultMin(null);
    	}

    	if(!editMax.getText().equals("")) {
    		template.setDefaultMax(Double.parseDouble(editMax.getText()));
    		instance.changelog = instance.changelog + "\tSet Template Default Maximum Distance to " + template.getDefaultMax() + "\n\n";
    	}
    	else {
    		template.setDefaultMax(null);
    	}

    	template.setDefaultStream(editStream.isSelected());
    	instance.changelog = instance.changelog + "\t\tSet Template Default Stream to " + template.getDefaultStream() + "\n\n";

    	if(!editVolume.getText().equals("")) {
    		template.setDefaultVolume(Double.parseDouble(editVolume.getText()));
    		instance.changelog = instance.changelog + "\t\tSet Template Default Volume to " + template.getDefaultVolume() + "\n\n";
    	}
    	else {
    		template.setDefaultVolume(null);
    	}

    	if(!editPitch.getText().equals("")) {
    		template.setDefaultPitch(Double.parseDouble(editPitch.getText()));
    		instance.changelog = instance.changelog + "\t\tSet Template Default Pitch to " + template.getDefaultPitch() + "\n\n";
    	}
    	else {
    		template.setDefaultPitch(null);
    	}

    	if (editLoad.getValue() != null) {
    		if (editLoad.getValue().equals("All true")) {
    			template.setLOLMSetting(0);
    			instance.changelog = instance.changelog + "\t\tSet Template Default Pitch to All True\n\n";
			} else if (editLoad.getValue().equals("All false")) {
				template.setLOLMSetting(1);
				instance.changelog = instance.changelog + "\t\tSet Template Default Pitch to All False\n\n";
			} else if (editLoad.getValue().equals("First true, remaining false")) {
				template.setLOLMSetting(2);
				instance.changelog = instance.changelog + "\t\tSet Template Default Pitch to First True, Remaining False\n\n";
			} else if (editLoad.getValue().equals("Alternate true and false")) {
				template.setLOLMSetting(3);
				instance.changelog = instance.changelog + "\t\tSet Template Default Pitch to Alternate True and False\n\n";
			}
		}
    		
    	instance.changelog = instance.changelog + "Editing Template Finished - Date: " + java.time.LocalDate.now() + "  Time: " + java.time.LocalTime.now() + "\n\n";
    	
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
    			
    		if(editMin != null &&  !p.checkDouble(editMin.getText())) {
    			System.out.println("Min distance was invalid!");
    			return false;
    		} else {
    			String text = editMin.getText();
				if (!text.equals("") && Double.parseDouble(text) < 0) {
					System.out.println("Min distance was negative!");
					return false;
				}
			}
    		
    		if(editMax != null && !p.checkDouble(editMax.getText())) {
    			System.out.println("Max distance was invalid!");
    			return false;
    		} else {
				String text = editMax.getText();
				if (!text.equals("") && Double.parseDouble(editMax.getText()) < 0) {
					System.out.println("Max distance was negative!");
					return false;
				}
			}
    		
    		if(editPitch != null && !p.checkDouble(editPitch.getText())) {
				System.out.println("Pitch was invalid!");
    			return false;
    		} else {
				String text = editPitch.getText();
				if (!text.equals("") && Double.parseDouble(editPitch.getText()) < 0) {
					System.out.println("Pitch was negative!");
					return false;
				}
			}
    		
    		if(editPitch != null && !p.checkDouble(editPitch.getText())) {
				System.out.println("Volume was invalid!");
    			return false;
    		} else {
				String text = editPitch.getText();
				if (!text.equals("") && Double.parseDouble(editPitch.getText()) < 0) {
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
        
        instance.changelog = instance.changelog + "Deleted Template " + template.getName() + " - Date: " + java.time.LocalDate.now() + "  Time: " + java.time.LocalTime.now() + "\n\n";
        
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

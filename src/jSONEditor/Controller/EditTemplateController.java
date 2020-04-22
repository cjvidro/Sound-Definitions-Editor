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
    
    public void createTemplate() {
    	Template template = new Template();
    	
    	template.setName(editTemplate.getText());
    	template.setDefaultCategory((Category) editCategory.getValue());

    	if(!editMin.getText().equals("")) {
    		template.setDefaultMin(Double.parseDouble(editMin.getText()));
    	}
    	else {
    		template.setDefaultMin(null);
    	}

    	if(!editMax.getText().equals("")) {
    		template.setDefaultMax(Double.parseDouble(editMax.getText()));
    	}
    	else {
    		template.setDefaultMax(null);
    	}

    	template.setDefaultStream(editStream.isSelected());

    	if(!editVolume.getText().equals("")) {
    		template.setDefaultVolume(Double.parseDouble(editVolume.getText()));
    	}
    	else {
    		template.setDefaultVolume(null);
    	}

    	if(!editPitch.getText().equals("")) {
    		template.setDefaultPitch(Double.parseDouble(editPitch.getText()));
    	}
    	else {
    		template.setDefaultPitch(null);
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
    }
    
    protected boolean validateTemplate() {
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
        
        instance.templates.remove(template);
        
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

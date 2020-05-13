package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The controller class for the core GUI
 */
public class ProjectController {
    EditorData editorData = EditorData.getInstance();

    /*****************************************************
     * FXML fields
     *****************************************************/

    // used in the general project view - These are null to sub-code controller methods
    @FXML private VBox playsoundsVBox;
    @FXML private ScrollPane coreScrollPane;
    @FXML private Menu recentProjects;
    @FXML private Menu editTemplateDropdown;

    // references to be used
    public static Menu editTemplateDropdownReference = null;
    
    @FXML
    public void initialize() throws IOException {
        editorData.projectController = this;

        // populate recent projects
        if (recentProjects != null) {
            for (File file : EditorData.getInstance().saves) {
                if (file != null) {
                    MenuItem item = new MenuItem();
                    item.setText(file.getName());

                    item.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            SoundIO.loadSoundDefinitions(file);
                            try {
                                populatePlaysounds();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            EditorData.getInstance().currentDirectory = file;
                        }
                    });

                    recentProjects.getItems().add(item);
                }
            }
        }
        /*
        if(EditorData.getInstance().templates.size() == 0)
        {
        	Template template = new Template();
        	template.setName("Template 1");
        	template.setDefaultCategory(Category.master);
        	template.setDefaultMin(10.0);
        	template.setDefaultMax(10.0);
        	template.setDefaultStream(true);
        	template.setDefaultVolume(10.0);
        	template.setDefaultPitch(10.0);
        	template.setLOLMSetting(0);
        	
        	EditorData.getInstance().templates.add(template);
        	
        	Template template2 = new Template();
        	template2.setName("Template 2");
        	template2.setDefaultCategory(Category.master);
        	template2.setDefaultMin(10.0);
        	template2.setDefaultMax(10.0);
        	template2.setDefaultStream(true);
        	template2.setDefaultVolume(10.0);
        	template2.setDefaultPitch(10.0);
        	template2.setLOLMSetting(0);
        	
        	EditorData.getInstance().templates.add(template2);
        }
        */
        if(editTemplateDropdown != null) {
            editTemplateDropdownReference = editTemplateDropdown;

        	for(Template template: EditorData.getInstance().templates) {
        		MenuItem item = new MenuItem();
        		
        		item.setText(template.getName());
        		
        		item.setOnAction(new EventHandler<ActionEvent>() {
        			
                    @Override
                    public void handle(ActionEvent event) {
                    	EditTemplateController edit = new EditTemplateController();
            			edit.setTemplate(template);
                        try {
							showEditTemplate(event);
						} catch (IOException e) {
							e.printStackTrace();
						}
                    }
                });
        		
        		editTemplateDropdown.getItems().add(item);
        	}
        }
        
        // Populate the playsounds on the LHS
        populatePlaysounds();
    }


    /*****************************************************
     * Change Scenes and Button Functionality
     *****************************************************/
    public boolean quit() throws Exception {
        if (editorData.getInstance().currentDirectory != null) {
            saveProject();
        }

        class ExpectedQuitException extends Exception {
            public ExpectedQuitException(String message) {
                super(message);
            }
        }
        throw new ExpectedQuitException("User exited");
    }

    @FXML
    private void quit(ActionEvent event) throws Exception {
        System.out.println("Quit");
        try {
            quit();
        } catch (Exception e) {
            System.exit(0);
        }
    }

    @FXML
    public Stage showExport(ActionEvent event) throws IOException {
        System.out.println("Show Export");

        // load FXML and set the controller
        ExportController controller = new ExportController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/export.fxml")));
        loader.setController(controller); // export controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage exportWindow = new Stage();
        exportWindow.setTitle("JSON Sound Definitions Editor - Export");
        exportWindow.setScene(new Scene(root));
        exportWindow.initModality(Modality.APPLICATION_MODAL);
        exportWindow.setResizable(false);
        exportWindow.show();

        return exportWindow;
    }

    @FXML
    public Stage showSettings(ActionEvent event) throws IOException {
        System.out.println("Show Settings");

        // load FXML and set the controller
        SettingsController controller = new SettingsController(); // the controller for the settings GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/settings.fxml")));
        loader.setController(controller); // export controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage settingsWindow = new Stage();
        settingsWindow.setTitle("JSON Sound Definitions Editor - Settings");
        settingsWindow.setScene(new Scene(root));
        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.setResizable(false);
        settingsWindow.show();

        return settingsWindow;
    }

    @FXML
    public Stage showAddTemplate(ActionEvent event) throws IOException {
        System.out.println("Show Add Template");

        // load FXML and set the controller
        AddTemplateController controller = new AddTemplateController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/addTemplate.fxml")));
        loader.setController(controller); // addTemplate controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage addTemplateWindow = new Stage();
        addTemplateWindow.setTitle("JSON Sound Definitions Editor - Add Template");
        addTemplateWindow.setScene(new Scene(root));
        addTemplateWindow.initModality(Modality.APPLICATION_MODAL);
        addTemplateWindow.setResizable(false);
        addTemplateWindow.show();

        return addTemplateWindow;
    }

    @FXML
    public Stage showEditTemplate(ActionEvent event) throws IOException {
        System.out.println("Show Edit Template");

        // load FXML and set the controller
        EditTemplateController controller = new EditTemplateController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/editTemplate.fxml")));
        loader.setController(controller); // addTemplate controller
        Parent root = loader.load();

        // set JavaFX stage details
        Stage editTemplateWindow = new Stage();
        editTemplateWindow.setTitle("JSON Sound Definitions Editor - Edit Template");
        editTemplateWindow.setScene(new Scene(root));
        editTemplateWindow.initModality(Modality.APPLICATION_MODAL);
        editTemplateWindow.setResizable(false);
        editTemplateWindow.show();

        return editTemplateWindow;
    }

    @FXML
    /**
     * Displays the add playsound window
     */
    public Stage showAddPlaysound() throws IOException {
        System.out.println("Show Add Playsound");

        // load FXML and set the controller
        ModifyPlaysoundController controller = new ModifyPlaysoundController();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/addPlaysound.fxml")));
        loader.setController(controller);
        Parent root = loader.load();

        // set JavaFX stage details
        Stage addPlaysoundWindow = new Stage();
        addPlaysoundWindow.setTitle("JSON Sound Definitions Editor - Add Playsound");
        addPlaysoundWindow.setScene(new Scene(root));
        addPlaysoundWindow.initModality(Modality.APPLICATION_MODAL);
        addPlaysoundWindow.setResizable(false);
        addPlaysoundWindow.show();

        return addPlaysoundWindow;
    }

    @FXML
    /**
     * Displays the edit playsound window
     */
    public Stage showEditPlaysound(Playsound playsound) throws IOException {
        System.out.println("Show Edit Playsound");

        // load FXML and set the controller
        ModifyPlaysoundController controller = new ModifyPlaysoundController();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/editPlaysound.fxml")));
        loader.setController(controller);
        Parent root = loader.load();

        // set JavaFX stage details
        Stage editPlaysoundWindow = new Stage();
        editPlaysoundWindow.setTitle("JSON Sound Definitions Editor - Edit Playsound");
        editPlaysoundWindow.setScene(new Scene(root));
        editPlaysoundWindow.initModality(Modality.APPLICATION_MODAL);
        editPlaysoundWindow.setResizable(false);
        editPlaysoundWindow.show();

        controller.populatePlaysound(playsound); // populate with info

        return editPlaysoundWindow;
    }


    protected void populatePlaysounds() throws IOException {
        String expandedPaneName = "";
        if (editorData.expandedPane != null) {
            expandedPaneName = editorData.expandedPane.getText();
        }

        if (playsoundsVBox != null) {
            // remove any current mention of playsounds
            playsoundsVBox.getChildren().clear();

            // Create new nodes to populate
            Accordion accordion = new Accordion();

            TitledPane playsounds = new TitledPane();
            playsounds.setText("Playsounds");
            playsounds.setFont(new Font(15));
            accordion.getPanes().add(playsounds);

            if (expandedPaneName.equals("Playsounds")) {
                playsounds.setExpanded(true);
                accordion.setExpandedPane(playsounds);
            }

            VBox box = new VBox();
            playsounds.setContent(box);

            playsoundsVBox.getChildren().add(accordion);

            HashMap<String, VBox> groupMap = new HashMap<>();

            // load each of the playsounds
            for (Playsound playsound : editorData.playsounds) {
                if (playsound.getGroup() == null) {
                    // single playsound case
                    Label label = new Label(playsound.getName());
                    label.setFont(new Font(15));
                    box.getChildren().add(label);

                    label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                                // double clicked
                                try {
                                    showEditPlaysound(playsound);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    // multiple playsounds case

                    String groupName = playsound.getGroup().getName();

                    VBox group;
                    // check if there is already a group with this name
                    if (groupMap.get(groupName) == null) {
                        // create a new group
                        Accordion groupAccordian = new Accordion();
                        TitledPane groupPane = new TitledPane();
                        groupPane.setText(groupName);
                        groupPane.setFont(new Font(15));
                        groupAccordian.getPanes().add(groupPane);
                        VBox groupBox = new VBox();
                        groupPane.setContent(groupBox);

                        groupMap.put(groupName, groupBox);
                        group = groupBox;
                        box.getChildren().add(groupAccordian);

                        groupPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if(event.getButton().equals(MouseButton.SECONDARY) && event.getClickCount() == 1) {
                                    // double clicked
//                                    showEditPlaysound(playsound.getGroup(), (Stage) groupPane.getScene().getWindow());  //************* WILL HAVE TO REVIST
                                }
                            }
                        });
                    } else {
                        group = groupMap.get(groupName);
                    }

                    // add the playsound
                    Label label = new Label(playsound.getName());
                    label.setFont(new Font(15));
                    group.getChildren().add(label);

                    label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                                // double clicked
                                try {
                                    showEditPlaysound(playsound);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        }
    }
    
    @FXML
    protected void populateTemplate() {
    	editTemplateDropdownReference.getItems().clear();
    	
    	for(Template template: EditorData.getInstance().templates) {
    		MenuItem item = new MenuItem();
    		item.setText(template.getName());

    		item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	EditTemplateController edit = new EditTemplateController();
        			edit.setTemplate(template);
                    try {
						showEditTemplate(event);
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            });
    		
    		editTemplateDropdownReference.getItems().add(item);
    	}
    }

    @FXML
    public boolean saveProject() {
        return SoundIO.saveProject();
    }

    @FXML
    private void saveProjectAs() {
        SoundIO.saveProjectAs();
    }

    @FXML
    protected void importSoundDefinitions() throws IOException {
        SoundIO.importSoundDefinitions();
        populatePlaysounds();
    }

    @FXML
    private void newProject() throws IOException {
        editorData.currentDirectory = null;
        editorData.playsounds = new ArrayList<>();
        populatePlaysounds();

        System.out.println("New Project!");
    }

    @FXML
    private void helpButton() {
        System.out.println("Pressed help button");

//        try {
//            Desktop.getDesktop().browse(new URL("https://mhg.scottcorbat.com/help").toURI());   // Need to replace w/ a new URL
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }
}

package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The controller class for the core GUI
 */
public class ProjectController {
    EditorData editorData = EditorData.getInstance();
    private static final ToggleGroup folders = new ToggleGroup();

    /*****************************************************
     * FXML fields
     *****************************************************/

    // used in the general project view - These are null to sub-code controller methods
    @FXML private VBox playsoundsVBox;
    @FXML private ScrollPane coreScrollPane;
    @FXML private Menu recentProjects;
    @FXML private Menu editTemplateDropdown;
    @FXML private VBox folderDisplay;
    @FXML private StackPane addPlaysoundDisplay;

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
                            populateTemplate();
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

        	for(Template template: editorData.templates) {
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

        		// Make the toggle between folder and add view toggle
        		folders.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
        		    if (newVal == null) {
        		        if (folderDisplay.isVisible()) {
        		            toggleView();
                        }
                    }
                });
        	}
        }
        
        // Populate the folders on the LHS
        populateFolders();

        // Center addPlaysoundDisplay button
        addPlaysoundDisplay.minWidthProperty().bind(coreScrollPane.widthProperty());
        addPlaysoundDisplay.minHeightProperty().bind(coreScrollPane.heightProperty());

        folderDisplay.setVisible(false);
        addPlaysoundDisplay.setVisible(true);
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


    /**
     * Populates the folders list
     */
    private void populateFolders() {
        String currentToggle = getCurrentFolder();

        playsoundsVBox.getChildren().clear();

        Set<String> folderNames = editorData.folders.keySet();
        for (String s : folderNames) {
            ToggleButton folder = new ToggleButton();
            folder.setToggleGroup(folders);
            folder.setText(s);
            folder.setFont(new Font(16));
            folder.maxWidthProperty().bind(playsoundsVBox.widthProperty());
            folder.prefWidthProperty().bind(playsoundsVBox.widthProperty());
            folder.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showFolder(((ToggleButton)event.getSource()).getText());
                }
            });

            playsoundsVBox.getChildren().add(folder);

            // set it to be selected if it was selected before
            if (currentToggle != null && currentToggle.equals(s)) {
                folder.setSelected(true);
                currentToggle = null;
            }
        }
    }

    /**
     * Populates the view project with the contents of a folder
     * @param folderName
     */
    private void showFolder(String folderName) {
        if (editorData.folders.get(folderName) == null || folders.getSelectedToggle() == null) {
            return;
        }

        // show the folder display if not shown already
        if (!folderDisplay.isVisible()) {
            toggleView();
        }

        System.out.println("Showing folder " + folderName);

        folderDisplay.getChildren().clear();

        PlaysoundGroup folder = editorData.folders.get(folderName);

        for (Playsound p : folder.playsounds) {
            // load FXML and set the controller
            DisplayController playsoundController = new DisplayController(); // the controller for the view project GUI
            FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/playsoundDisplay.fxml")));
            loader.setController(playsoundController);
            Node playsound = null;
            try {
                playsound = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            playsoundController.playsoundNode.setText(p.getName());
            playsoundController.deletePlaysoundButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    editorData.playsounds.remove(p);
                    p.getGroup().playsounds.remove(p);
                    refresh();
                }
            });

            for (Sound s : p.sounds) {
                DisplayController soundController = new DisplayController();
                FXMLLoader loader2 = new FXMLLoader((getClass().getResource("../../view/soundDisplay.fxml")));
                loader2.setController(soundController);
                Node sound = null;
                try {
                    sound = loader2.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                soundController.soundNodeName.setText(s.getName());
                soundController.soundProperties.setText(getSoundDescription(s));

                // delete functionality
                soundController.deleteSoundButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        p.removeSound(s);
                        refresh();
                    }
                });

                playsoundController.soundBoxContainer.getChildren().add(sound);
            }


            folderDisplay.getChildren().add(playsound);
        }
    }

    /**
     * @return The string name of the currently selected folder
     */
    private String getCurrentFolder() {
        String curFolder;
        try {
            curFolder = getCurrentToggle().getText();
        } catch (NullPointerException e) {
            return null;
        }

        return curFolder;
    }

    /**
     *
     */
    private ToggleButton getCurrentToggle() {
        ToggleButton curButton;
        try {
            curButton = ((ToggleButton) folders.getSelectedToggle());
        } catch (NullPointerException e) {
            return null;
        }

        return curButton;
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
        populateFolders();
    }

    @FXML
    private void newProject() throws IOException {
        editorData.currentDirectory = null;
        editorData.playsounds = new ArrayList<>();
        populateFolders();

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

    @FXML
    /**
     * Shows the new folder popup
     */
    private void newFolder() throws IOException{
        System.out.println("Show New Folder");

        // load FXML and set the controller
        FolderController controller = new FolderController();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/addFolder.fxml")));
        loader.setController(controller);
        Parent root = loader.load();

        // set JavaFX stage details
        Stage addFolderWindow = new Stage();
        addFolderWindow.setTitle("JSON Sound Definitions Editor - Add Folder");
        addFolderWindow.setScene(new Scene(root));
        addFolderWindow.initModality(Modality.APPLICATION_MODAL);
        addFolderWindow.setResizable(false);
        addFolderWindow.show();
    }

    /**
     * Refreshes the folders for project view
     */
    public void refresh() {
        populateFolders();
        showFolder(getCurrentFolder());
    }

    /**
     * Creates a string with the description of a sound
     * @param sound
     * @return A string of the description of the properties of the sound
     */
    private String getSoundDescription(Sound sound) {
        StringBuilder stringBuilder = new StringBuilder();

        if (sound.getStream() != null) {
            stringBuilder.append("stream: " + sound.getStream().toString().toLowerCase() + "; ");
        }
        if (sound.getLOLM() != null) {
            stringBuilder.append("load_on_low_memory: " + sound.getLOLM().toString().toLowerCase() + "; ");
        }
        if (sound.getVolume() != null) {
            stringBuilder.append("volume: " + sound.getVolume() + "; ");
        }

        return stringBuilder.toString();
    }

    /**
     * Toggles between the folder view and the addPlaysound button view
     */
    private void toggleView() {
        addPlaysoundDisplay.setVisible(!addPlaysoundDisplay.isVisible());
        folderDisplay.setVisible(!folderDisplay.isVisible());
    }
}

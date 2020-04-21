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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * The controller class for the core GUI
 */
public class ProjectController {
    EditorData editorData = EditorData.getInstance();

    /*****************************************************
     * FXML fields
     *****************************************************/

    // Used for adding / editing playsounds
    @FXML public TextField nameField;
    @FXML public TextField incrementBox;
    @FXML public ComboBox categoryBox;
    @FXML public ComboBox templateBox;
    @FXML public TextField minDistanceField;
    @FXML public TextField maxDistanceField;

    // used in the general project view - These are null to sub-code controller methods
    @FXML public VBox soundsVBox;
    @FXML private VBox playsoundsVBox;
    @FXML private ScrollPane coreScrollPane;
    @FXML private TextField referenceName;
    @FXML private TextField referenceGroup;
    @FXML private Menu recentProjects;

    // references to be used
    private static VBox soundsVBoxReference = null;
    private static VBox playsoundsVBoxReference = null;
    private static ScrollPane coreScrollpaneReference = null;
    protected static TextField playsoundName = null;
    protected static TextField playsoundGroup = null;
    public static ProjectController playsoundControllerReference = null; // used for testing
    public static ProjectController soundControllerReference = null; // used for testing
    public static ProjectController editPlaysoundControllerReference = null; // used for testing

    @FXML
    public void initialize() {

        // populate categories
        if (categoryBox != null) {
            for (Category category : Category.values()) {
                categoryBox.getItems().addAll(category);
            }
        }

        // populate templates
        if (templateBox != null) {
            for (Template template : editorData.templates) {
                templateBox.getItems().addAll(template.getName());
            }
        }

        // Create references if needed
        if (soundsVBoxReference == null) {
            soundsVBoxReference = soundsVBox;
        }

        if (playsoundsVBoxReference == null) {
            playsoundsVBoxReference = playsoundsVBox;
        }

        if (coreScrollpaneReference == null) {
            coreScrollpaneReference = coreScrollPane;
        }

        if (playsoundName == null || (referenceName != null && referenceName != playsoundName)) {
            playsoundName = referenceName;
        }

        if (playsoundGroup == null || (referenceGroup != null && referenceGroup != playsoundGroup)) {
            playsoundGroup = referenceGroup;
        }

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
                            populatePlaysounds();
                            EditorData.getInstance().currentDirectory = file;
                        }
                    });

                    recentProjects.getItems().add(item);
                }
            }
        }

        // Populate the playsounds on the LHS
        populatePlaysounds();
    }


    /*****************************************************
     * Change Scenes and Button Functionality
     *****************************************************/
    public boolean quit() throws Exception {
        saveProject();

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

        /*
        INSERT Prompt Save FUNCTIONALITY HERE
         */

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
        exportWindow.setScene(new Scene(root, 350, 190));
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
        settingsWindow.setScene(new Scene(root, 400, 200));
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

    public Stage showAddPlaysound(Stage addPlaysoundWindow) throws IOException {
        System.out.println("Show Add Playsound");

        // load FXML and set the controller
        ProjectController controller = new ProjectController();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/addPlaysound.fxml")));
        loader.setController(controller); // addPlaysound/viewProject controller
        Node addPlaysound = loader.load();

        coreScrollpaneReference.setContent(addPlaysound);

        // set JavaFX stage details
        addPlaysoundWindow.setTitle("JSON Sound Definitions Editor - Add Playsound");

        playsoundControllerReference = controller;

        return addPlaysoundWindow;
    }

    @FXML
    private void showAddPlaysound(ActionEvent event) throws IOException {
        // Calls the above helper method for testing purposes
        showAddPlaysound((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    /**
     * @param overlyingBox - The overlying HBox for a sound
     * @return an array of HBoxes corresponding to a sounds directory, stream, volume, pitch, and LOLM
     */
    public HBox[] getSoundHBoxes(HBox overlyingBox) {
        HBox[] soundBoxes = new HBox[5];

        if (overlyingBox != null) {
            VBox containingBox = (VBox) overlyingBox.getChildren().get(1);

            soundBoxes[0] = (HBox) containingBox.getChildren().get(0); // directory box
            soundBoxes[1] = (HBox) containingBox.getChildren().get(1); // stream box
            soundBoxes[2] = (HBox) containingBox.getChildren().get(2); // volume box
            soundBoxes[3] = (HBox) containingBox.getChildren().get(3); // pitch box
            soundBoxes[4] = (HBox) containingBox.getChildren().get(4); // LOLM box
        }

        return soundBoxes;
    }

    public boolean checkDirectory(String string) {
        if (string.equals("")) {
            System.out.println("Directory is invalid");
            return false;
        }

        return true;
    }

    /**
     * Checks if the sounds are valid
     * @return true if valid, false if invalid
     */
    public boolean validateSounds() {
        if (soundsVBox != null) {
            for (Node soundNode : soundsVBox.getChildren()) {
                HBox overlyingBox = (HBox) soundNode;

                // check if this is the extra "box" for adding more sounds
                if (overlyingBox != null) {
                    try {
                        overlyingBox.getChildren().get(1);
                    } catch (IndexOutOfBoundsException e) {
                        return true;
                    }
                }

                HBox[] soundBoxes = getSoundHBoxes(overlyingBox);

                String directory = ((TextField) soundBoxes[0].getChildren().get(2)).getText();

                if (!checkDirectory(directory)) return false;

                String volumeText =((TextField) soundBoxes[2].getChildren().get(2)).getText();
                String pitchText = ((TextField) soundBoxes[3].getChildren().get(2)).getText();

                // check if volume or pitch are invalid
                if (!checkDouble(volumeText) || !checkDouble(pitchText)) return false;
            }
        }
        return false;
    }

    public boolean checkIfNameExists(String string) {
        if (string == null || string.equals("")) {
            System.out.println("Name is blank!");
            return false;
        }

        for (Playsound playsound : editorData.playsounds) {
            if (string.equals(playsound.getName())) {
                System.out.println("A playsound named " + string + " already exists!");
                return false;
            }
        }
        return true;
    }

    public boolean checkIncrement(String increment) {
        if (increment == null || increment.equals("")) {
            System.out.println("Increment is blank!");
            return  false;
        }

        try {
            Integer.parseInt(increment);
        }
        catch (NumberFormatException e) {
            System.out.println("Could not parse increment");
            return false;
        }

        return true;
    }

    public boolean checkDouble(String string) {
        if (string == null) {
            return false;
        }

        if (string.equals("")) {
            return true;
        }

        try {
            Double num = Double.parseDouble(string);
            if (num < 0) {
                System.out.println("Double was negative!");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Could not parse double");
            return false;
        }

        return true;
    }

    /**
     * Checks if a playsound is valid
     * @return true if valid, false if invalid
     */
    public boolean validatePlaysound() {
        if (nameField != null) {
            // check that name exists and is valid
            if (!checkIfNameExists(nameField.getText())) return false;

            // check if increment is an integer
            if (incrementBox != null && !checkIncrement(incrementBox.getText())) return false;

            // check min and max distance
            if (minDistanceField != null && maxDistanceField != null && (!checkDouble(minDistanceField.getText())
                    || !checkDouble(maxDistanceField.getText()))) return false;

            return validateSounds();
        }
        return false;
    }

    public int validateIncrement(String incrementTemp) {
        int increment = 1;

        // Check if to use default
        try {
            if (Integer.parseInt(incrementTemp) < 1) {
                System.out.println("Increment invalid - using default (1 Increment)");
            } else {
                increment = Integer.parseInt(incrementTemp);
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Could not parse increment as an integer. . .");
            try {
                increment = (int) Double.parseDouble(incrementTemp);
                System.out.println("Successfully parsed increment as a double!");
            } catch (NumberFormatException f) {
                System.out.println("Tried parsing increment as a double and failed - using default (1 increment)");
            }
        }

        return increment;
    }

    public boolean validateIncrementNames(String name, int increment) {
        for (int i = 1; i <= increment; i++) {
            for (Playsound playsound : editorData.playsounds) {
                if ((name + i).equals(playsound.getName())) {
                    System.out.println("A playsound named " + name + i + " already exists!");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean createPlaysound() {
        boolean valid = validatePlaysound();

        int increment = 1;
        if (valid) {
            increment = validateIncrement(incrementBox.getText());
            valid = validateIncrementNames(nameField.getText(), increment);
        }

        if (valid) {
             PlaysoundGroup group = new PlaysoundGroup();
             group.setName(nameField.getText());

             for (int i = 1; i <= increment; i++) {

                Playsound playsound = new Playsound();

                if (increment == 1) {
                    // single playsound
                    playsound.setName(nameField.getText());
                } else {
                    // multiple playsounds
                    playsound.setName(nameField.getText() + i);
                    playsound.setGroup(group);
                    group.playsounds.add(playsound);
                }
                playsound.setCategory((Category) categoryBox.getValue());

                /*
                Playsound Details
                */
                // check if min distance is empty
                if (!minDistanceField.getText().equals("")) {
                    playsound.setMin(Double.parseDouble(minDistanceField.getText()));
                }

                // check if max distance is empty
                if (!maxDistanceField.getText().equals("")) {
                    playsound.setMax(Double.parseDouble(maxDistanceField.getText()));
                }
            /*
            Sound details
             */
                // Add all of the individual sounds
                for (Node soundNode : soundsVBox.getChildren()) {
                    HBox overlyingBox = (HBox) soundNode;

                    // check if this is the extra "box" for adding more sounds
                    if (overlyingBox != null) {
                        try {
                            overlyingBox.getChildren().get(1);
                        } catch (IndexOutOfBoundsException e) {
                            break;
                        }
                    }

                    HBox[] soundBoxes = getSoundHBoxes(overlyingBox);

                    String directory;
                    if (increment == 1) {
                        directory = ((TextField) soundBoxes[0].getChildren().get(2)).getText();
                    } else {
                        directory = ((TextField) soundBoxes[0].getChildren().get(2)).getText() + i;
                    }
                    Boolean stream = ((CheckBox) soundBoxes[1].getChildren().get(2)).isSelected();

                    // check if volume, pitch, lolm are empty
                    Double volume = null;
                    Double pitch = null;

                    if (!((TextField) soundBoxes[2].getChildren().get(2)).getText().equals("")) {
                        volume = Double.parseDouble(((TextField) soundBoxes[2].getChildren().get(2)).getText());
                    }

                    if (!((TextField) soundBoxes[3].getChildren().get(2)).getText().equals("")) {
                        pitch = Double.parseDouble(((TextField) soundBoxes[3].getChildren().get(2)).getText());
                    }

                    Boolean lolm = ((CheckBox) soundBoxes[4].getChildren().get(2)).isSelected();

                    playsound.addSound(directory, stream, pitch, volume, lolm);
                }

                // Add the playsound to editorData instance
                editorData.playsounds.add(playsound);
            }
            
            return true;
        }

        return false;
    }

    private VBox addSound(ProjectController controller) throws IOException {
        int sounds = controller.soundsVBox.getChildren().size();

        // load FXML and set the controller
        ProjectController myController = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/sound.fxml")));
        loader.setController(myController); // view project controller
        Node sound = loader.load();

        controller.soundsVBox.getChildren().add(sounds - 1, sound);

        System.out.println("Add " + controller.soundsVBox.getChildren().size());

        soundControllerReference = myController;

        return (VBox) ((HBox) sound).getChildren().get(1);
    }

    @FXML private void updateTemplate() {
        if (templateBox != null) {
            String name = (String)templateBox.getValue();
            Template template = null;
            for (Template templateRef : editorData.templates) {
                if (templateRef.getName().equals(name)) {
                    template = templateRef;
                    break;
                }
            }

            // update values - playsound
            if (categoryBox != null && template.getDefaultCategory() != null) {
                categoryBox.setValue(template.getDefaultCategory());
            }
            if (minDistanceField != null && template.getDefaultMin() != null) {
                minDistanceField.setText(template.getDefaultMin() + "");
            }
            if (maxDistanceField != null && template.getDefaultMax() != null) {
                maxDistanceField.setText(template.getDefaultMax() + "");
            }

            // update values - sound
            for (Node soundNode : soundsVBox.getChildren()) {
                HBox overlyingBox = (HBox) soundNode;

                // check if this is the extra "box" for adding more sounds
                if (overlyingBox != null) {
                    try {
                        overlyingBox.getChildren().get(1);
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                }

                HBox[] soundBoxes = getSoundHBoxes(overlyingBox);

                ((CheckBox) soundBoxes[1].getChildren().get(2)).setSelected(template.getDefaultStream()); // stream

                if (template.getDefaultVolume() != null) {
                    ((TextField) soundBoxes[2].getChildren().get(2)).setText(template.getDefaultVolume() + ""); // volume
                }

                if (template.getDefaultPitch() != null) {
                    ((TextField) soundBoxes[3].getChildren().get(2)).setText(template.getDefaultPitch() + ""); //pitch
                }

                ((CheckBox) soundBoxes[4].getChildren().get(2)).setSelected(template.detectLOLMSetting()); // LOLM
            }
        }
    }

    @FXML
    private void addSound() throws IOException{
        int sounds = soundsVBox.getChildren().size();

        // load FXML and set the controller
        ProjectController controller = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/sound.fxml")));
        loader.setController(controller); // view project controller
        Node sound = loader.load();

        soundsVBox.getChildren().add(sounds - 1, sound);

        System.out.println("Add " + soundsVBox.getChildren().size());
    }

    @FXML
    private void removeSound(ActionEvent event) {
        Button button = (Button) event.getSource();
        HBox sound = (HBox) button.getParent();

        soundsVBoxReference.getChildren().remove(sound);

        System.out.println("Removed sound");
    }

    protected Stage saveAddPlaysound(Stage viewProjectWindow) throws IOException {
        System.out.println("Save Add Playsound");

        boolean success = createPlaysound();

        if (success) {
            System.out.print("Added playsound ");
            System.out.println(editorData.playsounds.get(editorData.playsounds.size() - 1).getName());

            return showViewProject(viewProjectWindow);
        }

        System.out.println("Invalid playsound");
        return null;
    }

    @FXML
    private void saveAddPlaysound(ActionEvent event) throws IOException {
        // Calls the above helper method for testing purposes
        saveAddPlaysound((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    public Stage showViewProject(Stage viewProjectWindow) throws IOException {
        // save the expanded pane
        editorData.expandedPane = ((Accordion)playsoundsVBoxReference.getChildren().get(0)).getExpandedPane();

        // reset references
        soundsVBoxReference = null;
        playsoundsVBoxReference = null;
        coreScrollpaneReference = null;

        // load FXML and set the controller
        ProjectController controller = new ProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/viewProject.fxml")));
        loader.setController(controller); // addPlaysound/viewProject controller
        Parent root = loader.load();

        double width =  viewProjectWindow.getScene().getWidth();
        double height = viewProjectWindow.getScene().getHeight();

        // set JavaFX stage details
        viewProjectWindow.setScene(new Scene(root, width, height));
        viewProjectWindow.setTitle("JSON Sound Definitions Editor");

        populatePlaysounds();

        return viewProjectWindow;
    }

    @FXML
    private void cancelAddPlaysound(ActionEvent event) throws IOException {
        // Calls the above helper method for testing purposes
        showViewProject((Stage) ((Button) event.getSource()).getScene().getWindow());
    }

    protected void populatePlaysounds() {
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
                                showEditSingle(playsound, (Stage) label.getScene().getWindow());
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
                                    showEditGroup(playsound.getGroup(), (Stage) groupPane.getScene().getWindow());
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
                                showEditSingle(playsound, (Stage) label.getScene().getWindow());
                            }
                        }
                    });
                }
            }
        }
    }

    public Stage showEditSingle(Playsound playsound, Stage editPlaysoundWindow){
        System.out.println("Edit " + playsound.getName());

        // load FXML and set the controller
        ProjectController controller = new ProjectController();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/editPlaysound.fxml")));
        loader.setController(controller); // addPlaysound/viewProject controller
        Node addPlaysound = null;
        try {
            addPlaysound = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        editPlaysoundControllerReference = controller;
        soundsVBoxReference = controller.soundsVBox;

        coreScrollpaneReference.setContent(addPlaysound);

        // set JavaFX stage details
        editPlaysoundWindow.setTitle("JSON Sound Definitions Editor - Edit Playsound");

        // Set playsound details
        controller.nameField.setText(playsound.getName());
        if (playsound.getMin() != null) {
        controller.minDistanceField.setText(playsound.getMin() + "");
        }
        if (playsound.getMax() != null) {
            controller.maxDistanceField.setText(playsound.getMax() + "");
        }
        controller.categoryBox.getSelectionModel().select(playsound.getCategory());

        // set the reference detail
        controller.playsoundName.setText(playsound.getName());

        /*
        Set sound details
         */
        setSoundDetails(playsound, controller, false);

        return editPlaysoundWindow;
    }

    public Stage showEditGroup(PlaysoundGroup playsoundGroup, Stage editPlaysoundWindow) {
        System.out.println("Edit Group " + playsoundGroup.getName());

        // load FXML and set the controller
        ProjectController controller = new ProjectController();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/editPlaysound.fxml")));
        loader.setController(controller); // addPlaysound/viewProject controller
        Node addPlaysound = null;
        try {
            addPlaysound = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        editPlaysoundControllerReference = controller;
        soundsVBoxReference = controller.soundsVBox;

        coreScrollpaneReference.setContent(addPlaysound);

        // set JavaFX stage details
        editPlaysoundWindow.setTitle("JSON Sound Definitions Editor - Edit Group");

        // USES FIRST PLAYSOUND AS THE TEMPLATE
        Playsound playsound = playsoundGroup.playsounds.get(0);

        controller.incrementBox.setText(playsoundGroup.playsounds.size() + "");

        // Set playsound details
        controller.nameField.setText(playsoundGroup.getName());
        if (playsound.getMin() != null) {
            controller.minDistanceField.setText(playsound.getMin() + "");
        }
        if (playsound.getMax() != null) {
            controller.maxDistanceField.setText(playsound.getMax() + "");
        }
        controller.categoryBox.getSelectionModel().select(playsound.getCategory());

        // set the reference
        controller.playsoundGroup.setText(playsound.getGroup().getName());

        /*
        Set sound details
         */
        setSoundDetails(playsound, controller, true);

        return editPlaysoundWindow;
    }

    private void setSoundDetails(Playsound playsound, ProjectController controller, Boolean group) {
        int numSounds = playsound.sounds.size();
        // populate first playsound
        VBox firstSoundVBox = ((VBox) ((HBox) controller.soundsVBox.getChildren().get(0)).getChildren().get(1));
        Sound firstSound = playsound.sounds.get(0);

        String firstDirectory = firstSound.getDirectory();
        if (group) {
            firstDirectory = firstDirectory.substring(0, firstDirectory.length() - 1);
        }
        ((TextField) ((HBox) firstSoundVBox.getChildren().get(0)).getChildren().get(2)).setText(firstDirectory); // set directory
        ((CheckBox) ((HBox) firstSoundVBox.getChildren().get(1)).getChildren().get(2)).setSelected(firstSound.getStream()); // set stream
        if (firstSound.getVolume() != null) {
            ((TextField) ((HBox) firstSoundVBox.getChildren().get(2)).getChildren().get(2)).setText(firstSound.getVolume()  + ""); // set volume
        }
        if (firstSound.getPitch() != null) {
            ((TextField) ((HBox) firstSoundVBox.getChildren().get(3)).getChildren().get(2)).setText(firstSound.getPitch()  + ""); // set pitch
        }
        ((CheckBox) ((HBox) firstSoundVBox.getChildren().get(4)).getChildren().get(2)).setSelected(firstSound.getLOLM()); // set LOLM

        // populate remaining playsounds
        for (int i = 1; i < numSounds; i++) {
            try {
                VBox soundVBox = addSound(controller); // create the sound GUI display
                Sound sound = playsound.getSound(i);

                // populate the sound
                String directory = sound.getDirectory();
                if (group) {
                    directory = directory.substring(0, directory.length() - 1);
                }
                ((TextField) ((HBox) soundVBox.getChildren().get(0)).getChildren().get(2)).setText(directory); // set directory
                ((CheckBox) ((HBox) soundVBox.getChildren().get(1)).getChildren().get(2)).setSelected(sound.getStream()); // set stream
                if (sound.getVolume() != null) {
                    ((TextField) ((HBox) soundVBox.getChildren().get(2)).getChildren().get(2)).setText(sound.getVolume()  + ""); // set volume
                }
                if (sound.getPitch() != null) {
                    ((TextField) ((HBox) soundVBox.getChildren().get(3)).getChildren().get(2)).setText(sound.getPitch()  + ""); // set pitch
                }
                ((CheckBox) ((HBox) soundVBox.getChildren().get(4)).getChildren().get(2)).setSelected(sound.getLOLM()); // set LOLM

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean deletePlaysound(String playsoundRefName, String refGroup, int debug) {
        // search for the playsound
        Playsound playsound = null;
        PlaysoundGroup group = null;


        if (debug != 0) {
            incrementBox = new TextField();
            incrementBox.setText(debug + "");
        }

        if (incrementBox != null && incrementBox.getText().equals("1")) {
            // single playsound
            for (Playsound psound : editorData.playsounds) {
                if (psound.getName().equals(playsoundRefName)) {
                    playsound = psound;
                    break;
                }
            }
        } else {
            // playsound group
            for (Playsound psound : editorData.playsounds) {
                if (psound.getGroup().getName().equals(refGroup)) {
                    group = psound.getGroup();
                    break;
                }
            }
        }

        // check if could not find playsound or group
        if (playsound == null && group == null) {
            System.out.println(playsoundRefName);
            System.out.println(refGroup);
            System.out.println("Could not remove playsound " + playsoundRefName);
            return false;
        }

        // delete playsound
        if (group == null) {
            editorData.playsounds.remove(playsound); // remove from core display
            if (playsound.getGroup() != null) {
                playsound.getGroup().playsounds.remove(playsound); // remove from group
            }

            System.out.println("Deleted playsound " + playsound.getName());
            return true;
        }

        // delete playsoundgroup
        for (Playsound psound : group.playsounds) {
            editorData.playsounds.remove(psound); // remove from core display
        }

        System.out.println("Deleted group " + group.getName());
        return true;
    }


    @FXML
    private void deletePlaysound(ActionEvent event) throws IOException {
        // get the reference info
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        TextField refBox = getRefBox(stage);
        TextField refGroup = getRefGroup(stage);

        deletePlaysound(refBox.getText(), refGroup.getText(), 0); // calls the above helper method
        showViewProject(stage);
    }

    @FXML
    private void saveEditPlaysound(ActionEvent event) throws IOException {
        // get the reference info
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        TextField refBox = getRefBox(stage);
        TextField refGroup = getRefGroup(stage);

        // delete the old playsound
        deletePlaysound(refBox.getText(), refGroup.getText(), 0); // calls the above helper method

        // save the new playsound
        saveAddPlaysound(stage);

        showViewProject(stage);
    }

    private TextField getRefBox(Stage stage) {
        return ((TextField)((VBox)((AnchorPane)((ScrollPane)((SplitPane)((VBox) stage.getScene().getRoot())
                .getChildren().get(2)).getItems().get(1)).getContent()).getChildren().get(0)).getChildren().get(10));
    }

    private TextField getRefGroup(Stage stage) {
        return ((TextField)((VBox)((AnchorPane)((ScrollPane)((SplitPane)((VBox) stage.getScene().getRoot())
                .getChildren().get(2)).getItems().get(1)).getContent()).getChildren().get(0)).getChildren().get(11));
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
    protected void importSoundDefinitions() {
        SoundIO.importSoundDefinitions();
        populatePlaysounds();
    }
}

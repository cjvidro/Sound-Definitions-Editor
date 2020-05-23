package jSONEditor.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ModifyPlaysoundController {
    private static EditorData editorData = EditorData.getInstance();

    @FXML public TextField nameField;
    @FXML public ComboBox categoryBox;
    @FXML public ComboBox templateBox;
    @FXML public ComboBox folderBox;
    @FXML public TextField referenceName;
    @FXML private Button saveButton;
    @FXML private TextField fileDirectoryField;
    @FXML private TextField soundName;

    @FXML public VBox soundsVBox;
    private static VBox soundsVBoxRef;

    /*
    Core Functionality -------------------------------------------------------------------------------------------------
     */
    @FXML
    public void initialize() {
        if (soundsVBox != null) soundsVBoxRef = soundsVBox;

        if (nameField != null) {
            if (referenceName == null) {
                referenceName = new TextField();
            }
            referenceName.setText(nameField.getText());
        }
        loadTemplates();
        loadCategories();
        loadFolders();
    }

    @FXML
    /**
     * Adds a playsound to internal storage structure
     */
    public void addPlaysound() {
        if (validateInput()) {
            System.out.println("Input is valid!");

            // set playsound details
            Playsound playsound = new Playsound();
            playsound.setName(nameField.getText());
            playsound.setCategory((Category) categoryBox.getValue());

            PlaysoundGroup folder = editorData.folders.get(folderBox.getSelectionModel().getSelectedItem());
            playsound.setGroup(folder);
            folder.playsounds.add(playsound);

            // set sound details
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

                String name = ((TextField) soundBoxes[0].getChildren().get(2)).getText();
                File file = new File(((TextField) soundBoxes[1].getChildren().get(2)).getText());
                Boolean stream = ((CheckBox) soundBoxes[2].getChildren().get(2)).isSelected();

                // check if volume, lolm are empty
                Double volume = null;

                if (!((TextField) soundBoxes[3].getChildren().get(2)).getText().equals("")) {
                    volume = Double.parseDouble(((TextField) soundBoxes[3].getChildren().get(2)).getText());
                }

                Boolean lolm = ((CheckBox) soundBoxes[4].getChildren().get(2)).isSelected();

                playsound.addSound(name, file, stream, volume, lolm);
            }

            // Add the playsound to editorData instance
            editorData.playsounds.add(playsound);

            System.out.println("Added playsound " + playsound.getName());

            showViewProject();
        }
    }

    @FXML
    /**
     * Edits an existing playsound
     */
    public void editPlaysound() {
        if (validateInput()) {
            System.out.println("Input is valid!");

            // Find the playsound
            Playsound playsound = null;
            for (Playsound p : editorData.playsounds) {
                if (p.getName().equals(referenceName.getText())) {
                    playsound = p;
                    break;
                }
            }

            deletePlaysound(playsound);
            addPlaysound();
        }
    }

    @FXML
    /**
     * FXML method to delete an existing playsound
     */
    public void deletePlaysound() {
        // Find the playsound
        Playsound playsound = null;
        for (Playsound p : editorData.playsounds) {
            if (p.getName().equals(referenceName.getText())) {
                playsound = p;
                break;
            }
        }

        deletePlaysound(playsound);
        showViewProject();
    }

    /**
     * Deletes a playsound
     * @param playsound to be deleted
     */
    private void deletePlaysound(Playsound playsound) {
        editorData.playsounds.remove(playsound);
        playsound.getGroup().playsounds.remove(playsound);
        System.out.println("Removed playsound " + playsound.getName());
    }

    @FXML
    /**
     * Called when a template is selected
     */
    private void updateTemplate() {
        if (templateBox != null) {
            String name = (String)templateBox.getValue();
            Template template = null;
            for (Template t : editorData.templates) {
                if (t.getName().equals(name)) {
                    template = t;
                    break;
                }
            }

            // update values of playsound
            if (categoryBox != null && template.getDefaultCategory() != null) {
                categoryBox.setValue(template.getDefaultCategory());
            }

            // update values of sounds
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

                ((CheckBox) soundBoxes[2].getChildren().get(2)).setSelected(template.getDefaultStream()); // stream

                if (template.getDefaultVolume() != null) {
                    ((TextField) soundBoxes[3].getChildren().get(2)).setText(template.getDefaultVolume() + ""); // volume
                }

                ((CheckBox) soundBoxes[4].getChildren().get(2)).setSelected(template.detectLOLMSetting()); // LOLM
            }
        }
    }

    /*
    Scene Functionality ------------------------------------------------------------------------------------------------
     */

    @FXML
    /**
     * Exits the modify playsound scene
     */
    private void showViewProject(ActionEvent event) {
        Stage stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
        stage.close();
        editorData.projectController.refresh();
        System.out.println("Exited Modify Playsound");
    }

    /**
     * Calls showViewProject when not called from FXML
     */
    private void showViewProject() {
        showViewProject(new ActionEvent(saveButton, tail -> null));
    }

    /*
    Helper Methods -----------------------------------------------------------------------------------------------------
     */

    /**
     * Loads templates into the template box
     */
    private void loadTemplates() {
        if (templateBox != null) {
            for (Template template : editorData.templates) {
                templateBox.getItems().addAll(template.getName());
            }
        }
    }

    /**
     *  Loads categories into the category box
     */
    private void loadCategories() {
        if (categoryBox != null) {
            for (Category category : Category.values()) {
                categoryBox.getItems().addAll(category);
            }
        }
    }

    /**
     * Loads the folders into the folders box
     */
    private void loadFolders() {
        if (folderBox != null) {
            for (String s : editorData.folders.keySet()) {
                folderBox.getItems().addAll(s);

                if (s.equals("Default")) {
                    folderBox.getSelectionModel().select(s);
                }
            }
        }
    }

    /**
     * Populates values when editing a single playsound
     * @param playsound
     */
    public void populatePlaysound(Playsound playsound) {
        try {
            if (((Stage) saveButton.getScene().getWindow()).getTitle().equals("JSON Sound Definitions Editor - Edit Playsound")) {
                // set name and category
                nameField.setText(playsound.getName());
                categoryBox.getSelectionModel().select(playsound.getCategory());
                folderBox.getSelectionModel().select(playsound.getGroup().getName());

                // set reference information
                referenceName.setText(playsound.getName());

                // populate sounds
                populateSounds(playsound);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates only the sound values for a particular playsound
     * @param playsound
     */
    private void populateSounds(Playsound playsound) {
        int numOfSounds = playsound.sounds.size();

        // populate first playsound
        VBox firstSoundVBox = ((VBox) ((HBox) soundsVBox.getChildren().get(0)).getChildren().get(1));
        Sound firstSound = playsound.sounds.get(0);

        String firstDirectory = firstSound.getName();
        ((TextField) ((HBox) firstSoundVBox.getChildren().get(0)).getChildren().get(2)).setText(firstDirectory); // set name
        ((TextField) ((HBox) firstSoundVBox.getChildren().get(1)).getChildren().get(2)).setText(firstSound.getFile().getAbsolutePath()); // set directory
        ((CheckBox) ((HBox) firstSoundVBox.getChildren().get(2)).getChildren().get(2)).setSelected(firstSound.getStream()); // set stream
        if (firstSound.getVolume() != null) {
            ((TextField) ((HBox) firstSoundVBox.getChildren().get(3)).getChildren().get(2)).setText(firstSound.getVolume()  + ""); // set volume
        }
        ((CheckBox) ((HBox) firstSoundVBox.getChildren().get(4)).getChildren().get(2)).setSelected(firstSound.getLOLM()); // set LOLM

        // populate remaining playsounds
        for (int i = 1; i < numOfSounds; i++) {
            try {
                VBox soundVBox = addSoundVariation(); // create the sound GUI display
                Sound sound = playsound.getSound(i);

                // populate the sound
                String directory = sound.getName();
                ((TextField) ((HBox) soundVBox.getChildren().get(0)).getChildren().get(2)).setText(directory); // set name
                ((TextField) ((HBox) soundVBox.getChildren().get(1)).getChildren().get(2)).setText(sound.getFile().getAbsolutePath()); // set name
                ((CheckBox) ((HBox) soundVBox.getChildren().get(2)).getChildren().get(2)).setSelected(sound.getStream()); // set stream
                if (sound.getVolume() != null) {
                    ((TextField) ((HBox) soundVBox.getChildren().get(3)).getChildren().get(2)).setText(sound.getVolume()  + ""); // set volume
                }
                ((CheckBox) ((HBox) soundVBox.getChildren().get(4)).getChildren().get(2)).setSelected(sound.getLOLM()); // set LOLM

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds an additional playsound variation display
     */
    @FXML
    private VBox addSoundVariation() throws IOException {
        int sounds = soundsVBox.getChildren().size();

        // load FXML and set the controller
        ModifyPlaysoundController myController = new ModifyPlaysoundController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/sound.fxml")));
        loader.setController(myController); // view project controller
        Node sound = loader.load();

        soundsVBox.getChildren().add(sounds - 1, sound);

        return (VBox) ((HBox) sound).getChildren().get(1);
    }

    @FXML
    private void removeSound(ActionEvent event) {
        Button button = (Button) event.getSource();
        HBox sound = (HBox) button.getParent();

        soundsVBoxRef.getChildren().remove(sound);

        System.out.println("Removed sound");
    }

    /**
     * Validates whether the user input is valid
     * @return true if input is valid
     */
    private boolean validateInput() {
        return (validatePlaysoundDetails() && validateSoundDetails());
    }

    /**
     * Validates whether the playsound details (name) is valid
     * @return true if input is valid
     */
    private boolean validatePlaysoundDetails() {
        // validate name
        if (nameField.getText().equals("")) {
            System.out.println("Name is blank!");
            return false;
        }

        // check if name already exists
        for (Playsound p : editorData.playsounds) {
            if (p.getName().equals(nameField.getText())) {
                // found a playsound with the name, check if it is the same playsound
                if (!p.getName().equals(referenceName.getText())) {
                    System.out.println("A playsound with this name already exists!");
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Determines if the sound details are valid
     * @return true if input is valid
     */
    private boolean validateSoundDetails() {
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

                // check if name is invalid
                String name = ((TextField) soundBoxes[0].getChildren().get(2)).getText();
                if (!checkName(name)) return false;

                // check if directory is invalid
                String directory = ((TextField) soundBoxes[1].getChildren().get(2)).getText();
                if(!checkDirectory(directory)) return false;

                // check if volume is invalid
                String volumeText =((TextField) soundBoxes[3].getChildren().get(2)).getText();
                if (!checkDouble(volumeText)) return false;
            }
        }
        return false;
    }

    /**
     * @param overlyingBox - The overlying HBox for a sound
     * @return an array of HBoxes corresponding to a sounds directory, stream, volume, and LOLM
     */
    public HBox[] getSoundHBoxes(HBox overlyingBox) {
        HBox[] soundBoxes = new HBox[5];

        if (overlyingBox != null) {
            VBox containingBox = (VBox) overlyingBox.getChildren().get(1);

            soundBoxes[0] = (HBox) containingBox.getChildren().get(0); // name box
            soundBoxes[1] = (HBox) containingBox.getChildren().get(1); // sound directory box
            soundBoxes[2] = (HBox) containingBox.getChildren().get(2); // stream box
            soundBoxes[3] = (HBox) containingBox.getChildren().get(3); // volume box
            soundBoxes[4] = (HBox) containingBox.getChildren().get(4); // LOLM box
        }

        return soundBoxes;
    }

    /**
     * Determines if a name is valid
     * @param string the name
     * @return true if the name is not empty
     */
    public boolean checkName(String string) {
        if (string.equals("")) {
            System.out.println("Sound name is blank!");
            return false;
        }

        return true;
    }

    /**
     * Determines if a directory is valid
     * @param string the absolute directory as a string
     * @return true if the directory is a valid file
     */
    public boolean checkDirectory(String string) {
        return true;

//        File file = new File(string);
//        if (!file.exists()) {
//            System.out.println("Sound file does not exist!");
//            return false;
//        }
//
//        String ending = file.getAbsolutePath().substring(file.getAbsolutePath().length() - 4);
//
//        if (ending.equals(".ogg") || ending.equals(".wav") || ending.equals(".fsb")) {
//            return true;
//        }
//
//        System.out.println("This is not a valid sound file!");
//        return false;
    }

    /**
     * Determines if a string is an acceptable double input
     * @param string the user's input
     * @return true if the string is valid
     */
    public boolean checkDouble(String string) {
        if (string == null) {
            System.out.println("String was null");
            return false;
        }

        if (string.equals("")) {
            return true; // Valid since no user input is allowed
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
     * Prompts the user to select a sound file
     * And then autofills the sound file directory label
     */
    @FXML
    private void selectSoundFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sound File","*.ogg", "*.wav", "*.fsb"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            String filePath = file.getAbsolutePath();

            // update the file directory
            fileDirectoryField.setText(filePath);

            if (soundName != null && soundName.getText().equals("")) {
                // update the sound name if blank
                soundName.setText(file.getName().substring(0, file.getName().length() - 4));
            }
        }
    }
}

package jSONEditor.Tests;

import jSONEditor.Controller.KeyController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyControllerTest {
    private Stage myStage;
    KeyController controller;
    ActionEvent event;

    @Before
    public void start() throws Exception {
        event = new ActionEvent();

        controller = new KeyController(); // the controller for settings GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../../view/key.fxml")));
        loader.setController(controller); // key controller
        Parent root = loader.load();

        // set JavaFX stage details
        myStage = new Stage();
        myStage.setTitle("JSON Sound Definitions Editor - Account");
        myStage.setScene(new Scene(root));
        myStage.show();
    }

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void testSetup() {
        assertNotNull(myStage);
        assertEquals("JSON Sound Definitions Editor - Account", myStage.getTitle());
    }

    @Test
    public void testSaveKey() {
        controller.keyBox.setText("");
        assertFalse(controller.saveKey(myStage));
        controller.keyBox.setText("123");
        assertFalse(controller.saveKey(myStage));
        controller.keyBox.setText("1111111122222222333333334444444455555555aaaaaaaabNbbbbbccccccddd");
        assertFalse(controller.saveKey(myStage));

        controller.keyBox.setText("1111111122222222333333334444444455555555aaaaaaaabbbbbbbccccccddd");
        assertTrue(controller.saveKey(myStage));
    }
}
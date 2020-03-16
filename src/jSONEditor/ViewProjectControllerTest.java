package jSONEditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewProjectControllerTest {

    private Stage myStage;

    @Before
    public void start() throws Exception {
        ViewProjectController controller = new ViewProjectController(); // the controller for the view project GUI
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/viewProject.fxml")));
        loader.setController(controller); // view project controller
        Parent root = loader.load();

        // set JavaFX stage details
        myStage = new Stage();
        myStage.setTitle("JSON Sound Definitions Editor");
        myStage.setScene(new Scene(root, 1280, 720));
        myStage.show();
    }

    @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    @Test
    public void testSetup() {
        assertNotNull(myStage);
        assertEquals("JSON Sound Definitions Editor", myStage.getTitle());
    }
}
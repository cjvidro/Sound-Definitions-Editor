package jSONEditor.Controller;

import javafx.scene.control.TitledPane;

import java.util.ArrayList;

public class EditorData {
    private static EditorData single_instance = null;

    // "Global" variables that should be accessible by ALL classes in the package
    public ArrayList<Playsound> playsounds;
    public ArrayList<Template> templates;
    public TitledPane expandedPane;

    private EditorData() {
        playsounds = new ArrayList<>();
        templates = new ArrayList<>();
    }

    public static EditorData getInstance() {
        if (single_instance == null) {
            single_instance = new EditorData();
        }

        return single_instance;
    }
}
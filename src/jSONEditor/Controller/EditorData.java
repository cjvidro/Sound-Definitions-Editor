package jSONEditor.Controller;

import javafx.scene.control.TitledPane;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EditorData {
    private static EditorData single_instance = null;

    // "Global" variables that should be accessible by ALL classes in the package
    public ArrayList<Playsound> playsounds;
    public ArrayList<Template> templates;
    public TitledPane expandedPane;
    public File currentDirectory = null;
    public static HashMap<String, File> saves = null;

    private EditorData() {
        playsounds = new ArrayList<>();
        templates = new ArrayList<>();
    }

    public static EditorData getInstance() {
        if (single_instance == null) {
            single_instance = new EditorData();
        }

        // setup saves
        if (saves == null) {
            // try to load the file
            try {
                FileInputStream fis = new FileInputStream("./config/saves.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                saves = (HashMap) ois.readObject();
                ois.close();
                fis.close();
                System.out.println("Successfully loaded saves!");
            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("Failed to load saves!");

                System.out.println("Creating a new save map. . .");
                saves = new HashMap<>();
                serializeSaves();

            } catch (ClassNotFoundException c) {
                System.out.println("Class not found!");
                c.printStackTrace();
            }
        }


        return single_instance;
    }

    public static boolean serializeSaves() {
        try {
            FileOutputStream fos = new FileOutputStream("./config/saves.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(saves);
            oos.close();
            fos.close();
            return true;
        } catch (IOException e) {
            System.out.println("Failed to serialize saves!");
            e.printStackTrace();
            return false;
        }
    }
}
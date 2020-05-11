package jSONEditor.Controller;

import javafx.scene.control.TitledPane;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

public class EditorData {
    private static EditorData single_instance = null;

    // "Global" variables that should be accessible by ALL classes in the package
    public ArrayList<Playsound> playsounds;
    public static ArrayList<Template> templates = null;
    public TitledPane expandedPane;
    public File currentDirectory = null;
    public static File[] saves;
    public static Boolean autosave = null;

    private EditorData() {
        playsounds = new ArrayList<>();
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
                saves = (File[]) ois.readObject();
                ois.close();
                fis.close();
                System.out.println("Successfully loaded saves!");
            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("Failed to load saves!");

                System.out.println("Creating a new save map. . .");
                saves = new File[5];
                serializeSaves();

            } catch (ClassNotFoundException c) {
                System.out.println("Class not found!");
                c.printStackTrace();
            }
        }


        // setup settings
        if (autosave == null) {
            // try to load the files
            try {
                FileInputStream fis = new FileInputStream("./config/autosave.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                autosave = (Boolean) ois.readObject();
                ois.close();
                fis.close();

                System.out.println("Successfully loaded settings!");
            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("Failed to load settings!");

                System.out.println("Creating a new settings. . .");
                autosave = true;

                serializeSettings();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (templates == null) {

            // try to load the file
            try {
                FileInputStream fis = new FileInputStream("./config/templates.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                templates = (ArrayList<Template>) ois.readObject();
                ois.close();
                fis.close();
                System.out.println("Successfully loaded templates!");
            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("Failed to load templates!");

                System.out.println("Creating a new template list. . .");
                templates = new ArrayList<>();
                serializeTemplateSaves();

            } catch (ClassNotFoundException c) {
                System.out.println("Class not found!");
                c.printStackTrace();
            }
        }

        return single_instance;
    }
    
    public static boolean serializeTemplateSaves() {
        try {
            File file = new File("./config");
            file.mkdir();
            FileOutputStream fos = new FileOutputStream("./config/templates.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(templates);
            oos.close();
            fos.close();
            System.out.println("Serialized templates!");
            return true;
        } catch (IOException e) {
            System.out.println("Failed to serialize template saves!");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean serializeSaves() {
        try {
            File file = new File("./config");
            file.mkdir();
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

    public static boolean serializeSettings() {
        try {
            // autosave
            File file = new File("./config");
            file.mkdir();
            FileOutputStream fos = new FileOutputStream("./config/autosave.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(autosave);
            oos.close();
            fos.close();

            System.out.println("Serialized settings");
            return true;
        } catch (IOException e) {
            System.out.println("Failed to serialize settings!");
            e.printStackTrace();
            return false;
        }
    }
}
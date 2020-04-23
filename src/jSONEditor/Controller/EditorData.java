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
    public static Boolean webBackup = null;
    protected static String key = null;
    protected static String username = null;
    public String changelog = "";

    private EditorData() {
        playsounds = new ArrayList<>();
        changelog = "";
    }

    public static EditorData getInstance() {
        if (single_instance == null) {
            single_instance = new EditorData();

            try {
                loadKey();
            } catch (IOException e) {
                System.out.println("Failed to authenticate user!");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Failed to authenticate user!");
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                System.out.println("Failed to authenticate user!");
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Failed to authenticate user!");
                e.printStackTrace();
            }
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
        if (autosave == null || webBackup == null) {
            // try to load the files
            try {
                FileInputStream fis = new FileInputStream("./config/autosave.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                autosave = (Boolean) ois.readObject();
                ois.close();
                fis.close();

                fis = new FileInputStream("./config/webBackup.ser");
                ois = new ObjectInputStream(fis);
                webBackup = (Boolean) ois.readObject();
                ois.close();
                fis.close();

                System.out.println("Successfully loaded settings!");
            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("Failed to load settings!");

                System.out.println("Creating a new settings. . .");
                autosave = true;
                webBackup = true;

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

            // web Backup
            fos = new FileOutputStream("./config/webBackup.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(webBackup);
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

    public static void saveKey() throws IOException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        if (MySQLAccess.getUsername() == null) {
            username = null;
        }

        // Get AES Key
        SecretKey myKey = null;
        try (FileInputStream fis = new FileInputStream(new File("./config/key.dat"));
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            myKey = (SecretKey) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // generate IV
        SecureRandom random = new SecureRandom();
        byte [] iv = new byte [16];
        random.nextBytes( iv );

        // create cipher
        Cipher cipher = Cipher.getInstance( myKey.getAlgorithm() + "/CBC/PKCS5Padding" );
        cipher.init( Cipher.ENCRYPT_MODE, myKey, new IvParameterSpec( iv ) );

        // create sealed object
        SealedObject sealedEm1 = new SealedObject( key, cipher);

        // Save it
        FileOutputStream fos = new FileOutputStream("./config/user.aes");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(sealedEm1);
    }

    public static void loadKey() throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException {
        // Get AES Key
        SecretKey myKey = null;
        try (FileInputStream fis = new FileInputStream(new File("./config/key.dat"));
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            myKey = (SecretKey) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Read
        FileInputStream fis = new FileInputStream("./config/user.aes");
        ObjectInputStream ois = new ObjectInputStream(fis);
        SealedObject sealedObject = (SealedObject) ois.readObject();
        key = (String) sealedObject.getObject(myKey);

        System.out.println("Loaded user key!");

        username = MySQLAccess.getUsername();
    }
}
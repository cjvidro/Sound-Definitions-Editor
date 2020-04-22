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
    public ArrayList<Template> templates;
    public TitledPane expandedPane;
    public File currentDirectory = null;
    public static File[] saves;
    protected static String key = null;

    private EditorData() {
        playsounds = new ArrayList<>();
        templates = new ArrayList<>();
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

        return single_instance;
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

    public static void saveKey() throws IOException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
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
    }
}
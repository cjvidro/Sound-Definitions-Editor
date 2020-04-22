package jSONEditor.Controller;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class MySQLAccess {
    private static Connection connect = null;
    private static Statement statement = null;
    private static ResultSet rs = null;


    private static class UserPass implements Serializable {
        String username;
        String password;
    }

    private static UserPass userPass = new UserPass();

    public static void readDataBase() throws Exception {
        initialize();

        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection(
                    "jdbc:mysql://scottcorbat.com:3306/?serverTimezone=UTC", userPass.username, userPass.password);

            System.out.println("Successfully connected!");


            statement = connect.createStatement();
            rs = statement.executeQuery("use mhg_django;"); // select database
            //rs = statement.executeQuery("describe home_userkey;");

//            /*
//            Print result
//             */
//            System.out.println("-----------------------------------------------");
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int columnsNumber = rsmd.getColumnCount();
//            while (rs.next()) {
//                for (int i = 1; i <= columnsNumber; i++) {
//                    if (i > 1) System.out.print(",  ");
//                    String columnValue = rs.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
//                }
//                System.out.println("");
//            }
//            System.out.println("-----------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
    }

    }

    private static void initialize() throws NoSuchAlgorithmException, InvalidKeyException, IOException, ClassNotFoundException {
        // Get AES Key
        SecretKey myKey = null;
        try (FileInputStream fis = new FileInputStream(new File("./config/key.dat"));
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            myKey = (SecretKey) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Read
        FileInputStream fis = new FileInputStream("./config/db.aes");
        ObjectInputStream ois = new ObjectInputStream(fis);
        SealedObject sealedObject = (SealedObject) ois.readObject();
        userPass = (UserPass) sealedObject.getObject(myKey);
    }

    public static String getUsername() {
        EditorData.getInstance();
        String key = EditorData.key;

        if (key == null) {
            return null;
        }

        // initializaiton
        try {
            initialize();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        // database stuff
        try {

            statement = connect.createStatement();
            rs = statement.executeQuery("use mhg_django;"); // select database
            rs = statement.executeQuery("select username from home_userkey where token = '" + key + "';");


            if (rs.next() && !(rs.getString(1).equals(""))){
                System.out.println("Valid user token! Connected as user " + rs.getString(1));
                return rs.getString(1);
            } else {
                System.out.println("Invalid token!");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    Upload sound_def (File file)
     */
    public static boolean uploadSoundDef(File file) {

        return false;
    }
}
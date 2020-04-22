package jSONEditor.Controller;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    private static class UserPass implements Serializable {
        String username;
        String password;
    }

    UserPass userPass = new UserPass();

    public void readDataBase() throws Exception {
        initialize();

        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection(
                    "jdbc:mysql://scottcorbat.com:3306/?serverTimezone=UTC", userPass.username, userPass.password);

            System.out.println("Successfully connected!");
        } catch (SQLException e) {
            e.printStackTrace();
    }

    }

    private void initialize() throws NoSuchAlgorithmException, InvalidKeyException, IOException, ClassNotFoundException {
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
}
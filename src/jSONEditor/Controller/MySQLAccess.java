package jSONEditor.Controller;

import com.jcraft.jsch.*;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class MySQLAccess {
    // mySQL stuff
    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    // SFTP stuff
    private static String host = "scottcorbat.com";
    private static int port = 22;
    private static Session session = null;


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
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    Upload sound_def (File file)
     */
    public static boolean uploadSoundDef(File localFile) {
        if (EditorData.username == null || EditorData.webBackup == false) {
            return false;
        }

        EditorData.getInstance(); // initialization

        String projectName = EditorData.getInstance().currentDirectory.getName();

        String localPath = localFile.getAbsolutePath();
        String serverPath = "/home/mhg/mhg/media/sound_defs/user_" + EditorData.username + "/" + projectName + "_" + localFile.getName();
        String tablePath = "sound_defs/user_" + EditorData.username + "/" + projectName + "_" + localFile.getName();

        // upload to server
        try {
            upload(localPath, serverPath);
        } catch (JSchException e) {
            System.out.println("Upload failed!");
            e.printStackTrace();
        } catch (SftpException e) {
            System.out.println("Upload failed!");
            e.printStackTrace();
        }


        String sql = "insert into home_sounddef (username, file) values (?, ?)";

        try {
            ps = connect.prepareStatement(sql);
            ps.setString(1, EditorData.username);

            ps.setString(2, tablePath);

            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Sound definitions file uploaded successfully!");
                return true;
            } else {
                System.out.println("Sound definitions upload failed!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean connectSFTP() throws JSchException {
        JSch jsch = new JSch();

        // Uncomment the line below if the FTP server requires certificate
        // jsch.addIdentity("private-key-path);

        // session = jsch.getSession(server);

        // Comment the line above and uncomment the two lines below if the FTP server requires password
        session = jsch.getSession(userPass.username, host, port);
        session.setPassword(userPass.password);

        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        System.out.println("Connected to SFTP");
        return true;
    }

    private static void upload(String source, String destination) throws JSchException, SftpException {
        connectSFTP();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        sftpChannel.put(source, destination);
        sftpChannel.exit();

        System.out.println("Upload success!");

        disconnect();
    }

    public static void download(String source, String destination) throws JSchException, SftpException {
        connectSFTP();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        sftpChannel.get(source, destination);
        sftpChannel.exit();

        disconnect();
    }

    public static boolean disconnect() {
        if (session != null) {
            session.disconnect();
            System.out.println("Disconnected from SFTP");
        }

        return true;
    }
}
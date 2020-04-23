package jSONEditor.Tests;

import com.jcraft.jsch.JSchException;
import jSONEditor.Controller.MySQLAccess;
import org.junit.Test;

import static org.junit.Assert.*;

public class MySQLAccessTest {

    @Test
    public void testConnection() {
        MySQLAccess mySQLAccess = new MySQLAccess();
        try {
            mySQLAccess.readDataBase();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testSFTP() throws JSchException {
        MySQLAccess access = new MySQLAccess();
        assertTrue(access.connectSFTP());
        assertTrue(access.disconnect());
    }

}
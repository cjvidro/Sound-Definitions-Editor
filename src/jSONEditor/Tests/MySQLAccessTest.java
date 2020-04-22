package jSONEditor.Tests;

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

}
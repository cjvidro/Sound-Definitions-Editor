package jSONEditor.Tests;

import static org.junit.Assert.*;

import jSONEditor.Controller.Settings;
import org.junit.Test;

public class SettingsTest {

	Settings obj = new Settings();
	
	@Test
	public void testAuto() {
		assertEquals(true, obj.getAutoBackup());
		obj.toggleAutoSave();
		assertEquals(false, obj.getAutoBackup());
		obj.toggleAutoSave();
		assertEquals(true, obj.getAutoBackup());
	}
	
	@Test
	public void testLOLM() {
		assertEquals(true, obj.getLOLM());
		obj.toggleAlternateLoadMem();
		assertEquals(false, obj.getLOLM());
		obj.toggleAlternateLoadMem();
		assertEquals(true, obj.getLOLM());
	}
}

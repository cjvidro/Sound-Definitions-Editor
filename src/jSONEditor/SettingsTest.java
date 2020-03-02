package jSONEditor;

import static org.junit.Assert.*;

import org.junit.Test;

public class SettingsTest {

	Settings obj = new Settings();
	
	@Test
	public void testAuto() {
		assertEquals(true, obj.getAuto());
		obj.toggleAutoSave();
		assertEquals(false, obj.getAuto());
		obj.toggleAutoSave();
		assertEquals(true, obj.getAuto());
	}
	
	@Test
	public void testLOLM() {
		assertEquals(true, obj.getLOLM());
		obj.toggleAlternateLoadMem();
		assertEquals(false, obj.getLOLM());
		obj.toggleAlternateLoadMem();
		assertEquals(true, obj.getLOLM());
	}
	
	@Test
	public void testWeb() {
		assertEquals(false, obj.getWeb());
		obj.allowWebBackup();
		assertEquals(true, obj.getWeb());
		obj.allowWebBackup();
		assertEquals(false, obj.getWeb());
	}
}

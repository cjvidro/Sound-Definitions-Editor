package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import jSONEditor.Settings;

public class SettingsTest {

	Settings obj = new Settings();
	
	@Test
	public void testAuto() {
		assertEquals(true, obj.getAuto());
		obj.toggleAutoSave();
		assertEquals(false, obj.getAuto());
	}
	
	@Test
	public void testLOLM() {
		assertEquals(true, obj.getLOLM());
		obj.toggleAlternateLoadMem();
		assertEquals(false, obj.getLOLM());
	}
	
	@Test
	public void testWeb() {
		assertEquals(false, obj.getWeb());
		obj.allowWebBackup();
		assertEquals(true, obj.getWeb());
	}
}

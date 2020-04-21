package jSONEditor.Tests;

import static org.junit.Assert.*;

import jSONEditor.Controller.Template;
import org.junit.Before;
import org.junit.Test;

public class TemplateTest {
	Template template;
	
	@Before
	public void setup() {
		template = new Template();
	}
	
	@Test
    public void initial() {
        assertEquals(0, template.getLOLMSetting());
    }

	@Test
	public void detectLOLMSetting0() {
		template.setLOLMSetting(0);
		
		assertEquals(true, template.detectLOLMSetting());
	}
	
	@Test
	public void detectLOLMSetting1() {
		template.setLOLMSetting(1);
		
		assertEquals(false, template.detectLOLMSetting());
	}
	
	@Test
	public void detectLOLMSetting2() {
		template.setLOLMSetting(2);
		
		assertEquals(true, template.detectLOLMSetting());
		
		assertEquals(false, template.detectLOLMSetting());
		
		assertEquals(false, template.detectLOLMSetting());
	}
	
	@Test
	public void detectLOLMSetting3() {
		template.setLOLMSetting(3);
		
		assertEquals(true, template.detectLOLMSetting());
		
		assertEquals(false, template.detectLOLMSetting());
		
		assertEquals(true, template.detectLOLMSetting());
		
		assertEquals(false, template.detectLOLMSetting());
	}
}

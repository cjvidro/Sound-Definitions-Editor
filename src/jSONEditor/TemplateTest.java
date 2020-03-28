package jSONEditor;

import static org.junit.Assert.*;

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
		template.setDefaultLOLM(true);
		
		template.setLOLMSetting(0);
		
		assertEquals(true, template.detectLOLMSetting());
		
		template.setDefaultLOLM(false);
		
		assertEquals(false, template.detectLOLMSetting());
	}

	@Test
	public void detectLOLMSetting1() {
		template.setLOLMSetting(1);
		
		assertEquals(true, template.detectLOLMSetting());
	}
	
	@Test
	public void detectLOLMSetting2() {
		template.setLOLMSetting(2);
		
		assertEquals(false, template.detectLOLMSetting());
	}
	
	@Test
	public void detectLOLMSetting3() {
		template.setLOLMSetting(3);
		
		assertEquals(true, template.detectLOLMSetting());
		
		assertEquals(false, template.detectLOLMSetting());
		
		assertEquals(false, template.detectLOLMSetting());
	}
	
	@Test
	public void detectLOLMSetting4() {
		template.setLOLMSetting(4);
		
		assertEquals(true, template.detectLOLMSetting());
		
		assertEquals(false, template.detectLOLMSetting());
		
		assertEquals(true, template.detectLOLMSetting());
		
		assertEquals(false, template.detectLOLMSetting());
	}
	
	@Test
	public void detectLOLMSetting5() {
		template.setLOLMSetting(5);
		
		assertEquals(false, template.detectLOLMSetting());
		
		assertEquals(true, template.detectLOLMSetting());
		
		assertEquals(false, template.detectLOLMSetting());
		
		assertEquals(true, template.detectLOLMSetting());
	}
}

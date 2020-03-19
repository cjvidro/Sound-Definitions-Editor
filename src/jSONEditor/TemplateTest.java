package jSONEditor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TemplateTest {

	Template template;
	
	Playsound playsound;
	String name;
	Double min_distance;
	Double max_distance;
	Category category;
	
	Sound sound;
	String directory;
	Boolean stream;
	Double pitch;
	Double volume;
	Boolean load_on_low_mem;
	
	@Before
	public void start() {
		playsound = new Playsound();
		sound = new Sound();
		template = new Template();
		
		playsound.setDefaults();
		playsound.setDefaults();
		
		directory = "sounds/badger/ambient";
	}
	
	/*
	 * Start Test Nulls
	 */
	@Test
	public void testNullName() {
		if(playsound.checkNullString(name)) {
			name = playsound.getDefaultName();
		}
		assertEquals(name, playsound.getName());
	}
	
	@Test
	public void testNullMin() {
		if(playsound.checkNullDoubles(min_distance)) {
			min_distance = playsound.getDefaultMin();
		}
		assertEquals(min_distance, playsound.getMin());
	}
	
	@Test
	public void testNullMax() {
		if(playsound.checkNullDoubles(max_distance)) {
			max_distance = playsound.getDefaultMin();
		}
		assertEquals(max_distance, playsound.getMax());
	}
	
	@Test
	public void testNullCategory() {
		if(playsound.checkNullCategory(category)) {
			category = playsound.getDefaultCategory();
		}
		assertEquals(category, playsound.getCategory());
	}
	/*
	 * End Test Nulls
	 */
	
	/*
	 * Start Test setDefaults
	 */
	
	/*
	 * End Test setDefaults
	 */
}

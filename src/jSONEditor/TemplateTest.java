package jSONEditor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TemplateTest {

	Template template;
	
	Playsound sound;
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
		sound = new Playsound();
		sound = new Sound();
		template = new Template();
		
		sound.setDefaults();
		sound.setDefaults();
		
		directory = "sounds/badger/ambient";
	}
	
	/*
	 * Start Test Nulls
	 */
	@Test
	public void testNullName() {
		if(sound.checkNullString(name)) {
			name = sound.getDefaultName();
		}
		assertEquals(name, sound.getName());
	}
	
	@Test
	public void testNullMin() {
		if(sound.checkNullDoubles(min_distance)) {
			min_distance = sound.getDefaultMin();
		}
		assertEquals(min_distance, sound.getMin());
	}
	
	@Test
	public void testNullMax() {
		if(sound.checkNullDoubles(max_distance)) {
			max_distance = sound.getDefaultMin();
		}
		assertEquals(max_distance, sound.getMax());
	}
	
	@Test
	public void testNullCategory() {
		if(sound.checkNullCategory(category)) {
			category = sound.getDefaultCategory();
		}
		assertEquals(category, sound.getCategory());
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

package jSONEditor;

public class Template {
	
	/*
	 * Sets all of the default variables in the Sounds class.
	 */
	void setDefaultSounds(String name, double min_distance, double max_distance, Category category) {
		Sounds obj = new Sounds();
		
		obj.setDefaultVars(name, min_distance, max_distance, category);
	}
	
	/*
	 * Sets all of the default variables in the PlaySound class.
	 */
	void setDefaultPlaySounds(boolean stream, double pitch, double volume, boolean load_on_load_mem) {
		PlaySound obj = new PlaySound();
		
		obj.setDefaultVars(stream, pitch, volume, load_on_load_mem);
	}
}

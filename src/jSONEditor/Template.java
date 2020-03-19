package jSONEditor;

public class Template {
	
	/*
	 * Sets all of the default variables in the Playsound class.
	 */
	void setDefaultSounds(String name, double min_distance, double max_distance, Category category) {
		Playsound obj = new Playsound();
		
		obj.setDefaultVars(name, min_distance, max_distance, category);
	}
	
	/*
	 * Sets all of the default variables in the Sound class.
	 */
	void setDefaultPlaySounds(boolean stream, double pitch, double volume, boolean load_on_load_mem) {
		Sound obj = new Sound();
		
		obj.setDefaultVars(stream, pitch, volume, load_on_load_mem);
	}
}

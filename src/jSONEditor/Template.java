package jSONEditor;

public class Template {
	
	void setDefaultSounds(String name, double min_distance, double max_distance, Category category) {
		Sounds obj = new Sounds();
		
		obj.setDefaults(name, min_distance, max_distance, category);
	}
	
	void setDefaultPlaySounds(boolean stream, double pitch, double volume, boolean load_on_load_mem) {
		PlaySound obj = new PlaySound();
		
		obj.setDefaults(stream, pitch, volume, load_on_load_mem);
	}
}

package jSONEditor;

import java.util.ArrayList;

public class Sounds {
	
	ArrayList<PlaySound> sound = new ArrayList<>();
	String name;
	double min_distance;
	double max_distance;
	Category category;
	int index = 0;
	
	PlaySound getPlaysound(int index) {
		return sound.get(index);
	}
	
	void setPlaysound(String directory, boolean stream, double volume, double pitch, boolean load) {
		PlaySound play = new PlaySound();
		
		play.setDirectory(directory);
		play.setStream(stream);
		play.setVolume(volume);
		play.setPitch(pitch);
		play.setLoad(load);
		play.setIndex(index);
		index++;
		
		sound.add(play);
	}
	
	Category getCategory() {
		return category;
	}
	
	void setCategory(Category category) {
		this.category = category;
	}
	
	double getMin() {
		return min_distance;
	}
	
	void setMin(double min_distance) {
		this.min_distance = min_distance;
	}
	
	double getMax() {
		return max_distance;
	}
	
	void setMax(double max_distance) {
		this.max_distance = max_distance;
	}
	
	String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}
}

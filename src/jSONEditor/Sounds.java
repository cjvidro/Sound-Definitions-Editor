package jSONEditor;

import java.util.ArrayList;

public class Sounds {
	//Comments like this signal the starts and ends of related methods.
	
	/*
	 * Comments like this give extra information to specific methods.
	 */
	
	//Variable that the user will see.
	ArrayList<PlaySound> sound = new ArrayList<>();
	String name;
	Double min_distance;
	Double max_distance;
	Category category;
	int index = 0;
	
	//Default variables
	String defaultName = "Sound " + index;
	Double defaultMin = 0.0;
	Double defaultMax = 0.0;
	Category defaultCategory = Category.master;
	
	//Start default methods.
	void setDefaultVars(String name, Double min_distance, Double max_distance, Category category) {
		defaultName = name;
		defaultMin = min_distance;
		defaultMax = max_distance;
		defaultCategory = category;
	}
	
	String getDefaultName() {
		return defaultName;
	}
	
	Double getDefaultMax() {
		return defaultMax;
	}
	
	Double getDefaultMin() {
		return defaultMin;
	}
	
	Category getDefaultCategory() {
		return defaultCategory;
	}
	
	PlaySound getPlaysound(int index) {
		return sound.get(index);
	}
	
	void setDefaults() {
		name = getDefaultName();
		min_distance = getDefaultMin();
		max_distance = getDefaultMax();
		category = getDefaultCategory();
	}
	//End default methods
	
	//Start check null methods
	/*
	 * This will check if the name is null.
	 */
	boolean checkNullString(String string) {
		if(string == null)
			return true;
		return false;
	}
	
	/*'
	 * This will check if the max and min distances are null.
	 */
	boolean checkNullDoubles(Double distance) {
		if(distance == null)
			return true;
		return false;
	}
	
	/*
	 * This will check if the category is null.
	 */
	boolean checkNullCategory(Category cat) {
		if(cat == null)
			return true;
		return false;
	}
	//End check null methods
	
	//Start getters and setters
	void setPlaysound(String directory, Boolean stream, Double volume, Double pitch, Boolean load) {
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
	
	Double getMin() {
		return min_distance;
	}
	
	void setMin(Double min_distance) {
		this.min_distance = min_distance;
	}
	
	Double getMax() {
		return max_distance;
	}
	
	void setMax(Double max_distance) {
		this.max_distance = max_distance;
	}
	
	String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}
	//End getters and setters
}

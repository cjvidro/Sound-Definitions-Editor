package jSONEditor.Controller;
import java.io.File;
import java.util.ArrayList;

/**
 * This class is correlated with the sound_definitions playsounds
 */
public class Playsound {
	/*
	Playsound variables
	 */
	private PlaysoundGroup group;
	public ArrayList<Sound> sounds = new ArrayList<>(); // Contains all of the sounds this playsound is related to
	private String name; // playsound name
	private Category category; // category of sound

	/*
	Methods
	 */

	/**
	 * Add a sound to this playsound
	 * @param directory - directory of this sound
	 * @param stream - stream property
	 * @param volume - volume property
	 * @param lolm - load_on_low_memory property
	 */
	public void addSound(String directory, File file, Boolean stream, Double volume, Boolean lolm) {
		Sound sound = new Sound();

		sound.setName(directory);
		sound.setStream(stream);
		sound.setFile(file);
		sound.setLOLM(lolm);
		sound.setIndex(numberSounds()); // the index location in the sounds array list
		sound.setPlaysound(this);

		sounds.add(sound);
	}

	/**
	 * @param index The index of the sound
	 * @return the sound
	 */
	public Sound getSound(int index) {
		return sounds.get(index);
	}

	/**
	 * Removes a single sound from this playsound
	 * @param sound the sound to be removed
	 */
	public void removeSound(Sound sound) {
		if (this != sound.getPlaysound()) {
			// this sound is not associated with this playsound
			return;
		}

		sounds.remove(sound);
		int index = sound.getIndex();
		updateIndices(index);
	}

	/**
	 * Adjusts each index when a playsound is removed
	 * @param index the index the removal took place at
	 */
	private void updateIndices(int index) {
		for (int i = index; i < numberSounds(); i++) {
			sounds.get(i).setIndex(i);
		}
	}

	/*
	Getters and setters
	 */
	public int numberSounds() {
		return sounds.size();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGroup(PlaysoundGroup group) {
		this.group = group;
	}

	public PlaysoundGroup getGroup() {
		return group;
	}
}
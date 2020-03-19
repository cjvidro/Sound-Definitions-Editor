package jSONEditor;
import java.util.ArrayList;

/**
 * This class is correlated with the sound_definitions playsounds
 */
public class Playsound {
	/*
	Playsound variables
	 */
	protected ArrayList<Sound> sounds = new ArrayList<>(); // Contains all of the sounds this playsound is related to
	private String name; // playsound name
	private Double min_distance; // min_distance property
	private Double max_distance; // max_distance property
	private Category category; // category of sound

	/*
	Methods
	 */

	/**
	 * Add a sound to this playsound
	 * @param directory - directory of this sound
	 * @param stream - stream property
	 * @param pitch - pitch property
	 * @param volume - volume property
	 * @param lolm - load_on_low_memory property
	 */
	protected void addSound(String directory, Boolean stream, Double pitch, Double volume, Boolean lolm) {
		Sound sound = new Sound();

		sound.setDirectory(directory);
		sound.setStream(stream);
		sound.setVolume(volume);
		sound.setPitch(pitch);
		sound.setLOLM(lolm);
		sound.setIndex(numberSounds()); // the index location in the sounds array list
		sound.setPlaysound(this);

		sounds.add(sound);
	}

	/**
	 * @param index The index of the sound
	 * @return the sound
	 */
	protected Sound getSound(int index) {
		return sounds.get(index);
	}

	/**
	 * Removes a single sound from this playsound
	 * @param sound the sound to be removed
	 */
	protected void removeSound(Sound sound) {
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
	protected int numberSounds() {
		return sounds.size();
	}

	protected Category getCategory() {
		return category;
	}

	protected void setCategory(Category category) {
		this.category = category;
	}

	protected Double getMin() {
		return min_distance;
	}

	protected void setMin(Double min_distance) {
		this.min_distance = min_distance;
	}

	protected Double getMax() {
		return max_distance;
	}

	protected void setMax(Double max_distance) {
		this.max_distance = max_distance;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}
}
package jSONEditor.Controller;

import java.io.File;

/**
 * This class is correlated with the sound_definitions "sounds" array for each playsound
 */
public class Sound {
	/*
	Sound variables
	 */
	private Playsound playsound;
	private File file; // The *original* file
	private String name; // the name of the sound
	private Boolean stream; // stream property
	private Double volume; // volume property
	private Boolean lolm; // load_on_low_memory property
	private int index = 0; // the index within the playsound this sounds is. Starts with 0.
	
	/*
	Getters and Setters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getStream() {
		return stream; 
	}

	public void setStream(Boolean stream) {
		this.stream = stream; 
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Boolean getLOLM() {
		return lolm;
	}

	public void setLOLM(Boolean lolm) {
		this.lolm = lolm;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Playsound getPlaysound() {
		return playsound;
	}

	public void setPlaysound(Playsound playsound) {
		this.playsound = playsound;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}

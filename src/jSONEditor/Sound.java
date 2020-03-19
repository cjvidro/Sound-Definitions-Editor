package jSONEditor;

/**
 * This class is correlated with the sound_definitions "sounds" array for each playsound
 */
public class Sound {
	// Properties associated with this sound
	private String directory; // the directory the sound is located in relative to the sounds folder
	private Boolean stream; // stream property
	private Double pitch; // pitch property
	private Double volume; // volume property
	private Boolean load_on_low_mem; // load_on_low_memory property
	private int index = 0; // the index within the playsound this sound is. Starts with 0.
	 
	
	// Start check null methods
	/*
	 * This will check if the passed in stream and load are null
	 */
	protected boolean checkNullBoolean(Boolean bool) {
		if(bool == null)
			return true;
		return false;
	}
	
	/*
	 * This will check if the passed in pitch and volume are null
	 */
	protected boolean checkNullDoubles(Double sound) {
		if(sound == null)
			return true;
		return false;
	}
	//End check null methods
	
	
	//Start getters and setters.
	protected String getDirectory() {
		return directory;
	}

	protected void setDirectory(String directory) {
		this.directory = directory; 
	}

	protected boolean getStream() {
		return stream; 
	}

	protected void setStream(Boolean stream) {
		this.stream = stream; 
	}

	protected double getPitch() {
		return pitch; 
	}

	protected void setPitch(Double pitch) {
		this.pitch = pitch;
	}

	protected double getVolume() {
		return volume;
	}

	protected void setVolume(Double volume) {
		this.volume = volume;
	}

	protected boolean getLoad() {
		return load_on_low_mem;
	}

	protected void setLoad(Boolean load_on_low_mem) {
		this.load_on_low_mem = load_on_low_mem;
	}

	protected int getIndex() {
		return index;
	}

	protected void setIndex(int index) {
		this.index = index;
	}
	//End getters and setters.
}

package jSONEditor;

/**
 * This class is correlated with the sound_definitions "sounds" array for each playsound
 */
public class Sound {
	/*
	Sound variables
	 */
	private Playsound playsound;
	private String directory; // the directory the sounds is located in relative to the sounds folder
	private Boolean stream; // stream property
	private Double pitch; // pitch property
	private Double volume; // volume property
	private Boolean lolm; // load_on_low_memory property
	private int index = 0; // the index within the playsound this sounds is. Starts with 0.
	
	/*
	Getters and Setters
	 */
	protected String getDirectory() {
		return directory;
	}

	protected void setDirectory(String directory) {
		this.directory = directory; 
	}

	protected Boolean getStream() {
		return stream; 
	}

	protected void setStream(Boolean stream) {
		this.stream = stream; 
	}

	protected Double getPitch() {
		return pitch; 
	}

	protected void setPitch(Double pitch) {
		this.pitch = pitch;
	}

	protected Double getVolume() {
		return volume;
	}

	protected void setVolume(Double volume) {
		this.volume = volume;
	}

	protected Boolean getLOLM() {
		return lolm;
	}

	protected void setLOLM(Boolean lolm) {
		this.lolm = lolm;
	}

	protected int getIndex() {
		return index;
	}

	protected void setIndex(int index) {
		this.index = index;
	}

	protected Playsound getPlaysound() {
		return playsound;
	}

	protected void setPlaysound(Playsound playsound) {
		this.playsound = playsound;
	}
}

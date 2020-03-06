package jSONEditor;

public class PlaySound {
	//Comments like this signal the starts and ends of related methods.
	
	/*
	 * Comments like this give extra information to specific methods.
	 */

	//Variables that the user will see
	String directory;
	Boolean stream;
	Double pitch;
	Double volume;
	Boolean load_on_low_mem;
	int index;
	
	
	//Default variables
	Boolean defaultStream = false;
	Double defaultPitch = 0.0;
	Double defaultVolume = 0.0;
	Boolean defaultLoad = false;
	
	
	//Start default methods.
	void setDefaultVars(Boolean stream, Double pitch, Double volume, Boolean load_on_load_mem) {
		defaultStream = stream;
		defaultPitch = pitch;
		defaultVolume = volume;
		defaultLoad = load_on_low_mem;
	}
	
	boolean getDefaultStream() {
		return defaultStream;
	}
	
	double getDefaultPitch() {
		return defaultPitch;
	}
	
	double getDefaultVolume() {
		return defaultVolume;
	}
	
	boolean getDefaultLoad() {
		return defaultLoad;
	}
	
	void setDefaults() {
		stream = getDefaultStream();
		pitch = getDefaultPitch();
		volume = getDefaultVolume();
		load_on_low_mem = getDefaultLoad();
	}
	//End default methods.
	 
	
	// Start check null methods
	/*
	 * This will check if the passed in stream and load are null
	 */
	boolean checkNullBoolean(Boolean bool) {
		if(bool == null)
			return true;
		return false;
	}
	
	/*
	 * This will check if the passed in pitch and volume are null
	 */
	boolean checkNullDoubles(Double sound) {
		if(sound == null)
			return true;
		return false;
	}
	//End check null methods
	
	
	//Start getters and setters.
	String getDirectory() {
		return directory;
	}
	
	void setDirectory(String directory) { 
		this.directory = directory; 
	}
	
	boolean getStream() { 
		return stream; 
	}
	
	void setStream(Boolean stream) { 
		this.stream = stream; 
	}
	
	double getPitch() { 
		return pitch; 
	}
	
	void setPitch(Double pitch) {
		this.pitch = pitch;
	}
	
	double getVolume() {
		return volume;
	}
	
	void setVolume(Double volume) {
		this.volume = volume;
	}
	
	boolean getLoad() {
		return load_on_low_mem;
	}
	
	void setLoad(Boolean load_on_low_mem) {
		this.load_on_low_mem = load_on_low_mem;
	}
	
	void setIndex(int index) {
		this.index = index;
	}
	//End getters and setters.
}

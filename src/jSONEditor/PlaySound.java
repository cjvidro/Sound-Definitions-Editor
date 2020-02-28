package jSONEditor;

public class PlaySound {

	String directory;
	boolean stream;
	double pitch;
	double volume;
	boolean load_on_low_mem;
	int index;
	
	String getDirectory() { 
		return directory;
	}
	
	void setDirectory(String directory) { 
		this.directory = directory; 
	}
	
	boolean getStream() { 
		return stream; 
	}
	
	void setStream(boolean stream) { 
		this.stream = stream; 
	}
	
	double getPitch() { 
		return pitch; 
	}
	
	void setPitch(double pitch) {
		this.pitch = pitch;
	}
	
	double getVolume() {
		return volume;
	}
	
	void setVolume(double volume) {
		this.volume = volume;
	}
	
	boolean getLoad() {
		return load_on_low_mem;
	}
	
	void setLoad(boolean load_on_low_mem) {
		this.load_on_low_mem = load_on_low_mem;
	}
	
	void setIndex(int index) {
		this.index = index;
	}
}

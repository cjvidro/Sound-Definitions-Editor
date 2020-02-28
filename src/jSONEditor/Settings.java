package jSONEditor;

public class Settings {
	
	boolean auto = true;
	boolean lolm = true;
	boolean web = false;
	
	boolean getAuto() {
		return auto;
	}
	
	boolean getLOLM() {
		return lolm;
	}
	
	void toggleAutoSave() {
		
		if(auto)
			auto = false;
		else
			auto = true;
	}
	
	void toggleAlternateLoadMem() {
		
		if(lolm)
			lolm = false;
		else
			lolm = true;
	}
	
	void allowWebBackup() {
		
		if(web)
			web = false;
		else
			web = true;
	}
	
	//TO DO
	void help() {
		
	}
}

package jSONEditor;

public class Settings {
	
	boolean auto = true;
	boolean lolm = true;
	boolean web = false;
	
	public boolean getAuto() {
		return auto;
	}
	
	public boolean getLOLM() {
		return lolm;
	}
	
	public boolean getWeb() {
		return web;
	}
	
	public void toggleAutoSave() {
		auto = !auto;
	}
	
	public void toggleAlternateLoadMem() {
		lolm = !lolm;
	}
	
	public void allowWebBackup() {
		web = !web;
	}
	
	//TO DO
	public void help() {
		
	}
}

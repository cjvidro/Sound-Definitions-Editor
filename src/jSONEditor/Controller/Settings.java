package jSONEditor.Controller;

public class Settings {
	
	boolean autoBackup = true;
	boolean lolm = true;
	boolean web = false;
	
	public boolean getAutoBackup() {
		return autoBackup;
	}
	
	public boolean getLOLM() {
		return lolm;
	}
	
	public boolean getWeb() {
		return web;
	}
	
	public void toggleAutoSave() {
		autoBackup = !autoBackup;
	}
	
	public void toggleAlternateLoadMem() {
		lolm = !lolm;
	}
	
	public void allowWebBackup() {
		web = !web;
	}
}

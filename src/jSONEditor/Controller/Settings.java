package jSONEditor.Controller;

public class Settings {
	
	boolean autoBackup = true;
	boolean lolm = true;
	
	public boolean getAutoBackup() {
		return autoBackup;
	}
	
	public boolean getLOLM() {
		return lolm;
	}
	
	public void toggleAutoSave() {
		autoBackup = !autoBackup;
	}
	
	public void toggleAlternateLoadMem() {
		lolm = !lolm;
	}
}

package jSONEditor.Controller;

import java.io.Serializable;

public class Template implements Serializable {
	//Name to distinguish Templates
	private String name;
	
    // Playsound defaults
    private Category category; // category of sound

    // Sound defaults
    private Boolean stream; // stream property
    private Double volume; // volume property
    private Boolean lolm; // load_on_low_memory property

    // Other:
    private int LOLMSetting = 0; // 0: auto true; 1: auto false; 2: First true remaining are false; 3: Alternate starting with true;
    private boolean first = true; // Helper for detectLOLMSetting. This will help when the LOLMSetting is set to 2 or 3.
    
    /*
     * Sets all default playsounds for user.
     */
	public void setDefaultPlaysound(Playsound playsound) {
    	playsound.setCategory(getDefaultCategory());
    }
    
    /*
     * Sets all default sounds for user.
     */
	public void setDefaultSound(Sound sound) {
    	sound.setStream(getDefaultStream());
    	sound.setVolume(getDefaultVolume());
    	sound.setLOLM(detectLOLMSetting());
    }
    
    /*
     * This method is to help with the LOLMSetting. This will return the desired result with the inputed setting.
     */
	public Boolean detectLOLMSetting() {
    	if(getLOLMSetting() == 0)
    	{
    		return true;
    	}
    	else if(getLOLMSetting() == 1)
    	{
    		return false;
    	}
    	else if(getLOLMSetting() == 2)
    	{
    		if(first)
    		{
    			first = false;
    			
    			return true;
    		}
    		
    		return false;
    	}
    	else
    	{
    		if(first)
    		{
    			setDefaultLOLM(true);
    			
    			first = false;
    			
    			return getDefaultLOLM();
    		}
    		
    		setDefaultLOLM(!getDefaultLOLM());
    		
    		return getDefaultLOLM();
    	}
    }
    
    /*
     * Getters and Setters
     */
	public Category getDefaultCategory() {
		return category;
	}

	public void setDefaultCategory(Category category) {
		this.category = category;
	}

	public Boolean getDefaultStream() {
		return stream; 
	}

	public void setDefaultStream(Boolean stream) {
		this.stream = stream; 
	}

	public Double getDefaultVolume() {
		return volume;
	}

	public void setDefaultVolume(Double volume) {
		this.volume = volume;
	}

	private Boolean getDefaultLOLM() {
		return lolm;
	}

	private void setDefaultLOLM(Boolean lolm) {
		this.lolm = lolm;
	}

	public int getLOLMSetting()
	{
		return LOLMSetting;
	}

	public void setLOLMSetting(int LOLMSetting)
	{
		this.LOLMSetting = LOLMSetting;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

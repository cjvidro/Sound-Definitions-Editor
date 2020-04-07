package jSONEditor.Controller;

public class Template {
	//Name to distinguish Templates
	private String name;
	
    // Playsound defaults
    private Double min_distance; // min_distance property
    private Double max_distance; // max_distance property
    private Category category; // category of sound

    // Sound defaults
    private Boolean stream; // stream property
    private Double pitch; // pitch property
    private Double volume; // volume property
    private Boolean lolm; // load_on_low_memory property

    // Other:
    private int LOLMSetting = 0; // 0: No effect; 1: auto true; 2: auto false; 3: First true remaining are false; 4: Alternate starting with true; 5. Alternate starting with false.
    private boolean first = true; // Helper for detectLOLMSetting. This will help when the LOLMSetting is set to 3, 4, or 5.
    
    /*
     * Sets all default playsounds for user.
     */
	public void setDefaultPlaysound(Playsound playsound) {
    	playsound.setMin(getDefaultMin());
    	playsound.setMax(getDefaultMax());
    	playsound.setCategory(getDefaultCategory());
    }
    
    /*
     * Sets all default sounds for user.
     */
	public void setDefaultSound(Sound sound) {
    	sound.setStream(getDefaultStream());
    	sound.setPitch(getDefaultPitch());
    	sound.setVolume(getDefaultVolume());
    	sound.setLOLM(detectLOLMSetting());
    }
    
    /*
     * This method is to help with the LOLMSetting. This will return the desired result with the inputed setting.
     */
	public Boolean detectLOLMSetting() {
    	if(getLOLMSetting() == 0)
    	{
    		return getDefaultLOLM();
    	}
    	else if(getLOLMSetting() == 1)
    	{
    		return true;
    	}
    	else if(getLOLMSetting() == 2)
    	{
    		return false;
    	}
    	else if(getLOLMSetting() == 3)
    	{
    		if(first)
    		{
    			first = false;
    			
    			return true;
    		}
    		
    		return false;
    	}
    	else if(getLOLMSetting() == 4)
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
    	else
    	{
    		if(first)
    		{
    			setDefaultLOLM(false);
    			
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

	public Double getDefaultMin() {
		return min_distance;
	}

	public void setDefaultMin(Double min_distance) {
		this.min_distance = min_distance;
	}

	public Double getDefaultMax() {
		return max_distance;
	}

	public void setDefaultMax(Double max_distance) {
		this.max_distance = max_distance;
	}

	public Boolean getDefaultStream() {
		return stream; 
	}

	public void setDefaultStream(Boolean stream) {
		this.stream = stream; 
	}

	public Double getDefaultPitch() {
		return pitch; 
	}

	public void setDefaultPitch(Double pitch) {
		this.pitch = pitch;
	}

	public Double getDefaultVolume() {
		return volume;
	}

	public void setDefaultVolume(Double volume) {
		this.volume = volume;
	}

	public Boolean getDefaultLOLM() {
		return lolm;
	}

	public void setDefaultLOLM(Boolean lolm) {
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

	public String getString() {
		return name;
	}
}

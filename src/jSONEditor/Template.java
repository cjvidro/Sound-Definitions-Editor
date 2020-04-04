package jSONEditor;

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
    protected void setDefaultPlaysound(Playsound playsound) {
    	playsound.setMin(getDefaultMin());
    	playsound.setMax(getDefaultMax());
    	playsound.setCategory(getDefaultCategory());
    }
    
    /*
     * Sets all default sounds for user.
     */
    protected void setDefaultSound(Sound sound) {
    	sound.setStream(getDefaultStream());
    	sound.setPitch(getDefaultPitch());
    	sound.setVolume(getDefaultVolume());
    	sound.setLOLM(detectLOLMSetting());
    }
    
    /*
     * This method is to help with the LOLMSetting. This will return the desired result with the inputed setting.
     */
    protected Boolean detectLOLMSetting() {
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
    protected Category getDefaultCategory() {
		return category;
	}

	protected void setDefaultCategory(Category category) {
		this.category = category;
	}

	protected Double getDefaultMin() {
		return min_distance;
	}

	protected void setDefaultMin(Double min_distance) {
		this.min_distance = min_distance;
	}

	protected Double getDefaultMax() {
		return max_distance;
	}

	protected void setDefaultMax(Double max_distance) {
		this.max_distance = max_distance;
	}
	
	protected Boolean getDefaultStream() {
		return stream; 
	}

	protected void setDefaultStream(Boolean stream) {
		this.stream = stream; 
	}

	protected Double getDefaultPitch() {
		return pitch; 
	}

	protected void setDefaultPitch(Double pitch) {
		this.pitch = pitch;
	}

	protected Double getDefaultVolume() {
		return volume;
	}

	protected void setDefaultVolume(Double volume) {
		this.volume = volume;
	}

	protected Boolean getDefaultLOLM() {
		return lolm;
	}

	protected void setDefaultLOLM(Boolean lolm) {
		this.lolm = lolm;
	}
	
	protected int getLOLMSetting()
	{
		return LOLMSetting;
	}
	
	protected void setLOLMSetting(int LOLMSetting)
	{
		this.LOLMSetting = LOLMSetting;
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	protected String getString() {
		return name;
	}
}

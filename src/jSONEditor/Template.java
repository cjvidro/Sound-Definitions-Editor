package jSONEditor;

public class Template {
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
    private int LOLMSetting = 0; // 0: auto false; 1: auto true; 2: Alternate; 3: First true remaining are false
}

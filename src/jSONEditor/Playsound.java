package jSONEditor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class Playsound {
	//Comments like this signal the starts and ends of related methods.
	
	/*
	 * Comments like this give extra information to specific methods.
	 */
	
	//Variable that the user will see.
	ArrayList<Sound> sound = new ArrayList<>();
	String name;
	Double min_distance;
	Double max_distance;
	Category category;
	int index = 0;

	//Default variables
	String defaultName = "Sound " + index;
	Double defaultMin = 0.0;
	Double defaultMax = 0.0;
	Category defaultCategory = Category.master;
	
	//Start default methods.
	void setDefaultVars(String name, Double min_distance, Double max_distance, Category category) {
		defaultName = name;
		defaultMin = min_distance;
		defaultMax = max_distance;
		defaultCategory = category;
	}
	
	String getDefaultName() {
		return defaultName;
	}
	
	Double getDefaultMax() {
		return defaultMax;
	}
	
	Double getDefaultMin() {
		return defaultMin;
	}
	
	Category getDefaultCategory() {
		return defaultCategory;
	}
	
	Sound getPlaysound(int index) {
		return sound.get(index);
	}
	
	void setDefaults() {
		name = getDefaultName();
		min_distance = getDefaultMin();
		max_distance = getDefaultMax();
		category = getDefaultCategory();
	}
	//End default methods
	
	//Start check null methods
	/*
	 * This will check if the name is null.
	 */
	boolean checkNullString(String string) {
		if(string == null)
			return true;
		return false;
	}
	
	/*'
	 * This will check if the max and min distances are null.
	 */
	boolean checkNullDoubles(Double distance) {
		if(distance == null)
			return true;
		return false;
	}
	
	/*
	 * This will check if the category is null.
	 */
	boolean checkNullCategory(Category cat) {
		if(cat == null)
			return true;
		return false;
	}
	//End check null methods
	
	//Start getters and setters
	void setPlaysound(String directory, Boolean stream, Double volume, Double pitch, Boolean load) {
		Sound play = new Sound();
		
		play.setDirectory(directory);
		play.setStream(stream);
		play.setVolume(volume);
		play.setPitch(pitch);
		play.setLoad(load);
		play.setIndex(index);
		index++;
		
		sound.add(play);
	}
	
	Category getCategory() {
		return category;
	}
	
	void setCategory(Category category) {
		this.category = category;
	}
	
	Double getMin() {
		return min_distance;
	}
	
	void setMin(Double min_distance) {
		this.min_distance = min_distance;
	}
	
	Double getMax() {
		return max_distance;
	}
	
	void setMax(Double max_distance) {
		this.max_distance = max_distance;
	}
	
	String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}
  
	void addPlaySound(String directory, boolean stream, double volume, double pitch, boolean load) {
		
		//new playsound to add to the sound arraylist
		Sound newSound = new Sound();
		
		//setting the values of the new playsounds with the input of the function
    	newSound.setDirectory(directory);
    	newSound.setStream(stream);
    	newSound.setVolume(volume);
    	newSound.setPitch(pitch);
    	newSound.setLoad(load);
    	
    	//adding the new playound to the sound arraylist
    	sound.add(newSound);
		
	}
	
	void readInAndAddPlaySounds(String filename) {
		
        try (FileReader reader = new FileReader(filename))
        {
            //JSON parser object to parse read file
            JSONParser jsonParser = new JSONParser();
            //Read JSON file
            JSONObject newPlaySound = (JSONObject) jsonParser.parse(reader);
 
            System.out.println(newPlaySound);
           
            // get an array from the JSON sound object
            JSONArray JSONplaysounds = (JSONArray) newPlaySound.get("sounds");
            
            //Create a new arraylist to place the playsounds from the JSON sound array
            ArrayList<Sound> newSounds = new ArrayList<>();
            
            // take the elements of the JSON sound array
            for (int i = 0; i < JSONplaysounds.size(); i++) {
            	
            	//temp objects to access functions and store values
            	JSONObject tempJSON = (JSONObject) JSONplaysounds.get(i);
            	Sound tempSounds = new Sound();
            	
            	//temp values
            	String tempDirectory = "";
            	boolean tempStream = false;
            	double tempVolume = 1.0;
            	double tempPitch = 1.0;
            	boolean tempLoad = true;
            	
            	//Pulling values from JSONObject to temp values
            	tempDirectory = (String) tempJSON.get("name");
            	tempStream = (boolean) tempJSON.get("stream");
            	tempVolume = (double) tempJSON.get("volume");
            	tempPitch = (double) tempJSON.get("pitch");
            	tempLoad = (boolean) tempJSON.get("load_on_low_mem");
            	
            	addPlaySound(tempDirectory, tempStream, tempVolume, tempPitch, tempLoad);
            }
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
	}
	
	void readInAndReplacePlaySounds(String filename) {
		
        try (FileReader reader = new FileReader(filename))
        {
            //JSON parser object to parse read file
            JSONParser jsonParser = new JSONParser();
            //Read JSON file
            JSONObject newPlaySound = (JSONObject) jsonParser.parse(reader);
 
            System.out.println(newPlaySound);
           
            // get an array from the JSON sound object
            JSONArray JSONplaysounds = (JSONArray) newPlaySound.get("sounds");
            
            //Create a new arraylist to place the playsounds from the JSON sound array
            ArrayList<Sound> newSounds = new ArrayList<>();
            
            // take the elements of the JSON sound array
            for (int i = 0; i < JSONplaysounds.size(); i++) {
            	
            	//temp objects to access functions and store values
            	JSONObject tempJSON = (JSONObject) JSONplaysounds.get(i);
            	Sound tempSounds = new Sound();
            	
            	//temp values
            	String tempDirectory = "";
            	boolean tempStream = false;
            	double tempVolume = 1.0;
            	double tempPitch = 1.0;
            	boolean tempLoad = true;
            	
            	//Pulling values from JSONObject to temp values
            	tempDirectory = (String) tempJSON.get("name");
            	tempStream = (boolean) tempJSON.get("stream");
            	tempVolume = (double) tempJSON.get("volume");
            	tempPitch = (double) tempJSON.get("pitch");
            	tempLoad = (boolean) tempJSON.get("load_on_low_mem");
            	
            	//setting temp Sound with temp values
            	tempSounds.setDirectory(tempDirectory);
            	tempSounds.setStream(tempStream);
            	tempSounds.setVolume(tempVolume);
            	tempSounds.setPitch(tempPitch);
            	tempSounds.setLoad(tempLoad);
            	
            	//adding temp playsound to the newSounds arraylist
            	newSounds.add(tempSounds);
            }
            
            //make the sounds array equal to the new playsounds
            sound = newSounds;
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
	}
	
	void readInSoundFile(String filename) {
         
        try (FileReader reader = new FileReader(filename))
        {
            //JSON parser object to parse read file
            JSONParser jsonParser = new JSONParser();
            //Read JSON file
            JSONObject newSound = (JSONObject) jsonParser.parse(reader);
 
            System.out.println(newSound);
            
            // get the category from the JSON sound object
            category = (Category) newSound.get("category");
            System.out.println("The category is: " + category);
 
            // get the min_distance from the JSON sound object
            min_distance = (double) newSound.get("min_distance");
            System.out.println("The min_distance is: " + min_distance);
            
            // get the max_distance from the JSON sound object
            max_distance = (double) newSound.get("max_distance");
            System.out.println("The max_distance is: " + max_distance);
           
            // get an array from the JSON sound object
            JSONArray JSONplaysounds = (JSONArray) newSound.get("sounds");
            
            //Create a new arraylist to place the playsounds from the JSON sound array
            ArrayList<Sound> newSounds = new ArrayList<>();
            
            // take the elements of the JSON sound array
            for (int i = 0; i < JSONplaysounds.size(); i++) {
            	
            	//temp objects to access functions and store values
            	JSONObject tempJSON = (JSONObject) JSONplaysounds.get(i);
            	Sound tempSounds = new Sound();
            	
            	//temp values
            	String tempDirectory = "";
            	boolean tempStream = false;
            	double tempVolume = 1.0;
            	double tempPitch = 1.0;
            	boolean tempLoad = true;
            	
            	//Pulling values from JSONObject to temp values
            	tempDirectory = (String) tempJSON.get("name");
            	tempStream = (boolean) tempJSON.get("stream");
            	tempVolume = (double) tempJSON.get("volume");
            	tempPitch = (double) tempJSON.get("pitch");
            	tempLoad = (boolean) tempJSON.get("load_on_low_mem");
            	
            	//setting temp Sound with temp values
            	tempSounds.setDirectory(tempDirectory);
            	tempSounds.setStream(tempStream);
            	tempSounds.setVolume(tempVolume);
            	tempSounds.setPitch(tempPitch);
            	tempSounds.setLoad(tempLoad);
            	
            	//adding temp playsound to the newSounds arraylist
            	newSounds.add(tempSounds);
            }
            
            //make the sounds array equal to the new playsounds
            sound = newSounds;
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
	}
	
	void writeOutSoundFile() {
        
        //JSON Sound file
        JSONObject soundFile = new JSONObject();
        
        //put current variable into JSONObject
        soundFile.put("category", category);
        soundFile.put("min_distance", min_distance);
        soundFile.put("max_distance", max_distance);
        soundFile.put("sounds", sound);
		
        //Write JSON file
        try (FileWriter file = new FileWriter("soundFile.json")) {
 
            file.write(soundFile.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

}

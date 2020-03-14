package jSONEditor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class Sounds {
	
	ArrayList<PlaySound> sound = new ArrayList<>();
	String name;
	double min_distance;
	double max_distance;
	Category category;
	int index = 0;
	
	String defaultName = "Sound " + index;
	double defaultMin = 0;
	double defaultMax = 0;
	Category defaultCategory = Category.master;
	
	void setDefaults(String name, double min_distance, double max_distance, Category category) {
		defaultName = name;
		defaultMin = min_distance;
		defaultMax = max_distance;
		defaultCategory = category;
	}
	
	PlaySound getPlaysound(int index) {
		return sound.get(index);
	}
	
	void setPlaysound(String directory, boolean stream, double volume, double pitch, boolean load) {
		PlaySound play = new PlaySound();
		
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
	
	double getMin() {
		return min_distance;
	}
	
	void setMin(double min_distance) {
		this.min_distance = min_distance;
	}
	
	double getMax() {
		return max_distance;
	}
	
	void setMax(double max_distance) {
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
		PlaySound newPlaySound = new PlaySound();
		
		//setting the values of the new playsounds with the input of the function
    	newPlaySound.setDirectory(directory);
    	newPlaySound.setStream(stream);
    	newPlaySound.setVolume(volume);
    	newPlaySound.setPitch(pitch);
    	newPlaySound.setLoad(load);
    	
    	//adding the new playound to the sound arraylist
    	sound.add(newPlaySound);
		
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
            ArrayList<PlaySound> newPlaySounds = new ArrayList<>();
            
            // take the elements of the JSON sound array
            for (int i = 0; i < JSONplaysounds.size(); i++) {
            	
            	//temp objects to access functions and store values
            	JSONObject tempJSON = (JSONObject) JSONplaysounds.get(i);
            	PlaySound tempPlaySounds = new PlaySound();
            	
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
            ArrayList<PlaySound> newPlaySounds = new ArrayList<>();
            
            // take the elements of the JSON sound array
            for (int i = 0; i < JSONplaysounds.size(); i++) {
            	
            	//temp objects to access functions and store values
            	JSONObject tempJSON = (JSONObject) JSONplaysounds.get(i);
            	PlaySound tempPlaySounds = new PlaySound();
            	
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
            	
            	//setting temp PlaySound with temp values
            	tempPlaySounds.setDirectory(tempDirectory);
            	tempPlaySounds.setStream(tempStream);
            	tempPlaySounds.setVolume(tempVolume);
            	tempPlaySounds.setPitch(tempPitch);
            	tempPlaySounds.setLoad(tempLoad);
            	
            	//adding temp playsound to the newPlaySounds arraylist
            	newPlaySounds.add(tempPlaySounds);
            }
            
            //make the sounds array equal to the new playsounds
            sound = newPlaySounds;
 
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
            ArrayList<PlaySound> newPlaySounds = new ArrayList<>();
            
            // take the elements of the JSON sound array
            for (int i = 0; i < JSONplaysounds.size(); i++) {
            	
            	//temp objects to access functions and store values
            	JSONObject tempJSON = (JSONObject) JSONplaysounds.get(i);
            	PlaySound tempPlaySounds = new PlaySound();
            	
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
            	
            	//setting temp PlaySound with temp values
            	tempPlaySounds.setDirectory(tempDirectory);
            	tempPlaySounds.setStream(tempStream);
            	tempPlaySounds.setVolume(tempVolume);
            	tempPlaySounds.setPitch(tempPitch);
            	tempPlaySounds.setLoad(tempLoad);
            	
            	//adding temp playsound to the newPlaySounds arraylist
            	newPlaySounds.add(tempPlaySounds);
            }
            
            //make the sounds array equal to the new playsounds
            sound = newPlaySounds;
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
	}

}

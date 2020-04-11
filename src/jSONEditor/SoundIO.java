package jSONEditor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import jdk.nashorn.internal.parser.JSONParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SoundIO {
	EditorData editorData = EditorData.getInstance();



	//TO DO
	boolean importFile(String file) {
		return false;
	}

	//TO DO
	boolean export() {
		return false;
	}

	//TO DO
	boolean validateFile(String file) {
		return false;
	}
	
	protected void readInPlaySound(String filename) {
		try (FileReader reader = new FileReader(filename))
        {
			//JSON parser object to parse read file
			JSONParser jsonParser = new JSONParser(filename, null, false);
			//Read JSON file
			JSONObject newPlaySound = (JSONObject) jsonParser.parse();
        
			EditorData instance = EditorData.getInstance();
        
			// get the category from the JSON sound object
			Category category = (Category) newPlaySound.get("category");
			System.out.println("The category is: " + category);	
        
			// get the min_distance from the JSON sound object
			Double min_distance = (Double) newPlaySound.get("min_distance");
			System.out.println("The min_distance is: " + min_distance);	
        
			// get the max_distance from the JSON sound object
			Double max_distance = (Double) newPlaySound.get("max_distance");
			System.out.println("The max_distance is: " + max_distance);	
       
			Playsound playsound = new Playsound();
			
			playsound.setName(filename);
			playsound.setCategory(category);
			playsound.setMax(max_distance);
			playsound.setMin(min_distance);
			
			instance.playsounds.add(playsound);
			
			// get an array from the JSON sound object
			JSONArray JSONplaysounds = (JSONArray) newPlaySound.get("sounds");
        
			// take the elements of the JSON sound array
			for (int i = 0; i < JSONplaysounds.size(); i++) {
        	
				//temp objects to access functions and store values
				JSONObject tempJSON = (JSONObject) JSONplaysounds.get(i);
        	
				//temp values
				String directory = "";
				Boolean stream = false;
				Double volume = 1.0;
				Double pitch = 1.0;
				Boolean lolm = true;
        	
				//Pulling values from JSONObject to temp values
				directory = (String) tempJSON.get("name");
				stream = (Boolean) tempJSON.get("stream");
				volume = (Double) tempJSON.get("volume");
				pitch = (Double) tempJSON.get("pitch");
				lolm = (Boolean) tempJSON.get("load_on_low_mem");
        	
				//setting temp PlaySound with temp values
				instance.playsounds.get(instance.playsounds.size() - 1).addSound(directory, stream, pitch, volume, lolm);
			}

        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (ParseException e) {
        	e.printStackTrace();
        }
	
	}

	protected boolean writePlaysounds() {
		System.out.print("ran");

		// the master object that holds everything
		JSONObject master = new JSONObject();

		// create each playsound to add to master
		for (Playsound playsound : editorData.playsounds) {
			
			
			// playsound attributes
			JSONObject playsoundDetails = new JSONObject();

			if (playsound.getMin() != null) {
				playsoundDetails.put("min_distance", playsound.getMin());
			}
			if (playsound.getMax() != null) {
				playsoundDetails.put("max_distance", playsound.getMax());
			}
			if (playsound.getCategory() != null) {
				playsoundDetails.put("category", playsound.getCategory().name());
			}

			// populate the sounds array
			JSONArray playsoundSoundsArray = new JSONArray();
			for (Sound sound : playsound.sounds) {
				JSONObject soundObject = new JSONObject();
				soundObject.put("name", "sounds/" + sound.getDirectory());
				soundObject.put("stream", sound.getStream());
				if (sound.getPitch() != null) {
					soundObject.put("pitch", sound.getPitch());
				}
				if (sound.getVolume() != null) {
					soundObject.put("volume", sound.getVolume());
				}
				soundObject.put("load_on_low_memory", sound.getLOLM());

				playsoundSoundsArray.add(soundObject);
			}

			playsoundDetails.put("sounds", playsoundSoundsArray);

			// add to the master object
			master.put(playsound.getName(), playsoundDetails);
		}

		// Write JSON file
		try (FileWriter file = new FileWriter("sound_definitions.json")) {

			file.write(toPrettyFormat(master.toJSONString()));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private String toPrettyFormat(String jsonString)
	{
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(jsonString).getAsJsonObject();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(json);

		return prettyJson;
	}

}

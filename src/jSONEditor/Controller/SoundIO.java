package jSONEditor.Controller;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SoundIO {
	public static boolean readInPlaySound(String filePath) {
		try (FileReader reader = new FileReader(filePath)) {

			//JSON parser object to parse read file
			JSONParser jsonParser = new JSONParser();

			//Read JSON file
			JSONObject newPlaySound = (JSONObject) jsonParser.parse(reader);
			
			//Get an instance of EditorData
			EditorData instance = EditorData.getInstance();
			
			for(Object ps: newPlaySound.keySet()) {
				//System.out.println("Key = " + ps + " Value = " + newPlaySound.get(ps));
				//Create a new Playsound
				Playsound playsound = new Playsound();
				
				//Instantiate playsound with nulls.
				playsound.setCategory(null);
				playsound.setMax(null);
				playsound.setMin(null);
				playsound.setName(ps.toString());
				
				//Add the new playsound
				instance.playsounds.add(playsound);
				
				JSONObject next = (JSONObject) jsonParser.parse(newPlaySound.get(ps).toString());
				
				for(Object ps2: next.keySet())
				{
					//System.out.println("Key = " + ps2 + " Value = " + next.get(ps2));
					if(ps2.toString().equals("category"))
					{
						//Set Category
						if(next.get(ps2).toString().equals("master"))
						{
							instance.playsounds.get(instance.playsounds.size() - 1).setCategory(Category.master);
						}
						else if(next.get(ps2).toString().equals("ui"))
						{
							instance.playsounds.get(instance.playsounds.size() - 1).setCategory(Category.ui);
						}
						else if(next.get(ps2).toString().equals("music"))
						{
							instance.playsounds.get(instance.playsounds.size() - 1).setCategory(Category.music);
						}
						else if(next.get(ps2).toString().equals("weather"))
						{
							instance.playsounds.get(instance.playsounds.size() - 1).setCategory(Category.weather);
						}
						else if(next.get(ps2).toString().equals("neutral"))
						{
							instance.playsounds.get(instance.playsounds.size() - 1).setCategory(Category.neutral);
						}
						else if(next.get(ps2).toString().equals("player"))
						{
							instance.playsounds.get(instance.playsounds.size() - 1).setCategory(Category.player);
						}
						else if(next.get(ps2).toString().equals("hostile"))
						{
							instance.playsounds.get(instance.playsounds.size() - 1).setCategory(Category.hostile);
						}
					}
					else if(ps2.toString().equals("min_distance"))
					{
						instance.playsounds.get(instance.playsounds.size() - 1).setMin((Double) next.get(ps2));
					}
					else if(ps2.toString().equals("max_distance"))
					{
						//Set Min_distance
						instance.playsounds.get(instance.playsounds.size() - 1).setMax((Double) next.get(ps2));
					}
					else if(ps2.toString().equals("sounds"))
					{
						// get an array from the JSON sound object
						JSONArray newSound = (JSONArray) jsonParser.parse(next.get(ps2).toString());
						
						//temp values
						String directory = null;
						Boolean stream = null;
						Double volume = null;
						Double pitch = null;
						Boolean lolm = null;
						
						for(int i = 0; i < newSound.size(); i++) 
						{
							JSONObject obj = (JSONObject) jsonParser.parse(newSound.get(i).toString());
							
							for(Object s: obj.keySet())
							{
								if(s.toString().equals("name"))
								{
									directory = obj.get(s).toString();
								}
								else if(s.toString().equals("stream"))
								{
									stream = (Boolean) obj.get(s);
								}
								else if(s.toString().equals("volume"))
								{
									volume = (Double) obj.get(s);
								}
								else if(s.toString().equals("pitch"))
								{
									pitch = (Double) obj.get(s);
								}
								else if(s.toString().equals("load_on_low_memory"))
								{
									lolm = (Boolean) obj.get(s);
								}
							}
							
							//setting playsound with all values
							instance.playsounds.get(instance.playsounds.size() - 1).addSound(directory, stream, pitch, volume, lolm);
						}	
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Failed to import sound definition file!");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to import sound definition file!");
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Failed to import sound definition file!");
			return false;
		}

		System.out.println("Successfully imported sound definition file!");
		return true;
	}

	public static boolean importSoundDefinitions() {
		// select the sound_definitions file
		File sound_definitions = chooseFile();

		if (sound_definitions == null) {
			System.out.println("Failed to import sound definition file!");
			return false;
		}

		return readInPlaySound(sound_definitions.getAbsolutePath());
	}

	public static boolean loadSoundDefinitions(File save) {
		return readInPlaySound(save.getAbsolutePath() + "/sound_definitions.json");
	}

	private static File chooseFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON","*.json"));
		return fileChooser.showOpenDialog(new Stage());
	}


	public static boolean writePlaysounds() {
		if (EditorData.getInstance().currentDirectory == null) {
			// use default directory
			return writePlaysounds(new File(""));
		} else {
			return writePlaysounds(EditorData.getInstance().currentDirectory);
		}
	}

	private static boolean writePlaysounds(File saveDirectory) {
		// the master object that holds everything
		JSONObject master = new JSONObject();

		// create each playsound to add to master
		for (Playsound playsound : EditorData.getInstance().playsounds) {
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
		try (FileWriter file = new FileWriter(saveDirectory + "/" + "sound_definitions.json")) {

			file.write(toPrettyFormat(master.toJSONString()));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private static String toPrettyFormat(String jsonString)
	{
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(jsonString).getAsJsonObject();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(json);

		return prettyJson;
	}

	private static File chooseDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		if (EditorData.getInstance().currentDirectory != null) {
			directoryChooser.setInitialDirectory(EditorData.getInstance().currentDirectory);
		} else {
			directoryChooser.setInitialDirectory(new File("."));
		}
		return directoryChooser.showDialog(new Stage());
	}

	public static boolean saveProjectAs() {
		// get the new file folder
		File selectedDirectory = chooseDirectory();

		if (selectedDirectory == null) {
			System.out.println("Failed to save project!");
			return false;
		}

		// Check if this save already exists
		File[] array = EditorData.getInstance().saves;
		for (File file : array) {
			if (file != null && selectedDirectory.getAbsoluteFile().equals(file.getAbsoluteFile())) {
				System.out.println("A project with this file path already exists!");
				return false;
			}
		}

		// create backup folder
		File backupsFolder = new File(selectedDirectory.getAbsolutePath() + "/Backups");
		backupsFolder.mkdir();

		// set the current directory
		EditorData.getInstance().currentDirectory = selectedDirectory;

		// save the save directory
		for (int i = 3; i >= 0; i--) {
			// shift current saves
			EditorData.getInstance().saves[i + 1] = array[i];
		}

		EditorData.getInstance().saves[0] = selectedDirectory;
		EditorData.serializeSaves();

		// save sound_defintions file
		writePlaysounds(selectedDirectory);

		/*
		Save changelog
		 */

		System.out.println("Saved project as " + selectedDirectory.getName());
		return true;
	}
	
	public static File selectedDirectoryName(){
		// get the new file folder
		File selectedDirectory = chooseDirectory();

		if (selectedDirectory == null) {
			System.out.println("Failed to export!");
			return null;
		}

		return selectedDirectory;
	}
	
	public static boolean exportPlaysounds(File selectedDirectory) {
		if (selectedDirectory == null) {
			// use default directory
			return writePlaysounds(new File(""));
		} else {
			return writePlaysounds(selectedDirectory);
		}
	}

	public static boolean saveProject() {
		File selectedDirectory = EditorData.getInstance().currentDirectory;

		if (selectedDirectory == null) {
			return saveProjectAs();
		}

		// create / move backups
		File save5 = new File(selectedDirectory.getAbsolutePath() + "/Backups/sound_definitions_5.json");
		save5.delete();
		File save4 = new File(selectedDirectory.getAbsolutePath() + "/Backups/sound_definitions_4.json");
		save4.renameTo(save5);
		File save3 = new File(selectedDirectory.getAbsolutePath() + "/Backups/sound_definitions_3.json");
		save3.renameTo(save4);
		File save2 = new File(selectedDirectory.getAbsolutePath() + "/Backups/sound_definitions_2.json");
		save2.renameTo(save3);
		File save1 = new File(selectedDirectory.getAbsolutePath() + "/Backups/sound_definitions_1.json");
		save1.renameTo(save2);
		File lastSave = new File(selectedDirectory.getAbsolutePath() + "/sound_definitions.json");
		lastSave.renameTo(save1);

		// save sound_defintions file
		writePlaysounds(selectedDirectory);

		/*
		Save changelog
		 */

		// Backup to server
		MySQLAccess access = new MySQLAccess();
		access.uploadSoundDef(lastSave);

		System.out.println("Saved project " + selectedDirectory.getName());
		return true;
	}
}

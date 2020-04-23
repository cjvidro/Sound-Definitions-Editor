package jSONEditor.Tests;

import jSONEditor.Controller.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SoundIOTest {
	@Test
    public void testWritePlaysounds() throws IOException {
        // setup stuff
        EditorData editorData = EditorData.getInstance();
        editorData.playsounds = new ArrayList<>();

        Playsound playsound = new Playsound();
        playsound.setName("testName");
        playsound.setMin(new Double(1.3));
        playsound.setMax(new Double(0.6));
        playsound.setCategory(Category.master);

        Sound sound = new Sound();
        sound.setPlaysound(playsound);
        sound.setDirectory("testDirectory/testSound");
        sound.setStream(true);
        sound.setPitch(new Double(4.2));
        sound.setVolume(new Double(1.2));
        sound.setLOLM(false);
        sound.setIndex(1);

        playsound.sounds.add(sound);
        editorData.playsounds.add(playsound);

        // perform the test
        assertTrue(SoundIO.writePlaysounds());

        // check the read
        BufferedReader reader1 = new BufferedReader(new FileReader("./sound_definitions.json"));
        BufferedReader reader2 = new BufferedReader(new FileReader("./src/jSONEditor/Tests/TEST_sound_definitions.json"));

        String line1 = reader1.readLine();
        String line2 = reader2.readLine();

        boolean areEqual = true;
        int lineNum = 1;
        while (line1 != null || line2 != null) {
            if(line1 == null || line2 == null) {
                areEqual = false;
                break;
            }
            else if(! line1.equalsIgnoreCase(line2)) {
                areEqual = false;
                break;
            }

            line1 = reader1.readLine();
            line2 = reader2.readLine();
            lineNum++;
        }

        reader1.close();
        reader2.close();

        assertTrue(areEqual);
    }
    
    @Test
    public void testReadPlaysounds() {
    	SoundIO soundio = new SoundIO();
    	
    	EditorData instance = EditorData.getInstance();
    	
    	soundio.readInPlaySound("./src/jSONEditor/Tests/TEST_sound_definitions.json");
    	
    	assertEquals("testName", instance.playsounds.get(instance.playsounds.size() - 1).getName());
    	assertEquals((Double) 1.3, instance.playsounds.get(instance.playsounds.size() - 1).getMin());
    	assertEquals(Category.master, instance.playsounds.get(instance.playsounds.size() - 1).getCategory());
    	assertEquals((Double) 0.6, instance.playsounds.get(instance.playsounds.size() - 1).getMax());
    	assertEquals((Double) 1.2, instance.playsounds.get(instance.playsounds.size() - 1).sounds.get(instance.playsounds.get(instance.playsounds.size() - 1).sounds.size() - 1).getVolume());
    	assertEquals((Boolean) true, instance.playsounds.get(instance.playsounds.size() - 1).sounds.get(instance.playsounds.get(instance.playsounds.size() - 1).sounds.size() - 1).getStream());
    	assertEquals((Boolean) false, instance.playsounds.get(instance.playsounds.size() - 1).sounds.get(instance.playsounds.get(instance.playsounds.size() - 1).sounds.size() - 1).getLOLM());
    	assertEquals("sounds/testDirectory/testSound", instance.playsounds.get(instance.playsounds.size() - 1).sounds.get(instance.playsounds.get(instance.playsounds.size() - 1).sounds.size() - 1).getDirectory());
    	assertEquals((Double) 4.2, instance.playsounds.get(instance.playsounds.size() - 1).sounds.get(instance.playsounds.get(instance.playsounds.size() - 1).sounds.size() - 1).getPitch());
    	
    	instance.playsounds.remove(instance.playsounds.size() - 1);
    	
    	assertEquals(0, instance.playsounds.size());
    }
    
    @Test
    public void testReadMultipleSounds() {
    	SoundIO soundio = new SoundIO();
    	
    	EditorData instance = EditorData.getInstance();
    	
    	soundio.readInPlaySound("./src/jSONEditor/Tests/TEST_multiple_sound_definitions.json");
    	
    	assertEquals("TEST_multiple_sounds", instance.playsounds.get(0).getName());
    	assertEquals((Double) 2.0, instance.playsounds.get(0).getMin());
    	assertEquals(Category.master, instance.playsounds.get(0).getCategory());
    	assertEquals((Double) 5.2, instance.playsounds.get(0).getMax());
    	assertEquals((Double) 55.0, instance.playsounds.get(0).sounds.get(0).getVolume());
    	assertEquals((Boolean) true, instance.playsounds.get(0).sounds.get(0).getStream());
    	assertEquals((Boolean) false, instance.playsounds.get(0).sounds.get(0).getLOLM());
    	assertEquals("sounds/thing/", instance.playsounds.get(0).sounds.get(0).getDirectory());
    	assertEquals((Double) 8.2, instance.playsounds.get(0).sounds.get(0).getPitch());
    	
    	
    	assertEquals((Double) 78.0, instance.playsounds.get(0).sounds.get(1).getVolume());
    	assertEquals((Boolean) false, instance.playsounds.get(0).sounds.get(1).getStream());
    	assertEquals((Boolean) true, instance.playsounds.get(0).sounds.get(1).getLOLM());
    	assertEquals("sounds/thing/", instance.playsounds.get(0).sounds.get(1).getDirectory());
    	assertEquals((Double) 20.0, instance.playsounds.get(0).sounds.get(1).getPitch());
    

    	instance.playsounds.remove(0);
    	
    	assertEquals(0, instance.playsounds.size());
    }
    
    @Test
    public void testReadMultiplePlaysounds() {
    	SoundIO soundio = new SoundIO();
    	
    	EditorData instance = EditorData.getInstance();
    	
    	soundio.readInPlaySound("./src/jSONEditor/Tests/TEST_multiple_playsounds.json");
    	
    	assertEquals("PS4", instance.playsounds.get(0).getName());
    	assertEquals(Category.weather, instance.playsounds.get(0).getCategory());
    	
    	assertEquals("PS3", instance.playsounds.get(1).getName());
    	assertEquals(Category.hostile, instance.playsounds.get(1).getCategory());
    	
    	assertEquals("PS6", instance.playsounds.get(2).getName());
    	assertEquals(Category.neutral, instance.playsounds.get(2).getCategory());
    	
    	assertEquals("PS5", instance.playsounds.get(3).getName());
    	assertEquals(Category.neutral, instance.playsounds.get(3).getCategory());
    	
    	assertEquals("PS7", instance.playsounds.get(4).getName());
    	assertEquals(Category.player, instance.playsounds.get(4).getCategory());
    	
    	assertEquals("PS2", instance.playsounds.get(5).getName());
    	assertEquals(Category.music, instance.playsounds.get(5).getCategory());
    	
    	assertEquals("PS1", instance.playsounds.get(6).getName());
    	assertEquals(Category.ui, instance.playsounds.get(6).getCategory());
    	
    	for(int i = 0; i < 5; i++)
    	{
    		assertEquals((Double) 1.0, instance.playsounds.get(i).sounds.get(0).getVolume());
        	assertEquals((Boolean) false, instance.playsounds.get(i).sounds.get(0).getStream());
        	assertEquals((Boolean) false, instance.playsounds.get(i).sounds.get(0).getLOLM());
        	assertEquals("sounds/sounds/sound", instance.playsounds.get(i).sounds.get(0).getDirectory());
        	assertEquals((Double) 1.0, instance.playsounds.get(i).sounds.get(0).getPitch());
    	}
    	for(int i = 5; i < 7; i++)
    	{
    		assertEquals((Double) 1.0, instance.playsounds.get(i).sounds.get(0).getVolume());
        	assertEquals((Boolean) true, instance.playsounds.get(i).sounds.get(0).getStream());
        	assertEquals((Boolean) true, instance.playsounds.get(i).sounds.get(0).getLOLM());
        	assertEquals("sounds/sounds/sound", instance.playsounds.get(i).sounds.get(0).getDirectory());
    	}
    	
    	for(int i = 6; i >= 0; i--)
    	{
    		instance.playsounds.remove(i);
    		assertEquals(i, instance.playsounds.size());
    	}
    }
}
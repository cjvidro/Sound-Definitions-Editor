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
        SoundIO soundIO = new SoundIO();
        assertTrue(soundIO.writePlaysounds());

        // check the read
        BufferedReader reader1 = new BufferedReader(new FileReader("sound_definitions.json"));
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
    public void testReadPlaysounds() throws Exception {
    	SoundIO soundio = new SoundIO();
    	
    	EditorData instance = EditorData.getInstance();
    	
    	soundio.readInPlaySound("./MHG/src/jSONEditor/Tests/TEST_sound_definitions.json");
    	
    	assertEquals("testName", instance.playsounds.get(instance.playsounds.size() - 1).getName());
    	assertEquals((Double) 1.3, instance.playsounds.get(instance.playsounds.size() - 1).getMin());
    	assertEquals(Category.master, instance.playsounds.get(instance.playsounds.size() - 1).getCategory());
    	assertEquals((Double) 0.6, instance.playsounds.get(instance.playsounds.size() - 1).getMax());
    	assertEquals((Double) 1.2, instance.playsounds.get(instance.playsounds.size() - 1).sounds.get(instance.playsounds.get(instance.playsounds.size() - 1).sounds.size() - 1).getVolume());
    	assertEquals((Boolean) true, instance.playsounds.get(instance.playsounds.size() - 1).sounds.get(instance.playsounds.get(instance.playsounds.size() - 1).sounds.size() - 1).getStream());
    	assertEquals((Boolean) false, instance.playsounds.get(instance.playsounds.size() - 1).sounds.get(instance.playsounds.get(instance.playsounds.size() - 1).sounds.size() - 1).getLOLM());
    	assertEquals("sounds/testDirectory/testSound", instance.playsounds.get(instance.playsounds.size() - 1).sounds.get(instance.playsounds.get(instance.playsounds.size() - 1).sounds.size() - 1).getDirectory());
    	assertEquals((Double) 4.2, instance.playsounds.get(instance.playsounds.size() - 1).sounds.get(instance.playsounds.get(instance.playsounds.size() - 1).sounds.size() - 1).getPitch());
    }
    
}
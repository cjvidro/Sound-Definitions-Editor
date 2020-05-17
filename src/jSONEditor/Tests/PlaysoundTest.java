package jSONEditor.Tests;

import jSONEditor.Controller.Category;
import jSONEditor.Controller.Playsound;
import jSONEditor.Controller.PlaysoundGroup;
import jSONEditor.Controller.Sound;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class PlaysoundTest {
    Playsound playsound;

    @Before
    public void before() {
        playsound = new Playsound();
    }

    @Test
    public void initial() {
        assertEquals(0, playsound.numberSounds());
        assertNull(playsound.getCategory());
        assertNull(playsound.getName());
    }

    @Test
    public void addSound() {
        playsound.addSound("/animals/cow", new File(""),true, null, false);
        assertEquals(1, playsound.numberSounds());

        Sound sound = playsound.getSound(0);
        assertEquals("/animals/cow", sound.getName());
        assertTrue(sound.getStream());
        assertNull(sound.getVolume());
        assertFalse(sound.getLOLM());
    }

    @Test
    public void removeSound() {
        playsound.addSound("/animals/cow", new File(""), true, null, false);
        assertEquals(1, playsound.numberSounds());
        playsound.removeSound(playsound.getSound(0));
        assertEquals(0, playsound.numberSounds());
    }

    @Test
    public void numberSounds() {
        assertEquals(0, playsound.numberSounds());
        for (int i = 1; i < 11; i++) {
            playsound.addSound("/animals/cow" + i, new File(""), true, null, false);
            assertEquals(i, playsound.numberSounds());
        }
    }

    @Test
    public void category() {
        playsound.setCategory(Category.master);
        assertEquals(Category.master, playsound.getCategory());
    }

    @Test
    public void name() {
        playsound.setName("cat");
        assertEquals("cat", playsound.getName());
    }

    @Test
    public void group() {
        PlaysoundGroup group = new PlaysoundGroup();
        group.setName("myGroup");
        assertEquals("myGroup", group.getName());

        playsound.setGroup(group);
        assertEquals(group, playsound.getGroup());

    }
}
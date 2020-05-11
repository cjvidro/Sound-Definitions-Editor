package jSONEditor.Tests;

import jSONEditor.Controller.Playsound;
import jSONEditor.Controller.Sound;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SoundTest {
    Sound sound;

    @Before
    public void setup() {
        sound = new Sound();
    }

    @Test
    public void initial() {
        assertNull(sound.getPlaysound());
        assertNull(sound.getDirectory());
        assertNull(sound.getStream());
        assertNull(sound.getVolume());
        assertNull(sound.getLOLM());
        assertEquals(0, sound.getIndex());
    }

    @Test
    public void getDirectory() {
        sound.setDirectory("cow/moo");
        assertEquals("cow/moo", sound.getDirectory());
    }

    @Test
    public void getStream() {
        sound.setStream(true);
        assertTrue(sound.getStream());
    }

    @Test
    public void getVolume() {
        sound.setVolume(3.4);
        assertEquals(new Double(3.4), sound.getVolume());
    }

    @Test
    public void getLOLM() {
        sound.setLOLM(true);
        assertTrue(sound.getLOLM());
    }

    @Test
    public void getIndex() {
        sound.setIndex(3);
        assertEquals(3, sound.getIndex());
    }

    @Test
    public void getPlaysound() {
        Playsound playsound = new Playsound();
        sound.setPlaysound(playsound);
        assertEquals(playsound, sound.getPlaysound());
    }
}
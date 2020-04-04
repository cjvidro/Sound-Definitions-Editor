package jSONEditor;

import java.util.ArrayList;

/**
 * This is a group of playsounds
 */
public class PlaysoundGroup {
    private String name;
    protected ArrayList<Playsound> playsounds = new ArrayList<>();

    protected void setName(String name) {
        this.name = name;
    }

    protected String getName() {
        return name;
    }
}

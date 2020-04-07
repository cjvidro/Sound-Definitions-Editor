package jSONEditor.Controller;

import java.util.ArrayList;

/**
 * This is a group of playsounds
 */
public class PlaysoundGroup {
    private String name;
    protected ArrayList<Playsound> playsounds = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

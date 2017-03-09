package com.santiagogil.a100units.model.pojos;

import android.graphics.Color;

public class Unit {

    private String ID;
    private String description;
    private Color color;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

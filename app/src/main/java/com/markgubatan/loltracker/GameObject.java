package com.markgubatan.loltracker;

import android.graphics.Bitmap;

/**
 * Data structure containing a game object's image and description.
 */
public class GameObject {
    private String name;
    private String description;

    public GameObject() {

    }

    public GameObject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

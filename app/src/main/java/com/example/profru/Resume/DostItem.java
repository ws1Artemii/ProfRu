package com.example.profru.Resume;

import android.graphics.drawable.Drawable;

public class DostItem {

    private String description;
    private Drawable image;
    private String image_path;

    public String getDescription() {
        return description;
    }

    public Drawable getImage() {
        return image;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public DostItem(Drawable _image, String _description) {
        image = _image;
        description = _description;
    }

    public DostItem(String compressed) {
        this(null, compressed.split("|")[1]);
        image_path = compressed.split("|")[0];
    }
}

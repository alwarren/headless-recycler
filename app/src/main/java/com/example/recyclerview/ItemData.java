package com.example.recyclerview;

public class ItemData {
    private String title;
    private int imageId;

    public ItemData(String title, int imageId) {

        this.title = title;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }
}
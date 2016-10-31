package com.codepath.apps.tripletweet.models;

import org.parceler.Parcel;

@Parcel
public class Media {

    public String type;
    public String mediaUrl;

    // empty constructor needed by the Parceler library
    public Media() {}

    public Media (String type, String mediaUrl){
        this.type = type;
        this.mediaUrl = mediaUrl;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPhoto() {
        return "photo".equals(type);
    }
}

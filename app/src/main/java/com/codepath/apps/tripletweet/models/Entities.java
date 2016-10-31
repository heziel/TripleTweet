package com.codepath.apps.tripletweet.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.Collections;
import java.util.List;

@Parcel
public class Entities {

    public Media media;

    // empty constructor needed by the Parceler library
    public Entities() {}

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public static Entities fromJSON(JSONObject jsonObject){
        Entities entities = new Entities();
      //  entities = null;
        int length = jsonObject.length();

        try {
            if (length > 4) {
                JSONArray jsonArray = jsonObject.getJSONArray("media");
                JSONObject multimediaJson = jsonArray.getJSONObject(0);
                String type = multimediaJson.getString("type");
                String media_url = multimediaJson.getString("media_url");
                entities.media = new Media(type, media_url);
            }
            else{
                entities.media = new Media("", "");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entities;

    }
}

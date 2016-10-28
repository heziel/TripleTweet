package com.codepath.apps.tripletweet.models;


import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String name;
    public   long uniqueId;
    public String screenName;
    public String profileImageUrl;

    public String getName() {
        return name;
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static User fromJSON(JSONObject jsonObject){
        User user = new User();

        // extract and fill the values
        try {
            user.name = jsonObject.getString("name");
            user.uniqueId = jsonObject.getLong("id  ");
            user.screenName = jsonObject.getString("");
            user.profileImageUrl =jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;

    }


}
package com.codepath.apps.tripletweet.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Tweet {

    public String body;
    public long unique_id; // unique id of the tweet
    public String createdAt;
    public User user;

    public void setUser(User user) {
        this.user = user;
    }

    // empty constructor needed by the Parceler library
    public Tweet() {}

    public String getBody() {
        return body;
    }

    public long getUnique_id() {
        return unique_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.unique_id = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }


    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){

        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i=0 ; i < jsonArray.length(); i++){

            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);

                if ( tweet != null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();

                // continue even if tweet failed
                continue;
            }
        }

        return tweets;
    }
}
package com.codepath.apps.tripletweet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.codepath.apps.tripletweet.R;
import com.codepath.apps.tripletweet.models.Tweet;
import com.codepath.apps.tripletweet.viewHolder.TweetViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;


public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetViewHolder>{

    private List<Tweet> tweetList;
    private Context context;


    public TweetsArrayAdapter(Context context, List<Tweet> tweetList){
        this.context = context;
        this.tweetList = tweetList;
    }

    public List<Tweet> getTweetList() {
        return tweetList;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TweetViewHolder viewHolder ;
        LayoutInflater inflater = LayoutInflater.from(context);

        View  tweetView =  inflater.inflate(R.layout.timeline_tripletweet_feed, parent,false);

        viewHolder = new TweetViewHolder(context,tweetView,tweetList);

        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Tweet tweet = tweetList.get(position);

       // String body = tweetList.get(position).getBody();

        holder.getTvTweetFeed().setText(tweet.getBody());

        holder.getTvName().setText(tweet.getUser().getName());

        holder.getTvTimeStamp().setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        // populate the picture.
        Glide.with(context).load(tweet.getUser().getProfileImageUrl())
                .placeholder(R.drawable.twitter_bird_logo)
                .into(holder.getIvProfilePic());

    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    /*
    * getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    */
    public String getRelativeTimeAgo(String rawJsonDate) {

        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    /* 2 methods For Pull to Refresh */

    // Clean all elements of the recycler
    public void clear() {
        tweetList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> tweets) {
        tweetList.addAll(tweets);
        notifyDataSetChanged();
    }



}

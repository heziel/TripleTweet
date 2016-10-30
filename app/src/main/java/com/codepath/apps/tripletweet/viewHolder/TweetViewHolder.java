package com.codepath.apps.tripletweet.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.tripletweet.R;
import com.codepath.apps.tripletweet.activity.DetailActivity;
import com.codepath.apps.tripletweet.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    @BindView(R.id.tvTweetFeed) TextView tvTweetFeed;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.ivProfilePic) ImageView ivProfilePic;
    @BindView(R.id.tvTimeStamp) TextView tvTimeStamp;
    @BindView(R.id.tvUserName) TextView tvUserName;

    private List<Tweet> tweetList;
    private Context context;

    // Getters

    public TextView getTvUserName() { return tvUserName; }

    public TextView getTvTimeStamp() { return tvTimeStamp; }

    public ImageView getIvProfilePic() { return ivProfilePic; }

    public TextView getTvName() { return tvName; }

    public TextView getTvTweetFeed() {
        return tvTweetFeed;
    }

    // ViewHolder Constructor

    public TweetViewHolder(Context context, View itemView, List<Tweet> tweetList) {

        super(itemView);

        this.tweetList = tweetList;
        this.context = context;

        itemView.setOnClickListener(this);
        ButterKnife.bind(this,itemView);
    }


    @Override
    public void onClick(View v) {

        int position = getLayoutPosition();
        // get the tweet
        Tweet tweet = tweetList.get(position);

        // send the url to DetailActivity
         Intent i = new Intent(context, DetailActivity.class);
          i.putExtra("tweet", Parcels.wrap(tweet));
          context.startActivity(i);
    }
}

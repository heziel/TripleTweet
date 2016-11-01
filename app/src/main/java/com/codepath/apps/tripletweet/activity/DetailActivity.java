package com.codepath.apps.tripletweet.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.tripletweet.R;
import com.codepath.apps.tripletweet.models.Tweet;
import com.codepath.apps.tripletweet.network.TwitterApplication;
import com.codepath.apps.tripletweet.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tvDetailName)
    TextView tvDetailName;
    @BindView(R.id.ivDetailProfilePic)
    ImageView ivDetailProfilePic;
    @BindView(R.id.tvDetailUserName)
    TextView tvDetailUserName;
    @BindView(R.id.tvDetailTweetFeed)
    TextView tvDetailTweetFeed;
    @BindView(R.id.ivRetweets)
    ImageView ivRetweet;
    @BindView(R.id.ivDetailFeedImage)
    ImageView ivDetailFeedImage;

    Tweet tweet;

    TwitterClient client = TwitterApplication.getRestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get Parcel
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        toolbarSupport();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        loadUserInfo();

        buttonsHandler();
    }

    private void buttonsHandler() {

        final Long tweetId = tweet.getUnique_id();


        Boolean isRetweeted = tweet.getRetweeted();
        if (isRetweeted) {
            ivRetweet.setImageResource(R.drawable.ic_twitter_retweet_done);
        } else {
            ivRetweet.setImageResource(R.drawable.ic_twitter_retweet_default);
        }

        ivRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivRetweet.setImageResource(R.drawable.ic_twitter_retweet_done);
                client.retweet(tweetId, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ivRetweet.setImageResource(R.drawable.ic_twitter_retweet_done);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                });
            }
        });
    }

    /*
    * Load all the information from the user into the detail activity.
     */
    private void loadUserInfo() {


        tvDetailName.setText(tweet.getUser().getName());

        // populate the picture.
        Glide.with(this).load(tweet.getUser().getProfileImageUrl())
                .placeholder(R.drawable.twitter_bird_logo)
                .into(ivDetailProfilePic);

        tvDetailUserName.setText(tweet.getUser().getScreenName());

        tvDetailTweetFeed.setText(tweet.getBody());

        String media_url = tweet.getEntities().getMedia().getMediaUrl();

        Glide.with(this).load(media_url)
                .placeholder(null)
                .into(ivDetailFeedImage);
    }

    private void toolbarSupport() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_logo_white_48);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.string.tweet_color))));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

}

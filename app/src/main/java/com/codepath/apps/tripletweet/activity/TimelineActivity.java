package com.codepath.apps.tripletweet.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.tripletweet.R;
import com.codepath.apps.tripletweet.adapter.TweetsArrayAdapter;
import com.codepath.apps.tripletweet.models.Tweet;
import com.codepath.apps.tripletweet.network.TwitterApplication;
import com.codepath.apps.tripletweet.network.TwitterClient;
import com.codepath.apps.tripletweet.utils.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    @BindView(R.id.rvTripleTweet) RecyclerView rvTripleTweet;

    private TwitterClient twitterClient;
    private ArrayList<Tweet> tweetArrayList;
    private TweetsArrayAdapter tweetsArrayAdapter;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        init();

       pullToRefresh();
    }

    private void pullToRefresh() {

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // clear the array of data
                tweetArrayList.clear();
                // Notify the adapter of the update
                tweetsArrayAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved

                populateTimeline();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    /*
    *   Initializer
     */
    private void init() {

        ButterKnife.bind(this);

        // getting singleton client
        twitterClient = TwitterApplication.getRestClient();

        populateTimeline();

        tweetArrayList = new ArrayList<>();
        tweetsArrayAdapter = new TweetsArrayAdapter(this,tweetArrayList);

        // layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        // Attach the adapter to the recyclerview to populate items
        rvTripleTweet.setAdapter(tweetsArrayAdapter);

        // Set layout manager to position the items
        rvTripleTweet.setLayoutManager(linearLayoutManager);

        // Only ever call `setContentView` once right at the top
       // setContentView(R.layout.activity_timeline);

/*
        //  Pagination
        scrollListener = new EndlessRecyclerViewScrollListener( linearLayoutManager)  {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadNextDataFromApi(page);
            }
        };

        rvTripleTweet.addOnScrollListener(scrollListener);*/

    }

    // Loading more Tweets to the timeLine
    private void loadNextDataFromApi(int page) {
        //  Implement Load More...
    }

    // send an api request
    // fill the list view
    private void populateTimeline() {

        twitterClient.getHomeTimeLine(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                tweetArrayList.addAll(tweets);
                tweetsArrayAdapter.notifyDataSetChanged();

                // disable pull to refresh
                swipeContainer.setRefreshing(false);

                Toast.makeText(TimelineActivity.this,"GOOD", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", json.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG", responseString);
            }
        });
    }

}

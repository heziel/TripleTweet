package com.codepath.apps.tripletweet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.tripletweet.R;
import com.codepath.apps.tripletweet.models.Tweet;
import com.codepath.apps.tripletweet.network.TwitterApplication;
import com.codepath.apps.tripletweet.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class ComposeFragment extends DialogFragment {

    public static int REQUEST_CODE = 200;

    @BindView(R.id.etNewTweet)
    TextView etNewTweet;
    @BindView(R.id.btnSubmitTweet)
    Button btnSubmitTweet;

    TwitterClient client = TwitterApplication.getRestClient();

    // Empty Constructor
    public ComposeFragment() {
    }

    //
    public static ComposeFragment newInstance(String title) {
        ComposeFragment composeFragment = new ComposeFragment();
        Bundle args = new Bundle();

        args.putString("title", title);
        composeFragment.setArguments(args);

        return composeFragment;
    }

    // Defines the listener interface
    public interface newTweetListener {
        void onFinishComposeTweet(Tweet tweet);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnSubmitTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBackResult();
            }
        });

    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult() {
        final newTweetListener listener = (newTweetListener) getActivity();
        client.postUpdate(etNewTweet.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Go back to the timeline
                //    Tweet tweet = Tweet.fromJSON(response);
                //    Intent i = new Intent();
                //   i.putExtra("tweet", Parcels.wrap(tweet));

                Tweet curTweeet = Tweet.fromJSON(response);
                listener.onFinishComposeTweet(curTweeet);
                dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG", responseString);
            }

        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.compose_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
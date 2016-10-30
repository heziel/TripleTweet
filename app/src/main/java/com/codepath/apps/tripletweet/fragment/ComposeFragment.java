package com.codepath.apps.tripletweet.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.tripletweet.R;
import com.codepath.apps.tripletweet.models.Tweet;
import com.codepath.apps.tripletweet.network.TwitterApplication;
import com.codepath.apps.tripletweet.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ComposeFragment extends DialogFragment {

    public static int REQUEST_CODE = 200;
    final private static int MAX_CHARS = 140;

    @BindView(R.id.etNewTweet) EditText etNewTweet;
    @BindView(R.id.btnSubmitTweet) Button btnSubmitTweet;
    @BindView(R.id.tvCharcter) TextView tvCharacters;
    @BindView(R.id.btnCancelTweet) Button btnCancelTweet;

    TwitterClient client = TwitterApplication.getRestClient();

    /*
    *   Empty Constructor
    */
    public ComposeFragment() {
    }

    /*
    *   New Instance of Compose fragment
    */
    public static ComposeFragment newInstance(String title) {
        ComposeFragment composeFragment = new ComposeFragment();
        Bundle args = new Bundle();

        args.putString("title", title);
        composeFragment.setArguments(args);

        return composeFragment;
    }

    /*
    *   Defines the listener interface
    */
    public interface newTweetListener {
        void onFinishComposeTweet(Tweet tweet);
    }

    /*
    * on View Created
    */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        submitOnClickHandler();
        cancelOnClickHandler();
        readTweetFromFile();
        editTextChangedListenerHandler();
    }

    /*
     * Edit Text Changed Listener Handler
     */
    private void editTextChangedListenerHandler() {
        etNewTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCharacters.setText(String.valueOf(MAX_CHARS - s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /*
    * Submit On Click Handler
    */
    private void submitOnClickHandler() {
        btnSubmitTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBackResult();
            }
        });
    }

    /*
    * Cancel On Click Handler
    */
    private void cancelOnClickHandler() {
       btnCancelTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelAlert();
            }
        });
    }

    private void showCancelAlert() {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Save To Draft")
                .setMessage("Are you sure ?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveTweet(etNewTweet.getText().toString());
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitTweet();
                    }
                })
                .create();

        dialog.show();

    }

    /*
     * Call this method exit the dialog alert.
     */
    private void exitTweet() {
        saveTweet("");
        dismiss();
    }

    /*
     * Call this method save draft tweet to file
     */
    private void saveTweet(String text) {
        String filename = "myTweet.txt";
        String string = text;
        FileOutputStream outputStream;

        try {
            outputStream = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(),"Tweet Saved",Toast.LENGTH_SHORT).show();
        dismiss();
    }

    /*
     * Call this method read a draft tweet from file
     */
    private void readTweetFromFile(){
        BufferedReader input = null;
        try {
            input = new BufferedReader(
                    new InputStreamReader(getContext().openFileInput("myTweet.txt")));
            String line;
            StringBuffer buffer = new StringBuffer();

            while ((line = input.readLine()) != null) {
                buffer.append(line + "\n");
            }
            etNewTweet.setText(buffer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * Call this method to send the data back to the parent fragment
    */
    public void sendBackResult() {
        final newTweetListener listener = (newTweetListener) getActivity();
        client.postUpdate(etNewTweet.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
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


    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();

    }
}
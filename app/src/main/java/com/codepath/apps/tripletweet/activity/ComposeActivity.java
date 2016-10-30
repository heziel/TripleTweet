package com.codepath.apps.tripletweet.activity;


import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class ComposeActivity extends DialogFragment{

    public static int REQUEST_CODE = 200;

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Toast.makeText(getContext(),"out",Toast.LENGTH_SHORT).show();
    }
}

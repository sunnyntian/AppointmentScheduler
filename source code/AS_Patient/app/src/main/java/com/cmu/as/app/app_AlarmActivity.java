package com.cmu.as.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.cmu.apointmentscedulerpatient.app.R;

public class app_AlarmActivity extends Activity {
    // MediaPlayer
    private MediaPlayer alarmMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get music and create MediaPlayer for it
        alarmMusic = MediaPlayer.create(this, R.raw.girl);
        // set as cycle play
        alarmMusic.setLooping(true);
        // play music
        alarmMusic.start();
        //create a dialogue
        new AlertDialog.Builder(app_AlarmActivity.this).setTitle("Alarm")
                .setMessage("It's time for hospital appointment!")
                .setPositiveButton("OK", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // stop music
                        alarmMusic.stop();
                        // terminate Activity
                        app_AlarmActivity.this.finish();
                    }
                }).show();
    }
}
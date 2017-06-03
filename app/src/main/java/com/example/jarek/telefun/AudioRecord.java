package com.example.jarek.telefun;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import java.io.IOException;

public class AudioRecord extends Activity {
    private MediaRecorder myAudioRecorder;
    private Button startRecording;
    private Button stop;
    private Button playRecording;
    private MediaPlayer mediaPlayer;
    private String dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_recorder);
//        initMedia();

        myAudioRecorder = new MediaRecorder();
        dataSource = Environment.getExternalStorageDirectory().getAbsolutePath();
        dataSource += "/audioRecorderTest.3gp";
        initButtonsClick();
    }

    private void initButtonsClick() {
        startRecording = (Button) findViewById(R.id.btnRecordingStart);
        playRecording = (Button) findViewById(R.id.playRecording);
        stop = (Button) findViewById(R.id.btnStop);
        stop.setClickable(false);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnRecordingStart:
                        startRecording();
                    case R.id.playRecording:
                        playRecording();
                    default:
                        break;
                }
            }
        };
        startRecording.setOnClickListener(listener);
        playRecording.setOnClickListener(listener);
    }

    private void playRecording() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(dataSource);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        stop.setVisibility(View.VISIBLE);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                stop.setClickable(false);
                stop.setOnClickListener(null);
                stop.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void stopPlaying() {
        mediaPlayer.stop();
        mediaPlayer.release();
//        mediaPlayer = null;
    }


    private void startRecording() {
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(dataSource);
        try {
            myAudioRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAudioRecorder.start();
        stop.setVisibility(View.VISIBLE);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
                stop.setClickable(false);
                stop.setOnClickListener(null);
                stop.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void stopRecording() {
        myAudioRecorder.stop();
        myAudioRecorder.release();
    }

}

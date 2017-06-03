package com.example.jarek.telefun;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioDescriptionSave extends MainActivity {

    private Integer soundID;
    private String soundName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_desc_save);
        soundID = getIntent().getIntExtra("AudioID",0);
        Resources res = getResources();
        soundName = res.getResourceEntryName(sounds[soundID]);

    }

    public void SaveSD(View view){
// Potrzebujemy ścieżki do karty SD:
        File sdcard = Environment.getExternalStorageDirectory();
// Dodajemy do ścieżki własny folder:
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki/");
// jeżeli go nie ma to tworzymy:
        dir.mkdir();
// Zapiszmy do pliku nasz tekst:
        File file = new File(dir, soundName + ".txt");
        EditText ET = (EditText)findViewById(R.id.editLyrics);
        String text = ET.getText().toString();
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(text.getBytes());
            os.close();
        }catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void ViewSD(View view){
        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki/");
        File file = new File(dir, soundName + ".txt");
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        String contents = new String(bytes);
        TextView tv = (TextView)findViewById(R.id.viewLyric);
        tv.setText(contents);
    }
}

package com.example.jarek.telefun;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final private int ALERT_DIALOG_BUTTON = 1;
    static final private int ALERT_DIALOG_LIST = 2;
    private Button btnNewAlertDialogButton;
    private Button btnAlertDialogSoundList;
    private ListView lv;
    protected String[] phoneNames;
    protected String[] phoneSpecs;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.phoneNames);
        initResources();
        initPhoneNamesListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PhoneSpec.class);
                intent.putExtra("specTexts",phoneSpecs[position]);
                intent.putExtra("specPics",phonePics[position]);
                startActivity(intent);
            }
        });

        Button audioRec = (Button) findViewById(R.id.audioRecorder);
        audioRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AudioRecord.class);
                startActivity(intent);
            }
        });

        btnNewAlertDialogButton = (Button) findViewById(R.id.btnNewAlertDialogButton);
        btnAlertDialogSoundList = (Button) findViewById(R.id.btnAlertDialogSoundList);
        initButtonsClick();
    }

    private void initButtonsClick() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnNewAlertDialogButton:
                        showDialog(ALERT_DIALOG_BUTTON);
                        break;
                    case R.id.btnAlertDialogSoundList:
                        showDialog(ALERT_DIALOG_LIST);
                        break;
                    default:
                        break;
                }
            }
        };
        btnNewAlertDialogButton.setOnClickListener(listener);
        btnAlertDialogSoundList.setOnClickListener(listener);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
            case ALERT_DIALOG_BUTTON:
                return createAlertDialogButton();
            case ALERT_DIALOG_LIST:
                return createAlertDialogList();
            default:
                return null;
        }
    }

    public Dialog createAlertDialogList() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final String[] sounds = { "Pierwszy dzwiek", "Drugi dzwiek" };
        dialogBuilder.setTitle("Lista dzwiekow");
        dialogBuilder.setItems(sounds, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playSound(which);
                Intent intent = new Intent(MainActivity.this, AudioDescriptionSave.class);
                intent.putExtra("AudioID", which);
                startActivity(intent);
            }
        });
        return dialogBuilder.create();
    }

    private void playSound(int id) {
        if (mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, sounds[id]);
        mediaPlayer.start();
    }

    public void stopSound(View view) {

        if (mediaPlayer != null)
            mediaPlayer.stop();
    }

    private Dialog createAlertDialogButton() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Wyjscie");
        dialogBuilder.setMessage("Czy na pewno chcesz wyjsc z aplikacji?");
        dialogBuilder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("Wychodze");
                MainActivity.this.finish();
            }
        });
        dialogBuilder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("Anulowales wyjscie");
            }
        });
        return dialogBuilder.create();
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    private void initResources() {
        Resources res = getResources();
        phoneNames = res.getStringArray(R.array.phoneNames);
        phoneSpecs = res.getStringArray(R.array.phoneSpecs);
    }

    private void initPhoneNamesListView() {
        lv.setAdapter(new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1,phoneNames));
    }

    protected int[] sounds = {
            R.raw.deanthedinosauce_heavyrainwcars,
            R.raw.ifartinurgeneraldirection_chirpingbirds2008
    };

    private int[] phonePics = {
            R.drawable.samsungs4,
            R.drawable.lgspirit,
            R.drawable.sonyxperiac3
    };
}

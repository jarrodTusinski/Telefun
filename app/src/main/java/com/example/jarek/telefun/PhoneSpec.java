package com.example.jarek.telefun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PhoneSpec extends Activity {

    static final private int CUSTOM_ALERT_DIALOG = 1;
    static final private int SIMPLE_ALERT_DIALOG = 2;
    private Button btnCustomAlertDialog;
    private Button btnAlertDialog;

    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.phone_specs);
        String he = getIntent().getStringExtra("specTexts");
        TextView tv = (TextView) findViewById(R.id.phoneSpec);
        ImageView phonePic = (ImageView) findViewById(R.id.phonePic);
        Integer in = getIntent().getIntExtra("specPics",0);
        phonePic.setImageResource(in);

        tv.setText(he);

        Button backToMain = (Button) findViewById(R.id.backToMainButton);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCustomAlertDialog = (Button) findViewById(R.id.btnCustomAlertDialog);
        btnAlertDialog = (Button) findViewById(R.id.btnAlertDialog);
        initButtonsClick();
    }

    private void initButtonsClick() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnCustomAlertDialog:
                        showDialog(CUSTOM_ALERT_DIALOG);
                        break;
                    case R.id.btnAlertDialog:
                        showDialog(SIMPLE_ALERT_DIALOG);
                        break;
                    default:
                        break;
                }
            }
        };
        btnCustomAlertDialog.setOnClickListener(listener);
        btnAlertDialog.setOnClickListener(listener);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case CUSTOM_ALERT_DIALOG:
                return createCustomAlertDialog();
            case SIMPLE_ALERT_DIALOG:
                return createSimpleAlertDialog();
            default:
                return null;
        }
    }

    private Dialog createSimpleAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Simple alert dialog");
        dialogBuilder.setMessage(R.string.lorem_ipsum);
        return dialogBuilder.create();
    }

    private Dialog createCustomAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(getCustomDialogLayout())
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return dialogBuilder.create();
    }

    private View getCustomDialogLayout() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.custom_dialog, (ViewGroup) findViewById(R.id.customDialog));
    }
}

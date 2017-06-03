package com.example.jarek.telefun;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileEdit extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_edit);
    }

    public void SaveText(View view){
        try {
// Otwarcie pliku myfilename.txt do zapisu z trybem dopisania do
//istniejącego pliku:
            OutputStreamWriter out = new
                    OutputStreamWriter(openFileOutput("myfilename.txt",MODE_APPEND));
// Pobranie tekstu z kontrolki EditText do obiektu klasy string
//a następnie zapis do pliku:
            EditText ET = (EditText)findViewById(R.id.editText1);
            String text = ET.getText().toString();
            out.write(text);
            out.write('\n');
// zamknięcie pliku
            out.close();
            Toast.makeText(this,"Text Saved !", Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
//gdy nie uda się zapisać:
            Toast.makeText(this,"Sorry Text could't be added", Toast.LENGTH_LONG).show();
        }
    }

    public void ViewText (View view) throws FileNotFoundException {
        StringBuilder text = new StringBuilder();
        try {
// Otwarcie pliku do wczytania:
            InputStream instream = openFileInput("myfilename.txt");
// Jeżeli istnieje możliwość wczytania pliku:
            if (instream != null) {// przygotujmy plik do wczytania:
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line=null;
                while (( line = buffreader.readLine()) != null) {
//Czytamy plik wiersz po wierszu i zapisujemy
                    text.append(line);
                    text.append('\n');
                }}}catch (IOException e) {
            e.printStackTrace();
        }
//Ustawiamy nasz wczytany tekst w TextView
        TextView tv = (TextView)findViewById(R.id. textView1);
        tv.setText(text);
    }

    public void ClearText(View v) throws IOException {

        //czysci TextView
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText("");

        //czysci plik
        OutputStreamWriter out = new
                OutputStreamWriter(openFileOutput("myfilename.txt",0));
        out.write("");
        //kasuje plik
        out.close();
        Context context = getApplicationContext();
        boolean del = context.deleteFile("myfilename.txt");
        if (del)
            Toast.makeText(this,"Skasowano plik",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Skasowanie pliku nie powiodlo sie",Toast.LENGTH_SHORT).show();
    }

    public void SaveSD(View view){
// Potrzebujemy ścieżki do karty SD:
        File sdcard = Environment.getExternalStorageDirectory();
// Dodajemy do ścieżki własny folder:
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki/");
// jeżeli go nie ma to tworzymy:
        dir.mkdir();
// Zapiszmy do pliku nasz tekst:
        File file = new File(dir, "myfilename.txt");
        EditText ET = (EditText)findViewById(R.id.editText1);
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
        File file = new File(dir, "myfilename.txt");
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
        TextView tv = (TextView)findViewById(R.id. textView1);
        tv.setText(contents);
    }
}

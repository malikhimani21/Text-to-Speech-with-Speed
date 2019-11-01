package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, AdapterView.OnItemSelectedListener {

    private TextToSpeech textToSpeech;
    private Button button;
    private EditText editText;
    private Spinner spinner;

    private static String speed = "Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        textToSpeech = new TextToSpeech(this, this);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speed();
                listen();
            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), "Language is not supported", Toast.LENGTH_LONG).show();
            } else {
                button.setEnabled(true);
                listen();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        speed = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void listen() {
        String string = editText.getText().toString().trim();
        textToSpeech.speak(string, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void speed() {
        if (speed.equals("Very Slow")) {
            textToSpeech.setSpeechRate(0.1f);
        }
        if (speed.equals("Slow")) {
            textToSpeech.setSpeechRate(0.5f);
        }
        if (speed.equals("Normal")) {
            textToSpeech.setSpeechRate(1.0f);
        }
        if (speed.equals("Fast")) {
            textToSpeech.setSpeechRate(1.5f);
        }
        if (speed.equals("Very Fast")) {
            textToSpeech.setSpeechRate(2.0f);
        }
    }

    @Override
    protected void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}

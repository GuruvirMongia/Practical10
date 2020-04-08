package com.example.practical100;

import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener{

    double pitch=0.0f,speechRate=0.0f;
    private TextToSpeech tts;
    SeekBar sBSpeechRate,sBPitchRate;
    EditText eTPronounce;
    Button btnSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeControls();
        tts = new TextToSpeech(this, this);
    }
    private void initializeControls() {

        sBSpeechRate=findViewById(R.id.sBSpeechRate);
        sBPitchRate=findViewById(R.id.sBPitchRate);
        eTPronounce=findViewById(R.id.eTPronounce);
        btnSpeak=findViewById(R.id.btnSpeak);
        sBSpeechRate.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                speechRate=((double)progress+1)/10;
            }
        });
        sBPitchRate.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                pitch=((double)progress+1)/10;
            }
        });
        eTPronounce.setText("Welcome to Agent of Shield");
        btnSpeak.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }
        } else {
            Toast.makeText(getBaseContext(), "Initilization Failed!",Toast.LENGTH_SHORT).show();
        }
    }
    private void speakOut() {
        String text = eTPronounce.getText().toString();
        tts.setPitch((float)pitch);
        tts.setSpeechRate((float)speechRate);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
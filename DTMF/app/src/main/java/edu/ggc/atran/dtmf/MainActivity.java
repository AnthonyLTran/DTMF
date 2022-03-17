package edu.ggc.atran.dtmf;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {
    private RadioButton duration1;
    private RadioButton duration2;
    private RadioButton duration3;
    private ToneAsyncTask task;
    private char c;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        duration1 = findViewById(R.id.rbShort);
        duration1 = findViewById(R.id.rbMedium);
        duration1 = findViewById(R.id.rbLong);

    }

    public void shortCLicked(View view) {
        char c = 'S';
    }

    public void mediumCLicked(View view) {
        char c = 'M';
    }

    public void longCLicked(View view) {
        char c = 'L';
    }

    public void buttonCLicked(View view) {
        Button b = (Button) view;
        String s = b.getText().toString();
        Log.v("DTMFTool", s + " clicked!");
        task = new ToneAsyncTask();
        task.execute(s.charAt(0), 'M');


    }


    class ToneAsyncTask extends AsyncTask<Character, Void, AudioTrack> {
            private static final int SAMPLE_RATE = 8000;

            @Override
            protected AudioTrack doInBackground(Character... chars) {

                char c = chars[0];        // the DTMF key to render as a tone
                int duration = 0;        // one of S, M, L for short, medium, long
                switch (chars[1]) {
                    case 'S':
                        duration = 250;
                        break;
                    case 'M':
                        duration = 500;
                        break;
                    case 'L':
                        duration = 4000;
                        break;
                }
                short[] samples = DTMF.generateTone(c, duration);
                AudioTrack track = new AudioTrack.Builder()
                        .setAudioAttributes(new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION_SIGNALLING)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build())
                        .setAudioFormat(new AudioFormat.Builder()
                                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                                .setSampleRate(SAMPLE_RATE)
                                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                                .build())
                        .setBufferSizeInBytes(samples.length)
                        .build();

                track.play();
                track.write(samples, 0, samples.length);
                track.release();
                return track;

            }

        }

}

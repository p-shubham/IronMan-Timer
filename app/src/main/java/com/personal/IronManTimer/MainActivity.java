package com.personal.IronManTimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seek;
    TextView timeText;
    int second, minute;
    boolean flag = false;
    Button startButton;
    CountDownTimer countDownTimer;

    public void setTimer(int progress){
        timeText = findViewById(R.id.timeView);
        minute = progress / 60;
        second = progress - (progress / 60) * 60;
        String secondString = Integer.toString(second);
        String minuteString = Integer.toString(minute);
        if(second <=9 ){
            secondString = "0" + second;
        }
        timeText.setText(minuteString + ":" + secondString);
    }

    public void startTimer(View view){
        if(!flag) {
            flag = true;
            startButton.setText("STOP");
            countDownTimer = new CountDownTimer(seek.getProgress() * 1000 + 50, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setTimer((int) millisUntilFinished / 1000);
                    seek.setProgress((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    seek.setProgress(0);
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                    mPlayer.start();
                }
            }.start();
        } else {
            countDownTimer.cancel();
            seek.setProgress(30);
            timeText.setText("0:30");
            startButton.setText("START");
            flag = false;
        }
    }

    public void resetTimer(View view){
        seek.setProgress(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Iron Man Timer");
        startButton = findViewById(R.id.startButton);
        seek = findViewById(R.id.seekBar);
        seek.setMax(600);
        seek.setProgress(30);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}

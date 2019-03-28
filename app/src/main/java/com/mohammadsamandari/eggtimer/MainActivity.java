package com.mohammadsamandari.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SeekBar seekBar;
    Button btnGo;
    CountDownTimer countDownTimer;

    String minute, second;
    int wholeTime, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.lblTimer);
        btnGo = findViewById(R.id.btnGo);

        //status 1=stopped  2=going
        status = 1;
        calculateTime(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                calculateTime(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

//                This 3 line of code , cancel countdowntimer in case it has started before, before running it again.
                if(countDownTimer!=null){
                    countDownTimer.cancel();
                    countDownTimer=null;
                }
                
                countDownTimer = new CountDownTimer(wholeTime * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        calculateTime((int) (millisUntilFinished / 1000));
                    }

                    @Override
                    public void onFinish() {
                        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.popcorn);
                        mediaPlayer.start();

                        status = 1;
                        seekBar.setEnabled(true);
                        Button button = (Button) v;
                        button.setText("Start");

                        seekBar.setProgress(30);
                        textView.setText("00:30");
                    }
                };

                if (status == 1) {
                    seekBar.setEnabled(false);
                    Button button = (Button) v;
                    button.setText("Stop");

                    countDownTimer.start();
                    status = 2;

                } else {

                    status = 1;
                    seekBar.setEnabled(true);
                    Button button = (Button) v;
                    button.setText("Start");

                    seekBar.setProgress(30);
                    textView.setText("00:30");

                }
            }
        });
    }


    private void calculateTime(int progress) {
        wholeTime = progress;

//        when deviding an int to another int, with / , the result is going to be a whole number
//        when deviding an int to another int, to get the remainder use %

//        This line of code format the result of the devide to be 2 digit. no matter what.
        minute = new DecimalFormat("00").format(progress / 60);
        second = new DecimalFormat("00").format(progress % 60);

        textView.setText(minute + ":" + second);
    }
}

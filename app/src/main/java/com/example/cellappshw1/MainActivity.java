package com.example.cellappshw1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    long START_TIME_MILLI=120000;
    long TIME_LEFT_MILLI=120000;
    CountDownTimer timer;
    TextView timerView;

    ProgressBar bpb;
    ProgressBar rpb;
    Button bHeavy;
    Button rHeavy;
    Button rSpecial;
    Button bSpecial;

    Switch turns;
    ImageView bImg;
    ImageView rImg;
    int redMovesCounter=0;
    int blueMovesCounter=0;
    int winCounterRed=0;
    int winCounterBlue=0;
    int player;
    Random r=new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bImg = findViewById(R.id.blue_pic);
        Glide.with(getApplicationContext()).load(R.drawable.ic_baseline_accessibility_new_24).into(bImg);
        rImg = findViewById(R.id.red_pic);
        Glide.with(getApplicationContext()).load(R.drawable.red_player).into(rImg);
        bpb = findViewById(R.id.HP_blue);
        rpb = findViewById(R.id.HP_red);
        bHeavy = findViewById(R.id.heavy_blue_butt);
        rHeavy = findViewById(R.id.heavy_red_butt);
        rSpecial = findViewById(R.id.special_red_butt);
        bSpecial = findViewById(R.id.special_blue_butt);


        turns = findViewById(R.id.player_turn);
        player=r.nextInt(2-0)+0;
        if (player==0){
            turns.setChecked(true);
        }
        else{
            turns.setChecked(false);
        }
        rollDice();
        rHeavy.setOnClickListener(this::heavyAttack);
        bHeavy.setOnClickListener(this::heavyAttack);
        rSpecial.setOnClickListener(this::specialAttack);
        bSpecial.setOnClickListener(this::specialAttack);
        timerView=findViewById(R.id.timer);
        timerStart();
    }

    private void rollDice() {
        MediaPlayer diceRoll=MediaPlayer.create(MainActivity.this, R.raw.dice);
        diceRoll.start();
    }

    private void lightAttackSound(){
        MediaPlayer sound=MediaPlayer.create(MainActivity.this, R.raw.light);
        sound.start();
    }

    private void heavyAttackSound(){
        MediaPlayer sound=MediaPlayer.create(MainActivity.this, R.raw.heavy);
        sound.start();
    }

    private void specialAttackSound(){
        MediaPlayer sound=MediaPlayer.create(MainActivity.this, R.raw.special);
        sound.start();
    }

    private void deathSound(){
        MediaPlayer sound=MediaPlayer.create(MainActivity.this, R.raw.death);
        sound.start();
    }

    private void timerStart() {
        timer=new CountDownTimer(TIME_LEFT_MILLI, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TIME_LEFT_MILLI=millisUntilFinished;
                int minutes=(int)(TIME_LEFT_MILLI/1000)/60;
                int seconds=(int)(TIME_LEFT_MILLI/1000)%60;
                String time=String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
                timerView.setText(time);
            }

            @Override
            public void onFinish() {
                checkWinTimer();
            }
        }.start();
    }

    private void checkWinTimer() {
        if (bpb.getProgress()>rpb.getProgress()){
            blueWin();;
        }
        else if (bpb.getProgress()<rpb.getProgress()){
            redWin();
        }
        else {
            draw();
        }
    }

    private void draw() {
        String name="DRAW";
        AlertDialog alert=new AlertDialog.Builder(this)
                .setMessage("DRAW!")
                .setPositiveButton("Replay?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rpb.setProgress(100);
                        bpb.setProgress(100);
                        redMovesCounter=0;
                        blueMovesCounter=0;
                        player=r.nextInt(2-0)+0;
                        if (player==0){
                            turns.setChecked(true);
                        }
                        else{
                            turns.setChecked(false);
                        }
                        TIME_LEFT_MILLI=START_TIME_MILLI;
                        timerStart();

                    }
                })
                .setNegativeButton("End Game?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        endGameSend();
                    }
                })
                .create();
        alert.show();
    }


    public void checkWin(ProgressBar rpb, ProgressBar bpb) {
        if (bpb.getProgress() <= 0) {
            redWin();
        } else if (rpb.getProgress() <= 0) {
            blueWin();
        }
    }

    public void endGameSend(){
        if (winCounterBlue>winCounterRed){
            String name="Blue";
            Intent intent=new Intent(this, GameEnd.class)
                    .putExtra("winCount", winCounterRed)
                    .putExtra("winnerName", name);
            startActivity(intent);
        }
        else if (winCounterBlue<winCounterRed){
            String name="Red";
            Intent intent=new Intent(this, GameEnd.class)
                    .putExtra("winCount", winCounterBlue)
                    .putExtra("winnerName", name);
            startActivity(intent);
        }
        else if (winCounterBlue==winCounterRed){
            String name="DRAW";
            Intent intent=new Intent(this, GameEnd.class)
                    .putExtra("winCount", winCounterBlue)
                    .putExtra("winnerName", name);
            startActivity(intent);
        }

    }

    private void redWin() {
        winCounterRed++;
        deathSound();
        AlertDialog alert=new AlertDialog.Builder(this)
                .setMessage("Red Player Wins In "+redMovesCounter+" Moves!")
                .setPositiveButton("Replay?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rpb.setProgress(100);
                        bpb.setProgress(100);
                        redMovesCounter=0;
                        blueMovesCounter=0;
                        player=r.nextInt(2-0)+0;
                        if (player==0){
                            turns.setChecked(true);
                        }
                        else{
                            turns.setChecked(false);
                        }
                        TIME_LEFT_MILLI=START_TIME_MILLI;
                        timerStart();
                    }
                })
                .setNegativeButton("End Game?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        endGameSend();
                    }
                })
                .create();
        alert.show();
    }



    private void blueWin() {
        winCounterBlue++;
        deathSound();
        AlertDialog alert=new AlertDialog.Builder(this)
                .setMessage("Blue Player Wins In "+blueMovesCounter+" Moves!")
                .setPositiveButton("Replay?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rpb.setProgress(100);
                        bpb.setProgress(100);
                        redMovesCounter=0;
                        blueMovesCounter=0;
                        player=r.nextInt(2-0)+0;
                        if (player==0){
                            turns.setChecked(true);
                        }
                        else{
                            turns.setChecked(false);
                        }
                        TIME_LEFT_MILLI=START_TIME_MILLI;
                        timerStart();
                    }
                })
                .setNegativeButton("End Game?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        endGameSend();
                    }
                })
                .create();
        alert.show();
    }

    public void heavyAttack(View v) {
        if (v == rHeavy && !turns.isChecked()) {

            redMovesCounter++;
            turns.setChecked(true);
            bpb.setProgress(bpb.getProgress() - 30);
        } else if (v == bHeavy && turns.isChecked()) {
            blueMovesCounter++;
            turns.setChecked(false);
            rpb.setProgress(rpb.getProgress() - 30);
        }
        heavyAttackSound();
        checkWin(rpb, bpb);
    }

    public void specialAttack(View v) {
        if (v == rSpecial && !turns.isChecked()) {
            redMovesCounter++;
            turns.setChecked(true);
            bpb.setProgress(bpb.getProgress() - 45);
        } else if (v == bSpecial && turns.isChecked()) {
            blueMovesCounter++;
            turns.setChecked(false);
            rpb.setProgress(rpb.getProgress() - 45);
        }
        specialAttackSound();
        checkWin(rpb, bpb);
    }

    public void lightAttack(View v) {
        if (v == findViewById(R.id.light_red_butt) && !turns.isChecked()) {
            redMovesCounter++;
            turns.setChecked(true);
            bpb.setProgress(bpb.getProgress() - 15);
        } else if (v == findViewById(R.id.light_blue_butt) && turns.isChecked()) {
            blueMovesCounter++;
            turns.setChecked(false);
            rpb.setProgress(rpb.getProgress() - 15);
        }
        lightAttackSound();
        checkWin(rpb, bpb);
    }
}

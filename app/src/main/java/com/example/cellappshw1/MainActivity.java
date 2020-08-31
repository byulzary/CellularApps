package com.example.cellappshw1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ProgressBar bpb;
    ProgressBar rpb;
    Button bHeavy;
    Button rHeavy;
    Button rSpecial;
    Button bSpecial;

    Switch turns;
    ImageView bImg;
    ImageView rImg;

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
        rHeavy.setOnClickListener(this::heavyAttack);
        bHeavy.setOnClickListener(this::heavyAttack);
        rSpecial.setOnClickListener(this::specialAttack);
        bSpecial.setOnClickListener(this::specialAttack);
    }

    public void checkWin(ProgressBar rpb, ProgressBar bpb) {
        if (bpb.getProgress() <= 0) {
            redWin();
        } else if (rpb.getProgress() <= 0) {
            blueWin();
        }
    }

    private void redWin() {
        AlertDialog alert=new AlertDialog.Builder(this)
                .setMessage("Red Player Wins")
                .create();
        alert.show();
    }

    private void blueWin() {
        AlertDialog alert=new AlertDialog.Builder(this)
                .setMessage("Blue Player Wins")
                .create();
        alert.show();
    }

    public void heavyAttack(View v) {
        if (v == rHeavy && !turns.isChecked()) {
            turns.setChecked(true);
            bpb.setProgress(bpb.getProgress() - 45);
        } else if (v == bHeavy && turns.isChecked()) {
            turns.setChecked(false);
            rpb.setProgress(rpb.getProgress() - 45);
        }
        checkWin(rpb, bpb);
    }

    public void specialAttack(View v) {
        if (v == rSpecial && !turns.isChecked()) {
            turns.setChecked(true);
            bpb.setProgress(bpb.getProgress() - 45);
        } else if (v == bSpecial && turns.isChecked()) {
            turns.setChecked(false);
            rpb.setProgress(rpb.getProgress() - 45);
        }
        checkWin(rpb, bpb);
    }

    public void lightAttack(View v) {
        if (v == findViewById(R.id.light_red_butt) && !turns.isChecked()) {
            turns.setChecked(true);
            bpb.setProgress(bpb.getProgress() - 15);
        } else if (v == findViewById(R.id.light_blue_butt) && turns.isChecked()) {
            turns.setChecked(false);
            rpb.setProgress(rpb.getProgress() - 15);
        }
        checkWin(rpb, bpb);
    }
}

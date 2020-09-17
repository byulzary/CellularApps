package com.example.cellappshw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameEnd extends AppCompatActivity {

    TextView winnerText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_end);
        winnerText = findViewById(R.id.winnerText);
        button = findViewById(R.id.menuButton);
        Intent intentRecv = getIntent();
        int num = intentRecv.getIntExtra("winCount", 0);
        num = num + 1;
        String name = intentRecv.getStringExtra("winnerName");
        if (name!="DRAW") {
            winnerText.setText(name + " Wins!" +
                    "\nIn " + num + " Rounds!");
            button.setOnClickListener(this::toMainMenu);
        }
        else{
            winnerText.setText(name+" Of "+num+" Rounds!");
        }
    }

    private void toMainMenu(View view) {
        Intent intent=new Intent(this, Menu.class);
        startActivity(intent);
    }
}

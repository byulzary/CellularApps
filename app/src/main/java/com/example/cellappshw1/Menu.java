package com.example.cellappshw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    Button topTenButton;
    Button playNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        playNowButton=findViewById(R.id.playNow);
        topTenButton=findViewById(R.id.topTen);
        playNowButton.setOnClickListener(this::playNow);
        topTenButton.setOnClickListener(this::topTen);
    }

    private void playNow(View view) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void topTen(View view) {
        Intent intent=new Intent(this, TopTen.class);
        startActivity(intent);
    }
}

package com.example.colours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView score1, score2, score3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences sp = this.getSharedPreferences("scores", Context.MODE_PRIVATE);

        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);
        score3 = findViewById(R.id.score3);

        if(sp.getBoolean("first", false))
            score1.setText("1. WIN");
        else score1.setText("1. LOSE");

        if(sp.getBoolean("second", false))
            score2.setText("2. WIN");
        else score2.setText("2. LOSE");

        if(sp.getBoolean("second", false))
            score3.setText("3. WIN");
        else score3.setText("3. LOSE");
    }
}
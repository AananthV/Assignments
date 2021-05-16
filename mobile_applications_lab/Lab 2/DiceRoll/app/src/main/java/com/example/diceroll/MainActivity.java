package com.example.diceroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int STOP = 1;
    final int START = 0;

    Handler handler = new Handler();
    int imageGap = 100;
    int p1ImageArray[] = {R.drawable.p1d1, R.drawable.p1d2, R.drawable.p1d3, R.drawable.p1d4, R.drawable.p1d5, R.drawable.p1d6};
    int p2ImageArray[] = {R.drawable.p2d1, R.drawable.p2d2, R.drawable.p2d3, R.drawable.p2d4, R.drawable.p2d5, R.drawable.p2d6};
    int state = STOP;

    ImageView imageView;
    Button button, resetButton;
    TextView p1Score, p2Score;

    DiceGame diceGame;

    Runnable runnable = new Runnable() {
        public void run() {
            diceGame.nextRandomNumber();
            if (diceGame.currentPlayer == 0)
                imageView.setImageResource(p1ImageArray[diceGame.currentNumber - 1]);
            else if (diceGame.currentPlayer == 1)
                imageView.setImageResource(p2ImageArray[diceGame.currentNumber - 1]);
            handler.postDelayed(this, imageGap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_dice);
        button = findViewById(R.id.start_stop);
        resetButton = findViewById(R.id.reset);

        p1Score = findViewById(R.id.player1_score);
        p2Score = findViewById(R.id.player2_score);

        diceGame = new DiceGame();

        updateScore();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state == STOP) {
                    button.setText("STOP");
                    handler.post(runnable);
                    state = START;
                } else {
                    handler.removeCallbacks(runnable);
                    diceGame.roll();
                    updateScore();
                    state = STOP;
                    button.setText("ROLL");
                    if (diceGame.winner == -1){
                        Toast.makeText(getApplicationContext(), "Player " + (diceGame.currentPlayer + 1) + "'s turn!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }

    private int getPlayerColor(int id) {
        switch (id) {
            case 0:
                return 0xff0099cc;
            case 1:
                return 0xff99cc00;
        }

        return 0;
    }

    private void updateScore() {
        p1Score.setText("" + diceGame.scores[0]);
        p2Score.setText("" + diceGame.scores[1]);

        button.setBackgroundColor(getPlayerColor(diceGame.currentPlayer));

        if (diceGame.winner != -1) {
            Toast.makeText(getApplicationContext(), "Winner is player " + (diceGame.winner) + "!", Toast.LENGTH_SHORT).show();
            button.setVisibility(View.GONE);
        }
    }

    private void reset() {
        diceGame.reset();
        updateScore();
        button.setVisibility(View.VISIBLE);
    }
}
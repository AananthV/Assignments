package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TicTacToe game;
    GridLayout mGridLayout;
    ArrayList<Button> mButtons;
    TextView mCurrentMove, mWinner, mDraw;
    LinearLayout mCurrentMoveContainer, mWinnerContainer;
    Button mResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new TicTacToe();
        mGridLayout = (GridLayout) findViewById(R.id.grid);
        mButtons = new ArrayList<Button>();
        mCurrentMove = (TextView) findViewById(R.id.current_move);
        mWinner = (TextView) findViewById(R.id.winner);
        mDraw = (TextView) findViewById(R.id.draw);
        mCurrentMoveContainer = (LinearLayout) findViewById(R.id.current_move_container);
        mWinnerContainer = (LinearLayout) findViewById(R.id.winner_container);
        mResetButton = (Button) findViewById(R.id.reset_button);

        for (int i = 0; i < 9; i++) {
            Button b = new Button(this);

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();

            lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            lp.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

            int margin = 8;
            lp.topMargin = margin;
            lp.bottomMargin = margin;
            lp.leftMargin = margin;
            lp.rightMargin = margin;

            b.setLayoutParams(lp);

            b.setTextSize(48);

            int finalI = i;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeMove(finalI);
                }
            });

            mGridLayout.addView(b);
            mButtons.add(b);
        }

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        reset();
    }

    private String getPlayerSymbol(int id) {
        if (id == game.PLAYER1)
            return "X";
        else if (id == game.PLAYER2)
            return "O";

        return "";
    }

    private int getPlayerColor(int id) {
        if (id == game.PLAYER1)
            return 0xff669900;
        else if (id == game.PLAYER2)
            return 0xffcc0000;

        return 0xffA9A9A9;
    }

    private void updateGrid() {
        for (int i = 0; i < 9; i++) {
            mButtons.get(i).setText(getPlayerSymbol(game.grid[i]));
            mButtons.get(i).setBackgroundColor(getPlayerColor(game.grid[i]));
        }
    }

    private void updateMove() {
        mCurrentMove.setText(getPlayerSymbol(game.currentPlayer));
        mCurrentMove.setTextColor(getPlayerColor(game.currentPlayer));

        Toast toast = Toast.makeText(getApplicationContext(),
                "Player " + getPlayerSymbol(game.currentPlayer) + " move",
                Toast.LENGTH_SHORT);

        toast.show();
    }

    private void updateWinner() {
        Log.d("winner", game.winner + "");

        if (game.winner == -1)
            return;

        mCurrentMoveContainer.setVisibility(View.GONE);

        if (game.winner == game.DRAW) {
            mDraw.setVisibility(View.VISIBLE);

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Game was Drawn!",
                    Toast.LENGTH_SHORT);

            toast.show();

            return;
        }

        mWinnerContainer.setVisibility(View.VISIBLE);
        mWinner.setText(getPlayerSymbol(game.winner));
        mWinner.setTextColor(getPlayerColor(game.winner));

        Toast toast = Toast.makeText(getApplicationContext(),
                "Player " + getPlayerSymbol(game.winner) + " won!",
                Toast.LENGTH_SHORT);

        toast.show();
    }

    private void makeMove(int i) {
        if (game.makeMove(i)) {
            updateGrid();
            updateMove();
            updateWinner();
        }
    }

    private void reset() {
        game.reset();
        updateGrid();
        updateMove();

        mCurrentMoveContainer.setVisibility(View.VISIBLE);
        mDraw.setVisibility(View.GONE);
        mWinnerContainer.setVisibility(View.GONE);
    }
}
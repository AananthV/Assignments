package com.example.diceroll;

import java.util.Random;

public class DiceGame {

    // Dice Stuff
    final int min = 1;
    final int max = 6;
    int currentNumber = 1;

    // Game Stuff
    int winner; // 1 - P1, 2 - P2, 3 - Draw
    int targetScore = 25;
    int currentPlayer = -1;
    int numPlayers = 2;
    int scores[] = {0, 0};

    // Constructor
    DiceGame() {
        reset();
    }

    // Check if game is over
    public void checkIfGameIsOver () {
        for (int i = 0; i < numPlayers; i++) {
            if (scores[i] > targetScore) {
                winner = i + 1;
                return;
            }
        }
    }

    public void nextRandomNumber() {
        int r = 0;
        do {
            r = new Random().nextInt((max - min) + 1) + min;
        } while (r == currentNumber);
        currentNumber = r;
    }

    // Function to make a move
    public void roll() {
        if( winner != -1 ) {
            return;
        }

        scores[currentPlayer] += currentNumber;

        currentPlayer = (currentPlayer + 1) % numPlayers;

        checkIfGameIsOver();
    }

    public void reset() {
        for (int i = 0; i < numPlayers; i++) scores[i] = 0;
        winner = -1;
        currentPlayer = 0;
    }
}
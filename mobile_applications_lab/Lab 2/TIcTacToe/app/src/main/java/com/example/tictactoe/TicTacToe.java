package com.example.tictactoe;

public class TicTacToe {
    int grid[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1}; // 1 for P1 and 2 for P2
    int winner; // 1 - P1, 2 - P2, 3 - Draw
    int numPlays = 0;
    int currentPlayer = -1;
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static final int DRAW = 3;

    // Constructor
    TicTacToe() {
        winner = -1;
        currentPlayer = PLAYER1;
    }

    // Check if game is over
    public void checkIfGameIsOver () {
        if((grid[0] == 1 && grid[3] == 1 && grid[6] == 1) ||
                (grid[0] == 1 && grid[1] == 1 && grid[2] == 1) ||
                (grid[0] == 1 && grid[4] == 1 && grid[8] == 1) ||
                (grid[1] == 1 && grid[4] == 1 && grid[7] == 1) ||
                (grid[2] == 1 && grid[5] == 1 && grid[8] == 1) ||
                (grid[3] == 1 && grid[4] == 1 && grid[5] == 1) ||
                (grid[6] == 1 && grid[7] == 1 && grid[8] == 1) ||
                (grid[2] == 1 && grid[4] == 1 && grid[6] == 1)
        ) {
            winner = PLAYER1;
        } else if((grid[0] == 2 && grid[3] == 2 && grid[6] == 2) ||
                (grid[0] == 2 && grid[1] == 2 && grid[2] == 2) ||
                (grid[0] == 2 && grid[4] == 2 && grid[8] == 2) ||
                (grid[1] == 2 && grid[4] == 2 && grid[7] == 2) ||
                (grid[2] == 2 && grid[5] == 2 && grid[8] == 2) ||
                (grid[3] == 2 && grid[4] == 2 && grid[5] == 2) ||
                (grid[6] == 2 && grid[7] == 2 && grid[8] == 2) ||
                (grid[2] == 2 && grid[4] == 2 && grid[6] == 2)
        ) {
            winner = PLAYER2;
        } else if (numPlays == grid.length) {
            winner = DRAW;
        }
    }

    // Function to make a move
    public boolean makeMove(int i) {
        if(winner != -1 || i >= grid.length || grid[i] != -1) {
            return false;
        }

        grid[i] = currentPlayer;
        currentPlayer = currentPlayer == PLAYER1 ? PLAYER2 : PLAYER1;

        numPlays++;

        checkIfGameIsOver();

        return true;
    }

    public void reset() {
        winner = -1;
        numPlays = 0;
        for (int i = 0; i < 9; i++) grid[i] = -1;
        currentPlayer = PLAYER1;
    }
}
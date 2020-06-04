package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import java.util.Random;

/**
 * IntelligentPlayer.java
 * A player who looks a few turns ahead via the MiniMax algorithm
 *
 * @author Jake Sturgeon
 * @version 1.0 on 11/04/2017
 */
public class IntelligentPlayer extends Connect4Player {

    //init random object
    private final Random RAND = new Random();

    /**
     * Method that makes the intelligent player's move
     *
     * @param gameState the current Connect4 game state
     */
    @Override
    public void makeMove(Connect4GameState gameState) {
        int[] choice = minimax(gameState, 4, true);

        //if the intelli-player cannot find a winning play => make random move
        if (choice[0] == -1) {
            choice[0] = RAND.nextInt(Connect4GameState.NUM_COLS);
        }
        try {
            gameState.move(choice[0]);
            System.out.println("Computer dropped counter in column " + choice[0]);
        } catch (ColumnFullException e) {
            System.out.println(e);
        }

    }

    //use minimax to make the optimum move
    private int[] minimax(Connect4GameState gameState, int depth, boolean max) {

        //get the score of the current gamestate
        int stateScore = getScore(gameState);

        //if the depth == 0 or is a terminal node return the move
        if (depth == 0 || stateScore == Integer.MAX_VALUE || stateScore == Integer.MIN_VALUE) {
            int[] temp = {-1, stateScore};
            return temp;
        }
        //if its the maximising move return the max value
        if (max) {
            int[] bestValue = {-1, Integer.MIN_VALUE};
            //create a new gamestate for each move
            for (int col = 0; col < Connect4GameState.NUM_COLS; col++) {
                try {
                    Connect4GameState copy = gameState.copy();
                    copy.move(col);
                    int[] v = minimax(copy, depth - 1, false);
                    if (bestValue[1] < v[1]) {
                        bestValue[1] = v[1];
                        bestValue[0] = col;
                    }

                } catch (ColumnFullException e) {
                    System.out.println(e);
                }
            }
            return bestValue;
            //if its the maximising move return the min value
        } else {
            int[] bestValue = {-1, Integer.MAX_VALUE};
            //create a new gamestate for each move
            for (int col = 0; col < Connect4GameState.NUM_COLS; col++) {
                try {
                    Connect4GameState copy = gameState.copy();
                    copy.move(col);
                    int[] v = minimax(copy, depth - 1, true);
                    if (bestValue[1] > v[1]) {
                        bestValue[1] = v[1];
                        bestValue[0] = col;
                    }
                } catch (ColumnFullException e) {
                    System.out.println(e);
                }
            }
            return bestValue;
        }
    }

    //method used to get each gamestate score
    private int getScore(Connect4GameState gameState) {
        //init points
        int points = 0;

        //for each counter
        for (int col = 0; col < Connect4GameState.NUM_COLS; col++) {
            for (int row = 0; row < Connect4GameState.NUM_ROWS; row++) {

                //get points for each direction on a counter
                int verticalPoints = getVPoints(row, col, gameState);
                int horizontalPoints = getHPoints(row, col, gameState);
                int diagonalOne = getDiagonalOnePoints(row, col, gameState);
                int diagonalTwo = getDiagonalTwoPoints(row, col, gameState);

                //if a winning counter return maxInt
                if (verticalPoints >= Connect4GameState.NUM_IN_A_ROW_TO_WIN
                    || horizontalPoints >= Connect4GameState.NUM_IN_A_ROW_TO_WIN
                    || diagonalOne >= Connect4GameState.NUM_IN_A_ROW_TO_WIN
                    || diagonalTwo >= Connect4GameState.NUM_IN_A_ROW_TO_WIN) {

                    //if red terminal return max value
                    if (gameState.getCounterAt(col, row) == Connect4GameState.RED) {
                        return Integer.MAX_VALUE;
                    }
                    //if yellow terminal return min value
                    if (gameState.getCounterAt(col, row) == Connect4GameState.YELLOW) {
                        return Integer.MIN_VALUE;
                    }
                } else if (gameState.getCounterAt(col, row) == Connect4GameState.RED) {
                    points += verticalPoints + horizontalPoints + diagonalOne + diagonalTwo;
                } else if (gameState.getCounterAt(col, row) == Connect4GameState.YELLOW) {
                    points -= verticalPoints + horizontalPoints + diagonalOne + diagonalTwo;
                }

            }
        }
        return points;
    }

    //method used to loop in each direction
    private int movePoints(int row, int col, int y, int x, Connect4GameState gameState) {
        int score = 0;
        int c = col;
        int r = row;
        for (int i = 0; i < Connect4GameState.NUM_IN_A_ROW_TO_WIN; i++) {
            if (gameState.getCounterAt(col, row) == gameState.getCounterAt(c, r)) {
                score++;
                row += y;
                col += x;
            }
        }
        return score;
    }

    /*
    *
    * Methods used to set the direction for the method movePoints
    *
    * */
    private int getVPoints(int row, int col, Connect4GameState gameState) {
        if (row > Connect4GameState.NUM_ROWS - Connect4GameState.NUM_IN_A_ROW_TO_WIN) {
            return 0;
        }
        return movePoints(row, col, 1, 0, gameState);
    }

    private int getHPoints(int row, int col, Connect4GameState gameState) {
        if (col > Connect4GameState.NUM_COLS - Connect4GameState.NUM_IN_A_ROW_TO_WIN) {
            return 0;
        }
        return movePoints(row, col, 0, 1, gameState);
    }

    private int getDiagonalOnePoints(int row, int col, Connect4GameState gameState) {
        if (row > Connect4GameState.NUM_ROWS - Connect4GameState.NUM_IN_A_ROW_TO_WIN
            || col > Connect4GameState.NUM_COLS - Connect4GameState.NUM_IN_A_ROW_TO_WIN) {
            return 0;
        }
        return movePoints(row, col, 1, 1, gameState);
    }

    private int getDiagonalTwoPoints(int row, int col, Connect4GameState gameState) {
        if (row <= Connect4GameState.NUM_ROWS - Connect4GameState.NUM_IN_A_ROW_TO_WIN
            || col > Connect4GameState.NUM_COLS - Connect4GameState.NUM_IN_A_ROW_TO_WIN) {
            return 0;
        }
        return movePoints(row, col, -1, 1, gameState);
    }

}

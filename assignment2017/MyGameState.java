package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.IllegalColumnException;
import assignment2017.codeprovided.IllegalRowException;

/**
 * MyGameState.java
 * The state of the connect4 game
 *
 * @author Jake Sturgeon
 * @version 1.0 on 07/03/2017
 */
public class MyGameState extends Connect4GameState {

    //instance variables
    private int[][] board;
    private int turn;
    private int lastPlacedRow;
    private int lastPlacedCol;

    /**
     * Default constructor
     */
    public MyGameState() {
    }

    //private only used for copying a game state
    private MyGameState(int[][] board, int turn) {
        this.board = board;
        this.turn = turn;
    }

    /**
     * Method used to start the game. It sets the board to empty and sets the first turn to red.
     */
    @Override
    public void startGame() {
        //create new board instance
        board = new int[NUM_ROWS][NUM_COLS];
        //set all elements to EMPTY
        for (int c = 0; c < NUM_COLS; c++) {
            for (int r = 0; r < NUM_ROWS; r++) {
                board[r][c] = EMPTY;
            }
        }
        //set the turn to red
        turn = RED;
    }

    /**
     * Method that places the counter in the correct column
     *
     * @param col the column in which to drop the counter, in the range 0-6
     * @throws ColumnFullException On the column being full
     * @throws IllegalColumnException On the inputted column being out of bounds
     * @see ColumnFullException
     * @see IllegalColumnException
     */
    @Override
    public void move(int col) throws ColumnFullException, IllegalColumnException {
        //handle exceptions
        if (col < 0 || col >= NUM_COLS) {
            throw new IllegalColumnException(col);
        }
        if (isColumnFull(col)) {
            throw new ColumnFullException(col);
        }

        //loop to next empty space in the col
        //catch errors
        int j = 0;
        try {
            while (getCounterAt(col, j) != EMPTY) {
                j++;
            }
        } catch (IllegalColumnException e) {
            System.out.println(e + " in MyGameState.java Method move");

        } catch (IllegalRowException e) {
            System.out.println(e + " in MyGameState.java Method move");
        }

        //set the found position to the current turn
        board[j][col] = whoseTurn();
        //change the turn
        changeTurn();
        //update the last position that was set
        lastPlacedRow = j;
        lastPlacedCol = col;


    }

    /**
     * @return The current turn of the state
     */
    @Override
    public int whoseTurn() {
        return turn;
    }

    /**
     * Method that returns the counter at the index col, row
     *
     * @param col the column of the position being queried (in the range 0-6)
     * @param row the row of the position being queried (in the range 0-5)
     * @return The counter at col, row
     * @throws IllegalColumnException On the inputted column being out of bounds
     * @throws IllegalRowException On the inputted row being out of bounds
     * @see IllegalColumnException
     * @see IllegalRowException
     */
    @Override
    public int getCounterAt(final int col, final int row)
        throws IllegalColumnException, IllegalRowException {
        //handle exceptions
        if (col < 0 || col >= NUM_COLS) {
            throw new IllegalColumnException(col);
        }
        if (row < 0 || row >= NUM_ROWS) {
            throw new IllegalRowException(row);
        }
        //return the counter at the current index
        return board[row][col];
    }

    /**
     * Used to see if the board is full
     *
     * @return True if the board has no empty spaces left
     */
    @Override
    public boolean isBoardFull() {
        //assume board is full
        boolean isFull = true;

        int i = 0;
        try {
            //if a column is not full => the board is not full and the loop ends
            while (isFull && i < NUM_COLS) {
                isFull = isColumnFull(i);
                i++;
            }
        } catch (IllegalColumnException e) {
            System.out.println(e + " in MyGameState.java Method isBoardFull");
        }

        return isFull;
    }

    /**
     * used to see if the selected column is full
     *
     * @param col the column being queried
     * @return True if the column has no more empty spaces
     * @throws IllegalColumnException On the inputted column being out of bounds
     * @see IllegalColumnException
     */
    @Override
    public boolean isColumnFull(int col) throws IllegalColumnException {
        //handle exceptions
        if (col < 0 || col >= NUM_COLS) {
            throw new IllegalColumnException(col);
        }

        //assume the column is full
        boolean isFull = true;
        try {
            //if the top is empty => the column is empty
            isFull = getCounterAt(col, NUM_ROWS - 1) != EMPTY;
        } catch (IllegalRowException e) {
            System.out.println(e + " in MyGameState.java Method isColumnFull");
        } catch (IllegalColumnException e) {
            System.out.println(e + " in MyGameState.java Method isColumnFull");
        }

        return isFull;
    }

    /**
     * Method to obtain the winner
     * <p>If there isn't a winner the colour returned is EMPTY</p>
     *
     * @return The winners colour
     */
    @Override
    public int getWinner() {
                                /*
         * To please the test as well as the game
         * Temporarily change the turn colour
         */
        int temp;
        if (whoseTurn() == RED) {
            temp = YELLOW;
        } else {
            temp = RED;
        }

        //test horizontally
        //set x to 1 as the placed counter will always be correct
        int x = 1;
        int i = 1;
        //to check if zero has been found
        boolean leftZeroFound = false;
        boolean rightZeroFound = false;
        while (!rightZeroFound || !leftZeroFound) {
            //check to right of last placed
            if (!rightZeroFound && inBounds(0, i)
                && board[lastPlacedRow][lastPlacedCol + i] == temp) {
                x++;
            } else {
                rightZeroFound = true;
            }

            //check to left of last placed
            if (!leftZeroFound && inBounds(0, -i)
                && board[lastPlacedRow][lastPlacedCol - i] == temp) {
                x++;
            } else {
                leftZeroFound = true;
            }

            i++;
        }
        //return the winning colour if a row has been found
        if (x >= NUM_IN_A_ROW_TO_WIN) {
            return temp;
        }

        //check vertically
        //reset x to 1 as the placed counter will always be correct
        x = 1;
        i = 1;
        //to check if zero has been found
        boolean zeroFound = false;
        while (!zeroFound) {
            //check below last placed
            if (inBounds(-i, 0) && board[lastPlacedRow - i][lastPlacedCol] == temp) {
                x++;
            } else {
                zeroFound = true;
            }

            i++;
        }
        //return the winning colour if a row has been found
        if (x >= NUM_IN_A_ROW_TO_WIN) {
            return temp;
        }

        //check top right diagonal
        //reset x to 1 as the placed counter will always be correct
        x = 1;
        i = 1;
        //to check if zero has been found
        leftZeroFound = false;
        rightZeroFound = false;
        while (!rightZeroFound || !leftZeroFound) {
            //check top right diagonal
            if (!rightZeroFound && inBounds(i, i)
                && board[lastPlacedRow + i][lastPlacedCol + i] == temp) {
                x++;
            } else {
                rightZeroFound = true;
            }

            //check bottom left
            if (!leftZeroFound && inBounds(-i, -i)
                && board[lastPlacedRow - i][lastPlacedCol - i] == temp) {
                x++;
            } else {
                leftZeroFound = true;
            }

            i++;
        }
        if (x >= NUM_IN_A_ROW_TO_WIN) {
            return temp;
        }

        //check top left
        //set x to 1 as the placed counter will always be correct
        x = 1;
        i = 1;
        //to check if zero has been found
        leftZeroFound = false;
        rightZeroFound = false;
        while (!rightZeroFound || !leftZeroFound) {
            //check top left
            if (!leftZeroFound && inBounds(i, -i)
                && board[lastPlacedRow + i][lastPlacedCol - i] == temp) {
                x++;
            } else {
                leftZeroFound = true;
            }

            //check bottom right
            if (!rightZeroFound && inBounds(-i, i)
                && board[lastPlacedRow - i][lastPlacedCol + i] == temp) {
                x++;
            } else {
                rightZeroFound = true;
            }

            i++;
        }
        //return the winning colour if a row has been found
        if (x >= NUM_IN_A_ROW_TO_WIN) {
            return temp;
        }

        //return Empty if a winner has not been found
        return EMPTY;
    }

    /**
     * Method to see if the game is over
     *
     * @return True if the board is full or if a winner has been found
     */
    @Override
    public boolean gameOver() {
        //true if the board full or a winner has been found
        return getWinner() != EMPTY || isBoardFull();
    }

    /**
     * Deep copy of the current state of MyGameState
     *
     * @return A deep copy of the current state of MyGameState
     */
    @Override
    public Connect4GameState copy() {
        //create new array object
        int[][] copy = new int[NUM_ROWS][NUM_COLS];

        //copy the elements over to the new array
        for (int i = 0; i < NUM_ROWS; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, NUM_COLS);
        }

        //copies the current state of the game;
        return new MyGameState(copy, this.whoseTurn());
    }

    //method that checks if the array pointer is in bounds
    private boolean inBounds(int deltaRow, int deltaCol) {
        boolean row = false;
        boolean col = false;

        if (lastPlacedRow + deltaRow < NUM_ROWS && lastPlacedRow + deltaRow >= 0) {
            row = true;
        }
        if (lastPlacedCol + deltaCol < NUM_COLS && lastPlacedCol + deltaCol >= 0) {
            col = true;
        }

        return row && col;
    }

    //private method that toggles the current turn to the next
    private void changeTurn() {
        if (turn == RED) {
            turn = YELLOW;
        } else {
            turn = RED;
        }
    }
}

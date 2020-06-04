package assignment2017;

import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;

/**
 * Connect4ConsoleDisplay.java
 * Prints the current state of the gamestate
 *
 * @author Jake Sturgeon
 * @version 1.0 on 01/03/2017
 */
public class Connect4ConsoleDisplay implements Connect4Displayable {

    //instant variables
    private final Connect4GameState GAMESTATE;

    /**
     * Constructor
     *
     * @param GAMESTATE The game state that you wish to display to the screen
     */
    public Connect4ConsoleDisplay(Connect4GameState GAMESTATE) {
        this.GAMESTATE = GAMESTATE;
    }

    /**
     * Used to print the current state of the game to the screen
     */
    @Override
    public void displayBoard() {
        //New line to separate from last display
        System.out.println();
        //from the contents of the game state
        for (int row = Connect4GameState.NUM_ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < Connect4GameState.NUM_COLS; col++) {
                //print the left corner boundary
                if (col == 0) {
                    System.out.print("| ");
                }
                //print the game states counter at i, j
                if (GAMESTATE.getCounterAt(col, row) == Connect4GameState.RED) {
                    System.out.print("R ");
                } else if (GAMESTATE.getCounterAt(col, row) == Connect4GameState.YELLOW) {
                    System.out.print("Y ");
                } else {
                    System.out.print("  ");
                }

                //print the right corner boundary
                if (col == Connect4GameState.NUM_ROWS) {
                    System.out.print("|");
                }
            }
            //new line after each row
            System.out.println();
        }
        //print bottom row boundary
        System.out.print(" ");
        for (int i = 0; i < Connect4GameState.NUM_COLS; i++) {
            System.out.print("--");
        }
        System.out.print("-");
        System.out.println();
        //print the column numbers
        System.out.print(" ");
        for (int i = 0; i < Connect4GameState.NUM_COLS; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
    }
}

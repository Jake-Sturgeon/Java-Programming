package assignment2017;

import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;

/**
 * Connect4.java
 * Controls the game's architecture
 *
 * @author Jake Sturgeon
 * @version 1.0 on 01/03/2017
 */
public class Connect4 {

    //instance variables
    private final Connect4GameState GAMESTATE;
    private final Connect4Player RED;
    private final Connect4Player YELLOW;
    private final Connect4Displayable DISPLAY;

    /**
     * Constructor.
     *
     * @param GAMESTATE The GameState to be used during the game
     * @param RED The RED player
     * @param YELLOW The YELLOW player
     * @param DISPLAY The DISPLAY to be used during the game
     */
    public Connect4(Connect4GameState GAMESTATE, Connect4Player RED, Connect4Player YELLOW,
        Connect4Displayable DISPLAY) {

        this.GAMESTATE = GAMESTATE;
        this.RED = RED;
        this.YELLOW = YELLOW;
        this.DISPLAY = DISPLAY;
    }

    /**
     * Method that runs Connect4's architecture
     */
    public void play() {
        //Init game state
        GAMESTATE.startGame();

        //Loop until a winner has been found or the board is full
        boolean gameOver;
        do {
            DISPLAY.displayBoard();
            /*
             * checks if the player is a bot
             * if not it waits fot the button event in Connect4ConsoleDisplayGUI
             * Checks if game over before move is played to prevent thread complications
             */
            gameOver = GAMESTATE.gameOver();
            if (!gameOver) {
                if (GAMESTATE.whoseTurn() == Connect4GameState.RED) {
                    //if its not a keyboard player it doesn't have to wait for input
                    if (!(RED instanceof KeyboardPlayer)) {
                        RED.makeMove(GAMESTATE);
                    }
                } else if (GAMESTATE.whoseTurn() == Connect4GameState.YELLOW) {
                    //prevents the nogui screen outputting forever
                    if (DISPLAY instanceof Connect4ConsoleDisplay) {
                        YELLOW.makeMove(GAMESTATE);
                    }
                }
            }
        } while (!gameOver);
        //DISPLAY last state
        DISPLAY.displayBoard();
        if (!GAMESTATE.isBoardFull()) {
            //find the winner and output victory message
            if (GAMESTATE.getWinner() == Connect4GameState.RED) {
                if (DISPLAY instanceof Connect4ConsoleDisplay) {
                    System.out.println("Red Wins!");
                } else {
                    ((Connect4ConsoleDisplayGUI) DISPLAY).playWinner("Red");
                }
            } else {
                if (DISPLAY instanceof Connect4ConsoleDisplay) {
                    System.out.println("Red Wins!");
                } else {
                    ((Connect4ConsoleDisplayGUI) DISPLAY).playWinner("Yellow");
                }
            }
        } else {
            //if it was a draw print draw
            if (DISPLAY instanceof Connect4ConsoleDisplay) {
                System.out.println("It was a draw!");
            } else {
                ((Connect4ConsoleDisplayGUI) DISPLAY).playWinner("Draw");
            }
        }
    }
}

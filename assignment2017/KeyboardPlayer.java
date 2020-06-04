package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * KeyboardPlayer.java
 * Handles the users input
 *
 * @author Jake Sturgeon
 * @version 1.0 on 01/03/2017
 */
public class KeyboardPlayer extends Connect4Player {

    /**
     * Allows the player to make their move
     *
     * @param gameState the current Connect4 game state
     */
    @Override
    public void makeMove(Connect4GameState gameState) {
        //set up scanner
        Scanner sc = new Scanner(System.in);
        int move = -1;
        //do until move is valid, catch all errors

        System.out.println("Please enter a column number, 0 to 6 followed by return.");
        do {
            try {
                move = sc.nextInt();
                //set the counter in the chosen col
                gameState.move(move);
            } catch (ColumnFullException e) {
                //allow the player to make there move again
                move = -1;
                System.out.println("Please enter a column number, 0 to 6 followed by return.");
            } catch (InputMismatchException e) {
                System.out.println("Please enter a column number, 0 to 6 followed by return.");
                //reset scanner
                sc = new Scanner(System.in);
            } catch (IllegalColumnException e) {
                System.out.println("Please enter a column number, 0 to 6 followed by return.");
            }
        } while (move < 0 || move >= Connect4GameState.NUM_COLS);
    }
}

package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;
import java.util.Random;

/**
 * RandomPlayer.java
 * It acts as a random player
 *
 * @author Jake Sturgeon
 * @version 1.0 on 01/03/2017
 */
public class RandomPlayer extends Connect4Player {

    //instance variable
    private final Random RAND = new Random();

    /**
     * Allows the random player to make their move
     *
     * @param gameState the current Connect4 game state
     */
    @Override
    public void makeMove(Connect4GameState gameState) {
        //init the random object
        int move = -1;

        //do until a move is valid
        do {
            try {
                move = RAND.nextInt(Connect4GameState.NUM_COLS);
                //set the counter in the chosen col
                gameState.move(move);
            } catch (ColumnFullException e) {
                //allow the player to move again
                move = -1;
                System.out.println(e + ". Random Player is trying again");
            } catch (IllegalColumnException e) {
                System.out.println(e + ". Random Player is trying again");
            }
        } while (move < 0 || move >= Connect4GameState.NUM_COLS);
        System.out.println("Computer dropped counter in column " + move);
    }
}

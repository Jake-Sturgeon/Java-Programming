package assignment2017;

import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;

/**
 * PlayConnect4.java
 * Used to start a new game of connect 4
 *
 * @author Jake Sturgeon
 * @version 1.0 on 01/03/2017
 */
public class PlayConnect4 {

    /**
     * This is the main method used to set up the game state, the players, the display to be used
     * and finally starts the connect 4 game.
     *
     * @param args -nogui == Play the game in terminal -gui == play the game with the gui
     */
    public static void main(String[] args) {
        //init a new game state
        Connect4GameState gameState = new MyGameState();

        //if nogui is selected
        if (args.length != 0 && args[0].equals("-nogui")) {
            Connect4Displayable display = new Connect4ConsoleDisplay(gameState);
            Connect4Player red = new IntelligentPlayer();
            Connect4Player yellow = new KeyboardPlayer();

            Connect4 game = new Connect4(gameState, red, yellow, display);
            //run the game
            game.play();
        } else if (args.length != 0 && args[0].equals("-gui")) {
            Connect4Displayable display = new Connect4ConsoleDisplayGUI(gameState);
            Connect4Player yellow = new KeyboardPlayer();
            Connect4Player red;

            //wait for the users choice
            int choice = ((Connect4ConsoleDisplayGUI) display).getUserChoice();

            //if there is no choice => use intelligent player
            switch (choice) {
                case 0:
                    red = new RandomPlayer();
                    break;
                case 1:
                    red = new KeyboardPlayer();
                    break;
                default:
                    red = new IntelligentPlayer();
                    break;
            }

            Connect4 game = new Connect4(gameState, red, yellow, display);
            //run the game
            game.play();
        } else {
            System.out.println("Please specify a gui mode");
        }
    }
}

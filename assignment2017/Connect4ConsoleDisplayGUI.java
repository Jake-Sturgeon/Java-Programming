package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.IllegalColumnException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Connect4ConsoleDisplayGUI.java
 * Display the state of the game with a GUI
 *
 * @author Jake Sturgeon
 * @version 1.0 on 07/03/2017
 */
public class Connect4ConsoleDisplayGUI extends JFrame implements Connect4Displayable {

    //init instance variables
    private final Connect4GameState GAMESTATE;
    private final JPanel CONNECT_4_GRID;
    private final Container CONTENT_PANE;

    /**
     * Constructor
     *
     * @param GAMESTATE The gamestate you want to display
     */
    public Connect4ConsoleDisplayGUI(Connect4GameState GAMESTATE) {
        //set the gamestate
        this.GAMESTATE = GAMESTATE;

        //setup frame
        frame();

        //set the contentpane
        CONTENT_PANE = getContentPane();
        CONTENT_PANE.setLayout(new BorderLayout());

        //set up connect 4 grid
        CONNECT_4_GRID = new JPanel();
        CONNECT_4_GRID.setOpaque(true);
        CONNECT_4_GRID
            .setLayout(new GridLayout(Connect4GameState.NUM_ROWS, Connect4GameState.NUM_COLS));

        //set up containers
        containers();

        //make it visible;
        this.setVisible(true);
    }

    //private method that sets the frames variables
    private void frame() {
        //setup frame
        setTitle("Connect4");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(100 * Connect4GameState.NUM_COLS, 100 * Connect4GameState.NUM_ROWS);
        setLocation((int) dimension.getWidth() / 4, (int) dimension.getHeight() / 4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //method that sets all the buttons and events
    private void containers() {
        //create each counter cell
        for (int row = 0; row < Connect4GameState.NUM_ROWS; row++) {
            for (int col = 0; col < Connect4GameState.NUM_COLS; col++) {
                /*
                 * add a new JPanel to each grid element
                 */
                JPanel jPanel = new JPanel();
                jPanel.setBackground(Color.WHITE);
                jPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN));

                //if the player clicks on a column, a counter is placed in that column
                jPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //get the location of the click and relative width of the window
                        int width = CONTENT_PANE.getSize().width / Connect4GameState.NUM_COLS;
                        int col = e.getComponent().getX() / width;
                        try {
                            GAMESTATE.move(col);
                        } catch (ColumnFullException ex) {
                            System.out.println(ex.getMessage());
                        } catch (IllegalColumnException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        jPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        jPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
                    }
                });
                CONNECT_4_GRID.add(jPanel);
            }
        }

        //Make bottom panel buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 0));
        for (int i = 0; i < Connect4GameState.NUM_COLS; i++) {
            makeButton(String.valueOf(i), buttonPanel);
        }
        buttonPanel.setBackground(Color.WHITE);

        //add buttons to the container
        CONTENT_PANE.add(buttonPanel, BorderLayout.SOUTH);
        CONTENT_PANE.add(CONNECT_4_GRID, BorderLayout.CENTER);

    }

    //private method that set ups the buttons for the bottun panel
    private void makeButton(String name, JPanel buttonPanel) {
        JButton button = new JButton(name);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int col = Integer.valueOf(e.getActionCommand());
                try {
                    GAMESTATE.move(col);
                } catch (ColumnFullException ex) {
                    System.out.println(ex.getMessage());
                } catch (IllegalColumnException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    /**
     * Method that gets the users choice for there opponent
     *
     * @return The choice of opponent - 0 == Random Player 1 == Keyboard Player 2 == Intelligent
     * Player
     */
    public int getUserChoice() {
        JFrame choice = new JFrame();
        String[] options = {"Random", "Keyboard", "Intelligent"};
        String message = "Who do you wish to play";
        String tile = "Choose an option";
        String initialValue = "Intelligent";

        //set up the option window
        int number = JOptionPane.showOptionDialog(choice, message, tile, JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE, null, options, initialValue);

        return number;
    }

    /**
     * Method that prints the winners colour to the screen
     *
     * @param winner The String of the winning colour
     */
    public void playWinner(String winner) {
        JLabel label = new JLabel();

        //sleep so the player can see the winning play
        try {
            Thread.sleep(1300);
            CONNECT_4_GRID.setVisible(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            label.setFont(new Font(null, Font.BOLD, (int) (CONTENT_PANE.getSize().width * 0.1f)));
        }

        label.setText("The winner is " + winner.toUpperCase());
        CONTENT_PANE.add(label, BorderLayout.CENTER);
    }

    /**
     * Displays the current state of the game
     */
    @Override
    public void displayBoard() {
        //i is used as a pointer to each cell
        int i = 0;
        for (int row = Connect4GameState.NUM_ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < Connect4GameState.NUM_COLS; col++) {
                if (GAMESTATE.getCounterAt(col, row) == Connect4GameState.RED) {
                    CONNECT_4_GRID.getComponent(i).setBackground(Color.RED);
                } else if (GAMESTATE.getCounterAt(col, row) == Connect4GameState.YELLOW) {
                    CONNECT_4_GRID.getComponent(i).setBackground(Color.YELLOW);
                } else {
                    CONNECT_4_GRID.getComponent(i).setBackground(Color.WHITE);
                }
                i++;
            }
        }
        //to allow window resizing
        repaint();
    }
}


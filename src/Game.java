
/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This framework
 * is very effective for turn-based games. We STRONGLY recommend you review
 * these lecture slides, starting at slide 8, for more details on
 * Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view, implements a
 * bit of controller functionality through the reset button, and then
 * instantiates a GameBoard. The GameBoard will handle the rest of the game's
 * view and controller functionality, and it will instantiate a TicTacToe object
 * to serve as the game's model.
 */
public class Game implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Uno");
        frame.setLocation(300, 300);

        final String intro = "Time to Play Uno!" + " Two player game. \nThe turns automatically "
                + "alternate between the players once a move is made."
                + "\nplayers start with 7 cards, skip cards skip opponnents turn,"
                + "\nPlus two cards add two automatically to opponnent"
                + "\nAnyColor skips opponnent turn and allows you to play any card"
                + "\nAnyColor +4 is same Any Color, but adds 4 cards to opponnent"
                + "\nIf you don't have a card that works, press the deck button to draw"
                + "\nIf you have more than 7 cards, click swap to see other cards"
                + "\nValid turns are playing appopriate color or matching number" 
                + "\nThe any cards always work"
                + "\nA player wins when they finish their cards!" + "\nTo save a game click save"
                + "\nLoad the game using load when you reopen"
                + "\nPlayer 1 cards are the top row and Player 2 cards are the bottom"
                + "\nFinally, if you want a new game simply click reset!";

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        JOptionPane.showMessageDialog(frame, intro, "Instructions", JOptionPane.PLAIN_MESSAGE);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        control_panel.setLayout(new FlowLayout());
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define
        // it as an
        // anonymous inner class that is an instance of ActionListener with its
        // actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be
        // called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });

        final JButton save = new JButton("save");
        final JButton load = new JButton("load");
        final JButton Swap = new JButton("Swap");
        final JButton deck = new JButton("deck");

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.save();
            }
        });

        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.load();
            }
        });

        deck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.draw();
            }
        });

        Swap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.swap();
            }
        });

        control_panel.add(reset);
        control_panel.add(save);
        control_panel.add(load);
        control_panel.add(Swap);
        control_panel.add(deck);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements
     * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
     * this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
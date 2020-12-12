
/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.*;

/**
 
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Uno uno; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 1200;
    public static final int BOARD_HEIGHT = 1000;
    Font font = new Font("Times New Roman", Font.PLAIN, 30);
    ArrayList<Card> p1curr7 = new ArrayList<Card>(7);
    ArrayList<Card> p2curr7 = new ArrayList<Card>(7);

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key
        // listener.
        setFocusable(true);

        uno = new Uno(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game board based
         * off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                if (uno.getCurrentPlayer()) {
                    Card c = getCard(p);

                    if (uno.playTurn(c)) {
                     // Calls card play method by dynamic dispatch
                        c.play(uno, c);
                    }
                } else if (!uno.getCurrentPlayer()) {
                    Card c = getCard2(p);

                    if (uno.playTurn(c)) {
                        // Calls card play method by dynamic dispatch
                        c.play(uno, c);
                    }
                }

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        uno.reset();
        status.setText("Player 1's Turn");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (uno.getCurrentPlayer()) {
            status.setText("Player 1's Turn");
        } else {
            status.setText("Player 2's Turn");
        }

        int winner = uno.checkWinner();
        if (winner == 1) {
            status.setText("Player 1 wins!!!");
        } else if (winner == 2) {
            status.setText("Player 2 wins!!!");
        }
    }

    /**
     * Draws the game board.
     * This method takes care of all the drawing in the Program
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1200, 1000);

        g.setColor(Color.BLACK);
        g.drawRect(25, 50, 1100, 200);

        g.drawRect(25, 500, 1100, 200);
        ArrayList<Card> player1 = uno.getP1cards();
        ArrayList<Card> player2 = uno.getP2cards();
        for (int i = 0; i < Math.min(player1.size(), 7); i++) {

            Card current = player1.get(i);
            if (!uno.getCurrentPlayer()) {
                g.setColor(Color.WHITE);
                g.drawRect(25, 500, 1100, 200);
            } else if (current instanceof NumberCards) {
                Color c1;
                int n1;
                c1 = ((NumberCards) current).getColor();
                n1 = ((NumberCards) current).getNumber();
                String s = String.valueOf(n1);
                g.setColor(c1);
                g.fillRect(45 + i * 150 + 10, 50 + 10, 130, 180);
                g.setColor(Color.BLACK);
                g.setFont(font);
                g.drawString(s, 45 + i * 150 + 10 + 3, 235);
                p1curr7.add(i, current);
            } else if (current instanceof SkipCards) {
                Color c1;
                c1 = ((SkipCards) current).getColor();
                g.setColor(c1);
                g.fillRect(45 + i * 150 + 10, 50 + 10, 130, 180);
                g.setColor(Color.BLACK);
                g.setFont(font);
                g.drawString("Skip", 45 + i * 150 + 10 + 3, 235);
                p1curr7.add(i, current);
            } else if (current instanceof PlusTwo) {
                Color c1;
                c1 = ((PlusTwo) current).getColor();
                g.setColor(c1);
                g.fillRect(45 + i * 150 + 10, 50 + 10, 130, 180);
                g.setColor(Color.BLACK);
                g.setFont(font);
                g.drawString("+2", 45 + i * 150 + 10 + 3, 235);
                p1curr7.add(i, current);

            } else if (current instanceof AnyColor) {
                g.setColor(Color.BLACK);
                g.fillRect(45 + i * 150 + 10, 50 + 10, 130, 180);
                g.setColor(Color.WHITE);
                g.setFont(font);
                g.drawString("Any Color", 45 + i * 150 + 10 + 3, 235);
                p1curr7.add(i, current);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(45 + i * 150 + 10, 50 + 10, 130, 180);
                g.setColor(Color.WHITE);
                g.setFont(font);
                g.drawString("Any + 4", 45 + i * 150 + 10 + 3, 235);
                p1curr7.add(i, current);
            }

        }

        for (int i = 0; i < Math.min(player2.size(), 7); i++) {
            Card current = player2.get(i);
            if (uno.getCurrentPlayer()) {
                g.setColor(Color.WHITE);
                g.drawRect(25, 50, 1100, 200);
            } else if (current instanceof NumberCards) {
                Color c1;
                int n1;
                c1 = ((NumberCards) current).getColor();
                n1 = ((NumberCards) current).getNumber();
                String s = String.valueOf(n1);
                g.setColor(c1);
                g.fillRect(45 + i * 150 + 10, 500 + 10, 130, 180);
                g.setColor(Color.BLACK);
                g.setFont(font);
                g.drawString(s, 45 + i * 150 + 10 + 3, 685);
                p2curr7.add(i, current);
            } else if (current instanceof SkipCards) {
                Color c1;
                c1 = ((SkipCards) current).getColor();
                g.setColor(c1);
                g.fillRect(45 + i * 150 + 10, 500 + 10, 130, 180);
                g.setColor(Color.BLACK);
                g.setFont(font);
                g.drawString("Skip", 45 + i * 150 + 10 + 3, 685);
                p2curr7.add(i, current);
            } else if (current instanceof PlusTwo) {
                Color c1;
                c1 = ((PlusTwo) current).getColor();
                g.setColor(c1);
                g.fillRect(45 + i * 150 + 10, 500 + 10, 130, 180);
                g.setColor(Color.BLACK);
                g.setFont(font);
                g.drawString("+2", 45 + i * 150 + 10 + 3, 685);
                p2curr7.add(i, current);

            } else if (current instanceof AnyColor) {
                g.setColor(Color.BLACK);
                g.fillRect(45 + i * 150 + 10, 500 + 10, 130, 180);
                g.setColor(Color.WHITE);
                g.setFont(font);
                g.drawString("Any Color", 45 + i * 150 + 10 + 3, 685);
                p2curr7.add(i, current);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(45 + i * 150 + 10, 500 + 10, 130, 180);
                g.setColor(Color.WHITE);
                g.setFont(font);
                g.drawString("Any + 4", 45 + i * 150 + 10 + 3, 685);
                p2curr7.add(i, current);
            }

        }
        Card current = uno.getinPlay();
        if (current instanceof NumberCards) {
            Color c1;
            int n1;
            c1 = ((NumberCards) current).getColor();
            n1 = ((NumberCards) current).getNumber();
            String s = String.valueOf(n1);
            g.setColor(c1);
            g.fillRect(535, 280, 130, 180);
            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(s, 538, 455);

        } else if (current instanceof SkipCards) {
            Color c1;
            c1 = ((SkipCards) current).getColor();
            g.setColor(c1);
            g.fillRect(535, 280, 130, 180);
            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString("Skip", 538, 455);
        } else if (current instanceof PlusTwo) {
            Color c1;
            c1 = ((PlusTwo) current).getColor();
            g.setColor(c1);
            g.fillRect(535, 280, 130, 180);
            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString("+2", 538, 455);

        } else if (current instanceof AnyColor) {
            g.setColor(Color.BLACK);
            g.fillRect(535, 280, 130, 180);
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString("Any Color", 538, 455);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(535, 280, 130, 180);
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString("Any + 4", 538, 455);
        }

    }

    // Method to help mouse adapter click on right card
    public Card getCard(Point p) {
        Card c;
        if (p.x > 55 && p.x < 185 && p.y > 60 && p.y < 240) {
            c = p1curr7.get(0);
            return c;
        } else if (p.x > 205 && p.x < 335 && p.y > 60 && p.y < 240) {
            c = p1curr7.get(1);
            return c;
        } else if (p.x > 355 && p.x < 485 && p.y > 60 && p.y < 240) {
            c = p1curr7.get(2);
            return c;
        } else if (p.x > 505 && p.x < 635 && p.y > 60 && p.y < 240) {
            c = p1curr7.get(3);
            return c;
        } else if (p.x > 655 && p.x < 785 && p.y > 60 && p.y < 240) {
            c = p1curr7.get(4);
            return c;
        } else if (p.x > 805 && p.x < 935 && p.y > 60 && p.y < 240) {
            c = p1curr7.get(5);
            return c;
        } else if (p.x > 955 && p.x < 1085 && p.y > 60 && p.y < 240) {
            c = p1curr7.get(6);
            return c;
        }
        return null;
    }

    // Helps get player 2 card
    public Card getCard2(Point p) {
        Card c;
        if (p.x > 55 && p.x < 185 && p.y > 510 && p.y < 690) {
            c = p2curr7.get(0);
            return c;
        } else if (p.x > 205 && p.x < 335 && p.y > 510 && p.y < 690) {
            c = p2curr7.get(1);
            return c;
        } else if (p.x > 355 && p.x < 485 && p.y > 510 && p.y < 690) {
            c = p2curr7.get(2);
            return c;
        } else if (p.x > 505 && p.x < 635 && p.y > 510 && p.y < 690) {
            c = p2curr7.get(3);
            return c;
        } else if (p.x > 655 && p.x < 785 && p.y > 510 && p.y < 690) {
            c = p2curr7.get(4);
            return c;
        } else if (p.x > 805 && p.x < 935 && p.y > 510 && p.y < 690) {
            c = p2curr7.get(5);
            return c;
        } else if (p.x > 955 && p.x < 1085 && p.y > 510 && p.y < 690) {
            c = p2curr7.get(6);
            return c;
        }
        return null;
    }

    // allows players to draw from the deck
    public void draw() {
        if (uno.getCurrentPlayer()) {
            ArrayList<Card> p1;
            p1 = uno.getP1cards();
            Card c = uno.draw();
            p1.add(c);
            uno.setP1cards(p1);
            repaint();
        } else if (!uno.getCurrentPlayer()) {
            ArrayList<Card> p2;
            p2 = uno.getP2cards();
            Card c = uno.draw();
            p2.add(c);
            uno.setP2cards(p2);
            repaint();

        }
    }

    // If player has more than 7 cards allows them to see those ones
    public void swap() {
        if (uno.getCurrentPlayer()) {
            ArrayList<Card> p1;
            p1 = uno.getP1cards();
            int size = p1.size();
            if (p1.size() > 7) {
                Card c = p1.remove(0);
                p1.add(size - 1, c);
                uno.setP1cards(p1);
                repaint();

            }
        } else if (!uno.getCurrentPlayer()) {
            ArrayList<Card> p2;
            p2 = uno.getP2cards();
            int size = p2.size();
            if (p2.size() > 7) {
                Card c = p2.remove(0);
                p2.add(size - 1, c);
                uno.setP2cards(p2);
                repaint();
            }
        }
    }

    // This method essentially just uses dynamic dispatch to save the file
    public void save() {
        try {
            FileWriter write = new FileWriter("files/SavedFile.txt", false);
            BufferedWriter towrite = new BufferedWriter(write);
            String save = "";
            if (uno.getCurrentPlayer()) {
                save += "1";
            } else if (!uno.getCurrentPlayer()) {
                save += "2";
            }
            towrite.write(save);
            towrite.flush();
            towrite.newLine();
            LinkedList<Card> store = uno.getDeck();
            ArrayList<Card> p1 = uno.getP1cards();
            ArrayList<Card> p2 = uno.getP2cards();
            Card inplay = uno.getinPlay();
            for (Card c : store) {
                String combine = c.toString();
                towrite.write(combine);
            }
            towrite.flush();
            towrite.newLine();
            for (Card c : p1) {
                String combine = c.toString();
                towrite.write(combine);
            }
            towrite.flush();
            towrite.newLine();
            for (Card c : p2) {
                String combine = c.toString();
                towrite.write(combine);
            }
            towrite.flush();
            towrite.newLine();
            String combine = inplay.toString();
            towrite.write(combine);
            towrite.flush();
            towrite.close();
        } catch (IOException e) {

        }

    }

    // Helper for load
    private Card helper(String combine) {
        if (combine.equals("r0")) {
            return new NumberCards(0, Color.RED);
        } else if (combine.equals("r1")) {
            return new NumberCards(1, Color.RED);
        } else if (combine.equals("r2")) {
            return new NumberCards(2, Color.RED);
        } else if (combine.equals("r3")) {
            return new NumberCards(3, Color.RED);
        } else if (combine.equals("r4")) {
            return new NumberCards(4, Color.RED);
        } else if (combine.equals("r5")) {
            return new NumberCards(5, Color.RED);
        } else if (combine.equals("r6")) {
            return new NumberCards(6, Color.RED);
        } else if (combine.equals("r7")) {
            return new NumberCards(7, Color.RED);
        } else if (combine.equals("r8")) {
            return new NumberCards(8, Color.RED);
        } else if (combine.equals("r9")) {
            return new NumberCards(9, Color.RED);
        } else if (combine.equals("b0")) {
            return new NumberCards(0, Color.BLUE);
        } else if (combine.equals("b1")) {
            return new NumberCards(1, Color.BLUE);
        } else if (combine.equals("b2")) {
            return new NumberCards(2, Color.BLUE);
        } else if (combine.equals("b3")) {
            return new NumberCards(3, Color.BLUE);
        } else if (combine.equals("b4")) {
            return new NumberCards(4, Color.BLUE);
        } else if (combine.equals("b5")) {
            return new NumberCards(5, Color.BLUE);
        } else if (combine.equals("b6")) {
            return new NumberCards(6, Color.BLUE);
        } else if (combine.equals("b7")) {
            return new NumberCards(7, Color.BLUE);
        } else if (combine.equals("b8")) {
            return new NumberCards(8, Color.BLUE);
        } else if (combine.equals("b9")) {
            return new NumberCards(9, Color.BLUE);
        } else if (combine.equals("g0")) {
            return new NumberCards(0, Color.GREEN);
        } else if (combine.equals("g1")) {
            return new NumberCards(1, Color.GREEN);
        } else if (combine.equals("g2")) {
            return new NumberCards(2, Color.GREEN);
        } else if (combine.equals("g3")) {
            return new NumberCards(3, Color.GREEN);
        } else if (combine.equals("g4")) {
            return new NumberCards(4, Color.GREEN);
        } else if (combine.equals("g5")) {
            return new NumberCards(5, Color.GREEN);
        } else if (combine.equals("g6")) {
            return new NumberCards(6, Color.GREEN);
        } else if (combine.equals("g7")) {
            return new NumberCards(7, Color.GREEN);
        } else if (combine.equals("g8")) {
            return new NumberCards(8, Color.GREEN);
        } else if (combine.equals("g9")) {
            return new NumberCards(9, Color.GREEN);
        } else if (combine.equals("y0")) {
            return new NumberCards(0, Color.YELLOW);
        } else if (combine.equals("y1")) {
            return new NumberCards(1, Color.YELLOW);
        } else if (combine.equals("y2")) {
            return new NumberCards(2, Color.YELLOW);
        } else if (combine.equals("y3")) {
            return new NumberCards(3, Color.YELLOW);
        } else if (combine.equals("y4")) {
            return new NumberCards(4, Color.YELLOW);
        } else if (combine.equals("y5")) {
            return new NumberCards(5, Color.YELLOW);
        } else if (combine.equals("y6")) {
            return new NumberCards(6, Color.YELLOW);
        } else if (combine.equals("y7")) {
            return new NumberCards(7, Color.YELLOW);
        } else if (combine.equals("y8")) {
            return new NumberCards(8, Color.YELLOW);
        } else if (combine.equals("y9")) {
            return new NumberCards(9, Color.YELLOW);
        } else if (combine.equals("r#")) {
            return new SkipCards(Color.RED);
        } else if (combine.equals("g#")) {
            return new SkipCards(Color.GREEN);
        } else if (combine.equals("b#")) {
            return new SkipCards(Color.BLUE);
        } else if (combine.equals("y#")) {
            return new SkipCards(Color.YELLOW);
        } else if (combine.equals("r+")) {
            return new PlusTwo(Color.RED);
        } else if (combine.equals("g+")) {
            return new PlusTwo(Color.GREEN);
        } else if (combine.equals("b+")) {
            return new PlusTwo(Color.BLUE);
        } else if (combine.equals("y+")) {
            return new PlusTwo(Color.YELLOW);
        } else if (combine.equals("a$")) {
            return new AnyColor();
        } else {
            return new AnyColorPlus4();
        }
    }

    // Load method
    public void load() {
        try {
            FileReader read = new FileReader("files/SavedFile.txt");
            BufferedReader toread = new BufferedReader(read);
            LinkedList<Card> createdeck = new LinkedList<Card>();
            ArrayList<Card> createp1 = new ArrayList<Card>();
            ArrayList<Card> createp2 = new ArrayList<Card>();
            Card createinplay;
            String player = toread.readLine();
            if (player.equals("1")) {
                uno.setPlayer1(true);
            } else if (player.equals("2")) {
                uno.setPlayer1(false);
            }
            String readdeck = toread.readLine();
            for (int i = 0; i < readdeck.length(); i = i + 2) {
                String one = Character.toString(readdeck.charAt(i));
                String two = Character.toString(readdeck.charAt(i + 1));
                String combine = one + two;
                Card c = helper(combine);
                createdeck.add(c);
            }
            uno.setDeck(createdeck);
            String readp1 = toread.readLine();
            for (int i = 0; i < readp1.length(); i = i + 2) {
                String one = Character.toString(readp1.charAt(i));
                String two = Character.toString(readp1.charAt(i + 1));
                String combine = one + two;
                Card c = helper(combine);
                createp1.add(c);
            }
            uno.setP1cards(createp1);
            String readp2 = toread.readLine();
            for (int i = 0; i < readp2.length(); i = i + 2) {
                String one = Character.toString(readp2.charAt(i));
                String two = Character.toString(readp2.charAt(i + 1));
                String combine = one + two;
                Card c = helper(combine);
                createp2.add(c);
            }
            uno.setP2cards(createp2);
            String readinplay = toread.readLine();
            createinplay = helper(readinplay);
            uno.setInPlay(createinplay);
            repaint();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } catch (NullPointerException e) {
            
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
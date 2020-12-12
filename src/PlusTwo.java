import java.awt.Color;
import java.util.ArrayList;

public class PlusTwo implements Card {
    private Color color;

    public PlusTwo(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    // updates game state
    @Override
    public void play(Uno u, Card c) {
        boolean turn = u.getCurrentPlayer();
        if (turn) {
            ArrayList<Card> p1;
            p1 = u.getP1cards();
            p1.remove(c);
            u.setP1cards(p1);
            u.setInPlay(c);
            u.setPlayer1(false);
            ArrayList<Card> p2;
            p2 = u.getP2cards();
            Card d1 = u.draw();
            Card d2 = u.draw();
            p2.add(d1);
            p2.add(d2);
            u.setP2cards(p2);
        } else {
            ArrayList<Card> p2;
            p2 = u.getP2cards();
            p2.remove(c);
            u.setP2cards(p2);
            u.setInPlay(c);
            u.setPlayer1(true);
            ArrayList<Card> p1;
            p1 = u.getP1cards();
            Card d1 = u.draw();
            Card d2 = u.draw();
            p1.add(d1);
            p1.add(d2);
            u.setP1cards(p1);
        }

    }

    // Saving Purposes
    @Override
    public String toString() {
        String c1;
        if (color.equals(Color.RED)) {
            c1 = "r";
        } else if (color.equals(Color.BLUE)) {
            c1 = "b";
        } else if (color.equals(Color.GREEN)) {
            c1 = "g";
        } else {
            c1 = "y";
        }
        return c1 + "+";
    }

}

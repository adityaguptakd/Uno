import java.awt.Color;
import java.util.ArrayList;

public class SkipCards implements Card {
    private Color color;

    public SkipCards(Color color) {
        this.color = color;
    }

    // Updates game state
    @Override
    public void play(Uno u, Card c) {
        boolean turn = u.getCurrentPlayer();
        if (turn) {
            ArrayList<Card> p1;
            p1 = u.getP1cards();
            p1.remove(c);
            u.setP1cards(p1);
            u.setInPlay(c);
        } else {
            ArrayList<Card> p2;
            p2 = u.getP2cards();
            p2.remove(c);
            u.setP2cards(p2);
            u.setInPlay(c);
        }

    }

    public Color getColor() {
        return color;
    }

    // Loading purposes
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
        return c1 + "#";
    }

}

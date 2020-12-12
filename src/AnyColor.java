import java.util.ArrayList;

public class AnyColor implements Card {

    // Updates Game State
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

    // Saving Purposes
    @Override
    public String toString() {
        return "a$";
    }

}

import java.util.ArrayList;

public class AnyColorPlus4 implements Card {

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
            ArrayList<Card> p2;
            p2 = u.getP2cards();
            Card d1 = u.draw();
            Card d2 = u.draw();
            Card d3 = u.draw();
            Card d4 = u.draw();
            p2.add(d1);
            p2.add(d2);
            p2.add(d3);
            p2.add(d4);
            u.setP2cards(p2);
        } else {
            ArrayList<Card> p2;
            p2 = u.getP2cards();
            p2.remove(c);
            u.setP2cards(p2);
            u.setInPlay(c);
            ArrayList<Card> p1;
            p1 = u.getP1cards();
            Card d1 = u.draw();
            Card d2 = u.draw();
            Card d3 = u.draw();
            Card d4 = u.draw();
            p1.add(d1);
            p1.add(d2);
            p1.add(d3);
            p1.add(d4);
            u.setP1cards(p1);
        }

    }
    
    // Saving Purposes
    @Override
    public String toString() {
        return "p*";
    }

}

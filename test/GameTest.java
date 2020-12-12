import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * You can use this file (and others) to test your implementation.
 */

public class GameTest {
    private Uno uno;

    @BeforeEach
    public void setUp() {
        uno = new Uno();
    }

    @Test // Check for Winner p1
    public void checkForWinnerTest() {
        ArrayList<Card> p1 = new ArrayList<Card>();
        uno.setP1cards(p1);
        assertEquals(uno.checkWinner(), 1);
    }

    @Test // Check for Winner p2
    public void checkForWinnerTest2() {
        ArrayList<Card> p2 = new ArrayList<Card>();
        uno.setP2cards(p2);
        assertEquals(uno.checkWinner(), 2);
    }

    @Test // Check for No Winner
    public void checkForWinnerTest3() {
        ArrayList<Card> p2 = new ArrayList<Card>();
        NumberCards c = new NumberCards(1, Color.RED);
        p2.add(c);
        uno.setP2cards(p2);
        assertEquals(uno.checkWinner(), 0);
    }

    @Test // Can't play Turn when Game is Over
    public void checkForPlayTurnTest() {
        ArrayList<Card> p1 = new ArrayList<Card>();
        uno.setP1cards(p1);
        uno.checkWinner();
        NumberCards c = new NumberCards(1, Color.RED);
        uno.setInPlay(c);
        assertTrue(!uno.playTurn(c));
    }

    @Test // Another Check for Play Turn
    public void checkforPlayTurnTest2() {
        AnyColor c = new AnyColor();
        uno.setInPlay(c);
        NumberCards c1 = new NumberCards(1, Color.RED);
        assertTrue(uno.playTurn(c1));
    }

    @Test // Can't play a blue 1 when the in play card is a red 2 for example
    public void checkforPlayTurnTest3() {
        NumberCards c = new NumberCards(2, Color.RED);
        uno.setInPlay(c);
        NumberCards c1 = new NumberCards(1, Color.BLUE);
        assertTrue(!uno.playTurn(c1));
    }

    @Test // Check Deck Size
    public void deckSizeTest() {
        LinkedList<Card> cards = uno.getDeck();
        // Should be 97 because 7 cards to each player and one card initialized to be
        // inplay
        assertEquals(cards.size(), 97);
    }

    @Test // Check Plus Two adds two to opponent
    public void plus2Test() {
        uno.setPlayer1(false);
        NumberCards c = new NumberCards(2, Color.RED);
        uno.setInPlay(c);
        PlusTwo c1 = new PlusTwo(Color.RED);
        assertTrue(uno.playTurn(c1));
        c1.play(uno, c1);
        ArrayList<Card> cards = uno.getP1cards();
        assertEquals(cards.size(), 9);
    }

    @Test // Check Any Plus 4 adds 4 and skips opponent turn
    public void anyPlus4Test() {
        uno.setPlayer1(false);
        NumberCards c = new NumberCards(2, Color.RED);
        uno.setInPlay(c);
        AnyColorPlus4 c1 = new AnyColorPlus4();
        assertTrue(uno.playTurn(c1));
        c1.play(uno, c1);
        ArrayList<Card> cards = uno.getP1cards();
        assertTrue(!uno.getCurrentPlayer());
        assertEquals(cards.size(), 11);
    }

    @Test // Check Skip skips opponent turn
    public void skipTest() {
        NumberCards c = new NumberCards(2, Color.RED);
        uno.setInPlay(c);
        SkipCards c1 = new SkipCards(Color.RED);
        assertTrue(uno.playTurn(c1));
        assertTrue(uno.getCurrentPlayer());
    }
}

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Uno {

    private LinkedList<Card> deck;
    private boolean player1;
    private boolean gameOver;
    private ArrayList<Card> p1cards;
    private ArrayList<Card> p2cards;
    private Card inPlay;

    /**
     * Constructor sets up game state.
     */
    public Uno() {
        reset();
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is taken or
     * after the game has ended. If the turn is successful and the game has not
     * ended, the player is changed. If the turn is unsuccessful or the game has
     * ended, the player is not changed.
     * 
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
    public boolean playTurn(Card card) {
        if (card == null || gameOver) {
            return false;
        }
        Color play;
        int number = -1;
        if (inPlay instanceof NumberCards) {
            play = ((NumberCards) inPlay).getColor();
            number = ((NumberCards) inPlay).getNumber();
        } else if (inPlay instanceof PlusTwo) {
            play = ((PlusTwo) inPlay).getColor();

        } else if (inPlay instanceof SkipCards) {
            play = ((SkipCards) inPlay).getColor();
        } else {
            play = Color.ORANGE;
        }
        if (play.equals(Color.ORANGE)) {
            return true;
        } else if (card instanceof NumberCards) {
            Color c = ((NumberCards) card).getColor();
            int n = ((NumberCards) card).getNumber();
            if (!c.equals(play) && !(n == number)) {
                return false;
            }
        } else if (card instanceof SkipCards) {
            Color c = ((SkipCards) card).getColor();
            if (!c.equals(play) && !(card.getClass().equals(inPlay.getClass()))) {
                return false;
            }
        } else if (card instanceof PlusTwo) {
            Color c = ((PlusTwo) card).getColor();
            if (!c.equals(play) && !(card.getClass().equals(inPlay.getClass()))) {
                return false;
            }

        }
        checkWinner();
        return true;

    }

    /**
     * 
     * 
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2 has
     *         won.
     */
    public int checkWinner() {
        if (p1cards.isEmpty()) {
            gameOver = true;
            return 1;
        } else if (p2cards.isEmpty()) {
            gameOver = true;
            return 2;
        } else {
            return 0;
        }

    }

    // Creates Deck and then shuffles it
    public LinkedList<Card> createDeck() {
        LinkedList<Card> create = new LinkedList<Card>();
        for (int i = 0; i < 10; i++) {
            create.add(new NumberCards(i, Color.RED));
            create.add(new NumberCards(i, Color.RED));
        }
        for (int i = 0; i < 10; i++) {
            create.add(new NumberCards(i, Color.YELLOW));
            create.add(new NumberCards(i, Color.YELLOW));
        }
        for (int i = 0; i < 10; i++) {
            create.add(new NumberCards(i, Color.GREEN));
            create.add(new NumberCards(i, Color.GREEN));
        }
        for (int i = 0; i < 10; i++) {
            create.add(new NumberCards(i, Color.BLUE));
            create.add(new NumberCards(i, Color.BLUE));
        }
        for (int i = 0; i < 4; i++) {
            create.add(new SkipCards(Color.RED));
        }
        for (int i = 0; i < 4; i++) {
            create.add(new SkipCards(Color.YELLOW));
        }
        for (int i = 0; i < 4; i++) {
            create.add(new SkipCards(Color.GREEN));
        }
        for (int i = 0; i < 4; i++) {
            create.add(new SkipCards(Color.BLUE));
        }
        for (int i = 0; i < 2; i++) {
            create.add(new PlusTwo(Color.RED));
        }
        for (int i = 0; i < 2; i++) {
            create.add(new PlusTwo(Color.YELLOW));
        }
        for (int i = 0; i < 2; i++) {
            create.add(new PlusTwo(Color.GREEN));
        }
        for (int i = 0; i < 2; i++) {
            create.add(new PlusTwo(Color.BLUE));
        }
        for (int i = 0; i < 4; i++) {
            create.add(new AnyColor());
        }
        for (int i = 0; i < 4; i++) {
            create.add(new AnyColorPlus4());
        }
        Collections.shuffle(create);
        return create;
    }

    // updates game state when player draws
    public Card draw() {
        if (deck.isEmpty()) {
            deck = createDeck();
            deck.removeAll(p1cards);
            deck.removeAll(p2cards);
        }
        Card c = deck.remove();
        return c;
    }

    // First card in play should be number card
    public boolean check(Card c) {
        boolean check = true;
        if (c instanceof AnyColor || c instanceof AnyColorPlus4 
                || c instanceof PlusTwo || c instanceof SkipCards) {
            check = false;
        }
        return check;
    }

    public void reset() {
        deck = createDeck();
        p1cards = new ArrayList<Card>();
        p2cards = new ArrayList<Card>();
        Card c = draw();
        boolean check = check(c);
        while (!check) {
            deck.addLast(c);
            c = draw();
            check = check(c);
        }
        inPlay = c;

        for (int i = 0; i < 7; i++) {
            p1cards.add(draw());
        }
        for (int i = 0; i < 7; i++) {
            p2cards.add(draw());
        }
        gameOver = false;
        player1 = true;
    }

    /**
     * getCurrentPlayer is a getter for the player whose turn it is in the game.
     * 
     * @return true if it's Player 1's turn, false if it's Player 2's turn.
     */
    public boolean getCurrentPlayer() {
        return player1;
    }

    public void setPlayer1(boolean val) {
        player1 = val;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     * 
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public Card getinPlay() {
        return inPlay;
    }

    public void setInPlay(Card inPlay) {
        this.inPlay = inPlay;
    }

    public ArrayList<Card> getP1cards() {
        return p1cards;
    }

    public void setP1cards(ArrayList<Card> cards) {
        p1cards = cards;
    }

    public ArrayList<Card> getP2cards() {
        return p2cards;
    }

    public void setP2cards(ArrayList<Card> cards) {
        p2cards = cards;
    }

    public LinkedList<Card> getDeck() {
        return deck;
    }

    public void setDeck(LinkedList<Card> deck) {
        this.deck = deck;
    }

}

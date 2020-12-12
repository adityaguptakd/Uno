Hi, this file outlines the 4 necessary features of the game.
I implemented everything I said I would implement in my project proposal so 
no unexpected surprises here.

Concept 1: Collections
My use of collections was using LinkedList and ArrayList to model the state of my game.
I used a LinkedList to model the deck which was a good implementation because LinkeLists
allow a good ability to simply remove the first element in the deck, and this is important
in my game to allow players to draw from the deck, and they should only be drawing from the top
and the LinkedList implementation forces them to only be able to do this. Any array really
would have been a clunky implementation for something like a deck because the size of the 
deck also keeps changing as the game goes on and lists are way better for this adding and 
removing capability.

I used ArrayLists to model both players current cards. The array list was a good choice
because players cards are constantly changing and the amount of cards they hold is changing
every turn so Arraylists allow for this quick resizing ability. Also, Arraylists allow us
to quickly get cards from a specific index because players can obviously play any card from 
there array list, so we have to quickly be able to get that card. So array lists allow the
benefit we get from array's while also giving this resizing ability which is critical to uno.

Concept: Dynamic Dispatch and Inheritance
My use of Dynamic Dispatch and inheritance is crucial to my game. First of all, without
this my game code would be extremely terrible to read and I don't even know if it would
be possible to implement. I have a general Card interface which just specifies a single
play method which obviously every type of card has to implement. When a user clicks on a card,
I have a way to tell which card it is and then I just call card.play() and then based on the 
dynamic type, the respective play method is called and the game state is updated accordingly. 
Next, I also use dynamic dispatch when saving the game. Every implementation of the card interface
has a toString method which converts a respective card to a sting and then this string is stored
in the file. This made my code a lot easier to read rather than having a ton of if else statements.

Concept: JUnit Tests
I have test most cases for the game. i test the end conditions, I test to make sure that only
valid cards can be played. I test to make sure that the game is being initialized correctly. 
I basically test almost all features of the underlying game state and they have nothing to do with
the GUI so I think this should be good. 

Concept: FileIO
My FileIo is all taken care of in the GameBoard Class. Basically, I store everything in a text file.
The first line of the text file is who's turn it is, the second line specifies  what is in the deck.
The third Line specifies p1's current cards. The fourth line specifies p2's current cards.
The last line specifies what card is currently in play. Most of this is taken care of by the 
toString method implemented in each implementation of Card. This way each card corresponds to a
specific unique string so I know exactly how to reload the game. 

Loading is the only think I was not sure how to do better. I have a bunch of if else statements
specifying how to read the file. Reading who's turn is easy. Reading the deck and such was a bit
more complicated. I read two characters at a time because that is how I stored the cards. Each
two character string corresponds to a unique card so that way there is no problems. Finally,
I just update the game state using this new loaded information and repaint so that the users
see the loaded game. 
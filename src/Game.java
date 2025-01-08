import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game
{
    
    ArrayList<Card> deck;
    ArrayList<Card> hand;

    JFrame gamewindow = new JFrame("Joker Poker");
    JPanel gamebg = new JPanel();
    
    private class Card
    {
        String suit;
        String rank;

        Card(String suit, String rank)
        {
            this.suit = suit;
            this.rank = rank;
        }

        // Overload toString method
        public String toString() { return rank + " of " + suit ; }

        public int getValue()
        {
            switch (rank) 
            {
                case "A":  return 11;
                case "K": return 10;
                case "Q": return 10;
                case "J": return 10;

                default: return Integer.parseInt(rank);
            }
        }
    }

    int windowWidth = 600;
    int windowHeight = 600;


    Game()
    {
        startGame();
    }

    public void startGame()
    {
        buildGUI();
        buildDeck();
        shuffleDeck();
        drawHand();

    }

    public void buildDeck()
    {
        deck = new ArrayList<Card>();

        String[] suits = {"C", "D", "H", "S"};
        String[] ranks = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

        for(int i=0; i<suits.length; i++)
        {
            for(int j=0; j<ranks.length; j++)
            {
                Card card = new Card(suits[i],ranks[j]);
                deck.add(card);
            }
        }

        // Error Checking: Print Deck
        //System.out.println("Building Deck:");
        //System.out.println(deck);
    }

    public void shuffleDeck()
    {
        Random random = new Random();
        for (int i = (deck.size() - 1); i>0; i--)
        {
            int j = random.nextInt(i + 1); // Get a random index from 0 to i
            // Swap deck[i] with deck[j]
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }

        //// Error Checking : Print shuffled deck
        //System.out.println("Shuffling Deck:");
        //System.out.println(deck);

    }

    public void drawHand()
    {
        hand = new ArrayList<Card>();

        // Draw 8 cards
        for (int i=0; i<8; i++)
        {
            // Ensure the deck isn't empty
            if (!deck.isEmpty())
            {  
                Card card = deck.remove(0);  // Remove the top card
                hand.add(card);  // Add card to hand
            }
            else
            {
                System.out.println("The deck is empty, cannot draw more cards!");
                break;  // Stop drawing if there are no more cards left in the deck
            }
        }
        
        // Error checking: Print hand
        //System.out.println("Hand:");
        //for (Card card : hand) {System.out.println(card);}

    }

    public void buildGUI()
    {

        gamewindow.setSize(windowWidth, windowHeight);
        gamewindow.setLocationRelativeTo(null);
        gamewindow.setResizable(true);
        gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamewindow.setIconImage(new ImageIcon("icons/windowicon.jpg").getImage());
        
        gamebg.setLayout(new BorderLayout());
        gamebg.setBackground(new Color(53,101,77));
        gamewindow.add(gamebg);
        
        gamewindow.setVisible(true);

    }
}

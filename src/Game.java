import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;

public class Game
{
    int windowWidth = 800;
    int windowHeight = 800;
    int cardwidth = 100;
    int cardheight = 150;
    int playerhandsize = 8;
    int handcram = -5;
    Font m6x11;

    ArrayList<Card> deck;
    ArrayList<Card> hand;

    JFrame gamewindow = new JFrame("Joker Poker");
    
    JPanel gamebg = new JPanel();
    JPanel handPanel = new JPanel();
    JPanel actionpanel = new JPanel();

    JButton discardbtn = new JButton("Discard");
    JButton ranksortbtn = new JButton("Sort (Rank)");
    JButton suitsortbtn = new JButton("Sort (Suit)");
    JButton playbutton = new JButton("Play Hand");
    
    
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
        public String toString()
        { 
            return rank + " of " + suit;
        }

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

        public String getImagePath()
        {
            String path = "./cards/" + rank + "of" + suit + ".png";
            //System.err.println(path);
            if (new File(path).isFile())
            {
                return path;
            } 
            else return "./cards/errcard.png";
        }
    }

    Game()
    {
        startGame(); 
    }

    public void startGame()
    {
        buildFont();
        buildGUI();
        buildDeck();
        shuffleDeck();
        drawHand();

    }

    public void buildDeck()
    {
        deck = new ArrayList<Card>();

        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] ranks = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};

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

    public void buildGUI()
    {

        // Window settings
        gamewindow.setSize(windowWidth, windowHeight);
        gamewindow.setLocationRelativeTo(null);
        gamewindow.setResizable(false);
        gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamewindow.setIconImage(new ImageIcon("icons/windowicon.png").getImage());
        
        // Window background
        gamebg.setLayout(null);
        gamebg.setBackground(new Color(53,101,77));

        // Window Creation
        gamewindow.setContentPane(gamebg);

        buildActionPanel();
        buildHandPanel();

        gamewindow.setVisible(true);

    }

    public void buildActionPanel()
    {
        // Action Panel settings
        actionpanel.setOpaque(false);
        
        // WORK ON TEXT WRAPPING FOR SORT BUTTONS

        // Borders for use in CompoundBorders to center text easier
        Border visibleBorder = BorderFactory.createLineBorder(Color.white, 2);
        Border biginvisibleBorder = BorderFactory.createEmptyBorder(28, 10, 20, 10);  // Top, Left, Bottom, Right
        Border smallinvisibleBorder = BorderFactory.createEmptyBorder(15, 10, 10, 10);
        
        // Button settings
        discardbtn.setFocusable(false);
        discardbtn.setBackground(new Color(254,86,95,255));
        discardbtn.setForeground(new Color(255,255,255,255));
        discardbtn.setPreferredSize(new Dimension(130,63));
        discardbtn.setFont(m6x11.deriveFont(28f));
        discardbtn.setBorder(BorderFactory.createCompoundBorder(visibleBorder, biginvisibleBorder));
        
        suitsortbtn.setFocusable(false);
        suitsortbtn.setBackground(new Color(164,89,166,255));
        suitsortbtn.setForeground(new Color(255,255,255,255));
        suitsortbtn.setFont(m6x11.deriveFont(16f));
        suitsortbtn.setBorder(BorderFactory.createCompoundBorder(visibleBorder, smallinvisibleBorder));
        suitsortbtn.setPreferredSize(new Dimension(80,63));

        ranksortbtn.setFocusable(false);
        ranksortbtn.setBackground(new Color(239,191,47,255));
        ranksortbtn.setForeground(new Color(255,255,255,255));
        ranksortbtn.setFont(m6x11.deriveFont(16f));
        ranksortbtn.setBorder(BorderFactory.createCompoundBorder(visibleBorder, smallinvisibleBorder));
        ranksortbtn.setPreferredSize(new Dimension(80,63));
        
        playbutton.setFocusable(false);
        playbutton.setBackground(new Color(76,194,146,255));
        playbutton.setForeground(new Color(255,255,255,255));
        playbutton.setFont(m6x11.deriveFont(28f));
        playbutton.setBorder(BorderFactory.createCompoundBorder(visibleBorder, biginvisibleBorder));
        playbutton.setPreferredSize(new Dimension(130,63));


        // Adding buttons
        actionpanel.add(discardbtn);
        actionpanel.add(suitsortbtn);
        actionpanel.add(ranksortbtn);
        actionpanel.add(playbutton);
        actionpanel.setBounds(150, 685, 500, 75);

        // Testing code for outline
        //actionpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        gamebg.add(actionpanel);

    }

    public void buildHandPanel()
    {
        // Unnecessary?
        handPanel = new JPanel();
        handPanel.setOpaque(false);
        handPanel.setLayout(new FlowLayout(FlowLayout.CENTER,handcram,0));
        handPanel.setBounds(7, 500, 780, cardheight+20);
        handPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        //Investiage later for making the handpanel look nicer by waking the cards centered vertically
        
        //handPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        gamebg.add(handPanel);
    }

    public void drawHand()
    {
        hand = new ArrayList<Card>();

        // Draw 8 cards
        for (int i=0; i<playerhandsize; i++)
        {
            // Check for empty deck
            if (!deck.isEmpty())
            {  
                Card card = deck.remove(0);  // Remove the top card
                hand.add(card);  // Add card to hand
            }
            else
            {
                System.out.println("The deck is empty");
                break;  // Stop drawing if there are no more cards left in the deck
            }
        }

        // Draw hand
        for (Card card : hand)
        {
            ImageIcon cardImage = new ImageIcon(card.getImagePath());
            JLabel cardLabel = new JLabel(cardImage);
            handPanel.add(cardLabel);
        }
        
        // Error checking: Print hand
        System.out.println("Hand:");
        for (Card card : hand) {System.out.println(card);}

    }

    public void buildFont()
    {
        try
        {
            m6x11 = Font.createFont(Font.TRUETYPE_FONT, new File("./font/m6x11.ttf"));
            //System.out.println("Font found");
        }
        catch( IOException|FontFormatException e)
        {
            System.err.println("Font could not be found");
            e.printStackTrace();
        }
    }
}

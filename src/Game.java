import java.awt.*;
import java.awt.event.*;
import java.io.File;
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

    ArrayList<Card> deck;
    ArrayList<Card> hand;

    JFrame gamewindow = new JFrame("Joker Poker");
    
    JPanel gamebg = new JPanel();
    JPanel handPanel = new JPanel();
    JPanel actionpanel = new JPanel();

    ArrayList<JButton> buttonlist;
    //There's interesting tech here with intializing all these buttons into an arraylist and iterating inside buildActionPanel
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
            return rank + " of " + suit ;
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
            //String imagePath = "./cards/" + toString().replaceAll("\\s", "").toLowerCase() + ".png";
            
            //return "./cards/" + toString().replaceAll("\\s", "").toLowerCase() + ".png";

            //return new File(("./cards/" + toString().replaceAll("\\s", "").toLowerCase() + ".png").isFile())
            //? "./cards/" + toString().replaceAll("\\s", "").toLowerCase() + ".png"
            //: "./cards/errcard.PNG";

            String path = "./cards/" + toString().replaceAll("\\s", "").toLowerCase() + ".png";
            if (new File(path).isFile())
            {
                return path;
            } 
            else
            {
                return "./cards/errcard.PNG";
            }
        }
    }

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
        
        // Button settings
        //Figure out pixelated sans serif fonts later
        discardbtn.setFocusable(false);
        discardbtn.setBackground(new Color(254,86,95,255));
        discardbtn.setForeground(new Color(255,255,255,255));
        discardbtn.setFont(new Font("sans serif",Font.PLAIN,14));
        discardbtn.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        discardbtn.setPreferredSize(new Dimension(120,63));
        
        suitsortbtn.setFocusable(false);
        suitsortbtn.setBackground(new Color(164,89,166,255));
        suitsortbtn.setForeground(new Color(255,255,255,255));
        suitsortbtn.setFont(new Font("sans serif",Font.PLAIN,14));
        suitsortbtn.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        suitsortbtn.setPreferredSize(new Dimension(80,63));

        ranksortbtn.setFocusable(false);
        ranksortbtn.setBackground(new Color(239,191,47,255));
        ranksortbtn.setForeground(new Color(255,255,255,255));
        ranksortbtn.setFont(new Font("sans serif",Font.PLAIN,14));
        ranksortbtn.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        ranksortbtn.setPreferredSize(new Dimension(80,63));
        
        playbutton.setFocusable(false);
        playbutton.setBackground(new Color(76,194,146,255));
        playbutton.setForeground(new Color(255,255,255,255));
        playbutton.setFont(new Font("sans serif",Font.PLAIN,14));
        playbutton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        playbutton.setPreferredSize(new Dimension(120,63));



        actionpanel.add(discardbtn);
        actionpanel.add(suitsortbtn);
        actionpanel.add(ranksortbtn);
        actionpanel.add(playbutton);
        actionpanel.setBounds(150, 685, 500, 75);

        actionpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        gamebg.add(actionpanel);

    }

    public void buildHandPanel()
    {
        handPanel = new JPanel();
        handPanel.setOpaque(false);
        handPanel.setLayout(new FlowLayout(FlowLayout.CENTER,handcram,0));
        handPanel.setBounds(7, 500, 780, cardheight+20);
        handPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        //Investiage later for making the handpanel look nicer
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

}

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
    int handcram = -5; // Negative values for overlap
    Font m6x11;

    ArrayList<Card> deck;
    ArrayList<Card> hand;
    ArrayList<Card> discardpile;
    ArrayList<Card> drawpile;

    JFrame gamewindow = new JFrame("Joker Poker");
    
    JPanel gamebg = new JPanel();
    JPanel handPanel = new JPanel();
    JPanel actionPanel = new JPanel();
    JPanel drawpilePanel = new JPanel();
    JPanel discardpilePanel = new JPanel();


    JButton discardbtn = new JButton("Discard");
    JButton ranksortbtn = new JButton("Sort (Rank)");
    JButton suitsortbtn = new JButton("Sort (Suit)");
    JButton playbutton = new JButton("Play Hand");

    Game()
    {
        startGame(); 
    }

    public void startGame()
    {
        buildGUI();
        buildDeck();
        shuffleDeck();
        drawHand(playerhandsize);
    }

    public void buildGUI()
    {

        // Window settings
        gamewindow.setSize(windowWidth, windowHeight);
        gamewindow.setLocationRelativeTo(null);
        gamewindow.setResizable(false);
        gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamewindow.setIconImage(new ImageIcon("src/icons/windowicon.png").getImage());
        
        // Window background
        gamebg.setLayout(null);
        gamebg.setBackground(new Color(53,101,77));

        // Window Creation
        gamewindow.setContentPane(gamebg);

        buildFont();
        buildActionPanel();

        gamewindow.setVisible(true);

    }

    public void buildFont()
    {
        try
        {
            m6x11 = Font.createFont(Font.TRUETYPE_FONT, new File("src/font/m6x11.ttf"));
        }
        catch( IOException|FontFormatException e)
        {
            System.err.println("Font could not be found");
            e.printStackTrace();
        }
    }

    public void buildActionPanel()
    {
        // Action Panel settings
        actionPanel.setOpaque(false);
        
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
        discardbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                discardcards();
            }
        });
        
        suitsortbtn.setFocusable(false);
        suitsortbtn.setBackground(new Color(164,89,166,255));
        suitsortbtn.setForeground(new Color(255,255,255,255));
        suitsortbtn.setFont(m6x11.deriveFont(16f));
        suitsortbtn.setBorder(BorderFactory.createCompoundBorder(visibleBorder, smallinvisibleBorder));
        suitsortbtn.setPreferredSize(new Dimension(80,63));
        suitsortbtn.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                sortHand(false);
                //System.out.println("Sort By Suit Button pressed");
            }
        });

        ranksortbtn.setFocusable(false);
        ranksortbtn.setBackground(new Color(239,191,47,255));
        ranksortbtn.setForeground(new Color(255,255,255,255));
        ranksortbtn.setFont(m6x11.deriveFont(16f));
        ranksortbtn.setBorder(BorderFactory.createCompoundBorder(visibleBorder, smallinvisibleBorder));
        ranksortbtn.setPreferredSize(new Dimension(80,63));
        ranksortbtn.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                sortHand(true);
                //System.out.println("Sort By Rank Button pressed");
            }
        });
        
        playbutton.setFocusable(false);
        playbutton.setBackground(new Color(76,194,146,255));
        playbutton.setForeground(new Color(255,255,255,255));
        playbutton.setFont(m6x11.deriveFont(28f));
        playbutton.setBorder(BorderFactory.createCompoundBorder(visibleBorder, biginvisibleBorder));
        playbutton.setPreferredSize(new Dimension(130,63));
        playbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // Code to handle the click event
                System.out.println("Play Hand Button pressed");
            }
        });


        // Adding buttons
        actionPanel.add(discardbtn);
        actionPanel.add(suitsortbtn);
        actionPanel.add(ranksortbtn);
        actionPanel.add(playbutton);
        actionPanel.setBounds(150, 685, 500, 75);

        // Testing code for outline
        //actionpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        gamebg.add(actionPanel);

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
                Card card = new Card(suits[i],ranks[j],false);
                deck.add(card);
            }
        }

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

    public void drawHand(int numcards)
    {
        if (hand==null)
        {
            hand = new ArrayList<Card>();
        }

        // Draw hand
        for (int i=0; i<numcards; i++)
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

        renderCardIcons();

        // Error checking: Print hand
        //System.out.println("Hand:");
        //for (Card card : hand) {System.out.println(card);}

    }

    public void sortHand(boolean sorttype)
    {
        // Which suits to display first in the hand
        String[] suitOrder = {"Spades", "Hearts", "Clubs", "Diamonds"};

        if (sorttype) 
        {
            // Sort by rank first, then suit
            hand.sort((card1, card2) -> {
            int rank1 = card1.getValue();
            int rank2 = card2.getValue();
            if (rank1 != rank2)
            {
                return Integer.compare(rank2, rank1); // Highest rank first
            }
            
            // If ranks are the same, sort by suit (Spades > Hearts > Clubs > Diamonds)
            int suitIndex1 = java.util.Arrays.asList(suitOrder).indexOf(card1.suit);
            int suitIndex2 = java.util.Arrays.asList(suitOrder).indexOf(card2.suit);
            return Integer.compare(suitIndex1, suitIndex2);
            });
        }
        else
        {
            // Sort by suit first, then rank
            hand.sort((card1, card2) ->{
            // Suit sorting: Spades > Hearts > Clubs > Diamonds
            int suitIndex1 = java.util.Arrays.asList(suitOrder).indexOf(card1.suit);
            int suitIndex2 = java.util.Arrays.asList(suitOrder).indexOf(card2.suit);
            if (suitIndex1 != suitIndex2)
            {
                return Integer.compare(suitIndex1, suitIndex2);
            }
            // If suits are equal sort by rank (highest leftmost)
            return Integer.compare(card2.getValue(), card1.getValue()); // Highest rank first within suit
            });
        }

        renderCardIcons();

        // Print sorted hand
        // System.out.println("\nNew order of cards in the hand:");
        // for (Card card : hand)
        // {
        //     System.out.println(card);
        // }
    

    }

    public void discardcards()
    {
        System.out.println("Code that discards the hand here");
    }

    public void renderCardIcons()
    {
        // Remove the handPanel if it already exists
        // Not doing this every time the cards gets rerendered gives weird behavior (like the sort buttons being put in the handPanel for some reason ???)
        if  (handPanel!= null||java.util.Arrays.asList(gamebg.getComponents()).contains(handPanel))
        {
            handPanel.removeAll();
            gamebg.remove(handPanel);
        }
        
        handPanel.setLayout(new GridBagLayout());
        handPanel.setBounds(7, 500, 780, cardheight+20);
        handPanel.setBackground(new Color(255,255,255,40));
        handPanel.setOpaque(true);
        gamebg.add(handPanel);

        // Row of cards using FlowLayout for horizontal alignment
        JPanel cardRow = new JPanel(new FlowLayout(FlowLayout.CENTER, handcram, 0)) {{ setOpaque(false); }};
        // cardRow Contraints
        GridBagConstraints gbc = new GridBagConstraints()
        {{
            anchor = GridBagConstraints.CENTER;
            fill = GridBagConstraints.HORIZONTAL; // Allow horizontal flow
            weightx = 1.0; // Allow expanding horizontally
        }};

        // Add the cardIcons to cardRow for nicer UI behavior
        for (Card card : hand)
        {
            ImageIcon cardImage = new ImageIcon(card.getImagePath());
            JLabel cardLabel = new JLabel(cardImage);
            cardLabel.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    // Code to handle the click event
                    card.is_selected = !card.is_selected;
                    System.out.println(card + (card.is_selected ? " is selected" : " is not selected"));
                }

                @Override
                public void mouseEntered(MouseEvent e)
                {
                    // Scale the card by a factor of 1.03
                    cardLabel.setIcon(new ImageIcon(cardImage.getImage().getScaledInstance((int) (cardwidth * 1.03), (int) (cardheight * 1.03), Image.SCALE_REPLICATE)));
                    gamebg.revalidate();
                    gamebg.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    // Remove Scaling
                    cardLabel.setIcon(cardImage);
                    gamebg.revalidate();
                    gamebg.repaint();
                }

            });
            cardRow.add(cardLabel);
        }
        
        handPanel.add(cardRow, gbc);
        gamebg.add(handPanel);
        gamebg.revalidate();
        gamebg.repaint();
        
    }

}

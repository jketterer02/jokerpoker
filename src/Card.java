import java.io.File;

public class Card
    {
        String suit;
        String rank;
        boolean is_selected;

        Card(String suit, String rank, boolean is_selected)
        {
            this.suit = suit;
            this.rank = rank;
            this.is_selected = is_selected;
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
                case "Ace": return 14;
                case "King": return 13;
                case "Queen": return 12;
                case "Jack": return 11;

                default: return Integer.parseInt(rank);
            }
        }

        public String getImagePath()
        {
            String path = "src/cards/" + rank + "of" + suit + ".png";
            //System.err.println(path);
            if (new File(path).isFile())
            {
                return path;
            } 
            else return "src/cards/errcard.png";
        }
    }

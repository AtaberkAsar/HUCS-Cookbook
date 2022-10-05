public class Card{
    private final String suit;
    private final int value;
    private static final String[] SUITS = {"spades", "hearts", "diamonds", "clubs"};

    /**
     *
     * @param suit final suit of card
     * @param value final value of card
     */
    public Card(String suit, int value){
        suit = suit.toLowerCase();
        boolean flag = false;
        for(String suitI : SUITS){
            if(suit.equals(suitI)){
                flag = true;
                break;
            }
        }
        if(!flag){
            System.out.println("Unknown suit, default suit (spades) will be assigned to the card...");
            suit = "spades";
        }
        this.suit = suit;
        if(value < 1 || value > 13){
            System.out.println(String.format("Value of a card must be between 1 and 13, (%d - 1)%13 + 1 = %d will be assigned to the card...",
                    value, (value - 1) % 13 + 1));
        }
        this.value = (value - 1) % 13 + 1;
    }

    public String getSuit(){
        return suit;
    }
    public int getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Card){
            return suit.equals(((Card) o).getSuit()) && (value == ((Card) o).getValue());
        }
        return false;
    }
}


package misc.poker;

public class Card implements Comparable<Card> {
    
    private int rank;
    private char suit;

    public Card(String c) {
	rank = getIntegerRank(c.toUpperCase().charAt(0));
	suit = c.charAt(1);	
    }
    
    private int getIntegerRank(char r)
    {
	return "--23456789TJQKA".indexOf(r);
    }

    public int compareTo(Card other) {
	if (rank == other.rank)
	{
	    return 0;
	}
	else if (rank > other.rank)
	{
	    return 1;
	}
	else
	{
	    return -1;
	}
    }

    public int getRank() {
	return rank;
    }

    public char getSuit() {
	return suit;
    }
    
    public String toString() {
	return (Integer.toString(rank) + suit);
    }

    public boolean isPairWith(Card otherCard) {
	return getRank() == otherCard.getRank();
    }
    

}

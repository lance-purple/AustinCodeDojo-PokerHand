package misc.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    private static final int NUM_RANKS = 13;
    private List<Card> cards;

    public Hand(String handSpecification) {
	cards = new ArrayList<Card>();
	for (String c : handSpecification.split(","))
	{
	    cards.add(new Card(c));
	}
    }

    public int getPairRank()
    {
	int[] pairRank = new int[2];
	int pairIndex = 0;
	
	for (int i = 0; i < cards.size(); i++)
	{
	    for (int j = i+1; j < cards.size(); j++)
	    {
		int rankI = cards.get(i).getRank();
		int rankJ = cards.get(j).getRank();
		
		if (rankI == rankJ)
		{
		    pairRank[pairIndex++] = cards.get(i).getRank();
		}
	    }
	}
	
	if (pairIndex == 0) {
	    return 0;
	}
	else if (pairIndex == 1)
	    return pairRank[0];
	
	return pairRank[0] * NUM_RANKS + pairRank[1];
    }

    public Outcome beats(Hand otherHand) {
	Outcome result = checkPairs(otherHand);
	if (result != Outcome.draw) return result;
	
	return compareHighCards(otherHand);
    }

    private Outcome checkPairs(Hand otherHand) {
	int thisHandPairRank  = getPairRank();
	int otherHandPairRank = otherHand.getPairRank();
	
	if (thisHandPairRank > otherHandPairRank)
	{
	    return Outcome.win;
	}
	else if (thisHandPairRank < otherHandPairRank)
	{
	    return Outcome.lose;
	}
	else
	{
	    return Outcome.draw;
	}
    }

    private Outcome compareHighCards(Hand otherHand) {
	Outcome result = Outcome.draw;
	while (result == Outcome.draw && cards.size() != 0) {
	    int higherThan = getHighCard().compareTo(otherHand.getHighCard());
	    if(higherThan > 0) {
		result = Outcome.win;
	    }
	    if (higherThan < 0) {
		result = Outcome.lose;
	    }
	    cards.remove(getHighCard());
	    otherHand.cards.remove(otherHand.getHighCard());
	}
	return result;
    }

    private Card getHighCard() {
	return Collections.max(cards);
    }

}

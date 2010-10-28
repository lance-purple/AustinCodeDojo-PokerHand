package misc.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    private static final int NUM_RANKS = 13;
    
    private List<Card> cards;

    public Hand(String handSpecification) {
	cards = new ArrayList<Card>();
	for (String c : handSpecification.split(",")) {
	    cards.add(new Card(c));
	}
    }

    public int getPairRank() {
	int[] pairRank = new int[2];
	int pairIndex = 0;

	boolean thereMightStillBePairs = true;
	for (int i = 0; i < cards.size() && thereMightStillBePairs; i++) {
	    final Card firstCandidateCard = cards.get(i);
	    for (int j = i + 1; j < cards.size() && thereMightStillBePairs; j++) {
		final Card secondCandidateCard = cards.get(j);
		if (firstCandidateCard.isPairWith(secondCandidateCard)) {
		    pairRank[pairIndex++] = firstCandidateCard.getRank();
		    thereMightStillBePairs = pairIndex < 2;
		}
	    }
	}

	if (pairIndex == 0) {
	    return 0;
	} else if (pairIndex == 1)
	    return pairRank[0];

	return pairRank[0] * NUM_RANKS + pairRank[1];
    }

    public Outcome beats(Hand otherHand) {
	if(isThreeOfAKind()) {
	    return Outcome.win;
	}
	Outcome result = checkPairs(otherHand);
	if (result != Outcome.draw)
	    return result;

	return compareHighCards(otherHand);
    }

    private boolean isThreeOfAKind() {
	int rankCount[] = new int[13];
	for(Card card : cards) {
	    rankCount[card.getRank()]++;
	}
	for(int count : rankCount) {
	    if(count == 3) return true;
	}
	return false;
    }

    private Outcome checkPairs(Hand otherHand) {
	int thisHandPairRank = getPairRank();
	int otherHandPairRank = otherHand.getPairRank();

	if (thisHandPairRank > otherHandPairRank) {
	    return Outcome.win;
	} else if (thisHandPairRank < otherHandPairRank) {
	    return Outcome.lose;
	} else {
	    return Outcome.draw;
	}
    }

    private Outcome compareHighCards(Hand otherHand) {
	Outcome result = Outcome.draw;
	while (result == Outcome.draw && cards.size() != 0) {
	    int higherThan = getHighCard().compareTo(otherHand.getHighCard());
	    if (higherThan > 0) {
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

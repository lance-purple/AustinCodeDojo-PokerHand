package misc.poker;

import static org.junit.Assert.*;

import org.junit.Test;

public class HandTest {

    private static final String PAIR_OF_THREES_AND_FOURS = "3h,3s,4d,4h,5s";

    private static final String THREE_DEUCES = "2d,2s,2h,3d,4h";

    private static final String SEVEN_OF_HEARTS_HIGH = "2c,3c,4d,5s,7h";

    private static final String EIGHT_OF_SPADES_HIGH_WITH_FIVE_KICKER = "2d,3d,4c,5h,8s";

    private static final String EIGHT_OF_SPADES_HIGH_WITH_SIX_KICKER = "2h,3h,4d,6s,8h";

    private static final String TEN_OF_SPADES_HIGH = "2d,3d,4c,5h,ts";

    private static final String JACK_OF_SPADES_HIGH = "2d,3d,4c,5h,js";

    private static final String JACK_OF_SPADES_TIE = "2h,3h,4d,5c,jd";

    private static final String PAIR_OF_TWOS = "2h,2h,3d,4c,6d";

    private static final String PAIR_OF_TWOS_WITH_5_KICKER = "2h,2c,3d,4c,5d";

    private static final String PAIR_OF_TWOS_WITH_6_KICKER = "2h,2c,3d,4c,6d";

    private static final String PAIR_OF_THREES = "3h,3c,2d,4d,5h";

    private static final String PAIR_OF_TWOS_AND_THREES = "2h,2c,3h,3c,4d";
    private static final String PAIR_OF_TWOS_AND_THREES_TIE = "2s,2d,3s,3d,4s";

    private static final String PAIR_OF_JACKS = "2s,3s,4s,js,jd";

    private static final String THREE_THREES = "3h,3s,3c,4d,5h";

    private static final String KING_HIGH = "2h,3d,4h,5c,ks";

    @Test
    public void highCardHandShouldBeatLowCardHard() throws Exception {
	Hand lowHand = new Hand(SEVEN_OF_HEARTS_HIGH);
	Hand highHand = new Hand(EIGHT_OF_SPADES_HIGH_WITH_FIVE_KICKER);

	assertEquals("high hand should beat low hand", highHand.beats(lowHand),
		Outcome.win);
    }

    @Test
    public void lowCardShouldNotBeatHighCard() throws Exception {
	Hand lowHand = new Hand(SEVEN_OF_HEARTS_HIGH);
	Hand highHand = new Hand(EIGHT_OF_SPADES_HIGH_WITH_FIVE_KICKER);

	assertEquals("lowHand cannot beat high hand", lowHand.beats(highHand),
		Outcome.lose);
    }

    @Test
    public void handWithTenShouldBeatLowHand() throws Exception {
	Hand lowHand = new Hand(SEVEN_OF_HEARTS_HIGH);
	Hand tenHand = new Hand(TEN_OF_SPADES_HIGH);

	assertEquals("ten should beat low hand", tenHand.beats(lowHand),
		Outcome.win);
    }

    @Test
    public void handWithJackShouldBeatAHandWithATen() throws Exception {
	Hand lowHand = new Hand(TEN_OF_SPADES_HIGH);
	Hand jackHand = new Hand(JACK_OF_SPADES_HIGH);

	assertEquals("jack should beat ten hand", jackHand.beats(lowHand),
		Outcome.win);
    }

    @Test
    public void sameHighCardShouldUseKicker() throws Exception {
	Hand lowHand = new Hand(EIGHT_OF_SPADES_HIGH_WITH_FIVE_KICKER);
	Hand highHand = new Hand(EIGHT_OF_SPADES_HIGH_WITH_SIX_KICKER);

	assertEquals("lowHand cannot beat high hand", highHand.beats(lowHand),
		Outcome.win);
    }

    @Test
    public void tyingHandsShouldDraw() throws Exception {
	Hand firstHand = new Hand(JACK_OF_SPADES_HIGH);
	Hand secondHand = new Hand(JACK_OF_SPADES_TIE);

	assertEquals(Outcome.draw, firstHand.beats(secondHand));
    }

    @Test
    public void pairOfTwosShouldBeatJackHigh() throws Exception {
	Hand firstHand = new Hand(JACK_OF_SPADES_HIGH);
	Hand secondHand = new Hand(PAIR_OF_TWOS);

	assertEquals(Outcome.lose, firstHand.beats(secondHand));
    }

    @Test
    public void jackHighShouldNotBeatPairOfTwos() throws Exception {
	Hand firstHand = new Hand(JACK_OF_SPADES_HIGH);
	Hand secondHand = new Hand(PAIR_OF_TWOS);

	assertEquals(Outcome.win, secondHand.beats(firstHand));
    }

    @Test
    public void pairThatDiffersByKickerShouldWin() throws Exception {
	Hand firstHand = new Hand(PAIR_OF_TWOS_WITH_5_KICKER);
	Hand secondHand = new Hand(PAIR_OF_TWOS_WITH_6_KICKER);
	assertEquals(Outcome.win, secondHand.beats(firstHand));
    }

    @Test
    public void highPairWins() throws Exception {
	Hand firstHand = new Hand(PAIR_OF_THREES);
	Hand secondHand = new Hand(PAIR_OF_TWOS);

	assertEquals(Outcome.win, firstHand.beats(secondHand));
    }

    @Test
    public void twoPairBeatsOneHigherPair() throws Exception {
	Hand twoPair = new Hand(PAIR_OF_TWOS_AND_THREES);
	Hand higherSinglePair = new Hand(PAIR_OF_JACKS);

	assertEquals(Outcome.win, twoPair.beats(higherSinglePair));
    }
    
    @Test
    public void twoEqualPairsTie() throws Exception {
	Hand twoPair = new Hand(PAIR_OF_TWOS_AND_THREES);
	Hand twoPairTie = new Hand(PAIR_OF_TWOS_AND_THREES_TIE);

	assertEquals(Outcome.draw, twoPair.beats(twoPairTie));	
    }
    
    @Test
    public void threeOfAKindBeatsLowCard() throws Exception {
	Hand threeThrees = new Hand(THREE_THREES);
	Hand kingHigh = new Hand(KING_HIGH);

	assertEquals(Outcome.win, threeThrees.beats(kingHigh));	
    }
    
    @Test
    public void threeOfAKindBeatsAHigherRankedTwoPair() throws Exception {
	Hand threeDeuces = new Hand(THREE_DEUCES);
	Hand pairOfThreesAndFours = new Hand(PAIR_OF_THREES_AND_FOURS);
	
	assertEquals(Outcome.win, threeDeuces.beats(pairOfThreesAndFours));
    }
    
    @Test
    public void twoOfAKindIsBeatenByAThreeOfAKind() throws Exception {
	Hand threeDeuces = new Hand(THREE_DEUCES);
	Hand pairOfThreesAndFours = new Hand(PAIR_OF_THREES_AND_FOURS);
	
	assertEquals(Outcome.lose, pairOfThreesAndFours.beats(threeDeuces));
    }
}

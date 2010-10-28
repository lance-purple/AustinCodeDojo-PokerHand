package misc.poker;

enum HandClassification {
    HIGH_CARD {
	@Override
	boolean isKind(Hand hand) {
	    return false;
	}
    },
    PAIR_HAND {
	@Override
	boolean isKind(Hand hand) {
	    return false;
	}
    },
    TRIP_HAND {
	@Override
	boolean isKind(Hand hand) {
	    return false;
	}
    };
    abstract boolean isKind(Hand hand);
}
package Modules;

public enum PokerHands {

	ROYAL_FLUSH(1),
	STRAIGHT_FLUSH(2),
	FOUR_KIND(3),
	FULL_HOUSE(4),
	FLUSH(5),
	STRAIGHT(6),
	THREE_KIND(7),
	TWO_PAIR(8),
	ONE_PAIR(9),
	BASE(10);
	
	private final int handRank;
	
	PokerHands (int hRank){
		handRank = hRank;
	}
	
	@Override
	public String toString(){
		return String.valueOf(handRank);
	}
	
}

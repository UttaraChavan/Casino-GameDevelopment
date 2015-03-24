package Game;

public class Card {
	private int rank, suit;
	private static String[] suits = { "hearts", "spades", "diamonds", "clubs" };
	private static String[] ranks  = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
	
	/*public static String RankAsString( int r ) {
		return ranks[r];
	}*/

	Card(int suit, int rank){
		this.rank=rank;
		this.suit=suit;
	}

	@Override 
	public String toString(){
		return ranks[rank] + " of " + suits[suit];
	}

	public String getRank() {
		return ranks[rank];
	}

	public String getSuit() {
		return suits[suit];
	}

}

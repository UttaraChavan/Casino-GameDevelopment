package Modules;

public class Card {
	private int rank, suit;
	private static String[] suits = { "hearts", "spades", "diamonds", "clubs" };
	private static String[] ranks  = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
	
	Card(int suit, int rank){
		this.rank=rank;
		this.suit=suit;
	}

	public String getRank() {
		return ranks[rank];
	}
	public int getRankPos(){
		return rank;
	}
	

	public String getSuit() {
		return suits[suit];
	}
	public int getSuitPos(){
		return suit;
	}
	

	@Override 
	public String toString(){
		return ranks[rank] + " of " + suits[suit];
	}

}

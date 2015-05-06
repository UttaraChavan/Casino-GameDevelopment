package Modules;

import java.util.ArrayList;

public class PokerHandHelper {

	private int[] handRanks = new int[13];
	private int[] handSuits = new int[4];

	ArrayList<Card> hand;

	public PokerHandHelper(ArrayList<Card> h){
		hand = h;
		populateArrays();
	}

	private void populateArrays() {
		for (Card c : hand){
			handRanks[c.getRankPos()]++;
			handSuits[c.getSuitPos()]++;
		}

		for (int i=0; i<4; i++){
			System.out.print(handSuits[i]+" ");
		}
		System.out.println();
		for (int i=0; i<13; i++){
			System.out.print(handRanks[i]+" ");
		}
	}

	public PokerHands getHandRank(){

		boolean royalFlag = false;
		int[] max = new int[2];
		max = getMax();

		if (handSuits[2] == 5){
			int i;
			for (i=0; i<5; i++){
				if (handRanks[1] == 0)
					break;
				if (i == 5)
					royalFlag = true;
			}
		} 

		if (royalFlag){
			return PokerHands.ROYAL_FLUSH;
		} else {
			if (isSuit() && isSeq()){
				return PokerHands.STRAIGHT_FLUSH;
			} else if (isSuit()){
				return PokerHands.FLUSH;
			} else if (isSeq()){
				return PokerHands.STRAIGHT;
			} else if (max[0] == 4){
				return PokerHands.FOUR_KIND;
			} else if (max[0] == 3 && max[1] == 2){
				return PokerHands.FULL_HOUSE;
			} else if (max[0] == 3 && max[1] == 1){
				return PokerHands.THREE_KIND;
			} else if (max[0] == 2 && max[1] == 2){
				return PokerHands.TWO_PAIR;
			} else if (max[0] == 2){
				return PokerHands.ONE_PAIR;
			} else {
				return PokerHands.BASE;
			}
		}

	}

	private int[] getMax() {
		int[] m = new int[2];
		m[0] = -1; m[1] = -1;
		for (int i=0; i<handRanks.length; i++){
			if (handRanks[i] > m[0]){
				m[1] = m[0];
				m[0] = handRanks[i];
			}
		}
		return m;
	}

	private boolean isSuit() {
		for (int i=0; i<handSuits.length; i++){
			if (handSuits[i] == 5)
				return true;
		}
		return false;
	}

	private boolean isSeq() {
		for (int i=0; i<handRanks.length-5; ){
			int j, in_limit = i+5;
			
			for (j=i; j<in_limit; j++){
				if (handRanks[j] == 0){
					i=j+1;
					break;
				}					
			}
			if (j == in_limit)
				return true;
		}
		return false;
	}

	public int getMaxRank(){
		if (handRanks[0] == 1)
			return 0;

		for (int i=12; i>0; i--){
			if (handRanks[i] == 1)
				return i;
		}

		return 13;
	}

	public int getCountPos(int count, int startIndex){
		if (startIndex == 0){
			if (handRanks[0] == count){
				return 0;
			}
			for (int i=12; i>1; i--){
				if (handRanks[i] == count)
					return i;
			}
		} else {
			for (int i=startIndex; i>0; i--){
				if (handRanks[i] == count)
					return i;
			}
		}

		return 13;
	}
}

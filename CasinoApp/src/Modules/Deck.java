package Modules;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	private ArrayList<Card> cards;
	
	Deck() {
		cards = new ArrayList<Card>();
		
		//Add 52 cards to the deck
		for (int i=0; i<4; i++){
			for (int j=0; j<13; j++) {
				cards.add( new Card(i,j) );
			}
		}

		Shuffle();	
	}
	
	//Shuffle the deck
	public void Shuffle() {
		int index1, index2;
		Random generator = new Random();
		Card temp;
		
		for (int i=0; i<100; i++) {
			index1 = generator.nextInt( cards.size() - 1 );
			index2 = generator.nextInt( cards.size() - 1 );
			temp = cards.get( index2 );
			cards.set( index2 , cards.get( index1 ) );
			cards.set( index1, temp );
		}
	}

	public Card drawFromDeck() {     
		return cards.remove(0);
	}

	public int getTotalCards() {
		return cards.size();   
	}

}

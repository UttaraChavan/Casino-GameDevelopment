package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import Client.Player;

//public class BlackJack implements Game {
public class BlackJack extends GameAbstract {

	Player player, dealer;
	Deck deck;
	int plySum, dealSum, BET;
	boolean split=false, doubleTheBet=false, insurance=false;
	public static final int BUST = 21;
	HashMap<String, Boolean> HMap;
	HashMap<String, Integer> HMapCard;
	ArrayList<Card> plyCard, dealCard;

	public BlackJack(Player p){
		this.player = p;
		this.deck = GetDeck();

		HMapCard = new HashMap<String, Integer>();
		HMapCard.put("A", new Integer(11));
		for (int i=2; i<=10; i++){
			HMapCard.put(String.valueOf(i), new Integer(i));
		}
		HMapCard.put("J", new Integer(10));
		HMapCard.put("Q", new Integer(10));
		HMapCard.put("K", new Integer(10));

		/*Iterator<Entry<String, Integer>> itr = HMapCard.entrySet().iterator();

		System.out.println("Enter option number: ");
		while (itr.hasNext()){

			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)itr.next();

				System.out.println(pair.getKey()+ ": "+ pair.getValue());

		}*/
		System.out.println("------------------------- BlackJack Game Started -------------------------------");
		play();
	}

	public Deck GetDeck() {
		// TODO Auto-generated method stub
		return new Deck();
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		System.out.println(player);
		StrartRound();
		while (player.getBal()+BET>0){			
			int opt;
			if (doubleTheBet && plyCard.size() >= 3)
				opt = 3;
			else opt = DisplayOptions();

			switch (opt) {
			case 1:
				System.out.println("Deal");

				//ArrayList<Card> plyCard = new ArrayList<Card>();
				Card c1 = deck.drawFromDeck();
				Card c2 = deck.drawFromDeck();
				/*Card c1 = new Card(0, 0);
				Card c2 = new Card(1, 0);*/
				/*Card c1 = new Card(0, 3);
				Card c2 = new Card(1, 4);*/

				plyCard.add(c1);
				plyCard.add(c2);

				plySum = AddCardVal(plySum, c1);
				System.out.println("first "+plySum);
				plySum = AddCardVal(plySum, c2);
				System.out.println("second "+plySum);

				//ArrayList<Card> dealCard = new ArrayList<Card>();
				Card dealC1 = deck.drawFromDeck();
				//Card dealC1 = new Card(2, 0);
				dealCard.add(dealC1);
				dealSum = HMapCard.get(dealC1.getRank());

				System.out.println("Dealer's card: "+ dealC1 +" Dealer's Total: "+ dealSum);
				System.out.println("Your cards: "+ c1 +", "+ c2 + " Total: "+ plySum);

				if (plySum == BUST){
					Win(player, BET);
					System.out.println("BlackJack!!! You won...\t Money: "+player.getBal());
					StrartRound();
				} else {					
					HMap.put("1. Deal", false);
					HMap.put("2. NewCard", true);
					HMap.put("3. Stand", true);
					if (c1.getRank() == c2.getRank()){
						HMap.put("4. Split", true);
					}
					if (plySum == 9 || plySum == 10 || plySum ==11){
						HMap.put("5. Double", true);
					}
					if (dealC1.getRank().equals("A")){
						HMap.put("6. Insurance", true);
					}

				}			
				break;

			case 2:
				System.out.println("NewCard");
				Card cNew = deck.drawFromDeck();
				System.out.println("New Card: "+cNew);
				plySum = AddCardVal(plySum, cNew);
				System.out.println("Your points after adding card: "+plySum);

				if (plySum == BUST && insurance){
					Card cStand = deck.drawFromDeck();
					dealCard.add(cStand);
					dealSum = AddCardVal(dealSum, cStand);
					if (dealSum == BUST){
						Win(player, BET/2);
						System.out.println("It's a Stannd-Off.. Dealer's Total: "+ dealSum);
						System.out.println("Youhave: "+player.getBal());
					} else {
						Lost(player, BET/2);
						System.out.println("Dealer didn't get BlackJack. Dealer's Total: "+ dealSum);
						System.out.println("But you reached 21 and lose your insurance amount..Youhave: "+player.getBal());
					}
					StrartRound();
				} else if (plySum == BUST){
					Win(player, BET);
					System.out.println("Dealer's Total: "+ dealSum);
					System.out.println("You won!!!\t Money: "+player.getBal());

					StrartRound();
				} else if (plySum > BUST){
					Lost(player, BET);
					System.out.println("Busted...\t Your balance: "+player.getBal());
					StrartRound();
				} else {
					HMap.put("1. Deal", false);
					if (HMap.get("4. Split")) HMap.put("4. Split", false);
					if (HMap.get("5. Double")) HMap.put("5. Double", false);
					if (HMap.get("6. Insurance")) HMap.put("6. Insurance", false);
					//if (doubleTheBet) HMap.put("2. NewCard", false);
				}

				break;

			case 3:
				System.out.println("stand");
				while (dealSum < 17){
					Card cStand = deck.drawFromDeck();
					dealCard.add(cStand);
					dealSum = AddCardVal(dealSum, cStand);
				}
				System.out.println("Dealer's Total: "+ dealSum);
				System.out.println("Your Total: "+ plySum);

				if (dealSum == plySum){					
					System.out.println("This is Draw =(\t Your balance: "+player.getBal());
					StrartRound();
				} else if (dealSum <= BUST && dealSum > plySum){
					if (dealSum == BUST && dealCard.size() == 2 && insurance){
						System.out.println("Dealer got BlackJack.. But you are Insured!!!! You current balance: "+player.getBal());
					} else if (insurance){
						Lost(player, BET/2);
						System.out.println("Dealer didn't get BlackJack, You lose your insurance money...\t Your balance: "+player.getBal());
					} else{
						Lost(player, BET);					
						System.out.println("You Lost...\t Your balance: "+player.getBal());
					}
					StrartRound();
				} else {
					Win(player, BET);
					System.out.println("You won!!!\t Money: "+player.getBal());
					StrartRound();
				}
				break;
			case 4:
				split=true;
				System.out.println("Split");
				break;
			case 5:
				doubleTheBet=true;
				BET = BET*2;
				System.out.println("Double");
				break;
			case 6:
				insurance=true;
				if (HMap.get("4. Split")) HMap.put("4. Split", false);
				if (HMap.get("5. Double")) HMap.put("5. Double", false);
				if (HMap.get("6. Insurance")) HMap.put("6. Insurance", false);
				System.out.println("Insurance");
				break;
			}
		}
	}

	private int AddCardVal(int plySum, Card c) {
		// TODO Auto-generated method stub
		if (c.getRank().equals("A")){
			if (plySum + HMapCard.get(c.getRank()) > BUST)
				plySum = plySum + 1;	
			else plySum = plySum + HMapCard.get(c.getRank());	
		} else plySum = plySum + HMapCard.get(c.getRank());	
		return plySum;
	}

	private void StrartRound() {
		// TODO Auto-generated method stub
		HMap = new HashMap<String, Boolean>();
		HMap.put("1. Deal", true);
		HMap.put("2. NewCard", false);
		HMap.put("3. Stand", false);
		HMap.put("4. Split", false);
		HMap.put("5. Double", false);
		HMap.put("6. Insurance", false);
		dealSum = 0; plySum = 0; BET=10;
		plyCard = new ArrayList<Card>();
		dealCard = new ArrayList<Card>(); 
		split=false; doubleTheBet=false; insurance=false;
	}

	private int DisplayOptions() {
		// TODO Auto-generated method stub
		Iterator<Entry<String, Boolean>> itr = HMap.entrySet().iterator();

		System.out.println("Enter option number: ");
		while (itr.hasNext()){

			Map.Entry<String, Boolean> pair = (Map.Entry<String, Boolean>)itr.next();

			if (pair.getValue()){
				System.out.println(pair.getKey());
			}
		}

		Scanner kbd = new Scanner(System.in);
		return kbd.nextInt();
	}

}

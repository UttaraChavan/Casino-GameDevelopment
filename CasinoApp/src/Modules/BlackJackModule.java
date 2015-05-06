package Modules;

import java.util.ArrayList;
import java.util.HashMap;

public class BlackJackModule extends GameAbstract {

	Player player, dealer;
	Deck deck;
	int dealSum, handIndex;

	public int opt, BET;
	public String Result = "";

	int[] plySum = new int[2];
	public boolean doubleTheBet=false, startNextRound=false;
	boolean splitFirst=false, insurance=false, split=false;
	public static final int BUST = 21;
	public HashMap<String, Boolean> HMapDisplayChoices;
	HashMap<String, Integer> HMapCard;
	public ArrayList<Card> dealCard;
	public ArrayList<ArrayList<Card>> plyCard;

	public BlackJackModule(Player p){
		this.player = p;

		HMapCard = new HashMap<String, Integer>();
		HMapCard.put("A", new Integer(11));
		for (int i=2; i<=10; i++){
			HMapCard.put(String.valueOf(i), new Integer(i));
		}
		HMapCard.put("J", new Integer(10));
		HMapCard.put("Q", new Integer(10));
		HMapCard.put("K", new Integer(10));

		System.out.println("------------------------- BlackJack Game Started -------------------------------");
	}

	public Deck GetDeck() {
		return new Deck();
	}

	public String StartPlay(int option){
		Result = "";
		opt = option;
		play();
		return Result;
	}

	@Override
	public void play() {
		System.out.println(player);
	
		switch (opt) {
		case 1:
			System.out.println("Deal");

			this.deck = GetDeck();
			
			Card c1 = deck.drawFromDeck();
			Card c2 = deck.drawFromDeck();
			
			plyCard.get(0).add(c1);
			plyCard.get(0).add(c2);

			plySum[0] = AddCardVal(plySum[0], c1);
			System.out.println("first "+plySum[0]);
			plySum[0] = AddCardVal(plySum[0], c2);
			System.out.println("second "+plySum[0]);

			Card dealC1 = deck.drawFromDeck();
		
			dealCard.add(dealC1);
			dealSum = HMapCard.get(dealC1.getRank());

			System.out.println("Dealer's card: "+ dealC1 +" Dealer's Total: "+ dealSum);
			System.out.println("Your cards: "+ c1 +", "+ c2 + " Total: "+ plySum[0]);

			if (plySum[0] == BUST){
				Win(player, BET);
				System.out.println("BlackJack!!! You won...\t Money: "+player.getBal());
				if (!split){
					startNextRound=true;
					Result = "BlackJack!!! You won...\t Money: "+ player.getBal();
				}

				
			} else {					
				HMapDisplayChoices.put("1. Deal", false);
				HMapDisplayChoices.put("2. NewCard", true);
				HMapDisplayChoices.put("3. Stand", true);
				if (c1.getRank() == c2.getRank()){
					HMapDisplayChoices.put("4. Split", true);
				}
				if (plySum[0] == 9 || plySum[0] == 10 || plySum[0] == 11){
					HMapDisplayChoices.put("5. Double", true);
				}
				if (dealC1.getRank().equals("A")){
					HMapDisplayChoices.put("6. Insurance", true);
				}
			}			
			break;

		case 2:
			System.out.println("NewCard");

			System.out.println("PlayCard arrayList has: "+ plyCard.size() + " elements");

			Card cNew = deck.drawFromDeck();
			System.out.println("New Card: "+cNew);
			plyCard.get(handIndex).add(cNew);
			plySum[handIndex] = AddCardVal(plySum[handIndex], cNew);
			System.out.println("Your points after adding card: "+plySum[handIndex]);

			if (plySum[handIndex] == BUST && insurance){
				Card cStand = deck.drawFromDeck();
				dealCard.add(cStand);
				dealSum = AddCardVal(dealSum, cStand);
				if (dealSum == BUST){
					Win(player, BET/2);
					System.out.println("It's a Stannd-Off.. Dealer's Total: "+ dealSum);
					if (!split){
						startNextRound=true;
						Result = "It's a Stannd-Off.. Dealer's Total: "+ dealSum;
					}

					System.out.println("Youhave: "+player.getBal());
				} else {
					Lost(player, BET/2);
					System.out.println("Dealer didn't get BlackJack. Dealer's Total: "+ dealSum);
					System.out.println("But you reached 21 and lose your insurance amount..Youhave: "+player.getBal());

					if (!split){
						startNextRound=true;
						Result = "Dealer didn't get BlackJack. But you reached 21 and lose your insurance amount..";
					}

				}
				if (handIndex == plyCard.size()-1){
					StrartRound();
				} else {
					splitFirst=true;
					handIndex++; 
					System.out.println("Now you are playing for second hand..");
					Result = "Now you are playing for second hand..";
				}
			} else if (plySum[handIndex] == BUST){
				Win(player, BET);
				System.out.println("Dealer's Total: "+ dealSum);
				System.out.println("You won!!!\t Money: "+player.getBal());
				if (!split || split && splitFirst){
					startNextRound=true;
					Result = "You won!!!";
				}


				if (handIndex == plyCard.size()-1){ 
					if (plyCard.size() == 2 && !splitFirst){
						getDealerSum();
						if (dealSum > BUST){
							Win(player, BET);
							System.out.println("You won for first hand!!!\t Money: "+player.getBal());
							
						} else if (dealSum == BUST || dealSum >= plySum[0]){
							Lost(player, BET);
							System.out.println("You lost for first hand!!!\t Money: "+player.getBal());
							
						} else if (dealSum  < plySum[0]){
							Win(player, BET);
							System.out.println("You won for first hand!!!\t Money: "+player.getBal());
							
						}
					}
					
				} else {
					splitFirst=true;
					handIndex++;
					System.out.println("Now you are playing for second hand..");
					Result = "Now you are playing for second hand..";
				}
			} else if (plySum[handIndex] > BUST){
				Lost(player, BET);
				System.out.println("Busted...\t Your balance: "+player.getBal());
				if (!split || split && splitFirst){
					startNextRound=true;
					Result = "Busted...";
				}

				if (handIndex == plyCard.size()-1){
					if (plyCard.size() == 2 && !splitFirst){
						getDealerSum();
						if (dealSum > BUST){
							Win(player, BET);
							System.out.println("You won for first hand!!!\t Money: "+player.getBal());
						
						} else if (dealSum == BUST || dealSum >= plySum[0]){
							Lost(player, BET);
							System.out.println("You lost for first hand!!!\t Money: "+player.getBal());
							
						} else if (dealSum  < plySum[0]){
							Win(player, BET);
							System.out.println("You won for first hand!!!\t Money: "+player.getBal());
							
						}
					}
					
				} else {
					splitFirst=true;
					handIndex++;
					System.out.println("Now you are playing for second hand..");
					Result = "Now you are playing for second hand..";
				}
			} else {
				HMapDisplayChoices.put("1. Deal", false);
				if (HMapDisplayChoices.get("4. Split")) HMapDisplayChoices.put("4. Split", false);
				if (HMapDisplayChoices.get("5. Double")) HMapDisplayChoices.put("5. Double", false);
				if (HMapDisplayChoices.get("6. Insurance")) HMapDisplayChoices.put("6. Insurance", false);
			}

			break;

		case 3:
			System.out.println("stand");				

			if (handIndex == plyCard.size()-1){
				getDealerSum();

				System.out.println("Dealer's Total: "+ dealSum);
				System.out.println("Your Total: "+ plySum[handIndex]);

				while (handIndex >= 0){
					if (plyCard.size() == 2 && handIndex == 0 && splitFirst){
						
						break;
					} else {
						if (dealSum == plySum[handIndex]){					
							System.out.println("This is Draw =(\t Your balance: "+player.getBal());
							if (!split || split && splitFirst){
								startNextRound=true;
								Result = "This is Draw =(";
							}

							
						} else if (dealSum <= BUST && dealSum > plySum[handIndex]){
							if (dealSum == BUST && dealCard.size() == 2 && insurance){
								System.out.println("Dealer got BlackJack.. But you are Insured!!!! You current balance: "+player.getBal());
								if (!split || split && splitFirst){
									startNextRound=true;
									Result = "Dealer got BlackJack.. But you are Insured!!!!";
								}

							} else if (insurance){
								Lost(player, BET/2);
								System.out.println("Dealer didn't get BlackJack, You lose your insurance money...\t Your balance: "+player.getBal());
								if (!split){
									startNextRound=true;
									Result = "Dealer didn't get BlackJack, You lose your insurance money...";
								}

							} else{
								Lost(player, BET);					
								System.out.println("You Lost...\t Your balance: "+player.getBal());
								if (!split || split && splitFirst){
									startNextRound=true;
									Result = "You Lost...";
								}

							}
							
						} else {
							Win(player, BET);
							System.out.println("You won!!!\t Money: "+player.getBal());
							if (!split || split && splitFirst){
								startNextRound=true;
								Result = "You won!!!";
							}

							
						}
						handIndex--;
					}

				}
				
			} else {
				handIndex++;
				System.out.println("Now you are playing for second hand..");
				Result = "Now you are playing for second hand..";
			}
			break;
		case 4:
			split=true;
			plyCard.add(new ArrayList<Card>());
			plyCard.get(1).add(plyCard.get(0).remove(1));
			if (plyCard.get(0).get(0).getRank().equals("A")){
				plySum[0] = 11; plySum[1] = 11;
			}

			if (HMapDisplayChoices.get("4. Split")) HMapDisplayChoices.put("4. Split", false);
			if (HMapDisplayChoices.get("5. Double")) HMapDisplayChoices.put("5. Double", false);
			if (HMapDisplayChoices.get("6. Insurance")) HMapDisplayChoices.put("6. Insurance", false);
			System.out.println("Split");
			break;
		case 5:
			doubleTheBet=true;
			BET = BET*2;

			if (HMapDisplayChoices.get("4. Split")) HMapDisplayChoices.put("4. Split", false);
			if (HMapDisplayChoices.get("5. Double")) HMapDisplayChoices.put("5. Double", false);
			if (HMapDisplayChoices.get("6. Insurance")) HMapDisplayChoices.put("6. Insurance", false);
			System.out.println("Double");
			break;
		case 6:
			insurance=true;
			if (HMapDisplayChoices.get("4. Split")) HMapDisplayChoices.put("4. Split", false);
			if (HMapDisplayChoices.get("5. Double")) HMapDisplayChoices.put("5. Double", false);
			if (HMapDisplayChoices.get("6. Insurance")) HMapDisplayChoices.put("6. Insurance", false);
			System.out.println("Insurance");
			break;
		}
	}
	

	private void getDealerSum() {
		while (dealSum < 17){
			Card cStand = deck.drawFromDeck();
			dealCard.add(cStand);
			dealSum = AddCardVal(dealSum, cStand);
		}
		startNextRound=true;
	}

	private int AddCardVal(int plySum, Card c) {
		
		if (c.getRank().equals("A")){
			if (plySum + HMapCard.get(c.getRank()) > BUST)
				plySum = plySum + 1;	
			else plySum = plySum + HMapCard.get(c.getRank());	
		} else plySum = plySum + HMapCard.get(c.getRank());	
		return plySum;
	}

	public void StrartRound() {
		
		HMapDisplayChoices = new HashMap<String, Boolean>();
		HMapDisplayChoices.put("1. Deal", true);
		HMapDisplayChoices.put("2. NewCard", false);
		HMapDisplayChoices.put("3. Stand", false);
		HMapDisplayChoices.put("4. Split", false);
		HMapDisplayChoices.put("5. Double", false);
		HMapDisplayChoices.put("6. Insurance", false);
		dealSum = 0; plySum[0] = 0; plySum[1] = 0; BET=10; handIndex=0;

		dealCard = new ArrayList<Card>(); 
		splitFirst=false; doubleTheBet=false; insurance=false; startNextRound=false; split=false;

		plyCard = new ArrayList<ArrayList<Card>>();
		plyCard.add(new ArrayList<Card>());
	}

}

package Modules;

import java.util.ArrayList;
import java.util.HashMap;

public class PokerModule extends GameAbstract {

	Player player1, player2;
	Deck deck;
	public int dealer=1, totalPlayer1 = 0, totalPlayer2 = 0, lastPlayer1 = 0, lastPlayer2 = 0, raise=0, open=0, opt;
	boolean check1=false, check2=false, call1=false, call2=false, all1=false, all2=false;
	public boolean GameisOn=true;
	public final int BIGTOKEN=100, SMALLTOKEN=50, CALL=1, RAISE=2, ALLIN=3, FOLD=4, BET=5, CHECK=6;
	HashMap<String, Boolean> HMap;
	public ArrayList<Card> openCards, plyHand1, plyHand2;
	public Player thisTurnPlayer;
	public boolean[] displayOptionArray = new boolean[6];
	public String DealerName = "";
	public int bet_amount=0, raise_amount=0;
	public Player winner;

	public PokerModule(Player p, Player p2){

		//p.Add(800);
		this.player1 = p;
		//System.out.println("You need 2 players to play this game.\nEnter the name of other player: ");
		//Scanner kbd = new Scanner(System.in);
		//this.player2 = new Player(kbd.nextLine(), 1000);
		this.player2 = p2;

		//this.deck = GetDeck();	

		/*Iterator<Entry<String, Integer>> itr = HMapCard.entrySet().iterator();

		System.out.println("Enter option number: ");
		while (itr.hasNext()){

			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)itr.next();

				System.out.println(pair.getKey()+ ": "+ pair.getValue());

		}*/
		System.out.println("------------------------- Poker: Texas Hold'em Game Started -------------------------------");
		PrepareForNextRound();
		GameisOn=true;
		//play();
	}

	public void PrepareForNextRound() {
		open=0;
		//int nextTern;
		openCards = new ArrayList<Card>();
		check1=false; check2=false; call1=false; call2=false;  all1=false; all2=false;

		System.out.println("------------------------------- New Game ----------------------------------");
		System.out.println(player1);
		System.out.println(player2);

		dealer = (dealer+1)%2;
		if (dealer == 0){
			System.out.println("Dealer is 0");
			totalPlayer1 = BIGTOKEN;
			lastPlayer1 = BIGTOKEN;
			totalPlayer2 = SMALLTOKEN;
			lastPlayer2 = SMALLTOKEN;
			thisTurnPlayer = player2;
			DealerName = player1.getName();
		} else {
			System.out.println("Dealer is 1");
			totalPlayer1 = SMALLTOKEN;
			lastPlayer1 = SMALLTOKEN;
			totalPlayer2 = BIGTOKEN;
			lastPlayer2 = BIGTOKEN;
			thisTurnPlayer = player1;
			DealerName = player2.getName();
		}
		//nextTern = dealer; 
		int temp = player1.getBal()-totalPlayer1;
		System.out.println(DealerName + " is the dealer. Puts large token: " + totalPlayer1 + ". His available balance is: " + temp);
		temp = player2.getBal()-totalPlayer2;
		System.out.println(thisTurnPlayer.getName() + " puts small token: " + totalPlayer2 + ". His available balance is: " + temp);		

		HMap = new HashMap<String, Boolean>();
		HMap.put("1. Call", true);
		HMap.put("2. Raise", true);
		HMap.put("3. All in", true);
		HMap.put("4. Fold", true);
		HMap.put("5. Bet", false);
		HMap.put("6. Check", false);
		PrepareDisplayOptionArray();

		deck = GetDeck();
		plyHand1 = new ArrayList<Card>();
		plyHand2 = new ArrayList<Card>();			
		System.out.println("On start");
		for (int i=0; i<2; i++){
			plyHand1.add(deck.drawFromDeck());
		}
		for (int i=0; i<2; i++){
			plyHand2.add(deck.drawFromDeck());
		}

		System.out.println(player1.getName() + " cards are: ");
		for (Card c : plyHand1){
			System.out.println(c);
		}
		System.out.println(player2.getName() + " cards are: ");
		for (Card c : plyHand2){
			System.out.println(c);
		}

	}

	private Deck GetDeck() {
		return new Deck();
	}

	public boolean StartPlay(int o) {
		if (open != 4){
			opt = o;
			play();
			ManageDisplayOptions(opt);
			PrepareDisplayOptionArray();
			if (opt != FOLD && GameisOn)
				ChangePlayerTurn();
		} else {
			Evaluate();
			System.out.println("Game is on flag: "+GameisOn);
		}

		return GameisOn;
	}


	private void PrepareDisplayOptionArray() {
		displayOptionArray[0] = HMap.get("1. Call");
		displayOptionArray[1] = HMap.get("2. Raise");
		displayOptionArray[2] = HMap.get("3. All in");
		displayOptionArray[3] = HMap.get("4. Fold");
		displayOptionArray[4] = HMap.get("5. Bet");
		displayOptionArray[5] = HMap.get("6. Check");
	}

	public void Evaluate(){
		plyHand1.addAll(openCards);
		plyHand2.addAll(openCards);
		//Player winner;
		if (open == 4) {
			winner = compare(plyHand1, plyHand2);
		} else {
			if (all1){
				winner = player1;
			} else if (all2) {
				winner = player2;
			} else {
				if (thisTurnPlayer == player1) {
					winner = player2;
				} else {
					winner = player1;
				}					
			}				
		}
		System.out.println("Winner is: \n" + winner.getName());
		System.out.println("Totals: " + totalPlayer1 + " " + totalPlayer2);
		if (winner == player1){
			Win(player1, totalPlayer2);
			Lost(player2, totalPlayer2);
		} else {
			Win(player2, totalPlayer1);
			Lost(player1, totalPlayer1);
		}
		System.out.println("Balance of players at the end ond of this round: ");
		System.out.println(player1);
		System.out.println(player2);
		GameisOn=false;
	}

	private void ChangePlayerTurn() {
		if (thisTurnPlayer == player1) thisTurnPlayer = player2;
		else thisTurnPlayer = player1;		
	}

	private void ManageDisplayOptions(int opt) {
		if (opt == FOLD) {
			Evaluate();
			GameisOn = false;
		} else if (opt == CHECK){
			if (check1 == true && check2 == true || (raise == 2 && (call1 == true || call2 == true)) || call1 == true && check2 == true || call2 == true && check1 == true){
				open = open+1;
				if (open == 1) {
					for (int i=0; i<3; i++){
						openCards.add(deck.drawFromDeck());
					}	
				} else if (open == 2) {
					openCards.add(deck.drawFromDeck());
				} else if (open == 3) {
					openCards.add(deck.drawFromDeck());
				}
				System.out.println("Cards on table");
				for (Card c: openCards){
					System.out.print(c + " ");
				}

				check1 = false; check2 = false; call1 = false; call2 = false; raise =0;				
				HMap.put("4. Fold", false);
			} else {
				HMap.put("4. Fold", true);
			}
			HMap.put("1. Call", false);
			HMap.put("2. Raise", false);
			HMap.put("3. All in", true);
			HMap.put("5. Bet", true);
			HMap.put("6. Check", true);
		} else if (opt == ALLIN){
			if (all1 && all2){
				//open = 4;
				Evaluate();
				GameisOn = false;
			} else {
				HMap.put("1. Call", false);
				HMap.put("2. Raise", false);
				HMap.put("3. All in", true);
				HMap.put("4. Fold", true);
				HMap.put("5. Bet", false);
				HMap.put("6. Check", false);
			}
			
		} else if (opt == CALL){
			HMap.put("1. Call", false);
			HMap.put("2. Raise", false);
			HMap.put("3. All in", true);
			HMap.put("4. Fold", true);
			HMap.put("5. Bet", true);
			HMap.put("6. Check", true);
		} else if (opt == RAISE){
			HMap.put("1. Call", true);
			HMap.put("2. Raise", true);
			HMap.put("3. All in", true);
			HMap.put("4. Fold", true);
			HMap.put("5. Bet", false);
			HMap.put("6. Check", false);
		} else if (opt == BET){
			HMap.put("1. Call", true);
			HMap.put("2. Raise", false);
			HMap.put("3. All in", true);
			HMap.put("4. Fold", true);
			HMap.put("5. Bet", false);
			HMap.put("6. Check", false);
		}
	}

	@Override
	public void play() {
		System.out.print(thisTurnPlayer);
	
		if (opt == CALL) {
			if (thisTurnPlayer == player1) {
				lastPlayer1 = totalPlayer2 - totalPlayer1;
				totalPlayer1 = totalPlayer1 + lastPlayer1; 
				call1 = true;
			} else {
				lastPlayer2 = totalPlayer1 - totalPlayer2;
				totalPlayer2 = totalPlayer2 + lastPlayer2; 
				call2 = true;
			}
			if (raise != 2)
				raise = 0;
		} else if (opt == RAISE) {
			
			if (thisTurnPlayer == player1) {
				lastPlayer1 = lastPlayer1 + raise_amount;
				totalPlayer1 = totalPlayer1 + lastPlayer1; 
			} else {
				lastPlayer2 = lastPlayer2 + raise_amount;
				totalPlayer2 = totalPlayer2 + lastPlayer2; 
			}
			if (raise == 0 || raise == 1)
				raise++;

		} else if (opt == ALLIN) {
			if (thisTurnPlayer == player1) {
				lastPlayer1 = player1.getBal() - totalPlayer1;
				totalPlayer1 = player1.getBal(); 
				all1 = true;
			} else {
				lastPlayer2 = player2.getBal() - totalPlayer2;
				totalPlayer2 = player2.getBal(); 
				all2 = true;
			}
		} else if (opt == FOLD) {
			System.out.println(thisTurnPlayer + " folded");
		} else if (opt == BET) {
			
			if (thisTurnPlayer == player1) {
				lastPlayer1 = bet_amount;
				totalPlayer1 = totalPlayer1 + lastPlayer1; 
			} else {
				lastPlayer2 = bet_amount;
				totalPlayer2 = totalPlayer2 + lastPlayer2; 
			}
		} else if (opt == CHECK) {
			if (thisTurnPlayer == player1) check1 = true;
			else check2 = true;
		}

	}

	private Player compare(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		PokerHandHelper pokerHand1 = new PokerHandHelper(hand1);
		PokerHandHelper pokerHand2 = new PokerHandHelper(hand2);
		PokerHands first = pokerHand1.getHandRank(), second = pokerHand2.getHandRank();
		int firstHandRank = Integer.valueOf(first.toString()), 
				SecHandRank = Integer.valueOf(second.toString());
		System.out.println("First player plays on: " + firstHandRank);		
		System.out.println("Second player plays on: " + SecHandRank);

		Player winner = null;

		if (firstHandRank < SecHandRank)
			winner =  player1;
		else if (firstHandRank > SecHandRank)
			winner =  player2;
		else if (firstHandRank == SecHandRank){
			if (first == PokerHands.BASE || first == PokerHands.STRAIGHT || first == PokerHands.FLUSH || first == PokerHands.STRAIGHT_FLUSH){
				if (pokerHand1.getMaxRank() == 0)
					winner =  player1;
				else if (pokerHand2.getMaxRank() == 0)
					winner =  player2;
				else if (pokerHand1.getMaxRank() > pokerHand2.getMaxRank())
					winner =  player1;
				else if (pokerHand1.getMaxRank() < pokerHand2.getMaxRank())
					winner =  player2;
				else {
					if (pokerHand1.getCountPos(1, 0) > pokerHand2.getCountPos(1, 0)) winner =  player1; 
					else  winner =  player2;
				}

			} else if (first == PokerHands.ONE_PAIR){
				int index1 = pokerHand1.getCountPos(2, 0), index2 = pokerHand2.getCountPos(2, 0);
				if (index1 == 0)
					winner =  player1;
				else if (index2 == 0)
					winner =  player2;
				else if (index1 > index2)
					winner =  player1;
				else winner =  player2;
			} else if (first == PokerHands.TWO_PAIR){
				int index1 = pokerHand1.getCountPos(2, 0), index2 = pokerHand2.getCountPos(2, 0);
				if (index1 == index2){
					int nextIndex1, nextIndex2;
					if (index1 == 0){
						nextIndex1 = pokerHand1.getCountPos(2, 12);
						nextIndex2 = pokerHand2.getCountPos(2, 12);
					} else {
						nextIndex1 = pokerHand1.getCountPos(2, index1-1);
						nextIndex2 = pokerHand2.getCountPos(2, index2-1);
					}
					if (nextIndex1 > nextIndex2)
						winner =  player1;
					else if (nextIndex1 < nextIndex2)
						winner =  player2;
					else {
						if (pokerHand1.getCountPos(1, 0) > pokerHand2.getCountPos(1, 0)) winner =  player1;
						else  winner =  player2;
					}
				} else {
					if (index1 == 0)
						winner =  player1;
					else if (index2 == 0)
						winner =  player2;
					else if (index1 > index2)
						winner =  player1;
					else 
						winner =  player2;
				}
			} else if (first == PokerHands.THREE_KIND || first == PokerHands.FOUR_KIND || first == PokerHands.FULL_HOUSE){
				int count=0;
				if (first == PokerHands.THREE_KIND || first == PokerHands.FULL_HOUSE)
					count=3;
				else if (first == PokerHands.FOUR_KIND)
					count=4;

				int index1 = pokerHand1.getCountPos(count, 0), index2 = pokerHand2.getCountPos(count, 0);

				if (index1 == 0)
					winner =  player1;
				else if (index2 == 0)
					winner =  player2;
				else if (index1 > index2)
					winner =  player1;
				else 
					winner =  player2;

			} 
		}
		return winner;
	}

}

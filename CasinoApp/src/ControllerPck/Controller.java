package ControllerPck;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.BlackJack;
import Client.Poker;
import Client.PokerStart;
import Client.SlotMachine;
import Client.SlotMachineStart;
import Modules.BlackJackModule;
import Modules.Player;
import Modules.PokerModule;
import Modules.SlotMachineModule;

public class Controller {

	public Player player;
	public Player otherPlayer;

	SlotMachineStart slotMachineStartFrame;
	PokerStart pokerStartFrame;

	SlotMachineModule module_slotMachine; 					//Reference to Game module Slot Machine
	BlackJackModule module_blackjack; 						//Reference to Game module BlackJack
	PokerModule module_poker; 								//Reference to Game module Poker

	SlotMachine slotMachineGameUI;							//Reference to SlotMachine UI
	BlackJack blackjackGameUI;								//Reference to BlackJack UI
	Poker pokerGameUI;										//Reference to Poker UI
	Poker pokerGameUI2;										//Reference to other player's UI in Poker

	public final String SLOT_MACHINE_STARTGAME_BUTTON = "Slot Machine Start";
	public final String SLOT_MACHINE_REFRESHGAME_BUTTON = "Slot Machine Refresh";

	public final String BLACKJACK_DEAL = "BlackJack Deal";
	public final String BLACKJACK_NEWCARD = "BlackJack New Card";
	public final String BLACKJACK_STAND = "BlackJack Stand";
	public final String BLACKJACK_DOUBLE = "BlackJack Double";
	public final String BLACKJACK_SPLIT = "BlackJack Split";
	public final String BLACKJACK_INSURANCE = "BlackJack Insurance";

	public final String POKER_STARTGAME_BUTTON = "Poker Start";
	public final String POKER_CALL_BUTTON = "Poker Call";
	public final String POKER_RAISE_BUTTON = "Poker Raise";
	public final String POKER_ALLIN_BUTTON = "Poker All in";
	public final String POKER_FOLD_BUTTON = "Poker Fold";
	public final String POKER_BET_BUTTON = "Poker Bet";
	public final String POKER_CHECK_BUTTON = "Poker Check";

	public final String POKER_HELP_OK_BUTTON = "Raise ok";

	public final String SLOT_MACHINE_RADIO = "Radio Slot Machine";
	public final String BLACKJACK_RADIO = "Radio BlackJack";
	public final String POKER_RADIO = "Radio Poker";

	public int pokerBetAmount;
	public ArrayList<String> playersWaiting;
	public static HashMap<String, PokerStart> HashStoringWaitingPlayerUI = new HashMap<String, PokerStart>(); 

	public void ButtonPressAction(String button){
		boolean[] blkjckDisplyChoices = new boolean[6]; 
		String BlkJckRes = "";
		switch (button){
		case SLOT_MACHINE_STARTGAME_BUTTON:
			module_slotMachine = new SlotMachineModule(player, SlotMachineStart.seedAmount);		
			module_slotMachine.SelectLines(SlotMachineStart.linesSelected);

			slotMachineGameUI = new SlotMachine(slotMachineStartFrame.controller);
			slotMachineGameUI.setVisible(true);
			break;
		case SLOT_MACHINE_REFRESHGAME_BUTTON:
			System.out.println("Refresh Button");
			if (player.getBal() - module_slotMachine.BET >= 0){
				module_slotMachine.play();
				slotMachineGameUI.ChangeState(module_slotMachine.playBoard, module_slotMachine.points);
				slotMachineGameUI.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(slotMachineGameUI, "You have no money");
				slotMachineGameUI.dispose();
			}
			break;
		case BLACKJACK_DEAL:
			System.out.println("Deal Button");	
			blackjackGameUI.RefreshScreenForNextRound();
			blackjackGameUI.lblresult.setVisible(false);

			if (player.getBal()-module_blackjack.BET>=0) {
				module_blackjack.StrartRound();
				BlkJckRes = module_blackjack.StartPlay(1);

				if (!module_blackjack.startNextRound){
					blackjackGameUI.SetResultLabel(BlkJckRes);
					blkjckDisplyChoices[0] = module_blackjack.HMapDisplayChoices.get("1. Deal");
					blkjckDisplyChoices[1] = module_blackjack.HMapDisplayChoices.get("2. NewCard");
					blkjckDisplyChoices[2] = module_blackjack.HMapDisplayChoices.get("3. Stand");
					blkjckDisplyChoices[3] = module_blackjack.HMapDisplayChoices.get("4. Split");
					blkjckDisplyChoices[4] = module_blackjack.HMapDisplayChoices.get("5. Double");
					blkjckDisplyChoices[5] = module_blackjack.HMapDisplayChoices.get("6. Insurance");
					blackjackGameUI.DisplayBoardChanges(blkjckDisplyChoices, module_blackjack.dealCard, module_blackjack.plyCard, player.getBal());
				} else {
					blackjackGameUI.SetResultLabel(BlkJckRes);
					blkjckDisplyChoices[0] = true; blkjckDisplyChoices[1] = false;
					blkjckDisplyChoices[2] = false; blkjckDisplyChoices[3] = false;
					blkjckDisplyChoices[4] = false; blkjckDisplyChoices[5] = false;
					blackjackGameUI.DisplayBoardChanges(blkjckDisplyChoices, module_blackjack.dealCard, module_blackjack.plyCard, player.getBal());
					module_blackjack.StrartRound();
				}
				blackjackGameUI.lblBetAmount.setText("Bet Amount: " + module_blackjack.BET);
				blackjackGameUI.lblBetAmount.setVisible(true);
				blackjackGameUI.repaint();
				blackjackGameUI.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(blackjackGameUI, "You have no money");
				blackjackGameUI.dispose();
			}
			break;
		case BLACKJACK_NEWCARD:
			System.out.println("BLACKJACK_NEWCARD");
			BlkJckRes = module_blackjack.StartPlay(2);

			if (!module_blackjack.startNextRound){
				blkjckDisplyChoices[0] = module_blackjack.HMapDisplayChoices.get("1. Deal");
				blkjckDisplyChoices[1] = module_blackjack.HMapDisplayChoices.get("2. NewCard");
				blkjckDisplyChoices[2] = module_blackjack.HMapDisplayChoices.get("3. Stand");
				blkjckDisplyChoices[3] = module_blackjack.HMapDisplayChoices.get("4. Split");
				blkjckDisplyChoices[4] = module_blackjack.HMapDisplayChoices.get("5. Double");
				blkjckDisplyChoices[5] = module_blackjack.HMapDisplayChoices.get("6. Insurance");
				blackjackGameUI.DisplayBoardChanges(blkjckDisplyChoices, module_blackjack.dealCard, module_blackjack.plyCard, player.getBal());
			} else {
				blackjackGameUI.SetResultLabel(BlkJckRes);
				blkjckDisplyChoices[0] = true; blkjckDisplyChoices[1] = false;
				blkjckDisplyChoices[2] = false; blkjckDisplyChoices[3] = false;
				blkjckDisplyChoices[4] = false; blkjckDisplyChoices[5] = false;
				blackjackGameUI.DisplayBoardChanges(blkjckDisplyChoices, module_blackjack.dealCard, module_blackjack.plyCard, player.getBal());
				module_blackjack.StrartRound();
			}
			blackjackGameUI.repaint();
			blackjackGameUI.setVisible(true);

			if (module_blackjack.doubleTheBet && module_blackjack.plyCard.get(0).size() >= 3) 	
				ButtonPressAction(BLACKJACK_STAND);
			break;
		case BLACKJACK_STAND:
			System.out.println("BLACKJACK_STAND");
			BlkJckRes = module_blackjack.StartPlay(3);

			if (!module_blackjack.startNextRound){
				blkjckDisplyChoices[0] = module_blackjack.HMapDisplayChoices.get("1. Deal");
				blkjckDisplyChoices[1] = module_blackjack.HMapDisplayChoices.get("2. NewCard");
				blkjckDisplyChoices[2] = module_blackjack.HMapDisplayChoices.get("3. Stand");
				blkjckDisplyChoices[3] = module_blackjack.HMapDisplayChoices.get("4. Split");
				blkjckDisplyChoices[4] = module_blackjack.HMapDisplayChoices.get("5. Double");
				blkjckDisplyChoices[5] = module_blackjack.HMapDisplayChoices.get("6. Insurance");
				blackjackGameUI.DisplayBoardChanges(blkjckDisplyChoices, module_blackjack.dealCard, module_blackjack.plyCard, player.getBal());
			} else {
				blackjackGameUI.SetResultLabel(BlkJckRes);
				blkjckDisplyChoices[0] = true; blkjckDisplyChoices[1] = false;
				blkjckDisplyChoices[2] = false; blkjckDisplyChoices[3] = false;
				blkjckDisplyChoices[4] = false; blkjckDisplyChoices[5] = false;
				blackjackGameUI.DisplayBoardChanges(blkjckDisplyChoices, module_blackjack.dealCard, module_blackjack.plyCard, player.getBal());
				module_blackjack.StrartRound();
			}
			blackjackGameUI.repaint();
			blackjackGameUI.setVisible(true);
			break;
		case BLACKJACK_DOUBLE:
			System.out.println("BLACKJACK_DOUBLE");
			BlkJckRes = module_blackjack.StartPlay(5);

			blkjckDisplyChoices[0] = module_blackjack.HMapDisplayChoices.get("1. Deal");
			blkjckDisplyChoices[1] = module_blackjack.HMapDisplayChoices.get("2. NewCard");
			blkjckDisplyChoices[2] = module_blackjack.HMapDisplayChoices.get("3. Stand");
			blkjckDisplyChoices[3] = module_blackjack.HMapDisplayChoices.get("4. Split");
			blkjckDisplyChoices[4] = module_blackjack.HMapDisplayChoices.get("5. Double");
			blkjckDisplyChoices[5] = module_blackjack.HMapDisplayChoices.get("6. Insurance");
			blackjackGameUI.DisplayBoardChanges(blkjckDisplyChoices, module_blackjack.dealCard, module_blackjack.plyCard, player.getBal());
			blackjackGameUI.lblBetAmount.setText("Bet Amount: " + module_blackjack.BET);
			blackjackGameUI.lblBetAmount.setVisible(true);
			blackjackGameUI.repaint();
			blackjackGameUI.setVisible(true);
			break;
		case BLACKJACK_SPLIT:
			System.out.println("BLACKJACK_SPLIT");
			BlkJckRes = module_blackjack.StartPlay(4);

			blkjckDisplyChoices[0] = module_blackjack.HMapDisplayChoices.get("1. Deal");
			blkjckDisplyChoices[1] = module_blackjack.HMapDisplayChoices.get("2. NewCard");
			blkjckDisplyChoices[2] = module_blackjack.HMapDisplayChoices.get("3. Stand");
			blkjckDisplyChoices[3] = module_blackjack.HMapDisplayChoices.get("4. Split");
			blkjckDisplyChoices[4] = module_blackjack.HMapDisplayChoices.get("5. Double");
			blkjckDisplyChoices[5] = module_blackjack.HMapDisplayChoices.get("6. Insurance");
			blackjackGameUI.DisplayBoardChanges(blkjckDisplyChoices, module_blackjack.dealCard, module_blackjack.plyCard, player.getBal());
			blackjackGameUI.repaint();
			blackjackGameUI.setVisible(true);
			break;
		case BLACKJACK_INSURANCE:
			System.out.println("BLACKJACK_INSURANCE");
			BlkJckRes = module_blackjack.StartPlay(6);

			blkjckDisplyChoices[0] = module_blackjack.HMapDisplayChoices.get("1. Deal");
			blkjckDisplyChoices[1] = module_blackjack.HMapDisplayChoices.get("2. NewCard");
			blkjckDisplyChoices[2] = module_blackjack.HMapDisplayChoices.get("3. Stand");
			blkjckDisplyChoices[3] = module_blackjack.HMapDisplayChoices.get("4. Split");
			blkjckDisplyChoices[4] = module_blackjack.HMapDisplayChoices.get("5. Double");
			blkjckDisplyChoices[5] = module_blackjack.HMapDisplayChoices.get("6. Insurance");
			blackjackGameUI.DisplayBoardChanges(blkjckDisplyChoices, module_blackjack.dealCard, module_blackjack.plyCard, player.getBal());
			blackjackGameUI.repaint();
			blackjackGameUI.setVisible(true);
			break;
		case POKER_STARTGAME_BUTTON:
			//otherPlayer = new Player("Kasturi", 1000);								//Other player later replaced by selection

			pokerGameUI = new Poker(pokerStartFrame.controller);
			pokerGameUI2 = new Poker(pokerStartFrame.controller);

			if (player.getBal() > 0 && otherPlayer.getBal() > 0) {
				module_poker = new PokerModule(player, otherPlayer);

				PrepareUIForPokerNextRound();

			} else if (player.getBal() > 0) {
				JOptionPane.showMessageDialog(pokerGameUI, player.getName() + " is Winner!!!!");
			} else {
				JOptionPane.showMessageDialog(pokerGameUI, otherPlayer.getName() + " is Winner!!!!");
			}						
			break;
		case POKER_CALL_BUTTON:
			System.out.println("in Controller Call");
			int diffBetTotal = module_poker.totalPlayer1 - module_poker.totalPlayer2;
			if (diffBetTotal<0) diffBetTotal=-1*diffBetTotal;
			if (module_poker.thisTurnPlayer.getBal() - diffBetTotal > 0) {
				if (!module_poker.StartPlay(module_poker.CALL)) {

					module_poker.PrepareForNextRound();
					module_poker.GameisOn = true;

					if (player.getBal() > 0 && otherPlayer.getBal() > 0) {
						PrepareUIForPokerNextRound();
					} else if (player.getBal() > 0) {
						JOptionPane.showMessageDialog(pokerGameUI, player.getName() + " is Winner!!!!");
					} else {
						JOptionPane.showMessageDialog(pokerGameUI, otherPlayer.getName() + " is Winner!!!!");
					}	

				} else {
					int potmoney_call = module_poker.totalPlayer1 + module_poker.totalPlayer2;
					if (module_poker.thisTurnPlayer == player) {
						PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(player.getBal()-module_poker.totalPlayer1), potmoney_call, otherPlayer.getName() + " Call");
						PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(otherPlayer.getBal() - module_poker.totalPlayer2), potmoney_call, pokerGameUI2.lblOtherPlayersMove.getText());
					} else {
						PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(otherPlayer.getBal()-module_poker.totalPlayer2), potmoney_call, player.getName() + " Call");
						PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(player.getBal()-module_poker.totalPlayer1), potmoney_call, pokerGameUI.lblOtherPlayersMove.getText());
					}

					AlternatePokerPlayers();
				}
			} else {
				if (module_poker.thisTurnPlayer == player) {
					JOptionPane.showMessageDialog(pokerGameUI, otherPlayer.getName() + " is Winner!!!!");
					JOptionPane.showMessageDialog(pokerGameUI2, otherPlayer.getName() + " is Winner!!!!");
				} else {
					JOptionPane.showMessageDialog(pokerGameUI, player.getName() + " is Winner!!!!");
					JOptionPane.showMessageDialog(pokerGameUI2, player.getName() + " is Winner!!!!");
				}
				pokerGameUI.dispose();
				pokerGameUI2.dispose();
			}

			break;
		case POKER_RAISE_BUTTON:
			System.out.println("in Controller Raise");

			String[] options = {"OK"};
			JPanel panel = new JPanel();
			JLabel lbl = new JLabel("Enter bet amount: 100 + ");
			JTextField txt = new JTextField(10);
			panel.add(lbl);
			panel.add(txt);
			int selectedOption;

			if (module_poker.thisTurnPlayer == player)
				selectedOption = JOptionPane.showOptionDialog(pokerGameUI, panel, module_poker.thisTurnPlayer.getName(), JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
			else 
				selectedOption = JOptionPane.showOptionDialog(pokerGameUI2, panel, module_poker.thisTurnPlayer.getName(), JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);

			if (selectedOption == 0){
				pokerBetAmount = 100 + Integer.valueOf(txt.getText());
			}
			module_poker.raise_amount = pokerBetAmount;

			int totalForThisTurnPlayer;
			if (module_poker.thisTurnPlayer == player) {
				totalForThisTurnPlayer = module_poker.totalPlayer1;
			} else {
				totalForThisTurnPlayer = module_poker.totalPlayer2;
			}

			if (module_poker.thisTurnPlayer.getBal()-totalForThisTurnPlayer-pokerBetAmount > 0) {
				if (!module_poker.StartPlay(module_poker.RAISE)) {
					JOptionPane.showMessageDialog(pokerGameUI, module_poker.winner.getName() + " is Winner!!!!");
					JOptionPane.showMessageDialog(pokerGameUI2, module_poker.winner.getName() + " is Winner!!!!");

					module_poker.PrepareForNextRound();
					module_poker.GameisOn = true;
					PrepareUIForPokerNextRound();					
				} else {
					AlternatePokerPlayers();

					int potmoney_raise = module_poker.totalPlayer1 + module_poker.totalPlayer2;

					if (module_poker.thisTurnPlayer == player) {
						PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(player.getBal() - module_poker.totalPlayer1), potmoney_raise, otherPlayer.getName() + " Raise: " + pokerBetAmount);
						PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(otherPlayer.getBal()-module_poker.totalPlayer2), potmoney_raise, pokerGameUI2.lblOtherPlayersMove.getText());
					} else {
						PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(otherPlayer.getBal() - module_poker.totalPlayer2), potmoney_raise, player.getName() + " Raise: " + pokerBetAmount);
						PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(player.getBal()-module_poker.totalPlayer1), potmoney_raise, pokerGameUI.lblOtherPlayersMove.getText());
					}

				}
			} else {
				module_poker.raise_amount = 0;
				if (module_poker.thisTurnPlayer == player)
					JOptionPane.showMessageDialog(pokerGameUI, "You don't have enough amount for raise. No raise is applied");
				else
					JOptionPane.showMessageDialog(pokerGameUI2, "You don't have enough amount for raise. No raise is applied");
			}
			break;
		case POKER_ALLIN_BUTTON:
			System.out.println("in Controller All In");
			if (!module_poker.StartPlay(module_poker.ALLIN)) {
				JOptionPane.showMessageDialog(pokerGameUI, module_poker.winner.getName() + " is Winner!!!!");
				JOptionPane.showMessageDialog(pokerGameUI2, module_poker.winner.getName() + " is Winner!!!!");
				pokerGameUI.dispose();
				pokerGameUI2.dispose();

			} else {
				AlternatePokerPlayers();
				int potmoney_all = module_poker.totalPlayer1 + module_poker.totalPlayer2;
				if (module_poker.thisTurnPlayer == player) {
					PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(player.getBal() - module_poker.totalPlayer1), potmoney_all, otherPlayer.getName() + " All In");
					PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(0), potmoney_all, pokerGameUI2.lblOtherPlayersMove.getText());
				} else {
					PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(otherPlayer.getBal() - module_poker.totalPlayer2), potmoney_all, player.getName() + " All In");
					PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(0), potmoney_all, pokerGameUI.lblOtherPlayersMove.getText());
				}
			}
			break;
		case POKER_FOLD_BUTTON:
			System.out.println("in Controller Fold");
			if (!module_poker.StartPlay(module_poker.FOLD)) {
				JOptionPane.showMessageDialog(pokerGameUI, module_poker.winner.getName() + " is Winner!!!!");
				JOptionPane.showMessageDialog(pokerGameUI2, module_poker.winner.getName() + " is Winner!!!!");

				module_poker.PrepareForNextRound();
				module_poker.GameisOn = true;				
				PrepareUIForPokerNextRound();
			}
			break;
		case POKER_BET_BUTTON:
			System.out.println("in Controller Bet");

			String[] options_bet = {"OK"};
			JPanel panel_bet = new JPanel();
			JLabel lbl_bet = new JLabel("Enter bet amount: ");
			JTextField txt_bet = new JTextField(10);
			panel_bet.add(lbl_bet);
			panel_bet.add(txt_bet);

			int selectedOption_bet;
			if (module_poker.thisTurnPlayer == player)
				selectedOption_bet = JOptionPane.showOptionDialog(pokerGameUI, panel_bet, module_poker.thisTurnPlayer.getName(), JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options_bet , options_bet[0]);
			else 
				selectedOption_bet = JOptionPane.showOptionDialog(pokerGameUI2, panel_bet,  module_poker.thisTurnPlayer.getName(), JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options_bet , options_bet[0]);

			if (selectedOption_bet == 0){
				pokerBetAmount = Integer.valueOf(txt_bet.getText());
			}
			module_poker.bet_amount = pokerBetAmount;

			int totalForThisTurnPlayer_bet;
			if (module_poker.thisTurnPlayer == player) {
				totalForThisTurnPlayer_bet = module_poker.totalPlayer1;
			} else {
				totalForThisTurnPlayer_bet = module_poker.totalPlayer2;
			}

			if (module_poker.thisTurnPlayer.getBal()-totalForThisTurnPlayer_bet-pokerBetAmount > 0) {
				if (!module_poker.StartPlay(module_poker.BET)) {
					JOptionPane.showMessageDialog(pokerGameUI, module_poker.winner.getName() + " is Winner!!!!");
					JOptionPane.showMessageDialog(pokerGameUI2, module_poker.winner.getName() + " is Winner!!!!");

					module_poker.PrepareForNextRound();
					module_poker.GameisOn = true;
					PrepareUIForPokerNextRound();
				} else {
					AlternatePokerPlayers();

					int potmoney_bet = module_poker.totalPlayer1 + module_poker.totalPlayer2;

					if (module_poker.thisTurnPlayer == player) {
						PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(player.getBal() - module_poker.totalPlayer1), potmoney_bet, otherPlayer.getName() + " Bet: " + pokerBetAmount);
						PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(otherPlayer.getBal()-module_poker.totalPlayer2), potmoney_bet, pokerGameUI2.lblOtherPlayersMove.getText());
					} else {
						PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(otherPlayer.getBal() - module_poker.totalPlayer2), potmoney_bet, player.getName() + " Bet: " + pokerBetAmount);
						PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(player.getBal()-module_poker.totalPlayer1), potmoney_bet, pokerGameUI.lblOtherPlayersMove.getText());
					}
				}				

			} else {
				module_poker.bet_amount = 0;
				if (module_poker.thisTurnPlayer == player)
					JOptionPane.showMessageDialog(pokerGameUI, "You don't have enough amount for bet. No raise is applied");
				else
					JOptionPane.showMessageDialog(pokerGameUI2, "You don't have enough amount for bet. No raise is applied");
			}
			break;
		case POKER_CHECK_BUTTON:
			System.out.println("in Controller Check");
			if (!module_poker.StartPlay(module_poker.CHECK)) {
				JOptionPane.showMessageDialog(pokerGameUI, module_poker.winner.getName() + " is Winner!!!!");
				JOptionPane.showMessageDialog(pokerGameUI2, module_poker.winner.getName() + " is Winner!!!!");

				module_poker.PrepareForNextRound();
				module_poker.GameisOn = true;
				PrepareUIForPokerNextRound();

			} else {
				pokerGameUI.AddDealerCards(module_poker.openCards);
				pokerGameUI2.AddDealerCards(module_poker.openCards);

				int potmoney_check = module_poker.totalPlayer1 + module_poker.totalPlayer2;
				if (module_poker.thisTurnPlayer == player) {
					PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(player.getBal() - module_poker.totalPlayer1), potmoney_check, otherPlayer.getName() + " Check");
					PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(otherPlayer.getBal() - module_poker.totalPlayer2), potmoney_check, pokerGameUI2.lblOtherPlayersMove.getText());
				} else {
					PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(otherPlayer.getBal() - module_poker.totalPlayer2), potmoney_check, player.getName() + " Check");
					PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(player.getBal() - module_poker.totalPlayer1), potmoney_check, pokerGameUI.lblOtherPlayersMove.getText());
				}


				AlternatePokerPlayers();
				pokerGameUI.repaint();
				pokerGameUI.setVisible(true);
				pokerGameUI2.repaint();
				pokerGameUI2.setVisible(true);
			}			
			break;
		}
	}

	private void PrepareUIForPokerNextRound() {

		int potmoney = module_poker.totalPlayer1 + module_poker.totalPlayer2;
		pokerGameUI.RefreshScreenForNextRound();
		pokerGameUI.lblYourCards.setText(player.getName() + "'s Cards");
		pokerGameUI.AddPlayerCards(module_poker.plyHand1);
		pokerGameUI.lblDealer.setText(module_poker.DealerName + " is Dealer");
		pokerGameUI.lblDealer.setVisible(true);
		PokerUpdateScreenAmountsLastMove(pokerGameUI, String.valueOf(player.getBal()-module_poker.totalPlayer1), potmoney, "New Game");

		pokerGameUI2.RefreshScreenForNextRound();
		pokerGameUI2.lblYourCards.setText(otherPlayer.getName() + "'s Cards");
		pokerGameUI2.AddPlayerCards(module_poker.plyHand2);
		pokerGameUI2.lblDealer.setText(module_poker.DealerName + " is Dealer");
		pokerGameUI2.lblDealer.setVisible(true);
		PokerUpdateScreenAmountsLastMove(pokerGameUI2, String.valueOf(otherPlayer.getBal()-module_poker.totalPlayer2), potmoney, "New Game");

		if (module_poker.thisTurnPlayer == player){
			pokerGameUI2.inactivateButtons();
		} else {
			pokerGameUI.inactivateButtons();
		}
		AlternatePokerPlayers();
	}

	private void PokerUpdateScreenAmountsLastMove(Poker UIObject, String bal, int potmoney, String lastMove) {
		UIObject.lblCurrrentBalance.setText("Your balance is: " + bal);
		UIObject.lblMoneyInPot.setText("Money in Pot: " + potmoney);
		UIObject.lblOtherPlayersMove.setText(lastMove);
		UIObject.repaint();
		UIObject.setVisible(true);
	}

	private void AlternatePokerPlayers(){
		if (module_poker.thisTurnPlayer == player){
			pokerGameUI2.inactivateButtons();
			pokerGameUI.ActivateButtons(module_poker.displayOptionArray);
		} else {
			pokerGameUI.inactivateButtons();
			pokerGameUI2.ActivateButtons(module_poker.displayOptionArray);
		}
	}

	public void RadioAction(String radioButton){
		switch (radioButton){
		case SLOT_MACHINE_RADIO:
			slotMachineStartFrame = new SlotMachineStart(this);
			slotMachineStartFrame.setVisible(true);
			break;
		case BLACKJACK_RADIO:		
			module_blackjack = new BlackJackModule(player);
			blackjackGameUI = new BlackJack(this);
			blackjackGameUI.setVisible(true);
			break;
		case POKER_RADIO:
			Sqlite SqlObj = new Sqlite();
			playersWaiting = new ArrayList<String>();
			playersWaiting = SqlObj.getListOfPlayersWaitingForPoker();

			pokerStartFrame = new PokerStart(this);
			
			if (playersWaiting == null){
				SqlObj.updatePokerStatusWait(player, "true");
				pokerStartFrame.labelOtherPlayer.setText("No player available. Wait for other player");				
				pokerStartFrame.btnNewButton.setVisible(false);
				pokerStartFrame.setVisible(true);
				HashStoringWaitingPlayerUI.put(player.getName(), pokerStartFrame);
			} else {
				pokerStartFrame.labelOtherPlayer.setText("You will be playing with " + playersWaiting.get(0).split(",")[0]);
				pokerStartFrame.setVisible(true);
				otherPlayer = new Player(playersWaiting.get(0).split(",")[0], Integer.valueOf(playersWaiting.get(0).split(",")[1]));
				SqlObj.updatePokerStatusWait(player, "false");
				System.out.println(otherPlayer.getName() + " " + otherPlayer.getBal());
				HashStoringWaitingPlayerUI.get(otherPlayer.getName()).dispose();
				HashStoringWaitingPlayerUI.remove(otherPlayer.getName());
				pokerStartFrame.btnNewButton.setVisible(true);;
			}
		
			
			break;
		}

	}
}

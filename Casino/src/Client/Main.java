package Client;

import java.util.Scanner;

import Game.BlackJack;
import Game.SlotMachine;

public class Main {

	public static void main(String args[]){
		Scanner kbd = new Scanner(System.in);
		System.out.println("Enter your Name: ");
		int startAmount = 200;		
		Player player = new Player(kbd.nextLine(), startAmount);
		
		System.out.println("Game (Poker: Texas Hold'em / Blackjack / Slot Machine): ");
		
		switch (kbd.nextLine()){
		case "Poker: Texas Hold'em": 
			System.out.println("Welcome to Poker: Texas Hold'em!!!");
			break;
		case "Blackjack": 
			System.out.println("Welcome to Blackjack!!!");
			BlackJack blkJck = new BlackJack(player);
			break;
		case "Slot Machine":
			System.out.println("Welcome to Slot Machine!!!");
			SlotMachine slot = new SlotMachine(player);
			break;
		}
		
	}
}

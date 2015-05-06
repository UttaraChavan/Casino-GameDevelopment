package Modules;

import Client.Poker;
import ControllerPck.Controller;

public class PokerOtherPlayer implements Runnable{

	public Controller otherController ;
	Thread t;
	Poker pokerGameUI;
	
	public PokerOtherPlayer(Controller c){
		otherController = c;
		t = new Thread();
		t.start();
	}
	
	public Thread GetThread(){
		return t;
	}
	
	@Override
	public void run() {
		
		pokerGameUI = new Poker(otherController);
		pokerGameUI.setVisible(true);
	}

}

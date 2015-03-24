package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Client.Player;

public class SlotMachine extends GameAbstract {

	Player player;
	int[][] playBoard = new int[3][5];
	int[][] winPoints = {{2500,250,50,5}, 
			{500,100,25,2}, 
			{400,80,20,2}, 
			{350,75,10,0}, 
			{250,50,10,0},
			{200,50,10,0}, 
			{125,50,5,0}, 
			{125,25,5,0}, 
			{100,25,5,0}, 
			{100,25,5,0}};
	int BET;
	List list;
	HashMap<Integer, String> linesSelected = new HashMap<Integer, String>();

	public SlotMachine(Player p){
		this.player = p;
		BET=1;
		GenerateOriginalList();

		System.out.println("------------------------- BlackJack Game Started -------------------------------");
		play();
	}

	private void GenerateOriginalList() {
		// TODO Auto-generated method stub
		list = new ArrayList();
		for (int i=1; i<51; i++){
			list.add(i%10+1);
		}

		Iterator itr = list.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

		System.out.println(player);
		FixBet();
		System.out.println("Retirned");
		SelectLines();
		while (player.getBal()+BET>0){

			Collections.shuffle(list);
			/*Iterator itr = list.iterator();
			while(itr.hasNext()){
				System.out.println(itr.next());
			}*/

			Iterator itr = list.iterator();
			while (itr.hasNext()){
				for (int i=0; i<playBoard.length; i++)
					for (int j=0; j<playBoard[0].length; j++)
						playBoard[i][j] = (Integer) itr.next();
				break;
			}

			System.out.println("PlayBoard");
			for (int i=0; i<playBoard.length; i++){
				for (int j=0; j<playBoard[0].length; j++)
					System.out.print(playBoard[i][j]+" ");
				System.out.println();
			}

			int points = 0;
			for (Map.Entry<Integer, String> entry : linesSelected.entrySet()) {
				//System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				int line = entry.getKey();
				String[] position = entry.getValue().split(";");
				HashMap<Integer, Integer> span = new HashMap<Integer, Integer>();
				//int[] span = new int[5];
				span.put(playBoard[Integer.valueOf(position[0].substring(1,2))][Integer.valueOf(position[0].substring(3,4))], 1);
				//span[0] = 1;

				//switch (line){
				//case 1:
				for (int i=1; i<position.length; i++){
					System.out.println("Current: " + playBoard[Integer.valueOf(position[i].substring(1,2))][Integer.valueOf(position[i].substring(3,4))]);
					System.out.println("Prior: " + playBoard[Integer.valueOf(position[i-1].substring(1,2))][Integer.valueOf(position[i-1].substring(3,4))]);
					if(playBoard[Integer.valueOf(position[i].substring(1,2))][Integer.valueOf(position[i].substring(3,4))] == playBoard[Integer.valueOf(position[i-1].substring(1,2))][Integer.valueOf(position[i-1].substring(3,4))]){
						int val = span.get(playBoard[Integer.valueOf(position[i-1].substring(1,2))][Integer.valueOf(position[i-1].substring(3,4))])+1;
						span.put(playBoard[Integer.valueOf(position[i].substring(1,2))][Integer.valueOf(position[i].substring(3,4))], val);
						//span[i] = span[i+1] + 1;
					} else {

						span.put(playBoard[Integer.valueOf(position[i].substring(1,2))][Integer.valueOf(position[i].substring(3,4))], 1);
						//span[i] = 1;
					}
				}	
				//break;
				//case 2:
				//for (int i=1; i<position.length; i++){

				//}
				//break;
				//case 3:
				//for (int i=1; i<position.length; i++){

				//}
				//break;
				//case 4:
				//for (int i=1; i<position.length; i++){

				//}
				//break;
				//case 5:
				//for (int i=1; i<position.length; i++){

				//}
				//break;
				//}
				points = points + Evaluate(span);

				System.out.println(line + "Points earned: " + points);
			}

			if (points == 0){
				Lost(player, BET);
			} else {
				Win(player, BET);
			}
		}
	}

	private int Evaluate(HashMap<Integer, Integer> span) {
		// TODO Auto-generated method stub
		int sum=0;

		for (Map.Entry<Integer, Integer> entry : span.entrySet()) {
			//sum = sum + winPoints[entry.getKey()-1][5-entry.getValue()];
			System.out.println(entry.getKey() + "-->" + entry.getValue());
		}

		return sum;
	}

	private void FixBet() {
		// TODO Auto-generated method stub
		Scanner kbd = new Scanner(System.in);
		boolean f=false;
		while (!f){
			System.out.println("Select Option: ");
			if (BET==1){
				System.out.println("1. Incrrement");
				System.out.println("3. Fix");
			} else if (BET==player.getBal()){
				System.out.println("2. Decrement");
				System.out.println("3. Fix");
			} else {
				System.out.println("1. Increament");
				System.out.println("2. Decrement");
				System.out.println("3. Fix");
			}

			int b = kbd.nextInt();
			if (b == 1)
				BET++;
			else if (b == 2)
				BET--;
			else if (b == 3)
				f=true;
		}
		System.out.println("BET after increament " + BET);
	}

	private void SelectLines() {
		// TODO Auto-generated method stub
		Scanner kbd = new Scanner(System.in);
		System.out.println("Select Line 1: (Y/N)");
		if (kbd.nextLine().equals("Y"))
			linesSelected.put(1, "(1,0);(1,1);(1,2);(1,3);(1,4)");
		System.out.println("Select Line 2: (Y/N)");
		if (kbd.nextLine().equals("Y"))
			linesSelected.put(2, "(0,0);(0,1);(0,2);(0,3);(0,4)");
		System.out.println("Select Line 3: (Y/N)");
		if (kbd.nextLine().equals("Y"))
			linesSelected.put(3, "(2,0);(2,1);(2,2);(2,3);(2,4)");
		System.out.println("Select Line 4: (Y/N)");
		if (kbd.nextLine().equals("Y"))
			linesSelected.put(4, "(0,0);(1,1);(2,2);(1,3);(0,4)");
		System.out.println("Select Line 5: (Y/N)");
		if (kbd.nextLine().equals("Y"))
			linesSelected.put(5, "(2,0);(1,1);(0,2);(1,3);(2,4)");

		/*Iterator itr = linesSelected.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}*/

		BET = BET*linesSelected.size();
		System.out.println("BET after select lines " + BET);
	}
}

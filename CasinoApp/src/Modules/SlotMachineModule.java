package Modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SlotMachineModule extends GameAbstract {

	Player player;
	public int[][] playBoard = new int[3][5];						//Matrix for play
	int[][] winPoints = {{2500,250,100,50},  						//Matrix to store points score row : value generated
			{500,100,50,20}, 										//Column : number of times value appears
			{400,80,25,10}, 										//First row number 10 last row number 1
			{350,75,20,5}, 
			{250,50,20,5},
			{200,50,15,5}, 
			{125,50,15,0}, 
			{125,25,10,0}, 
			{100,25,10,0}, 
			{100,25,10,0}};
	public int BET, points;
	List<Integer> list;												//Lists all numbers from 1 to 10 each repeated 5 times
	HashMap<Integer, String> linesSelected = new HashMap<Integer, String>();


	public SlotMachineModule(Player p, int bet){
		this.player = p;
		BET=bet;

		GenerateOriginalList();

		System.out.println("------------------------- Slot Machine Game Started -------------------------------");
		//play();
	}

	private void GenerateOriginalList() {
		// TODO Auto-generated method stub
		list = new ArrayList<Integer>();
		for (int i=1; i<51; i++){
			list.add(i%10+1);
		}
	}

	@Override
	public void play() {

		System.out.println(player);
		Collections.shuffle(list);

		Iterator<Integer> itr = list.iterator();
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

		points = 0;
		//System.out.println("Size is " + linesSelected.entrySet().size());
		for (Map.Entry<Integer, String> entry : linesSelected.entrySet()) {
			//System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			int line = entry.getKey();
			String[] position = entry.getValue().split(";");
			HashMap<Integer, Integer> span = new HashMap<Integer, Integer>();
			span.put(playBoard[Integer.valueOf(position[0].substring(1,2))][Integer.valueOf(position[0].substring(3,4))], 1);

			for (int i=1; i<position.length; i++){
				//System.out.println("Current: " + playBoard[Integer.valueOf(position[i].substring(1,2))][Integer.valueOf(position[i].substring(3,4))]);
				//System.out.println("Prior: " + playBoard[Integer.valueOf(position[i-1].substring(1,2))][Integer.valueOf(position[i-1].substring(3,4))]);
				if(playBoard[Integer.valueOf(position[i].substring(1,2))][Integer.valueOf(position[i].substring(3,4))] == playBoard[Integer.valueOf(position[i-1].substring(1,2))][Integer.valueOf(position[i-1].substring(3,4))]){
					int val = span.get(playBoard[Integer.valueOf(position[i-1].substring(1,2))][Integer.valueOf(position[i-1].substring(3,4))])+1;
					span.put(playBoard[Integer.valueOf(position[i].substring(1,2))][Integer.valueOf(position[i].substring(3,4))], val);
				} else {
					span.put(playBoard[Integer.valueOf(position[i].substring(1,2))][Integer.valueOf(position[i].substring(3,4))], 1);
				}
			}
			points = points + Evaluate(span);
			System.out.println("Line No: " + line + " Points earned: " + points);
		}

		if (points == 0){
			Lost(player, BET);
		} else {
			Win(player, points);
		}

		System.out.println("Your balance at the end of this round: " + player.getBal());
	}

	private int Evaluate(HashMap<Integer, Integer> span) {
		int sum=0;

		for (Map.Entry<Integer, Integer> entry : span.entrySet()) {
			System.out.println(entry.getKey() + "-->" + entry.getValue());
			if (entry.getValue() >= 2){
				sum = sum + winPoints[10-entry.getKey()][5-entry.getValue()];
			}
		}

		return sum;
	}

	public void SelectLines(int[] lines) {
		int count=0;
		for (int i=0; i<lines.length; i++){
			if (lines[i] == 1){
				count++;
				switch (i){
				case 0:
					linesSelected.put(1, "(1,0);(1,1);(1,2);(1,3);(1,4)");
					break;
				case 1:
					linesSelected.put(2, "(0,0);(0,1);(0,2);(0,3);(0,4)");
					break;
				case 2:
					linesSelected.put(3, "(2,0);(2,1);(2,2);(2,3);(2,4)");
					break;
				case 3:
					linesSelected.put(4, "(0,0);(1,1);(2,2);(1,3);(0,4)");
					break;
				case 4:
					linesSelected.put(5, "(2,0);(1,1);(0,2);(1,3);(2,4)");
					break;
				}
			}				
		}
		BET = BET*count;
		System.out.println("BET after select lines " + BET);
	}
}

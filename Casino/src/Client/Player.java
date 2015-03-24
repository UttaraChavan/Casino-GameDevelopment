package Client;

public class Player {
	private String name;
	private int money;
	
	public Player(String n, int m){
		this.name = n;
		this.money = m;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getBal(){
		return money;
	}
	
	public void Deduct(int n){
		this.money = this.money - n;
	}
	
	public void Add(int n){
		this.money = this.money + n;
	}
	
	@Override
	public String toString(){
		return "Player: " + this.name + " Avaialable balance: " + this.money;
	}
}

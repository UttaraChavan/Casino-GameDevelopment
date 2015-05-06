package Modules;

public class GameAbstract implements Game {

	@Override
	public void play() {
		// TODO Auto-generated method stub
		//This is just for testing purpose.
		
		System.out.println("You are in abstract class");
	}

	@Override
	public void Lost(Player p, int n) {
		// TODO Auto-generated method stub
		p.Deduct(n);
	}

	@Override
	public void Win(Player p, int n) {
		// TODO Auto-generated method stub
		p.Add(n);
	}
	

}

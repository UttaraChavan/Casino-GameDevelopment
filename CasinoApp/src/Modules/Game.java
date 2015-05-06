package Modules;

public interface Game {

	//public Deck GetDeck();
	public void play();
	public void Lost(Player p, int n);
	public void Win(Player p, int n);
	
}

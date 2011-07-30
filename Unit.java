
public class Unit {
	private int healthRemaining = 1000;
	private int positionX;
	private int positionY;
	private Player player;
	
	public Unit(int health, int posX, int posY, Player owner) {
		healthRemaining = health;
		positionX = posX;
		positionY = posY;
		player = owner;
	}
	
	public void setHealth(int num) {
		healthRemaining = num;
	}
	
	public void moveTo(int x, int y) {
		positionX = x;
		positionY = y;
	}
	
	public Player getPlayer() {
		return player;
	}
}

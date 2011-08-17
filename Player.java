
public class Player {
	public static enum Side {LEFT, RIGHT, TOP, BOTTOM};
	private final int num;
	private final Side side;
	private int money = 100;
	private final int PRICE_SOLDIER = 10;
	private final int PRICE_TANK = 15;
	private final int PRICE_PLANE = 20;
	
	/**
	 * Represents a human (or possibly computer) player
	 * @param number The player's unique number (1, 2, etc)
	 * @param side The side the player is on
	 */
	public Player(int number, Side side) {
		this.num = number;
		this.side = side;
	}
	
	public int getNumber() { return num; }
	public Side getSide() { return side; }
	public int moneyRemaining() { return money; }
	
	public Soldier buySoldier() {
		if (PRICE_SOLDIER > money)
			return null;
		money = money - PRICE_SOLDIER;
		return (new Soldier(this));
	}
	
	/*
	public Tank buyTank() {
		if (PRICE_TANK > money)
			return null;
		money = money - PRICE_TANK;
		return (new Tank(this));
	} */

}

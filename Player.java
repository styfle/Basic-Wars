import java.util.ArrayList;


/**
 * This class represents either a human or computer player
 */
public class Player {
	public static enum Side {LEFT, RIGHT, TOP, BOTTOM};
	public static final int PRICE_SOLDIER = 10;
	public static final int PRICE_TANK = 15;
	public static final int PRICE_PLANE = 20;
	private final int num;
	private final Side side;
	private int money = 100;
	private ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
	private ArrayList<Tank> tanks = new ArrayList<Tank>();
	private ArrayList<Plane> planes = new ArrayList<Plane>();
	
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
	
	public boolean buySoldier() {
		if (PRICE_SOLDIER > money)
			return false;
		money = money - PRICE_SOLDIER;
		return soldiers.add(new Soldier(this));
	}
	
	public boolean buyTank() {
		if (PRICE_TANK > money)
			return false;
		money = money - PRICE_TANK;
		return tanks.add(new Tank(this));
	}
	
	public boolean buyPlane() {
		if (PRICE_PLANE > money)
			return false;
		money = money - PRICE_PLANE;
		return planes.add(new Plane(this));
	}
	
	public int sizeOfSoldiers() { return soldiers.size(); }
	public int sizeOfTanks() { return tanks.size(); }
	public int sizeOfPlanes() { return planes.size(); }

}

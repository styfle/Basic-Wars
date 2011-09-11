import java.util.ArrayList;


/**
 * This class represents either a human or computer player
 */
public class Player {
	//public static enum Side {LEFT, RIGHT, TOP, BOTTOM};
	public static final int PRICE_SOLDIER = 10;
	public static final int PRICE_TANK = 15;
	public static final int PRICE_PLANE = 20;
	private final int num;
	private int money = 100;
	private ArrayList<Unit> units = new ArrayList<Unit>(10);
	private int soldierCount = 0;
	private int tankCount = 0;
	private int planeCount = 0;
	private int currentIndex = 0;
	
	/**
	 * Represents a human (or possibly computer) player
	 * @param number The player's unique number (1, 2, etc)
	 */
	public Player(int number) {
		this.num = number;
	}
	
	public int getNumber() { return num; }
	public int moneyRemaining() { return money; }
	public int getUnitCount() { return units.size(); }
	
	public boolean buySoldier() {
		if (PRICE_SOLDIER > money)
			return false;
		money = money - PRICE_SOLDIER;
		soldierCount++;
		return units.add(new Unit(Unit.Type.SOLDIER, this));
	}
	
	public boolean buyTank() {
		if (PRICE_TANK > money)
			return false;
		money = money - PRICE_TANK;
		tankCount++;
		return units.add(new Unit(Unit.Type.TANK, this));
	}
	
	public boolean buyPlane() {
		if (PRICE_PLANE > money)
			return false;
		money = money - PRICE_PLANE;
		planeCount++;
		return units.add(new Unit(Unit.Type.PLANE, this));
	}
	
	public int sizeOfSoldiers() { return soldierCount; }
	public int sizeOfTanks() { return tankCount; }
	public int sizeOfPlanes() { return planeCount; }
	
	public Unit getNextUnit() {
		Unit u = null;
		if (currentIndex < units.size())
			u = units.get(currentIndex);
		currentIndex++;
		return u;
	}
	
	public void removeUnit(Unit u) {
		units.remove(u);
	}

}

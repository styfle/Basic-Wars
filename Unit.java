import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * This class is a unit (or game piece) that the
 * player moves around on the {@link GameMap} (or board).
 */
public abstract class Unit {
	private final int MAX_HEALTH;
	private int healthRemaining;
	private Player player;
	private BufferedImage image;
	
	public Unit(Player owner, int maxHealth, String unit) {
		if (owner == null) {
			throw new IllegalArgumentException("Units must have an owner!");
		}
		
		//String imagePath = "images/"+unit+owner.getNumber()+"_"+owner.getSide().toString()+".png";
		String imagePath = "images/"+unit+owner.getNumber()+".png";
		player = owner;
		MAX_HEALTH = maxHealth;
		healthRemaining = maxHealth;
		
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not find image: " + imagePath);
		}
	}
	
	public void setHealth(int num) {
		healthRemaining = num;
	}
	
	public int getHealth() {
		return healthRemaining;
	}
	
	public int getMaxHealth() {
		return MAX_HEALTH;
	}
	
	public double getHealthPercent() {
		return healthRemaining / (double)MAX_HEALTH * 100;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player owner) {
		player = owner;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public boolean isDead() {
		return healthRemaining <= 0;
	}
	
	public void attackedBy(Unit u) {
		if (u instanceof Soldier) {
			attackedBySoldier();
		} else if (u instanceof Tank) {
			attackedByTank();
		} else if (u instanceof Plane) {
			attackedByPlane();
		} else {
			throw new IllegalArgumentException("Attacking unit is not recognized: " + u);
		}
		
		if (isDead()) {
			System.out.println("Unit was killed");
			player.removeUnit(this);
		}
	}
	
	private void attackedBySoldier() { //these eventually could be overriden
		healthRemaining = healthRemaining - 50;
	}
	
	private void attackedByTank() {
		healthRemaining = healthRemaining - 100;
	}
	
	private void attackedByPlane() {
		healthRemaining = healthRemaining - 75;
	}
	
	
}

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/**
 * This class is a unit (or game piece) that the
 * player moves around on the {@link GameMap} (or board).
 */
public class Unit {
	private String name;
	private final int MAX_HEALTH;
	private int healthRemaining;
	private final int MAX_MOVES;	// move radius
	private final int MAX_ATTACK;	// attack radius
	private Player player;
	private BufferedImage image;
	private ImageIcon icon;
	public static enum Type {SOLDIER, TANK, PLANE};
	private Type type;
	
	
	public Unit(Type type, Player owner) {
		if (owner == null) {
			throw new IllegalArgumentException("Units must have an owner!");
		}
		
		switch (type) {
			case SOLDIER:
				name = "soldier";
				MAX_HEALTH = 100;
				MAX_MOVES = 2;
				break;
			case TANK:
				name = "tank";
				MAX_HEALTH = 200;
				MAX_MOVES = 1;
				break;
			case PLANE:
				name = "plane";
				MAX_HEALTH = 150;
				MAX_MOVES = 3;
				break;
			default:
				throw new IllegalArgumentException("Cannot create a unit of type: " + type);
		}
		
		String imagePath = "images/"+name+owner.getNumber()+".png";
		this.type = type;
		this.player = owner;
		this.healthRemaining = MAX_HEALTH;
		this.MAX_ATTACK = 2;
		
		try {
			image = ImageIO.read(new File(imagePath));
			icon = new ImageIcon(image.getScaledInstance(15, 15, BufferedImage.SCALE_SMOOTH));
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not find image: " + imagePath);
		}
	}
	
	public void setHealth(int h) { healthRemaining = h; }
	
	public int getHealth() { return healthRemaining; }
	
	public int getMaxHealth() { return MAX_HEALTH; }
	
	public Type getType() { return type; }
	
	public double getHealthPercent() {
		return healthRemaining / (double)MAX_HEALTH * 100;
	}
	
	public Player getPlayer() { return player; }
	
	public BufferedImage getImage() { return image; }
	
	public ImageIcon getIcon() { return icon; }
	
	public int getMaxMoves() { return MAX_MOVES; }
	
	public int getMaxAttackDist() { return MAX_ATTACK; }
	
	public boolean isDead() { return healthRemaining <= 0; }
	
	public void attackedBy(Unit u) {
		switch(u.getType()) {
			case SOLDIER: attackedBySoldier(); break;
			case TANK: attackedByTank(); break;
			case PLANE: attackedByPlane(); break;
			default:
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

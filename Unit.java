import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Unit {
	private final int MAX_HEALTH;
	private int healthRemaining;
	private Player player;
	private BufferedImage image;
	
	public Unit(Player owner, int maxHealth, String imagePath) {
		if (owner == null) {
			throw new IllegalArgumentException("Units must have an owner!");
		}
		
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
	
	public void setImage(BufferedImage i) {
		image = i;
	}
}

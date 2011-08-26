import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class Drawable {
	private int x;
	private int y;
	private String text;
	private static final Color color = Color.GRAY;
	private static final Font font = new Font("Arial", Font.BOLD, 24);
	
	public Drawable(String s, int xPos, int yPos) {
		if (s == null)
			throw new IllegalArgumentException("Drawable's text cannot be null!");
		text = s;
		x = xPos;
		y = yPos;
	}
	
	public void setText(String s) { text = s;}
	public String getText() { return text; }
	
	/**
	 * Draws the object using parameters from init()
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, x, y);
	}
	
}
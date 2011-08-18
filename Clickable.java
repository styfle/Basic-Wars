import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;


/**
 * Clickable string to draw on the screen
 */
public abstract class Clickable {
	protected String name;
	protected BufferedImage image;
	private int x;
	private int y;
	private Graphics2D g;
	private TextLayout layout;
	protected Color COLOR_NORMAL = new Color(0,150,0);
	protected Color COLOR_HOVER = BasicWars.bleach(COLOR_NORMAL, 0.75f);
	private Color color = COLOR_NORMAL;
	
	/**
	 * Set the printable name of this clickable
	 * @param n Text name
	 */
	protected void setName(String n) {
		name = n;
	}
	
	/**
	 * @return Printable string for this clickable object
	 */
	public String getName() { return name; }
	
	/**
	 * @return Color to paint this object
	 */
	public Color getColor() { return color; }
	
	
	/**
	 * Set the image for this clickable
	 * @param i Image for clickable
	 */
	protected void setImage(BufferedImage i) {
		image = i;
	}
	
	/**
	 * @return Image of this clickable object or null if no image is available
	 */
	public BufferedImage getImage() { return image; }
	
	/**
	 * Constructor like method that initializes the clickable object
	 * @param tl - TextLayout with info about painting
	 * @param x - The x coordinate
	 * @param y - The y coordinate
	 * @param g - The graphics to paint with
	 */
	public void initClickable(TextLayout tl, int x, int y, Graphics2D g) {
		this.layout = tl;
		this.x = x;
		this.y = y;
		this.g = g;
	}
	
	/**
	 * Draws the clickable object using parameters from initClickable()
	 */
	public void draw() {
		layout.draw(g, x, y);
	}
	
	/**
	 * @param p Point of click
	 */
	public void mouseMoved(Point p) {
		if (contains(p)) {
			color = COLOR_HOVER;
		} else {
			color = COLOR_NORMAL;
		}
	}
	
	/**
	 * @param point of click
	 * @return True if point is in the bounds of the object
	 */
	public boolean contains(Point p) {
		if (layout == null)
			return false;
		return layout.getPixelBounds(g.getFontRenderContext(), x, y).contains(p);
	}
	
	/**
	 * Action to perform onClick
	 * @param o Reference to the top most parent panel
	 */
	public abstract void onClick(BasicWars o);
	
}

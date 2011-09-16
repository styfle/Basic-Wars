import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;


/**
 * Clickable string to draw on the screen
 */
public abstract class Clickable {
	private String name;
	private BufferedImage image;
	private int x;
	private int y;
	private Graphics2D g;
	private TextLayout layout;
	private final Color COLOR_NORMAL;
	private final Color COLOR_HOVER;
	private Color color;
	
	/**
	 * @param name Visible text for this clickable
	 * @param enabled False if not clickable
	 * @param image Optional image to show when hovering
	 * @param c Optional color for clickable
	 */
	public Clickable(String name, boolean enabled, BufferedImage image, Color c) {
		if (name == null)
			throw new IllegalArgumentException("Clickable's name cannot be null!");
		
		this.name = name;
		if (!enabled) {
			this.image = new BufferedImage(Menu.IMAGE_WIDTH, Menu.IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = this.image.getGraphics();
			Color grayedOut = new Color(45,60,45);
			g.setColor(grayedOut);
			g.setFont(new Font("Courier", Font.BOLD, 30));
			g.drawString("Disabled", 65, 120);
			COLOR_NORMAL = grayedOut;
			COLOR_HOVER = grayedOut;
			color = COLOR_NORMAL;
		} else {
			if (image == null)
				image = new BufferedImage(Menu.IMAGE_WIDTH, Menu.IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
			if (c == null)
				c = new Color(0,150,0);
			
			this.image = image;
			COLOR_NORMAL = c;
			COLOR_HOVER = BasicWars.bleach(COLOR_NORMAL, 0.60f);
			color = COLOR_NORMAL;
		}
			
		
	}
	
	/**
	 * Uses default colors with no image
	 * @param name Visible text for this clickable object
	 */
	public Clickable(String name) {
		if (name == null)
			throw new IllegalArgumentException("Clickable's name cannot be null!");
		this.name = name;
		COLOR_NORMAL = new Color(0,150,0);
		COLOR_HOVER = BasicWars.bleach(COLOR_NORMAL, 0.60f);
		color = COLOR_NORMAL;
	}
	
	public void setEnabled(boolean enable) {
		if (enable) {
			color = new Color(0,150,0);
		} else {
			color = new Color(45,60,45);
		}
	}
	
	/**
	 * Set the printable name of this clickable
	 * @param n Text name
	 */
	protected void setName(String n) { name = n; }
	
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
	 * @param i BufferedImage for clickable
	 */
	protected void setImage(BufferedImage i) { image = i; }
	
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
	public void init(TextLayout tl, int x, int y, Graphics2D g) {
		this.layout = tl;
		this.x = x;
		this.y = y;
		this.g = g;
	}
	
	/**
	 * Draws the clickable object using parameters from init()
	 */
	public void draw() {
		layout.draw(g, x, y);
	}
	
	/**
	 * Call when hovering over element
	 */
	public void startHover() { color = COLOR_HOVER; }
	
	/**
	 * Call when hovering stops
	 */
	public void stopHover() { color = COLOR_NORMAL; }
	
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

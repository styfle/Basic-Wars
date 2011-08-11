import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;

public class Cell {
	private Polygon hexagon = new Polygon();
	private int x;
	private int y;
	public Unit unit;
	public Cell north;
	public Cell south;
	public Cell northEast;
	public Cell southEast;
	public Cell northWest;
	public Cell southWest;	
	private static final Color COLOR_BORDER = Color.LIGHT_GRAY;
	private final Color COLOR_FILL;
	private final Color COLOR_FILL_HOVER;
	private Color color; //current color fill
	public static enum Type {EARTH, WATER, LAVA, TREE};
	private static final double ANGLE = (2*Math.PI)/6;
	public static final int DIST_TO_CORNER = 15;
	public static final int DIST_TO_EDGE = (int)(DIST_TO_CORNER * Math.cos(ANGLE/2));
	
	/**
	 * Hexagon shaped cell on the board
	 * @param xPos The x position
	 * @param yPos The y position
	 * @param c The color of the cell
	 */
	public Cell(int xPos, int yPos, Type cellType) {
		x = xPos;
		y = yPos;
		switch (cellType) {
			case EARTH: color = new Color(139, 69, 19, 175); break;
			case WATER: color = new Color(0, 0, 255, 175); break;
			case LAVA: color = new Color(255, 0, 0, 175); break;
			case TREE: color = new Color(0, 255, 0, 175); break;
			default:
				System.err.println("Unknown cell type!");
		}
		COLOR_FILL = color;
		COLOR_FILL_HOVER = bleach(COLOR_FILL, 0.25f);
		
		for (int i = 0; i < 6; i++) {
			int x2 = (int)(x + DIST_TO_CORNER * Math.cos(i * ANGLE));
			int y2 = (int)(y + DIST_TO_CORNER * Math.sin(i * ANGLE));
			hexagon.addPoint(x2, y2);
		}
	}
	
	/**
	 * Place a unit to this cell. Only one unit per cell.
	 * @param u Unit that resides in this cell
	 */
	public void setUnit(Unit u) {
		unit = u;
	}

	public void paintCell(Graphics g) {
		g.setColor(color);
		g.fillPolygon(hexagon);
		//g.drawOval(x, y, 1, 1); //center point
		g.setColor(COLOR_BORDER);
		g.drawPolygon(hexagon);
		if (unit != null) { // Ideal image is 33x28 so resize doesn't look terrible
			Image image = unit.getImage();
			Graphics2D g2 = (Graphics2D)g;
	        int newW = (int)(DIST_TO_CORNER * 2);
	        int newH = (int)(DIST_TO_EDGE * 2);
	        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2.drawImage(image, x-DIST_TO_CORNER, y-DIST_TO_EDGE, newW, newH, null);
			//Image image = unit.getImage();//.getScaledInstance(2*DIST_TO_CORNER, 2*DIST_TO_EDGE, Image.SCALE_SMOOTH);
			//g.drawImage(image,x-DIST_TO_CORNER+2,y-DIST_TO_EDGE+1,null);
		}
	}
	
	public boolean contains(int x, int y) {
		return hexagon.contains(x, y);
	}
	
	public void mouseEntered() {
		color = COLOR_FILL_HOVER;
	}
	
	public void mouseExited() {
		color = COLOR_FILL;
	}
	
	public Cell generateSouth() {
		Cell c = null;
		if (south == null) {
			c = new Cell(x, y + 2*DIST_TO_EDGE, Type.EARTH);
			south = c;
			c.north = this;
		}
		return c;
	}
	
	public Cell generateSouthEast() {
		Cell c = null;
		if (southEast == null) {
			c = new Cell(x + (int)(1.5*DIST_TO_CORNER), y + DIST_TO_EDGE, Type.EARTH);
			southEast = c;
			c.northWest = this;
		}
		return c;
	}
	
	public Cell generateNorthEast() {
		Cell c = null;
		if (northEast == null) {
			c = new Cell(x + (int)(1.5*DIST_TO_CORNER), y - DIST_TO_EDGE, Type.EARTH);
			northEast = c;
			c.southWest = this;
		}
		return c;
	}
	
	public Cell generateAdjacentCell() {
		Cell c = null;
		if (south == null) {
			System.out.println("South");
			c = new Cell(x, y + 2*DIST_TO_EDGE, Type.EARTH);
			south = c;
			c.north = this;			
		} else if (southEast == null) {
			c = new Cell(x + (int)(1.5*DIST_TO_CORNER), y + DIST_TO_EDGE, Type.EARTH);
			southEast = c;
			c.northWest = this;
		} else if (northEast == null) {
			c = new Cell(x + (int)(1.5*DIST_TO_CORNER), y - DIST_TO_EDGE, Type.EARTH);
			northEast = c;
			c.southWest = this;
		} else if (southWest == null) {
			c = new Cell(x - (int)(1.5*DIST_TO_CORNER), y + DIST_TO_EDGE, Type.EARTH);
			southWest = c;
			c.northEast = this;
		} else if (northWest == null) {
			c = new Cell(x - (int)(1.5*DIST_TO_CORNER), y - DIST_TO_EDGE, Type.EARTH);
			northWest = c;
			c.southEast = this;
		} else if (north == null) {
			c = new Cell(x, y - 2*DIST_TO_EDGE, Type.EARTH);
			north = c;
			c.south = this;
		}
		return c;
	}
	
	/**
	  * Lightens a color by a given amount
	  * 
	  * @param color The color to lighten
	  * @param amount The amount to lighten the color. 0 is unchanged; 1 is completely white
	  * @return The bleached color
	  */
	  public Color bleach(Color color, float amount)
	  {
	    int red = (int) ((color.getRed() * (1 - amount) / 255 + amount) * 255);
	    int green = (int) ((color.getGreen() * (1 - amount) / 255 + amount) * 255);
	    int blue = (int) ((color.getBlue() * (1 - amount) / 255 + amount) * 255);
	    return new Color(red, green, blue);
	  }

	
}

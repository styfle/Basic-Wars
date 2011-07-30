import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JComponent;

public class Cell extends JComponent {
	private static final long serialVersionUID = 610376406049573992L;
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
	private final Color COLOR_BORDER = Color.LIGHT_GRAY;
	private final Color COLOR_FILL;
	private final Color COLOR_FILL_HOVER;
	private Color color; //current color fill
	private final double ANGLE = 2*Math.PI/6;
	private final int DIST_TO_CORNER = 50;
	private final int DIST_TO_EDGE = (int)(DIST_TO_CORNER * Math.cos(ANGLE/2));
	
	/**
	 * Hexagon shaped cell on the board
	 * @param xPos The x position
	 * @param yPos The y position
	 * @param c The color of the cell
	 */
	public Cell(int xPos, int yPos, Color c) {
		x = xPos;
		y = yPos;
		color = COLOR_FILL = c;
		COLOR_FILL_HOVER = bleach(COLOR_FILL, 0.25f);
		
		for (int i = 0; i < 6; i++) {
			int x2 = (int)(x + DIST_TO_CORNER * Math.cos(i * ANGLE));
			int y2 = (int)(y + DIST_TO_CORNER * Math.sin(i * ANGLE));
			hexagon.addPoint(x2, y2);
			//g.drawLine(x, y, x2, y2);
		}
	}
	
	public int getDistToCorner() { return DIST_TO_CORNER; }
	public int getDistToEdge() {return DIST_TO_EDGE; }
	
	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillPolygon(hexagon);
		//g.drawOval(x, y, 1, 1);
		g.setColor(COLOR_BORDER);
		g.drawPolygon(hexagon);
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
	
	//public boolean isSurrounded() {
		//return north != null && south != null && northEast != null
			//	&& southEast != null && northWest != null && southWest != null;
	//}
	
	public Cell generateAdjacentCell() { //TODO determine color
		Cell c = null;
		if (south == null) {
			System.out.println("South");
			c = new Cell(x, y + 2*DIST_TO_EDGE, COLOR_FILL);
			south = c;
			c.north = this;			
		} else if (southEast == null) {
			c = new Cell(x + (int)(1.5*DIST_TO_CORNER), y + DIST_TO_EDGE, COLOR_FILL);
			southEast = c;
			c.northWest = this;
		} else if (northEast == null) {
			c = new Cell(x + (int)(1.5*DIST_TO_CORNER), y - DIST_TO_EDGE, COLOR_FILL);
			northEast = c;
			c.southWest = this;
		} else if (southWest == null) {
			c = new Cell(x - (int)(1.5*DIST_TO_CORNER), y + DIST_TO_EDGE, COLOR_FILL);
			southWest = c;
			c.northEast = this;
		} else if (northWest == null) {
			c = new Cell(x - (int)(1.5*DIST_TO_CORNER), y - DIST_TO_EDGE, COLOR_FILL);
			northWest = c;
			c.southEast = this;
		} else if (north == null) {
			c = new Cell(x, y - 2*DIST_TO_EDGE, COLOR_FILL);
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

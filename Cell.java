import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;

public class Cell {
	private Polygon hexagon = new Polygon();
	private int x;
	private int y;
	public Cell north;
	public Cell south;
	public Cell northEast;
	public Cell southEast;
	public Cell northWest;
	public Cell southWest;
	public static enum Type {EARTH, WATER, LAVA, TREE};
	private Type type;
	private Unit unit;
	private static final Color COLOR_BORDER = Color.LIGHT_GRAY;
	private final Color COLOR_FILL;
	private final Color COLOR_FILL_HOVER;
	private static final Color COLOR_FILL_SELECTED = new Color(250, 255 , 200);
	private final Color COLOR_VALID_MOVE;
	private Color currentFill; //current color fill
	private Color currentBorder = COLOR_BORDER;
	private static final double ANGLE = (2*Math.PI)/6;
	public static final int DIST_TO_CORNER = 17; // DETERMINES SIZE OF EVERYTHING
	public static final int DIST_TO_EDGE = (int)(DIST_TO_CORNER * Math.cos(ANGLE/2));
	private boolean firstPaint = true;
	
	/**
	 * Hexagon shaped cell on the board
	 * @param xPos The x position
	 * @param yPos The y position
	 * @param c The color of the cell
	 */
	public Cell(int xPos, int yPos, Type cellType) {
		x = xPos;
		y = yPos;
		type = cellType;
		switch (type) {
			case EARTH:currentFill = new Color(139, 69, 19, 175); break;
			case WATER: currentFill = new Color(0, 0, 255, 175); break;
			case LAVA: currentFill = new Color(255, 0, 0, 175); break;
			case TREE: currentFill = new Color(0, 255, 0, 175); break;
			default:
				System.err.println("Unknown cell type!");
		}
		COLOR_FILL = currentFill;
		COLOR_FILL_HOVER = BasicWars.bleach(COLOR_FILL, 0.25f);
		COLOR_VALID_MOVE = BasicWars.bleach(COLOR_FILL, 0.50f);
		
		for (int i = 0; i < 6; i++) {
			int x2 = (int)(x + DIST_TO_CORNER * Math.cos(i * ANGLE));
			int y2 = (int)(y + DIST_TO_CORNER * Math.sin(i * ANGLE));
			hexagon.addPoint(x2, y2);
		}		
	}
	
	public void paintCell(Graphics g) {
		g.setColor(currentFill);
		g.fillPolygon(hexagon);
		//g.drawOval(x, y, 1, 1); //center point
		g.setColor(currentBorder);
		g.drawPolygon(hexagon);
		if (unit != null) { // Ideal image is 33x28 so resize doesn't look terrible
			Image image = unit.getImage();
			Graphics2D g2 = (Graphics2D)g;
	        int newW = (int)(DIST_TO_CORNER * 2);
	        int newH = (int)(DIST_TO_EDGE * 2);
	        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2.drawImage(image, x-DIST_TO_CORNER, y-DIST_TO_EDGE, newW, newH, null);
			//image = image.getScaledInstance(2*DIST_TO_CORNER, 2*DIST_TO_EDGE, Image.SCALE_SMOOTH);
			//g.drawImage(image,x-DIST_TO_CORNER+2,y-DIST_TO_EDGE+1,null);
		}
	}
	
	/**
	 * Place a unit to this cell. Only one unit per cell.
	 * @param u Unit that resides in this cell
	 */
	public void setUnit(Unit u) { unit = u; }
	public Unit getUnit() { return unit; }
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public Type getType() { return type; }
	
	public boolean contains(Point p) { return hexagon.contains(p); }
	
	public static Type parseType(char ch) {
		Cell.Type type;
		switch (ch) {
			case 'E': type = Cell.Type.EARTH; break;
			case 'W': type = Cell.Type.WATER; break;
			case 'L': type = Cell.Type.LAVA; break;
			case 'T': type = Cell.Type.TREE; break;
			default: type = Cell.Type.LAVA;
		}
		return type;
	}
	
	public Cell setSelected(BasicWars o) {
		if (o != null) {
			currentFill = COLOR_FILL_SELECTED;
			o.showSelected(unit);
		} else {
			currentFill = COLOR_FILL;
		}
		return this;
	}
	
	public void setValidMove(boolean isValid) {
		currentFill = (isValid) ? COLOR_VALID_MOVE : COLOR_FILL;
	}
	
	public void setValidAttack(boolean isValid) {
		currentFill = (isValid) ? COLOR_VALID_MOVE : COLOR_FILL;
	}
	
	public void mouseEntered() {
		currentFill = COLOR_FILL_HOVER;
	}
	
	public void mouseExited() {
		currentFill = COLOR_FILL;
	}
	
	public Cell[] getAdjacentCells() {
		Cell[] adj = new Cell[6];
		adj[0] = north;
		adj[1] = northEast;
		adj[2] = southEast;
		adj[3] = south;
		adj[4] = southWest;
		adj[5] = northWest;
		return adj;
	}
	
	public Cell generateSouth(Type type) {
		Cell c = new Cell(x, y + 2*DIST_TO_EDGE, type);
		south = c;
		c.north = this;
		if (southWest != null) {
			southWest.southEast = south;
			south.northWest = southWest;
		}
		if (southEast != null) {
			southEast.southWest = south;
			south.northEast = southEast;
		}
		return c;
	}
	
	public Cell generateSouthEast(Type type) {
		Cell c = new Cell(x + (int)(1.5*DIST_TO_CORNER), y + DIST_TO_EDGE, type);
		southEast = c;
		c.northWest = this;
		if (south != null) {
			south.northEast = southEast;
			southEast.southWest = south;
		}
		if (northEast != null) {
			northEast.south = southEast;
			southEast.north = northEast;
		}
		return c;
	}
	
	public Cell generateNorthEast(Type type) {
		Cell c = new Cell(x + (int)(1.5*DIST_TO_CORNER), y - DIST_TO_EDGE, type);
		northEast = c;
		c.southWest = this;
		if (north != null) {
			north.southEast = c;
			c.northWest = north;
			
			Cell relNorth = north.northEast;
			if (relNorth != null) {
				relNorth.south = c;
				c.north = relNorth;
				
				Cell relNorthEast = relNorth.southEast;
				if (relNorthEast != null) {
					relNorthEast.southWest = c;
					northEast.northEast = relNorthEast;
				}
			}
			
			
		}
		if (southEast != null) {
			southEast.north = northEast;
			northEast.south = southEast;
		}
		return c;
	}
	
	public boolean firstPaint() {
		if (firstPaint) {
			firstPaint = false;
			return true;
		}
		return false;
	}

}

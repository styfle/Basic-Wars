import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class represents the 'playable map' by reading
 * a custom map from the user or loading a predefined map.
 */
public class GameMap extends Clickable {
	private ArrayList<Cell> cells = new ArrayList<Cell>(500);
	public final static int CELL_COLS = 25; // width
	public final static int CELL_ROWS = 20; // height
	
	public GameMap(String name, String mapData) {
		super(name);
		buildMap(mapData);
	}
	
	public GameMap(File file) throws IOException {
		super(file.getName());
		BufferedReader r = new BufferedReader(new FileReader(file));		
		StringBuilder mapData = new StringBuilder(500);
		String line;
		while ((line = r.readLine()) != null) {
			mapData.append(line).append("\n");
		}
		String name = file.getName();
		name = name.substring(0, name.lastIndexOf('.'));
		buildMap(mapData.toString());
	}
	
	private void buildMap(String mapData) {
		if (mapData == null)
			return;

		Cell c = new Cell(Cell.DIST_TO_CORNER, 0, Cell.Type.SWAMP);
		String[] lines = mapData.split("\n");
		
		if (lines.length != CELL_ROWS)
			throw new IllegalArgumentException("Maps must have " + CELL_ROWS + " rows!\n Your map has " + lines.length + " rows.");
		
		Cell left = c;
		for (int i=0; i< lines.length; i++) {
			if (lines[i].length() != CELL_COLS)
				throw new IllegalArgumentException("Maps must have " + CELL_COLS + " columns!\n" +
						"Your map has " + lines[i].length() + " columns on line " + (i+1) + ".");
			try {
				buildRow(lines[i], left);
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage() + " on line " + (i+1) + ".");
			}
			left = left.south;
		} //((p.getNumber() == 1) ? (i % 25 < 11) : (i % 25 > 12));
		
		// check for 10 cells on each side
		int leftEarthCount = 0;
		int rightEarthCount = 0;
		for (int i=0; i<cells.size(); i++) {
			Cell cell = cells.get(i);
			if (cell.getType().equals(Cell.Type.EARTH)) {
				if (BasicWars.isOnLeftSide(i))
					leftEarthCount++;
				if (BasicWars.isOnRightSide(i))
					rightEarthCount++;
			}
		}
		
		if (leftEarthCount < 10 || rightEarthCount < 10)
			throw new IllegalArgumentException("Maps must have at least 10 earth cells on the left and right.\n" +
					this.getName() + " has " + leftEarthCount +" left earth cells and " + rightEarthCount + " right earth cells.");
		
		// generate image of cells
		BufferedImage image = new BufferedImage(GameMapView.WIDTH, GameMapView.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		setImage(image);
		Graphics2D g = image.createGraphics();
		for (Cell cell : cells) {
			cell.paintCell(g);
		}
	}
	
	private void buildRow(String line, Cell next) {
		char ch = line.charAt(0);
		Cell.Type type = Cell.parseType(ch);
		Cell c = next.generateSouth(type);
		cells.add(c);
		for (int i=1; i<line.length(); i++) {
			type = Cell.parseType(line.charAt(i));
			if (i % 2 == 0) {
				next = c.generateNorthEast(type);
			} else {
				next = c.generateSouthEast(type);
			}
			c = next;
			cells.add(c);
		}
	}
	
	public ArrayList<Cell> getCells() {
		return cells;
	}
	
	@Override
	public void onClick(BasicWars o) {
		for (Cell c : cells) {
			c.setUnit(null); // remove any units on map
			c.setSelected(null); // remove any selected cells
		}
		o.setMap(this);
		o.loadUnitMenu();
	}
	
}

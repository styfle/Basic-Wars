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
		buildMap(name, mapData);
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
		buildMap(name, mapData.toString());
	}
	
	private void buildMap(String name, String mapData) {
		//setName(name);
		if (mapData == null)
			return;
		Cell c = new Cell(Cell.DIST_TO_CORNER, 0, Cell.Type.TREE);
		String[] lines = mapData.split("\n");
		if (lines.length != CELL_ROWS)
			throw new IllegalArgumentException("Maps must have " + CELL_ROWS + " rows!\n Your map has " + lines.length + " rows.");
		
		Cell left = c;
		for (String line : lines) {
			if (line.length() != CELL_COLS)
				throw new IllegalArgumentException("Maps must have " + CELL_COLS + " columns!\nYour map has " + line.length() + " columns.");
			buildRow(line, left);
			left = left.south;
		}
		
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
		o.loadMap(this);
	}
	
}

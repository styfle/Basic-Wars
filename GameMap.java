import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class reads a custom map from the user
 * or loads a predefined map when it is selected.
 *
 */
public class GameMap {
	private String name;
	private ArrayList<Cell> cells = new ArrayList<Cell>(500);
	public final static int CELL_COLS = 25; // width
	public final static int CELL_ROWS = 20; // height
	private TextLayout layout;
	private int x;
	private int y;
	private Graphics2D g;
	private final Color COLOR_NORMAL = new Color(0,150,0);
	private final Color COLOR_HOVER = BasicWars.bleach(COLOR_NORMAL, 0.75f);
	private Color color = COLOR_NORMAL; //current color;
	private BufferedImage image;
	
	
	public GameMap(String name, String mapData) {
		buildMap(name, mapData);
	}
	
	public GameMap(String pathToMap) throws IOException {		
		BufferedReader r = new BufferedReader(new FileReader(pathToMap));		
		StringBuilder mapData = new StringBuilder(500);
		String line;
		while ((line = r.readLine()) != null) {
			mapData.append(line).append("\n");
		}
		buildMap("Custom Map", mapData.toString());
	}
	
	private void buildMap(String name, String mapData) {
		this.name = name;
		if (mapData == null)
			return;
		Cell c = new Cell(Cell.DIST_TO_CORNER, 0, Cell.Type.TREE);
		String[] lines = mapData.split("\n");
		if (lines.length != CELL_ROWS)
			throw new IllegalArgumentException("Maps must hat " + CELL_ROWS + " rows!");
		
		Cell left = c;
		for (String line : lines) {
			if (line.length() != CELL_COLS)
				throw new IllegalArgumentException("Maps must have " + CELL_COLS + " columns!");
			buildRow(line, left);
			left = left.south;
		}
		
		// generate image of cells
		image = new BufferedImage(GameMapView.WIDTH, GameMapView.HEIGHT, BufferedImage.TYPE_INT_ARGB);
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
	
	public String getName() { return name; }
	
	public Color getColor() { return color; }
	
	public BufferedImage getImage() { return image; }
	
	public void initTitle(TextLayout tl, int x, int y, Graphics2D g) {
		this.layout = tl;
		this.x = x;
		this.y = y;
		this.g = g;		
		layout.draw(g, x, y);
	}
	
	public void updateTitle(Point p) {
		if (nameClicked(p)) {
			color = COLOR_HOVER;
		} else {
			color = COLOR_NORMAL;
		}
	}
	
	public boolean nameClicked(Point p) {
		if (layout == null)
			return false;
		return layout.getPixelBounds(g.getFontRenderContext(), x, y).contains(p);
	}
	
}

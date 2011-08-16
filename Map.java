import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.util.ArrayList;


/**
 * This class reads a custom map from the user
 * or loads a predefined map when it is selected.
 *
 */
public class Map {
	private String name;
	private ArrayList<Cell> cells = new ArrayList<Cell>(500);
	public final static int CELL_COLS = 25; // width
	public final static int CELL_ROWS = 20; // height
	private TextLayout layout;
	private int x;
	private int y;
	private Graphics2D g;
	private final Color COLOR_NORMAL = new Color(0,150,0);
	private final Color COLOR_HOVER = Main.bleach(COLOR_NORMAL, 0.75f);
	private Color color = COLOR_NORMAL; //current color;
	
	
	public Map(String name, String mapData) {
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
		/*
		try {
			BufferedReader r = new BufferedReader(new FileReader(mapSource));
			String line;
			while ((line = r.readLine()) != null) {
				System.out.print(line);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot read file: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Cannot read file: " + e.getMessage());
		}*/
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
		return layout.getPixelBounds(g.getFontRenderContext(), x, y).contains(p);
	}
	
}

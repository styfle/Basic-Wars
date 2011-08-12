import java.util.ArrayList;


/**
 * This class reads a custom map from the user
 * or loads a predefined map when it is selected.
 *
 */
public class Map {
	private ArrayList<Cell> cells = new ArrayList<Cell>(500);
	public final static int CELL_COLS = 25; // width
	public final static int CELL_ROWS = 20; // height
	private final static String map0 = "EEEEEEEEEEEEEEEEEEEEEEEEE\n" +
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n";
	
	private final static String map1 = "EEEEEEEEEEEEEEEEEEEEEEEEE\n" +
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEETEEEEEEEEEEEEE\n" + 
								"EEEEEEEEETTWWWEEEEEEEEEEE\n" + 
								"EEEEEEEEEWWWWWWEEEEEEEEEE\n" + 
								"EEEEEEEEEWWWWWWEEEEEEEEEE\n" + 
								"EEEEEEEEEWWWWWWEEEEEEEEEE\n" + 
								"EEEEEEEEEEWWWWWEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEWTTEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEEEEEEEEEEEEEEE\n";
	
	public Map(String mapData) {
		Cell c = new Cell(Cell.DIST_TO_CORNER, 0, Cell.Type.TREE);
		String[] lines = mapData.split("\n");
		Cell left = c;
		for (String line : lines) {
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
	
	public Map() {
		this(map1);
	}
	
	public ArrayList<Cell> getCells() {
		return cells;
	}
	
	
}

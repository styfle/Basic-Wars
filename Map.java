import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class reads a custom map from the user
 * or loads a predefined map when it is selected.
 *
 */
public class Map {
	private ArrayList<Cell> cells = new ArrayList<Cell>(500);
	private final static int CELL_COLS = 25; // width
	private final static int CELL_ROWS = 20; // height
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
								"EEEEEEEEEEEEWWEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEWWWEEEEEEEEEEE\n" + 
								"EEEEEEEEEEWWWWWEEEEEEEEEE\n" + 
								"EEEEEEEEEWWWWWWEEEEEEEEEE\n" + 
								"EEEEEEEEEEWWWWWEEEEEEEEEE\n" + 
								"EEEEEEEEEEWWWWEEEEEEEEEEE\n" + 
								"EEEEEEEEEEEWWEEEEEEEEEEEE\n" + 
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
		Cell c = new Cell(50, 50, Cell.Type.EARTH);
		String[] lines = mapData.split("\n");
		Cell north = null;
		for (String line : lines) {
			Cell prev = null;			
			for (int i=0; i<line.length(); i++) {
				char ch = line.charAt(i); //TODO switch char to get cell type
				if (i % 2 == 0)
					c.generateSouthEast();
				else
					c.generateNorthEast();
			}
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
	
	public Map() {
		this(map0);
	}
}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GameBoardView extends JPanel {
	private static final long serialVersionUID = -8221311233615840987L;
	private static final boolean DOUBLE_BUFFERED = true;
	private static final int COLS = 60; // X
	private static final int ROWS = 40;	// Y
	private static final int CELL_SIZE = 15; // Pixels
	public static final int WIDTH  = COLS * CELL_SIZE;
    public static final int HEIGHT = ROWS * CELL_SIZE;
    //private GameBoardModel model = new GameBoardModel(COLS, ROWS);
    private ArrayList<Cell> cells = new ArrayList<Cell>(50);
    private Graphics2D g;
    
    public GameBoardView() {
    	super(DOUBLE_BUFFERED);   	

		Cell c = new Cell(0, 0, Color.red); // dummy cell

		int x = c.getDistToCorner() + 0;
		int y = c.getDistToEdge() - 1;
		c = new Cell(x, y, new Color(0f, .3f, 1f, .5f));
		cells.add(c);
		
		for (int i=0; i<21; i++) {
			Cell newCell = c.generateAdjacentCell();
			
			if (newCell == null) {				
				c.south.northEast = c.southEast;
				c.south.northWest = c.southWest;
				c.southEast.southWest = c.south;
				c.southEast.north = c.northEast;
				c.southWest.southEast = c.south;
				c.southWest.north = c.northWest;
				
				c.northEast.south = c.southEast;
				c.northEast.northWest = c.north;
				c.northWest.south = c.southWest;
				c.northWest.northEast = c.north;
				c.north.southWest = c.northWest;
				c.north.southEast = c.northEast;
				
				c = c.south;
				i--;
				continue;
			}
			
			if (!cells.contains(newCell)) {
				cells.add(newCell);
			}
		}
    	
    	
    	this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (Cell c : cells) {
					if (c.contains(e.getX(), e.getY()))
						System.out.println("Cell " + cells.indexOf(c) + " clicked!");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) { }
			@Override
			public void mouseExited(MouseEvent e) {	}			
			@Override
			public void mousePressed(MouseEvent e) { }
			@Override
			public void mouseReleased(MouseEvent e) { }
			
		});
    	this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) { }

			@Override
			public void mouseMoved(MouseEvent e) {
				for (Cell c : cells) {
					if (c.contains(e.getX(), e.getY()))
						c.mouseEntered();
					else
						c.mouseExited();
				}
				repaint();
			}
    		
    	});
		
    }
    
    @Override
	public void paint(Graphics graphics) {
		g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (Cell c : cells)
			c.paint(graphics);
				
		//System.out.println("Painting WHOLE GameBoard!");
		
		/*
		g.setColor(Color.LIGHT_GRAY);
		for (int r=1; r<ROWS; r++) {  // Horizontal lines
            g.drawLine(0, r*CELL_SIZE, WIDTH, r*CELL_SIZE);
        }
        for (int c=1; c<COLS; c++) {
            g.drawLine(c*CELL_SIZE, 0, c*CELL_SIZE, HEIGHT);
        }
        
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int x = c * CELL_SIZE;
                int y = r * CELL_SIZE;
                Unit unit = model.getUnitAt(new Point(c, r));
                if (unit != null) { //check which player
                    g.setColor(Color.GREEN); //then pick correct image
                    g.fillOval(x+2, y+2, CELL_SIZE-4, CELL_SIZE-4);
                }
            }
        }
        */
		
	}
    
}

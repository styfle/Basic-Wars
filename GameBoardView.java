import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


public class GameBoardView extends JPanel {
	private static final long serialVersionUID = -8221311233615840987L;
	public static final int WIDTH = Cell.DIST_TO_CORNER*2 * (Map.CELL_COLS-6);	
    public static final int HEIGHT = Cell.DIST_TO_EDGE*2 * (Map.CELL_ROWS+2);
    private Map map;
    private int index = 0;
    
    public GameBoardView(Map m) {
    	super(true); // double buffered
    	map = m;
    	
    	/*
		Cell c = new Cell(0, 0, Cell.Type.EARTH); // dummy cell
		int x = Cell.DIST_TO_CORNER + 30;
		int y = Cell.DIST_TO_EDGE + 255;
		c = new Cell(x, y, Cell.Type.EARTH);	
		addCell(c, 15, 20); // recursively build map
		*/
		
		System.out.println("Loaded " + map.getName() + " (" + map.getCells().size() + " cells)");
		
		Timer t = new Timer(3, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}			
		});
		
		t.start();
		
    	this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Cell> cells = map.getCells();
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
				for (Cell c : map.getCells()) {
					if (c.contains(e.getX(), e.getY()))
						c.mouseEntered();
					else
						c.mouseExited();
				}
				//repaint(); //TODO make a more efficient check
			}
    		
    	});
		
    }
    
    /*
    private void addCell(Cell c, int row, int col) {
    	map.getCells().add(c);
		if (row > 0) {
			if (c.south == null) {
				Cell newCell = c.generateSouth(Cell.Type.EARTH);
				addCell(newCell, row-1, col);
			}
		}
    	if (col > 0) {
    		if (c.southEast == null) {
    			if (c.south != null && c.south.northEast !=null) {
    				Cell cellSE = c.south.northEast;
    				c.southEast = cellSE;
    				cellSE.northWest = c;
    			}
    		}
    		if (c.northEast == null) {
    			Cell cellNE = c.generateNorthEast(Cell.Type.EARTH);
    			if (c.southEast != null) {
    				cellNE.south = c.southEast;
    	    		c.southEast.north = cellNE;    	    		
    			}
    			addCell(cellNE, 0, col-1);
    		}
    	}
    }
    */
    @Override
	public void paintComponent(Graphics graphics) {
    	if (index < map.getCells().size()) {
    		return;
    	}
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
    
    @Override
    public void paintChildren(Graphics g) {
    	ArrayList<Cell> cells = map.getCells();
    	if (index+1 > cells.size()) {
    		for (Cell c : cells)
    			c.paintCell(g);
    		return;
    	}
    	for (int i=0; i<index; i++) {
    		cells.get(i).paintCell(g);
    	}
    	index++;
    }
    
    public Timer createTimer(final ArrayList<Cell> cells, final Graphics g) {
    	Timer t = new Timer(10, new ActionListener() {
    		int i = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (i > cells.size()-1)
					return;
				cells.get(i).paintCell(g);
				System.out.println("Cell painted");
				i++;
				
			}
		});
		//t.setRepeats(false);
		return t;
    }
    
}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GameBoardView extends JPanel {
	private static final long serialVersionUID = -8221311233615840987L;
	private static final boolean DOUBLE_BUFFERED = true;
	public static final int WIDTH  = 600;
    public static final int HEIGHT = 650;
    private ArrayList<Cell> cells = new ArrayList<Cell>(50);
    
    public GameBoardView() {
    	super(DOUBLE_BUFFERED);

		Cell c = new Cell(0, 0, Cell.Type.EARTH); // dummy cell

		int x = Cell.DIST_TO_CORNER + 30;
		int y = Cell.DIST_TO_EDGE + 255;
		c = new Cell(x, y, Cell.Type.EARTH);//new Color(0f, .3f, 1f, .5f));
		
		addCell(c, 15, 20); //recursively build map
		
		System.out.println(cells.size() + " cells generated for map.");
		
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
				repaint(); //TODO make a more efficient check
			}
    		
    	});
		
    }
    
    private void addCell(Cell c, int row, int col) {
    	cells.add(c);
		if (row > 0) {
			if (c.south == null) {
				Cell newCell = c.generateSouth();
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
    			Cell cellNE = c.generateNorthEast();
    			if (c.southEast != null) {
    				cellNE.south = c.southEast;
    	    		c.southEast.north = cellNE;    	    		
    			}
    			addCell(cellNE, 0, col-1);
    		}
    	}
    }
    
    @Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
    
    @Override
    public void paintChildren(Graphics g) {
		for (Cell c : cells)
			c.paintCell(g);
    }
    
}

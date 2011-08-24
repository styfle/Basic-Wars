import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


/**
 * This is the view where the game is actually playable.
 * Note that the first paint will be animated.
 */
public class GameMapView extends JPanel {
	private static final long serialVersionUID = -8221311233615840987L;
	public static final int WIDTH = Cell.DIST_TO_CORNER*2 * (GameMap.CELL_COLS-6);	
    public static final int HEIGHT = Cell.DIST_TO_EDGE*2 * (GameMap.CELL_ROWS+2);
    private GameMap map;
    private int index = 0; // used for animation
    private Cell selected = null;
    private JPopupMenu popup = new JPopupMenu("Options");
    private JMenuItem moveItem = new JMenuItem("Move");
	private JMenuItem attackItem = new JMenuItem("Attack");
    
    public GameMapView(GameMap m) {
    	super(true); // double buffered
    	map = m;
		System.out.println("Loaded " + map.getName() + " (" + map.getCells().size() + " cells)");
		
		Timer t = new Timer(25, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}			
		});
		t.setInitialDelay(0);
		t.start();
		
		this.add(popup);
		
    	this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Cell> cells = map.getCells();
				if (SwingUtilities.isLeftMouseButton(e)) {
					Cell clicked = null;
					for (Cell c : cells) {
						if (c.contains(e.getPoint())) {
							System.out.println("Cell " + cells.indexOf(c) + " selected!");
							clicked = c;
							break;
						}
					}
					if (clicked != null) {
						for (Cell c : cells)
							if (c.equals(clicked))
								selected = c.setSelected(true);
							else
								c.setSelected(false);
					}
				} else if (SwingUtilities.isRightMouseButton(e)) {
					if (selected != null) {
						for (Cell c : cells)
							if (!c.equals(selected) && c.contains(e.getPoint())) {
								System.out.println("Cell " + cells.indexOf(c) + " right-clicked!");
								popup.removeAll();
								if (c.getUnit() == null)
									popup.add(moveItem);
								else
									popup.add(attackItem);
								popup.show(e.getComponent(), c.getX(), c.getY());
							}
					}
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
    	/*
    	this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) { }

			@Override
			public void mouseMoved(MouseEvent e) {
				for (Cell c : map.getCells()) {
					if (c.contains(e.getPoint()))
						c.mouseEntered();
					else
						c.mouseExited();
				}
			}
    		
    	}); */
		
    }
    
    @Override
	public void paintComponent(Graphics graphics) {
    	if (!isMapLoaded()) {
    		return;
    	}
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(BasicWars.BG_COLOR);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
    
    @Override
    public void paintChildren(Graphics g) {
    	ArrayList<Cell> cells = map.getCells();
    	if (isMapLoaded()) {
    		for (Cell c : cells)
    			c.paintCell(g);
    		return;
    	}
    	
		cells.get(index).paintCell(g);
		cells.get(index+1).paintCell(g);
		cells.get(index+2).paintCell(g);
		cells.get(index+3).paintCell(g);
		cells.get(index+4).paintCell(g);
		cells.get(index+5).paintCell(g);
		cells.get(index+6).paintCell(g);
		cells.get(index+7).paintCell(g);
		cells.get(index+8).paintCell(g);
		cells.get(index+9).paintCell(g);
    	index += 10; // load 10 at a time
    }
    
    public boolean isMapLoaded() {
    	return index+1 > map.getCells().size();
    }
    
}

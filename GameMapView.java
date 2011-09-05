import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;

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
    private Cell rightClicked = null;
    private JPopupMenu popup = new JPopupMenu("Options");
    private JMenuItem moveItem = new JMenuItem("Move");
	private JMenuItem attackItem = new JMenuItem("Attack");
	private ActionListener al;
	private ArrayList<Player> players;
	private Player playerTurn; //current player in control
	private int movesRemaining; //current players moves
    
    public GameMapView(GameMap m, ArrayList<Player> players) {
    	super(true); // double buffered
    	
    	this.map = m;
    	this.players = players;
    	this.playerTurn = players.get(0);
    	this.movesRemaining = 1;
    	this.add(popup);
    	
		System.out.println("Loaded " + map.getName() + " (" + map.getCells().size() + " cells)");
		
		Timer t = new Timer(25, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}			
		});
		t.setInitialDelay(0);
		t.start();

		al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//selected should never be null at this point
				BasicWars o = ((BasicWars)getParent());
				if (selected.getUnit().getPlayer() != playerTurn) {
					o.showMessage("Player "+playerTurn.getNumber()+", are you trying to cheat?\nYou can't control a unit that you don't own!");
				} else {
					if (e.getSource().equals(moveItem)) {
						// one less move available
						movesRemaining--;
						
						Unit u = selected.getUnit();
						selected.setUnit(null);
						for (Cell c : map.getCells())
							c.setValidMove(false);
						
						//TODO animate movement
						rightClicked.setUnit(u);
						selected.setSelected(null);
						selected = rightClicked.setSelected(o);
						rightClicked = null;
						System.out.println("MoveItem Selected  : " + selected);
					} else if (e.getSource().equals(attackItem)) {
						System.out.println("AttackItem Selected: " + selected);
						Unit attacker = selected.getUnit();
						Unit victim = rightClicked.getUnit();
						//TODO animate attack
						victim.attackedBy(attacker);
						if (victim.isDead())
							rightClicked.setUnit(null);
						
						// deselect after attack
						selected.setSelected(null);
						selected = null;
						
						//check if game over
						if (!o.isGameOver())
							nextPlayerTurn();
					}
					
				}
			}
		};
		
		moveItem.addActionListener(al);
		attackItem.addActionListener(al);
		popup.add(moveItem);
		popup.add(attackItem);
		
    	this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Cell> cells = map.getCells();
				BasicWars o = ((BasicWars)getParent());
				if (SwingUtilities.isLeftMouseButton(e)) {
					Cell clicked = null;
					for (Cell c : cells) {
						if (c.contains(e.getPoint())) {
							System.out.println("Cell " + cells.indexOf(c) + " selected!");
							clicked = c;
							break;
						}
					}
					
					HashSet<Cell> validMoves = getValidCells(clicked);
					System.out.println("Valid cells: " + validMoves.size()); //debug
					
					for (Cell c : cells)
						if (c.equals(clicked)) {
							selected = c.setSelected(o);
						} else {
							c.setSelected(null);
							if (validMoves.contains(c) && clicked.getUnit().getPlayer() == playerTurn)
								c.setValidMove(true);
							else
								c.setValidMove(false);
						}
					
				} else if (SwingUtilities.isRightMouseButton(e)) {
					if (selected != null) {
						for (Cell c : cells)
							if (!c.equals(selected) && c.contains(e.getPoint())) {
								rightClicked = c;
								System.out.println("Cell " + cells.indexOf(c) + " right-clicked!");
								boolean isEmptyCell = (c.getUnit() == null);
								boolean canMove = movesRemaining > 0;
								boolean validMove = getValidCells(selected).contains(rightClicked);
								moveItem.setEnabled(isEmptyCell && canMove && validMove);
								attackItem.setEnabled(!isEmptyCell);
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
    
    private void nextPlayerTurn() {
    	int i = players.indexOf(playerTurn);
    	i++;
    	if (i < players.size())
    		playerTurn = players.get(i);
    	else
    		playerTurn = players.get(0);
    	System.out.println("Player turn: " + playerTurn);
    }
    
    /**
     * @param c The center cell
     * @param radius Max radius from Cell c that is legal
     * @return set of cells that are <= radius from c
     */
    private HashSet<Cell> getValidCells(Cell c) {
    	HashSet<Cell> set = new HashSet<Cell>(10);
    	if (c != null && c.getUnit() != null) {
    		addAdj(c, set, 1, c.getUnit().getMaxMoves());
    	} //else return empty set
    	return set;
    }
    
    private void addAdj(Cell c, HashSet<Cell> set, int i, int max) {
    	if (c == null || i > max)
    		return;
    	for (Cell adj : c.getAdjacentCells()) {
    		//System.out.println("Adding " + adj + " as valid move.");
    		set.add(adj);
    		addAdj(adj, set, i+1, max);
    	}
    }
    
}

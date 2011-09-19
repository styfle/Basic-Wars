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
	public static final int WIDTH = 695;//Cell.DIST_TO_CORNER*2 * (GameMap.CELL_COLS-6);	
    public static final int HEIGHT = 650;//Cell.DIST_TO_EDGE*2 * (GameMap.CELL_ROWS+2);
    private GameMap map;
    private int index = 0; // used for animation
    private Cell selected = null;
    private Cell rightClicked = null;
    private JPopupMenu popup = new JPopupMenu("Options");
    private JMenuItem moveItem = new JMenuItem("Move");
	private JMenuItem attackItem = new JMenuItem("Attack");
	private JMenuItem skipItem = new JMenuItem("Skip Turn");
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
		
		Timer t = new Timer(10, new ActionListener() { // 1000ms / 10ms pf = 100fps
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
				if (e.getSource().equals(skipItem)) {
					//remove highlights for valid moves
					for (Cell c : map.getCells())
						c.setValidMove(false);
					
					// deselect after attack
					selected.setSelected(null);
					selected = null;
					nextPlayerTurn(o);
				} else if (selected.getUnit().getPlayer() != playerTurn) {
					o.showMessage("Player "+playerTurn.getNumber()+", are you trying to cheat?\nYou can't control a unit that you don't own!");
				} else {
					if (e.getSource().equals(moveItem)) {
						
						// check if valid move
						if (!canMoveToCell(selected, rightClicked)) {
							o.showMessage("You can't move there, you're just a " + selected.getUnit().getType()+"! Surely you know the rules of battle?");
							return;
						}
						
						// one less move available
						movesRemaining--;
						
						Unit u = selected.getUnit();
						selected.setUnit(null);
						for (Cell c : map.getCells())
							c.setValidMove(false);
						
						//TODO animate movement
						rightClicked.setUnit(u);
						selected.setSelected(null);
						selected = rightClicked;
						
						rightClicked = null;
						o.showTurn(playerTurn, movesRemaining);
						
						if (selected == null) // DEBUG
							System.out.println("OMG: selected == null");
						
						for (Cell c : getValidAttacks(selected))
								c.setValidAttack(true);
								
						selected.setSelected(o); //ensure color is correct
						
					} else if (e.getSource().equals(attackItem)) {
						Unit attacker = selected.getUnit();
						Unit victim = rightClicked.getUnit();
						//TODO animate attack
						victim.attackedBy(attacker);
						if (victim.isDead())
							rightClicked.setUnit(null);
						
						//remove highlights for valid moves
						for (Cell c : map.getCells())
							c.setValidMove(false);
						
						// deselect after attack
						selected.setSelected(null);
						selected = null;
						o.showSelected(null);
						
						//check if game over
						if (!o.isGameOver()) {
							nextPlayerTurn(o);
						}
					}
					
				}
			}
		};
		
		moveItem.addActionListener(al);
		attackItem.addActionListener(al);
		skipItem.addActionListener(al);
		
		popup.add(moveItem);
		popup.add(attackItem);
		popup.addSeparator();
		popup.add(skipItem);
		
    	this.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				
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
					
					HashSet<Cell> validCells;
					if (movesRemaining > 0)
						validCells = getValidMoves(clicked);
					else
						validCells = getValidAttacks(clicked);
					//System.out.println("Valid cells: " + validMoves.size()); //debug
					
					for (Cell c : cells)
						if (c.equals(clicked)) {
							selected = c.setSelected(o);
						} else {
							c.setSelected(null);
							if (validCells.contains(c) && clicked.getUnit().getPlayer() == playerTurn)
								c.setValidMove(true);
							else
								c.setValidMove(false);
						}
					
				} else if (SwingUtilities.isRightMouseButton(e)) {
					if (selected != null) {
						for (Cell c : cells)
							if (c.contains(e.getPoint()) && !c.equals(selected)) {
								rightClicked = c;
								System.out.println("Cell " + cells.indexOf(c) + " right-clicked!");
								boolean isEmptyCell = (c.getUnit() == null);
								boolean canMove = movesRemaining > 0;
								boolean validMove = getValidMoves(selected).contains(rightClicked);
								boolean validAttack = getValidAttacks(selected).contains(rightClicked);
								moveItem.setEnabled(isEmptyCell && canMove && validMove);
								attackItem.setEnabled(!isEmptyCell && validAttack);
								popup.show(e.getComponent(), c.getX(), c.getY());
								break;
							}
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) { }
			@Override
			public void mouseExited(MouseEvent e) {	}			
			@Override
			public void mouseClicked(MouseEvent e) { } // mousePressed is more responsive
			@Override
			public void mouseReleased(MouseEvent e) { }
			
		});
		
    }
    
    @Override
	public void paintComponent(Graphics graphics) {
    	if (isMapLoaded() || index == 0) {
			Graphics2D g = (Graphics2D)graphics;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(BasicWars.BG_COLOR);
			g.fillRect(0, 0, WIDTH, HEIGHT);
    	}
	}
    
    @Override
    public void paintChildren(Graphics g) {
    	ArrayList<Cell> cells = map.getCells();
    	if (isMapLoaded()) {
    		for (Cell c : cells)
    			c.paintCell(g);
    		return;
    	}
    	
		cells.get(index+0).paintCell(g);
		cells.get(index+1).paintCell(g);
		cells.get(index+2).paintCell(g);
		cells.get(index+3).paintCell(g);
		cells.get(index+4).paintCell(g);
		index += 5; // load 5 at a time
		//cells.get(index+5).paintCell(g);
		//cells.get(index+6).paintCell(g);
		//cells.get(index+7).paintCell(g);
		//cells.get(index+8).paintCell(g);
		//cells.get(index+9).paintCell(g);
    	//index += 10; // load 10 at a time
    }
    
    public boolean isMapLoaded() {
    	return index+1 > map.getCells().size();
    }
    
    public Player getPlayerTurn() { return playerTurn; }
    public int getMovesRemaining() { return movesRemaining; }
    public Cell getSelected() { return selected; }
    
    private void nextPlayerTurn(BasicWars o) {
    	int i = players.indexOf(playerTurn);
    	i++;
    	if (i < players.size())
    		playerTurn = players.get(i);
    	else
    		playerTurn = players.get(0);
    	
    	this.movesRemaining = 1;
    	o.showTurn(playerTurn, movesRemaining);
    }
    
    /**
     * @param c The center cell
     * @return set of cells that are <= radius from c
     */
    private HashSet<Cell> getValidMoves(Cell c) {
    	HashSet<Cell> set = new HashSet<Cell>(10);
    	if (c != null && c.getUnit() != null) {
    		addAdj(c, c, set, 1, c.getUnit().getMaxMoves(), false);
    	} //else return empty set
    	return set;
    }
    
    private HashSet<Cell> getValidAttacks(Cell c) {
    	HashSet<Cell> set = new HashSet<Cell>(10);
    	if (c != null && c.getUnit() != null) {
    		addAdj(c, c, set, 1, c.getUnit().getMaxAttackDist(), true);
    	} //else return empty set
    	return set;
    }
    
    private void addAdj(Cell start, Cell cur, HashSet<Cell> set, int i, int max, boolean attack) {
    	if (cur == null || i > max)
    		return;
    	
    	for (Cell adj : cur.getAdjacentCells()) {
    		if (adj != null && (attack || canMoveToCell(start, adj)))
    			set.add(adj); // attacks are not limited by cell type
    		
    		addAdj(start, adj, set, i+1, max, attack);
    	}
    }
    
    private boolean canMoveToCell(Cell from, Cell to) {
    	boolean validMove = false;
    	if (from == null || to == null)
    		return false;
    	switch (from.getUnit().getType()) {
    		case SOLDIER: //water yes, swamp no
	    		validMove = (to.getType() != Cell.Type.SWAMP);
	    		break;
    		case TANK: // water no, swamp yes
	    		validMove = (to.getType() != Cell.Type.WATER);
	    		break;
    		case PLANE: // only fly over water/swamp, must land on earth
	    		validMove = (to.getType() == Cell.Type.EARTH);
	    		break;
    	}
    	return validMove;
    }
    
}

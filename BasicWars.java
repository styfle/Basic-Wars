import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

/**
 * Basic Wars is a turn-based strategy game written in Java.
 * It is loosely based on Advance Wars.
 * For use in the ICS Summer Of Code competition.
 * @author Steven Salat
 */
public class BasicWars extends JPanel {
	private static final long serialVersionUID = -5944312753990108995L;
	public static final String GAME_NAME = "Basic Wars";
	public static final String GAME_VERSION = "0.65";
	public static final Font HEAD_FONT = new Font("DialogInput", Font.PLAIN, 75);
	public static final Font BODY_FONT = new Font("Dialog", Font.PLAIN, 25);
	public static final Font BOLD_FONT = new Font("Courier", Font.BOLD, 30);
	public static final Color BG_COLOR = Color.BLACK;
	public static final Color HEAD_COLOR = Color.RED;
	public static final Color TEXT_COLOR = new Color(150,180,150);
	private static JFrame frame;
	private ControlPanel controlPanel;
	private MainMenuView mainMenu;
	private AboutView aboutView;
	private PlayerSelectView playerMenu;
	private MapSelectView mapMenu;
	private UnitSelectView unitMenu;
	private Timer timer;
	private int currentPlayer; // each player needs unit selection menus
	private ArrayList<Player> players;
	private GameMap map;
	private GameMapView board;
	private boolean isGameInProgress;
	
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
									"EEEEEEEEEEESEEEEEEEEEEEEE\n" + 
									"EEEEEEEEESSWWWEEEEEEEEEEE\n" + 
									"EEEEEEEEEWWWWWWSEEEEEEEEE\n" + 
									"EEEEEEEESWWWWWWSEEEEEEEEE\n" + 
									"EEEEEEEESWWWWWWSEEEEEEEEE\n" + 
									"EEEEEEEEESWWWWWSEEEEEEEEE\n" + 
									"EEEEEEEEEESSWSSEEEEEEEEEE\n" + 
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
	
	private final static String map2 = "EEEEEEEEEEEEEEEEEEEEEEEEE\n" +
										"EWEWWWEWWWEWWWEWWWEWWWEWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEEEEEWWWWWWWWWE\n" +
										"EWWWWWWWWWEEEEEWWWWWWWWWE\n" +
										"EWWWWWWWWWEEEEEWWWWWWWWWE\n" +
										"EWWWWWWWWWEEEEEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWEWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EWWWWWWWWWEWWWEWWWWWWWWWE\n" +
										"EEWWWWWWWWEWWWEWWWWWWWWEE\n" +
										"EEEEEEEEEEEEEEEEEEEEEEEEE\n";
	
	public GameMap[] maps = {	
			new GameMap("Plain Field", map0),
			new GameMap("Oasis", map1),
			new GameMap("Water World", map2)
		};
	
	public BasicWars() {
		super(new BorderLayout());
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");
		
		isGameInProgress = false;
		
		//System.setErr(new java.io.PrintStream("error.log"));
		
		// instantiate all JPanels at launch for smooth tranitions
		controlPanel = new ControlPanel("Basic Wars. Basically awesome.");
		mainMenu = new MainMenuView();
		aboutView = new AboutView();
		playerMenu = new PlayerSelectView();
		mapMenu = new MapSelectView(maps);
		
		players = new ArrayList<Player>(2);
		
		add(controlPanel, BorderLayout.NORTH);
		add(mainMenu, BorderLayout.CENTER);
	}
	
	/**
	 * Loads a map to the display
	 * @param m Map to load
	 */
	public void loadMap(boolean newGame) {
		remove(1);
		
		if (!newGame) {
			showTurn(board.getPlayerTurn(), board.getMovesRemaining());
			showSelected(board.getSelected().getUnit());
			showStatus("Game resumed successfully!");
		} else {
			board = new GameMapView(map, players);
			timer = new Timer(50, new ActionListener() { //speed of loading map
				@Override
				public void actionPerformed(ActionEvent e) {				
					if (board.isMapLoaded()) {
						timer.stop();
						
						showTurn(board.getPlayerTurn(), board.getMovesRemaining());
						showSelected(null); // enables extra menu items
						showStatus("Map loaded. Let's play!");
						
						// randomly add units to the board
						Random r = new Random();
						int i;
						Cell c;
						boolean valid;
						ArrayList<Cell> cells = map.getCells();
						for (Player p : players) {
							Unit u;
							while ((u = p.getNextUnit()) != null) {
								do {
									i = r.nextInt(cells.size());
									c = cells.get(i);
									valid = c.getUnit() == null && // must have no unit in cell
											c.getType().equals(Cell.Type.EARTH) && // must be earth type
											((p.getNumber() == 1) ? (i % 25 < 11) : (i % 25 > 12)); // place on correct side
								} while (!valid);
								c.setUnit(u);
								//System.out.println("Unit on cell #" + cells.indexOf(c));
							}
						}
					}
				}			
			});
			timer.start();
			showStatus("Loading map...(fancy ain't it?)");
		}
		
		add(board, BorderLayout.CENTER);
		isGameInProgress = true;
	}
	
	private void loadMenu(Menu menu, String status) {
		remove(1);
		showStatus(status);
		add(menu, BorderLayout.CENTER);
		validate();
		repaint();
	}
	
	/**
	 * Loads the main menu. Any games in progress will be discarded.
	 */
	public void loadMainMenu() {
		controlPanel.setMainMenuButton();
		mainMenu.setResumable(isGameInProgress);
		loadMenu(mainMenu, "Basic Wars. Basically awesome.");
	}
	
	public void loadAbout() {
		controlPanel.setMainMenuButton();
		loadMenu(aboutView, "Who cares? Just play the game already...");
	}
	
	public void loadPlayerMenu() {
		controlPanel.setMainMenuButton();
		loadMenu(playerMenu, "Select how many players.");
	}
	
	public void loadMapMenu() {
		currentPlayer = 0;
		controlPanel.setPlayerSelectButton();
		loadMenu(mapMenu, "Select a predefined map or load your own!");
	}
	
	public void loadUnitMenu() {
		controlPanel.setMapSelectButton();
		if (currentPlayer < players.size()) {
			unitMenu = new UnitSelectView(players.get(currentPlayer));
			loadMenu(unitMenu, "Select your units");
			currentPlayer++;
		} else {
			controlPanel.setMainMenuButton();
			loadMap(true); //new game
		}
	}
	
	public void showStatus(String s) { controlPanel.setStatus(s); }
	
	public void showSelected(Unit u) { controlPanel.setSelected(u); }
	
	public void showTurn(Player p, int moves) { controlPanel.setTurn(p, moves); }
	
	public void setMap(GameMap map) { this.map = map; }
	
	public void setPlayers(int count) {
		players.clear();
		for (int i=0; i<count; i++)
			players.add(new Player(i+1));
	}
	
	public void startNewGame() {
		players.clear();
		map = null;
		isGameInProgress = false;
		loadPlayerMenu();
	}
	
	public void resumeGame() {
		
		if (isGameInProgress)
			loadMap(false); // not new game, resume
		else
			showMessage("There is no game in session.");
		
		//showMessage("Resume is not implemented yet.");
	}
	
	public boolean isGameOver() {
		for (Player p : players) {
			if (p.getUnitCount() == 0) {
				System.out.println("GAME OVER!");
				showStatus("All of Player " + p.getNumber() + "'s units have been destroyed!");
				showTurn(null, 0);
				showMessage("Game Over! All of Player " + p.getNumber() + "'s units have been destroyed!");
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Helper method for launching GUI from main
	 */
	public static void createAndShowGUI() {
		frame = new JFrame(GAME_NAME + " v" + GAME_VERSION);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		JComponent panel = new BasicWars();
		panel.setOpaque(true); //content panes must be opaque
		frame.setContentPane(panel);
		frame.setPreferredSize(new Dimension(GameMapView.WIDTH, GameMapView.HEIGHT + ControlPanel.HEIGHT*2));

		//Display the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//Enable anti-aliasing
		Graphics2D g = (Graphics2D)frame.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //shapes
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //text
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); //quality
	}
	
	/**
	 * Lightens a color by a given amount
	 * 
	 * @param color The color to lighten
	 * @param amount The amount to lighten the color. 0 is unchanged; 1 is completely white
	 * @return The bleached color
	 */
	public static Color bleach(Color color, float amount) {
		int red = (int) ((color.getRed() * (1 - amount) / 255 + amount) * 255);
		int green = (int) ((color.getGreen() * (1 - amount) / 255 + amount) * 255);
		int blue = (int) ((color.getBlue() * (1 - amount) / 255 + amount) * 255);
		return new Color(red, green, blue);
	}
	
	/**
	 * Display an error dialog box when something bad occurs
	 * @param errorNum An arbitrary number to reference the error
	 * @param message The message to show the user
	 */
	public void showError(int errorNum, String message) {
		JOptionPane.showMessageDialog(this, message, "Error " + errorNum, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Display a message dialog box
	 * @param message The message to show the user
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) { }
		catch (ClassNotFoundException e) { }
		catch (InstantiationException e) { }
		catch (IllegalAccessException e) { }

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BasicWars.createAndShowGUI();
			}
		});
		
	}
}

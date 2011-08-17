import java.awt.*;

import javax.swing.*;


public class BasicWars extends JPanel {
	private static final long serialVersionUID = -5944312753990108995L;
	public static final String GAME_NAME = "Basic Wars";
	public static final String GAME_VERSION = "0.2";
	private static JFrame frame;
	private StatusPanel statusPanel = new StatusPanel("Select a map to begin playing");
	private MainMenuView mainMenu;
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
			new GameMap("Water World", map2),
			new GameMap("Load Custom Map", null)
		};
	
	public BasicWars() {
		super(new BorderLayout());
		
		mainMenu = new MainMenuView(maps);
		
		this.add(statusPanel, BorderLayout.NORTH);
		this.add(mainMenu, BorderLayout.CENTER);
	}
	
	/**
	 * Loads a map to the display
	 * @param m Map to load
	 */
	public void load(GameMap m) {
		removeAll();
		statusPanel.setStatus("Loading map...(fancy ain't it?)");
		add(statusPanel, BorderLayout.NORTH);
		add(new GameMapView(m), BorderLayout.CENTER);		
		frame.pack();
		//northPanel.setStatus("Select your units.");
	}
	
	public void setStatus(String s) {
		statusPanel.setStatus(s);
	}
	
	public static void createAndShowGUI() {
        frame = new JFrame(GAME_NAME + " v" + GAME_VERSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent panel = new BasicWars();
        panel.setOpaque(true); //content panes must be opaque
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(GameMapView.WIDTH, GameMapView.HEIGHT + StatusPanel.HEIGHT*2));

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
	
	
	
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BasicWars.createAndShowGUI();
            }
        });		
		
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
	 * @param unit String representation of the unit
	 * @param owner The player that will be using the unit
	 * @return Path to the image for this unit
	 */
	public static String getFileName(String unit, Player owner) {
		if (owner == null)
				throw new IllegalArgumentException("NULL Player. Can't create a soldier without a Player!");
		return "images/"+unit+owner.getNumber()+"_"+owner.getSide().toString();
	}
	
}

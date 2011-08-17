import java.awt.*;
import javax.swing.*;


public class GamePanel extends JPanel {
	private static final long serialVersionUID = -5944312753990108995L;
	private static JFrame frame;
	private static JPanel northPanel = new JPanel();
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
	
	public GamePanel() {
		super(new BorderLayout());
		JLabel north = new JLabel("Menubar could go here");
		northPanel.add(north);
		
		Map[] maps = {new Map("Plain Field",map0), new Map("Oasis",map1), new Map("Load Custom Map",null)};
		mainMenu = new MainMenuView(this, maps);
		//GameBoardView board = new GameBoardView(maps[1]);
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(mainMenu, BorderLayout.CENTER);
	}
	
	public void load(final Map m) {
		removeAll();
		add(northPanel, BorderLayout.NORTH);
		add(new GameBoardView(m), BorderLayout.CENTER);
		frame.pack();
	}
	
	public static void createAndShowGUI() {
        frame = new JFrame(Main.GAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent panel = new GamePanel();
        panel.setOpaque(true); //content panes must be opaque
        frame.setContentPane(panel);
        int h = (int)northPanel.getPreferredSize().getHeight();
        frame.setPreferredSize(new Dimension(GameBoardView.WIDTH, GameBoardView.HEIGHT + h*2 - 5));

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
	
}

import java.awt.*;
import javax.swing.*;


public class GamePanel extends JPanel {
	private static final long serialVersionUID = -5944312753990108995L;
	
	private static final int COLS = 60;
	private static final int ROWS = 40;	
	private static final int CELL_SIZE = 15; // Pixels
	private static final int WIDTH  = COLS * CELL_SIZE;
    private static final int HEIGHT = ROWS * CELL_SIZE;
	
	public GamePanel() {
		super(new BorderLayout());
		JLabel north = new JLabel("North side");
		JLabel south = new JLabel("South side");
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();
		northPanel.add(north);
		southPanel.add(south);
		
		GameBoardView board = new GameBoardView();
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(board, BorderLayout.CENTER);
	}
	
	public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame(Main.GAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent panel = new GamePanel();
        panel.setOpaque(true); //content panes must be opaque
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
	
}

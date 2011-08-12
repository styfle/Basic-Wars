import java.awt.*;
import javax.swing.*;


public class GamePanel extends JPanel {
	private static final long serialVersionUID = -5944312753990108995L;
	private static JPanel northPanel = new JPanel();
	
	public GamePanel() {
		super(new BorderLayout());
		JLabel north = new JLabel("Menubar could go here");
		northPanel.add(north);
		
		GameBoardView board = new GameBoardView(new Map());
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(board, BorderLayout.CENTER);
	}
	
	public static void createAndShowGUI() {
        JFrame frame = new JFrame(Main.GAME_NAME);
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

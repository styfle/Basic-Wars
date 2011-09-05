import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;


/**
 * This panel contains the controls/components for the current main panel
 * Buttons for the main menu, etc should go here
 */
public class ControlPanel extends JPanel {
	private static final long serialVersionUID = -8353479383875379010L;
	public static final int WIDTH = GameMapView.WIDTH;
	public static final int HEIGHT = 22;
	private static final int SEPARATION = 5;
	private static final Font STATUS_FONT = new Font("Courier", Font.PLAIN, 12);
	private JToolBar toolBar;
	private JButton mainMenuButton;
	private JButton playerSelectButton;
	private JButton mapSelectButton;
	private JPanel hud;
	private JLabel currentTurn;
	private JLabel statusLabel = new JLabel();
	private ActionListener al;
	//private static final Color BUTTON_COLOR = new Color(50,50,50);
	
	/**
	 * Panel that contains buttons for user navigation and status output
	 * @param status Display status on this panel
	 */
	public ControlPanel(String status) {
		super(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BasicWars main = (BasicWars)getParent();
				if (e.getSource().equals(mainMenuButton)) {
					main.loadMainMenu();
				} else if (e.getSource().equals(playerSelectButton)) {
					main.loadPlayerMenu();
				} else if (e.getSource().equals(mapSelectButton)) {
					main.loadMapMenu();
				}
				System.out.println(((JButton)e.getSource()).getText() + " clicked.");
			}
		};
		
		mainMenuButton = createButton("Main Menu");
		playerSelectButton = createButton("Player Select");
		mapSelectButton = createButton("Map Select");
		
		toolBar = new JToolBar("Toolbar");
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, SEPARATION, 0));
		toolBar.setBackground(BasicWars.BG_COLOR);
		
		hud = new JPanel();
		hud.setBackground(new Color(0, 0, 0, 50));
		currentTurn = new JLabel("Player 1:  move ");
		currentTurn.setFont(STATUS_FONT);
		currentTurn.setForeground(BasicWars.TEXT_COLOR);
		hud.add(currentTurn);
		
		statusLabel.setFont(STATUS_FONT);
		statusLabel.setForeground(BasicWars.TEXT_COLOR);
		statusLabel.setText(status);
		
		toolBar.add(mainMenuButton);
		toolBar.add(hud);
		toolBar.addSeparator(new Dimension(SEPARATION, HEIGHT));
		toolBar.add(statusLabel);		
		this.add(toolBar, BorderLayout.CENTER);
	}
	
	private JButton createButton(String label) {
		JButton b = new JButton(label);
		//b.setBorderPainted(false);
		b.setBounds(0, 0, 100, HEIGHT);
		/*b.setBackground(BUTTON_COLOR);*/
		b.setForeground(BasicWars.TEXT_COLOR);
		b.setFont(new Font("Arial", Font.BOLD, 12));
		b.setPreferredSize(new Dimension(b.getWidth(), HEIGHT));
		b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b.setFocusable(false);		
		b.addActionListener(al);
		return b;
	}
	
	/**
	 * Updates the status at the top of the screen
	 * @param status new status
	 */
	public void setStatus(String status) { statusLabel.setText(status); }
	
	/**
	 * When a unit is selected, show their health/etc
	 * @param u Selected Unit
	 */
	public void showSelected(Unit u) {
		if (u == null) {
			statusLabel = new JLabel("Select a unit.");
			statusLabel.setFont(STATUS_FONT);
			statusLabel.setForeground(BasicWars.TEXT_COLOR);
		} else {
			statusLabel = new JLabel(u.getHealth() + "/" + u.getMaxHealth() + " HP", u.getIcon(), SwingConstants.LEFT);
			statusLabel.setFont(STATUS_FONT);
			if (u.getHealthPercent() < 25)
				statusLabel.setForeground(Color.RED);
			else if (u.getHealthPercent() < 50)
				statusLabel.setForeground(Color.ORANGE);
			else if (u.getHealthPercent() < 75)
				statusLabel.setForeground(Color.YELLOW);
			else
				statusLabel.setForeground(Color.GREEN);
		}
		toolBar.removeAll();
		toolBar.add(mainMenuButton);
		toolBar.add(hud);
		toolBar.add(statusLabel);
		toolBar.repaint();
	}
	
	private void setButton(JButton button) {
		toolBar.removeAll();
		toolBar.add(button);
		toolBar.add(statusLabel);
	}
	
	/**
	 * Updates display with current player's turn and moves remaining
	 * @param p Player who is currently in control
	 */
	public void showTurn(Player p, int moves) {
		if (moves > 0)
			currentTurn.setText("Player " + p.getNumber() + ":  move ");
		else
			currentTurn.setText("Player " + p.getNumber() + ": attack");
		toolBar.repaint();
	}
	
	public void setMainMenuButton() { setButton(mainMenuButton); }
	public void setPlayerSelectButton() { setButton(playerSelectButton); }
	public void setMapSelectButton() { setButton(mapSelectButton); }
}

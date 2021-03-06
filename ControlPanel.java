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
	private JLabel currentTurn;
	private JLabel selectedUnit;
	private JLabel statusLabel;
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
		
		currentTurn = new JLabel("Player 1:   MOVE");
		currentTurn.setFont(STATUS_FONT);
		currentTurn.setForeground(BasicWars.TEXT_COLOR);
		selectedUnit = new JLabel("Nothing selected");
		selectedUnit.setFont(STATUS_FONT);
		selectedUnit.setForeground(BasicWars.TEXT_COLOR);
		
		statusLabel = new JLabel(status);
		statusLabel.setFont(STATUS_FONT);
		statusLabel.setForeground(BasicWars.TEXT_COLOR);
		
		toolBar.add(mainMenuButton);
		toolBar.addSeparator(new Dimension(SEPARATION, HEIGHT));
		toolBar.add(statusLabel);		
		this.add(toolBar, BorderLayout.CENTER);
	}
	
	private JButton createButton(String label) {
		JButton b = new JButton(label);
		//b.setBorderPainted(false);
		b.setBounds(0, 0, 100, HEIGHT);
		//b.setBackground(BUTTON_COLOR);
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
	public void setStatus(String status) { statusLabel.setText(status); statusLabel.repaint(); }
	
	/**
	 * When a unit is selected, show their health/etc
	 * @param u Selected Unit
	 */
	public void setSelected(Unit u) {
		if (u == null) {
			selectedUnit = new JLabel("Nothing selected");
			selectedUnit.setFont(STATUS_FONT);
			selectedUnit.setForeground(BasicWars.TEXT_COLOR);
			statusLabel.setText("Click on a unit to select it.");
		} else {
			selectedUnit = new JLabel(
					u.getHealth() + "/" + u.getMaxHealth() + " HP",
					u.getIcon(),
					SwingConstants.LEFT
			);
			statusLabel.setText("Right-click for actions.");
			selectedUnit.setFont(STATUS_FONT);
			if (u.getHealthPercent() < 25)
				selectedUnit.setForeground(Color.RED);
			else if (u.getHealthPercent() < 50)
				selectedUnit.setForeground(Color.ORANGE);
			else if (u.getHealthPercent() < 75)
				selectedUnit.setForeground(Color.YELLOW);
			else
				selectedUnit.setForeground(Color.GREEN);
		}
		toolBar.removeAll();
		toolBar.add(mainMenuButton);
		toolBar.addSeparator(new Dimension(SEPARATION, HEIGHT));
		toolBar.add(currentTurn);
		toolBar.addSeparator(new Dimension(SEPARATION, HEIGHT));
		toolBar.add(selectedUnit);
		toolBar.addSeparator(new Dimension(SEPARATION, HEIGHT));
		toolBar.add(statusLabel);
		toolBar.repaint();
	}
	
	private void setButton(JButton button) {
		toolBar.removeAll();
		toolBar.add(button);
		toolBar.addSeparator(new Dimension(SEPARATION, HEIGHT));
		toolBar.add(statusLabel);
	}
	
	/**
	 * Updates display with current player's turn and moves remaining
	 * @param p Player who is currently in control or Game Over when p == null
	 */
	public void setTurn(Player p, int moves) {
		if (p == null)
			currentTurn.setText("Game Over!");
		else if (moves > 0)
			currentTurn.setText("Player " + p.getNumber() + ":   MOVE");
		else
			currentTurn.setText("Player " + p.getNumber() + ": ATTACK");
		
		toolBar.repaint();
	}
	
	public void setMainMenuButton() { setButton(mainMenuButton); }
	public void setPlayerSelectButton() { setButton(playerSelectButton); }
	public void setMapSelectButton() { setButton(mapSelectButton); }
}

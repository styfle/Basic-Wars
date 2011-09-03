import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
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
	private JLabel hud;
	private JLabel statusLabel = new JLabel();
	private ActionListener al;
	private static final Color BUTTON_COLOR = new Color(50,50,50);
	
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
		
		hud = new JLabel("");
		
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
		b.setBorderPainted(false);
		b.setBounds(0, 0, 100, HEIGHT);
		b.setBackground(BUTTON_COLOR);
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
	 * When a unit is selected, show their health/etc on the hud
	 * @param u Selected Unit
	 */
	public void setSelected(Unit u) {
		if (u == null) {
			hud = new JLabel("Nothing selected.");
			statusLabel.setText("Select a unit");
		} else {
			hud = new JLabel(u.getHealth() + "/" + u.getMaxHealth() + " HP", u.getIcon(), SwingConstants.LEFT);
			statusLabel.setText("Player " + u.getPlayer().getNumber() + " selected " + u.toString());
		}
		hud.setForeground(Color.RED);
		hud.setFont(STATUS_FONT);
		toolBar.removeAll();
		toolBar.add(mainMenuButton);
		toolBar.add(hud);
		toolBar.add(statusLabel);
	}
	
	private void setButton(JButton button) {
		toolBar.removeAll();
		toolBar.add(button);
		toolBar.add(statusLabel);
	}
	
	public void setMainMenuButton() { setButton(mainMenuButton); }
	public void setPlayerSelectButton() { setButton(playerSelectButton); }
	public void setMapSelectButton() { setButton(mapSelectButton); }
}

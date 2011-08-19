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


/**
 * This panel contains the controls/components for the current main panel
 * Buttons for the main menu, etc should go here
 */
public class ControlPanel extends JPanel {
	private static final long serialVersionUID = -8353479383875379010L;
	public static final int WIDTH = GameMapView.WIDTH;
	public static final int HEIGHT = 22;
	private static final int SEPARATION = 5;
	private JButton mainMenuButton = new JButton();
	private JButton playerSelectButton = new JButton();
	private JButton mapSelectButton = new JButton();
	private JLabel statusLabel = new JLabel();
	private ActionListener al;
	
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
		
		JToolBar toolBar = new JToolBar("Toolbar");
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, SEPARATION, 0));
		toolBar.setBackground(BasicWars.BG_COLOR);
		
		statusLabel.setFont(new Font("Courier", Font.PLAIN, 12));
		statusLabel.setForeground(new Color(150,180,150));
		statusLabel.setText(status);
		
		toolBar.add(mainMenuButton);
		toolBar.add(playerSelectButton);
		toolBar.add(mapSelectButton);
		toolBar.addSeparator(new Dimension(SEPARATION, HEIGHT));
		toolBar.add(statusLabel);		
		this.add(toolBar, BorderLayout.NORTH);
	}
	
	private JButton createButton(String label) {
		JButton b = new JButton(label);
		b.setBorderPainted(false);
		b.setBounds(0, 0, 100, HEIGHT);
		b.setBackground(new Color(50,50,50));
		b.setForeground(new Color(150,180,150));
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
	public void setStatus(String status) {
		statusLabel.setText(status);
	}
}

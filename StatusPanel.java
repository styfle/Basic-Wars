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


public class StatusPanel extends JPanel {
	private static final long serialVersionUID = -8353479383875379010L;
	public final static int WIDTH = GameMapView.WIDTH;
	public final static int HEIGHT = 22;
	private JButton mainMenuButton = createButton("Main Menu");
	private JButton tempButton = createButton("Temp");
	private JLabel statusLabel = new JLabel();
	
	/**
	 * Panel that contains buttons for user navigation and status output
	 * @param status Display status on this panel
	 */
	public StatusPanel(String status) {
		super(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		JToolBar toolBar = new JToolBar("Toolbar");
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		toolBar.setBackground(Color.BLACK);
		
		statusLabel.setFont(new Font("Courier", Font.PLAIN, 12));
		statusLabel.setForeground(new Color(150,180,150));
		statusLabel.setText(status);
		
		toolBar.add(mainMenuButton);
		toolBar.add(tempButton);
		toolBar.add(statusLabel);		
		this.add(toolBar, BorderLayout.NORTH);
	}
	
	private JButton createButton(String label) {
		JButton b = new JButton(label);
		b.setBorderPainted(false);
		b.setBounds(0, 0, 100, HEIGHT);
		b.setBackground(new Color(50,50,50));
		b.setForeground(new Color(150,160,180));
		b.setFont(new Font("Arial", Font.BOLD, 12));
		b.setPreferredSize(new Dimension(75, HEIGHT));
		b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(mainMenuButton)) {
					System.out.println("Main menu button clicked");
					BasicWars main = (BasicWars)getParent();
					main.loadMainMenu();
				} else if (e.getSource().equals(tempButton)) {
					System.out.println("Temp button clicked");
				}
			}			
		});
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

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;


public class Overlay {
	private static final Color color = new Color(255, 25, 0);
	private static final Font font = new Font("Helvetica", Font.BOLD, 30);
	private JLabel title;
	private JLabel body;
	private JFrame frame;
	private Timer timer;
	
	public Overlay() {
		frame = new JFrame();
		frame.setBackground(new Color(30, 30, 30, 80));
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);

		Container pane = frame.getContentPane();
		pane.setLayout(new java.awt.BorderLayout());
		
		title = new JLabel();
		body = new JLabel();
		title.setForeground(color);
		body.setForeground(color);
		title.setFont(font);
		body.setFont(font);
		
		pane.add(title, java.awt.BorderLayout.NORTH);
		pane.add(body, java.awt.BorderLayout.CENTER);
		
		//Graphics g = pane.getGraphics();
		
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(false);
		
		frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) { }
			@Override
			public void mouseEntered(MouseEvent e) { }
			@Override
			public void mouseExited(MouseEvent e) { }
			@Override
			public void mousePressed(MouseEvent e) { hide(); }
			@Override
			public void mouseReleased(MouseEvent e) { }
		});
		

		timer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hide();
			}
		});
		timer.setRepeats(false);
		
	}
	
	public void show(String title, String body, int x, int y) {
		hide();
		
		this.title.setText(title.toUpperCase());
		this.body.setText(body);
		
		if (x == -1 || y == -1)
			frame.setLocationRelativeTo(null);
		else
			frame.setLocation(x, y);
		
		frame.pack();
		frame.setVisible(true);
		timer.start();
	}
	
	public void show(String text) {
		show("", text, -1, -1);
	}
	
	public void hide() {
		frame.setVisible(false);
		timer.stop();
	}
}
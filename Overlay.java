import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


public class Overlay {
	private final Color fg = new Color(255, 25, 0);
	private final Color bg = new Color(30, 30, 30, 80);
	private final Font font = new Font("Helvetica", Font.BOLD, 20);
	private JTextArea body;
	private JFrame frame;
	private Timer timer;
	
	public Overlay() {
		frame = new JFrame();
		frame.setBackground(bg);
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);

		Container pane = frame.getContentPane();
		pane.setLayout(new java.awt.BorderLayout());
		
		body = new JTextArea(2, 2);
		body.setBackground(bg);
		body.setBorder(new EmptyBorder(10, 10, 10, 10));
		body.setForeground(fg);
		body.setFont(font);
		
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
	
	public void show(String text, int x, int y) {
		hide();
		
		String[] n = text.split("\n");
		if (n.length <= 1)
			body.setRows(1);
		else
			body.setRows(n.length-1);
		
		body.setText(text);
		
		if (x == -1 || y == -1)
			frame.setLocationRelativeTo(null);
		else
			frame.setLocation(x, y);
		
		frame.pack();
		frame.setVisible(true);
		timer.start();
	}
	
	public void show(String text) {
		show(text, -1, -1);
	}
	
	public void hide() {
		frame.setVisible(false);
		timer.stop();
	}
}
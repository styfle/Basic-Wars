import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextLayout;

import javax.swing.JPanel;


public class MainMenuView extends JPanel {
	private static final long serialVersionUID = 3310239310608462441L;
	private Map[] maps;
	private Font headFont = new Font("DialogInput", Font.PLAIN, 65);
	private Font bodyFont = new Font("Dialog", Font.PLAIN, 25);
	public MainMenuView(Map[] mapSelection) {
		this.maps = mapSelection;
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				for (Map map : maps) {
					if (map.nameClicked(e.getPoint())) {
						System.out.println(map.getName() + " clicked");
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }

			@Override
			public void mousePressed(MouseEvent e) { }

			@Override
			public void mouseReleased(MouseEvent e) { }
			
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) { }

			@Override
			public void mouseMoved(MouseEvent e) {
				for (Map map : maps) {
					map.updateTitle(e.getPoint());
				}
				repaint();
			}
    		
    	});
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameBoardView.WIDTH, GameBoardView.HEIGHT);
		g.setColor(Color.RED);
		g.setFont(headFont);
		g.drawString(Main.GAME_NAME, 55, 60);
		
	}
    
    @Override
    public void paintChildren(Graphics graphics) { // clickable objects
    	Graphics2D g = (Graphics2D)graphics;
    	//g.setColor(new Color(0,150,0));
		g.setFont(bodyFont);
		int x = 300;
		int y = 100;
		for (int i=0; i<maps.length; i++) {
			Map map = maps[i];
			y += 60;
			g.setColor(map.getColor());
			TextLayout tl = new TextLayout(maps[i].getName(),bodyFont, g.getFontRenderContext());
			map.initTitle(tl, x, y, g);
			//tl.draw(g, x, y);
			//Rectangle2D rect = tl.getBounds(); 
			//rect.setRect(rect.getX() + x, rect.getY() + y,  rect.getWidth(), rect.getHeight());
			//g.draw(rect);
		}
    }
}

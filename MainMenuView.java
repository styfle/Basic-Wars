import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class MainMenuView extends JPanel {
	private static final long serialVersionUID = 3310239310608462441L;
	private GameMap[] maps;
	private Font headFont = new Font("DialogInput", Font.PLAIN, 65);
	private Font bodyFont = new Font("Dialog", Font.PLAIN, 25);
	private BufferedImage mapImage = new BufferedImage(200,200, BufferedImage.TYPE_INT_ARGB);
	
	/**
	 * This JPanel is a map selector
	 * @param mapSelection array of maps to select from
	 */
	public MainMenuView(GameMap[] mapSelection) {
		this.setBackground(Color.BLACK);
		this.maps = mapSelection;
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (GameMap map : maps) {
					if (map.nameClicked(e.getPoint())) {
						//gp.load(map);
						((BasicWars)getParent()).load(map);
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
				for (GameMap map : maps) {
					map.updateTitle(e.getPoint());
					if (map.nameClicked(e.getPoint())) {
						setCursor(new Cursor(Cursor.HAND_CURSOR));
						mapImage = map.getImage();
						break;
					}
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
		g.fillRect(0, 0, GameMapView.WIDTH, GameMapView.HEIGHT);
		g.setColor(Color.RED);
		g.setFont(headFont);
		g.drawString(BasicWars.GAME_NAME, 55, 60);
		
	}
    
    @Override
    public void paintChildren(Graphics graphics) { // clickable objects
    	Graphics2D g = (Graphics2D)graphics;
    	//g.setColor(new Color(0,150,0));
		g.setFont(bodyFont);
		int x = 300;
		int y = 100;
		for (GameMap map : maps) {
			y += bodyFont.getSize()*2;
			g.setColor(map.getColor());
			TextLayout tl = new TextLayout(map.getName(),bodyFont, g.getFontRenderContext());
			map.initTitle(tl, x, y, g);
			//Rectangle2D rect = tl.getBounds(); 
			//rect.setRect(rect.getX() + x, rect.getY() + y,  rect.getWidth(), rect.getHeight());
			//g.draw(rect);
		}
		Image image;
		int newWidth = GameMapView.WIDTH/3;
		int newHeight = GameMapView.HEIGHT/3;
		if (mapImage != null) {
			image = mapImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		} else {
			image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics imgGraphics = image.getGraphics();
			imgGraphics.setColor(new Color(62,64,66));
			int startPos = 30;
			Cell c = new Cell(0,0,Cell.Type.EARTH);
			for (int i=0; i<7; i++) {
				c = new Cell(startPos + i*(20),newHeight/2,Cell.Type.EARTH);
				c.paintCell(imgGraphics);
			}
		}
		g.drawImage(image, 50, 100, null);
    }
    
}

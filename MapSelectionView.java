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
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.*;


/**
 * This view allows the {@link Player} to select a {@link GameMap} to play on
 */
public class MapSelectionView extends JPanel {
	private static final long serialVersionUID = 3310239310608462441L;
	private ArrayList<Clickable> clickables = new ArrayList<Clickable>();
	private Font headFont = new Font("DialogInput", Font.PLAIN, 65);
	private Font bodyFont = new Font("Dialog", Font.PLAIN, 25);
	private BufferedImage mapImage;
	private boolean firstPaint = true;
	
	/**
	 * This JPanel is a map selector
	 * @param mapSelection array of maps to select from
	 */
	public MapSelectionView(GameMap[] mapSelection) {
		this.setBackground(Color.BLACK);
		
		clickables.add (new Clickable() {
			private BufferedImage image = createImage(); 
			private BufferedImage createImage() {// hack to call super.setImage()
				BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
				Graphics imgGraphics = image.getGraphics();
				imgGraphics.setColor(new Color(62,64,66));
				int startPos = 30;
				Cell c = new Cell(0,0,Cell.Type.EARTH);
				for (int i=0; i<7; i++) {
					c = new Cell(startPos + i*(20),100,Cell.Type.EARTH);
					c.paintCell(imgGraphics);
				}
				this.image = image;
				setImage(this.image); // hack to remove warning
				return image;
			}
			
			@Override
			public String getName() { return "Load Custom Map"; }
			@Override
			public void onClick(BasicWars o) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Basic Wars Maps (*.bw)","bw"));
				int a = fc.showOpenDialog(o);
				if (a == JFileChooser.APPROVE_OPTION) {
					try {
						GameMap map = new GameMap(fc.getSelectedFile());
						setName(map.getName());
						for (Clickable c : clickables) {
							if (c.getName().equals(map.getName())) {
								System.out.println("Overwriting " + map.getName());
								clickables.remove(c);
								break;
							}							
						}
						clickables.add(map);
						//o.loadMap(map);
					} catch (Exception e) {
						o.showError(1, "The file you selected could not be loaded:\n" + e.getMessage());
					}
				}
			}
		});
		for (Clickable c : mapSelection) {
			clickables.add(c);
		}

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (Clickable c : clickables) {
					if (c.contains(e.getPoint())) {
						c.onClick((BasicWars)getParent());
						break;
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
				for (Clickable c : clickables) {
					c.mouseMoved(e.getPoint());
					if (c.contains(e.getPoint())) {
						setCursor(new Cursor(Cursor.HAND_CURSOR));
						mapImage = c.getImage();
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
		final int x = 300;
		int y = 100;
		for (Clickable c : clickables) {
			y += bodyFont.getSize()*2;
			g.setColor(c.getColor());
			if (this.firstPaint) {
				TextLayout tl = new TextLayout("[" + c.getName() + "]", bodyFont, g.getFontRenderContext());
				c.initClickable(tl, x, y, g);
			}
			c.draw();
			//Rectangle2D rect = tl.getBounds(); 
			//rect.setRect(rect.getX() + x, rect.getY() + y,  rect.getWidth(), rect.getHeight());
			//g.draw(rect);
		}
		
		Image image = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);
		int newWidth = GameMapView.WIDTH/3;
		int newHeight = GameMapView.HEIGHT/3;
		if (mapImage != null) {
			image = mapImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		} 
		/* No image available
		else { 
			image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics imgGraphics = image.getGraphics();
			imgGraphics.setColor(new Color(62,64,66));
			int startPos = 30;
			Cell c = new Cell(0,0,Cell.Type.EARTH);
			for (int i=0; i<7; i++) {
				c = new Cell(startPos + i*(20),newHeight/2,Cell.Type.EARTH);
				c.paintCell(imgGraphics);
			}
		} */
		g.drawImage(image, 50, 100, null);
    }
    
}

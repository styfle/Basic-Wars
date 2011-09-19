import java.awt.Cursor;
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

import javax.swing.JPanel;


/**
 * This class is a menu in the game that allows
 * the user to select game settings.
 */
public abstract class Menu extends JPanel {
	private static final long serialVersionUID = -7203212514059859323L;
	protected static final int IMAGE_WIDTH = 325;
	protected static final int IMAGE_HEIGHT = IMAGE_WIDTH;
	private static final int Y_TOP = 175;
	private static final Cursor CURSOR_DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);
	private static final Cursor CURSOR_HAND = new Cursor(Cursor.HAND_CURSOR);
	private final String title;
	private static final int yPosHead = 110;
	private final int xPosHead;
	private final int xPosChild = 385;
	private int yPosChild = Y_TOP;
	protected ArrayList<Clickable> clickables = new ArrayList<Clickable>();
	protected ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	protected Image image;
	
	private boolean firstPaint = true;

	public Menu(String menuTitle) throws Exception {
		super(true); //double buffered
		
		title = menuTitle;
		xPosHead = 250 - title.length()*15;
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				for (Clickable c : clickables)
					if (c.contains(e.getPoint())) {
						c.onClick((BasicWars)getParent());
						break;
					}
			}
			@Override
			public void mouseEntered(MouseEvent e) { }
			@Override
			public void mouseExited(MouseEvent e) { }
			@Override
			public void mouseClicked(MouseEvent e) { }
			@Override
			public void mouseReleased(MouseEvent e) { }
			
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) { }

			@Override
			public void mouseMoved(MouseEvent e) {
				Cursor cursor = CURSOR_DEFAULT;
				boolean isHovering = false;
				for (Clickable c : clickables) {
					if (c.contains(e.getPoint())) {
						cursor = CURSOR_HAND;
						image = c.getImage();
						c.startHover();
						isHovering = true;
					} else {
						c.stopHover();
					}
				}
				if (!isHovering)
					image = null; // only show image when hovering
				setCursor(cursor);
				repaint();
			}
    		
    	});
		
	}
	
	
	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //shapes
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //text
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); //quality
		g.setColor(BasicWars.BG_COLOR);
		g.fillRect(0, 0, GameMapView.WIDTH, GameMapView.HEIGHT);
		g.setColor(BasicWars.HEAD_COLOR);
		g.setFont(BasicWars.HEAD_FONT);
		g.drawString(title, xPosHead, yPosHead);
	}
	
	@Override
	public void paintChildren(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //shapes
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //text
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); //quality
		for (Drawable d : drawables) {
			d.draw(g);
		}
		yPosChild = Y_TOP;
		for (Clickable c : clickables) {
			yPosChild += BasicWars.BODY_FONT.getSize()*2;
			g.setColor(c.getColor());
			if (firstPaint) {
				TextLayout tl = new TextLayout("[" + c.getName() + "]", BasicWars.BODY_FONT, g.getFontRenderContext());
				c.init(tl, xPosChild, yPosChild, g);
			}
			c.draw();
			//Rectangle2D rect = tl.getBounds(); 
			//rect.setRect(rect.getX() + x, rect.getY() + y,  rect.getWidth(), rect.getHeight());
			//g.draw(rect);
		}
		Image i;
		if (image == null)
			i = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		else
			i = image.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
		g.drawImage(i, 35, Y_TOP, null);
		g.dispose();
	}
}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFileChooser;
import javax.swing.filechooser.*;


/**
 * This view allows the {@link Player} to select a {@link GameMap} to play on
 */
public class MapSelectView extends Menu {
	private static final long serialVersionUID = 3310239310608462441L;
	
	/**
	 * This JPanel is a map selector
	 * @param mapSelection array of maps to select from
	 */
	public MapSelectView(GameMap[] mapSelection) {
		super("Map Select", 60);
		
		BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		Graphics imgGraphics = image.getGraphics();
		imgGraphics.setColor(new Color(62,64,66));
		int startPos = 30;
		Cell cell = new Cell(0,0,Cell.Type.EARTH);
		for (int i=0; i<7; i++) {
			cell = new Cell(startPos + i*(20),100,Cell.Type.EARTH);
			cell.paintCell(imgGraphics);
		}
		
		clickables.add (new Clickable("Load Custom Map", true, image, new Color(190, 170, 100)) {
			@Override
			public void onClick(BasicWars o) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Basic Wars Maps (*.bw)","bw"));
				int a = fc.showOpenDialog(o);
				if (a == JFileChooser.APPROVE_OPTION) {
					try {
						GameMap map = new GameMap(fc.getSelectedFile());
						for (Clickable c : clickables) {
							if (c.getName().equals(map.getName())) {
								System.out.println("Reloading " + map.getName());
								clickables.remove(c);
								break;
							}							
						}
						clickables.add(map);
					} catch (Exception e) {
						o.showError(7, "The file you selected could not be loaded:\n" + e.getMessage());
					}
				}
			}
		});
		
		for (Clickable c : mapSelection) {
			clickables.add(c);
		}
	}
	
    public void paintBackup(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		final int x = 300;
		int y = 100;
		for (Clickable c : clickables) {
			y += BasicWars.BODY_FONT.getSize()*2;
			g.setColor(c.getColor());
			//if (firstPaint) {
				TextLayout tl = new TextLayout("[" + c.getName() + "]", BasicWars.BODY_FONT, g.getFontRenderContext());
				c.init(tl, x, y, g);
			//}
			c.draw();
			//Rectangle2D rect = tl.getBounds(); 
			//rect.setRect(rect.getX() + x, rect.getY() + y,  rect.getWidth(), rect.getHeight());
			//g.draw(rect);
		}
		
		Image i;
		int newWidth = GameMapView.WIDTH/3;
		int newHeight = GameMapView.HEIGHT/3;
		if (image != null) {
			i = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		} else {
			i = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
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
		g.drawImage(i, 50, 100, null);
    }

}

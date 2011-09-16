import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * This is for debugging purposes and not to be included in release code.
 * Sprites are useful because multiple images can be read with one I/O
 */
public class SpriteTest extends JPanel {
	private static final long serialVersionUID = -7736204537894930600L;
	private static BufferedImage img;
	private int DIST_X = 31;
	private int DIST_Y = 31;

	public SpriteTest() {
		super(true);
		setOpaque(true);
	}

	public static void createAndShowGUI() {
        JFrame frame = new JFrame("Sprite Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent panel = new SpriteTest();
        panel.setOpaque(true); //content panes must be opaque
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(1000, 125));

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
	
	@Override
	public void paintComponent(Graphics g) {
		//g.setColor(Color.black);
		//g.fillRect(0, 0, 1000, 1000);
		int x = 7;
		int y = 1570;//68
		int w = 30;
		int h = 30;
		for (int a=0; a<10; a++) {
			BufferedImage i = img.getSubimage(x+(a*DIST_X), y, w, h);
			g.drawImage(i, 0 + a*100, 0, w*2, h*2, null);
		}
		g.dispose();
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			img = makeColorTransparent(
					ImageIO.read(new File("images/sprite-sheet.png")),
					new Color(192,192,192)
				);
		} 
		catch (UnsupportedLookAndFeelException e) { }
		catch (ClassNotFoundException e) { }
		catch (InstantiationException e) { }
		catch (IllegalAccessException e) { }
		catch (IOException e) { System.out.println("IOException!");}
		
		

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SpriteTest.createAndShowGUI();
			}
        });
	}
	
	private static BufferedImage makeColorTransparent(BufferedImage image, Color color) {
		final int markerRGB = color.getRGB() | 0xFF000000;
		ImageProducer ip = new FilteredImageSource(image.getSource(), new RGBImageFilter() {
	    	public final int filterRGB(int x, int y, int rgb) {
	    		if ( ( rgb | 0xFF000000 ) == markerRGB ) {
	    			return 0x00FFFFFF & rgb;
	    		}
	    		return rgb;
	    	}
		});
		java.awt.Image img = Toolkit.getDefaultToolkit().createImage(ip);
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics g = bi.getGraphics();
	    g.drawImage(img, 0, 0, null);
		return bi;
	}

}

import java.awt.Color;


/**
 * Basic Wars is a turn-based strategy game written in Java.
 * It is loosely based on Advance Wars.
 * For use in the ICS Summer Of Code competition.
 * @author Steven Salat
 */
public class Main {
	public static final String GAME_NAME = "Basic Wars";
	public static final String GAME_VERSION = "0.1";
	
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GamePanel.createAndShowGUI();
            }
        });
		
	}
	
	/**
	  * Lightens a color by a given amount
	  * 
	  * @param color The color to lighten
	  * @param amount The amount to lighten the color. 0 is unchanged; 1 is completely white
	  * @return The bleached color
	  */
	  public static Color bleach(Color color, float amount)
	  {
	    int red = (int) ((color.getRed() * (1 - amount) / 255 + amount) * 255);
	    int green = (int) ((color.getGreen() * (1 - amount) / 255 + amount) * 255);
	    int blue = (int) ((color.getBlue() * (1 - amount) / 255 + amount) * 255);
	    return new Color(red, green, blue);
	  }
}

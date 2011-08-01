
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
}

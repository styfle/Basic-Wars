
public class Main {
	public static final String GAME_NAME = "Basic Wars";
	
	public static void main(String[] args) {		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GamePanel.createAndShowGUI();
            }
        });
		
		
	}
}

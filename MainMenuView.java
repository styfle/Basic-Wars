import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**
 * This is the very first view the user will see.
 * It should have cool pictures and sounds!
 */
public class MainMenuView extends Menu {
	private static final long serialVersionUID = -9178274830210260069L;
	private Clickable newGame;
	private Clickable resumeEnable;
	private Clickable resumeDisable;
	private Clickable instructions;
	private Clickable about;
	private Clickable exit;
	
	public MainMenuView() {
		super(BasicWars.GAME_NAME);
		
		BufferedImage i = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = i.getGraphics();
		Cell c = new Cell(40, 70, Cell.Type.EARTH);
		c.paintCell(g);
		for (int j=0; j<4; j++) {
			c = c.generateNorthEast(Cell.Type.WATER);
			c.paintCell(g);
			c = c.generateSouthEast(Cell.Type.EARTH);
			c.paintCell(g);
			c = c.generateSouth(Cell.Type.SWAMP);
			c.paintCell(g);
		}
		c = c.generateNorthEast(Cell.Type.EARTH);
		c.paintCell(g);
		
		newGame = new Clickable("New Game", true, i, null) {
			@Override
			public void onClick(BasicWars o) {
				o.startNewGame();
			}
		};
		
		resumeEnable = new Clickable("Resume Game") {
			@Override
			public void onClick(BasicWars o) {
				o.resumeGame();
			}
		};
		
		resumeDisable = new Clickable("Resume Game", false, null, null) {
			@Override
			public void onClick(BasicWars o) {
				o.resumeGame();
			}
		};
		
		instructions = new Clickable("Instructions", false, null, null) {
			@Override
			public void onClick(BasicWars o) {
				o.showMessage("Sorry, no instructions yet. Coming soon!");
			}
		};
		
		i = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		g = i.getGraphics();
		g.setColor(BasicWars.TEXT_COLOR);
		g.setFont(BasicWars.BOLD_FONT);
		g.drawString("Hello", 85, 120);
		
		about = new Clickable("About", true, i, null) {
			@Override
			public void onClick(BasicWars o) {
				o.loadAbout();
			}
		 };
		
		i = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		g = i.getGraphics();
		g.setColor(BasicWars.HEAD_COLOR);
		g.setFont(BasicWars.BOLD_FONT);
		g.drawString("Goodbye", 65, 120);
		
		exit = new Clickable("Exit", true, i , BasicWars.HEAD_COLOR) {
			@Override
			public void onClick(BasicWars o) {
				System.exit(0);
			}
		};
		 
		setResumable(false); // build main menu with resume disabled
	}
	
	public void setResumable(boolean isResumable) {
		clickables.clear();
		Clickable resume = (isResumable) ? resumeEnable : resumeDisable;
		clickables.add(newGame);
		clickables.add(resume);
		clickables.add(instructions);
		clickables.add(about);
		clickables.add(exit);
	}
}

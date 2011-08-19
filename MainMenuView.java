import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**
 * This is the very first view the user will see.
 * It should have cool pictures and sounds!
 */
public class MainMenuView extends Menu {
	private static final long serialVersionUID = -9178274830210260069L;
	
	public MainMenuView() {
		super(BasicWars.GAME_NAME, 60);
		
		final BufferedImage i = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = i.getGraphics();
		Cell c = new Cell(70, 40, Cell.Type.EARTH);
		c.paintCell(g);
		for (int j=0; j<3; j++) {
			c = c.generateNorthEast(Cell.Type.WATER);
			c.paintCell(g);
			c = c.generateSouthEast(Cell.Type.LAVA);
			c.paintCell(g);
			c = c.generateSouth(Cell.Type.TREE);
			c.paintCell(g);
		}
		c = c.generateNorthEast(Cell.Type.EARTH);
		c.paintCell(g);
		//image = i.getScaledInstance(i.getWidth(null)*3, i.getHeight(null)*3, Image.SCALE_SMOOTH);
		image = i;
		//TODO add separate image for each option
		clickables.add(new Clickable("Start Game", true, i, null) {
			@Override
			public void onClick(BasicWars o) {
				System.out.println("Start clicked.");
				o.loadPlayerMenu();
			}
		});
		
		clickables.add(new Clickable("Instructions", false, null, null) {
			@Override
			public void onClick(BasicWars o) {
				System.out.println("Instructions clicked.");
				o.showError(101, "Unimplemented: Instructions");
			}
		});
		
		clickables.add(new Clickable("About", true, i, null) {
			@Override
			public void onClick(BasicWars o) {
				System.out.println("About clicked.");
				o.loadAbout();
			}
		 });
		 
		 
	}
}

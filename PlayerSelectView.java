import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**
 * This view allows the use to select how many people
 * will be playing the game. *Currently only 2 players*
 */
public class PlayerSelectView extends Menu {
	private static final long serialVersionUID = -1367393635036121396L;

	public PlayerSelectView() {
		super("Player Select");
		
		Clickable onePlayer = new Clickable("Single Player", false, null, null) {
			@Override
			public void onClick(BasicWars o) {
				o.showMessage("I regret to inform you that Single Player is not implemented.");
			}
		};
		
		BufferedImage i = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = i.getGraphics();
		g.setColor(Color.BLUE);
		g.fillOval(0, IMAGE_HEIGHT/3, IMAGE_WIDTH/2, IMAGE_HEIGHT/2);
		g.fillOval(IMAGE_WIDTH/2, IMAGE_HEIGHT/3, IMAGE_WIDTH/2, IMAGE_HEIGHT/2);
		g.setColor(Color.WHITE);
		g.drawOval(0, IMAGE_HEIGHT/3, IMAGE_WIDTH/2, IMAGE_HEIGHT/2);
		g.drawOval(IMAGE_WIDTH/2, IMAGE_HEIGHT/3, IMAGE_WIDTH/2, IMAGE_HEIGHT/2);
		
		Clickable twoPlayer = new Clickable("Two Player", true, i, null) {
			@Override
			public void onClick(BasicWars o) {
				o.setPlayers(2);
				o.loadMapMenu();
			}
		};
		
		clickables.add(onePlayer);
		clickables.add(twoPlayer);
	}
	
}

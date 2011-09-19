import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.util.ArrayList;



/**
 * A panel with instructions on how to play the game.
 * This can also be found on https://github.com/styfle/Basic-Wars/blob/master/readme.md
 */
public class InstructionView extends Menu {
	private static final long serialVersionUID = 6893902772033313258L;
	private Font font = new Font("Courier New", Font.PLAIN, 17);
	private final int Y_TOP = 150;
	private int y = Y_TOP;
	private final int LINE_LENGTH = 47;
	private ArrayList<String> text;
	//private boolean firstPaint = true;

	public InstructionView() {
		super("Instructions");
		text = BasicWars.toList("Basic Wars is very simple to play, but hard to master."
				+ " A player can move a unit and attack with a unit during their turn. Then their"
				+ " turn ends and the next player does the same. If you can't attack anything,"
				+ " just skip your turn or else the other player won't be able to go! Think of"
				+ " it like Chess. Move one piece at a time, repeat until the winner is chosen."
				+ " Click to select a unit and right click to perform actions with that unit."
				+ " A unit's type affect which cells it can move on or over. View the ReadMe"
				+ " for a detailed table about unit types and cell types."
				, LINE_LENGTH);
		text.add("I'll see you on the battlefield!");
		if (Desktop.isDesktopSupported()) {
			final Desktop desktop = Desktop.getDesktop();
			clickables.add(new Clickable("View ReadMe Online") {
				@Override
				public void onClick(BasicWars o) {
					try {
						desktop.browse(new java.net.URI("https://github.com/styfle/Basic-Wars/blob/master/readme.md"));
					} catch (Exception e) {
						o.showError(808, e.getMessage());
					}
				}
				
			});
		}
	}
	
	@Override
	public void paintChildren(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(BasicWars.TEXT_COLOR);
		g.setFont(font);
		y = Y_TOP;
		for (String s : text) {
			g.drawString(s, 80, y); // change x position if needed
			y += 30;
		}
		for (Clickable c : clickables) {
			TextLayout layout = new TextLayout("[" + c.getName() + "]", BasicWars.BODY_FONT, g.getFontRenderContext());
			c.init(layout, 100, 530, g);
			g.setColor(Color.BLUE);
			c.draw();
		}
	}
	
}
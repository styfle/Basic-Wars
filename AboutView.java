import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;


public class AboutView extends Menu {
	private static final long serialVersionUID = -4543847690002129914L;
	private Font font = new Font("Courier New", Font.PLAIN, 17);
	private final int Y_TOP = 130;
	private int y = Y_TOP;
	private final int LINE_LENGTH = 46;
	private ArrayList<String> text;

	public AboutView() {
		super("About");
		text = toList("Basic Wars is a strategy game loosely based on Advance Wars,"
				+ " developed for the ICS Summer of Code competition at UC,"
				+ " Irvine. I could have developed anything, but I chose this"
				+ " simple game because it involves a lot of Computer Science"
				+ " know-how such as Graph Theory, Inheritance, 2D Graphics, etc."
				+ " This game also has the potential to add online multiplayer"
				+ " and online games introduce a lot of fun/new features/problems."
				+ " I have never made a full-featured game before so I thought a"
				+ " modern war game (modern if it was made in 1990) would be a "
				+ " good place to start. I hope you enjoy it!"
				, LINE_LENGTH);
		text.add("-Steven Salat");
	}
	
	/**
	 * Turns a String into an ArrayList of Strings
	 * @param s String to convert
	 * @param maxLength Max length of the line before wrapping
	 * @return ArrayList of Strings, where each String represents a line of text
	 */
	private ArrayList<String> toList(String s, int maxLength) {
		ArrayList<String> output = new ArrayList<String>();
		String[] words = s.split(" ");
		StringBuilder line = new StringBuilder(maxLength);
		for (String word : words) {
			if (line.length() > maxLength) {
				output.add(line.toString());
				line = new StringBuilder(maxLength);
			}
			line.append(word).append(' ');
		}
		output.add(line.toString());
		return output;
	}
	
	@Override
	public void paintChildren(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(BasicWars.TEXT_COLOR);
		g.setFont(font);
		y = Y_TOP;
		for (String s : text) {
			g.drawString(s, 58, y);
			y += 30;
		}
	}

}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

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
	public MapSelectView(GameMap[] defaultMaps, ArrayList<File> userDefinedMaps) throws Exception {
		super("Map Select");
		
		BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		Graphics imgGraphics = image.getGraphics();
		imgGraphics.setColor(new Color(62,64,66));
		int startPos = 30;
		Cell cell = new Cell(0,0,Cell.Type.EARTH);
		for (int i=0; i<7; i++) {
			cell = new Cell(startPos + i*(20),100,Cell.Type.EARTH);
			cell.paintCell(imgGraphics);
		}
		
		clickables.add(new Clickable("Load Custom Map", true, image, new Color(190, 170, 100)) {
			@Override
			public void onClick(BasicWars o) {
				JFileChooser fc = new JFileChooser(new java.io.File(".")); // start in cd
				fc.setFileFilter(new FileNameExtensionFilter("Basic Wars Maps (*.bw)","bw"));
				int a = fc.showOpenDialog(o);
				if (a == JFileChooser.APPROVE_OPTION) {
					try {
						addMapFromFile(fc.getSelectedFile());
					} catch (Exception e) {
						o.showError(714, "The file you selected could not be loaded!", e.getMessage());
					}
				}
			}
		});
		
		for (Clickable c : defaultMaps) {
			clickables.add(c);
		}
		
		for (File userDefined : userDefinedMaps) {
			try {
				addMapFromFile(userDefined);
			} catch (Exception e) {
				// fail silently
				// user can get error when loading map manually
			}
		}
	}
	
	private void addMapFromFile(File f) throws Exception {
		GameMap map = new GameMap(f);
		for (Clickable c : clickables) {
			if (c.getName().equals(map.getName())) {
				System.out.println("Reloading " + map.getName());
				clickables.remove(c);
				break;
			}							
		}
		clickables.add(map);
	}

}

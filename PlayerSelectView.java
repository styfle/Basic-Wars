
/**
 * This view allows the use to select how many people
 * will be playing the game. *Currently only 2 players*
 */
public class PlayerSelectView extends Menu {
	private static final long serialVersionUID = -1367393635036121396L;

	public PlayerSelectView() {
		super("Player Select", 25);
		//TODO add images to each clickable
		Clickable onePlayer = new Clickable("Single Player", false, null, null) {
			@Override
			public void onClick(BasicWars o) {
				o.showMessage("I regret to inform you that Single Player is not implemented.");
			}
		};
		//onePlayer.COLOR_HOVER = onePlayer.COLOR_NORMAL;
		
		Clickable twoPlayer = new Clickable("Two Player") {
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class UnitSelectView extends Menu {
	private static final long serialVersionUID = 1115867647136193750L;
	private Player player;
	private Drawable money;
	
	public UnitSelectView(Player p) {
		super("Unit Select P" + p.getNumber());
		this.player = p;
		Unit soldier = new Unit(Unit.Type.SOLDIER, player);
		Unit tank = new Unit(Unit.Type.TANK , player);
		Unit plane = new Unit(Unit.Type.PLANE, player);
		
		money = new Drawable("$0", 200, 535);
		drawables.add(money);
		
		clickables.add(new Clickable(buildString('s'), true, soldier.getImage(), null) {
			@Override
			public void onClick(BasicWars o) {
				boolean success = player.buySoldier();
				if (!success) {
					o.showStatus("You don't have enough money to buy a soldier!");
				} else {
					o.showStatus("Player " + player.getNumber() + " bought a soldier for $"+Player.PRICE_SOLDIER+"!");
					setName(buildString('s'));
				}
				repaint();
			}
		});
		
		clickables.add(new Clickable(buildString('t'), true, tank.getImage(), null) {
			@Override
			public void onClick(BasicWars o) {
				boolean success = player.buyTank();
				if (!success) {
					o.showStatus("You don't have enough money to buy a tank!");
				} else {
					o.showStatus("Player " + player.getNumber() + " bought a tank for $"+ Player.PRICE_TANK +"!");
					setName(buildString('t'));
				}
				repaint();
			}
		});
		
		clickables.add(new Clickable(buildString('p'), true, plane.getImage(), null) {
			@Override
			public void onClick(BasicWars o) {
				boolean success = player.buyPlane();
				if (!success) {
					o.showStatus("You don't have enough money to buy a plane!");
				} else {
					o.showStatus("Player " + player.getNumber() + " bought a plane for $"+Player.PRICE_PLANE+"!");
					setName(buildString('p'));
				}
				repaint();
			}
		});
		
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setColor(BasicWars.TEXT_COLOR);
		g.setFont(BasicWars.BOLD_FONT);
		int MAX_PLAYERS = 2; // should probably get this from BasicWars.players
		String done = (player.getNumber() == MAX_PLAYERS) ?
					"Start Game!" :
					"Player " + (player.getNumber()+1);
		g.drawString(done, 90, 100);
		
		clickables.add(new Clickable("Done", true, image, new Color(0,255,100)) {
			@Override
			public void onClick(BasicWars o) {
				if (player.sizeOfUnits() == 0)
					o.showMessage("Hey, you didn't buy any units! How can you even play?");
				else
					o.loadUnitMenu();
			}
		});
		
	}
	
	private String buildString(char c) {
		money.setText("Player "+player.getNumber()+" has $" + player.moneyRemaining());
		switch (c) {
			case 's':
				return "Buy Soldier for $"+Player.PRICE_SOLDIER+" (" + player.sizeOfSoldiers() + ")";
			case 't':
				return "Buy Tank for $"+Player.PRICE_TANK+" (" + player.sizeOfTanks() + ")";
			case 'p':
				return "Buy Plane for $"+Player.PRICE_PLANE+" (" + player.sizeOfPlanes() + ")";
		}
		return "Error: Unknown Unit!";
	}
}
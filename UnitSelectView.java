import java.awt.Color;
import java.awt.image.BufferedImage;


public class UnitSelectView extends Menu {
	private static final long serialVersionUID = 1115867647136193750L;
	private Player player;
	private Clickable money;
	
	public UnitSelectView(Player p) {
		super("Unit Select P" + p.getNumber(), 0);
		this.player = p;
		Soldier soldier = new Soldier(player);
		Tank tank = new Tank(player);
		Plane plane = new Plane(player);
		
		money = new Clickable("money", true, new BufferedImage(200,200,2), Color.WHITE) {
			@Override
			public void onClick(BasicWars o) {
				o.showError(0, "Each player begins with the same ammount of money.");
			}
		};
		
		clickables.add(money);
		
		clickables.add(new Clickable(buildString('s'), true, soldier.getImage(), null) {
			@Override
			public void onClick(BasicWars o) {
				boolean success = player.buySoldier();
				if (!success) {
					o.setStatus("You don't have enough money to buy a soldier!");
				} else {
					o.setStatus("Player " + player.getNumber() + " bought a soldier!");
					this.name = buildString('s');
				}
				repaint();
			}
		});
		
		clickables.add(new Clickable(buildString('t'), true, tank.getImage(), null) {
			@Override
			public void onClick(BasicWars o) {
				boolean success = player.buyTank();
				if (!success) {
					o.setStatus("You don't have enough money to buy a tank!");
				} else {
					o.setStatus("Player " + player.getNumber() + " bought a tank!");
					this.name = buildString('t');
				}
				repaint();
			}
		});
		
		clickables.add(new Clickable(buildString('p'), true, plane.getImage(), null) {
			@Override
			public void onClick(BasicWars o) {
				boolean success = player.buyPlane();
				if (!success) {
					o.setStatus("You don't have enough money to buy a plane!");
				} else {
					o.setStatus("Player " + player.getNumber() + " bought a plane!");
					this.name = buildString('p');
				}
				repaint();
			}
		});
		
		clickables.add(new Clickable("Done", true, new BufferedImage(200, 200, 1), new Color(0,255,100)) {
			@Override
			public void onClick(BasicWars o) {
				o.loadUnitMenu();
			}
		});
		
	}
	
	private String buildString(char c) {
		money.name = "Player "+player.getNumber()+" has $" + player.moneyRemaining();
		switch (c) {
		case 's': return "Buy Soldier for $"+Player.PRICE_SOLDIER+" (" + player.sizeOfSoldiers() + ")";
		case 't': return "Buy Tank for $"+Player.PRICE_TANK+" (" + player.sizeOfTanks() + ")";
		case 'p': return "Buy Plane for $"+Player.PRICE_PLANE+" (" + player.sizeOfPlanes() + ")";
		}
		return "Error: Unknown Unit!";
	}
}
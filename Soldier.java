
public class Soldier extends Unit {
	
	public Soldier(Player owner) {		
		super(owner, 100,
				(owner.getSide() == Player.LEFT_SIDE)
					? "images/soldier1_left.png"
					: "images/soldier1_right.png"
		);
	}
}

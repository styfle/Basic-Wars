
public class Player {
	public static final byte LEFT_SIDE = 0;
	public static final byte RIGHT_SIDE = 1;
	private final String name;
	private final byte side;
	
	public Player(String name, byte side) {
		this.name = name;
		this.side = side;
	}
	
	public String getName() { return name; }
	public byte getSide() { return side; }
}

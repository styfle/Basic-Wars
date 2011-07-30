import java.awt.Point;


public class GameBoardModel {
	private Unit[][] board;
	
	public GameBoardModel(int width, int height) {
		board = new Unit[width][height];
		for (int i=0; i<width; i++)
			for (int j=0; j<height; j++)
				board[i][j] = null;
	}
	
	public boolean isEmpty(Point p) {
		return board[p.x][p.y] == null;
	}
	
	public void clearCell(Point p) {
		board[p.x][p.y] = null;
	}
	
	public void putUnitAt(Unit u, Point p) {
		board[p.x][p.y] = u;
	}
	
	public Unit getUnitAt(Point p) {
		return board[p.x][p.y];
	}
	
}

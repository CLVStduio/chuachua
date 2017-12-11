package cn.clvstudio.game.chuachua.model.game;

/**
 * 点的位置
 * @author Darnell
 *
 */
public class Point {
	private double x;
	private double y;
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public synchronized void getActual(double x,double y){
		this.x += x;
		this.y += y;
	}
	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
	
}

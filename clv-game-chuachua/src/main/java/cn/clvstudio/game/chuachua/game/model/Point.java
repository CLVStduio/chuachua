package cn.clvstudio.game.chuachua.game.model;

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
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public void getActual(double x,double y){
		this.x += x;
		this.y += y;
	}
	
}
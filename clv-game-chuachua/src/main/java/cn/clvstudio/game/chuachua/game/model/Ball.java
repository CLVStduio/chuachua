package cn.clvstudio.game.chuachua.game.model;

import java.util.List;
import java.util.Vector;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;

/**
 * 球
 * @author Darnell
 *
 */
public class Ball {
	/**圆心坐标*/
	private double CenterX;
	/**圆心坐标*/
	private double CenterY;
	/**直径*/
	private final static double diameter = GameParameter.BALL_DIAMETER;
	/**半径*/
	private static double radius;
	/**速度*/
	private int seep ;
	private double seepx;
	private double seepy;
	/**运动方向与x轴的夹角*/
	private double movementAngle;
	/** 球的几个关键点 */
	private static final List<Point> points = new Vector<Point>();
	/** 球的几个关键实际点 */
	private List<Point> actualPoints = new Vector<Point>();
	
	static {
		radius = diameter/2;
		double temp = radius/Math.sqrt(2);
		//最高点 0
		points.add(new Point(0,-radius));
		//右上点 1
		points.add(new Point(temp,-temp));
		//最右点 2
		points.add(new Point(radius,0));
		//右下点 3
		points.add(new Point(temp,temp));
		//最低点 4
		points.add(new Point(0,radius));
		//左下点 5
		points.add(new Point(-temp,temp));
		//最左点 6
		points.add(new Point(-radius,0));
		//左上点 7
		points.add(new Point(-temp,-temp));
	}
	
	/**
	 * @param movementAngle 初始角度
	 * @param direction 初始方向（1和-1）
	 */
	public Ball(double movementAngle,int direction){
		this.movementAngle = movementAngle;
		this.CenterX = 0;
		this.CenterY = GameParameter.INTERFACE_HEIGHT/2;
		this.seep=5;
		this.seepx = direction*seep*Math.cos(this.movementAngle*Math.PI/180);
		this.seepy = seep*Math.sin(this.movementAngle*Math.PI/180);
		this.actualPoints = points;
		getActualPoint();
	}
	
	/**
	 * 球的运动
	 * @return
	 */
	public void move(){
		this.CenterX += this.seepx;
		this.CenterY += this.seepy;
		getActualPoint();
	}
	
	public void getActualPoint(){
		this.actualPoints.forEach(point ->{
			point.getActual(this.CenterX, this.CenterY);
		});
	}

	/**
	 * 碰到物体改变运动轨迹及速度
	 * @param mode 碰撞模式
	 * @param obj 被碰撞的物体
	 */
	public void bounce(String mode,String obj){
		if(GameParameter.OBJ_BAN.equals(obj) && this.seep < GameParameter.MAX_SEEP){
				this.seep ++ ;
		}else if(GameParameter.OBJ_BRICK.equals(obj) && this.seep > GameParameter.MIN_SEEP){
			this.seep --;
		}
		this.seepx = this.seep*Math.cos(this.movementAngle*Math.PI/180);
		this.seepy = this.seep*Math.sin(this.movementAngle*Math.PI/180);
		if(GameParameter.UP_DOWN.equals(mode)){
			this.seepy *= -1;
			move();
			return;
		}
		if(GameParameter.LEFT_RIGHT.equals(mode)){
			this.seepx *= -1;
			move();
			return;
		}
		if(GameParameter.ANGLE.equals(mode)){
			if(this.movementAngle == 45){
				this.seepx *= -1;
				this.seepy *= -1;
				move();
				return;
			}
			this.seepx = this.seep*Math.sin(this.movementAngle*Math.PI/180);
			this.seepy = this.seep*Math.cos(this.movementAngle*Math.PI/180);
			move();
			return;
		}
	}

	public double getMovementAngle() {
		return movementAngle;
	}

	public void setMovementAngle(double movementAngle) {
		this.movementAngle = movementAngle;
	}

	public double getCenterX() {
		return CenterX;
	}

	public double getCenterY() {
		return CenterY;
	}

	public static double getRadius() {
		return radius;
	}

	public List<Point> getActualPoints() {
		return actualPoints;
	}
	
}






package cn.clvstudio.game.chuachua.model.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;

/**
 * 球
 * @author Darnell
 *
 */
public class Ball {
	private static final  Logger LOG = LoggerFactory.getLogger(Ball.class);
	
	/**圆心坐标*/
	private double CenterX;
	/**圆心坐标*/
	private double CenterY;
	/**半径*/
	private double radius ;
	/**速度*/
	private double seep ;
	private double seepx;
	private double seepy;
	private int seepxDirection;
	private int seepyDirection;
	/**运动方向与x轴的夹角*/
	private double movementAngle;
	
	/**
	 * @param movementAngle 初始角度
	 * @param direction 初始方向（1和-1）
	 */
	public Ball(double movementAngle,int direction){
		this.radius = GameParameter.BALL_RADIUS;
		this.movementAngle = movementAngle;
		this.CenterX = GameParameter.BALL_DIAMETER;
		this.CenterY = GameParameter.INTERFACE_HEIGHT/2;
		this.seep=GameParameter.INIT_SEEP;
		this.seepx = seep*Math.cos(this.movementAngle*Math.PI/180);
		this.seepy = seep*Math.sin(this.movementAngle*Math.PI/180);
		this.seepxDirection = 1;
		this.seepyDirection = direction;
		
	}
	
	/**
	 * 球的运动
	 * @return
	 */
	public synchronized void move(){
		this.CenterX += this.seepxDirection*this.seepx;
		this.CenterY += this.seepyDirection*this.seepy;
	}
	
	/**
	 * 碰到物体改变运动轨迹及速度
	 * @param mode 碰撞模式
	 * @param obj 被碰撞的物体
	 */
	public synchronized void bounce(String mode,String obj){
		LOG.debug("球被碰撞方式："+mode +" ；对象"+obj);
		if(GameParameter.OBJ_BAN.equals(obj) && this.seep < GameParameter.MAX_SEEP){
				this.seep ++ ;
		}else if(GameParameter.OBJ_BRICK.equals(obj) && this.seep > GameParameter.MIN_SEEP){
			this.seep --;
		}
		this.seepx = seep*Math.cos(this.movementAngle*Math.PI/180);
		this.seepy = seep*Math.sin(this.movementAngle*Math.PI/180);
		if(GameParameter.COLLISION_UP.equals(mode)){
			this.seepyDirection = -1;
			return;
		}
		if(GameParameter.COLLISION_DOWN.equals(mode)){
			this.seepyDirection = 1;
			return;
		}
		if(GameParameter.COLLISION_LEFT.equals(mode)){
			this.seepxDirection = -1;
			return;
		}
		if(GameParameter.COLLISION_RIGHT.equals(mode)){
			this.seepxDirection = 1;
			return;
		}
//		if(GameParameter.ANGLE.equals(mode)){
//			if(this.movementAngle == 45){
//				this.seepx *= -1;
//				this.seepy *= -1;
//				move();
//				return;
//			}
//			this.seepx = this.seep*Math.sin(this.movementAngle*Math.PI/180);
//			this.seepy = this.seep*Math.cos(this.movementAngle*Math.PI/180);
//			move();
//			return;
//		}
	}

	public double getMovementAngle() {
		return movementAngle;
	}

	public synchronized void setMovementAngle(double movementAngle) {
		this.movementAngle = movementAngle;
	}

	public double getCenterX() {
		return CenterX;
	}

	public double getCenterY() {
		return CenterY;
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Ball [CenterX=" + CenterX + ", CenterY=" + CenterY + "]";
	}
	
}






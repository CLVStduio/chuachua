package cn.clvstudio.game.chuachua.model.game;

import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;

/**
 * 板
 * 
 * @author Darnell
 *
 */
public class Ban {
	@SuppressWarnings("unused")
	private static final  Logger LOG = LoggerFactory.getLogger(Ban.class);
	public final int width = GameParameter.BAN_WIDTH;
	public final int height = GameParameter.BAN_HEIGHT;
	public double y;
	public double x;
	/** 板的几个关键实际点 */
	public List<Point> actualPoints ;
	static {
		
	}

	public Ban(double y) {
		this.y = y;
		this.x = (GameParameter.INTERACE_WIDTH - GameParameter.BAN_WIDTH) / 2;
		this.actualPoints = new Vector<Point>();
		if(y == GameParameter.BAN_TOP_A){
			actualPoints.add(new Point(13.2085, 0));
			// B 1
			actualPoints.add(new Point(208.6032, 0));
			// C 2
			actualPoints.add(new Point(220.6730, 11.3207));
			// D 3
			actualPoints.add(new Point(225, 24));
			// E 4
			actualPoints.add(new Point(222.0394, 37.1321));
			// F 5
			actualPoints.add(new Point(217.2571, 48));
			// H 6
			actualPoints.add(new Point(18.4463, 48));
			// I 7
			actualPoints.add(new Point(5.0101, 37.3584));
			// J 8
			actualPoints.add(new Point(0, 24));
			// K 9
			actualPoints.add(new Point(3.8714, 6.7925));
		}else{
			// H 0
			actualPoints.add(new Point(18.4463, 0));
			// F 1
			actualPoints.add(new Point(217.2571, 0));
			// E 2
			actualPoints.add(new Point(222.0394, 10.8679));
			// D 3
			actualPoints.add(new Point(225, 24));
			// C 4
			actualPoints.add(new Point(220.6730, 36.6793));
			// B 5
			actualPoints.add(new Point(208.6032, 48));
			// A 6
			actualPoints.add(new Point(13.2085, 48));
			// K 7
			actualPoints.add(new Point(3.8714, 41.2075));
			// J 8
			actualPoints.add(new Point(0, 24));
			// I 9
			actualPoints.add(new Point(5.0101, 10.6416));
		}
		getActualPoint(this.x, this.y);
	}

	public void getActualPoint(double x, double y) {
		this.actualPoints.forEach(point -> {
			point.getActual(x, y);
		});
	}

	/**
	 * 板的移动
	 * 
	 * @param disRatio
	 *            Displacement ratio 位移比
	 */
	public void move(double disRatio) {
		double x = disRatio * GameParameter.INTERACE_WIDTH;
		getActualPoint(x - this.x, 0);
		this.x = x;
	}

	/**
	 * 板是否和球发送碰撞
	 * 
	 * @param ball
	 *            球
	 */
	public void collision(Ball ball) {
		
		Point lowestP = new Point(ball.getCenterX(),ball.getCenterY()+ball.getRadius());
		Point highestP = new Point(ball.getCenterX(),ball.getCenterY()-ball.getRadius());
		if ( lowestP.getX() > this.actualPoints.get(8).getX() && lowestP.getX() < this.actualPoints.get(3).getX()
				&& lowestP.getY() >= this.actualPoints.get(0).getY() && highestP.getY() < this.actualPoints.get(0).getY() ) {
			ball.bounce(GameParameter.COLLISION_UP, GameParameter.OBJ_BAN);
			return;
		}
		if( lowestP.getX() > this.actualPoints.get(8).getX() && lowestP.getX() < this.actualPoints.get(3).getX()
				&& lowestP.getY() > this.actualPoints.get(5).getY() && highestP.getY() <= this.actualPoints.get(5).getY() ){
			ball.bounce(GameParameter.COLLISION_DOWN, GameParameter.OBJ_BAN);
			return;
		}
		
		Point leftmostP = new Point(ball.getCenterX()-ball.getRadius(),ball.getCenterY());
		Point rightmostP = new Point(ball.getCenterX()+ball.getRadius(),ball.getCenterY());
		if ( leftmostP.getY() > this.actualPoints.get(0).getY() && leftmostP.getY() < this.actualPoints.get(5).getY()
				&& rightmostP.getX() >= this.actualPoints.get(8).getX() && leftmostP.getX() < this.actualPoints.get(8).getX()) {
			ball.bounce(GameParameter.COLLISION_LEFT, GameParameter.OBJ_BAN);
			return;
		}
		if ( leftmostP.getY() > this.actualPoints.get(0).getY() && leftmostP.getY() < this.actualPoints.get(5).getY()
				&& leftmostP.getX() <= this.actualPoints.get(3).getX() && rightmostP.getX() > this.actualPoints.get(3).getX()) {
			ball.bounce(GameParameter.COLLISION_RIGHT, GameParameter.OBJ_BAN);
			return;
		}
//		
//		for (Point point : ball.getActualPoints()) {
//			if ((point.getX() <= actualPoints.get(0).getX() && point.getX() >= actualPoints.get(9).getX()
//					&& point.getY() >= actualPoints.get(0).getY() && point.getY() <= actualPoints.get(9).getY())
//					|| (point.getX() <= actualPoints.get(2).getX() && point.getX() >= actualPoints.get(1).getX()
//							&& point.getY() >= actualPoints.get(1).getY() && point.getY() <= actualPoints.get(2).getY())
//					|| (point.getX() <= actualPoints.get(4).getX() && point.getX() >= actualPoints.get(5).getX()
//							&& point.getY() >= actualPoints.get(4).getY() && point.getY() <= actualPoints.get(5).getY())
//					|| (point.getX() <= actualPoints.get(6).getX() && point.getX() >= actualPoints.get(7).getX()
//							&& point.getY() >= actualPoints.get(7).getY()
//							&& point.getY() <= actualPoints.get(6).getY())) {
//				ball.bounce(GameParameter.COLLISION_ANGLE, GameParameter.OBJ_BAN);
//				return;
//			}
//		}
	}
}

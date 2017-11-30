package cn.clvstudio.game.chuachua.model.game;

import java.util.List;
import java.util.Vector;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;

/**
 * 板
 * 
 * @author Darnell
 *
 */
public class Ban {
	public final int width = GameParameter.BAN_WIDTH;
	public final int height = GameParameter.BAN_HEIGHT;
	public double y;
	public double x;
	/** 板的几个关键点 */
	public static final List<Point> pointsA = new Vector<Point>();
	/** 板的几个关键点 */
	public static final List<Point> pointsB = new Vector<Point>();
	/** 板的几个关键实际点 */
	public List<Point> actualPoints = new Vector<Point>();

	static {
		// A 0
		pointsA.add(new Point(13.2085, 0));
		// B 1
		pointsA.add(new Point(208.6032, 0));
		// C 2
		pointsA.add(new Point(220.6730, 11.3207));
		// D 3
		pointsA.add(new Point(225, 24));
		// E 4
		pointsA.add(new Point(222.0394, 37.1321));
		// F 5
		pointsA.add(new Point(217.2571, 48));
		// H 6
		pointsA.add(new Point(18.4463, 48));
		// I 7
		pointsA.add(new Point(5.0101, 37.3584));
		// J 8
		pointsA.add(new Point(0, 24));
		// K 9
		pointsA.add(new Point(3.8714, 6.7925));
		// H 0
		pointsB.add(new Point(18.4463, 0));
		// F 1
		pointsB.add(new Point(217.2571, 0));
		// E 2
		pointsB.add(new Point(222.0394, 10.8679));
		// D 3
		pointsB.add(new Point(225, 24));
		// C 4
		pointsB.add(new Point(220.6730, 36.6793));
		// B 5
		pointsB.add(new Point(208.6032, 48));
		// A 6
		pointsB.add(new Point(13.2085, 48));
		// K 7
		pointsB.add(new Point(3.8714, 41.2075));
		// J 8
		pointsB.add(new Point(0, 24));
		// I 9
		pointsB.add(new Point(5.0101, 10.6416));
	}

	public Ban(double y) {
		this.y = y;
		this.x = (GameParameter.INTERACE_WIDTH-GameParameter.BAN_WIDTH)/2;
		this.actualPoints = y == GameParameter.BAN_TOP_A ? pointsA : pointsB;
		getActualPoint();
	}

	public void getActualPoint() {
		this.actualPoints.forEach(point -> {
			point.getActual(this.x, this.y);
		});
	}

	/**
	 * 板的移动
	 * 
	 * @param disRatio
	 *            Displacement ratio 位移比
	 */
	public void move(double disRatio) {
		this.x = disRatio * GameParameter.INTERACE_WIDTH;
		getActualPoint();
	}

	/**
	 * 板是否和球发送碰撞
	 * 
	 * @param ball 球
	 */
	public void collision(Ball ball) {
		Point highestP = ball.getActualPoints().get(0);
		Point lowestP = ball.getActualPoints().get(4);
		if ((highestP.getX() > actualPoints.get(0).getX() && highestP.getX() < actualPoints.get(5).getX())
				&& ((highestP.getY() < actualPoints.get(0).getY() && lowestP.getY() >= actualPoints.get(0).getY())
						|| (highestP.getY() < actualPoints.get(5).getY()
								&& lowestP.getY() >= actualPoints.get(5).getY()))) {
			ball.bounce(GameParameter.UP_DOWN, GameParameter.OBJ_BAN);
			return;
		}
		Point leftmostP = ball.getActualPoints().get(6);
		Point rightmostP = ball.getActualPoints().get(2);
		if ((leftmostP.getY() > actualPoints.get(9).getY() && leftmostP.getY() < actualPoints.get(4).getY()
				&& leftmostP.getX() < actualPoints.get(7).getX() && rightmostP.getX() >= actualPoints.get(7).getX())
				|| (leftmostP.getY() > actualPoints.get(2).getY() && leftmostP.getY() < actualPoints.get(7).getY()
						&& leftmostP.getX() <= actualPoints.get(2).getX()
						&& rightmostP.getX() > actualPoints.get(2).getX())) {
			ball.bounce(GameParameter.LEFT_RIGHT, GameParameter.OBJ_BAN);
			return;
		}

		for (Point point : ball.getActualPoints()) {
			if ((point.getX() <= actualPoints.get(0).getX() && point.getX() >= actualPoints.get(9).getX()
					&& point.getY() >= actualPoints.get(0).getY() && point.getY() <= actualPoints.get(9).getY())
					|| (point.getX() <= actualPoints.get(2).getX() && point.getX() >= actualPoints.get(1).getX()
							&& point.getY() >= actualPoints.get(1).getY() && point.getY() <= actualPoints.get(2).getY())
					|| (point.getX() <= actualPoints.get(4).getX() && point.getX() >= actualPoints.get(5).getX()
							&& point.getY() >= actualPoints.get(4).getY() && point.getY() <= actualPoints.get(5).getY())
					|| (point.getX() <= actualPoints.get(6).getX() && point.getX() >= actualPoints.get(7).getX()
							&& point.getY() >= actualPoints.get(7).getY()
							&& point.getY() <= actualPoints.get(6).getY())) {
				ball.bounce(GameParameter.ANGLE, GameParameter.OBJ_BAN);
				return ;
			}
		}

	}
}

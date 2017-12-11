package cn.clvstudio.game.chuachua.model.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;

/**
 * 砖块实体类
 * 
 * @author Darnell
 *
 */
public class Brick {
	@SuppressWarnings("unused")
	private static final  Logger LOG = LoggerFactory.getLogger(Brick.class);
	private int id;
	/** 宽 */
	private final int width = GameParameter.BRICK_WIDTH;
	/** 高 */
	private final int height = GameParameter.BRICK_HEIGHT;
	/** 右上角X坐标 */
	private int upperRightCornerX;
	/** 右上角Y坐标 */
	private int upperRightCornerY;
	/** 右下角X坐标 */
	private int lowerLeftCornerX;
	/** 右下角Y坐标 */
	private int lowerLeftCornerY;

	public Brick(int id, int upperRightCornerX, int upperRightCornerY) {
		this.upperRightCornerX = upperRightCornerX;
		this.upperRightCornerY = upperRightCornerY;
		this.lowerLeftCornerX = this.upperRightCornerX + this.width;
		this.lowerLeftCornerY = this.upperRightCornerY + this.height;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	/**
	 * 被球碰撞
	 * 
	 * @param ball
	 * @return
	 */
	public boolean collision(Ball ball) {
		Point lowestP = new Point(ball.getCenterX(),ball.getCenterY()+ball.getRadius());
		Point highestP = new Point(ball.getCenterX(),ball.getCenterY()-ball.getRadius());
		if (lowestP.getX() >= this.upperRightCornerX &&  lowestP.getX() <= this.lowerLeftCornerX
				&& lowestP.getY() >=this.upperRightCornerY && highestP.getY() < this.upperRightCornerY) {
			ball.bounce(GameParameter.COLLISION_UP, GameParameter.OBJ_BRICK);
			return true;
		}
		if (lowestP.getX() >= this.upperRightCornerX &&  lowestP.getX() <= this.lowerLeftCornerX
				&& highestP.getY() <= this.lowerLeftCornerY && lowestP.getY() > this.lowerLeftCornerY) {
			ball.bounce(GameParameter.COLLISION_DOWN, GameParameter.OBJ_BRICK);
			return true;
		}
		Point leftmostP = new Point(ball.getCenterX()-ball.getRadius(),ball.getCenterY());
		Point rightmostP = new Point(ball.getCenterX()+ball.getRadius(),ball.getCenterY());
		if (rightmostP.getY() >= this.upperRightCornerY && rightmostP.getY() <= this.lowerLeftCornerY
				&& rightmostP.getX() >= this.upperRightCornerX && leftmostP.getX() < this.upperRightCornerX ) {
			ball.bounce(GameParameter.COLLISION_LEFT, GameParameter.OBJ_BRICK);
			return true;
		}
		if (rightmostP.getY() >= this.upperRightCornerY && rightmostP.getY() <= this.lowerLeftCornerY
				&& leftmostP.getX() <= this.lowerLeftCornerX && rightmostP.getX() > this.lowerLeftCornerX) {
			ball.bounce(GameParameter.COLLISION_RIGHT, GameParameter.OBJ_BRICK);
			return true;
		}
//		for (Point point : ball.getActualPoints()) {
//			if (point.getX() >= this.upperRightCornerX && point.getX() <= this.lowerLeftCornerX
//					&& point.getY() >= this.upperRightCornerY && point.getY() <= this.lowerLeftCornerY) {
//				LOG.debug("砖发生角碰撞 "+this.toString() + "; " + ball.toString());
//				ball.bounce(GameParameter.ANGLE, GameParameter.OBJ_BRICK);
//				return true;
//			}
//		}
		return false;
	}

	@Override
	public String toString() {
		return id + ":(" + upperRightCornerX + "," + upperRightCornerY +")|(" + lowerLeftCornerX + "," + lowerLeftCornerY +")";
	}

}

package cn.clvstudio.game.chuachua.game.model;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;

/**
 * 砖块实体类
 * 
 * @author Darnell
 *
 */
public class Brick {
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
		Point highestP = ball.getActualPoints().get(0);
		Point lowestP = ball.getActualPoints().get(4);
		if ((highestP.getX() > this.upperRightCornerX && highestP.getX() < this.lowerLeftCornerX)
				&& ((lowestP.getY() >= this.upperRightCornerY && highestP.getY() <= this.upperRightCornerY)
						|| (lowestP.getY() >= this.lowerLeftCornerY && highestP.getY() <= this.lowerLeftCornerY))) {
			
			ball.bounce(GameParameter.UP_DOWN, GameParameter.OBJ_BRICK);
			return true;
		}

		Point leftmostP = ball.getActualPoints().get(6);
		Point rightmostP = ball.getActualPoints().get(2);
		if ((leftmostP.getY() > this.upperRightCornerY && leftmostP.getY() < this.lowerLeftCornerY)
				&& ((rightmostP.getX() >= this.upperRightCornerX && leftmostP.getX() <= this.upperRightCornerX)
						|| (leftmostP.getX() <= this.lowerLeftCornerX && rightmostP.getX() >= this.lowerLeftCornerX))) {
			
			ball.bounce(GameParameter.LEFT_RIGHT, GameParameter.OBJ_BRICK);
			return true;
		}

		for (Point point : ball.getActualPoints()) {
			if (point.getX() >= this.upperRightCornerX && point.getX() <= this.lowerLeftCornerX
					&& point.getY() >= this.upperRightCornerY && point.getY() <= this.lowerLeftCornerY) {
				
				ball.bounce(GameParameter.ANGLE, GameParameter.OBJ_BRICK);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return id + ":" + upperRightCornerX + ", " + upperRightCornerY +":" + lowerLeftCornerX + ", " + lowerLeftCornerY ;
	}

}

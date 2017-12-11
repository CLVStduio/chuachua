package cn.clvstudio.game.chuachua.model.game;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;

/**
 * 墙
 * 
 * @author Darnell
 *
 */
public class Wall {
	private final static Point origin = new Point(0, 0);
	private final static Point border = new Point(GameParameter.INTERACE_WIDTH, GameParameter.INTERFACE_HEIGHT);

	/**
	 * 墙是否和球发送碰撞
	 * 
	 * @param ball
	 *            球
	 */
	public static void collision(Ball ball) {
		if (ball.getCenterX()+ball.getRadius() >= border.getX()) {
			ball.bounce(GameParameter.COLLISION_LEFT, GameParameter.OBJ_WALL);
			return;
		}
		if (ball.getCenterX()-ball.getRadius() <= origin.getX() ) {
			ball.bounce(GameParameter.COLLISION_RIGHT, GameParameter.OBJ_WALL);
			return;
		}

		if (ball.getCenterY()+ball.getRadius() >= border.getY()) {
			ball.bounce(GameParameter.COLLISION_UP, GameParameter.OBJ_WALL);
			return;
		}
		if (ball.getCenterY()-ball.getRadius() <= origin.getY()) {
			ball.bounce(GameParameter.COLLISION_DOWN, GameParameter.OBJ_WALL);
			return;
		}
	}
}

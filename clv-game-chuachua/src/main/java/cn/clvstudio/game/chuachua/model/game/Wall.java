package cn.clvstudio.game.chuachua.game.model;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;

/**
 * 墙
 * @author Darnell
 *
 */
public class Wall {
	private static final Point origin = new Point(0,0);
	private static final Point border = new Point(GameParameter.INTERACE_WIDTH,GameParameter.INTERFACE_HEIGHT);
	/**
	 * 墙是否和球发送碰撞
	 * 
	 * @param ball 球
	 */
	public static void collision(Ball ball) {
		Point leftmostP = ball.getActualPoints().get(6);
		Point rightmostP = ball.getActualPoints().get(2);
		if(leftmostP.getX()<=origin.getX() || rightmostP.getX() >= border.getX()){
			ball.bounce(GameParameter.LEFT_RIGHT, GameParameter.OBJ_WALL);
			return;
		}
		
		Point highestP = ball.getActualPoints().get(0);
		Point lowestP = ball.getActualPoints().get(4);
		
		if(highestP.getY() <= origin.getY() || lowestP.getY() >= border.getY()){
			ball.bounce(GameParameter.UP_DOWN, GameParameter.OBJ_WALL);
			return;
		}
	}
}

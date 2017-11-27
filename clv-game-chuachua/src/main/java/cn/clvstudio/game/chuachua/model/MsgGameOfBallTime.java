package cn.clvstudio.game.chuachua.model;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;
import cn.clvstudio.game.chuachua.game.model.Ball;
import cn.clvstudio.game.chuachua.game.model.Time;

/**
 * 游戏中球和时间信息发送
 * @author Darnell
 *
 */
public class MsgGameOfBallTime {
	private double ballX;
	private double ballY;
	private String time;
	public MsgGameOfBallTime(Ball ball,Time time){
		this.ballX = ball.getCenterX()/GameParameter.INTERACE_WIDTH;
		this.ballY = ball.getCenterY()/GameParameter.INTERFACE_HEIGHT;
		this.time = time.toString();
	}
	public double getBallX() {
		return ballX;
	}
	public void setBallX(double ballX) {
		this.ballX = ballX;
	}
	public double getBallY() {
		return ballY;
	}
	public void setBallY(double ballY) {
		this.ballY = ballY;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}

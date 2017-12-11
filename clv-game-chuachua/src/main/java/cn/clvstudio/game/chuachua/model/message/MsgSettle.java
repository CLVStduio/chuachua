package cn.clvstudio.game.chuachua.model.message;

public class MsgSettle {
	/** 获胜方*/
	private int winFlag;
	/** 玩家A的分数*/
	private int scoreA;
	/** 玩家B的分数*/
	private int scoreB;
	public MsgSettle(int winFlag, int scoreA, int scoreB) {
		super();
		this.winFlag = winFlag;
		this.scoreA = scoreA;
		this.scoreB = scoreB;
	}
	public int getWinFlag() {
		return winFlag;
	}
	public void setWinFlag(int winFlag) {
		this.winFlag = winFlag;
	}
	public int getScoreA() {
		return scoreA;
	}
	public void setScoreA(int scoreA) {
		this.scoreA = scoreA;
	}
	public int getScoreB() {
		return scoreB;
	}
	public void setScoreB(int scoreB) {
		this.scoreB = scoreB;
	}
	
}

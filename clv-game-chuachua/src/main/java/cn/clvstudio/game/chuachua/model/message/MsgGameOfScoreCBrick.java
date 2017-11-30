package cn.clvstudio.game.chuachua.model.message;

import java.util.List;

/**
 * 游戏中传输信息类型，分数和要清理的砖块
 * @author Darnell
 */
public class MsgGameOfScoreCBrick {
	/**玩家A 的分数*/
	private int sa;
	/**玩家B 的分数*/
	private int sb;
	/** 要清理的砖块*/
	private List<Integer> cb;
	public MsgGameOfScoreCBrick (int scoreA,int scoreB,List<Integer> clearBrick){
		this.sa = scoreA;
		this.sb = scoreB;
		this.cb = clearBrick;
	}
	public int getSa() {
		return sa;
	}
	public void setSa(int sa) {
		this.sa = sa;
	}
	public int getSb() {
		return sb;
	}
	public void setSb(int sb) {
		this.sb = sb;
	}
	public List<Integer> getCb() {
		return cb;
	}
	public void setCb(List<Integer> cb) {
		this.cb = cb;
	}
	
}

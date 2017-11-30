package cn.clvstudio.game.chuachua.model;

/**
 * @author Darnell
 *
 */
public class MessageGeneral {
	/**
	 * 信息类型type（b:匹配；c:游戏中；d:结算中）
	 */
	private String t;
	/**
	 * 信息的具体内容content
	 */
	private String c;
	
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	
}

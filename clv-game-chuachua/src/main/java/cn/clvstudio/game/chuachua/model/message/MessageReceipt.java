package cn.clvstudio.game.chuachua.model;

/**
 * 信息回执
 * @author Darnell
 *
 */
public class MessageReceipt {
	/**
	 * 信息类型type（b:匹配；c:游戏中；d:结算中）
	 */
	private String t;
	/**
	 * 结果
	 */
	private String s;
	/**
	 * 信息（对手id或异常信息）
	 */
	private Object msg;
	
	public MessageReceipt(String t, String s, Object msg) {
		super();
		this.t = t;
		this.s = s;
		this.msg = msg;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	

	
}

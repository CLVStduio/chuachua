package cn.clvstudio.game.chuachua.game.model;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;

/**
 * 时间
 * @author Darnell
 *
 */
public class Time {
	/**分钟*/
	private int minute;
	/**秒*/
	private int second;
	/**毫秒*/
	private int millisencond;
	/**总毫秒数*/
	private int totalMS;
	
	public Time(int totalMS) {
		super();
		this.totalMS = totalMS;
		this.minute = this.totalMS/1000/60;
		this.second = this.totalMS/1000-this.minute*60;
		this.millisencond = this.totalMS-this.minute*60*1000-this.second*1000;
	}
	public Time(int minute, int second, int millisencond) {
		super();
		this.minute = minute;
		this.second = second;
		this.millisencond = millisencond;
		this.totalMS = minute*60*1000+second*1000+millisencond;
	}
	
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getMillisencond() {
		return millisencond;
	}
	public void setMillisencond(int millisencond) {
		this.millisencond = millisencond;
	}
	public int getTotalMS() {
		return totalMS;
	}
	public void setTotalMS(int totalMS) {
		this.totalMS = totalMS;
	}
	public void setTotalMS() {
		this.totalMS -= GameParameter.RUN_TIME;
		if(this.millisencond > GameParameter.RUN_TIME){
			this.millisencond -= GameParameter.RUN_TIME;
			return;
		}
		this.millisencond += 1000 - GameParameter.RUN_TIME;
		if(this.second >= 1){
			this.second -= 1;
			return;
		}
		this.second += 59;
		if(this.minute > 0){
			this.minute -= 1;
			return;
		}
	}
	public String toString(){
		return minute+":"+second;
	}
	public String toStringAll(){
		return minute+"'"+second+"''"+millisencond;
	}
}

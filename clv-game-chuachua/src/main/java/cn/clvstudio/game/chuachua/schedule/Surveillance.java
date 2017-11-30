package cn.clvstudio.game.chuachua.schedule;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.clvstudio.game.chuachua.constants.Constants.RoomStatus;

public class Surveillance implements Runnable{

	private static final  Logger LOG = LoggerFactory.getLogger(Surveillance.class);

	/** 游戏房间和定时线程映射 */
	@SuppressWarnings("rawtypes")
	private Map<GameRoom,ScheduledFuture> gameRoomMap ;
	/** 游戏房间列表 */
	private List<GameRoom> gameRoomList;
	
	public Surveillance(@SuppressWarnings("rawtypes") Map<GameRoom, ScheduledFuture> gameRoomMap, List<GameRoom> gameRoomList) {
		super();
		this.gameRoomMap = gameRoomMap;
		this.gameRoomList = gameRoomList;
	}

	@Override
	public void run() {
		LOG.info("查房中。。。");
		List<GameRoom> removeRoomList = new Vector<GameRoom>();
		synchronized (gameRoomMap){
			for (GameRoom game : gameRoomList){
				if(game.getStatus().equals(RoomStatus.ROOM_STATUS_OVER)){
					gameRoomMap.get(game).cancel(true);
					removeRoomList.add(game);
					gameRoomMap.remove(game);
					LOG.info("》》》 关闭房间，id："+game.getRoomId());
				}
			}
		}
		synchronized (gameRoomList){
			for(GameRoom game : removeRoomList){
				gameRoomList.remove(game);
			}
			removeRoomList = null;
			LOG.info("当前房间数量为："+gameRoomList.size());
		}
	}

}

package cn.clvstudio.game.chuachua.schedule;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import cn.clvstudio.game.chuachua.constants.Constants;
import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;
import cn.clvstudio.game.chuachua.constants.Constants.Surveillancer;

/** 定时工作
 * @author Darnell
 *
 */
@Component
public class TimingWork implements InitializingBean{

	private static final  Logger LOG = LoggerFactory.getLogger(TimingWork.class);
	
	/**定时器*/
	private static ScheduledExecutorService schedule ;
	/** 游戏房间和定时线程映射 */
	@SuppressWarnings("rawtypes")
	private static Map<GameRoom,ScheduledFuture> gameRoomMap ;
	/** 游戏房间列表 */
	private static List<GameRoom> gameRoomList;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void afterPropertiesSet() throws Exception {
		schedule = Executors.newScheduledThreadPool(Constants.MAX_GAME_ROOM_NUMS+2);
		gameRoomMap = new ConcurrentHashMap<GameRoom,ScheduledFuture>();
		gameRoomList = new Vector<GameRoom>();
		Surveillance surveillance = new Surveillance(gameRoomMap,gameRoomList);
		schedule.scheduleWithFixedDelay(surveillance, Surveillancer.initialDelay, Surveillancer.delay, TimeUnit.SECONDS);
		LOG.info("》》》 job 初始化完毕");
	}
	
	/** 房间登记并启动
	 * @param game 房间
	 */
	public static void gameRoomRegistration(GameRoom game){
		LOG.info("创建房间：id = "+game.getRoomId()+" ; 玩家A = 【"+game.getPlayerA()+"】 ; 玩家B = 【"+game.getPlayerB()+"】");
		gameRoomList.add(game);
		gameRoomMap.put(game, schedule.scheduleAtFixedRate(game, 0, GameParameter.RUN_TIME, TimeUnit.MILLISECONDS));
	}
}

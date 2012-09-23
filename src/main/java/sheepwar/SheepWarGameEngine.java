package sheepwar;

import javax.microedition.midlet.MIDlet;

import cn.ohyeah.stb.game.GameCanvasEngine;

/**
 * 游戏引擎
 * @author Administrator
 */
public class SheepWarGameEngine extends GameCanvasEngine implements Common {
	public static boolean isSupportFavor = false;
	public static int ScrW = 0;
	public static int ScrH = 0;
	
	public static SheepWarGameEngine instance = buildGameEngine();

	private static SheepWarGameEngine buildGameEngine() {
		return new SheepWarGameEngine(SheepWarMIDlet.getInstance());
	}

	public StateMain stateMain;
	public StateGame stateGame;
	public Prop[] props;

	private SheepWarGameEngine(MIDlet midlet) {
		super(midlet);
		setRelease(true);
		ScrW = screenWidth;
		ScrH = screenHeight;
		stateGame = new StateGame(this);
		stateMain = new StateMain(this,stateGame);
	}

	public int state;
	public int mainIndex, playingIndex;
	
	protected void loop() {
		
		/*处理键值*/
		switch (state) {   	
		case STATUS_INIT:
			init();
			break;
		case STATUS_MAIN_MENU: 
			stateMain.handleKey(keyState);
			break;
		case STATUS_GAME_PLAYING:
			stateGame.handleKey(keyState);
			break;
		}

		/*显示界面*/
		switch (state) {
		case STATUS_INIT:
			//showInit(g);
			break;
		case STATUS_MAIN_MENU:
			stateMain.show(g);
			break;
		case STATUS_GAME_PLAYING:
			stateGame.show(g);
			break;
		}
		
		/*执行逻辑*/
		switch (state) {
		case STATUS_INIT:
			//showInit(g);
			break;
		case STATUS_MAIN_MENU:
			stateMain.execute();
			break;
		case STATUS_GAME_PLAYING:
			stateGame.execute();
			break;
		}
		
		/*退出游戏*/
		exit();
	}

	private void init() {
		state = STATUS_MAIN_MENU;                           
	}
	private void exit(){
		if(stateMain.exit){
			exit = true;
		}
	}
	
	private long recordTime;
	public boolean timePass(int millisSeconds) {
		long curTime = System.currentTimeMillis();
		if (recordTime <= 0) {
			recordTime = curTime;
		}
		else {
			if (curTime-recordTime >= millisSeconds) {
				recordTime = 0;
				return true;
			}
		}
		return false;
	}
	
}

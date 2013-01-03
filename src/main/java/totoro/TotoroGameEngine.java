package totoro;

import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyState;

/**
 * 游戏引擎
 * @author Administrator
 */
public class TotoroGameEngine extends GameCanvasEngine implements Common {
	public static boolean isSupportFavor = false;
	public static int ScrW = 0;
	public static int ScrH = 0;
	
	public static TotoroGameEngine instance = buildGameEngine();

	private static TotoroGameEngine buildGameEngine() {
		if(instance==null){
			return new TotoroGameEngine(TotoroMIDlet.getInstance());
		}else{
			return instance;
		}
	}

	public StateMain stateMain;
	public StateGame stateGame;

	private TotoroGameEngine(MIDlet midlet) {
		super(midlet);
		setRelease(false);
		ScrW = screenWidth;
		ScrH = screenHeight;
		stateGame = new StateGame(this);
		stateMain = new StateMain(this);
	}

	public int state;
	public int mainIndex, playingIndex;
	private int cursorFrame;
	
	protected void loop() {
		
		/*显示界面*/
		switch (state) {
		case STATUS_INIT:
			showInit(g);
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
			cursorFrame = (cursorFrame+1)%12;
			init();
			break;
		case STATUS_MAIN_MENU:
			stateMain.execute();
			break;
		case STATUS_GAME_PLAYING:
			stateGame.execute();
			break;
		}
		
		/*处理键值*/
		switch (state) {   	
		case STATUS_INIT:
			handleInit(keyState);
			break;
		case STATUS_MAIN_MENU: 
			stateMain.handleKey(keyState);
			break;
		case STATUS_GAME_PLAYING:
			stateGame.handleKey(keyState);
			break;
		}
		
		/*退出游戏*/
		exit();
	}
	
	private void init() {
		//state = STATUS_MAIN_MENU;  
	}
	
	private void handleInit(KeyState key) {
		if(key.containsAnyKey()){
			key.clear();
			Resource.freeImage(Resource.id_bg);
			Resource.freeImage(Resource.id_text);
			state = STATUS_MAIN_MENU;
		}
	}


	private void showInit(SGraphics g) {
		Image bg = Resource.loadImage(Resource.id_bg);
		Image text = Resource.loadImage(Resource.id_text);
		g.drawImage(bg, 0, 0, 20);
		if(cursorFrame>4){
			int x = screenWidth/2 - text.getWidth()/2;
			g.drawImage(text, x, 450, 20);
		}
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

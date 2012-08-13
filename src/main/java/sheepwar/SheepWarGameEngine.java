package sheepwar;

import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.key.KeyCode;

public class SheepWarGameEngine extends GameCanvasEngine implements Common{
	
	public static boolean isSupportFavor = false;	
	public static int ScrW = 0;
	public static int ScrH = 0;
	public static SheepWarGameEngine instance = buildGameEngine();
	
	private static SheepWarGameEngine buildGameEngine() {
		return new SheepWarGameEngine(SheepWarMIDlet.getInstance());
	}
	
	private SheepWarGameEngine(MIDlet midlet) {
		super(midlet);
		setRelease(true);
		ScrW = screenWidth;
		ScrH = screenHeight;
	}
	
	public ShowGame showGame;
	public int status;
	public int mainIndex;
    
	
	protected void loop() {
		

		switch (status) {
		case STATUS_INIT:  //初始
			init();
			break;
		case STATUS_MAIN_MENU:  //主菜单
			processGameMenu();
			break;
		case STATUS_GAME_PLAYING:  //游戏中
			
			break;
		}
		
		switch (status) {
		case STATUS_INIT:
			break;
		case STATUS_MAIN_MENU:
			showGameMenu(g);
			break;
		case STATUS_GAME_PLAYING:
			
			break;
		}
		
	
	}

	private void init() {
		showGame = new ShowGame();
		status = STATUS_MAIN_MENU;  //进入游戏菜单
	}

	private void showGameMenu(Graphics g) {
		showGame.drawMainMenu(g, mainIndex);
	}

	private void processGameMenu() {

		if (keyState.containsAndRemove(KeyCode.UP)) {
			mainIndex = (mainIndex + 6 - 1) % 6;
		} else if (keyState.containsAndRemove(KeyCode.DOWN)) {
			mainIndex = (mainIndex + 1) % 6;
		} else if (keyState.containsAndRemove(KeyCode.OK)) {
			processSubMenu();
	    } 
		if (keyState.containsAndRemove(KeyCode.BACK)){ //返回键直接退出
		}
		
	}

	private void processSubMenu() {
		// TODO Auto-generated method stub
		//status = 
	}

	
}

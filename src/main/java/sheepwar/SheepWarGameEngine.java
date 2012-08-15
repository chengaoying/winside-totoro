package sheepwar;

import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.key.KeyCode;

/**
 * 游戏引擎
 * 
 * @author Administrator
 */
public class SheepWarGameEngine extends GameCanvasEngine implements Common {
	public Role own;
	public static boolean isMove = true;//

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
	public int mainIndex, playingIndex;

	protected void loop() {

		switch (status) {
		case STATUS_INIT: // 初始化
			init();
			break;
		case STATUS_MAIN_MENU: // 主菜单
			processGameMenu();
			break;
		case STATUS_GAME_PLAYING: // 游戏中
			processGamePlaying();// 游戏中的操作
			break;
		case STATUS_GAME_SHOP: // 道具商城
			// processShop();
			break;
		case STATUS_GAME_ARCHI:// 游戏成就
			break;
		case STATUS_GAME_RANKING:// 游戏排行
			// processRanking();
			break;
		case STATUS_GAME_HELP:// 游戏帮助
			// processHelp();
			break;
		}

		switch (status) {
		case STATUS_INIT:
			showInit(g);// 画出初始化的动画
			break;
		case STATUS_MAIN_MENU:
			showGameMenu(g);
			break;
		case STATUS_GAME_PLAYING:
			showGamePlaying(g);
			break;
		case STATUS_GAME_SHOP: // 道具商城
			// TODO showGameShop
			break;
		case STATUS_GAME_ARCHI:// 游戏成就
			// TODO showGameArchi
			break;
		case STATUS_GAME_RANKING:// 游戏排行
			break;
		case STATUS_GAME_HELP:// 游戏帮助
			break;
		}
	}

	private void moveRole(int i) {
		switch (i) {
		case 0: // 往上--主角

			break;
		case 1: // 往下--主角

			break;
		}

	}

	private void showInit(Graphics g) {
		/*
		 * g.setColor(0X000000); g.setClip(0, 0, 100, 100);
		 * g.setColor(0Xffffff); g.drawString("加载中,请稍后...", 300, 260, 10);
		 */
	}

	private void init() {
		showGame = new ShowGame();
		status = STATUS_MAIN_MENU; // 进入游戏菜单
	}

	private void showGameMenu(Graphics g) {
		showGame.drawMainMenu(g, mainIndex);
	}

	private void showGamePlaying(Graphics g) {
		showGame.drawGamePlaying(g, playingIndex);
	}

	private void processGameMenu() {

		if (keyState.containsAndRemove(KeyCode.UP)) {
			mainIndex = (mainIndex + 6 - 1) % 6;
		} else if (keyState.containsAndRemove(KeyCode.DOWN)) {
			mainIndex = (mainIndex + 1) % 6;
		} else if (keyState.containsAndRemove(KeyCode.OK)) {
			processSubMenu();
			showGame.clearMainMenu();
		}
		if (keyState.containsAndRemove(KeyCode.BACK)) { // 返回键直接退出
		}

	}

	private void processGamePlaying() {
		if (keyState.containsMoveEventAndRemove(KeyCode.UP)) {
			moveRole(0);//
		} else if (keyState.containsMoveEventAndRemove(KeyCode.DOWN)) {
			// moveRole(1);
		} else if (keyState.contains(KeyCode.OK) && own.status != 1) { // 普通攻击
			keyState.remove(KeyCode.OK);
		} else if (keyState.contains(KeyCode.NUM5) && own.status != 1) { // 时光闹钟
			keyState.remove(KeyCode.NUM5);
		}
	}

	private void processSubMenu() {
		if (mainIndex == 0) { //新游戏
			status = STATUS_GAME_PLAYING;
		} else if (mainIndex == 1) {// 继续游戏
		} else if (mainIndex == 2){ //游戏排行
		} else if (mainIndex == 3) {// 游戏商城
		} else if (mainIndex == 4) {// 游戏简介
		} else if (mainIndex == 5) {// 退出
			exit = true;
		}
		mainIndex=0;
	}

}

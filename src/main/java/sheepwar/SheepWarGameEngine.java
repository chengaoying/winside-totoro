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
	public Role wolf;

	public static boolean isSupportFavor = false;
	public static int ScrW = 0;
	public static int ScrH = 0;
	private CreateRole createRole;
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
	public int mainIndex, playingIndex,shopIndex,archiIndex,rankingIndex,helpIndex;
	
	private int mainOrgame=-1;                                 //返回商城界面：0返回主界面，1返回游戏中的界面
	private int shopX=0,shopY=0;

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
			 processShop();
			break;
		case STATUS_GAME_ARCHI:// 游戏成就
			processArchi();
			break;
		case STATUS_GAME_RANKING:// 游戏排行
			 processRanking();
			break;
		case STATUS_GAME_HELP:// 游戏帮助
			 processHelp();
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
			 showGameShop(g);
			break;
		case STATUS_GAME_ARCHI:// 游戏成就
			 showGameArchi(g);
			break;
		case STATUS_GAME_RANKING:// 游戏排行
			showRanking(g);
			break;
		case STATUS_GAME_HELP:// 游戏帮助
			showHelp(g);
			break;
		}
	}

	private void moveRole(int towards) {
		switch (towards) {
		case 0: // 往上--主角
			if(own.mapy>=164){//吊篮上极限坐标164
				own.mapy -= own.speed;
			}
			break;
		case 1: // 往下--主角
			own.direction = 1;
			if(own.mapy + own.height<460){//吊篮下极限坐标原460--问题：增加Y坐标极限会出现area out of Iamge ?
				own.mapy += own.speed;
			}
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
		status = STATUS_MAIN_MENU;                            // 进入游戏菜单
		showGame = new ShowGame();
		createRole = new CreateRole();
		own = createRole.createSheep();
		wolf = createRole.createWolf();
	}

	private void showGameMenu(Graphics g) {
		showGame.drawMainMenu(g, mainIndex);
	}

	private void showGamePlaying(Graphics g) {
		showGame.drawGamePlaying(g, playingIndex, own);
	}
	
	/*画出商店*/
	private void showGameShop(Graphics g) {
		showGame.drawGameShop(g,shopX,shopY);
	}
	
	/*画出成就系统*/
	private void showGameArchi(Graphics g) {
		showGame.drawGameArchi(g,archiIndex);
	}
	
	/*画出排行榜*/
	private void showRanking(Graphics g) {
		showGame.showRanking(g, rankingIndex);
	}
	
	/*画出帮助*/
	private void showHelp(Graphics g) {
        showGame.showHelp(g,helpIndex);
	}
	private void processGameMenu() {
		if (keyState.contains(KeyCode.UP)) {
			keyState.remove(KeyCode.UP);
			mainIndex = (mainIndex + 6 - 1) % 6;
		} else if (keyState.contains(KeyCode.DOWN)) {
			keyState.remove(KeyCode.DOWN);
			mainIndex = (mainIndex + 1) % 6;
		} else if (keyState.contains(KeyCode.OK)) {
			keyState.remove(KeyCode.OK);
			showGame.clearMainMenu();
			processSubMenu();
		}
		if (keyState.contains(KeyCode.BACK)) { // 返回键直接退出
			keyState.remove(KeyCode.BACK);
			showGame.clearMainMenu();
			exit = true;
		}
	}

	private void processGamePlaying() {
		if (keyState.containsMoveEventAndRemove(KeyCode.UP)) {
			moveRole(0);//
		} else if (keyState.containsMoveEventAndRemove(KeyCode.DOWN)) {
			moveRole(1);
		} else if (keyState.containsAndRemove(KeyCode.OK)) { // 普通攻击
			
		}else if(keyState.contains(KeyCode.NUM1)){    //时光闹钟
			keyState.remove(KeyCode.NUM1);
		}else if(keyState.contains(KeyCode.NUM2)){ //捕狼网
			keyState.remove(KeyCode.NUM2);
		}else if(keyState.contains(KeyCode.NUM3)){//盾牌
			keyState.remove(KeyCode.NUM3);
		}else if(keyState.contains(KeyCode.NUM4)){//激光枪
			keyState.remove(KeyCode.NUM4);
		}else if(keyState.contains(KeyCode.NUM5)){//驱散竖琴（平底锅将弃之）
			keyState.remove(KeyCode.NUM5);
		}else if(keyState.contains(KeyCode.NUM6)){//
			keyState.remove(KeyCode.NUM6);
		}else if(keyState.contains(KeyCode.NUM7)){
			keyState.remove(KeyCode.NUM7);
		}else if(keyState.contains(KeyCode.NUM8)){//木偶->可以增加一条生命
			keyState.remove(KeyCode.NUM8);
		}
		else if (keyState.containsAndRemove(KeyCode.NUM0)) { // 按0返回-----完善后应改为0键暂停！
			status=STATUS_MAIN_MENU;
			showGame.clearGamePlaying();
		}
	}
	
	/*商城操作*/ //TODO控制从左到右的判断，为了解决在右侧上下滚动时一次点击无效
	private void processShop() {
		if (keyState.contains(KeyCode.NUM0) || keyState.contains(KeyCode.BACK)) {
			keyState.remove(KeyCode.NUM0);
			keyState.remove(KeyCode.BACK);
//			if(mainOrgame==0){          //返回主界面------（-1返回商城界面）
//				status=STATUS_MAIN_MENU;
//			}else if(mainOrgame==1){     //返回游戏中的界面
//				status=STATUS_GAME_PLAYING;
//			}
			status=STATUS_MAIN_MENU;          //TODO 判断返回游戏中还是主界面
			shopX = 0;shopY = 0;
			showGame.clearShop();
		}
		if (keyState.contains(KeyCode.UP)) {
			keyState.remove(KeyCode.UP);
			if( shopY >0){
				shopY=shopY-1;
			}else {
				shopY = 0;
			}
		}
		if (keyState.contains(KeyCode.DOWN)) {
			keyState.remove(KeyCode.DOWN);
			if(shopY<4){
				shopY = shopY+1;
			}else{
				shopY=0;
			}
		}
		if (keyState.contains(KeyCode.LEFT)) {
			keyState.remove(KeyCode.LEFT);
			if(shopX>0){
				shopX = shopX-1;
			}
		}
		if (keyState.contains(KeyCode.RIGHT)) {
			keyState.remove(KeyCode.RIGHT);
			if(shopX<3){
				shopX = shopX+1;
			}
			if(shopX>2){
				shopX=0;
			}
			if(shopX==2){//当控制由左到右时，shopY置零
				shopY=0;
			}
		}
		if (keyState.contains(KeyCode.OK)) {
			keyState.remove(KeyCode.OK);
			showGame.clearMainMenu();
			if(shopX==2 && shopY==0){//进入充值
				showGame.clearShop();
			}
			if(shopX==2 && shopY==1){
//				if(mainOrgame==0){//返回主界面
//					showGame.clearShop();
//					status=STATUS_MAIN_MENU;
//				}else if(mainOrgame==1){//返回游戏中的界面
//					showGame.clearShop();
//					status=STATUS_GAME_PLAYING;
//				}
				showGame.clearShop();
				status=STATUS_MAIN_MENU;
			}
		}
	}
	
	/*成就操作*/
	private void processArchi() {
         if(keyState.contains(KeyCode.NUM0)){  //按0键返回主界面
        	 keyState.remove(KeyCode.NUM0);
        	 showGame.clearGameArchi();
        	 status=STATUS_MAIN_MENU;
         }
	}
	
	/*排行榜操作*/
	private void processRanking() {
		if(keyState.contains(KeyCode.NUM0)){       //按0键返回主界面
			showGame.clearRanking();
			status=STATUS_MAIN_MENU;
		}
	}
	
	/*帮助操作*/
	private void processHelp() {
     if(keyState.contains(KeyCode.NUM0)){          //按0键返回主界面
    	 keyState.remove(KeyCode.NUM0);
    	 showGame.clearHelp();
    	 status=STATUS_MAIN_MENU;
     }
	}
	
	/*注意和界面按钮的顺序一致*/
	private void processSubMenu() {
		if (mainIndex == 0) { //新游戏
			status = STATUS_GAME_PLAYING;
			
		} else if (mainIndex == 1) {// 道具商城
			showGame.clearGamePlaying();
			status=STATUS_GAME_SHOP;
			
		} else if (mainIndex == 2){ //成就系统
			status=STATUS_GAME_ARCHI;
			
		} else if (mainIndex == 3) {// 排行榜
			status=STATUS_GAME_RANKING;
			
		} else if (mainIndex == 4) {// 游戏帮助
			status=STATUS_GAME_HELP;
			
		}else if(mainIndex==5){//退出游戏
			exit = true;
		} 
		showGame.clearMainMenu();
	}
}

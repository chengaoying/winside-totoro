package sheepwar;


import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateMain implements Common{
	
	private StateGame stateGame;
	private SheepWarGameEngine engine;
	public StateMain(SheepWarGameEngine engine, StateGame stateGame){
		this.engine = engine;
		this.stateGame = stateGame;
	}
	
	public int menuAxis[][] = { { 523, 243 }, { 466, 288 }, { 523, 333 },
			{ 466, 378 }, { 523, 423 }, { 466, 468 }, };
	
	private int mainIndex;
	
	public void handleKey(KeyState keyState){
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			//exit = true;
			clear();
		}
		if (keyState.containsAndRemove(KeyCode.UP)) {
			mainIndex = (mainIndex + 6 - 1) % 6;
		} else if (keyState.containsAndRemove(KeyCode.DOWN)) {
			mainIndex = (mainIndex + 1) % 6;
		} else if (keyState.containsAndRemove(KeyCode.OK)) {
			processSubMenu();
			clear();
		}
	}

	public void show(SGraphics g) {
		Image main_bg = Resource.loadImage(Resource.id_main_bg);
		Image main_menu = Resource.loadImage(Resource.id_main_menu);
		g.drawImage(main_bg, 0, 0, 0);
		for (int i = 0; i < menuAxis.length; ++i) {
			g.drawRegion(main_menu,
					(mainIndex != i) ? main_menu.getWidth() / 2 : 0,
					i * main_menu.getHeight() / 6, main_menu.getWidth() / 2,
					main_menu.getHeight() / 6, 0, menuAxis[i][0], 
					menuAxis[i][1], 0);
		}
	}
	
	public void execute(){
		if(mainIndex == 0){
			stateGame.weapon = new Weapon();
			stateGame.createRole = new CreateRole();
			stateGame.batches = new Batches();
			stateGame.own = stateGame.createRole.createSheep();
		}
	}
	
	/*注意和界面按钮的顺序一致*/
	private void processSubMenu() {
		if (mainIndex == 0) { //新游戏
			engine.status = STATUS_GAME_PLAYING;
			
		} else if (mainIndex == 1) {// 道具商城
			StateShop ss =  new StateShop();
			ss.processShop();
			
		} else if (mainIndex == 2){ //成就系统
			StateAttainment sa = new StateAttainment();
			sa.processAttainment();
			
		} else if (mainIndex == 3) {// 排行榜
			StateRanking sr = new StateRanking();
			sr.processRanking();
			
		} else if (mainIndex == 4) {// 游戏帮助
			StateHelp sh = new StateHelp();
			sh.processHelp();
			
		}else if(mainIndex==5){//退出游戏
			//exit = true;
		} 
	}

	private void clear() {
		Resource.freeImage(Resource.id_main_bg);
		Resource.freeImage(Resource.id_main_menu);
	}
}

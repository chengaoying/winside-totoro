package totoro;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;

/**
 * 选择龙猫界面
 * @author jackey
 *
 */
public class StateSelectInterface implements Common{

	
	private TotoroGameEngine engine;
	private StateGame stateGame;
	private int menuIndex;
	private int num;
	
	public int menuAxis[][] = {{20, 20}, {20, 92}, {20, 164}, {20, 236}};
	
	public StateSelectInterface(TotoroGameEngine e){
		engine = e;
		stateGame = e.stateGame;
	}
	
	public void handle(KeyState keyState, int mainIndex) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			engine.state = STATUS_MAIN_MENU;
			menuIndex = 0;
			Resource.clearSelectInterface();
		}else if(keyState.containsAndRemove(KeyCode.UP)){
			if(menuIndex>0){
				menuIndex--;
			}
		}else if(keyState.containsAndRemove(KeyCode.DOWN)){
			if(menuIndex<3){
				menuIndex++;
			}
		}else if(keyState.containsAndRemove(KeyCode.OK)){
			processSelectTotoro(mainIndex);
		}
		if(menuIndex==2){
			num = engine.pm.getPriceById(61);
		}else if(menuIndex==3){
			num = engine.pm.getPriceById(62);
		}else{
			num = 0;
		}
	}
	
	private void processSelectTotoro(int mainIndex){
		if((menuIndex==2 && !StateGame.hasTotoro3) || (menuIndex==3 && !StateGame.hasTotoro4)){
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText("龙猫未解锁,解锁需要"+num+engine.getEngineService().getExpendAmountUnit()+",是否解锁?");
			if(menuIndex==2){
				int index = pc.popup();
				if(index==0){
					if(engine.pm.buyProp(61, 1, null)){
						StateGame.hasTotoro3 = true;
					}
				}
			}else{
				int index = pc.popup();
				if(index==0){
					if(engine.pm.buyProp(62, 1, null)){
						StateGame.hasTotoro4 = true;
					}
				}
			}
		}else{
			if(mainIndex==0){
				stateGame.factory = MoveObjectFactory.getInstance();
				stateGame.objectShow = MoveObjectShow.getInstance();
				StateGame.player = stateGame.factory.createNewPlayer(menuIndex);
				StateGame.grade = StateGame.player.grade;
				StateGame.bombGrade = StateGame.player.bombGrade;
				StateGame.lifeNum = StateGame.player.lifeNum;
				StateGame.blood = StateGame.player.blood;
				/*StateGame.currLevel = 1;
				stateGame.level = 1;*/
				StateGame.batchIndex = 0;
				StateGame.level_over = false;
				StateGame.wingplaneNums = 0;
				StateGame.missileGrade = 0;
				StateGame.laserNums = 0;
				StateGame.startGameVentoseNums = 3; //开始游戏送3个保险
				//StateGame.scores = stateGame.player.scores;
				engine.state = STATUS_GAME_PLAYING;
				StateGame.game_status = GAME_PLAY;
				StateGame.level_start_time = System.currentTimeMillis()/1000;
				Resource.clearSelectInterface();
			}else{
				stateGame.factory = MoveObjectFactory.getInstance();
				stateGame.objectShow = MoveObjectShow.getInstance();
				StateGame.grade = menuIndex+1;
				StateGame.player = stateGame.factory.createPlayer(StateGame.grade);
				if(StateGame.wingplaneNums>0){
					for(int i=0;i<StateGame.wingplaneNums;i++){
						stateGame.factory.createWingplane(StateGame.player);
					}
				}
				if(StateGame.laserNums>0){
					stateGame.factory.createLaster(StateGame.player);
				}
				if(StateGame.level_over){
					stateGame.factory.createBoss(StateGame.currLevel);
					for(int i=stateGame.factory.boss.size()-1;i>=0;i--){
						MoveObject mo = (MoveObject) stateGame.factory.boss.elementAt(i);
						mo.blood = StateGame.bossBlood;
						StateGame.isCeateBoss = true;
					}
				}
				StateGame.startGameVentoseNums = 0;
				stateGame.level = StateGame.currLevel;
				engine.state = STATUS_GAME_PLAYING;
				Resource.clearSelectInterface();
				StateGame.game_status = GAME_PLAY;
				StateGame.level_start_time = System.currentTimeMillis()/1000 - StateGame.levelInterval;
			}
		}
	}
	
	int bgIndex ;
	int bombX = 66, bombY = 396, bombIndex;
	int pinkBombY = 376;
	int pinkBombY2 = 376+21;
	int pinkBombY3 = 376+42;
	int pinkBombY4 = 376+63;
	public void show(SGraphics g){
		Image button = Resource.loadImage(Resource.id_main_button);
		Image infoBg = Resource.loadImage(Resource.id_selectInterface_info_bg);
		Image head = Resource.loadImage(Resource.id_selectInterface_head);
		//Image head2 = Resource.loadImage(Resource.id_selectInterface_head2);
		Image lock = Resource.loadImage(Resource.id_selectInterface_lock);
		Image slant = Resource.loadImage(Resource.id_selectInterface_slant);
		Image text = Resource.loadImage(Resource.id_selectInterface_text);
		Image text2 = Resource.loadImage(Resource.id_selectInterface_text2);
		Image bg = Resource.loadImage(Resource.id_sky_game_bg);
		
		g.setColor(0);
		g.fillRect(0, 0, ScrW, ScrH);
		
		int buttonW = button.getWidth(), buttonH = button.getHeight()/2;
		int textW = text.getWidth(), textH = text.getHeight()/4;
		int text2W = text2.getWidth(), text2H = text2.getHeight()/4;
		int headW = head.getWidth()/4, headH = head.getHeight();
		//int head2W = head2.getWidth()/4, head2H = head2.getHeight();
		int slantW = slant.getWidth(), slantH = slant.getHeight();
		for(int i=0;i<4;i++){
			if (menuIndex == i){
				g.drawRegion(button, 0, 0, buttonW, buttonH, 0, menuAxis[i][0], menuAxis[i][1], 20);
			} else {
				g.drawRegion(button, 0, buttonH, buttonW, buttonH, 0, menuAxis[i][0], menuAxis[i][1], 20);
			}
			g.drawRegion(text, 0, textH*i, textW, textH, 0, menuAxis[i][0]+90, menuAxis[i][1]+15, 20);
			g.drawRegion(head, headW*i, 0, headW, headH, 0, menuAxis[i][0]+5, menuAxis[i][1], 20);
		}
		if(!StateGame.hasTotoro3){
			g.drawImage(lock, menuAxis[2][0]+2, menuAxis[2][1]+2, 20);
		}
		if(!StateGame.hasTotoro4){
			g.drawImage(lock, menuAxis[3][0]+2, menuAxis[3][1]+2, 20);
		}
		
		//g.drawRegion(head2, head2W*menuIndex, 0, head2W, head2H, 0, 10, ScrH-head2H-20, 20);
		
		int mapx = buttonW+45;
		int mapy = 23;
		g.drawImage(infoBg, mapx, mapy, 20);
		int x = mapx + 30;
		int y = mapy+30;
		int power = playerParam[menuIndex][13];
		int bulletPower = playerParam[menuIndex][14];
		int effect = playerParam[menuIndex][15];
		for(int j=0;j<4;j++){
			g.drawRegion(text2, 0, text2H*j, text2W, text2H, 0, x, y, 20);
			y += slantH+text2H+5;
		}
		y -= slantH+text2H+2;
		StateMain.drawNum(g, num, x+text2W/2+15, y);
		int col = g.getColor();
		g.setColor(0xffffff);
		engine.setFont(20, true);
		//g.drawString(engine.getEngineService().getExpendAmountUnit(), x+text2W/2+70, y, 20);
		g.drawString(engine.getEngineService().getExpendAmountUnit()+"数:"+engine.getEngineService().getBalance(), x+170, y, 20);
		g.setColor(col);
		engine.setDefaultFont();
		
		y = mapy+30+text2H+3;
		for(int k=0;k<power;k++){
			g.drawImage(slant, x, y, 20);
			x += slantW;
		}
		y += text2H+slantH+4;
		x = mapx + 30;
		for(int k=0;k<bulletPower;k++){
			g.drawImage(slant, x, y, 20);
			x += slantW;
		}
		y += text2H+slantH+4;
		x = mapx + 30;
		for(int k=0;k<effect;k++){
			g.drawImage(slant, x, y, 20);
			x += slantW;
		}
		
		int bgX = 26, bgY = 321, bgW = bg.getWidth(), bgH = bg.getHeight();
		g.setClip(bgX, bgY, 588, 190);
		bgIndex = (bgIndex+1)%bgW;
		g.drawRegion(bg, bgIndex, 0, bgW-bgIndex, bgH, 0, bgX, bgY, 20);
		g.drawRegion(bg, 0, 0, bgIndex, bgH, 0, bgW-bgIndex+bgX, bgY, 20);
		
		Image totoro ;
		int totoroW, totoroH, totoroX, totoroY;
		if(menuIndex==0){
			Image bomb, bomb2, bomb3;
			totoro = Resource.loadImage(Resource.id_yellow_totoro);
			bomb = Resource.loadImage(Resource.id_yellow_totoro_bomb1);
			bomb2 = Resource.loadImage(Resource.id_yellow_totoro_bomb2);
			bomb3 = Resource.loadImage(Resource.id_yellow_totoro_bomb3);
			int bombW = bomb.getWidth(), bombH = bomb.getHeight();
			int bomb2W = bomb2.getWidth(), bomb2H = bomb2.getHeight();
			int bomb3W = bomb3.getWidth(), bomb3H = bomb3.getHeight();
			g.drawRegion(bomb3, 0, 0, bomb3W, bomb3H, 0, bombX+10, bombY+12, 20);
			g.drawRegion(bomb, 0, 0, bombW, bombH, 0, bombX+13, bombY+50, 20);
			g.drawRegion(bomb2, 0, 0, bomb2W, bomb2H, 0, bombX, bombY, 20);
		}else if(menuIndex==1){
			Image bomb;
			totoro = Resource.loadImage(Resource.id_pink_totoro);
			bomb = Resource.loadImage(Resource.id_pink_totoro_bomb);
			int bombW = bomb.getWidth()/5;
			int bombH = bomb.getHeight();
			g.drawRegion(bomb, bombW*3, 0, bombW, bombH, 0, bombX, pinkBombY, 20);
			g.drawRegion(bomb, bombW*2, 0, bombW, bombH, 0, bombX, pinkBombY2, 20);
			g.drawRegion(bomb, bombW, 0, bombW, bombH, 0, bombX, pinkBombY3, 20);
			g.drawRegion(bomb, bombW*4, 0, bombW, bombH, 0, bombX, pinkBombY4, 20);
		}else if(menuIndex==2){
			Image bomb;
			totoro = Resource.loadImage(Resource.id_blue_totoro);
			bomb = Resource.loadImage(Resource.id_blue_totoro_bomb4);
			int bombW = bomb.getWidth();
			int bombH = bomb.getHeight();
			g.drawRegion(bomb, 0, 0, bombW, bombH, 0, bombX, bombY, 20);
			g.setClip(0, 0, ScrW, ScrH);
		}else{
			Image bomb;
			totoro = Resource.loadImage(Resource.id_black_totoro);
			bomb = Resource.loadImage(Resource.id_black_totoro_bomb3);
			int bombW = bomb.getWidth()/3;
			int bombH = bomb.getHeight();
			bombIndex = (bombIndex+1)%3;
			g.drawRegion(bomb, bombIndex*bombW, 0, bombW, bombH, 0, bombX, bombY, 20);
		}
		totoroW = totoro.getWidth()/3;
		totoroH = totoro.getHeight();
		totoroX = bgX;
		totoroY = bgY+95-totoroH/2;
		g.drawRegion(totoro, 0, 0, totoroW, totoroH, 0, totoroX, totoroY, 20);
		g.setClip(0, 0, ScrW, ScrH);
	}
	
	public void execute(){
		bombX += 35;
		if(menuIndex==1){
			pinkBombY -= 4;
			pinkBombY2 -= 3;
			pinkBombY3 += 3;
			pinkBombY4 += 4;
		}
		if(bombX+50>=610){
			bombX = 66;
			if(menuIndex==1){
				pinkBombY = 376;
				pinkBombY2 = 376+21;
				pinkBombY3 = 376+42;
				pinkBombY4 = 376+63;
			}
		}
	}

}

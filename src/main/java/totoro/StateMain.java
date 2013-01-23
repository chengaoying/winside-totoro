package totoro;
import javax.microedition.lcdui.Image;

import cn.ohyeah.itvgame.model.GameRanking;
import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.ui.TextView;

public class StateMain implements Common{
	
	public boolean exit;
	private StateGame stateGame;
	private TotoroGameEngine engine;
	public StateMain(TotoroGameEngine engine){
		this.engine = engine;
		this.stateGame = engine.stateGame;
	}
	
	public int menuAxis[][] = {{20, 20}, {20, 92}, {20, 164}, {20, 236}};
	public int menuAxis2[][] = {{517, 342}, {517, 428}};
	
	private int mainIndex;
	private boolean isRight;
	
	public void handleKey(KeyState keyState){
		if (keyState.containsAndRemove(KeyCode.BACK)) {
			exit = true;
			Resource.clearMain();
		}else if (keyState.containsAndRemove(KeyCode.UP)) {
			if(!isRight){
				if(mainIndex>0){
					mainIndex --;
				}
			}else{
				if(mainIndex<5){
					isRight = false;
				}
				mainIndex--;
			}
		} else if (keyState.containsAndRemove(KeyCode.DOWN)) {
			if(!isRight){
				mainIndex++;
				if(mainIndex>3){
					isRight = true;
				}
			}else{
				if(mainIndex<5){
					mainIndex++;
				}
			}
		} else if (keyState.containsAndRemove(KeyCode.LEFT)) {
			if(isRight){
				mainIndex = 0;
				isRight = false;
			}
		} else if (keyState.containsAndRemove(KeyCode.RIGHT)) {
			if(!isRight){
				mainIndex = 4;
				isRight = true;
			}
		} else if (keyState.containsAndRemove(KeyCode.OK)) {
			processSubMenu();
		}
	}

	public void show(SGraphics g) {
		Image bg = Resource.loadImage(Resource.id_main_bg);
		Image button = Resource.loadImage(Resource.id_main_button);
		Image button2 = Resource.loadImage(Resource.id_main_button2);
		Image text = Resource.loadImage(Resource.id_main_text);
		Image upgrade = Resource.loadImage(Resource.id_main_upgrade);
		Image buy = Resource.loadImage(Resource.id_main_buy);
		Image totoro = Resource.loadImage(Resource.id_main_totoro);
		Image coin = Resource.loadImage(Resource.id_main_coin);
		
		g.drawImage(bg, 0, 0, 20);
		int buttonW = button.getWidth(), buttonH = button.getHeight()/2;
		int button2W = button2.getWidth(), button2H = button2.getHeight()/2;
		int textW = text.getWidth(), textH = text.getHeight()/4;
		for(int i=0;i<4;i++){
			if (mainIndex == i){
				g.drawRegion(button, 0, 0, buttonW, buttonH, 0, menuAxis[i][0], menuAxis[i][1], 20);
				g.drawRegion(text, 0, textH*i, textW, textH, 0, menuAxis[i][0]+36, menuAxis[i][1]+15, 20);
			} else {
				g.drawRegion(button, 0, buttonH, buttonW, buttonH, 0, menuAxis[i][0], menuAxis[i][1], 20);
				g.drawRegion(text, 0, textH*i, textW, textH, 0, menuAxis[i][0]+36, menuAxis[i][1]+15, 20);
			}
		}
		
		int price;
		if(StateGame.wingplaneMaxNums==1){
			price = engine.pm.getPriceById(63);
		}else if(StateGame.wingplaneMaxNums==2){
			price = engine.pm.getPriceById(64);
		}else {
			price = engine.pm.getPriceById(65);
		}
		
		g.drawRegion(button2, 0, mainIndex==4?0:button2H, button2W, button2H, 0, menuAxis2[0][0], menuAxis2[0][1], 20);
		g.drawImage(upgrade, menuAxis2[0][0]+20, menuAxis2[0][1]+33, 20);
		g.drawImage(coin, menuAxis2[0][0]+20, menuAxis2[0][1]+8, 20);
		drawNum(g, price, menuAxis2[0][0]+36, menuAxis2[0][1]+10);
		
		g.drawRegion(button2, 0, mainIndex==5?0:button2H, button2W, button2H, 0, menuAxis2[1][0], menuAxis2[1][1], 20);
		g.drawImage(buy, menuAxis2[1][0]+15, menuAxis2[1][1]+20, 20);
		
		int totoroW = totoro.getWidth()/2, totoroH = totoro.getHeight();
		int totoroX = 372, totoroY = 364;
		for(int i=0;i<StateGame.wingplaneMaxNums;i++){
			g.drawRegion(totoro, totoroW, 0, totoroW, totoroH, 0, totoroX, totoroY, 20);
			totoroX += totoroW+1;
		}
		for(int i=0;i<4-StateGame.wingplaneMaxNums;i++){
			g.drawRegion(totoro, 0, 0, totoroW, totoroH, 0, totoroX, totoroY, 20);
			totoroX += totoroW+1;
		}
		g.setColor(0xffffff);
		if(mainIndex==4){
			TextView.showMultiLineText(g, descInfo[mainIndex-4]+",价格为:"+price, 2, 33, 330, 220, 145);
		}else if(mainIndex==5){
			TextView.showMultiLineText(g, descInfo[mainIndex-4]+",价格为:"+engine.pm.getPriceById(66), 2, 33, 330, 220, 145);
		}
		g.drawString("当前游戏币:"+engine.getEngineService().getBalance(), 33, 475, 20);
		
		/*排行数据*/
		if(engine.rankList!=null){
			int x = 340, y = 65, w = 160, h = 26, w1 = 125;
			int myRanking = 0;
			for(int i=0;i<engine.rankList.length;i++){
				GameRanking rank = engine.rankList[i];
				String userId = rank.getUserId();
				String scores = String.valueOf(rank.getScores());
				TextView.showSingleLineText(g, userId, x, y, w, h, 1);
				TextView.showSingleLineText(g, scores, x+w, y, w1, h, 1);
				y += h+13;
				if(userId.equals(engine.getEngineService().getUserId())){
					myRanking = rank.getRanking();
				}
			}
			drawNum(g, myRanking, 450, 272);
		}
		g.setColor(0);
		
		drawNum(g, StateGame.ventoseNum, 450, 448);
	}
	
	public static void drawNum(SGraphics g, int n, int x, int y) {
		Image num = Resource.loadImage(Resource.id_main_num);
		String number = String.valueOf(n);
		int numW = num.getWidth()/10, numH = num.getHeight();
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(num, (number.charAt(i) - '0') * numW, 0, numW, numH, 0, x, y, 0);
			x += numW+1;
		}
	}
		
	public void execute(){}
	
	private void processSubMenu() {
		if (mainIndex == 0) { 			//新游戏
			engine.state = STATUS_SELECT_TOTORO;
			Resource.clearMain();
		} else if(mainIndex == 1){		//继续游戏
			boolean result = engine.readRecord();
			if(!result){
				PopupText pt = UIResource.getInstance().buildDefaultPopupText();
				pt.setText("没有游戏记录,请重新开始游戏");
				pt.popup();
				mainIndex = 0;
			}else{
				stateGame.factory = MoveObjectFactory.getInstance();
				stateGame.objectShow = MoveObjectShow.getInstance();
				StateGame.player = stateGame.factory.createPlayer(StateGame.grade);
				engine.state = STATUS_GAME_PLAYING;
				StateGame.game_status = GAME_PLAY;
				StateGame.level_start_time = System.currentTimeMillis()/1000;
			}
		} else if (mainIndex == 2) {	//充值
			Recharge recharge = new Recharge(engine);
			recharge.recharge();
		} else if (mainIndex == 3){ 	//退出游戏
			Resource.clearMain();
			engine.saveRecord();
			exit = true;
		} else if (mainIndex == 4) {	//龙猫升级
			if(StateGame.wingplaneMaxNums<4){
				if(StateGame.wingplaneMaxNums==1){
					if(engine.pm.buyProp(63, 1)){
						StateGame.wingplaneMaxNums ++;
					}
				}else if(StateGame.wingplaneMaxNums==2){
					if(engine.pm.buyProp(64, 1)){
						StateGame.wingplaneMaxNums ++;
					}
				}else {
					if(engine.pm.buyProp(65, 1)){
						StateGame.wingplaneMaxNums ++;
					}
				}
			}else{
				PopupText pt = UIResource.getInstance().buildDefaultPopupText();
				pt.setText("守护精灵个数已达上线");
				pt.popup();
			}
		} else if (mainIndex == 5) {	//购买
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText("是否要购买道具必杀技?");
			int index = pc.popup();
			if(index == 0){
				if(engine.pm.buyProp(66, 1)){
					StateGame.ventoseNum ++;
				}
			}
		}
	}
}

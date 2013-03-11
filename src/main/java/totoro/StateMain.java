package totoro;
import javax.microedition.lcdui.Image;

import cn.ohyeah.itvgame.model.GameRanking;
import cn.ohyeah.stb.game.Configurations;
import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.ui.TextView;

public class StateMain implements Common{
	
	public boolean exit;
	//private StateGame stateGame;
	private TotoroGameEngine engine;
	public StateMain(TotoroGameEngine engine){
		this.engine = engine;
		//this.stateGame = engine.stateGame;
	}
	
	public int menuAxis[][] = {{20, 20}, {20, 92}, {20, 164}, {20, 236}, {20, 308}};
	public int menuAxis2[][] = {{517, 342}, {517, 428}};
	
	public static int mainIndex;
	//private boolean isRight;
	
	public void handleKey(KeyState keyState){
		if (keyState.containsAndRemove(KeyCode.BACK)) {
			exit = true;
			Resource.clearMain();
		}else if (keyState.containsAndRemove(KeyCode.UP)) {
			/*if(!isRight){
				if(mainIndex>0){
					mainIndex --;
				}
			}else{
				if(mainIndex<5){
					isRight = false;
				}
				mainIndex--;
			}*/
			mainIndex = (mainIndex + 5 - 1) % 5;
		} else if (keyState.containsAndRemove(KeyCode.DOWN)) {
			/*if(!isRight){
				mainIndex++;
				if(mainIndex>3){
					isRight = true;
				}
			}else{
				if(mainIndex<5){
					mainIndex++;
				}
			}*/
			mainIndex = (mainIndex + 1) % 5;
		} /*else if (keyState.containsAndRemove(KeyCode.LEFT)) {
			if(isRight){
				mainIndex = 0;
				isRight = false;
			}
		} else if (keyState.containsAndRemove(KeyCode.RIGHT)) {
			if(!isRight){
				mainIndex = 4;
				isRight = true;
			}
		}*/ else if (keyState.containsAndRemove(KeyCode.OK)) {
			processSubMenu();
		}
	}

	public void show(SGraphics g) {
		Image bg = Resource.loadImage(Resource.id_main_bg);
		Image button = Resource.loadImage(Resource.id_main_button);
		Image text = Resource.loadImage(Resource.id_main_text);
		
		g.drawImage(bg, 0, 0, 20);
		int buttonW = button.getWidth(), buttonH = button.getHeight()/2;
		int textW = text.getWidth(), textH = text.getHeight()/5;
		for(int i=0;i<5;i++){
			if (mainIndex == i){
				g.drawRegion(button, 0, 0, buttonW, buttonH, 0, menuAxis[i][0], menuAxis[i][1], 20);
				g.drawRegion(text, 0, textH*i, textW, textH, 0, menuAxis[i][0]+36, menuAxis[i][1]+15, 20);
			} else {
				g.drawRegion(button, 0, buttonH, buttonW, buttonH, 0, menuAxis[i][0], menuAxis[i][1], 20);
				g.drawRegion(text, 0, textH*i, textW, textH, 0, menuAxis[i][0]+36, menuAxis[i][1]+15, 20);
			}
		}
		
		/*排行数据*/
		g.setColor(0xffffff);
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
			drawNum(g, myRanking, 450, 490);
		}
		g.setColor(0);
		
		//drawNum(g, StateGame.ventoseNum, 450, 448);
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
		boolean result = engine.readRecord();
		if (mainIndex == 0) { 			//新游戏
			if(result){
				PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
				pc.setText("你有存档是否覆盖重新开始?");
				if(pc.popup()==0){
					engine.state = STATUS_SELECT_TOTORO;
					Resource.clearMain();
				}else{
					mainIndex = 1;
				}
			}else{
				engine.state = STATUS_SELECT_TOTORO;
				Resource.clearMain();
			}
		} else if(mainIndex == 1){		//继续游戏
			if(!result){
				PopupText pt = UIResource.getInstance().buildDefaultPopupText();
				pt.setText("没有游戏记录,请重新开始游戏");
				pt.popup();
				mainIndex = 0;
			}else{
				engine.state = STATUS_SELECT_TOTORO;
				Resource.clearMain();
			}
		} else if (mainIndex == 2) {	//充值
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			if(Configurations.getInstance().isTelcomOperatorsTelcomfj()){
				pc.setText("是否退出游戏并跳转至大厅充值界面?");
				if (pc.popup() == 0) {
					ServiceWrapper sw = engine.getServiceWrapper();
					sw.gotoRechargePage();
					PopupText pt = UIResource.getInstance().buildDefaultPopupText();
					if (sw.isServiceSuccessful()) {
						exit = true;
					} 
					else {
						pt.setText("跳转至大厅充值界面失败, 原因: "+sw.getServiceMessage());
						pt.popup();
					}
				}
			}else{
				Recharge recharge = new Recharge(engine);
				recharge.recharge();
			}
		} else if (mainIndex == 3){ 	//游戏帮助
			StateHelp sh = new StateHelp(engine);
			sh.processHelp();
		} else if (mainIndex == 4) {	//退出游戏
			Resource.clearMain();
			//engine.saveRecord();
			exit = true;
			/*if(StateGame.wingplaneMaxNums<4){
				PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
				pc.setText("是否要购买守护精灵?");
				int index = pc.popup();
				if(index==0){
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
				}
			}else{
				PopupText pt = UIResource.getInstance().buildDefaultPopupText();
				pt.setText("守护精灵个数已达上线");
				pt.popup();
			}*/
		} /*else if (mainIndex == 5) {	//购买
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText("是否要购买道具必杀技?");
			int index = pc.popup();
			if(index == 0){
				if(engine.pm.buyProp(66, 1)){
					StateGame.ventoseNum ++;
				}
			}
		}*/
	}
}

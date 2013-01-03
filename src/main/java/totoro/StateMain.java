package totoro;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

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
			clear();
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
			clear();
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
		int buttonW = button.getWidth(), buttonH = button.getHeight()/3;
		int button2W = button2.getWidth(), button2H = button2.getHeight()/2;
		int textW = text.getWidth(), textH = text.getHeight()/5;
		for(int i=0;i<4;i++){
			if (mainIndex == i){
				g.drawRegion(button, 0, 0, buttonW, buttonH, 0, menuAxis[i][0], menuAxis[i][1], 20);
				g.drawRegion(text, 0, textH*i, textW, textH, 0, menuAxis[i][0]+36, menuAxis[i][1]+15, 20);
			} else {
				g.drawRegion(button, 0, buttonH, buttonW, buttonH, 0, menuAxis[i][0], menuAxis[i][1], 20);
				g.drawRegion(text, 0, textH*i, textW, textH, 0, menuAxis[i][0]+36, menuAxis[i][1]+15, 20);
			}
		}
		
		g.drawRegion(button2, 0, mainIndex==4?0:button2H, button2W, button2H, 0, menuAxis2[0][0], menuAxis2[0][1], 20);
		g.drawImage(upgrade, menuAxis2[0][0]+20, menuAxis2[0][1]+33, 20);
		g.drawImage(coin, menuAxis2[0][0]+20, menuAxis2[0][1]+8, 20);
		drawNum(g, 88, menuAxis2[0][0]+36, menuAxis2[0][1]+10);
		
		g.drawRegion(button2, 0, mainIndex==5?0:button2H, button2W, button2H, 0, menuAxis2[1][0], menuAxis2[1][1], 20);
		g.drawImage(buy, menuAxis2[1][0]+15, menuAxis2[1][1]+20, 20);
		
		int totoroW = totoro.getWidth()/2, totoroH = totoro.getHeight();
		int totoroX = 372, totoroY = 364;
		for(int i=0;i<4;i++){
			g.drawRegion(totoro, 0, 0, totoroW, totoroH, 0, totoroX, totoroY, 20);
			totoroX += totoroW+1;
		}
		
		drawNum(g, 100, 450, 452);
	}
	
	private void drawNum(SGraphics g, int n, int x, int y) {
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
			stateGame.factory = MoveObjectFactory.getInstance();
			stateGame.objectShow = MoveObjectShow.getInstance();
			stateGame.player = stateGame.factory.createNewPlayer();
			engine.state = STATUS_GAME_PLAYING;
			clear();
		} else if(mainIndex == 1){		//继续游戏
			
		} else if (mainIndex == 2) {	//充值
			
		} else if (mainIndex == 3){ 	//退出游戏
			clear();
			exit = true;
		} else if (mainIndex == 4) {	//龙猫升级
			
		} else if (mainIndex == 5) {	//购买
		}
	}

	private void clear() {
		Resource.freeImage(Resource.id_main_bg);
		Resource.freeImage(Resource.id_main_button);
		Resource.freeImage(Resource.id_main_button2);
		Resource.freeImage(Resource.id_main_buy);
		Resource.freeImage(Resource.id_main_coin);
		Resource.freeImage(Resource.id_main_num);
		Resource.freeImage(Resource.id_main_text);
		Resource.freeImage(Resource.id_main_totoro);
		Resource.freeImage(Resource.id_main_upgrade);
	}
}

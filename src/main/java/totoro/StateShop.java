package totoro;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.ui.TextView;

public class StateShop implements Common{
	private TotoroGameEngine engine;
	private boolean running;
	private int menuIndex;
	
	public StateShop(TotoroGameEngine e){
		engine = e;
	}
	
	public int processShop(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleShop(keyState, g);
				if (running) {
					long t1 = System.currentTimeMillis();
					showShop(g);
					engine.flushGraphics();
					System.gc();
					int sleepTime = (int)(125-(System.currentTimeMillis()-t1));
					if (sleepTime <= 0) {
						Thread.sleep(0);
					}
					else {
						Thread.sleep(sleepTime);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			Resource.clearShopPic();
		}
		return menuIndex;
	}

	private void showShop(SGraphics g) {
		Image bg = Resource.loadImage(Resource.id_shop_bg);
		Image button = Resource.loadImage(Resource.id_shop_button);
		Image buy = Resource.loadImage(Resource.id_shop_buy);
		//Image coin = Resource.loadImage(Resource.id_shop_coin);
		Image totoro = Resource.loadImage(Resource.id_shop_totoro);
		Image totoro2 = Resource.loadImage(Resource.id_shop_totoro2);
		Image upgrade = Resource.loadImage(Resource.id_shop_upgrade);
		
		int bgW = bg.getWidth(), bgH = bg.getHeight();
		int buttonW = button.getWidth(), buttonH = button.getHeight()/2;
		int totoroW = totoro.getWidth()/2, totoroH = totoro.getHeight();
		
		int x = ScrW/2 - bgW/2, y = ScrH/2 - bgH/2;
		g.drawImage(bg, x, y, 20);
		
		int totoro2X = x+24, totoro2Y = y+64;
		g.drawImage(totoro2, totoro2X, totoro2Y, 20);
		
		int totoroX = totoro2X+60, totoroY = totoro2Y+26;
		for(int i=0;i<StateGame.wingplaneMaxNums;i++){
			g.drawRegion(totoro, totoroW, 0, totoroW, totoroH, 0, totoroX, totoroY, 20);
			totoroX += totoroW+1;
		}
		for(int i=0;i<4-StateGame.wingplaneMaxNums;i++){
			g.drawRegion(totoro, 0, 0, totoroW, totoroH, 0, totoroX, totoroY, 20);
			totoroX += totoroW+1;
		}
		
		int price;
		if(StateGame.wingplaneMaxNums==1){
			price = engine.pm.getPriceById(63);
		}else if(StateGame.wingplaneMaxNums==2){
			price = engine.pm.getPriceById(64);
		}else {
			price = engine.pm.getPriceById(65);
		}
		
		int buttonX = totoroX+5, buttonY = totoro2Y+15;
		int upgradeX = buttonX+10, upgradeY = buttonY+4;
		int coinX = buttonX/*+8*/, coinY = upgradeY+22;
		g.drawRegion(button, 0, menuIndex==0?0:buttonH, buttonW, buttonH, 0, buttonX, buttonY, 20);
		g.drawImage(upgrade, upgradeX, upgradeY, 20);
		//g.drawImage(coin, coinX, coinY, 20);
		StateMain.drawNum(g, price, coinX+16, coinY);
		
		buttonY += buttonH+40;
		int buyX = buttonX+5, buyY = buttonY+12;
		g.drawRegion(button, 0, menuIndex==1?0:buttonH, buttonW, buttonH, 0, buttonX, buttonY, 20);
		g.drawImage(buy, buyX, buyY, 20);
		
		StateMain.drawNum(g, StateGame.ventoseNum+StateGame.startGameVentoseNums, 235, 328);
		
		g.setColor(0xffffff);
		if(menuIndex==0){
			TextView.showMultiLineText(g, descInfo[0]+",价格为:"+price, 2, x+315, y+80, 160, 100);
		}else if(menuIndex==1){
			TextView.showMultiLineText(g, descInfo[1]+",价格为:"+engine.pm.getPriceById(66), 2, x+315, y+80, 160, 100);
		}
		
		StateMain.drawNum(g, engine.getEngineService().getBalance(), x+385, y+199);
	}
	
	private void handleShop(KeyState keyState, SGraphics g) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
			menuIndex = 0;
		}else if(keyState.containsAndRemove(KeyCode.UP)){
			menuIndex = 0;
		}else if(keyState.containsAndRemove(KeyCode.DOWN)){
			menuIndex = 1;
		}else if(keyState.containsAndRemove(KeyCode.OK)){
			if(menuIndex == 0){
				if(StateGame.wingplaneMaxNums<4){
					PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
					pc.setText("是否要购买守护精灵?");
					int index = pc.popup();
					if(index==0){
						if(StateGame.wingplaneMaxNums==1){
							if(engine.pm.buyProp(63, 1, g)){
								StateGame.wingplaneMaxNums ++;
							}
						}else if(StateGame.wingplaneMaxNums==2){
							if(engine.pm.buyProp(64, 1, g)){
								StateGame.wingplaneMaxNums ++;
							}
						}else {
							if(engine.pm.buyProp(65, 1, g)){
								StateGame.wingplaneMaxNums ++;
							}
						}
					}
				}else{
					PopupText pt = UIResource.getInstance().buildDefaultPopupText();
					pt.setText("守护精灵个数已达上线");
					pt.popup();
				}
			}else{
				PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
				pc.setText("是否要购买道具必杀技?");
				int index = pc.popup();
				if(index == 0){
					if(engine.pm.buyProp(66, 1, g)){
						StateGame.ventoseNum ++;
					}
				}
			}
		}
	}
}

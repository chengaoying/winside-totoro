package totoro;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

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
				handleShop(keyState);
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
		
	}
	
	private void handleShop(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
			menuIndex = 0;
		}else if(keyState.containsAndRemove(KeyCode.UP)){
			menuIndex = 0;
		}else if(keyState.containsAndRemove(KeyCode.DOWN)){
			menuIndex = 1;
		}else if(keyState.containsAndRemove(KeyCode.OK)){
			running = false;
		}
	}
}

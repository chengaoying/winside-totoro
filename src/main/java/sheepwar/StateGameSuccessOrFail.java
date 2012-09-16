package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;

public class StateGameSuccessOrFail {

	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	
	public void processGameSuccessOrFail(boolean isSuccess){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleGameSuccessOrFail(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showGameSuccessOrFail(g, isSuccess);
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
			clear();
		}
		
	}
	
	private void showGameSuccessOrFail(SGraphics g, boolean isSuccess) {
		Image bg = Resource.loadImage(Resource.id_game_bg);
		g.drawImage(bg, 0, 0, 20);
		System.out.println("游戏结果："+isSuccess);
		if(isSuccess){
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			pt.setText("恭喜你通关成功，按确认键查看排行榜");
		}else{
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			pt.setText("游戏失败，按确认键返回主菜单");
		}
		
	}
	
	private void handleGameSuccessOrFail(KeyState keyState) {
	    if(keyState.containsAndRemove(KeyCode.OK)){ 
	    	running = false;
		}
	}

	private void clear() {
		
	}

}

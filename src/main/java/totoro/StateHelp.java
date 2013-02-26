package totoro;


import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.ui.TextView;

public class StateHelp implements Common{
	private TotoroGameEngine engine;
	private boolean running;
	private int menuIndex;
	
	public StateHelp(TotoroGameEngine e){
		engine = e;
	}
	
	public int processHelp(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleHelp(keyState, g);
				if (running) {
					long t1 = System.currentTimeMillis();
					showHelp(g);
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

	private void showHelp(SGraphics g) {
		g.setColor(0x4b4b4b);
		g.fillRect(0, 0, ScrW, ScrH);
		g.setColor(0x1e6094);
		int w = 565, h = 420;
		int x = ScrW/2 - w/2, y = ScrH/2 - h/2;
		g.fillRect(x, y, w, h);
		g.setColor(0xffffff);
		String str = helpInfo+helpInfo2+helpInfo3;
		TextView.showMultiLineText(g, str, 3, x, y, w, h);
	}
	
	private void handleHelp(KeyState keyState, SGraphics g) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
			menuIndex = 0;
		}else if(keyState.containsAndRemove(KeyCode.UP)){
			menuIndex = 0;
		}else if(keyState.containsAndRemove(KeyCode.DOWN)){
			menuIndex = 1;
		}else if(keyState.containsAndRemove(KeyCode.OK)){}
	}
}

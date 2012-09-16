package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

/**
 * ½±Àø¹Ø¿¨
 * @author Administrator
 *
 */
public class StateRewardLevel implements Common{
	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	
	public void processRewardLevel(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleRewardLevel(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showRewardLevel(g);
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
	
	private void showRewardLevel(SGraphics g) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		g.drawImage(game_bg, 0, 0, 20);
	}
	
	
	private void handleRewardLevel(KeyState keyState) {
	    if(keyState.containsAndRemove(KeyCode.OK)){ 
	    	running = false;
		}
	}

	private void clear() {
		// TODO Auto-generated method stub
		
	}

}

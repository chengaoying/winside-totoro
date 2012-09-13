package sheepwar;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateNextLevel implements Common{
	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	
	public void processNextLevel(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleNextLevel(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showNextLevel(g);
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

	private void showNextLevel(SGraphics g) {
		
	}
	
	private void handleNextLevel(KeyState keyState) {
	    if(keyState.containsAndRemove(KeyCode.OK)){ 
	    	running = false;
		}
	}

	private void clear() {
		
	}
}

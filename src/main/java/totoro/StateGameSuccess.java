package totoro;

import javax.microedition.lcdui.Font;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.ui.DrawUtil;

public class StateGameSuccess implements Common{

	
	private TotoroGameEngine engine;
	private boolean running;
	private int failIndex;
	private StateGame stateGame;
	
	public StateGameSuccess(TotoroGameEngine e, StateGame stateGame){
		engine = e;
		this.stateGame = stateGame;
	}
	
	public int processGameSuccess(int scores){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleGameSuccess(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showGameSuccess(g, scores);
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
			Resource.clearGameFail();
		}
		return failIndex;
	}

	private void showGameSuccess(SGraphics g, int scores) {
		stateGame.show(g);
		String str = "恭喜你成功通关,总分"+scores+"分,按确认返回主界面";
		Font font = g.getFont();    
		int textW = font.stringWidth(str);
		int w = textW+30;
		int h = 30;
		int x = ScrW/2-w/2;
		int y = ScrH/2-h/2;
		g.setColor(0x000000);
		DrawUtil.drawRect(g, x, y, w, h);
		x += w/2 - textW/2;
		y += 4;
		g.setColor(0xffff00);
		g.drawString(str, x, y, 20);
	}
	
	private void handleGameSuccess(KeyState keyState) {
		if(keyState.containsAndRemove(KeyCode.LEFT)){
			failIndex = 0;
		}else if(keyState.containsAndRemove(KeyCode.RIGHT)){
			failIndex = 1;
		}else if(keyState.containsAndRemove(KeyCode.OK)){
			running = false;
		}
	}

}

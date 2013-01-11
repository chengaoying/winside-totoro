package totoro;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateSubMenu implements Common{

	
	private TotoroGameEngine engine;
	private boolean running;
	private int menuIndex;
	
	public StateSubMenu(TotoroGameEngine e){
		engine = e;
	}
	
	public int processSubMenu(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleSubMenu(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showSubMenu(g);
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
			Resource.clearSubMenuPic();
		}
		return menuIndex;
	}

	private void showSubMenu(SGraphics g) {
		Image bg = Resource.loadImage(Resource.id_pause_bg);
		Image text = Resource.loadImage(Resource.id_pause_text);
		Image button = Resource.loadImage(Resource.id_main_button);
		
		int bgW = bg.getWidth(), bgH = bg.getHeight();
		int textW = text.getWidth(), textH = text.getHeight()/2;
		int buttonW = button.getWidth(), buttonH = button.getHeight()/2;
		int x = ScrW/2 - bgW/2, y = startP + gameH/2 - bgH/2;
		g.drawImage(bg, x, y, 20);
		x += (bgW-buttonW)/2;
		y += bgH/3-buttonH/2-10;
		int textx = x+(buttonW-textW)/2;
		int texty = y+(buttonH-textH)/2;
		for(int i=0;i<2;i++){
			if(i == menuIndex){
				g.drawRegion(button, 0, 0, buttonW, buttonH, 0, x, y, 20);
			}else{
				g.drawRegion(button, 0, buttonH, buttonW, buttonH, 0, x, y, 20);
			}
			g.drawRegion(text, 0, i*textH, textW, textH, 0, textx, texty, 20);
			y += buttonH+15;
			texty += buttonH+15;
		}
	}
	
	private void handleSubMenu(KeyState keyState) {
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

package totoro;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.ui.DrawUtil;

public class StateGameFail implements Common{

	
	private TotoroGameEngine engine;
	private boolean running;
	private int failIndex=1;
	private long startTime, endTime;
	private int interval;
	
	public StateGameFail(TotoroGameEngine e){
		engine = e;
		startTime = System.currentTimeMillis()/1000;
	}
	
	public int processGameFail(int level){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				
				endTime = System.currentTimeMillis()/1000;
				interval = (int) (10-(endTime-startTime));
				
				handleGameFail(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showGameFail(g, level);
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

	private void showGameFail(SGraphics g, int count) {
		Image bg = Resource.loadImage(Resource.id_fail_bg);
		Image text = Resource.loadImage(Resource.id_fail_text);
		//Image fail = Resource.loadImage(Resource.id_fail);
		Image button = Resource.loadImage(Resource.id_shop_button);
		
		int bgW = bg.getWidth(), bgH = bg.getHeight();
		int textW = text.getWidth(), textH = text.getHeight()/2;
		int buttonW = button.getWidth(), buttonH = button.getHeight()/2;
		//int failW = fail.getWidth(), failH = fail.getHeight();
		
		int w = bgW+30;
		int h = bgH+50;
		int x = ScrW/2-w/2;
		int y = ScrH/2-h/2;
		g.setColor(0X000000);
		DrawUtil.drawRect(g, x, y, w, h);
		g.setColor(0xffffff);
		
		int bgX = ScrW/2-bgW/2;
		int bgY = ScrH/2-bgH/2 - 15;
		g.drawImage(bg, bgX, bgY, 20);
		int buttonX = x + 90;
		int buttonY = y + h-buttonH;
		int textX = buttonX + buttonW/2-textW/2;
		int textY = buttonY + buttonH/2-textH/2;
		for(int i=0;i<2;i++){
			if(i == failIndex){
				g.drawRegion(button, 0, 0, buttonW, buttonH, 0, buttonX, buttonY, 20);
			}else{
				g.drawRegion(button, 0, buttonH, buttonW, buttonH, 0, buttonX, buttonY, 20);
			}
			g.drawRegion(text, 0, i*textH, textW, textH, 0, textX, textY, 20);
			buttonX += buttonW+15;
			textX = buttonX + buttonW/2-textW/2;
		}
		
		StateMain.drawNum(g, count, x+235, y+113);
		drawNum(g, interval, bgX+25, bgY+50);
	}
	
	public static void drawNum(SGraphics g, int n, int x, int y) {
		Image num = Resource.loadImage(Resource.id_fail_num);
		String number = String.valueOf(n);
		int numW = num.getWidth()/10, numH = num.getHeight();
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(num, (number.charAt(i) - '0') * numW, 0, numW, numH, 0, x, y, 0);
			x += numW+1;
		}
	}
	
	private void handleGameFail(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
			failIndex = 1;
		}else if(keyState.containsAndRemove(KeyCode.LEFT)){
			failIndex = 0;
		}else if(keyState.containsAndRemove(KeyCode.RIGHT)){
			failIndex = 1;
		}else if(keyState.containsAndRemove(KeyCode.OK)){
			running = false;
		}
		if(interval <= 0){
			failIndex = 1;
			running = false;
		}
	}

}

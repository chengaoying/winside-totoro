package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.ui.TextView;

public class StateHelp implements Common{
	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	private int helpIndex, helpX;
	private int pageIndex;
	
	public void processHelp(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleHelp(keyState);
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
			clear();
		}
		
	}

	private void showHelp(SGraphics g) {

		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_big = Resource.loadImage(Resource.id_shop_big);       //{137,108}
		Image game_help = Resource.loadImage(Resource.id_game_help);     //{214,18}
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);   //{17,498}
		Image achievement_left_right = Resource.loadImage(Resource.id_achievement_left_right);   //{}
		Image achievement_left_right1 = Resource.loadImage(Resource.id_achievement_left_right1);   //{380,452}
		Image slash = Resource.loadImage(Resource.id_slash);
		g.drawImage(game_bg, 0, 0, 20);
		g.drawImage(shop_big, 137, 108, 20);
		
		int helpLeftRightX = 380,helpLeftRightY = 452,sapceLeftRight = 52;				//°ïÖú½çÃæÖÐµÄ°´Å¥ºá×Ý×ø±ê,sapceLeftRight×óÓÒ¼ä¾à
		int helpShadowX = 4,helpShadowY = 4;
		g.drawImage(slash, helpLeftRightX+achievement_left_right.getWidth()/2+15, helpLeftRightY+7, 20);
		drawNum(g, 3, helpLeftRightX+achievement_left_right.getWidth()/2+slash.getWidth()+14, helpLeftRightY+8);
		g.drawRegion(achievement_left_right1, 0, 0, achievement_left_right1.getWidth()/2, 		//·­Ò³×ó°´Å¥
				achievement_left_right1.getHeight(), 0, helpLeftRightX, helpLeftRightY, 20);
		g.drawRegion(achievement_left_right1, achievement_left_right1.getWidth()/2, 0,			//·­Ò³ÓÒ°´Å¥
				achievement_left_right1.getWidth()/2, achievement_left_right1.getHeight(),
				0, helpLeftRightX+sapceLeftRight+achievement_left_right1.getWidth()/2, helpLeftRightY, 20);
		if(helpX == 0){
			g.drawRegion(achievement_left_right, 0, 0, achievement_left_right.getWidth()/2, 		//·­Ò³×ó°´Å¥
					achievement_left_right.getHeight(), 0, helpLeftRightX-helpShadowX, helpLeftRightY-helpShadowY, 20);
			drawNum(g,helpIndex+1,helpLeftRightX+achievement_left_right.getWidth()/2+3,helpLeftRightY+8); 		//Ò³ÃæÂë
			g.drawRegion(achievement_left_right, 1*achievement_left_right.getWidth()/2, 0, achievement_left_right.getWidth()/2,
					achievement_left_right.getHeight(), 0, helpLeftRightX+sapceLeftRight+achievement_left_right.getWidth()/2,
					helpLeftRightY, 20);
		}else if(helpX == 1){
			g.drawRegion(achievement_left_right, 0, 0, achievement_left_right.getWidth()/2, 		//·­Ò³×ó°´Å¥
					achievement_left_right.getHeight(), 0, helpLeftRightX, helpLeftRightY, 20);
			drawNum(g,helpIndex+1,helpLeftRightX+achievement_left_right.getWidth()/2+3,helpLeftRightY+8);; 		//Ò³ÃæÂë
			g.drawRegion(achievement_left_right, 1*achievement_left_right.getWidth()/2, 0, achievement_left_right.getWidth()/2,
					achievement_left_right.getHeight(), 0, helpLeftRightX-helpShadowX+sapceLeftRight+achievement_left_right.getWidth()/2,
					helpLeftRightY-helpShadowY, 20);
		}else{
			g.drawRegion(achievement_left_right, 0, 0, achievement_left_right.getWidth()/2, 		//·­Ò³×ó°´Å¥
					achievement_left_right.getHeight(), 0, helpLeftRightX, helpLeftRightY, 20);
			drawNum(g,helpIndex+1,helpLeftRightX+achievement_left_right.getWidth()/2+3,helpLeftRightY+8); 		//Ò³ÃæÂë
			g.drawRegion(achievement_left_right, achievement_left_right.getWidth()/2, 0,			//·­Ò³ÓÒ°´Å¥
					achievement_left_right1.getWidth()/2, achievement_left_right1.getHeight(),
					0, helpLeftRightX+sapceLeftRight+achievement_left_right.getWidth()/2, helpLeftRightY, 20);
		}
		g.drawImage(game_help, 214,18, 20);
		g.drawImage(achievement_out1, 17,498, 20);
		g.setColor(0xffffff);				//ÉèÖÃ×ÖÌåÑÕÉ«
		engine.setFont(19);					//ÉèÖÃ×ÖÌå´óÐ¡
		TextView.showMultiLineText(g, Resource.gameInfo[helpIndex], 8,150, 130, 360, 334);			//Ð´³öÃèÊöÐÅÏ¢
		engine.setDefaultFont();
	
	}
	
	private void handleHelp(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
		}
		if (keyState.containsAndRemove(KeyCode.OK)
				|| keyState.containsAndRemove(KeyCode.LEFT)
				|| keyState.containsAndRemove(KeyCode.RIGHT)) { // ·­Ò³µÄÅÐ¶Ï
			if (pageIndex == 0) {
				if (helpIndex > 0) {
					helpIndex--;
				}
			}
			if (pageIndex == 1) {
				if (helpIndex < 2) {
					helpIndex++;
				}
			}
		}
		if (keyState.containsAndRemove(KeyCode.LEFT)) {
			pageIndex = 0;
			if (helpX > 0) {
				helpX = helpX - 1;
			} else {
				helpX = 0;
			}
		}
		if (keyState.containsAndRemove(KeyCode.RIGHT)) {
			pageIndex = 1;
			if (helpX < 1) {
				helpX = helpX + 1;
			} else {
				helpX = 1;
			}
		}
	}

	private void drawNum(SGraphics g, int num, int x, int y) {
		Image imgNumeber = Resource.loadImage(Resource.id_shop_figure);
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(imgNumeber, (number.charAt(i) - '0') * imgNumeber.getWidth()/10, 0, 
					imgNumeber.getWidth()/10, imgNumeber.getHeight(), 0, x + i * (imgNumeber.getWidth()/10 + 1), y, 0);
		}
	}
	
	private void clear() {
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_game_help);
    	Resource.freeImage(Resource.id_achievement_left_right);
    	Resource.freeImage(Resource.id_achievement_left_right1);
    	Resource.freeImage(Resource.id_shop_figure);
    	Resource.freeImage(Resource.id_slash);
	
	}
}

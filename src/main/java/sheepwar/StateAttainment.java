package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateAttainment implements Common{
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	private int  archY,  rightY, archIndex, bX;
	private boolean isRight;  //判断是左边还是右边
	private boolean isBotton; //判断是否在底部（翻页按钮上）
	
	public void processAttainment(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleAttainment(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					ShowAttainment(g);
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

	private void ShowAttainment(SGraphics g) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_midding = Resource.loadImage(Resource.id_shop_midding);//{28,102}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{235,102}
		Image achievement_left = Resource.loadImage(Resource.id_achievement_left);//{457,381}//{51,123},{51,178},{51,232},{51,286},{51,342},{51,396}
		Image achievement = Resource.loadImage(Resource.id_achievement);//{270,19}
		Image achievement_left_right = Resource.loadImage(Resource.id_achievement_left_right);//{458,441}
		Image achievement_left_right1 = Resource.loadImage(Resource.id_achievement_left_right1);
		Image achievement_long = Resource.loadImage(Resource.id_achievement_long);//{247,114},{247,198},{247,277},{247,361},{}
		Image achievement_long1 = Resource.loadImage(Resource.id_achievement_long1);
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);//{55,451}
		Image achievement_points = Resource.loadImage(Resource.id_achievement_points);//{250,448}
		Image archivement_hoof = Resource.loadImage(Resource.id_archivement_hoof);//{539,130},{539,211},{539,293},{539,378}
		Image archivement_hoof1 = Resource.loadImage(Resource.id_archivement_hoof1);
		Image achievement_word = Resource.loadImage(Resource.id_achievement_word);
		Image slash = Resource.loadImage(Resource.id_slash);
		Image achievement_left1 = Resource.loadImage(Resource.id_shop_out_base);
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(achievement, 270, 19, TopLeft);
		g.drawImage(shop_midding, 28, 102, TopLeft);
		g.drawImage(achievement_out1, 55, 451, TopLeft);
		g.drawImage(shop_big, 235, 102, TopLeft);
		g.drawImage(achievement_points, 250, 448, TopLeft);
		g.drawImage(slash, 517, 450, TopLeft);					//画出斜杠
//		g.drawImage(achievement_left_right,458,441, TopLeft);
		
		int leftX = 52,leftY = 122,leftSpace = 15,shadowX = 4,shadowY = 4, mapx, mapy;   
		
		//成就左侧条目
		int left1H = achievement_left1.getHeight(), left1W = achievement_left1.getWidth();
		int leftW = achievement_left.getWidth(), leftH = achievement_left.getHeight();
		for(int i=0;i<6;i++){       
			g.drawRegion(achievement_left1, 0, 0, left1W, left1H, 
					0, leftX, leftY+(leftSpace+left1H)*i, TopLeft);
			if(!isRight && archY==i){
				mapx = leftX-shadowX+8;
				mapy = leftY-shadowY+8+(leftH+leftSpace)*i;
				g.drawRegion(achievement_left, 0, 0, leftW, leftH, 0,
						leftX-shadowX, leftY-shadowY+(leftH+leftSpace)*i, TopLeft);
			}else{
				mapx = leftX+8;
				mapy = leftY+8+(leftH+leftSpace)*i;
				g.drawRegion(achievement_left, 0, 0, leftW, leftH, 0,
						leftX, leftY+(leftH+leftSpace)*i, TopLeft);
			}
			g.drawRegion(achievement_word,0,
					i*achievement_word.getHeight() / 6, achievement_word.getWidth(),
					achievement_word.getHeight() / 6, 0, mapx,	mapy, TopLeft);
		}
		
		int x=247,y=116,spaceY=4;
		for(int i=0;i<4;i++){				
			if(isRight && rightY==i){   
				g.drawRegion(achievement_long, 0, 0, achievement_long.getWidth(), achievement_long.getHeight(), 0,
						x, y+(spaceY+achievement_long.getHeight())*i, TopLeft);
				g.drawRegion(archivement_hoof, 0, 0, archivement_hoof.getWidth(), archivement_hoof.getHeight(), 0,
						x+289, y+12+(spaceY+achievement_long.getHeight())*i, TopLeft);
				drawNum(g, 10, 546, y+(achievement_long.getHeight()+spaceY)*i+26);
			}else{
				g.drawRegion(achievement_long1, 0, 0, achievement_long1.getWidth(), achievement_long1.getHeight(), 0,
						x, y+(spaceY+achievement_long1.getHeight())*i, TopLeft);
				g.drawRegion(archivement_hoof1, 0, 0, archivement_hoof1.getWidth(), archivement_hoof1.getHeight(), 0,
						x+289, y+12+(spaceY+31+archivement_hoof1.getHeight())*i, TopLeft);
				drawNum(g, 30, 546, y+(achievement_long.getHeight()+spaceY)*i+26);
			}
		}
		
		//shadowX = 4,shadowY = 4：阴影效果的间隔值
		int leftRightX = 459,leftRightY = 441,distanceLAR = 60;				//distanceLAR: leftRightX和leftRightY的间距
		g.drawRegion(achievement_left_right1, 0, 0, achievement_left_right1.getWidth()/2,			//翻页左按钮底部
				achievement_left_right1.getHeight(), 0, leftRightX, leftRightY, TopLeft);
		g.drawRegion(achievement_left_right1, achievement_left_right1.getWidth()/2, 0,			//翻页右按钮底部
				achievement_left_right1.getWidth()/2, achievement_left_right1.getHeight(),
				0, leftRightX+distanceLAR+achievement_left_right1.getWidth()/2, leftRightY, TopLeft);
		if(isBotton && rightY == 4){		//左右按钮效果
			if(bX == 0){				
				g.drawRegion(achievement_left_right, 0, 0, achievement_left_right.getWidth()/2,			//翻页左按钮
						achievement_left_right.getHeight(), 0,
						leftRightX-shadowX, leftRightY-shadowY, TopLeft);
				drawNum(g,archIndex+1,leftRightX+distanceLAR+18,leftRightY+8);
				g.drawRegion(achievement_left_right, 1*achievement_left_right.getWidth()/2, 0, achievement_left_right.getWidth()/2,			//翻页右按钮
						achievement_left_right.getHeight(), 0,
						leftRightX+distanceLAR+achievement_left_right.getWidth()/2, leftRightY, TopLeft);
			}else if(bX == 1){
				g.drawRegion(achievement_left_right, 0, 0, achievement_left_right.getWidth()/2,			//翻页左按钮
						achievement_left_right.getHeight(), 0,
						leftRightX, leftRightY, TopLeft);
				drawNum(g,archIndex+1,leftRightX+distanceLAR+18,leftRightY+8); 		//页面码
				g.drawRegion(achievement_left_right,  1*achievement_left_right.getWidth()/2, 0, achievement_left_right.getWidth()/2,			//翻页右按钮
						achievement_left_right.getHeight(), 0,
						leftRightX+distanceLAR+achievement_left_right.getWidth()/2-shadowX, leftRightY-shadowY, TopLeft);
			}
			
		}else{
			g.drawRegion(achievement_left_right, 0, 0, achievement_left_right.getWidth()/2,			//翻页左按钮
					achievement_left_right.getHeight(), 0,
					leftRightX, leftRightY, TopLeft);
			drawNum(g,archIndex+1,leftRightX+distanceLAR+18,leftRightY+8); 		//页面码
			g.drawRegion(achievement_left_right, 1*achievement_left_right.getWidth()/2, 0, achievement_left_right.getWidth()/2,			//翻页右按钮
					achievement_left_right.getHeight(), 0,
					leftRightX+distanceLAR+achievement_left_right.getWidth()/2, leftRightY, TopLeft);
		}
		
	}

	private void clear() {
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_shop_midding);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_shop_go_pay);
    	Resource.freeImage(Resource.id_achievement);
    	Resource.freeImage(Resource.id_achievement_left_right);
    	Resource.freeImage(Resource.id_achievement_left_right1);
    	Resource.freeImage(Resource.id_achievement_long);
    	Resource.freeImage(Resource.id_achievement_long1);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_achievement_points);
    	Resource.freeImage(Resource.id_archivement_hoof);
    	Resource.freeImage(Resource.id_archivement_hoof1);
    	Resource.freeImage(Resource.id_achievement_left);
    	Resource.freeImage(Resource.id_achievement_word);
    	Resource.freeImage(Resource.id_slash);
    	Resource.freeImage(Resource.id_shop_out_base);
    	Resource.freeImage(Resource.id_shop_figure);
    }

	private void handleAttainment(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
		}else if(keyState.containsAndRemove(KeyCode.UP)){
        	if(isRight){
        		 if(rightY>0){
        			 rightY--;
    		   	 }
        		 if(rightY==4){
 					isBotton = true;
 				}else{
 					isBotton = false;
 				}
        	}else{
        		 if(archY>0){
    		   		 archY--;
    		   	 }
        	}
        }else if(keyState.containsAndRemove(KeyCode.DOWN)){
			if (isRight) {
				if (rightY < 4) {
					rightY++;
				}
				if(rightY==4){
					isBotton = true;
				}else{
					isBotton = false;
				}
			} else {
				if(archY<5){
					archY++;
				}
				//archY=(archY+1)%6;
			}
       }else if(keyState.containsAndRemove(KeyCode.LEFT)){
        	if(isBotton){
        		bX = 0;
        	}else{
        		isRight = false;
        		rightY = 0;
        	}
        }else if(keyState.containsAndRemove(KeyCode.RIGHT)){
			if (isBotton) {
				bX = 1;
			} else {
				isRight = true;
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

}

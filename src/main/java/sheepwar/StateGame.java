package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.util.Collision;
import cn.ohyeah.stb.util.RandomValue;

public class StateGame implements Common{
	
	private int tempx=ScrW, tempy=20, tempx2=ScrW, tempy2=30;//ScrW屏幕宽度，tempx初始=ScrW，可以用表达式tempx-=1来使其移动
	private SheepWarGameEngine engine;
	
	public StateGame(SheepWarGameEngine engine){
		this.engine = engine;
	}
	
	public CreateRole createRole;
	public Weapon weapon;
	public Role own; 
	
	public void handleKey(KeyState keyState){
		
		if (keyState.containsMoveEventAndRemove(KeyCode.UP)) {
			moveRole(0);
			
		} else if (keyState.containsMoveEventAndRemove(KeyCode.DOWN)) {
			moveRole(1);
			
		} else if (keyState.contains(KeyCode.OK)) {				 // 普通攻击
			keyState.remove(KeyCode.OK);
			weapon.createBomb(own, 2);
			
		}else if(keyState.containsAndRemove(KeyCode.NUM1)){    	//时光闹钟
			
		}else if(keyState.containsAndRemove(KeyCode.NUM2)){ 	//捕狼网
			
		}else if(keyState.containsAndRemove(KeyCode.NUM3)){		//盾牌
			
		}else if(keyState.containsAndRemove(KeyCode.NUM4)){		//激光枪
			
		}else if(keyState.containsAndRemove(KeyCode.NUM5)){		//驱散竖琴
			
		}else if(keyState.containsAndRemove(KeyCode.NUM6)){
			
		}else if(keyState.containsAndRemove(KeyCode.NUM7)){
			
		}else if(keyState.containsAndRemove(KeyCode.NUM8)){		//木偶->可以增加一条生命
			
		}else if(keyState.containsAndRemove(KeyCode.NUM9)){
			
		}else if (keyState.containsAndRemove(KeyCode.NUM0)) { 
			engine.status = STATUS_MAIN_MENU;
			clear();
		}
	}
	
	public void show(SGraphics g){
		drawGamePlaying(g);
		createRole.showSheep(g,own);
		createRole.showWolf(g);
		weapon.showBomb(g);
	}
	
	public void execute(){
		if(engine.timePass(5000)){
			createRole.createWolf();
		}
		if(createRole.npcs.size()<1 || weapon.bombs.size()<1){
			return;
		}
		for(int i=0;i<weapon.bombs.size();i++){
			Weapon bomb = (Weapon) weapon.bombs.elementAt(i);
			for(int j=0;j<createRole.npcs.size();j++){
				Role npc = (Role) createRole.npcs.elementAt(j);
				Role ballon = npc.role;
				if(ballon != null){
					if(Collision.checkCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, ballon.mapx, ballon.mapy, ballon.width, ballon.height)){
						createRole.npcs.removeElement(npc);
						weapon.bombs.removeElement(bomb);
					}
				}
			}
			/*子弹出界时移除*/
			if(bomb.mapx+bomb.width <=0){
				weapon.bombs.removeElement(bomb);
			}
		}
	}
	
	private void drawGamePlaying(SGraphics g){
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image playing_menu = Resource.loadImage(Resource.id_playing_menu);// {491,0}
		Image playing_cloudbig = Resource.loadImage(Resource.id_playing_cloudbig);
		Image playing_cloudsmall = Resource.loadImage(Resource.id_playing_cloudsmall);// {404,164}
		Image playing_lawn = Resource.loadImage(Resource.id_playing_lawn);// {0,499}
		Image playing_step = Resource.loadImage(Resource.id_playing_step);// {377,153},{377,240}{377,324}{377,409} Y 相差89
		Image playing_tree = Resource.loadImage(Resource.id_playing_tree);// {0,72}
		Image playing_lunzi = Resource.loadImage(Resource.id_playing_lunzi);//{374,132}
		Image playing_shenzi = Resource.loadImage(Resource.id_playing_shenzi); //{379,154}
		Image playing_lift = Resource.loadImage(Resource.id_playing_lift); //{342,303}
		Image playing_shenzi1 = Resource.loadImage(Resource.id_playing_shenzi1); //{399, 135}//横放的绳子
		Image playing_prop_memu = Resource.loadImage(Resource.id_playing_prop_memu); //{497,192}{564,192}//上下相差70
		Image playing_stop = Resource.loadImage(Resource.id_playing_stop); //{501,466}
		//Image bomb = Resource.loadImage(Resource.id_bomb); //{501,466}
//		Image blue = Resource.loadImage(Resource.id_balloon_blue);
		g.drawImage(game_bg, 0, 0, TopLeft);
		
		if(tempx+playing_cloudbig.getWidth()>0){
			tempx -= 1;
		}else{
			tempy = RandomValue.getRandInt(0, 114);
			tempx = ScrW;
		}
		g.drawRegion(playing_cloudbig, 0, 0, playing_cloudbig.getWidth(), playing_cloudbig.getHeight(), 
				0, tempx, tempy, TopLeft);
		
		if(tempx2+playing_cloudsmall.getWidth()>0){
			tempx2 -= 2;
		}else{
			tempy2 = RandomValue.getRandInt(0, 114);
			tempx2 = ScrW;
		}
		g.drawRegion(playing_cloudsmall, 0, 0, playing_cloudsmall.getWidth(), playing_cloudsmall.getHeight(), 
				0, tempx2, tempy2, TopLeft);
		g.drawImage(playing_lawn, 0, 499, TopLeft);
		g.drawImage(playing_tree, 0, 72, TopLeft);
		g.drawImage(playing_shenzi1, 399, 135, TopLeft);
		for(int i=0;i<4;i++){   //阶梯
			g.drawImage(playing_step, 377, 153+i*89, TopLeft);
		}
		g.drawRegion(playing_shenzi, 0, 0, playing_shenzi.getWidth(), (own.mapy-154),        //上下移动的绳子
				0, 379, 154, TopLeft);                                                        //竖直绳子 的纵坐标 154

		g.drawRegion(playing_lift, 0, 0, playing_lift.getWidth(), playing_lift.getHeight(),     //羊的吊篮
				0, 342, 154+(own.mapy-154), TopLeft);
		//g.drawRegion(bomb, 0, 0, bomb.getWidth()/3, bomb.getHeight(), 0, 345-18, 40+own.mapy, TopLeft); //吊篮上的飞镖
		
		g.drawImage(playing_lunzi, 374,132, TopLeft);
		g.drawImage(playing_menu, 491, 0, TopLeft);
		for(int i=0;i<4;i++){                                                                //游戏中的左侧框内容---道具内容
			g.drawImage(playing_prop_memu, 497,185+i*68, TopLeft);
			drawProp(g, i, 497+5,185+i*(68+3));                                              //第一列对应原图片中的前四个
			drawNum(g, i+1, 540+7, 223-17+i*73);//提示技能按键：1-4{540,223}
			
			g.drawImage(playing_prop_memu, 564,185+i*68, TopLeft);
			drawProp(g, i+4, 564+5,185+i*(68+2));  //第二列对应原图片中的后四个
			drawNum(g, i+4+1, 612, 223-17+i*73);//提示技能键5-8{}
		}
		g.drawImage(playing_stop, 500,459, TopLeft);//暂停游戏按钮
	
	}
	
	private void moveRole(int towards) {
		switch (towards) {
		case 0: // 往上--主角
			if(own.mapy>=164){
				own.mapy -= own.speed;
			}
			break;
		case 1: // 往下--主角
			own.direction = 1;
			if(own.mapy + own.height<460){
				own.mapy += own.speed;
			}
			break;
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
	
	private void drawProp(SGraphics g,int num,int x,int y){
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		String number=String.valueOf(num);
		for(byte i=0;i<number.length();i++){
			g.drawRegion(playing_prop, (number.charAt(i) - '0')* playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8,
					playing_prop.getHeight(), 0, x+i * (playing_prop.getWidth()/8 + 1), y, 0);
		}
	}
	
	private void clear() {
		Resource.freeImage(Resource.id_playing_menu);
		Resource.freeImage(Resource.id_playing_cloudbig);
		Resource.freeImage(Resource.id_playing_cloudbig);
		Resource.freeImage(Resource.id_playing_cloudsmall);
		Resource.freeImage(Resource.id_playing_lawn);
		Resource.freeImage(Resource.id_playing_step);
		Resource.freeImage(Resource.id_playing_tree);
		Resource.freeImage(Resource.id_game_bg);
		Resource.freeImage(Resource.id_playing_lunzi);
		Resource.freeImage(Resource.id_playing_shenzi);
		Resource.freeImage(Resource.id_playing_lift);
		Resource.freeImage(Resource.id_playing_shenzi1);
		Resource.freeImage(Resource.id_playing_prop_memu);
		Resource.freeImage(Resource.id_playing_prop);
		Resource.freeImage(Resource.id_playing_stop);   
		Resource.freeImage(Resource.id_playing_sheep);   
		Resource.freeImage(Resource.id_sheep_eye);   
		Resource.freeImage(Resource.id_sheep_hand);   
		Resource.freeImage(Resource.id_bomb);   
		Resource.freeImage(Resource.id_wolf_down);   
		Resource.freeImage(Resource.id_wolf_run);   
		Resource.freeImage(Resource.id_balloon_blue);   
		Resource.freeImage(Resource.id_balloon_green);   
		Resource.freeImage(Resource.id_balloon_multicolour);   
		Resource.freeImage(Resource.id_balloon_yellow);   
		Resource.freeImage(Resource.id_balloon_yellowred);   
		Resource.freeImage(Resource.id_balloon_red);   
	}

}

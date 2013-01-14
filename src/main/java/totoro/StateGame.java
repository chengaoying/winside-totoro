package totoro;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.util.Collision;

public class StateGame implements Common{
	
	private TotoroGameEngine engine;
	public StateGame(TotoroGameEngine engine){
		this.engine = engine;
	}
	
	public static int game_status;
	
	public static long level_start_time;
	public static long level_end_time;
	public static boolean level_over;
	
	private int level = 1;
	public boolean isNextLevel;
	
	public MoveObjectFactory factory;
	public MoveObjectShow objectShow;
	public static MoveObject player;
	
	private long bombStart, bombEnd;
	private int bombInterval = 400;
	
	private long spiritStart, spiritEnd;
	private long batteryStart, batteryEnd;
	private long reviveStime, reviveEtime;
	
	//存档数据
	public static int lifeNum;
	public static int currLevel;
	public static int scores;
	public static int blood;
	public static int grade;
	public static int bombGrade;
	public static boolean hasTotoro3;
	public static boolean hasTotoro4;
	
	public void handleKey(KeyState keyState){
		if(keyState.containsMoveEventAndRemove(KeyCode.UP) 
				&& player.status != ROLE_STATUS_PASS && game_status != GAME_SUCCESS){
			move(0);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.DOWN)
				&& player.status != ROLE_STATUS_PASS && game_status != GAME_SUCCESS){
			move(1);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.RIGHT)
				&& player.status != ROLE_STATUS_PASS && game_status != GAME_SUCCESS){
			move(2);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.LEFT)
				&& player.status != ROLE_STATUS_PASS && game_status != GAME_SUCCESS){
			move(3);
		}else if(keyState.containsAndRemove(KeyCode.NUM1) && player.status != ROLE_STATUS_PASS){
			if(player.status == ROLE_STATUS_ALIVE){
				player.status = ROLE_STATUS_PROTECTED;
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM0)){
			game_status = GAME_PAUSE;
		}else if(keyState.containsAndRemove(KeyCode.NUM2)){
			game_status = GAME_FAIL;
		}else if(keyState.containsAndRemove(KeyCode.NUM3)){
			for(int i=0;i<factory.boss.size();i++){
				MoveObject mo = (MoveObject) factory.boss.elementAt(i);
				mo.status = ROLE_STATUS_DEAD;
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM4)){
			player.bombGrade = (player.bombGrade+1)%5;
			if(player.bombGrade==0)
			{
				player.bombGrade = 1;
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM5)){
			player.grade ++;
			if(player.grade > 5){
				player.grade = 1;
			}
		}
		
	}
	
	public void execute(){
		
		//玩家复活
		if(player.status == ROLE_STATUS_DEAD && player.lifeNum>0){
			reviveEtime = getTime();
			System.out.println("time:"+(reviveEtime-reviveStime));
			if(reviveEtime-reviveStime > 1*1000){
				player = factory.revivePlayer();
			}
		}else if(player.lifeNum <= 0){
			System.out.println("game over");
			game_status = GAME_FAIL;
		}
		
		judgeNextLevel();
		
		level_end_time = getTime()/1000;
		//System.out.println("time:"+(level_end_time - level_start_time));
		if(level_end_time - level_start_time > levelInfo[level-1][1]){
			level_over = true;
		}
		
		bombEnd = getTime();
		if(player != null 
			&& player.status != ROLE_STATUS_DEAD 
			&& player.status != ROLE_STATUS_PASS 
			&& bombEnd - bombStart > bombInterval){
			
			factory.createBomb(player);
			bombStart = getTime();
		}
		
		createSpirits();
		
		createSpiritsBombs();
		
		collisionDetection();
		
		removeOutsideObject();
		
		entryGameStatus();
		
	}
	
	private void entryGameStatus() {
		switch (game_status){
		case GAME_PAUSE:	//暂停
			StateSubMenu menu = new StateSubMenu(engine);
			int index = menu.processSubMenu();
			if(index == 1){
				//返回主界面
				engine.state = STATUS_MAIN_MENU;
				Resource.clearGame();
				factory.removeAllObject();
			}
			game_status = GAME_PLAY;
			break;
		case GAME_SUCCESS: 	//通关
			drawPassInterface();
			break;	
		case GAME_FAIL:		//失败
			System.out.println(2);
			StateGameFail fail = new StateGameFail(engine);
			int i = fail.processGameFail();
			if(i == 0){
				//买复活
				if(engine.getEngineService().getBalance() > 3){
					
				}else{
					PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
					pc.setText(engine.getEngineService().getExpendAmountUnit()+"不足,是否去充值?");
					int p = pc.popup();
					if(p==0){
						Recharge recharge = new Recharge(engine);
						recharge.recharge();
					}
				}
			}else{
				//退出游戏
				engine.state = STATUS_MAIN_MENU;
				Resource.clearGame();
				factory.removeAllObject();
				game_status = GAME_PLAY;
			}
			break;
		} 
	}

	private void drawPassInterface() {
		
	}

	private void judgeNextLevel() {
		
		if(level_over && factory.boss.size()<1){
			isNextLevel = true;
			player.status = ROLE_STATUS_PASS;
			//factory.removeAllObject();
			if(level >= 8){
				//通关
				game_status = GAME_SUCCESS;
				factory.removeEnemy();
			}
		}
		
		if(player.status == ROLE_STATUS_PASS){
			player.mapx += player.speedX;
			if(player.mapx > ScrW){
				changeDatePass();
			}
		}
		
	}

	private void createSpiritsBombs() {
		for(int j=0;j<factory.spirits.size();j++){
			MoveObject object = (MoveObject) factory.spirits.elementAt(j);
			if(object.status2 == ROLE_STATUS2_ATTACK && object.attackPermission == ATTACK_PERMISSION_YES){
				factory.createSpiritBomb(object);
				object.status2 = ROLE_STATUS2_MOVE;
			}
		}
		
		for(int j=0;j<factory.boss.size();j++){
			MoveObject object = (MoveObject) factory.boss.elementAt(j);
			if(object.status2 == ROLE_STATUS2_ATTACK){
				factory.createSpiritBomb(object);
				object.status2 = ROLE_STATUS2_MOVE;
			}
		}
		
		for(int j=0;j<factory.battery.size();j++){
			MoveObject object = (MoveObject) factory.battery.elementAt(j);
			if(object.status2 == ROLE_STATUS2_ATTACK && object.attackPermission == ATTACK_PERMISSION_YES){
				factory.createBatteryBombs(object, player);
				object.status2 = ROLE_STATUS2_MOVE;
			}
		}
	}

	private void collisionDetection() {
		
		/*玩家普通攻击碰撞检测*/
		for(int i=0;i<factory.bombs.size();i++){
			MoveObject bomb = (MoveObject) factory.bombs.elementAt(i);
			for(int j=0;j<factory.spirits.size();j++){
				MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					bomb.status = ROLE_STATUS_DEAD;
					mo.blood -= bomb.damage;
				}
				if(mo.blood<=0){
					mo.status = ROLE_STATUS_DEAD;
					player.scores += mo.scores;
					scores = player.scores;
				}
			}
			for(int k=0;k<factory.boss.size();k++){
				MoveObject boss = (MoveObject) factory.boss.elementAt(k);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, boss.mapx, boss.mapy, boss.width, boss.height)){
					bomb.status = ROLE_STATUS_DEAD;
					boss.blood -= bomb.damage;
				}
				if(boss.blood<=0){
					boss.status = ROLE_STATUS_DEAD;
					player.scores += boss.scores;
					scores = player.scores;
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
			for(int k=0;k<factory.battery.size();k++){
				MoveObject battery = (MoveObject) factory.battery.elementAt(k);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, battery.mapx, battery.mapy, battery.width, battery.height)){
					bomb.status = ROLE_STATUS_DEAD;
					battery.blood -= bomb.damage;
				}
				if(battery.blood<=0){
					battery.status = ROLE_STATUS_DEAD;
					player.scores += battery.scores;
					scores = player.scores;
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
		}
		
		/*怪物碰撞检测*/
		for(int j=0;j<factory.spirits.size();j++){
			MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
			if(Collision.checkSquareCollision(player.mapx, player.mapy, player.width, player.height, mo.mapx, mo.mapy, mo.width, mo.height)
					 && player.status == ROLE_STATUS_ALIVE){
				if(player.status != ROLE_STATUS_PROTECTED){
					if(player.blood - mo.damage >0){
						player.blood -= mo.damage;
					}else{
						player.blood = 0;
					}
					blood = player.blood;
				}
				mo.blood -= player.damage;
			}
			if(mo.blood <= 0){
				mo.status = ROLE_STATUS_DEAD;
			}
		}
		
		/*敌方普通攻击碰撞检测*/
		for(int k=0;k<factory.spiritBombs.size();k++){
			MoveObject mo = (MoveObject) factory.spiritBombs.elementAt(k);
			if(Collision.checkSquareCollision(mo.mapx, mo.mapy, mo.width, mo.height, player.mapx, player.mapy, player.width, player.height)
					 && player.status == ROLE_STATUS_ALIVE){
				mo.status = ROLE_STATUS_DEAD;
				if(player.status != ROLE_STATUS_PROTECTED){
					if(player.blood - mo.damage >0){
						player.blood -= mo.damage;
					}else{
						player.blood = 0;
					}
					blood = player.blood;
				}
			}
		}
		
		if(player.blood <= 0 && player.status == ROLE_STATUS_ALIVE){
			player.status = ROLE_STATUS_DEAD;
			player.lifeNum --;
			lifeNum = player.lifeNum;
			reviveStime = getTime();
		}
	}
	
	private void createSpirits(){
		if(!isNextLevel){
			spiritEnd = System.currentTimeMillis();
			batteryEnd = System.currentTimeMillis();
			if(!level_over){
				if(spiritEnd - spiritStart >= levelInfo[level-1][2]){
					factory.cteateBatchSpirits(level);
					spiritStart = System.currentTimeMillis();
				}
				if(batteryEnd - batteryStart >= levelInfo[level-1][3] && level != 3 && level != 4){
					factory.createBattery(level);
					batteryStart = System.currentTimeMillis();
				}
			}else{
				if(factory.boss.size()<1){
					factory.createBoss(level);
				}
			}
		}
	}
	
	private void removeOutsideObject() {
		for(int i=0;i<factory.bombs.size();i++){
			MoveObject mo = (MoveObject) factory.bombs.elementAt(i);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.bombs.removeElement(mo);
			}
		}
		
		for(int j=0;j<factory.spirits.size();j++){
			MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
			if(mo.status == ROLE_STATUS_DEAD){
				//System.out.println("------remove------");
				factory.spirits.removeElement(mo);
			}
		}
		
		for(int i=0;i<factory.spiritBombs.size();i++){
			MoveObject mo = (MoveObject) factory.spiritBombs.elementAt(i);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.spiritBombs.removeElement(mo);
			}
		}
		
		for(int i=0;i<factory.boss.size();i++){
			MoveObject mo = (MoveObject) factory.boss.elementAt(i);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.boss.removeElement(mo);
			}
		}
		
		for(int i=0;i<factory.battery.size();i++){
			MoveObject mo = (MoveObject) factory.battery.elementAt(i);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.battery.removeElement(mo);
			}
		}
		//System.out.println("spirit.size:"+factory.spirits.size());
		//System.out.println("bomb num:"+factory.bombs.size());
		//System.out.println("spiritBombs num:"+factory.spiritBombs.size());
		//System.out.println("boss num:"+factory.boss.size());
		//System.out.println("battery num:"+factory.battery.size());
	}
	
	/*过关之后改变数据*/
	private void changeDatePass(){
		player.status = ROLE_STATUS_ALIVE;
		player.mapx = 0;
		isNextLevel = false;
		level_over = false;
		level_start_time = getTime()/1000;
		level++;
		bgIndex = 0;
		hillIndex = 0;
		wayIndex = 0;
		Resource.clearGame();
		factory.removeEnemy();
	}

	public void show(SGraphics g){
		drawGameBg(g);
		objectShow.showPlayer(g, player);
		objectShow.showBombs(g, factory.bombs);
		objectShow.showSpiritsBomb(g, factory.spiritBombs);
		objectShow.showSpirits(g, factory.spirits);
		objectShow.showBattery(g, factory.battery, player);
		objectShow.showBoss(g, factory.boss);
		drawInfo(g);
		if(isNextLevel){
			drawNextPrompt(g);
		}
	}
	
	private void drawGameBg(SGraphics g) {
		if(level == 1 || level == 2){
			drawGameBg_sky(g);
		}else if(level == 3 || level == 4){
			drawGameBg_burrow(g);
		}else if(level == 5 || level == 6){
			drawGameBg_ice(g);
		}else if(level == 7 || level == 8){
			drawGameBg_lava(g);
		}
	}

	private void drawNextPrompt(SGraphics g){
		String str = "恭喜你通过本关卡,下一关为第"+(level+1)+"关"; 
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
		g.setColor(0xffffff);
		g.drawString(str, x, y, 20);
	}
	
	private int centerIndex, center2Index, donwIndex, lavaIndex, upIndex;
	private void drawGameBg_lava(SGraphics g) {
		Image center = Resource.loadImage(Resource.id_lava_game_center);
		Image center2 = Resource.loadImage(Resource.id_lava_game_center_2);
		Image down = Resource.loadImage(Resource.id_lava_game_down);
		Image lava = Resource.loadImage(Resource.id_lava_game_lava);
		Image up = Resource.loadImage(Resource.id_lava_game_up);
		
		int centerW = center.getWidth(), centerH = center.getHeight();
		int center2W = center2.getWidth(), center2H = center2.getHeight();
		int lavaW = lava.getWidth(), lavaH = lava.getHeight();
		int downW = down.getWidth(), downH = down.getHeight();
		int upW = up.getWidth(), upH = up.getHeight();
		centerIndex = (centerIndex+1)%centerW;
		center2Index = (center2Index+1)%center2W;
		donwIndex = (donwIndex+1)%downW;
		lavaIndex = (lavaIndex+3)%lavaW;
		upIndex = (upIndex+2)%upW;
		
		int mapy = 73;
		g.drawRegion(center2, center2Index, 0, center2W-center2Index, center2H, 0, 0, mapy, 20);
		g.drawRegion(center2, 0, 0, center2Index, center2H, 0, center2W-center2Index, mapy, 20);
		mapy = 73+center2H+centerH-25;
		g.drawRegion(lava, lavaIndex, 0, lavaW-lavaIndex, lavaH, 0, 0, mapy, 20);
		g.drawRegion(lava, 0, 0, lavaIndex, lavaH, 0, lavaW-lavaIndex, mapy, 20);
		mapy = 73+center2H-5;
		g.drawRegion(center, centerIndex, 0, centerW-centerIndex, centerH, 0, 0, mapy, 20);
		g.drawRegion(center, 0, 0, centerIndex, centerH, 0, centerW-centerIndex, mapy, 20);
		g.drawRegion(up, upIndex, 0, upW-upIndex, upH, 0, 0, 73, 20);
		g.drawRegion(up, 0, 0, upIndex, upH, 0, upW-upIndex, 73, 20);
		g.drawRegion(down, donwIndex, 0, downW-donwIndex, downH, 0, 0, ScrH-downH, 20);
		g.drawRegion(down, 0, 0, donwIndex, downH, 0, downW-donwIndex, ScrH-downH, 20);
	}

	public void drawGameBg_burrow(SGraphics g){
		Image center = Resource.loadImage(Resource.id_burrow_game_center);
		Image down = Resource.loadImage(Resource.id_burrow_game_down);
		Image up = Resource.loadImage(Resource.id_burrow_game_up);
		Image net = Resource.loadImage(Resource.id_burrow_game_net);
		Image bubble = Resource.loadImage(Resource.id_burrow_game_bubble);
		
		int centerW = center.getWidth(), centerH = center.getHeight();
		int downW = down.getWidth(), downH = down.getHeight();
		int upW = up.getWidth(), upH = up.getHeight();
		int netW = net.getWidth(), netH = net.getHeight();
		int bubbleW = bubble.getWidth(), bubbleH = bubble.getHeight();
		bgIndex = (bgIndex+1)%centerW;
		hillIndex = (hillIndex+2)%upW;
		wayIndex = (wayIndex+3)%downW;
		g.drawRegion(center, bgIndex, 0, centerW-bgIndex, centerH, 0, 0, 73+35, 20);
		g.drawRegion(center, 0, 0, bgIndex, centerH, 0, centerW-bgIndex, 73+35, 20);
		g.drawRegion(up, hillIndex, 0, upW-hillIndex, upH, 0, 0, 73, 20);
		g.drawRegion(up, 0, 0, hillIndex, upH, 0, upW-hillIndex, 73, 20);
		
		if(mapx+netW>0){
			mapx -= 1;
		}else{
			mapx = ScrW;
		}
		g.drawRegion(net, 0, 0, netW, netH, 0, mapx, 360, 20);
		
		if(mapx2+bubbleW>0){
			mapx2 -= 1;
		}else{
			mapx2 = ScrW;
		}
		g.drawRegion(bubble, 0, 0, bubbleW, bubbleH, 0, mapx2, 142, 20);
		
		g.drawRegion(down, wayIndex, 0, downW-wayIndex, downH, 0, 0, ScrH-downH, 20);
		g.drawRegion(down, 0, 0, wayIndex, downH, 0, downW-wayIndex, ScrH-downH, 20);
	}
	
	int mapx = 200, mapx2 = 300;
	public void drawGameBg_sky(SGraphics g){
		Image game_bg = Resource.loadImage(Resource.id_sky_game_bg);
		Image hill = Resource.loadImage(Resource.id_sky_game_hill); 
		Image way = Resource.loadImage(Resource.id_sky_game_way);
		
		int bgW =  game_bg.getWidth(), bgH = game_bg.getHeight();
		int hillW = hill.getWidth(), hillH = hill.getHeight();
		int wayW = way.getWidth(), wayH = way.getHeight();
		bgIndex = (bgIndex+1)%bgW;
		wayIndex = (wayIndex+3)%wayW;
		g.drawRegion(game_bg, bgIndex, 0, bgW-bgIndex, bgH, 0, 0, 0, 20);
		g.drawRegion(game_bg, 0, 0, bgIndex, bgH, 0, bgW-bgIndex, 0, 20);
		
		if(mapx+hillW>0){
			mapx -= 2;
		}else{
			mapx = ScrW;
		}
		g.drawRegion(hill, 0, 0, hillW, hillH, 0, mapx, 70, 20);
		
		g.drawRegion(way, wayIndex, 0, wayW-wayIndex, wayH, 0, 0, ScrH-wayH, 20);
		g.drawRegion(way, 0, 0, wayIndex, wayH, 0, wayW-wayIndex, ScrH-wayH, 20);
	}
	
	private int bgIndex, hillIndex, wayIndex;
	public void drawGameBg_ice(SGraphics g){
		Image game_bg = Resource.loadImage(Resource.id_ice_game_bg);
		Image hill= Resource.loadImage(Resource.id_ice_game_bg_hill);
		Image way = Resource.loadImage(Resource.id_ice_game_bg_way);
		
		int bgW =  game_bg.getWidth(), bgH = game_bg.getHeight();
		int hillW = hill.getWidth(), hillH = hill.getHeight();
		int wayW = way.getWidth(), wayH = way.getHeight();
		bgIndex = (bgIndex+1)%bgW;
		hillIndex = (hillIndex+2)%hillW;
		wayIndex = (wayIndex+3)%wayW;
		g.drawRegion(game_bg, bgIndex, 0, bgW-bgIndex, bgH, 0, 0, 73, 20);
		g.drawRegion(game_bg, 0, 0, bgIndex, bgH, 0, bgW-bgIndex, 73, 20);
		g.drawRegion(hill, hillIndex, 0, hillW-hillIndex, hillH, 0, 0, 283, 20);
		g.drawRegion(hill, 0, 0, hillIndex, hillH, 0, hillW-hillIndex, 283, 20);
		g.drawRegion(way, wayIndex, 0, wayW-wayIndex, wayH, 0, 0, ScrH-wayH, 20);
		g.drawRegion(way, 0, 0, wayIndex, wayH, 0, wayW-wayIndex, ScrH-wayH, 20);
	}
	
	private void drawInfo(SGraphics g){
		Image infoBg = Resource.loadImage(Resource.id_game_info_bg);
		Image infoHead = Resource.loadImage(Resource.id_game_info_head);
		Image bloodBg = Resource.loadImage(Resource.id_game_blood_bg);
		Image headShadow = Resource.loadImage(Resource.id_game_head_shadow);
		Image bgUp = Resource.loadImage(Resource.id_game_bg_up);
		
		int infoBgW = infoBg.getWidth(), infoBgH = infoBg.getHeight();
		//int infoHeadW = infoHead.getWidth(), infoHeadH = infoHead.getHeight();
		int bloodBgW = bloodBg.getWidth(), bloodBgH = bloodBg.getHeight();
		int /*bgUpW = bgUp.getWidth(),*/ bgUpH = bgUp.getHeight();
		int offX = 120, offY = bgUpH/2-infoBgH/2;
		
		g.drawImage(bgUp, 0, 0, 20);
		StateMain.drawNum(g, scores, 25, offY+10);
		g.drawImage(infoBg, offX, offY, 20);
		g.drawImage(headShadow, offX+3, offY, 20);
		g.drawImage(infoHead, offX, offY, 20);
		StateMain.drawNum(g, level, offX+56, offY+15);
		StateMain.drawNum(g, lifeNum, offX+80, offY+15);
		offX += infoBgW - bloodBgW - 25;
		offY += infoBgH/2 - bloodBgH/2;
		g.drawImage(bloodBg, offX, offY, 20);
		g.setColor(0xffff00);
		DrawUtil.drawRect(g, offX+4, offY+4, player.blood*(bloodBgW-8)/playerParam[player.id][6], bloodBgH-8);
		g.setColor(0xffffff);
	}
	
	private long getTime(){
		return System.currentTimeMillis();
	}
	
	private void move(int towards) {
		switch (towards) {
		case 0: // 往上--主角
			if(player.mapy-startP <= player.speedY){
				player.mapy = startP;
			}else{
				player.mapy -= player.speedY;
			}
			break;
		case 1: // 往下--主角
			if(player.mapy+player.height<490){
				if((490 - (player.mapy+player.height)) < player.speedY){
					player.mapy = 490 - player.height;
				}else{
					player.mapy += player.speedY;
				}
			}
			break;
		case 2: // 往右--主角
			if(ScrW - ((player.mapx+player.width)) < player.speedX){
				player.mapx = ScrW - player.width;
			}else{
				player.mapx += player.speedX;
			}
			break;
		case 3:
			if(player.mapx < player.speedX){
				player.mapx = 0;
			}else{
				player.mapx -= player.speedX;
			}
			break;
		}
	}
}

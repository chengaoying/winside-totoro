package totoro;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.util.Collision;

public class StateGame implements Common{
	
	private TotoroGameEngine engine;
	public StateGame(TotoroGameEngine engine){
		this.engine = engine;
	}
	
	public static long level_start_time;
	public static long level_end_time;
	public static boolean level_over;
	
	private int level = 7;
	public boolean isNextLevel;
	
	public MoveObjectFactory factory;
	public MoveObjectShow objectShow;
	public MoveObject player;
	
	private long bombStart, bombEnd;
	private int bombInterval = 400;
	
	private long spiritStart, spiritEnd;
	private long batteryStart, batteryEnd;
	
	public void handleKey(KeyState keyState){
		if(keyState.containsMoveEventAndRemove(KeyCode.UP) && player.status != ROLE_STATUS_PASS){
			move(0);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.DOWN) && player.status != ROLE_STATUS_PASS){
			move(1);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.RIGHT) && player.status != ROLE_STATUS_PASS){
			move(2);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.LEFT) && player.status != ROLE_STATUS_PASS){
			move(3);
		}else if(keyState.containsAndRemove(KeyCode.NUM1) && player.status != ROLE_STATUS_PASS){
			if(player.status == ROLE_STATUS_ALIVE){
				player.status = ROLE_STATUS_PROTECTED;
			}
		}
		
	}
	
	public void execute(){
		
		level_end_time = getTime()/1000;
		//System.out.println("time:"+(level_end_time - level_start_time));
		if(level_end_time - level_start_time > levelInfo[level-1][1]){
			level_over = true;
		}
		
		bombEnd = getTime();
		if(player != null && player.status != ROLE_STATUS_DEAD && bombEnd - bombStart > bombInterval){
			factory.createBomb(player);
			bombStart = getTime();
		}
		
		createSpirits();
		
		createSpiritsBombs();
		
		collisionDetection();
		
		removeOutsideObject();
		
		judgeNextLevel();
		
		if(player.status == ROLE_STATUS_PASS){
			player.mapx += player.speedX;
			if(player.mapx > ScrW){
				player.status = ROLE_STATUS_ALIVE;
				player.mapx = 0;
				isNextLevel = false;
				level_over = false;
				level_start_time = getTime()/1000;
				level++;
			}
		}
	}
	
	private void judgeNextLevel() {
		if(level_over && factory.boss.size()<1){
			isNextLevel = true;
			player.status = ROLE_STATUS_PASS;
			factory.removeAllObject();
			if(level >= 8){
				//Í¨¹Ø
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
		
		/*Íæ¼ÒÆÕÍ¨¹¥»÷Åö×²¼ì²â*/
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
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
		}
		
		/*¹ÖÎïÅö×²¼ì²â*/
		for(int j=0;j<factory.spirits.size();j++){
			MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
			if(Collision.checkSquareCollision(player.mapx, player.mapy, player.width, player.height, mo.mapx, mo.mapy, mo.width, mo.height)){
				if(player.status != ROLE_STATUS_PROTECTED){
					player.blood -= mo.damage;
				}
				mo.blood -= player.damage;
			}
			if(mo.blood <= 0){
				mo.status = ROLE_STATUS_DEAD;
			}
			if(player.blood <= 0){
				player.status = ROLE_STATUS_DEAD;
			}
		}
		
		/*µÐ·½ÆÕÍ¨¹¥»÷Åö×²¼ì²â*/
		for(int k=0;k<factory.spiritBombs.size();k++){
			MoveObject mo = (MoveObject) factory.spiritBombs.elementAt(k);
			if(Collision.checkSquareCollision(mo.mapx, mo.mapy, mo.width, mo.height, player.mapx, player.mapy, player.width, player.height)){
				mo.status = ROLE_STATUS_DEAD;
				if(player.status != ROLE_STATUS_PROTECTED){
					player.blood -= mo.damage;
				}
			}
			if(player.blood <= 0){
				player.status = ROLE_STATUS_DEAD;
			}
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
				if(batteryEnd - batteryStart >= levelInfo[level-1][3]){
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

	public void show(SGraphics g){
		drawGameBg(g);
		objectShow.showPlayer(g, player);
		objectShow.showBombs(g, factory.bombs);
		objectShow.showSpiritsBomb(g, factory.spiritBombs);
		objectShow.showSpirits(g, factory.spirits);
		objectShow.showBattery(g, factory.battery, player);
		objectShow.showBoss(g, factory.boss);
		drawInfo(g);
	}
	
	private void drawGameBg(SGraphics g) {
		drawGameBg_ice(g);
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
		g.drawRegion(game_bg, bgIndex, 0, bgW-bgIndex, bgH, 0, 0, 0, 20);
		g.drawRegion(game_bg, 0, 0, bgIndex, bgH, 0, bgW-bgIndex, 0, 20);
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
		int bgUpW = bgUp.getWidth(), bgUpH = bgUp.getHeight();
		int offX = 120, offY = bgUpH/2-infoBgH/2;
		
		g.drawImage(bgUp, 0, 0, 20);
		g.drawImage(infoBg, offX, offY, 20);
		g.drawImage(headShadow, offX+3, offY, 20);
		g.drawImage(infoHead, offX, offY, 20);
		StateMain.drawNum(g, level, offX+56, offY+15);
		StateMain.drawNum(g, player.lifeNum, offX+80, offY+15);
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
		case 0: // ÍùÉÏ--Ö÷½Ç
			if(player.mapy-46 <= player.speedY){
				player.mapy = 46;
			}else{
				player.mapy -= player.speedY;
			}
			break;
		case 1: // ÍùÏÂ--Ö÷½Ç
			if((ScrH - (player.mapy+player.height)) < player.speedY){
				player.mapy = ScrH - player.height;
			}else{
				player.mapy += player.speedY;
			}
			break;
		case 2: // ÍùÓÒ--Ö÷½Ç
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

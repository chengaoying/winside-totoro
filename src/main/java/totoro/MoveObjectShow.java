package totoro;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.util.RandomValue;

/**
 * 显示物体对象
 * @author jackey
 *
 */
public class MoveObjectShow implements Common{

	private MoveObjectShow(){}
	private static MoveObjectShow instance;
	public static MoveObjectShow getInstance(){
		if(instance==null){
			instance = new MoveObjectShow();
		}
		return instance;
	}
	
	public void showPlayer(SGraphics g, MoveObject player){
		if(player.status != ROLE_STATUS_DEAD)  {
			Image playerImg = Resource.loadImage(player.picId);
			int playerW = player.width, playerH = player.height;
			g.drawRegion(playerImg, player.frame*playerW, 0, playerW, playerH, 0, player.mapx, player.mapy, 20);
			player.frame = (player.frame+1)%3;
		}
	}
	
	public void showBombs(SGraphics g, Vector bombs, MoveObject player){
		for(int i=bombs.size()-1;i>=0;i--){
			MoveObject object = (MoveObject) bombs.elementAt(i);
			Image bomb = Resource.loadImage(object.picId);
			if(player.grade == TOTORO_GRADE_FOUR && object.grade == TOTORO_BOMB_GRADE_FOUR){
				object.frame = (object.frame+1)%3;
				g.drawRegion(bomb, object.frame*object.width, 0, object.width, object.height, 0, object.mapx, object.mapy, 20);
			}else{
				g.drawRegion(bomb, object.frameIndex*object.width, 0, object.width, object.height, 0, object.mapx, object.mapy, 20);
			}
			object.mapx += object.speedX;
			object.mapy += object.speedY;
			if(object.mapx > ScrW){
				object.status = ROLE_STATUS_DEAD;
			}
		}
	}
	
	public void showLasers(SGraphics g, Vector lasers, MoveObject player){
		if(player.status == ROLE_STATUS_DEAD){
			return;
		}
		for(int i=0;i<lasers.size();i++){
			MoveObject mo = (MoveObject) lasers.elementAt(i);
			Image laserDevice = Resource.loadImage(Resource.id_prop_laser_device);
			Image laser = Resource.loadImage(mo.picId);
			mo.mapy =  player.mapy - mo.height + (i*(player.height+mo.height-15))+10;
			mo.mapx = player.mapx+player.width-10;
			if(mo.status2 != ROLE_STATUS2_MOVE){
				mo.frame = (mo.frame+1)%mo.frameNum;
				g.drawRegion(laser, 0, mo.frame*mo.height, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
				if(mo.frame == 3){
					mo.status2 = ROLE_STATUS2_MOVE;
					mo.startTime = System.currentTimeMillis();
				}
			}
			g.drawImage(laserDevice, mo.mapx, mo.mapy-4, 20);
		}
	}
	
	public void showMissile(SGraphics g, Vector missiles, MoveObjectFactory factory){
		for(int i=0;i<missiles.size();i++){
			MoveObject mo = (MoveObject)missiles.elementAt(i);
			Image moPic = Resource.loadImage(mo.picId);
			g.drawImage(moPic, mo.mapx, mo.mapy, 20);
			if(mo.mo == null || mo.mo.status == ROLE_STATUS_DEAD){
				mo.mo = factory.queryNearestObject(mo, factory.spirits, factory.boss, factory.battery);
			}
			if(mo.mo != null){
				float distanceX = getAbsValue((mo.mo.mapx+mo.mo.width/2)-(mo.mapx+mo.width/2));
				float distanceY = getAbsValue((mo.mo.mapy+mo.mo.height/2)-(mo.mapy+mo.height/2));
				float speedY = distanceY/distanceX*mo.speedX;
				mo.speedY = (int) speedY;
				if(mo.speedY>mo.speedX){
					mo.speedY = mo.speedX;
				}
				if(mo.mo.mapy>mo.mapy){
					mo.mapy += mo.speedY;
				}else if(mo.mo.mapy<mo.mapy){
					mo.mapy -= mo.speedY;
				}
			}
			mo.mapx += mo.speedX;
			if(mo.mapx>ScrW){
				mo.status = ROLE_STATUS_DEAD;
			}
		}
	}
	
	private int getAbsValue(int value){
		if(value<0){
			return -value;
		}else{
			return value;
		}
	}
	
	public void showWingplane(SGraphics g, Vector wingplane, MoveObject player){
		if(player.status == ROLE_STATUS_DEAD){
			return;
		}
		for(int i=0;i<wingplane.size();i++){
			MoveObject mo = (MoveObject)wingplane.elementAt(i);
			Image moPic = Resource.loadImage(mo.picId);
			mo.mapx = player.mapx + player.width/2-mo.width/2;
			if(i==0){
				mo.mapy = player.mapy-mo.height;
			}else if(i==1){
				mo.mapy = player.mapy + player.height;
			}else if(i==2){
				mo.mapy = player.mapy-mo.height-mo.height;
			}else {
				mo.mapy = player.mapy + player.height+mo.height;
			}
			g.drawImage(moPic, mo.mapx, mo.mapy, 20);
		}
	}
	
	public void showVentose(SGraphics g, Vector ventose){
		for(int i=0;i<ventose.size();i++){
			MoveObject mo = (MoveObject) ventose.elementAt(i);
			Image moPic = Resource.loadImage(mo.picId);
			g.drawRegion(moPic, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			if(mo.id == 2 || mo.id ==3){
				if(mo.mapy+mo.height<=490){
					mo.frame = (mo.frame+1)%3;
					mo.mapx += mo.speedX;
					mo.mapy += mo.speedY;
				}else{
					mo.frame = (mo.frame+1)%mo.frameNum;
					if(mo.frame==0 || mo.frame==1 || mo.frame == 2){
						mo.frame = 3;
					}else if(mo.frame==7){
						mo.status = ROLE_STATUS_DEAD;
					}
				}
			}else if(mo.id==4 || mo.id==5){
				mo.frame = (mo.frame+1)%mo.frameNum;
				if(mo.frame==4){
					mo.status = ROLE_STATUS_DEAD;
				}
			}
		}
	}
	
	public void showSpirits(SGraphics g, Vector spirits){
		g.setClip(0, 73, ScrW, gameH);
		for(int i=spirits.size()-1;i>=0;i--){
			MoveObject mo = (MoveObject) spirits.elementAt(i);
			Image spiritImg = Resource.loadImage(mo.picId);
			mo.endTime = System.currentTimeMillis();
			mo.bombETime = System.currentTimeMillis();
			if(mo.endTime-mo.startTime>mo.timeInterval){
				mo.frame = (mo.frame+1)%mo.frameNum;
				mo.startTime = System.currentTimeMillis();
			}
			if(mo.status == ROLE_STATUS_PROTECTED 
					&& mo.mapx+mo.width>0 
					&& mo.mapx<ScrW 
					&& mo.mapy+mo.height>startP 
					&& mo.mapy<ScrH){
				mo.status = ROLE_STATUS_ALIVE;
			}
			if(mo.position == OBJECT_POSITION_UP){
				if(mo.direction == OBJECT_DIRECTION_LEFT_DOWN){
					g.drawRegion(spiritImg, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
					mo.mapx -= mo.speedX;
					mo.mapy += mo.speedY;
				}else if(mo.direction == OBJECT_DIRECTION_RIGHT_DOWN){
					g.drawRegion(spiritImg, mo.frame*mo.width, 0, mo.width, mo.height, 0/*Sprite.TRANS_MIRROR*/, mo.mapx, mo.mapy, 20);
					mo.mapx += mo.speedX;
					mo.mapy += mo.speedY;
					if(mo.mapx+mo.width>=450){
						if(mo.directionValue >= 5){
							mo.direction = OBJECT_DIRECTION_LEFT_DOWN;
						}
					}
				}
				if(mo.mapy>=endP){
					mo.status = ROLE_STATUS_DEAD;
				}
			}else if(mo.position == OBJECT_POSITION_DOWN){
				if(mo.direction == OBJECT_DIRECTION_RIGHT_UP){
					g.drawRegion(spiritImg, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
					mo.mapx += mo.speedX;
					mo.mapy -= mo.speedY;
					if(mo.mapx+mo.width>=450){
						if(mo.directionValue >= 5){
							mo.direction = OBJECT_DIRECTION_LEFT_UP;
						}
					}
				}else if(mo.direction == OBJECT_DIRECTION_LEFT_UP){
					g.drawRegion(spiritImg, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
					mo.mapx -= mo.speedX;
					mo.mapy -= mo.speedY;
				}
				if(mo.mapy+mo.height<=startP){
					mo.status = ROLE_STATUS_DEAD;
				}
			}else if(mo.position == OBJECT_POSITION_LEFT){
				if(mo.direction == OBJECT_DIRECTION_RIGHT){
					g.drawRegion(spiritImg, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
					mo.mapx += mo.speedX;
					//mo.mapy -= mo.speedY;
				}
				if(mo.mapx >= ScrW){
					mo.status = ROLE_STATUS_DEAD;
				}
			}else if(mo.position == OBJECT_POSITION_RIGHT){
				if(mo.direction == OBJECT_DIRECTION_LEFT){
					g.drawRegion(spiritImg, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
					mo.mapx -= mo.speedX;
					//mo.mapy -= mo.speedY;
				}
				if(mo.mapx+mo.width <= 0){
					mo.status = ROLE_STATUS_DEAD;
				}
			}
			
			if(mo.bombETime - mo.bombSTime >= mo.bombInterval*1000){
				if(mo.status == ROLE_STATUS_ALIVE){
					mo.status2 = ROLE_STATUS2_ATTACK;
					mo.bombSTime = System.currentTimeMillis();
				}
			}
		}
		g.setClip(0, 0, ScrW, ScrH);
	}
	
	/*地面上的怪物*/
	public void showBattery(SGraphics g, Vector battery, MoveObject player){
		for(int i=0;i<battery.size();i++){
			MoveObject mo = (MoveObject) battery.elementAt(i);
			mo.bombETime = System.currentTimeMillis();
			mo.endTime = System.currentTimeMillis();
			Image moPic = Resource.loadImage(mo.picId);
			
			if(mo.bombETime - mo.bombSTime >= mo.bombInterval*1000){
				if(mo.status == ROLE_STATUS_ALIVE){
					mo.status2 = ROLE_STATUS2_ATTACK;
					mo.bombSTime = System.currentTimeMillis();
				}
			}
			
			if(mo.id == 300){
				if(mo.mapx > (player.mapx+player.width)){
					g.drawRegion(moPic, 0, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
				}else if(mo.mapx >= player.mapx && mo.mapx <= (player.mapx+player.width)){
					g.drawRegion(moPic, mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
				}else {
					g.drawRegion(moPic, mo.width+mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
				}
				mo.mapx -= mo.speedX;
			}else if(mo.id == 301){
				if(mo.status2 == ROLE_STATUS2_ATTACK){
					mo.frame = 2/*(mo.frame+1)%2 + 2*/;
					g.drawRegion(moPic, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
				}else if(mo.status2 == ROLE_STATUS2_MOVE){
					if(mo.endTime - mo.startTime > mo.timeInterval){
						mo.frame = (mo.frame+1)%2;
						mo.startTime = System.currentTimeMillis();
					}
					g.drawRegion(moPic, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
				}
				mo.mapx -= mo.speedX;
			}else if(mo.id == 302){
				g.drawRegion(moPic, 0, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
				if(mo.status2 == ROLE_STATUS2_ATTACK){
					Image effect = Resource.loadImage(Resource.id_lava_battery_effect);
					int eW = effect.getWidth()/2, eH = effect.getHeight();
					g.drawRegion(effect, eW, 0, eW, eH, 0, mo.mapx-8, mo.mapy-8, 20);
				}
				mo.mapx -= 1;
			}
			
			if(mo.mapx+mo.width <= 0){
				mo.status = ROLE_STATUS_DEAD;
			}
		}
	}
	
	public void showSpiritsBomb(SGraphics g, Vector bombs){
		for(int i=bombs.size()-1;i>=0;i--){
			MoveObject mo = (MoveObject) bombs.elementAt(i);
			Image moImg = Resource.loadImage(mo.picId);
			if(mo.id==16){
				mo.frame = (mo.frame+1)%3;
				g.drawRegion(moImg, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			}else if(mo.id==32){
				g.drawRegion(moImg, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			}else{
				g.drawRegion(moImg, 0, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			}
			mo.mapx += mo.speedX;
			mo.mapy += mo.speedY;
			if(mo.mapx+mo.width <= 0){
				mo.status = ROLE_STATUS_DEAD;
			}
		}
	}
	
	public void showBoss(SGraphics g, Vector bosses, MoveObjectFactory facotry){
		for(int i=0;i<bosses.size();i++){
			MoveObject boss = (MoveObject) bosses.elementAt(i);
			drawBoss(g, boss, facotry);
		}
	}
	
	public void showBossSkill(SGraphics g, Vector bossSkill){
		for(int i=bossSkill.size()-1;i>=0;i--){
			MoveObject mo = (MoveObject) bossSkill.elementAt(i);
			Image moImg = Resource.loadImage(mo.picId);
			if(mo.objectId == 203){
				g.drawRegion(moImg, mo.frameIndex*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			}else if(mo.objectId == 204 || mo.objectId == 200){
				mo.frame = (mo.frame+1)%mo.frameNum;
				g.drawRegion(moImg, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			}else{
				g.drawRegion(moImg, 0, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			}
			mo.mapx -= mo.speedX;
			mo.mapy += mo.speedY;
			if((mo.mapx+mo.width <= 0) || (mo.mapy>=490) 
					|| mo.mapy+mo.height<=0 || mo.mapx>ScrW){
				mo.status = ROLE_STATUS_DEAD;
			}
		}
	}

	public void showBoss8Skill2(SGraphics g, Vector boss8Skill){
		for(int i=boss8Skill.size()-1;i>=0;i--){
			MoveObject mo = (MoveObject) boss8Skill.elementAt(i);
			Image moImg = Resource.loadImage(mo.picId);
			mo.frame = (mo.frame+1)%mo.frameNum;
			g.drawRegion(moImg, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			
			if(mo.status == ROLE_STATUS_DEAD){
				boss8Skill.removeElement(mo);
			}
			
			if((mo.mapx > 60) ){
				if(mo.mapx-mo.speedX<=60){
					mo.mapx = 60;
				}else{
					mo.mapx -= mo.speedX;
				}
				//mo.mapy += mo.speedY;
			}else{
				mo.picId = boss8Skill2_3_PicId;
				mo.width = 120;
				mo.height = 76;
				mo.mapx -= 50;
				mo.mapy += 100;
				mo.frame = 0;
				mo.frameNum = 1;
				//mo.status2 = ROLE_STATUS2_ATTACK;
				mo.status = ROLE_STATUS_DEAD;
			}
		}
	}
	
	private void drawBoss(SGraphics g, MoveObject boss, MoveObjectFactory factory) {
		switch(boss.id){
		case 200:
			showBoss(g, boss, factory);
			break;
		case 201:
			showBoss(g, boss, factory);
			break;
		case 202:
			showBoss2(g, boss, factory);
			break;
		case 203:
			showBoss3(g, boss, factory);
			break;
		case 204:
			showBoss4(g, boss, factory);
			break;
		case 205:
			showBoss5(g, boss, factory);
			break;
		case 206:
			showBoss6(g, boss);
			break;
		case 207:
			showBoss7(g, boss, factory);
			break;
		}
	}
	
	private void showBoss(SGraphics g, MoveObject boss, MoveObjectFactory factory) {
		Image bossImg = Resource.loadImage(boss.picId);
		//int offX1 = ScrW/2-boss.width/2;
		int offX2 = ScrW-boss.width+20;
		int offX3 = ScrW/2 + ScrW/4-boss.width/2;
		boss.skill1ETime = System.currentTimeMillis();
		boss.skill2ETime = System.currentTimeMillis();
		boss.runTime2 = System.currentTimeMillis();
		//System.out.println("skill1Time："+(boss.skill1ETime-boss.skill1STime));
		//System.out.println("skill2Time："+(boss.skill2ETime-boss.skill2STime));
		/*System.out.println("runTime："+(boss.runTime2-boss.runTime1));
		System.out.println("status2:"+boss.status2);
		System.out.println("speedX:"+boss.speedX);*/
		if(boss.status2 == ROLE_STATUS2_MOVE || boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx <= offX3){
					int r = RandomValue.getRandInt(2);
					boss.direction = r==0?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
					boss.skill1STime = System.currentTimeMillis();
				}
			}else if(boss.direction == OBJECT_DIRECTION_RIGHT){
				boss.mapx += boss.speedX*2;
				if(boss.mapx >= offX2){
					boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
					boss.speedX += 120;
					boss.direction = OBJECT_DIRECTION_LEFT;
				}
			}else if(boss.direction == OBJECT_DIRECTION_UP){
				if(boss.runTime2-boss.runTime1 >= 3*1000){
					if(boss.stopInterval < 150){
						boss.stopInterval++;
						boss.mapy -= boss.speedY;
					}else{
						boss.stopInterval = 0;
						boss.runTime1 = System.currentTimeMillis();
					}
				}else{
					if(boss.id == 201){
						boss.bombETime = System.currentTimeMillis();
						if(boss.bombETime-boss.bombSTime>300){
							factory.createSpiritBomb(boss);
							boss.bombSTime = System.currentTimeMillis();
						}
					}
				}

				if(boss.mapy <= startP-30){
					boss.direction = OBJECT_DIRECTION_DOWN;
				}
				if((boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
					boss.skillStatue = 2;
					boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				}
				if(boss.skillStatue != 1 && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
					boss.skillStatue = 1;
					boss.direction = OBJECT_DIRECTION_RIGHT;
				}
			}else if(boss.direction == OBJECT_DIRECTION_DOWN){
				if(boss.runTime2-boss.runTime1 >= 3*1000){
					if(boss.stopInterval < 150){
						boss.stopInterval++;
						boss.mapy += boss.speedY;
					}else{
						boss.stopInterval = 0;
						boss.runTime1 = System.currentTimeMillis();
					}
				}else{
					if(boss.id == 201){
						boss.bombETime = System.currentTimeMillis();
						if(boss.bombETime-boss.bombSTime>300){
							factory.createSpiritBomb(boss);
							boss.bombSTime = System.currentTimeMillis();
						}
					}
				}
				
				if(boss.mapy+boss.height >= endP+30){
					boss.direction = OBJECT_DIRECTION_UP;
				}
				if((boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
					boss.skillStatue = 2;
					boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				}
				if(boss.skillStatue != 1 && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
					boss.skillStatue = 1;
					boss.direction = OBJECT_DIRECTION_RIGHT;
				}
			}
			boss.frame = (boss.frame+1)%boss.frameNum;
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		}else if(boss.status2 == ROLE_STATUS2_SKILL_ATTACK){
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx<=10){
					boss.direction = OBJECT_DIRECTION_RIGHT;
				}
				boss.frame = 2;
			}else if(boss.direction == OBJECT_DIRECTION_RIGHT){
				boss.mapx += boss.speedX;
				if(boss.mapx >= offX3){
					boss.mapx = offX3;
					int r = RandomValue.getRandInt(2);
					boss.direction = r==1?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
					boss.speedX -= 120;
					boss.status2 = ROLE_STATUS2_MOVE;
					boss.skillStatue = 0;
					boss.runTime1 = System.currentTimeMillis();
					boss.skill1STime = System.currentTimeMillis();
				}
				boss.frame = (boss.frame+1)%boss.frameNum;
			}
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		}
	}

	private void showBoss2(SGraphics g, MoveObject boss, MoveObjectFactory factory){
		Image bossImg = Resource.loadImage(boss.picId);
		//int offX1 = ScrW/2-boss.width/2;
		int offX2 = ScrW-boss.width+20;
		int offX3 = ScrW/2 + ScrW/4-boss.width/2;
		boss.skill1ETime = System.currentTimeMillis();
		boss.skill2ETime = System.currentTimeMillis();
		boss.runTime2 = System.currentTimeMillis();
		/*System.out.println("runTime："+(boss.runTime2-boss.runTime1));
		System.out.println("skill1ETime："+(boss.skill1ETime-boss.skill1STime));
		System.out.println("status2:"+boss.status2);
		System.out.println("speedX:"+boss.speedX);*/
		if(boss.status2 == ROLE_STATUS2_MOVE){
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx <= offX3){
					int r = RandomValue.getRandInt(2);
					boss.direction = r==0?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
					boss.skill1STime = System.currentTimeMillis();
				}
			}else if(boss.direction == OBJECT_DIRECTION_RIGHT){
				boss.mapx += boss.speedX*2;
				if(boss.mapx >= offX2){
					boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
					boss.speedX += 120;
					boss.direction = OBJECT_DIRECTION_LEFT;
				}
			}else if(boss.direction == OBJECT_DIRECTION_UP){
				if(boss.runTime2-boss.runTime1 >= 3*1000){
					if(boss.stopInterval < 150){
						boss.stopInterval++;
						boss.mapy -= boss.speedY;
					}else{
						boss.stopInterval = 0;
						boss.runTime1 = System.currentTimeMillis();
					}
				}else{
					boss.bombETime = System.currentTimeMillis();
					if(boss.bombETime-boss.bombSTime>300){
						factory.createSpiritBomb(boss);
						boss.bombSTime = System.currentTimeMillis();
					}
				}

				if(boss.mapy <= startP-30){
					boss.direction = OBJECT_DIRECTION_DOWN;
				}
				if(boss.skillStatue != 2 && (boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
					boss.skillStatue = 2;
					boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
					boss.direction = OBJECT_DIRECTION_DOWN;
					boss.speedY += 60;
				}
				if(boss.skillStatue != 1 && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
					boss.skillStatue = 1;
					boss.direction = OBJECT_DIRECTION_RIGHT;
				}
			}else if(boss.direction == OBJECT_DIRECTION_DOWN){
				if(boss.runTime2-boss.runTime1 >= 3*1000){
					if(boss.stopInterval < 150){
						boss.stopInterval++;
						boss.mapy += boss.speedY;
					}else{
						boss.stopInterval = 0;
						boss.runTime1 = System.currentTimeMillis();
					}
				}else{
					boss.bombETime = System.currentTimeMillis();
					if(boss.bombETime-boss.bombSTime>300){
						factory.createSpiritBomb(boss);
						boss.bombSTime = System.currentTimeMillis();
					}
				}
				
				if(boss.mapy+boss.height >= endP+30){
					boss.direction = OBJECT_DIRECTION_UP;
				}
				if(boss.skillStatue != 2 && (boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
					boss.skillStatue = 2;
					boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
					boss.direction = OBJECT_DIRECTION_DOWN;
					boss.speedY += 60;
				}
				if(boss.skillStatue != 1 && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
					boss.skillStatue = 1;
					boss.direction = OBJECT_DIRECTION_RIGHT;
				}
			}
			g.drawRegion(bossImg, 0, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		}else if(boss.status2 == ROLE_STATUS2_SKILL_ATTACK){
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx<=10){
					boss.direction = OBJECT_DIRECTION_RIGHT;
				}
				boss.frame = 0;
			}else if(boss.direction == OBJECT_DIRECTION_RIGHT){
				boss.mapx += boss.speedX;
				if(boss.mapx >= offX3){
					boss.mapx = offX3;
					int r = RandomValue.getRandInt(2);
					boss.direction = r==1?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
					boss.speedX -= 120;
					boss.status2 = ROLE_STATUS2_MOVE;
					boss.skillStatue = 0;
					boss.runTime1 = System.currentTimeMillis();
					boss.skill1STime = System.currentTimeMillis();
				}
				boss.frame = (boss.frame+1)%boss.frameNum;
			}
			g.drawRegion(bossImg, 0, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		}else if(boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			if(boss.direction == OBJECT_DIRECTION_DOWN){
				boss.mapy += boss.speedY;
				if(boss.mapy+boss.height>=470){
					factory.createBossSkill(boss, null);
					boss.speedY -= 60;
					boss.direction = OBJECT_DIRECTION_UP;
					boss.status2 = ROLE_STATUS2_MOVE;
					boss.skill2STime = System.currentTimeMillis()/1000;
					//boss.skillStatue = 0;
				}
				g.drawRegion(bossImg, 0, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
			}
		}
	}

	private void showBoss3(SGraphics g, MoveObject boss, MoveObjectFactory factory) {
		Image bossImg = Resource.loadImage(boss.picId);
		//int offX1 = ScrW/2-boss.width/2;
		int offX2 = ScrW-boss.width+20;
		int offX3 = ScrW/2 + ScrW/4-boss.width/2;
		boss.skill1ETime = System.currentTimeMillis();
		boss.skill2ETime = System.currentTimeMillis();
		boss.runTime2 = System.currentTimeMillis();
		/*System.out.println("runTime："+(boss.runTime2-boss.runTime1));
		System.out.println("skill1ETime："+(boss.skill1ETime-boss.skill1STime));
		System.out.println("status2:"+boss.status2);
		System.out.println("speedX:"+boss.speedX);*/
		if(boss.status2 == ROLE_STATUS2_MOVE || boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx <= offX3){
					int r = RandomValue.getRandInt(2);
					boss.direction = r==0?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
					boss.skill1STime = System.currentTimeMillis();
				}
			}else if(boss.direction == OBJECT_DIRECTION_RIGHT){
				boss.mapx += boss.speedX*2;
				if(boss.mapx >= offX2){
					boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
					boss.speedX += 120;
					boss.direction = OBJECT_DIRECTION_LEFT;
				}
			}else if(boss.direction == OBJECT_DIRECTION_UP){
				if(boss.runTime2-boss.runTime1 >= 3*1000){
					if(boss.stopInterval < 150){
						boss.stopInterval++;
						boss.mapy -= boss.speedY;
					}else{
						boss.stopInterval = 0;
						boss.runTime1 = System.currentTimeMillis();
					}
				}else{
					boss.bombETime = System.currentTimeMillis();
					if(boss.bombETime-boss.bombSTime>300){
						factory.createSpiritBomb(boss);
						boss.bombSTime = System.currentTimeMillis();
					}
				}

				if(boss.mapy <= startP-30){
					boss.direction = OBJECT_DIRECTION_DOWN;
				}
				if((boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
					boss.skillStatue = 2;
					boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				}
				if(boss.skillStatue != 1 && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
					boss.skillStatue = 1;
					boss.direction = OBJECT_DIRECTION_RIGHT;
				}
			}else if(boss.direction == OBJECT_DIRECTION_DOWN){
				if(boss.runTime2-boss.runTime1 >= 3*1000){
					if(boss.stopInterval < 150){
						boss.stopInterval++;
						boss.mapy += boss.speedY;
					}else{
						boss.stopInterval = 0;
						boss.runTime1 = System.currentTimeMillis();
					}
				}else{
					boss.bombETime = System.currentTimeMillis();
					if(boss.bombETime-boss.bombSTime>300){
						factory.createSpiritBomb(boss);
						boss.bombSTime = System.currentTimeMillis();
					}
				}
				
				if(boss.mapy+boss.height >= endP+30){
					boss.direction = OBJECT_DIRECTION_UP;
				}
				if((boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
					boss.skillStatue = 2;
					boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				}
				if(boss.skillStatue != 1 && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
					boss.skillStatue = 1;
					boss.direction = OBJECT_DIRECTION_RIGHT;
				}
			}
			boss.frame = 0;
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		}else if(boss.status2 == ROLE_STATUS2_SKILL_ATTACK){
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				if(boss.mapx<=0){
					boss.frame = (boss.frame+1)%boss.frameNum;
					if(boss.frame==4){
						boss.direction = OBJECT_DIRECTION_RIGHT;
					}
				}else{
					boss.frame = 1;
					if(boss.speedX-boss.mapx<=0){
						boss.mapx = 0;
					}else{
						boss.mapx -= boss.speedX;
					}
				}
			}else if(boss.direction == OBJECT_DIRECTION_RIGHT){
				boss.mapx += boss.speedX;
				if(boss.mapx >= offX3){
					boss.mapx = offX3;
					int r = RandomValue.getRandInt(2);
					boss.direction = r==1?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
					boss.speedX -= 120;
					boss.status2 = ROLE_STATUS2_MOVE;
					boss.skillStatue = 0;
					boss.runTime1 = System.currentTimeMillis();
					boss.skill1STime = System.currentTimeMillis();
				}
				boss.frame = 1;
			}
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		}
	
	} 

	int fireIndex, fireIndex1,fireIndex2;
	int changeIndex, changeIndex1, changeIndex2;
	private void showBoss4(SGraphics g, MoveObject boss, MoveObjectFactory factory) {
		Image bossImg = Resource.loadImage(boss.picId);
		Image fire = Resource.loadImage(Resource.id_ice_boss_1_fire);
		Image change = Resource.loadImage(Resource.id_ice_boss_1_fire_change);
		int fireW = fire.getWidth()/4, fireH = fire.getHeight();
		int changeW = change.getWidth()/3, changeH = change.getHeight();
		//int offX2 = ScrW-boss.width+20;
		int offX3 = ScrW/2 + ScrW/4-boss.width/2;
		boss.skill1ETime = System.currentTimeMillis();
		boss.skill2ETime = System.currentTimeMillis();
		boss.runTime2 = System.currentTimeMillis();
		if(boss.direction == OBJECT_DIRECTION_LEFT){
			boss.mapx -= boss.speedX;
			if(boss.mapx <= offX3){
				int r = RandomValue.getRandInt(2);
				boss.direction = r==0?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
				boss.skill1STime = System.currentTimeMillis();
				boss.skill2STime = System.currentTimeMillis();
			}
		}else if(boss.direction == OBJECT_DIRECTION_UP){
			if(boss.runTime2-boss.runTime1 >= 3*1000){
				if(boss.stopInterval < 150){
					boss.stopInterval++;
					boss.mapy -= boss.speedY;
				}else{
					boss.stopInterval = 0;
					boss.runTime1 = System.currentTimeMillis();
				}
			}

			if(boss.mapy <= startP-30){
				boss.direction = OBJECT_DIRECTION_DOWN;
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis();
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis();
			}
		}else if(boss.direction == OBJECT_DIRECTION_DOWN){
			if(boss.runTime2-boss.runTime1 >= 3*1000){
				if(boss.stopInterval < 150){
					boss.stopInterval++;
					boss.mapy += boss.speedY;
				}else{
					boss.stopInterval = 0;
					boss.runTime1 = System.currentTimeMillis();
				}
			}
			
			if(boss.mapy+boss.height >= endP+30){
				boss.direction = OBJECT_DIRECTION_UP;
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis();
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis();
			}
		}
		boss.frame = (boss.frame+1)%boss.frameNum;
		g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		
		if(boss.status2 == ROLE_STATUS2_MOVE){
			fireIndex = (fireIndex+1)%3;
			fireIndex1 = (fireIndex1+1)%3;
			fireIndex2 = (fireIndex2+1)%3;
		}else{
			fireIndex = 3;
			fireIndex1 = 3;
			fireIndex2 = 3;
		}
		if(boss.status2 != ROLE_STATUS2_SKILL2_ATTACK){
			g.drawRegion(fire, fireIndex*fireW, 0, fireW, fireH, 0, boss.mapx-fireW, boss.mapy, 20);
			g.drawRegion(fire, fireIndex1*fireW, 0, fireW, fireH, 0, boss.mapx-fireW, boss.mapy+(fireH+5), 20);
			g.drawRegion(fire, fireIndex2*fireW, 0, fireW, fireH, 0, boss.mapx-fireW, boss.mapy+2*(fireH+5), 20);
		}
		
		if(boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			changeIndex = (changeIndex+1)%3;
			changeIndex1 = (changeIndex1+1)%3;
			changeIndex2= (changeIndex2+1)%3;
			g.drawRegion(change, changeIndex*changeW, 0, changeW, changeH, 0, boss.mapx-fireW, boss.mapy, 20);
			g.drawRegion(change, changeIndex1*changeW, 0, changeW, changeH, 0, boss.mapx-fireW, boss.mapy+(fireH+5), 20);
			g.drawRegion(change, changeIndex2*changeW, 0, changeW, changeH, 0, boss.mapx-fireW, boss.mapy+2*(fireH+5), 20);
			
			int num = 3-factory.ghostSpirits.size();
			if(changeIndex == 2){
				factory.createGhostSpirit(boss, num);
				boss.status2 = ROLE_STATUS2_MOVE;
				boss.skillStatue = 0;
			}
		}
	}

	private void showBoss5(SGraphics g, MoveObject boss, MoveObjectFactory factory){
		Image bossImg = Resource.loadImage(boss.picId);
		int offX3 = ScrW/2 + ScrW/4-boss.width/2;
		boss.skill1ETime = System.currentTimeMillis();
		boss.skill2ETime = System.currentTimeMillis();
		boss.runTime2 = System.currentTimeMillis();
		
		if(boss.direction == OBJECT_DIRECTION_LEFT){
			boss.mapx -= boss.speedX;
			if(boss.mapx <= offX3){
				int r = RandomValue.getRandInt(2);
				boss.direction = r==0?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
				boss.skill1STime = System.currentTimeMillis();
				boss.skill2STime = System.currentTimeMillis();
			}
		}else if(boss.direction == OBJECT_DIRECTION_UP){
			if(boss.runTime2-boss.runTime1 >= 3*1000){
				if(boss.stopInterval < 150){
					boss.stopInterval++;
					boss.mapy -= boss.speedY;
				}else{
					boss.stopInterval = 0;
					boss.runTime1 = System.currentTimeMillis();
				}
			}else{
				boss.bombETime = System.currentTimeMillis();
				if(boss.bombETime-boss.bombSTime>300){
					factory.createSpiritBomb(boss);
					boss.bombSTime = System.currentTimeMillis();
				}
			}

			if(boss.mapy <= startP-30){
				boss.direction = OBJECT_DIRECTION_DOWN;
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis();
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis();
			}
		}else if(boss.direction == OBJECT_DIRECTION_DOWN){
			if(boss.runTime2-boss.runTime1 >= 3*1000){
				if(boss.stopInterval < 150){
					boss.stopInterval++;
					boss.mapy += boss.speedY;
				}else{
					boss.stopInterval = 0;
					boss.runTime1 = System.currentTimeMillis();
				}
			}else{
				boss.bombETime = System.currentTimeMillis();
				if(boss.bombETime-boss.bombSTime>300){
					factory.createSpiritBomb(boss);
					boss.bombSTime = System.currentTimeMillis();
				}
			}
			
			if(boss.mapy+boss.height >= endP+30){
				boss.direction = OBJECT_DIRECTION_UP;
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis();
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis();
			}
		}
		if(boss.status2 == ROLE_STATUS2_MOVE){
			boss.frame = 0;
		}else{
			boss.frame = (boss.frame+1)%boss.frameNum;
		}
		g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
	}

	private void showBoss6(SGraphics g, MoveObject boss){
		Image bossImg = Resource.loadImage(boss.picId);
		int offX3 = ScrW/2 + ScrW/4-boss.width/2;
		boss.skill1ETime = System.currentTimeMillis();
		boss.skill2ETime = System.currentTimeMillis();
		boss.runTime2 = System.currentTimeMillis();
		
		if(boss.direction == OBJECT_DIRECTION_LEFT){
			boss.mapx -= boss.speedX;
			if(boss.mapx <= offX3){
				int r = RandomValue.getRandInt(2);
				boss.direction = r==0?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
				boss.skill1STime = System.currentTimeMillis();
				boss.skill2STime = System.currentTimeMillis();
			}
		}else if(boss.direction == OBJECT_DIRECTION_UP){
			if(boss.runTime2-boss.runTime1 >= 3*1000){
				if(boss.stopInterval < 150){
					boss.stopInterval++;
					boss.mapy -= boss.speedY;
				}else{
					boss.stopInterval = 0;
					boss.runTime1 = System.currentTimeMillis();
				}
			}

			if(boss.mapy <= startP-30){
				boss.direction = OBJECT_DIRECTION_DOWN;
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis();
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis();
			}
		}else if(boss.direction == OBJECT_DIRECTION_DOWN){
			if(boss.runTime2-boss.runTime1 >= 3*1000){
				if(boss.stopInterval < 150){
					boss.stopInterval++;
					boss.mapy += boss.speedY;
				}else{
					boss.stopInterval = 0;
					boss.runTime1 = System.currentTimeMillis();
				}
			}
			
			if(boss.mapy+boss.height >= endP+30){
				boss.direction = OBJECT_DIRECTION_UP;
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis();
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis();
			}
		}
		if(boss.status2 == ROLE_STATUS2_MOVE){
			boss.endTime = System.currentTimeMillis();
			if(boss.endTime - boss.startTime >= boss.timeInterval){
				boss.frame = (boss.frame+1)%3;
				boss.startTime = System.currentTimeMillis();
			}
		}else{
			boss.frame = (boss.frame+1)%boss.frameNum;
			if(boss.frame==1){
				boss.frame=3;
			}
		}
		g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
	}

	private void showBoss7(SGraphics g, MoveObject boss, MoveObjectFactory factory) {
		Image bossImg = Resource.loadImage(boss.picId);
		int offX3 = ScrW/2 + ScrW/4-boss.width/2;
		boss.skill1ETime = System.currentTimeMillis();
		boss.skill2ETime = System.currentTimeMillis();
		boss.runTime2 = System.currentTimeMillis();
		
		if(boss.direction == OBJECT_DIRECTION_LEFT){
			boss.mapx -= boss.speedX;
			if(boss.mapx <= offX3){
				int r = RandomValue.getRandInt(2);
				boss.direction = r==0?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
				boss.skill1STime = System.currentTimeMillis();
				boss.skill2STime = System.currentTimeMillis();
			}
		}else if(boss.direction == OBJECT_DIRECTION_UP){
			if(boss.runTime2-boss.runTime1 >= 3*1000){
				if(boss.stopInterval < 150){
					boss.stopInterval++;
					boss.mapy -= boss.speedY;
				}else{
					boss.stopInterval = 0;
					boss.runTime1 = System.currentTimeMillis();
				}
			}else{
				boss.bombETime = System.currentTimeMillis();
				if(boss.bombETime-boss.bombSTime>300){
					factory.createSpiritBomb(boss);
					boss.bombSTime = System.currentTimeMillis();
				}
			}

			if(boss.mapy <= startP-30){
				boss.direction = OBJECT_DIRECTION_DOWN;
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis();
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis();
			}
		}else if(boss.direction == OBJECT_DIRECTION_DOWN){
			if(boss.runTime2-boss.runTime1 >= 3*1000){
				if(boss.stopInterval < 150){
					boss.stopInterval++;
					boss.mapy += boss.speedY;
				}else{
					boss.stopInterval = 0;
					boss.runTime1 = System.currentTimeMillis();
				}
			}else{
				boss.bombETime = System.currentTimeMillis();
				if(boss.bombETime-boss.bombSTime>300){
					factory.createSpiritBomb(boss);
					boss.bombSTime = System.currentTimeMillis();
				}
			}
			
			if(boss.mapy+boss.height >= endP+30){
				boss.direction = OBJECT_DIRECTION_UP;
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill1ETime - boss.skill1STime > boss.skill1Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis();
			}
			if(boss.status2 == ROLE_STATUS2_MOVE && (boss.skill2ETime - boss.skill2STime > boss.skill2Interval*1000)){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis();
			}
		}
		
		if(boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			drawSkill2(g, boss, factory);
		}
		
		if(boss.status2 == ROLE_STATUS2_MOVE || boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			boss.endTime = System.currentTimeMillis();
			if(boss.endTime - boss.startTime >= boss.timeInterval){
				boss.frame = (boss.frame+1)%3;
				boss.startTime = System.currentTimeMillis();
			}
		}else{
			if(boss.frame==3){
				boss.frame=4;
			}else{
				boss.frame = (boss.frame+1)%boss.frameNum;
			}
		}
		g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
	}
	
	int x = 250, y = 250;
	int x1 = 290, y1 = 265, w, h;
	private void drawSkill2(SGraphics g, MoveObject boss, MoveObjectFactory factory) {
		Image skillBg = Resource.loadImage(Resource.id_lava_boss_2_skill_bg);
		Image skill = Resource.loadImage(Resource.id_lava_boss_2_skill_1);
		
		int skillW = skill.getWidth()/3, skillH = skill.getHeight();
		//int skillBgW = skillBg.getWidth(), skillBgH = skillBg.getHeight();
		
		//x1 = x + skillBgW/2-skillW/2;
		//y1 = y+ skillBgH/2;
		if(h<=skillH-3){
			h = (h+3)%skillH;
			y1 -= 3;
			x1 -= 1;
			g.drawImage(skillBg, x, y, 20);
			g.drawRegion(skill, 0, 0, skillW, h, 0, x1, y1, 20);
		}else{
			factory.createBoss8Skill2(x1, y1);
			//x1 = 290;
			//y1 = 265;
			h = 0;
			boss.status2 = ROLE_STATUS2_MOVE;
			x1 = RandomValue.getRandInt(220, 300);
			y1 = RandomValue.getRandInt(265, 465);
			x = x1-40;
			y = y1-15;
		}
	}

	public void showGhostSpirits(SGraphics g, Vector ghostSpirits){
		for(int i=0;i<ghostSpirits.size();i++){
			MoveObject mo = (MoveObject) ghostSpirits.elementAt(i);
			mo.bombETime = System.currentTimeMillis();
			Image moPic = Resource.loadImage(mo.picId);
			mo.frame = (mo.frame+1)%mo.frameNum;
			g.drawRegion(moPic, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			/*mo.mapx -= mo.speedX;*/
			if(mo.direction == OBJECT_DIRECTION_LEFT_DOWN){
				mo.mapx -= mo.speedX;
				mo.mapy += mo.speedY;
				if(mo.mapx+mo.width <= 0){
					mo.direction = OBJECT_DIRECTION_RIGHT_DOWN;
				}else if(mo.mapy+mo.height >= 490){
					mo.direction = OBJECT_DIRECTION_LEFT_UP;
				}
			}else if(mo.direction == OBJECT_DIRECTION_RIGHT_DOWN){
				mo.mapx += mo.speedX;
				mo.mapy += mo.speedY;
				if(mo.mapx+mo.width >= ScrW){
					mo.direction = OBJECT_DIRECTION_LEFT_DOWN;
				}else if(mo.mapy+mo.height >= 490){
					mo.direction = OBJECT_DIRECTION_RIGHT_UP;
				}
			}else if(mo.direction == OBJECT_DIRECTION_LEFT_UP){
				mo.mapx -= mo.speedX;
				mo.mapy -= mo.speedY;
				if(mo.mapx+mo.width <= 0){
					mo.direction = OBJECT_DIRECTION_RIGHT_UP;
				}else if(mo.mapy <= startP){
					mo.direction = OBJECT_DIRECTION_LEFT_DOWN;
				}
			}else if(mo.direction == OBJECT_DIRECTION_RIGHT_UP){
				mo.mapx += mo.speedX;
				mo.mapy -= mo.speedY;
				if(mo.mapx+mo.width >= ScrW){
					mo.direction = OBJECT_DIRECTION_LEFT_UP;
				}else if(mo.mapy <= startP){
					mo.direction = OBJECT_DIRECTION_RIGHT_DOWN;
				}
			}
			
			if(mo.bombETime-mo.bombSTime > mo.bombInterval*1000){
				mo.status2 = ROLE_STATUS2_ATTACK;
				mo.bombSTime = System.currentTimeMillis();
			}
			
			/*if(mo.mapx+mo.width<=0){
				mo.status = ROLE_STATUS_DEAD;
			}*/
		}
	}
	
	public void showBoss8Spirit(SGraphics g, MoveObject boss8Spirit){
		if(boss8Spirit==null){
			return;
		}
	 	boss8Spirit.bombETime = System.currentTimeMillis();
		Image moPic = Resource.loadImage(boss8Spirit.picId);
		//boss8Spirit.frame = (boss8Spirit.frame+1)%boss8Spirit.frameNum;
		g.drawRegion(moPic, 0, 0, boss8Spirit.width, boss8Spirit.height, 0, boss8Spirit.mapx, boss8Spirit.mapy, 20);
		boss8Spirit.mapx -= boss8Spirit.speedX;
		
		if(boss8Spirit.bombETime-boss8Spirit.bombSTime > boss8Spirit.bombInterval*500){
			boss8Spirit.bombSTime = System.currentTimeMillis();
			boss8Spirit.status = ROLE_STATUS_DEAD;   
		}
		
	}
	
	public void showPropsIcon(SGraphics g, Vector props){
		for(int i=0;i<props.size();i++){
			MoveObject mo = (MoveObject) props.elementAt(i);
			mo.bombETime = System.currentTimeMillis();
			Image moPic = Resource.loadImage(mo.picId);
			g.drawRegion(moPic, 0, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			if(mo.direction == OBJECT_DIRECTION_LEFT_DOWN){
				mo.mapx -= mo.speedX;
				mo.mapy += mo.speedY;
				if(mo.mapx+mo.width <= 0){
					//mo.direction = OBJECT_DIRECTION_RIGHT_DOWN;
					mo.status = ROLE_STATUS_DEAD;
				}else if(mo.mapy+mo.height >= 490){
					mo.direction = OBJECT_DIRECTION_LEFT_UP;
				}
			}else if(mo.direction == OBJECT_DIRECTION_RIGHT_DOWN){
				mo.mapx += mo.speedX;
				mo.mapy += mo.speedY;
				if(mo.mapx+mo.width >= ScrW){
					mo.direction = OBJECT_DIRECTION_LEFT_DOWN;
				}else if(mo.mapy+mo.height >= 490){
					mo.direction = OBJECT_DIRECTION_RIGHT_UP;
				}
			}else if(mo.direction == OBJECT_DIRECTION_LEFT_UP){
				mo.mapx -= mo.speedX;
				mo.mapy -= mo.speedY;
				if(mo.mapx+mo.width <= 0){
					//mo.direction = OBJECT_DIRECTION_RIGHT_UP;
					mo.status = ROLE_STATUS_DEAD;
				}else if(mo.mapy <= startP){
					mo.direction = OBJECT_DIRECTION_LEFT_DOWN;
				}
			}else if(mo.direction == OBJECT_DIRECTION_RIGHT_UP){
				mo.mapx += mo.speedX;
				mo.mapy -= mo.speedY;
				if(mo.mapx+mo.width >= ScrW){
					mo.direction = OBJECT_DIRECTION_LEFT_UP;
				}else if(mo.mapy <= startP){
					mo.direction = OBJECT_DIRECTION_RIGHT_DOWN;
				}
			}
		}
	}
}

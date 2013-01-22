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
	
	public void showMissile(SGraphics g, Vector missiles){
		for(int i=0;i<missiles.size();i++){
			MoveObject mo = (MoveObject)missiles.elementAt(i);
			Image moPic = Resource.loadImage(mo.picId);
			g.drawImage(moPic, mo.mapx, mo.mapy, 20);
			mo.mapx += mo.speedX;
			if(mo.mapx>ScrW){
				mo.status = ROLE_STATUS_DEAD;
			}
		}
	}
	
	public void showWingplane(SGraphics g, Vector wingplane, MoveObject player){
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
			g.drawRegion(moPic, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
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
			showBoss(g, boss);
			break;
		case 201:
			showBoss(g, boss);
			break;
		case 202:
			showBoss2(g, boss, factory);
			break;
		case 203:
			showBoss3(g, boss);
			break;
		case 204:
			showBoss4(g, boss, factory);
			break;
		case 205:
			showBoss5(g, boss);
			break;
		case 206:
			showBoss6(g, boss);
			break;
		case 207:
			showBoss7(g, boss, factory);
			break;
		}
	}

	private void showBoss(SGraphics g, MoveObject boss) {
		Image bossImg = Resource.loadImage(boss.picId);
		if(boss.status2 == ROLE_STATUS2_MOVE || boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			boss.skill1ETime = System.currentTimeMillis()/1000;
			boss.skill2ETime = System.currentTimeMillis()/1000;
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx < (ScrW-boss.width+20)){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}else if(boss.direction == OBJECT_DIRECTION_UP){
				boss.mapy -= boss.speedY;
				if(boss.mapy <= startP-30){
					boss.direction = OBJECT_DIRECTION_DOWN;
				}
			}else if(boss.direction == OBJECT_DIRECTION_DOWN){
				boss.mapy += boss.speedY;
				if(boss.mapy+boss.height >= endP+30){
					boss.direction = OBJECT_DIRECTION_UP;
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
				if(boss.mapx >= ScrW-boss.width-20){
					int r = RandomValue.getRandInt(2);
					boss.direction = r==1?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
					boss.speedX -= 120;
					boss.status2 = ROLE_STATUS2_MOVE;
				}
				boss.frame = (boss.frame+1)%boss.frameNum;
			}
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		}
		if(boss.skill1ETime-boss.skill1STime>boss.skill1Interval && boss.status2 == ROLE_STATUS2_MOVE){
			boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
			boss.direction = OBJECT_DIRECTION_LEFT;
			boss.speedX += 120;
			boss.skill1STime = System.currentTimeMillis()/1000;
		}
		
		if(boss.skill2ETime-boss.skill2STime>boss.skill2Interval && boss.status2 == ROLE_STATUS2_MOVE){
			boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
			boss.skill2STime = System.currentTimeMillis()/1000;
		}
	}
	
	private void showBoss2(SGraphics g, MoveObject boss, MoveObjectFactory factory) {
		Image bossImg = Resource.loadImage(boss.picId);
		
		if(boss.skill1ETime-boss.skill1STime>boss.skill1Interval && boss.status2 == ROLE_STATUS2_MOVE){
			boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
			boss.direction = OBJECT_DIRECTION_LEFT;
			boss.speedX += 120;
			boss.skill1STime = System.currentTimeMillis()/1000;
		}
		
		if(boss.skill2ETime-boss.skill2STime>boss.skill2Interval && boss.status2 == ROLE_STATUS2_MOVE){
			boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
			boss.direction = OBJECT_DIRECTION_DOWN;
			boss.speedY += 60;
			boss.skill2STime = System.currentTimeMillis()/1000;
		}
		
		if(boss.status2 == ROLE_STATUS2_MOVE){
			boss.skill1ETime = System.currentTimeMillis()/1000;
			boss.skill2ETime = System.currentTimeMillis()/1000;
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx < (ScrW-boss.width-20)){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}else if(boss.direction == OBJECT_DIRECTION_UP){
				boss.mapy -= boss.speedY;
				if(boss.mapy <= startP-10){
					boss.direction = OBJECT_DIRECTION_DOWN;
				}
			}else if(boss.direction == OBJECT_DIRECTION_DOWN){
				boss.mapy += boss.speedY;
				if(boss.mapy+boss.height >= endP+10){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}
			g.drawRegion(bossImg, 0, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		}else if(boss.status2 == ROLE_STATUS2_SKILL_ATTACK){
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx<=10){
					boss.direction = OBJECT_DIRECTION_RIGHT;
				}
			}else if(boss.direction == OBJECT_DIRECTION_RIGHT){
				boss.mapx += boss.speedX;
				if(boss.mapx >= ScrW-boss.width-20){
					int r = RandomValue.getRandInt(2);
					boss.direction = r==1?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
					boss.speedX -= 120;
					boss.status2 = ROLE_STATUS2_MOVE;
				}
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
				}
			}/*else if(boss.direction == OBJECT_DIRECTION_UP){
				boss.mapy -= boss.speedY;
				if(boss.mapy >= ScrW-boss.width-20){
					//int r = RandomValue.getRandInt(2);
					//boss.direction = r==1?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
					boss.status2 = ROLE_STATUS2_MOVE;
				}
			}*/
			g.drawRegion(bossImg, 0, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		}
		
	}

	private void showBoss3(SGraphics g, MoveObject boss) {
		Image bossImg = Resource.loadImage(boss.picId);
		if(boss.status2 == ROLE_STATUS2_MOVE || boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			boss.skill1ETime = System.currentTimeMillis()/1000;
			boss.skill2ETime = System.currentTimeMillis()/1000;
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx < (ScrW-boss.width-20)){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}else if(boss.direction == OBJECT_DIRECTION_UP){
				boss.mapy -= boss.speedY;
				if(boss.mapy <= startP-10){
					boss.direction = OBJECT_DIRECTION_DOWN;
				}
			}else if(boss.direction == OBJECT_DIRECTION_DOWN){
				boss.mapy += boss.speedY;
				if(boss.mapy+boss.height >= endP+10){
					boss.direction = OBJECT_DIRECTION_UP;
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
				if(boss.mapx >= ScrW-boss.width-20){
					int r = RandomValue.getRandInt(2);
					boss.direction = r==1?OBJECT_DIRECTION_UP:OBJECT_DIRECTION_DOWN;
					boss.speedX -= 120;
					boss.status2 = ROLE_STATUS2_MOVE;
				}
				boss.frame = 1;
			}
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		}
		
		if(boss.skill1ETime-boss.skill1STime>boss.skill1Interval && boss.status2 == ROLE_STATUS2_MOVE){
			boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
			boss.direction = OBJECT_DIRECTION_LEFT;
			boss.speedX += 120;
			boss.skill1STime = System.currentTimeMillis()/1000;
		}
		
		if(boss.skill2ETime-boss.skill2STime>boss.skill2Interval && boss.status2 == ROLE_STATUS2_MOVE){
			boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
			boss.skill2STime = System.currentTimeMillis()/1000;
		}
	}

	int fireIndex, fireIndex1,fireIndex2;
	int changeIndex, changeIndex1, changeIndex2;
	private void showBoss4(SGraphics g, MoveObject boss, MoveObjectFactory facotry) {
		Image bossImg = Resource.loadImage(boss.picId);
		Image fire = Resource.loadImage(Resource.id_ice_boss_1_fire);
		Image change = Resource.loadImage(Resource.id_ice_boss_1_fire_change);
		int fireW = fire.getWidth()/4, fireH = fire.getHeight();
		int changeW = change.getWidth()/3, changeH = change.getHeight();
		//if(boss.status2 == ROLE_STATUS2_MOVE || boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			boss.skill1ETime = System.currentTimeMillis()/1000;
			boss.skill2ETime = System.currentTimeMillis()/1000;
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx < (ScrW-boss.width-20)){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}else if(boss.direction == OBJECT_DIRECTION_UP){
				boss.mapy -= boss.speedY;
				if(boss.mapy <= startP-10){
					boss.direction = OBJECT_DIRECTION_DOWN;
				}
			}else if(boss.direction == OBJECT_DIRECTION_DOWN){
				boss.mapy += boss.speedY;
				if(boss.mapy+boss.height >= endP+10){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}
			boss.frame = (boss.frame+1)%boss.frameNum;
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
			
			if(boss.skill1ETime-boss.skill1STime>boss.skill1Interval && boss.status2 == ROLE_STATUS2_MOVE){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis()/1000;
			}
			
			if(boss.skill2ETime-boss.skill2STime>boss.skill2Interval && boss.status2 == ROLE_STATUS2_MOVE){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis()/1000;
			}
			
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
				
				if(changeIndex == 2){
					facotry.createGhostSpirit(boss);
					boss.status2 = ROLE_STATUS2_MOVE;
				}
			}
		//}
	}

	private void showBoss5(SGraphics g, MoveObject boss) {
		Image bossImg = Resource.loadImage(boss.picId);
		//if(boss.status2 == ROLE_STATUS2_MOVE || boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			boss.skill1ETime = System.currentTimeMillis()/1000;
			boss.skill2ETime = System.currentTimeMillis()/1000;
			
			if(boss.skill1ETime-boss.skill1STime>boss.skill1Interval && boss.status2 == ROLE_STATUS2_MOVE){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis()/1000;
			}
			
			if(boss.skill2ETime-boss.skill2STime>boss.skill2Interval && boss.status2 == ROLE_STATUS2_MOVE){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis()/1000;
			}
			
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx < (ScrW-boss.width-20)){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}else if(boss.direction == OBJECT_DIRECTION_UP){
				boss.mapy -= boss.speedY;
				if(boss.mapy <= startP-10){
					boss.direction = OBJECT_DIRECTION_DOWN;
				}
			}else if(boss.direction == OBJECT_DIRECTION_DOWN){
				boss.mapy += boss.speedY;
				if(boss.mapy+boss.height >= endP+10){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}
			if(boss.status2 == ROLE_STATUS2_MOVE){
				boss.frame = 0;
			}else{
				boss.frame = (boss.frame+1)%boss.frameNum;
			}
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		//}
		
	}

	private void showBoss6(SGraphics g, MoveObject boss) {
		Image bossImg = Resource.loadImage(boss.picId);
		//if(boss.status2 == ROLE_STATUS2_MOVE || boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			boss.skill1ETime = System.currentTimeMillis()/1000;
			boss.skill2ETime = System.currentTimeMillis()/1000;
			
			if(boss.skill1ETime-boss.skill1STime>boss.skill1Interval && boss.status2 == ROLE_STATUS2_MOVE){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis()/1000;
			}
			
			if(boss.skill2ETime-boss.skill2STime>boss.skill2Interval && boss.status2 == ROLE_STATUS2_MOVE){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis()/1000;
			}
			
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx < (ScrW-boss.width-20)){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}else if(boss.direction == OBJECT_DIRECTION_UP){
				boss.mapy -= boss.speedY;
				if(boss.mapy <= startP-10){
					boss.direction = OBJECT_DIRECTION_DOWN;
				}
			}else if(boss.direction == OBJECT_DIRECTION_DOWN){
				boss.mapy += boss.speedY;
				if(boss.mapy+boss.height >= endP+10){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}
			if(boss.status2 == ROLE_STATUS2_MOVE){
				boss.frame = (boss.frame+1)%3;
			}else{
				boss.frame = (boss.frame+1)%boss.frameNum;
				if(boss.frame==1){
					boss.frame=3;
				}
			}
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		//}
	}

	private void showBoss7(SGraphics g, MoveObject boss, MoveObjectFactory factory) {
		Image bossImg = Resource.loadImage(boss.picId);
		//if(boss.status2 == ROLE_STATUS2_MOVE || boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
			boss.skill1ETime = System.currentTimeMillis()/1000;
			boss.skill2ETime = System.currentTimeMillis()/1000;
			
			if(boss.skill1ETime-boss.skill1STime>boss.skill1Interval 
					&& boss.status2 == ROLE_STATUS2_MOVE){
				boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
				boss.skill1STime = System.currentTimeMillis()/1000;
			}
			
			if(boss.skill2ETime-boss.skill2STime>boss.skill2Interval && boss.status2 == ROLE_STATUS2_MOVE){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis()/1000;
			}
			
			if(boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
				drawSkill2(g, boss, factory);
			}
			
			if(boss.direction == OBJECT_DIRECTION_LEFT){
				boss.mapx -= boss.speedX;
				if(boss.mapx < (ScrW-boss.width-20)){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}else if(boss.direction == OBJECT_DIRECTION_UP){
				if(boss.status2 == ROLE_STATUS2_MOVE){
					boss.mapy -= boss.speedY;
				}
				if(boss.mapy <= startP-10){
					boss.direction = OBJECT_DIRECTION_DOWN;
				}
			}else if(boss.direction == OBJECT_DIRECTION_DOWN){
				if(boss.status2 == ROLE_STATUS2_MOVE){
					boss.mapy += boss.speedY;
				}
				if(boss.mapy+boss.height >= endP+10){
					boss.direction = OBJECT_DIRECTION_UP;
				}
			}
			if(boss.status2 == ROLE_STATUS2_MOVE){
				boss.frame = (boss.frame+1)%3;
			}else{
				if(boss.frame==3){
					boss.frame=4;
				}else{
					boss.frame = (boss.frame+1)%boss.frameNum;
				}
			}
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
		//}
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
			x1 = 290;
			y1 = 265;
			h = 0;
			boss.status2 = ROLE_STATUS2_MOVE;
		}
	}

	public void showGhostSpirits(SGraphics g, Vector ghostSpirits){
		for(int i=0;i<ghostSpirits.size();i++){
			MoveObject mo = (MoveObject) ghostSpirits.elementAt(i);
			mo.bombETime = System.currentTimeMillis();
			Image moPic = Resource.loadImage(mo.picId);
			mo.frame = (mo.frame+1)%mo.frameNum;
			g.drawRegion(moPic, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			mo.mapx -= mo.speedX;
			
			if(mo.bombETime-mo.bombSTime > mo.bombInterval*1000){
				mo.status2 = ROLE_STATUS2_ATTACK;
				mo.bombSTime = System.currentTimeMillis();
			}
			
			if(mo.mapx+mo.width<=0){
				mo.status = ROLE_STATUS_DEAD;
			}
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

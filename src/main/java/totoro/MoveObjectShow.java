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
			g.drawRegion(moImg, 0, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
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
			}else if(mo.objectId == 204){
				mo.frame = (mo.frame+1)%mo.frameNum;
				g.drawRegion(moImg, mo.frame*mo.width, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			}else{
				g.drawRegion(moImg, 0, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			}
			mo.mapx -= mo.speedX;
			mo.mapy += mo.speedY;
			if(mo.mapx+mo.width <= 0){
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
			showBoss7(g, boss);
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
		System.out.println("boss.status2:"+(boss.status2));
		if(boss.skill1ETime-boss.skill1STime>boss.skill1Interval && boss.status2 == ROLE_STATUS2_MOVE){
			System.out.println("skill_1");
			boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
			boss.direction = OBJECT_DIRECTION_LEFT;
			boss.speedX += 120;
			boss.skill1STime = System.currentTimeMillis()/1000;
		}
		
		if(boss.skill2ETime-boss.skill2STime>boss.skill2Interval && boss.status2 == ROLE_STATUS2_MOVE){
			System.out.println("skill_2");
			boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
			boss.skill2STime = System.currentTimeMillis()/1000;
		}
	}
	
	private void showBoss2(SGraphics g, MoveObject boss, MoveObjectFactory factory) {
		Image bossImg = Resource.loadImage(boss.picId);
		if(boss.status2 == ROLE_STATUS2_MOVE){
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
					factory.createBossSkill(boss);
					boss.speedY -= 120;
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
		
		if(boss.skill1ETime-boss.skill1STime>boss.skill1Interval && boss.status2 == ROLE_STATUS2_MOVE){
			boss.status2 = ROLE_STATUS2_SKILL_ATTACK;
			boss.direction = OBJECT_DIRECTION_LEFT;
			boss.speedX += 120;
			boss.skill1STime = System.currentTimeMillis()/1000;
		}
		
		if(boss.skill2ETime-boss.skill2STime>boss.skill2Interval && boss.status2 == ROLE_STATUS2_MOVE){
			boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
			boss.direction = OBJECT_DIRECTION_DOWN;
			boss.speedY += 120;
			boss.skill2STime = System.currentTimeMillis()/1000;
		}
	}

	private void showBoss3(SGraphics g, MoveObject boss) {
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
			
			/*if(boss.skill2ETime-boss.skill2STime>boss.skill2Interval && boss.status2 == ROLE_STATUS2_MOVE){
				boss.status2 = ROLE_STATUS2_SKILL2_ATTACK;
				boss.skill2STime = System.currentTimeMillis()/1000;
			}*/
			
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
		// TODO Auto-generated method stub
		
	}

	private void showBoss7(SGraphics g, MoveObject boss) {
		// TODO Auto-generated method stub
		
	}
	
	public void showGhostSpirits(SGraphics g, MoveObjectFactory factory){
		for(int i=0;i<factory.ghostSpirits.size();i++){
			MoveObject mo = (MoveObject) factory.ghostSpirits.elementAt(i);
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

}

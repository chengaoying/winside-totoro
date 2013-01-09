package totoro;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;

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
		Image playerImg = Resource.loadImage(player.picId);
		int playerW = playerImg.getWidth()/3, playerH = playerImg.getHeight();
		g.drawRegion(playerImg, player.frame*playerW, 0, playerW, playerH, 0, player.mapx, player.mapy, 20);
		player.frame = (player.frame+1)%3;
	}
	
	public void showBombs(SGraphics g, Vector bombs){
		for(int i=bombs.size()-1;i>=0;i--){
			MoveObject object = (MoveObject) bombs.elementAt(i);
			Image bomb = Resource.loadImage(object.picId);
			int bombW = bomb.getWidth()/5, bombH = bomb.getHeight();
			g.drawRegion(bomb, 0, 0, bombW, bombH, 0, object.mapx, object.mapy, 20);
			object.mapx += object.speedX;
			if(object.mapx > ScrW){
				object.status = ROLE_STATUS_DEAD;
			}
		}
	}
	
	public void showSpirits(SGraphics g, Vector spirits){
		g.setClip(0, 0, ScrW, gameH);
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
			}else if(mo.id == 302){
				g.drawRegion(moPic, 0, 0, mo.width, mo.height, 0, mo.mapx, mo.mapy, 20);
			}
			mo.mapx -= mo.speedX;
			
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
	
	public void showBoss(SGraphics g, Vector bosses){
		for(int i=0;i<bosses.size();i++){
			MoveObject boss = (MoveObject) bosses.elementAt(i);
			boss.bombETime = System.currentTimeMillis();
			Image bossImg = Resource.loadImage(boss.picId);
			boss.frame = (boss.frame+1)%boss.frameNum;
			g.drawRegion(bossImg, boss.frame*boss.width, 0, boss.width, boss.height, 0, boss.mapx, boss.mapy, 20);
			if(boss.status2 == ROLE_STATUS2_MOVE || boss.status2 == ROLE_STATUS2_ATTACK){
				if(boss.direction == OBJECT_DIRECTION_LEFT){
					boss.mapx -= boss.speedX;
					if(boss.mapx < (ScrW-boss.width+20)){
						boss.direction = OBJECT_DIRECTION_UP;
					}
				}else if(boss.direction == OBJECT_DIRECTION_UP){
					boss.mapy -= boss.speedY;
					if(boss.mapy <= 0){
						boss.direction = OBJECT_DIRECTION_DOWN;
					}
				}else if(boss.direction == OBJECT_DIRECTION_DOWN){
					boss.mapy += boss.speedY;
					if(boss.mapy+boss.height >= endP){
						boss.direction = OBJECT_DIRECTION_UP;
					}
				}
			}else if(boss.status2 == ROLE_STATUS2_SKILL_ATTACK){
				
			}
			if(boss.bombETime - boss.bombSTime >= boss.bombInterval*1000){
				if(boss.status == ROLE_STATUS_ALIVE){
					boss.status2 = ROLE_STATUS2_ATTACK;
					boss.bombSTime = System.currentTimeMillis();
				}
			}
		}
	}
	
}

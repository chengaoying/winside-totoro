package sheepwar;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.util.RandomValue;

/**
 * 每次以批的形式出现的狼
 * @author xiaochen
 */
public class Batches implements Common{
	
	int count;				//每批狼的数量
	int period;				//每只出现的间隔
	int pattern;			//空中分布方式
	int ballonId;			//气球种类
	
	public Vector npcs = new Vector();   
	private int[] coors = {60,110,160,210};  //狼下落点的横坐标

	/* 气球属性 */
	public static int bublePara[] = {
	/* 0 图片宽，1 图片高，2提供的积分 */
	   45, 75, 200
	};
	
	/*npc（狼）属性*/
	public static int npcPara[] = {
		/*0-图片宽度，1-图片高度，2-移动速度，3-初始X坐标，4-初始Y坐标,*/
		45, 56, 5, -45-45, 26
	};
	
	/*创建一批狼*/
	public void createBatches(int level, int batch, int position){
		int count = BatchesInfo[level-1][batch][0];	//该批狼的数量
		int spreed_mode = BatchesInfo[level-1][batch][2];
		int ran = RandomValue.getRandInt(regular.length-1);  //折线方式
		for(int i=0;i<count;i++){
			Role wolf = new Role();
			wolf.status = ROLE_ALIVE;
			wolf.status2 = ROLE_ON_GROUND;
			wolf.speed = npcPara[2];
			wolf.width = npcPara[0];
			wolf.height = npcPara[1];
			wolf.direction = ROLE_MOVE_RIGHT; 
			wolf.position = position;
			wolf.colorId = BatchesInfo[level-1][batch][1];
			if(wolf.position == WOLF_POSITION_TOP){
				wolf.mapy = npcPara[4];
			}else{
				wolf.mapy = 466;
			}
			setWolfInfo(count, spreed_mode, wolf, i, ran); 
			
			npcs.addElement(wolf);
		}
	}
	
	/**
	 * 根据该批狼的数量，分布方式设置狼的起始坐标和降落点
	 * @param count  狼的数量
	 * @param spreed_mode  分布方式
	 * @param wolf
	 * @param i     该批狼的中第几只
	 */
	private void setWolfInfo(int count, int spreed_mode, Role wolf, int i, int ran){
		int space = 45;
		switch (count){
		case 1:
			wolf.mapx = npcPara[3];
			wolf.coorX = coors[RandomValue.getRandInt(0,4)];
			break;
		case 2:
			switch (spreed_mode){
			case SPREED_BELOW:
				wolf.mapx = npcPara[3] - i*(space*3);
				wolf.coorX = coors[2-i];
				break;
			case SPREED_ABOVE:
				wolf.mapx = npcPara[3] - i*space;
				wolf.coorX = coors[1+i];
				break;
			case SPREED_VERTICAL:
				wolf.mapx = npcPara[3] - i*(space+space);
				wolf.coorX = coors[1];
				break;
			}
			break;
		case 3:
			switch (spreed_mode){
			case SPREED_BELOW:
				wolf.mapx = npcPara[3] - i*(space*3);
				wolf.coorX = coors[2-i];
				break;
			case SPREED_ABOVE:
				wolf.mapx = npcPara[3] - i*space;
				wolf.coorX = coors[0+i];
				break;
			case SPREED_VERTICAL:
				wolf.mapx = npcPara[3] - i*(space+space);
				wolf.coorX = coors[1];
				break;
			}
			break;
		case 4:
			switch (spreed_mode){
			case SPREED_BELOW:
				wolf.mapx = npcPara[3] - i*(space*3);
				wolf.coorX = coors[3-i];
				break;
			case SPREED_ABOVE:
				wolf.mapx = npcPara[3] - i*space;
				wolf.coorX = coors[0+i];
				break;
			case SPREED_VERTICAL:
				wolf.mapx = npcPara[3] - i*(space+space);
				wolf.coorX = coors[1];
				break;
			case SPREED_IRREGULAR:
				wolf.coorX = coors[regular[ran][i]-1];
				wolf.mapx = npcPara[3] - i*(space+space);
				/*switch (ran){
				case 0:
					wolf.coorX = coors[regular[ran][i]-1];
					wolf.mapx = npcPara[3] - i*space;
					break;
				case 1:
					wolf.coorX = coors[regular[ran][i]-1];
					wolf.mapx = npcPara[3] - i*space;
					break;
				case 2:
					wolf.coorX = coors[regular[ran][i]-1];
					wolf.mapx = npcPara[3] - i*space;
					break;
				case 3:
					wolf.coorX = coors[regular[ran][i]-1];
					wolf.mapx = npcPara[3] - i*space;
					break;
				case 4:
					wolf.coorX = coors[regular[ran][i]-1];
					wolf.mapx = npcPara[3] - i*space;
					break;
				}*/
				break;
			}
			break;
		}
	}
	
	/*狼
	private void createWolf(int level) {
		Role wolf = new Role();
		wolf.status = ROLE_ALIVE;
		wolf.mapx = npcPara[3];
		wolf.mapy = npcPara[4];
		wolf.speed = npcPara[2];
		wolf.width = npcPara[0];
		wolf.height = npcPara[1];
		wolf.coorX = coors[RandomValue.getRandInt(0,4)];		
		wolf.direction = ROLE_MOVE_RIGHT;         
		
		npcs.addElement(wolf);
	}*/
	
	/*气球*/
	public void createBallon(Role wolf){
		if(wolf.role != null){
			return;
		}
		Role ballon = new Role();
		ballon.id = wolf.colorId;
		ballon.width = bublePara[0];
		ballon.height = bublePara[1];
		ballon.scores = bublePara[2];
		ballon.mapx = wolf.mapx+12;
		ballon.mapy = ballon.height - (wolf.mapy+58);      
		ballon.speed = wolf.speed;
		System.out.println("创建气球");
		wolf.role = ballon;
	}

	public void showWolf(SGraphics g, Weapon weapon) {
		Image wolf_Image = Resource.loadImage(Resource.id_wolf_run); 
		Image wolf_down = Resource.loadImage(Resource.id_wolf_down);
		Image wolf_climb = Resource.loadImage(Resource.id_wolf_climb);
		int len = npcs.size();
		Role wolf = null;
		for(int i=len-1;i>=0;i--){
			wolf = (Role)npcs.elementAt(i);
			if(wolf.position == WOLF_POSITION_TOP){  //狼由上往下
				wolfDown(g, wolf, weapon, wolf_Image, wolf_down, wolf_climb);
				
				/*向下的临界点*/
				if(wolf.mapx == wolf.coorX){    
					wolf.direction = ROLE_MOVE_DOWN;
				}
				/*向右的临界点*/
				if(wolf.mapy >= 463){
					wolf.direction = ROLE_MOVE_RIGHT;
				}
				/*向上临界点*/
				if(wolf.mapx == 420){     
					wolf.direction = ROLE_MOVE_UP;
					if(wolf.position == 0){
						setWolfLadders(wolf);
					}
				}
			}else if(wolf.position == WOLF_POSITION_BOTTOM){  //狼由下往上
				wolfUp(g, wolf, weapon, wolf_Image, wolf_down, wolf_climb);
				
				/*向上的临界点*/
				if(wolf.mapx == wolf.coorX){    
					wolf.direction = ROLE_MOVE_UP;
				}
				/*向右的临界点*/
				if(wolf.mapy <= 26){
					wolf.direction = ROLE_MOVE_RIGHT;
				}
			}
		}	
	}
	
	/*狼降落*/
	private void wolfDown(SGraphics g, Role wolf, Weapon weapon, Image wolf_Image, Image wolf_down,Image wolf_climb){
		int tempx = wolf.mapx;
		int tempy = wolf.mapy;
		if(wolf.direction == ROLE_MOVE_RIGHT){  //向右走
			if(!StateGame.pasueState){
				tempx += wolf.speed;
				wolf.mapx = tempx;
				wolf.frame = (wolf.frame + 1) % 6; 
			}
			g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, 0, tempx, tempy, 20);
		}else if(wolf.direction == ROLE_MOVE_DOWN){   //向下走
			createBallon(wolf); //创建气球
			wolf.status2 = ROLE_IN_AIR;
			int tempx_ballon = wolf.role.mapx;
			int tempy_ballon = wolf.role.mapy;
			Image ballon = Resource.loadImage(wolf.role.id);
			if(wolf.status == ROLE_ALIVE){
				if(!StateGame.pasueState){
					if(wolf.role.frame<2){
						wolf.role.frame = (wolf.role.frame+1);
					}else{
						tempy += wolf.speed;
						wolf.mapy = tempy;
						tempy_ballon += wolf.role.speed;
						wolf.role.mapy = tempy_ballon;
					}
					if(wolf.colorId != blue ){			//根据狼气球的颜色区分是否攻击的狼
						if(wolf.mapy == 146){				
							weapon.createBoom(wolf, Weapon.WEAPON_MOVE_RIGHT);
						}
					}
				}
				g.drawRegion(wolf_Image, 0, 0, wolf.width, wolf.height, 0, tempx, tempy, 20);
				g.drawRegion(ballon, wolf.role.frame*wolf.role.width, 0, wolf.role.width, wolf.role.height, 0, tempx_ballon, tempy_ballon, 20);
			}else if(wolf.status == ROLE_DEATH){
				if(wolf.role.frame<4){
					wolf.role.frame = (wolf.role.frame+1);
					g.drawRegion(ballon, wolf.role.frame*wolf.role.width, 0, wolf.role.width, wolf.role.height, 0, tempx_ballon, tempy_ballon, 20);
				}else{
					tempy += wolf.speed;
					wolf.mapy = tempy;
				}
				int w = wolf_down.getWidth()/2;
				int h = wolf_down.getHeight();
				wolf.frame = (wolf.frame+1)%2;
				g.drawRegion(wolf_down, wolf.frame*w, 0, w, h, 0, tempx, tempy, 20);
			}
			
		}else if(wolf.direction == ROLE_MOVE_UP){    //向上走
			wolf.frame = 0;
			int positionY = 190;
			if(wolf.position == ON_ONE_LADDER){
				if(tempy >= positionY){
					tempy -= wolf.speed;
					wolf.mapy = tempy;
					wolf.frame = (wolf.frame+1)%2;
				}
			}else if(wolf.position == ON_TWO_LADDER){
				if(tempy >= positionY+89){
					tempy -= wolf.speed;
					wolf.mapy = tempy;
					wolf.frame = (wolf.frame+1)%2;
				}
			}else if(wolf.position == ON_THREE_LADDER){
				if(tempy >= positionY+89*2){
					tempy -= wolf.speed;
					wolf.mapy = tempy;
					wolf.frame = (wolf.frame+1)%2;
				}
			}else if(wolf.position == ON_FOUR_LADDER){
				if(tempy >= positionY+89*3){
					tempy -= wolf.speed;
					wolf.mapy = tempy;
					wolf.frame = (wolf.frame+1)%2;
				}
			}
			int w = wolf_climb.getWidth()/2;
			int h = wolf_climb.getHeight();
			g.drawRegion(wolf_climb, wolf.frame*w, 0, w, h, 0, tempx, tempy, 20);
		}
		
	}
	
	/*狼上升*/
	private void wolfUp(SGraphics g, Role wolf, Weapon weapon, Image wolf_Image, Image wolf_down,Image wolf_climb){
		int tempx = wolf.mapx;
		int tempy = wolf.mapy;
		if(wolf.direction == ROLE_MOVE_RIGHT){  //向右走
			if(!StateGame.pasueState){
				tempx += wolf.speed;
				wolf.mapx = tempx;
				wolf.frame = (wolf.frame + 1) % 6; 
			}
			g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, 0, tempx, tempy, 20);
		}else if(wolf.direction == ROLE_MOVE_UP){   //向上走
			createBallon(wolf); //创建气球
			wolf.status2 = ROLE_IN_AIR;
			int tempx_ballon = wolf.role.mapx;
			int tempy_ballon = wolf.role.mapy;
			Image ballon = Resource.loadImage(wolf.role.id);
			if(wolf.status == ROLE_ALIVE){
				if(!StateGame.pasueState){
					if(wolf.role.frame<2){
						wolf.role.frame = (wolf.role.frame+1);
					}else{
						tempy -= wolf.speed;
						wolf.mapy = tempy;
						tempy_ballon -= wolf.role.speed;
						wolf.role.mapy = tempy_ballon;
					}
					if(wolf.colorId != blue ){			//根据狼气球的颜色区分是否攻击的狼
						if(wolf.mapy == 146){				
							weapon.createBoom(wolf, Weapon.WEAPON_MOVE_RIGHT);
						}
					}
				}
				g.drawRegion(wolf_Image, 0, 0, wolf.width, wolf.height, 0, tempx, tempy, 20);
				g.drawRegion(ballon, wolf.role.frame*wolf.role.width, 0, wolf.role.width, wolf.role.height, 0, tempx_ballon, tempy_ballon, 20);
			}else if(wolf.status == ROLE_DEATH){
				if(wolf.role.frame<4){
					wolf.role.frame = (wolf.role.frame+1);
					g.drawRegion(ballon, wolf.role.frame*wolf.role.width, 0, wolf.role.width, wolf.role.height, 0, tempx_ballon, tempy_ballon, 20);
				}else{
					tempy += wolf.speed;
					wolf.mapy = tempy;
				}
				int w = wolf_down.getWidth()/2;
				int h = wolf_down.getHeight();
				wolf.frame = (wolf.frame+1)%2;
				g.drawRegion(wolf_down, wolf.frame*w, 0, w, h, 0, tempx, tempy, 20);
			}
			
		}else if(wolf.direction == ROLE_MOVE_UP){}
		
	
	}
	
	/*没有被攻击到的狼，设置它在梯子上的位置*/
	private void setWolfLadders(Role wolf){
		if(!StateGame.HASWOLF_ONE){
			wolf.position = ON_FOUR_LADDER;
			StateGame.HASWOLF_ONE = true;
			return;
		}
		if(!StateGame.HASWOLF_TWO){
			wolf.position = ON_THREE_LADDER;
			StateGame.HASWOLF_TWO = true;
			return;
		}
		if(!StateGame.HASWOLF_THREE){
			wolf.position = ON_TWO_LADDER;
			StateGame.HASWOLF_THREE = true;
			return;
		}
		if(!StateGame.HASWOLF_FOUR){
			wolf.position = ON_ONE_LADDER;
			StateGame.HASWOLF_FOUR = true;
			return;
		}
	}
	
	public void clearObject(){
		npcs.removeAllElements();
	}
}

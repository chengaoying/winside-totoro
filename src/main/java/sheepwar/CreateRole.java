package sheepwar;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.util.RandomValue;

public class CreateRole implements Common {
	
	public final static int ROLE_MOVE_UP = 0;  		//上
	public final static int ROLE_MOVE_DOWN = 1;		//下
	public final static int ROLE_MOVE_LEFT = 2;		//左
	public final static int ROLE_MOVE_RIGHT = 3;	//右
	
	public Vector npcs = new Vector();   
	private int[] coors = {43,123,173,223};  //狼下落点的横坐标

	/* 气球属性 */
	public static int bublePara[] = {
	/* 0 图片宽，1 图片高， */
	   45, 75, 
	};
	
	/*npc（狼）属性*/
	public static int npcPara[] = {
		/*0-图片宽度，1-图片高度，2-移动速度，3-初始X坐标，4-初始Y坐标*/
		57, 71, 5, -57, 16
	};

	/*羊*/
	public Role createSheep() {
		Role role = new Role();
		role.mapx = 366; 
		role.mapy = 307;
		role.width = 49;
		role.height = 59;
		role.speed = 5;
		return role;
	}

	public void showSheep(SGraphics g, Role role) {
		Image playing_sheep = Resource.loadImage(Resource.id_playing_sheep); 
		Image sheep_eye = Resource.loadImage(Resource.id_sheep_eye);
		Image sheep_hand = Resource.loadImage(Resource.id_sheep_hand);

		g.drawRegion(sheep_hand, 0, 0, sheep_hand.getWidth(),
				sheep_hand.getHeight(), 0, 359 - 14,
				154 + 50 + (role.mapy - 154), TopLeft); 

		g.drawRegion(playing_sheep, 0, 0, playing_sheep.getWidth(),
				playing_sheep.getHeight(), 0, 361, 154 + 2 + (role.mapy - 154),
				TopLeft);

		g.drawRegion(sheep_eye, 0, 0, sheep_eye.getWidth(),
				sheep_eye.getHeight(), 0, 371 - 13,
				154 + 23 + (role.mapy - 154), TopLeft);
	}

	/*狼*/
	public void createWolf() {
		Role wolf = new Role();
		wolf.mapx = npcPara[3];
		wolf.mapy = npcPara[4];
		wolf.speed = npcPara[2];
		wolf.width = npcPara[0];
		wolf.height = npcPara[1];
		wolf.coorX = coors[RandomValue.getRandInt(0,4)];		
		wolf.direction = ROLE_MOVE_RIGHT;         
		
		npcs.addElement(wolf);
	}
	
	/*气球*/
	public void createBallon(Role wolf){
		if(wolf.role != null){
			return;
		}
		Role ballon = new Role();
		ballon.id = RandomValue.getRandInt(Resource.id_balloon_blue, Resource.id_balloon_yellowred+1);
		ballon.width = bublePara[0];
		ballon.height = bublePara[1];
		ballon.mapx = wolf.mapx+20;
		ballon.mapy = ballon.height - (wolf.mapy+65);      
		ballon.speed = wolf.speed;
		
		wolf.role = ballon;
	}

	public void showWolf(SGraphics g) {
		Image wolf_Image = Resource.loadImage(Resource.id_wolf_run); 
		Image wolf_down = Resource.loadImage(Resource.id_wolf_down);
		//Image ladder = Resource.loadImage(Resource.id_ladder);
		
		int len = npcs.size();
		Role wolf = null;
		for(int i=0;i<len;i++){
			wolf = (Role)npcs.elementAt(i);
			
			int tempx = wolf.mapx;
			int tempy = wolf.mapy;
			
			if(wolf.direction == ROLE_MOVE_RIGHT){
				tempx += wolf.speed;
				wolf.mapx = tempx;
				wolf.frame = (wolf.frame + 1) % 6; 
				g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, 0, tempx, tempy, TopLeft);
			}else if(wolf.direction == ROLE_MOVE_DOWN){
				createBallon(wolf);
				Image ballon = Resource.loadImage(wolf.role.id);
				int tempx_ballon = wolf.role.mapx;
				int tempy_ballon = wolf.role.mapy;
				if(wolf.role.frame<2){
					wolf.role.frame = (wolf.role.frame+1);
				}else{
					tempy += wolf.speed;
					wolf.mapy = tempy;
					tempy_ballon += wolf.role.speed;
					wolf.role.mapy = tempy_ballon;
				}
				g.drawRegion(wolf_Image, 0, 0, wolf.width, wolf.height, 0, tempx, tempy, TopLeft);
				g.drawRegion(ballon, wolf.role.frame*wolf.role.width, 0, wolf.role.width, wolf.role.height, 0, tempx_ballon, tempy_ballon, 20);
			}else if(wolf.direction == ROLE_MOVE_UP){
				tempy -= wolf.speed;
				wolf.mapy = tempy;
				g.drawRegion(wolf_Image, 0, 0, wolf.width, wolf.height, 0, tempx, tempy, TopLeft);
			}
			
			/*向下的临界点*/
			if(wolf.mapx == wolf.coorX){    
				wolf.direction = ROLE_MOVE_DOWN;
			}
			/*向右的临界点*/
			if(wolf.mapy == 446){
				wolf.direction = ROLE_MOVE_RIGHT;
			}
			/*向上临界点*/
			if(wolf.mapx == 423){     
				wolf.direction = ROLE_MOVE_UP;
			}
		}	
		
	}
}

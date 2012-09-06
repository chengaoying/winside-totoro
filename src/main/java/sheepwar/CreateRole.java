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
	
	/*npc属性*/
	public static int npcPara[] = {
		/*0-图片宽度，1-图片高度，2-移动速度，3-初始X坐标，4-初始Y坐标*/
		57, 71, 5, -57, 16
	};

	/* 创建玩家角色 */
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

	public void createWolf() {
		Role wolf = new Role();
		Role buble=new Role();
		wolf.mapx = npcPara[3];
		wolf.mapy = npcPara[4];
		wolf.speed = npcPara[2];
		wolf.width = npcPara[0];
		wolf.height = npcPara[1];
		wolf.coorX = coors[RandomValue.getRandInt(0,4)];		
		wolf.direction = ROLE_MOVE_RIGHT;         
		
		buble.width = bublePara[0];
		buble.height = bublePara[1];
		buble.mapx = wolf.mapx+36;
		buble.mapy = buble.height - (wolf.mapy+42);      
		buble.speed	= wolf.speed;
		
		wolf.role = buble;
		npcs.addElement(wolf);
	}

	public void showWolf(SGraphics g) {
		Image wolf_Image = Resource.loadImage(Resource.id_wolf_run); // {399, 135}
		Image wolf_down = Resource.loadImage(Resource.id_wolf_down); // 气球被击落时狼的图片
		Image blue = Resource.loadImage(Resource.id_balloon_blue);
		Image green = Resource.loadImage(Resource.id_balloon_green);
		Image multicolour = Resource.loadImage(Resource.id_balloon_multicolour);
		Image red = Resource.loadImage(Resource.id_balloon_red);
		Image yellow = Resource.loadImage(Resource.id_balloon_yellow);
		Image yellowRed = Resource.loadImage(Resource.id_balloon_yellowred);
		//Image ladder = Resource.loadImage(Resource.id_ladder);
		
		int len = npcs.size();
		Role wolf = null, buble = null;
		for(int i=0;i<len;i++){
			wolf = (Role)npcs.elementAt(i);
			int tempx = wolf.mapx;
			int tempy = wolf.mapy;
			buble = wolf.role;
			System.out.println("wolf.mapx:"+wolf.mapx);
			System.out.println("wolf.mapy:"+wolf.mapy);
			if(wolf.direction == ROLE_MOVE_RIGHT){
				tempx += wolf.speed;
				wolf.mapx = tempx;
				wolf.frame = (wolf.frame + 1) % 6; 
				g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, 0, tempx, tempy, TopLeft);
			}else if(wolf.direction == ROLE_MOVE_DOWN){
				tempy += wolf.speed;
				wolf.mapy = tempy;
				g.drawRegion(wolf_Image, 0, 0, wolf.width, wolf.height, 0, tempx, tempy, TopLeft);
			}else if(wolf.direction == ROLE_MOVE_UP){
				tempy -= wolf.speed;
				wolf.mapy = tempy;
				g.drawRegion(wolf_Image, 0, 0, wolf.width, wolf.height, 0, tempx, tempy, TopLeft);
			}
			
			if(wolf.mapx == wolf.coorX){    
				wolf.direction = ROLE_MOVE_DOWN;
			}
			
			if(wolf.mapy == 446){
				wolf.direction = ROLE_MOVE_RIGHT;
			}
			
			if(wolf.mapx == 424){     
				wolf.direction = ROLE_MOVE_UP;
			}
		}	
		
	}
}

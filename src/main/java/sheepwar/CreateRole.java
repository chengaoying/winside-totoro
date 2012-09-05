package sheepwar;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.util.RandomValue;

/** 创建npc(wolf)和玩家角色(sheep) */
public class CreateRole implements Common {
	public static Vector npcs = new Vector();    //画出的狼
	public static Vector bubles=new Vector(); //画出的气球
	private int tempx, tempy;
	
	private int[] coors = {80, 160,240,320};  //狼从开始横行到下落的点的横坐标
	
	
	/* 气球属性 */
	public static int bublePara[][] = {
	/* 0 图片宽，1 图片高，2 气球速度， */
	        { 45, 75, 3, }, // 蓝色气球
			{ 45, 75, 3, }, // 绿色气球
			{ 45, 75, 3,  }, // multicolour多色气球
			{ 45, 75, 3, }, // 红色气球
			{ 45, 75, 3, }, // 黄色气球
			{ 45, 75, 3,}, // 黄红色气球
	};

	/* 创建玩家角色 */
	public Role createSheep() {
		Role role = new Role();
		role.mapx = 366; // 可以调节玩家的开始时的位置
		role.mapy = 307;
		role.width = 49;
		role.height = 59;
		role.speed = 5;
		return role;
	}

	/** 画出角色-羊 */
	public void showSheep(SGraphics g, Role role) {
		Image playing_sheep = Resource.loadImage(Resource.id_playing_sheep); // {399,
																				// 135}
		Image sheep_eye = Resource.loadImage(Resource.id_sheep_eye);
		Image sheep_hand = Resource.loadImage(Resource.id_sheep_hand);

		g.drawRegion(sheep_hand, 0, 0, sheep_hand.getWidth(),
				sheep_hand.getHeight(), 0, 359 - 14,
				154 + 50 + (role.mapy - 154), TopLeft); // sheep_hand

		g.drawRegion(playing_sheep, 0, 0, playing_sheep.getWidth(),
				playing_sheep.getHeight(), 0, 361, 154 + 2 + (role.mapy - 154),
				TopLeft); // sheep起始坐标361,154

		g.drawRegion(sheep_eye, 0, 0, sheep_eye.getWidth(),
				sheep_eye.getHeight(), 0, 371 - 13,
				154 + 23 + (role.mapy - 154), TopLeft); // sheep_eye
	}

	/* 创建npc---狼wolf 和 气球buble */
	public void createWolf() {
		Role role = new Role();
		Role buble=new Role();
		role.mapx = 0;
		role.mapy = 16;
		role.speed = 4;
		role.coorX = coors[RandomValue.getRandInt(0,3)];		//狼在随随即的四格点内选择向下运动
		role.direction = -1;          //开始创建的对象是横行 ,0 向下，1  向上
		
		buble.mapy = role.mapy-24;      //气球起始的Y坐标
		buble.speed	= role.speed;
		npcs.addElement(role);
		bubles.addElement(buble);
	}

	/* 画出狼 */
	private int windex, wflag; // 画出动态狼的参数
	private int bindex, bflag; // 画出动态气球的参数
	public void showWolf(SGraphics g, Role role,Role buble) {
		Image wolf = Resource.loadImage(Resource.id_wolf_run); // {399, 135}
		Image wolf_down = Resource.loadImage(Resource.id_wolf_down); // 气球被击落时狼的图片
		Image blue = Resource.loadImage(Resource.id_balloon_blue);
		Image green = Resource.loadImage(Resource.id_balloon_green);
		Image multicolour = Resource.loadImage(Resource.id_balloon_multicolour);
		Image red = Resource.loadImage(Resource.id_balloon_red);
		Image yellow = Resource.loadImage(Resource.id_balloon_yellow);
		Image yellowRed = Resource.loadImage(Resource.id_balloon_yellowred);
		Image ladder = Resource.loadImage(Resource.id_ladder);
		long startTime =(long) (System.currentTimeMillis()/1000);
		
//		for(int i =	0;i<CreateRole.npcs.size()-1;i++){
//			role =	(Role)npcs.elementAt(i);
//		}
//		System.out.println("windex:"+windex);
		/* 画出动态的狼 */
		if (wflag >= 0) { // 前四个狼成功落地后向左走，从右边出，放梯子
			windex = (windex + 1) % 6; // 帧数
			wflag = 0;
		} else {
			wflag++;
		}
		
		if(role.mapx == role.coorX ){     //在特定的点狼行转向
			role.direction = 0;
			windex = 1;        
		}
		
		if(role.mapx ==424){      //狼下行至底部，横向转向上行 ,前四匹狼搭建梯子
			role.direction = 1;
		}
		
		if (role.direction == 0 && role.mapy<(516-wolf.getHeight())) {// 狼纵向
			tempy = role.mapy;
			
			buble.mapy = tempy-21;		//气球的Y坐标随着wolf的Y轴大小而变
			buble.mapx = role.mapx;
			tempx = role.mapx;
			if(bindex==2){    //当气球画到第三张的时候跟着狼一起向下运动
				role.mapy += role.speed;
			}
			//TODO 判断气球是否被击中，击中的话换wolf_down，
			g.drawRegion(wolf, windex * wolf.getWidth() / 6, 0,
					wolf.getWidth() / 6, wolf.getHeight(), 0, tempx, tempy,TopLeft); // TODO 当在下落点时，怎么让行走的图片变为静止的 或者让其消失
			
			/* 画出气球前三张的状态 */ //TODO 被武器击中 时画出最气球破裂的后两张图片效果
			if (bindex < 2) {
				if (bflag >= 0) {
					bindex = (bindex + 1) % 3; // 帧数
					bflag = 0;
				} else {
					bflag++;
				}
			} else {
				bindex = 2;
			}
			//TODO 如何进行取不同颜色的气球
			if(buble.id==1){  //蓝色气球
				g.drawRegion(blue, bindex * blue.getWidth() / 5, 0,
						blue.getWidth() / 5, blue.getHeight(), 0,
						tempx + blue.getWidth() / 5-27, tempy-28, TopLeft);
			}
			if(buble.id == 2){  //绿色球
				g.drawRegion(green, bindex * green.getWidth() / 5, 0,
						blue.getWidth() / 5, blue.getHeight(), 0,
						tempx + green.getWidth() / 5-27, tempy-28, TopLeft);
			}
			if(buble.id == 3){  //multicolour 彩色气球
				g.drawRegion(multicolour, bindex * multicolour.getWidth() / 5, 0,
						multicolour.getWidth() / 5, blue.getHeight(), 0,
						tempx + multicolour.getWidth() / 5-27, tempy-28, TopLeft);
			}
			if(buble.id == 4){ //红色气球
				g.drawRegion(red, bindex * red.getWidth() / 5, 0,
						red.getWidth() / 5, red.getHeight(), 0,
						tempx + red.getWidth() / 5-27, tempy-28, TopLeft);
			}
			if(buble.id == 5){   //黄色气球
				g.drawRegion(yellow, bindex * yellow.getWidth() / 5, 0,
						yellow.getWidth() / 5, yellow.getHeight(), 0,
						tempx + yellow.getWidth() / 5-27, tempy-28, TopLeft);
			}
			if(buble.id == 6){   //黄红色气球
				g.drawRegion(yellowRed, bindex * yellowRed.getWidth() / 5, 0,
						yellowRed.getWidth() / 5, yellowRed.getHeight(), 0,
						tempx + yellowRed.getWidth() / 5-27, tempy-28, TopLeft);
			}
			g.drawRegion(multicolour, bindex * multicolour.getWidth() / 5, 0,
					multicolour.getWidth() / 5, multicolour.getHeight(), 0,
					tempx + multicolour.getWidth() / 5-27, tempy-28, TopLeft);
		}else if(role.mapy ==516-wolf.getHeight()){ //狼纵行到底处改为横行至
				role.direction = -1;
				role.mapx += role.speed;
				tempx = role.mapx;
				tempy = role.mapy;
				g.drawRegion(wolf, windex * wolf.getWidth() / 6, 0,
						wolf.getWidth() / 6, wolf.getHeight(), 0, tempx, tempy,TopLeft);      
		}else if(role.direction == 1 && role.mapx ==424){ //狼在底部横行到mapx=424时开始上行
//			System.out.println("狼该向上走的横坐标："+role.mapx);
			tempx = role.mapx;
			role.mapy -= role.speed;
//			System.out.println("狼从下到上的纵坐标："+role.mapy);
			if(role.mapy>200-24){
				tempy = role.mapy;
			}else{
				tempy =200-24;
				windex=1;
			}
			
			g.drawRegion(ladder, 0, 0,ladder.getWidth() , ladder.getHeight(), 0,
					role.mapx, 516-ladder.getHeight(), TopLeft);       //狼 在特定位置放的梯子
			
			g.drawRegion(wolf, windex * wolf.getWidth() / 6, 0,
					wolf.getWidth() / 6, wolf.getHeight(), 0, tempx, tempy,TopLeft);
		}
		else { //起始处狼横行
			role.mapx += role.speed;
			tempx = role.mapx;
			tempy = role.mapy;
//			System.out.println("起始横坐标："+role.mapx);
			g.drawRegion(wolf, windex * wolf.getWidth() / 6, 0,
					wolf.getWidth() / 6, wolf.getHeight(), 0, tempx, tempy,TopLeft);
		}
		// }

		// if(tempy<(520-wolf.getHeight())){ //520是狼掉落的Y坐标
		// if(role.direction == 1){ //当direction == 1时，狼会向下运动
		// tempx = role.mapx;
		// tempy += role.speed;
		// role.mapy = tempy;
		// } else if(role.direction == 0) { //当direction == 0时，狼向上运动
		// tempx = role.mapx;
		// tempy -= role.speed;
		// role.mapy = tempy;
		// }else{//狼进行平移
		// tempx+=role.speed;
		//
		// }
		// tempy = role.mapy;
		// }else{
		// tempx = role.mapx+role.speed;
		// tempy=0;
		// tempy += role.speed;
		// role.mapy = tempy;
		// }
		// if(tempx==80){
		// g.drawRegion(wolf, index*wolf.getWidth()/6, 0, wolf.getWidth()/6,
		// wolf.getHeight(), 0,
		// tempy, 82, TopLeft);
		// role.direction=1;
		// //画出前三张气球模样
		//
		// //狼开始落下
		//
		// //如果气球没被击破，狼成功落地，可以根据业务需求进行
		// }else{
		// g.drawRegion(wolf, index*wolf.getWidth()/6, 0, wolf.getWidth()/6,
		// wolf.getHeight(), 0,
		// tempy+=role.speed, 82, TopLeft);
		// }

		// if(tempy-175<=80){
		// g.drawRegion(wolf, index*wolf.getWidth()/6, 0, wolf.getWidth()/6,
		// wolf.getHeight(), 0,
		// tempy-175, tempx-354, TopLeft);
		// }
		// // g.drawRegion(wolf, index*wolf.getWidth()/6, 0, wolf.getWidth()/6,
		// wolf.getHeight(), 0,
		// // tempy-175, tempx-354, TopLeft); //狼横走
		// g.drawRegion(wolf_down, 0, 0, wolf_down.getWidth(),
		// wolf_down.getHeight(), 0,
		// tempx-100, tempy, TopLeft); //狼的竖直下落(业务实际是当被击中时气球破裂，狼下坠)
	}


}

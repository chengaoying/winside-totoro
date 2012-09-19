package sheepwar;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.util.RandomValue;

public class CreateRedWolf implements Common {
	public Vector fruits = new Vector();
	/*水果属性*/
	public static int fruitPara[] = {
		/*0-图片宽度，1-图片高度，2提供的积分*/
		35,45,100,
	};

	public Role createRedWolf() {
		Role role = new Role();
		role.speed = 5;
		role.width = 29;
		role.height = 55;
		role.mapx = 300;
		role.mapy = 25;
		return role;
	}
	
	/*创建水果*/
	public void createFruits() {
		Role fruit = new Role();
		fruit.width = fruitPara[0];
		fruit.height = fruitPara[1];
		fruit.scores = fruitPara[2];
		fruit.speed = 10;
		fruit.mapy = 110;
		fruit.status = FRUIT_ON_TOP;
		fruit.id = RandomValue.getRandInt(selectFruit.length - 1);			//随即分配水果id
//		fruit.FRUIT_ON_SELECT = true;
		fruits.addElement(fruit);
	}
	
	
	private int redTx=300 ,redTy = 28;
	private int frame;
	public void showRedWolf(SGraphics g) {
		Image appleF = Resource.loadImage(Resource.id_apple);
		Image lemonF = Resource.loadImage(Resource.id_lemon);
		Image pearF = Resource.loadImage(Resource.id_orange);
		Image watermelonF = Resource.loadImage(Resource.id_watermelon);
		Image redWolfI = Resource.loadImage(Resource.id_red_wolf);
//		System.out.println("红狼横坐标："+redTx);
		 frame = (frame + 1)%2;
		if ( redTx+ redWolfI.getWidth() / 2 >=0) {
			redTx -= 5;
		} else if(redTx +redWolfI.getWidth()/2 < 0){
			redTx = 300;
		}
		/*if(!StateGame.pasueState){
			g.drawRegion(redWolfI, frame*redWolfI.getWidth()/2, 0, redWolfI.getWidth() / 2,
					redWolfI.getHeight(), 0, redTx, redTy, 20);
		}*/
		g.drawRegion(redWolfI, frame*redWolfI.getWidth()/2, 0, redWolfI.getWidth() / 2,
				redWolfI.getHeight(), 0, redTx, redTy, 20);
		if(redTx == 180 || redTx == 240){
			createFruits();
			Role fruit = null;
			for(int i = fruits.size() - 1;i>=0;i--){
				fruit = (Role)fruits.elementAt(i);
				int temy = fruit.mapy + fruit.speed;
				fruit.frame = (fruit.frame + 1) % 3;
				if(FRUIT_ON_SELECT){
					if(fruit.id == apple){
						g.drawRegion(appleF, fruit.frame*appleF.getWidth()/3, appleF.getHeight(), appleF.getWidth()/3, appleF.getHeight(), 0,
								110, temy, 20);
					}
					if(fruit.id == pear){
						g.drawImage(pearF, 0, 0, 20);
					}
					if(fruit.id == lemon){
						g.drawImage(lemonF, 0, 0, 20);
					}
					if(fruit.id == watermelon){
						g.drawImage(watermelonF, 0, 0, 20);
					}
				}else{
					System.out.println("没有选中水果就不用显示了");
				}
			}	
		}
		
	}

}


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
	
	public Role redWolf;
	
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
	public void createFruits(Role redWolf) {
		Role fruit = new Role();
		fruit.width = fruitPara[0];
		fruit.height = fruitPara[1];
		fruit.scores = fruitPara[2];
		fruit.speed = 10;
		fruit.mapy = redWolf.mapy;
		fruit.mapx = redWolf.mapx;
		fruit.status = FRUIT_ON_TOP;
		fruit.id = selectFruit[RandomValue.getRandInt(selectFruit.length - 1)];			//随即分配水果id
		fruits.addElement(fruit);
		System.out.println("fruits:"+fruits.size());
	}
	
	
	private int redTx ,redTy = 28, temx,temy;
	public void showRedWolf(SGraphics g) {
		Image appleF = Resource.loadImage(Resource.id_apple);
		Image lemonF = Resource.loadImage(Resource.id_lemon);
		Image pearF = Resource.loadImage(Resource.id_orange);
		Image watermelonF = Resource.loadImage(Resource.id_watermelon);
		Image redWolfI = Resource.loadImage(Resource.id_red_wolf);
		redTx = redWolf.mapx;
		redTy = redWolf.mapy;
		 redWolf.frame = (redWolf.frame + 1)%2;
		if ( redTx+ redWolfI.getWidth() / 2 >=0) {
			System.out.println(2222);
			redTx -= 5;
		} else {
			redTx = 300;
		}
		redWolf.mapx = redTx;
		/*if(!StateGame.pasueState){
			g.drawRegion(redWolfI, frame*redWolfI.getWidth()/2, 0, redWolfI.getWidth() / 2,
					redWolfI.getHeight(), 0, redTx, redTy, 20);
		}*/
		g.drawRegion(redWolfI, redWolf.frame*redWolfI.getWidth()/2, 0, redWolfI.getWidth() / 2,
				redWolfI.getHeight(), 0, redTx, redTy, 20);
		if(redTx == 180 || redTx == 240){
			createFruits(redWolf);
			Role fruit = null;
			for(int i = fruits.size() - 1;i>=0;i--){
				fruit = (Role)fruits.elementAt(i);
				temx = fruit.mapx;
				temy = fruit.mapy + fruit.speed;
				fruit.mapy = temy;
				fruit.frame = (fruit.frame + 1) % 3;
				System.out.println("fruit.id=="+fruit.id);
				//if(FRUIT_ON_SELECT){
					if(fruit.id == apple){
						g.drawRegion(appleF, /*fruit.frame*appleF.getWidth()/3*/0, 0, appleF.getWidth()/3, appleF.getHeight(), 0,
								temx, temy, 20);
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
				//}else{
					//System.out.println("没有选中水果就不用显示了");
				//}
			}	
		}
		
	}

}


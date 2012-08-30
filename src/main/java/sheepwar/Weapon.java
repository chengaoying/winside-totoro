package sheepwar;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


/**
 * 武器技能
 * @author Administrator
 */
public class Weapon implements Common {
	int id;                 //武器id
	int objectId;			//武器所属者	ID
	int speedX;             //X 轴移动速度
	int speedY;             //Y轴移动速度
	int direction;          //武器移动方向 2左3右
	int harm;               //造成的伤害
	int mapx;				//X轴坐标
	int mapy;				//Y轴坐标
	int width;				//武器宽度
	int height;				//武器高度
	float terminalX;		//终点横坐标
	float terminalY;		//终点纵坐标
	float flagx;			//标识x
	float flagy;			//标识y
	int random;				//随机跟踪
	boolean isSingle;		//单个属性
	
	private Vector bombs = new Vector();
	
	/**
	 * 创建普通武器 ---Shuriken
	 * @param own   被跟踪对象(如果有跟踪效果)
	 * @param objectId   发射者ID
	 * @param mapx        普通武器横坐标
	 * @param mapy        普通武器纵坐标
	 * @param direction   普通武器方向(2左3右)
	 * @param width       发射飞镖者的宽(用于定位子弹的坐标)
	 * @param height      发射飞镖者的高 (用于定位子弹的坐标
	 */
	public void createBomb(Role own) {
		Weapon w = new Weapon();
		w.direction = direction;
		w.objectId = objectId;
		w.mapx = own.mapx-40;  //飞镖发射的初始X坐标
		w.mapy = own.mapy+45;  //飞镖发射的初始Y坐标
		w.speedX = 5;
		bombs.addElement(w);
	}
	
	private int bombIndex, bombFlag;
	/**
	 * 画出普通武器
	 * @param g
	 */
	public void showBomb(Graphics g){
		Image bomb = Resource.loadImage(Resource.id_bomb);
		Weapon w = null;
		int tempx, tempy;
		int len = bombs.size()-1;
		for(int i=len; i>=0; i--){
			w = (Weapon)bombs.elementAt(i);
			tempx = w.mapx;
			tempy = w.mapy;
			tempx -= w.speedX;
			w.mapx = tempx;
			
			if(bombFlag>=0){                  
				bombIndex = (bombIndex+1)%3;
				bombFlag=0;
			}else{
				bombFlag++;
			}
			g.drawRegion(bomb, bombIndex *bomb.getWidth()/3, 0, bomb.getWidth()/3, bomb.getHeight(), 0, tempx, tempy, 0);
		}
	}
	

}

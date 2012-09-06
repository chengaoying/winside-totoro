package sheepwar;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;


/**
 * 武器技能
 * @author Administrator
 */
public class Weapon implements Common {
	int id;                 //武器id
	int objectId;			//武器所属者	ID
	int speedX;             //X轴移动速度
	int speedY;             //Y轴移动速度
	int direction;          //武器移动方向 
	int frame;				//帧数
	int mapx;				//X轴坐标
	int mapy;				//Y轴坐标
	int width;				//武器宽度
	int height;				//武器高度
	
	public final static int WEAPON_MOVE_LEFT = 0;
	public final static int WEAPON_MOVE_RIGHT = 1;
	
	public Vector bombs = new Vector();
	
	/**
	 * 创建普通武器 ---Shuriken
	 * @param own  
	 * @param direction   普通武器方向(2左3右)
	 */
	public void createBomb(Role own, int direction) {
		Weapon w = new Weapon();
		w.direction = direction;
		w.mapx = own.mapx-20;  
		w.mapy = own.mapy+45;  
		w.width = 18;
		w.height = 19;
		w.speedX = 6;
		bombs.addElement(w);
	}
	
	private int bombIndex, bombFlag;
	/**
	 * 画出普通武器
	 * @param g
	 */
	public void showBomb(SGraphics g){
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
	
	/*清除内存中的对象*/
	public void clearObjects() {
      bombs.removeAllElements();
	}
}

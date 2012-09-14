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
	boolean isUse;			//是否在使用
	
	public final static int WEAPON_MOVE_LEFT = 0;
	public final static int WEAPON_MOVE_RIGHT = 1;
	public final static int WEAPON_MOVE_DOWN = 2;
	
	public Vector bombs = new Vector();
	public Vector nets = new Vector();
	public Vector booms = new Vector();
	public Vector protects = new Vector();
	
	/*捕狼标识*/
	public boolean isUseNet;
	
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
		w.speedX = 10;
		bombs.addElement(w);
	}
	
	/**
	 * 画出普通武器
	 * @param g
	 */
	public void showBomb(SGraphics g){
		int len = bombs.size();
		Image bomb = Resource.loadImage(Resource.id_bomb);
		Weapon w = null;
		int tempx=0, tempy=0;
		for(int i=len-1; i>=0; i--){
			w = (Weapon)bombs.elementAt(i);
			if(w.direction == WEAPON_MOVE_LEFT){
				tempx = w.mapx;
				tempy = w.mapy;
				tempx -= w.speedX;
				w.mapx = tempx;
				w.frame = (w.frame+1)%3;
			}else if(w.direction == WEAPON_MOVE_DOWN){
				tempx = w.mapx;
				tempy = w.mapy;
				tempy += w.speedY;
				w.mapy = tempy;
			}
			g.drawRegion(bomb, w.frame *bomb.getWidth()/3, 0, bomb.getWidth()/3, bomb.getHeight(), 0, tempx, tempy, 0);
		}
	}
	
	/*捕狼网*/
	public void createNet(Role own, int direction){
		Weapon w = new Weapon();
		w.direction = direction;
		w.mapx = own.mapx-20;  
		w.mapy = own.mapy+45;  
		w.width = 13;
		w.height = 13;
		w.speedX = 15;
		w.isUse = false;
		nets.addElement(w);
	}
	
	public void showNet(SGraphics g){
		int len = nets.size();
		Image net = Resource.loadImage(Resource.id_net);
		Image net2 = Resource.loadImage(Resource.id_net2);
		Weapon w = null;
		for(int i=len-1; i>=0; i--){
			w = (Weapon)nets.elementAt(i);
			if(w.mapx>=180){
				w.mapx -= w.speedX;
				w.frame = (w.frame+1)%3;
				g.drawRegion(net, w.frame *net.getWidth()/3, 0, net.getWidth()/3, net.getHeight(), 0, w.mapx, w.mapy, 0);
			}else{
				w.height = net2.getHeight();
				w.width = net2.getWidth();
				if(!w.isUse){
					w.mapx = w.mapx+6-w.width/2;
					w.mapy = w.mapy+6-w.height/2;
					w.isUse = true;
				}else{
					w.mapy += w.speedX;
				}
				g.drawImage(net2, w.mapx, w.mapy, 20);
			}
		}
	}
	
	/*创建npc的普通攻击 武器*/
	public void createBoom(Role npc,int direction){
		Weapon w = new Weapon();
		w.direction = direction;
		w.mapx = (npc.mapx + npc.height)/2+npc.width;
		w.mapy = npc.mapy + npc.height/2;
		w.height = 22;
		w.width = 14;
		w.speedX = 10;		//子弹横向速度
		booms.addElement(w);
	}
	
	/*画出狼的武器*/
	public void showBoom(SGraphics g,Role player){ 
		g.setClip(0, 0, gameW, ScrH);
		Image boom = Resource.loadImage(Resource.id_boom);
		Weapon w = null;
		int tempx = 0,tempy = 0;
		for(int i = booms.size() - 1;i>=0;i --){
			w = (Weapon)booms.elementAt(i);
				w.mapx += w.speedX;
				tempy = w.mapy;
				tempx = w.mapx;
			g.drawRegion(boom, 0, 0, boom.getWidth(), boom.getHeight(), 0, tempx, tempy, 20);
		}
		g.setClip(0, 0, ScrW, ScrH);
	}
	
	/*创建防狼套装*/
	public void createProtect(Role player) {
		System.out.println("玩家横纵标------"+player.mapx);
		System.out.println("玩家纵纵标"+player.mapy);
       Weapon w = new Weapon();
       w.mapy = player.mapy + 30;
       w.mapx = player.mapx;
       w.height = 33;
       w.width = 32;
       protects.addElement(w);
	}
	
	/*画出防狼套装*/
	public void showProtect(SGraphics g,Role role){
		Image shield = Resource.loadImage(Resource.id_prop_3);
		Weapon w = null;
		int tempx = 0,tempy = 0;
		for(int i = protects.size() - 1;i >= 0;i --){
			w = (Weapon)protects.elementAt(i);
			if(StateGame.protectState){
				tempy = role.mapy+27;
				tempx = w.mapx;
				g.drawRegion(shield, 0, 0, shield.getWidth(), shield.getHeight(), 0, tempx, tempy, 20);
			}
		}
	}

	
	/*清除内存中的对象*/
	public void clearObjects() {
      bombs.removeAllElements();
      nets.removeAllElements();
      booms.removeAllElements();
      protects.removeAllElements();
	}
}

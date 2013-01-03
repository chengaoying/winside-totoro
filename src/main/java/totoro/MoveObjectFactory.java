package totoro;

import java.util.Vector;

/**
 * 生产对象的工厂
 * @author jackey
 *
 */
public class MoveObjectFactory {

	private MoveObjectFactory(){}
	private static MoveObjectFactory instance;
	public static MoveObjectFactory getInstance(){
		if(instance==null){
			instance = new MoveObjectFactory();
		}
		return instance;
	}
	
	public Vector bombs = new Vector();
	public Vector spirits = new Vector();
	
	/*玩家属性*/
	private int playerParam[][] = {
			/*0-id, 1-x坐标, 2-y坐标, 3-宽度, 4-高度, 5-生命数, 6-血量, 7-伤害, 8-等级, 9-x速度, 10-y速度*/
			{0, 20, 250, 37, 76, 3, 200, 10, 1, 10, 10},
	};
	
	/*玩家普通攻击属性*/
	private int bombParam[][] = {
			/*0-id, 1-宽度, 2-高度, 3-伤害, 4-x速度, 5-Y速度*/
			{10, 27, 21, 10, 20, 20},
	};
	
	/*精灵属性*/
	private int spiritParam[][] = {
			/*0-id, 1-宽度, 2-高度, 3-血量, 4-积分, 5-x速度, 6-y速度, 7-x坐标, 8-y坐标*/
			{100, 47, 62, 1, 100, 10, 10, 640, 200, },
	};
	
	/*创建普通精灵*/
	public void createSpirits(int level){
		
	}
	
	/*创建玩家普通攻击*/
	public void createBomb(MoveObject player){
		MoveObject object = new MoveObject();
		object.id = bombParam[0][0];
		object.width = bombParam[0][1];
		object.height = bombParam[0][2];
		object.damage = bombParam[0][3];
		object.speedX = bombParam[0][4];
		object.speedY = bombParam[0][5];
		object.grade = player.grade;
		object.mapx = player.mapx+player.width;
		object.mapy = player.mapy+player.height/2-object.height/2;
		bombs.addElement(object);
	}
	 
	/**
	 * 创建新游戏玩家
	 * @return
	 */
	public MoveObject createNewPlayer(){
		MoveObject object = new MoveObject();
		object.id = playerParam[0][0];
		object.mapx = playerParam[0][1];
		object.mapy = playerParam[0][2];
		object.width = playerParam[0][3];
		object.height = playerParam[0][4];
		object.lifeNum = playerParam[0][5];
		object.blood = playerParam[0][6];
		object.damage = playerParam[0][7];
		object.grade = playerParam[0][8];
		object.speedX = playerParam[0][9];
		object.speedY = playerParam[0][10];
		return object;
	}
}

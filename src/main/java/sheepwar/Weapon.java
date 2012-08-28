package sheepwar;


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
	float terminalY;		//终点纵坐标(对象是水雷的话,表示水雷的起始坐标)
	float flagx;			//标识x
	float flagy;			//标识y
	int random;				//随机跟踪
	boolean isSingle;		//单个属性
	
	/**
	 * 创建普通攻击---Shuriken
	 * @param own   被跟踪对象(如果有跟踪效果)
	 * @param objectId   发射者ID
	 * @param mapx        普通武器横坐标
	 * @param mapy        普通武器纵坐标
	 * @param direction   普通武器方向(2左3右)
	 * @param width       发射飞镖者的宽(用于定位子弹的坐标)
	 * @param height      发射飞镖者的高 (用于定位子弹的坐标
	 */
	public void createShuriken(Role own, int objectId, int mapx, int mapy, int direction, int width, int height) {
		Weapon shuriKen=new Weapon();
		shuriKen.direction = direction;
		shuriKen.objectId = objectId;
		
		
	}

}

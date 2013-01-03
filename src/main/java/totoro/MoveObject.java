package totoro;

/**
 * 可以移动并带属性的物体对象
 * @author jackey
 *
 */
public class MoveObject {
	
	int id;			//对象id
	int mapx;		//x轴坐标
	int mapy;		//y轴坐标
	int width;		//对象图片宽度
	int height;		//对象图片高度
	int lifeNum;	//生命条数
	int blood;		//血量
	int damage;		//造成的伤害值
	int speedX;		//x轴方向速度
	int speedY; 	//y轴方向速度
	int scores;		//积分
	int grade;		//等级
	int frame;		//对象图片帧数
}

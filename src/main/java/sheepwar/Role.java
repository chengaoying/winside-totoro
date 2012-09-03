package sheepwar;
public class Role {
	int id; 			//ID  判断羊||狼   气球id（1-5）
	int mapx; 			//在地图上的横坐标
	int mapy; 			//在地图上的纵坐标
	int direction; 		//移动方向 0 向上，1向下
	int status;  		//状态(0活动状态, 1死状态)
	int nonceLife;		//生命值
	int money; 			//金币数
	int lifeNum; 		//生命数
	int speed;			//移动速度
	int scores; 		//积分
//	int scores2;		//用于计算功勋的积分
	int skill; 			//技能，
	int coorX;			//下落点
	int height;        //角色自身高度
	int width;          //角色自身宽度
}

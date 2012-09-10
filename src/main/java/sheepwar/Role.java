package sheepwar;
public class Role {
	int id; 			//ID  判断羊||狼   气球id（1-5）
	int mapx; 			//在地图上的横坐标
	int mapy; 			//在地图上的纵坐标
	int frame;			//角色当前帧数
	int direction; 		//移动方向 0 向上，1向下
	int status;  		//状态(0活动状态, -1死状态)
	int position;		//狼在梯子上的位置( 1在第一个梯子上，2在第二个梯子上，3在第三个梯子上，4在第四个梯子上)
	int nonceLife;		//生命值
	int money; 			//金币数
	int lifeNum; 		//生命数
	int speed;			//移动速度
	int scores; 		//积分
	int coorX;			//下落点
	int height;         //角色自身高度
	int width;          //角色自身宽度
	int eatNum;			//单关击中的狼数
	
	
	Role role;		   //子对象
}

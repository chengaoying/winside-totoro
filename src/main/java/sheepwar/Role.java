package sheepwar;
public class Role {
	int id; 			//ID
	int mapx; 			//在地图上的横坐标
	int mapy; 			//在地图上的纵坐标
	int frame;			//角色当前帧数
	int direction; 		//移动方向 0 向上，1向下
	int status;  		//状态(0活动状态, -1死状态, 1暂停状态)
	int status2;		//第二状态（0在地面， 1离开地面）
	int position;		//狼在梯子上的位置( 1在第一个梯子上，2在第二个梯子上，3在第三个梯子上，4在第四个梯子上)
	int nonceLife;		//生命值
	int lifeNum; 		//生命数
	int speed;			//移动速度
	int scores; 		//积分
	int coorX;			//下落点
	int height;         //角色自身高度
	int width;          //角色自身宽度
	int eatNum;			//单关击中的狼数
	int bombNum;		//发射子弹数量
	int colorId;		//气球ID
	int attackTime;		//被击中次数
	
	Role role;		   //子对象
}

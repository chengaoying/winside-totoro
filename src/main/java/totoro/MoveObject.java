package totoro;

/**
 * 可以移动并带属性的物体对象
 * @author jackey
 *
 */
public class MoveObject {
	
	int status;				//状态1:活着，死亡， 无敌
	int status2;			//状态2：移动 ，攻击
	int id;					//对象id
	int objectId;			//所属对象id
	int mapx;				//x轴坐标
	int mapy;				//y轴坐标
	int width;				//对象图片宽度
	int height;				//对象图片高度
	int lifeNum;			//生命条数
	int blood;				//血量
	int damage;				//造成的伤害值
	int speedX;				//x轴方向速度
	int speedY; 			//y轴方向速度
	int scores;				//积分
	int grade;				//等级
	int bombGrade;			//玩家子弹等级
	int frame;				//对象图片帧数
	int picId;				//图片id
	int position;			//初始位置(0-上, 1-下, 2-右)
	int attackPermission;	//能不能攻击(0-no, 1-yes)
	int pirze;				//是否有奖品(0-no, 1-yes)
	int direction;			//移动方向(0-上, 1-下, 2-左, 3-右, 4-左上, 5-右上, 6-左下, 7-右下)
	long startTime;			//精灵帧数跳转控制
	long endTime;			//
	int timeInterval;		//精灵帧数跳转间隔
	int frameNum;			//图片总帧数
	int frameIndex;			//当前所在的帧
	int directionValue;		//方向值
	long bombSTime;			//发射子弹的时间控制
	long bombETime;
	int bombInterval;		//发射子弹的间隔
	long skill1STime;
	long skill1ETime;
	int skill1Interval;
	int skill1Damage;
	long skill2STime;
	long skill2ETime;
	int skill2Interval;
	int skill2Damage;
	long runTime1;		//boss run time1
	long runTime2;		//boss run time2
	int stopInterval;
	int skillStatue;	//1-技能1, 2-技能2
	
	int bombNum;
	int wingplaneNums;
	int wingplaneMaxNums;
	int missileGrade;  //1,2 等级
	int skillNums;		//技能数
	
	MoveObject mo;			//跟踪导弹跟踪对象
}

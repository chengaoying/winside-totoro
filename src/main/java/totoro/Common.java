package totoro;

/*
 * 公共变量类 
 */
public interface Common {
	
	public final static int STATUS_INIT = 0;			//初始
	public final static int STATUS_MAIN_MENU = 1;		//游戏主菜单 
	public final static int STATUS_GAME_PLAYING = 2;	//游戏中

	public final static int ScrW = TotoroGameEngine.ScrW;
	public final static int ScrH = TotoroGameEngine.ScrH;
	public final static int gameH = 490/*444*/;
	public final static int startP = 0/*46*/;
	public final static int endP = 490;

	public final static int spirit_id = 100;
	public final static int boss_id = 200;
	
	/*对象初始位置*/
	public static final int OBJECT_POSITION_UP = 1;			//上	
	public static final int OBJECT_POSITION_DOWN = 2;		//下
	public static final int OBJECT_POSITION_LEFT = 3;		//左
	public static final int OBJECT_POSITION_RIGHT = 4;		//右
	
	/*对象移动方向*/
	public static final int OBJECT_DIRECTION_UP = 0;			//上
	public static final int OBJECT_DIRECTION_DOWN = 1;			//下
	public static final int OBJECT_DIRECTION_LEFT = 2;			//左
	public static final int OBJECT_DIRECTION_RIGHT = 3;			//右
	public static final int OBJECT_DIRECTION_LEFT_UP = 4;			//左上
	public static final int OBJECT_DIRECTION_RIGHT_UP = 5;			//右上
	public static final int OBJECT_DIRECTION_LEFT_DOWN = 6;			//左下
	public static final int OBJECT_DIRECTION_RIGHT_DOWN = 7;		//右下
	
	/*精灵攻击状态*/
	public static final int ATTACK_PERMISSION_YES = 1;	//能攻击
	public static final int ATTACK_PERMISSION_NO = 0;	//不能攻击
	
	/*精灵死亡是否会掉落奖品*/
	public static final int SPIRITI_PRIZE_YES = 1;		//会
	public static final int SPIRITI_PRIZE_NO = 0;		//不会
	
	/*角色状态1*/
	public static final int ROLE_STATUS_ALIVE = 1;		//活着状态
	public static final int ROLE_STATUS_DEAD = -1;		//死亡转台	
	public static final int ROLE_STATUS_PROTECTED = 2;	//无敌状态
	public static final int ROLE_STATUS_PASS = 3;		//通关状态
	
	/*角色状态2*/
	public static final int ROLE_STATUS2_MOVE = 1;			//移动状态
	public static final int ROLE_STATUS2_ATTACK = 2;		//普通攻击状态
	public static final int ROLE_STATUS2_SKILL_ATTACK = 3;	//技能攻击状态
	
	/*玩家图片id*/
	public static final int player1PicId = Resource.id_own_totoro1;
	
	/*子弹图片id*/
	public static final int bomb1PicId = Resource.id_own_totoro1_bomb;

	/*精灵子弹图片*/
	public static final int spiritBomb1PicId = Resource.id_sky_spirit_bomb_1;
	public static final int bossBomb1PicId = Resource.id_sky_boss_bomb_1;
	public static final int bossBomb2PicId = Resource.id_sky_boss_bomb_2;
	
	/*精灵图片id*/
	public static final int spirits_1 = Resource.id_sky_spirits_1;
	public static final int spirits_2 = Resource.id_sky_spirits_2;
	public static final int spirits_3 = Resource.id_sky_spirits_3;
	
	/*炮台图片id*/
	public static final int battery_1 = Resource.id_sky_battery;
	
	/*boss图片id*/
	public static final int boss_1 = Resource.id_sky_boss_1;
	public static final int boss_2 = Resource.id_sky_boss_2;
	
	/*关卡等级信息*/
	public static final int levelInfo[][] = {
		/*0-关卡, 1-关卡时间(秒), 2-出怪时间间隔, 3-battery interval*/
		{1, 10, 3000, 10000},
		{2, 10, 3000, 10000},
		{3, 10, 3000, 10000},
		{4, 10, 3000, 10000},
		{5, 10, 3000, 10000},
		{6, 10, 3000, 10000},
		{7, 10, 3000, 10000},
		{8, 10, 3000, 10000},
	};
	
	/*玩家属性*/
	public int playerParam[][] = {
			/*0-id, 1-x坐标, 2-y坐标, 3-宽度, 4-高度, 5-生命数, 6-血量, 7-伤害, 8-等级, 9-x速度, 10-y速度
			 * ,11-玩家子弹等级, 12-玩家图片id*/
			{0, 20, 250, 37, 76, 3, 150, 50, 1, 10, 10, 1, player1PicId},
	};
	
	/*玩家普通攻击属性*/
	public int bombParam[][][] = {
			/*玩家等级*/
			{
				/*子弹等级*/
				/*0-id, 1-宽度, 2-高度, 3-伤害, 4-x速度, 5-Y速度, 6-子弹图片id*/
				{10, 27, 21, 10, 20, 20, bomb1PicId},
			},
	};
	
	/*boss属性*/
	public int bossParam[][] = {
			/*0-id, 1-宽度, 2-高度, 3-血量, 4-积分, 5-x速度, 6-y速度, 7-x坐标, 8-y坐标
			 * 9-图片id, 10-帧数间隔, 11-图片总帧数, 12-伤害, 13-发射子弹间隔, 14-方向, 15-初始位置*/
			{200, 186, 266, 100/*9000*/, 100, 5, 5, 0, 0, boss_1, 300, 5, 50, 1, OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT},
			{201, 356, 462, 100/*9000*/, 100, 5, 5, 0, 0, boss_2, 300, 3, 50, 1, OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT},
			{202, 356, 462, 100/*9000*/, 100, 5, 5, 0, 0, boss_2, 300, 3, 50, 1, OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT},
			{203, 356, 462, 100/*9000*/, 100, 5, 5, 0, 0, boss_2, 300, 3, 50, 1, OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT},
			{204, 356, 462, 100/*9000*/, 100, 5, 5, 0, 0, boss_2, 300, 3, 50, 1, OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT},
			{205, 356, 462, 100/*9000*/, 100, 5, 5, 0, 0, boss_2, 300, 3, 50, 1, OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT},
			{206, 356, 462, 100/*9000*/, 100, 5, 5, 0, 0, boss_2, 300, 3, 50, 1, OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT},
			{207, 356, 462, 100/*9000*/, 100, 5, 5, 0, 0, boss_2, 300, 3, 50, 1, OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT},
	};
	

	/*炮台属性*/
	public int batteryParam[][] = {
			/*0-id, 1-width, 2-height, 3-blood, 4-scores, 5-speedX, 6-speedY, 7-coorX, 8-coorY
			 * 9-position, 10-attackpermission, 11-picId, 12-frameNum, 13-damage, 14-bombInterval*/
			{300, 70, 69, 50, 30, 2, 0, ScrW, 423, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_1, 3, 10, 2},
			{300, 70, 69, 50, 30, 2, 0, ScrW, 423, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_1, 3, 10, 2},
	};
	
	/*精灵普通攻击属性*/
	public int spiritBombParam[][] = {
			/*0-精灵id, 1-id, 2-宽度, 3-高度, 4-伤害, 5-x速度, 6-Y速度, 7-子弹图片id*/
			{100, 15, 20, 19, 25, 20, 20, spiritBomb1PicId},
			{200, 16, 85, 50, 25, 20, 20, bossBomb1PicId},
			{201, 17, 85, 50, 25, 20, 20, bossBomb2PicId},
			{202, 18, 85, 50, 25, 20, 20, bossBomb2PicId},
			{203, 19, 85, 50, 25, 20, 20, bossBomb2PicId},
			{204, 20, 85, 50, 25, 20, 20, bossBomb2PicId},
			{205, 21, 85, 50, 25, 20, 20, bossBomb2PicId},
			{206, 22, 85, 50, 25, 20, 20, bossBomb2PicId},
			{300, 15, 20, 19, 25, 20, 10, spiritBomb1PicId},
	};
	
	/*精灵属性*/
	public int spiritParam[][] = {
	/*0-id, 1-宽度, 2-高度, 3-血量, 4-积分, 5-x速度, 6-y速度, 7-x坐标, 8-y坐标, 9-初始位置, 
	 * 10-是否会攻击, 11-图片id, 12-是否会掉落奖品, 13-帧数间隔, 14-图片总帧数 , 15-精灵伤害
	 * 16-发射子弹间隔*/
		{100, 47, 62, 1, 100, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_1, SPIRITI_PRIZE_NO, 500, 5, 10, 5},
		{101, 84, 76, 1, 100, 8, 8, 640, 200, 0, ATTACK_PERMISSION_NO, spirits_2, SPIRITI_PRIZE_NO, 500, 3, 10, 0},
		{102, 80, 62, 100, 100, 6, 6, 640, 200, 0, ATTACK_PERMISSION_NO, spirits_3, SPIRITI_PRIZE_NO, 500, 3, 10, 3},
	};
	
	/*精灵批数信息*/
	public int batchInfo[][][] = {
			/*0-id, 1-数量, 2-初始位置, 3-移动方向*/
		/*第一关怪物列表*/	
		{
			{100, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{101, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{102, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第二关怪物列表*/	
		{
			{101, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{101, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{102, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第三关怪物列表*/	
		{
			{101, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{101, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{102, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第四关怪物列表*/	
		{
			{101, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{101, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{102, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第五关怪物列表*/	
		{
			{101, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{101, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{102, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第六关怪物列表*/	
		{
			{101, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{101, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{102, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第七关怪物列表*/	
		{
			{101, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{101, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{102, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第八关怪物列表*/	
		{
			{101, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{101, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{102, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
	};
	
}

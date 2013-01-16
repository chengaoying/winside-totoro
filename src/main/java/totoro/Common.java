package totoro;

/*
 * 公共变量类 
 */
public interface Common {
	
	public final static int STATUS_INIT = 0;			//初始
	public final static int STATUS_MAIN_MENU = 1;		//游戏主菜单 
	public final static int STATUS_SELECT_TOTORO = 2;	//选择龙猫
	public final static int STATUS_GAME_PLAYING = 3;	//游戏中
	
	public final static int GAME_PLAY = 2;
	public final static int GAME_FAIL = 3;
	public final static int GAME_SUCCESS = 4;
	public final static int GAME_PAUSE = 5;

	public final static int ScrW = TotoroGameEngine.ScrW;
	public final static int ScrH = TotoroGameEngine.ScrH;
	public final static int gameH = 457;
	public final static int startP = 73;
	public final static int endP = 530;

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
	public static final int ROLE_STATUS2_SKILL_ATTACK = 3;	//技能一状态
	public static final int ROLE_STATUS2_SKILL2_ATTACK = 4;	//技能二状态
	
	/*龙猫等级*/
	public static final int TOTORO_GRADE_ONE = 1;
	public static final int TOTORO_GRADE_TWO = 2;
	public static final int TOTORO_GRADE_THREE = 3;
	public static final int TOTORO_GRADE_FOUR = 4;
	
	/*玩家子弹等级*/
	public static final int TOTORO_BOMB_GRADE_ONE = 1;
	public static final int TOTORO_BOMB_GRADE_TWO = 2;
	public static final int TOTORO_BOMB_GRADE_THREE = 3;
	public static final int TOTORO_BOMB_GRADE_FOUR = 4;
	
	/*玩家图片id*/
	public static final int pinkTotoroPicId = Resource.id_pink_totoro;
	public static final int yellowTotoroPicId = Resource.id_yellow_totoro;
	public static final int blueTotoroPicId = Resource.id_blue_totoro;
	public static final int blackTotoroPicId = Resource.id_black_totoro;
	
	/*子弹图片id*/
	public static final int pinkBombPicId = Resource.id_pink_totoro_bomb;
	public static final int yellowBomb1PicId = Resource.id_yellow_totoro_bomb1;
	public static final int yellowBomb2PicId = Resource.id_yellow_totoro_bomb2;
	public static final int yellowBomb3PicId = Resource.id_yellow_totoro_bomb3;
	public static final int blueBomb1PicId = Resource.id_blue_totoro_bomb1;
	public static final int blueBomb2PicId = Resource.id_blue_totoro_bomb2;
	public static final int blueBomb3PicId = Resource.id_blue_totoro_bomb3;
	public static final int blueBomb4PicId = Resource.id_blue_totoro_bomb4;
	public static final int blackBomb1PicId = Resource.id_black_totoro_bomb1;
	public static final int blackBomb2PicId = Resource.id_black_totoro_bomb2;
	public static final int blackBomb3PicId = Resource.id_black_totoro_bomb3;

	/*精灵子弹图片*/
	public static final int spiritBomb1PicId = Resource.id_sky_spirit_bomb_1;
	public static final int spiritBomb2PicId = Resource.id_burrow_spirit_bomb_2;
	public static final int spiritBomb3PicId = Resource.id_ice_spirit_bomb_1;
	public static final int spiritBomb4PicId = Resource.id_ice_spirit_bomb_2;
	public static final int spiritBomb5PicId = Resource.id_lava_spirit_bomb;
	public static final int spiritBomb6PicId = Resource.id_ice_boss_1_fire_attack;
	
	public static final int boss1SkillPicId = Resource.id_sky_boss_1_skill;
	public static final int boss2SkillPicId = Resource.id_sky_boss_2_skill;
	public static final int boss3SkillPicId = Resource.id_burrow_boss_1_skill;
	public static final int boss4SkillPicId = Resource.id_burrow_boss_2_skill;
	public static final int boss5SkillPicId = Resource.id_ice_boss_1_fire_attack;
	public static final int boss6SkillPicId_1 = Resource.id_ice_boss_2_skill_1;
	public static final int boss6SkillPicId_2 = Resource.id_ice_boss_2_skill_2;
	public static final int boss6SkillPicId_3 = Resource.id_ice_boss_2_skill_3;
	public static final int boss6SkillPicId_4 = Resource.id_ice_boss_2_skill_4;
	
	public static final int batteryBomb2PicId = Resource.id_ice_battery_bomb;
	public static final int batteryBomb3PicId = Resource.id_lava_battery_bomb;
	
	/*精灵图片id*/
	public static final int spirits_1 = Resource.id_sky_spirits_1;
	public static final int spirits_2 = Resource.id_sky_spirits_2;
	public static final int spirits_3 = Resource.id_sky_spirits_3;
	
	public static final int spirits_4 = Resource.id_burrow_spirit_1;
	public static final int spirits_5 = Resource.id_burrow_spirit_2;
	public static final int spirits_6 = Resource.id_burrow_spirit_3;
	public static final int spirits_7 = Resource.id_burrow_spirit_4;
	
	public static final int spirits_8 = Resource.id_ice_spirit_1;
	public static final int spirits_9 = Resource.id_ice_spirit_2;
	
	public static final int spirits_10 = Resource.id_lava_spirit_1;
	public static final int spirits_11 = Resource.id_lava_spirit_2;
	
	public static final int spirits_12 = Resource.id_ice_boss_1_fire_object;
	
	/*炮台图片id*/
	public static final int battery_1 = Resource.id_sky_battery;
	public static final int battery_2 = Resource.id_ice_battery;
	public static final int battery_3 = Resource.id_lava_battery;
	
	/*boss图片id*/
	public static final int boss_1 = Resource.id_sky_boss_1;
	public static final int boss_2 = Resource.id_sky_boss_2;
	public static final int boss_3 = Resource.id_burrow_boss_1;
	public static final int boss_4 = Resource.id_burrow_boss_2;
	public static final int boss_5 = Resource.id_ice_boss_1;
	public static final int boss_6 = Resource.id_ice_boss_2;
	public static final int boss_7 = Resource.id_lava_boss_1;
	public static final int boss_8 = Resource.id_lava_boss_2;
	
	/*关卡等级信息*/
	public static final int levelInfo[][] = {
		/*0-关卡, 1-关卡时间(秒), 2-出怪时间间隔, 3-battery interval*/
		{1, 10, 5000, 10000},
		{2, 10, 5000, 10000},
		{3, 10, 4000, 10000},
		{4, 10, 4000, 10000},
		{5, 10, 4000, 10000},
		{6, 10, 3000, 10000},
		{7, 10, 3000, 60000},
		{8, 10, 3000, 60000},
	};
	
	/*玩家属性*/
	public int playerParam[][] = {
			/*0-id, 1-x坐标, 2-y坐标, 3-宽度, 4-高度, 5-生命数, 6-血量, 7-伤害, 8-等级, 9-x速度, 10-y速度
			 * ,11-玩家子弹等级, 12-玩家图片id, 13-体力, 14-威力, 15-效果*/
			{0, 20, 250, 59, 112, 3, 100, 15, TOTORO_GRADE_ONE, 10, 8, TOTORO_BOMB_GRADE_ONE, yellowTotoroPicId, 4, 4, 7},
			{1, 20, 250, 37, 76, 3, 150, 10, TOTORO_GRADE_TWO, 8, 10, TOTORO_BOMB_GRADE_ONE, pinkTotoroPicId, 6, 3, 7},
			{2, 20, 250, 64, 108, 3, 200, 20, TOTORO_GRADE_THREE, 12, 12, TOTORO_BOMB_GRADE_ONE, blueTotoroPicId, 8, 6, 6},
			{3, 20, 250, 64, 101, 3, 150, 30, TOTORO_GRADE_FOUR, 15, 15, TOTORO_BOMB_GRADE_ONE, blackTotoroPicId, 6, 8, 7},
	};
	
	/*玩家普通攻击属性, 	数组中的数值为0表示不使用该值*/
	public int bombParam[][][] = {
			/*玩家等级*/
			{
				/*子弹等级*/
				/*0-id, 1-宽度, 2-高度, 3-伤害, 4-x速度, 5-Y速度, 6-子弹图片id*/
				{14, 32, 18, 20, 30, 6, yellowBomb1PicId},
				{15, 46, 25, 30, 30, 6, yellowBomb2PicId},
				{16, 100, 50, 50, 30, 6, yellowBomb3PicId},
			},
			{
				/*子弹等级*/
				/*0-id, 1-宽度, 2-高度, 3-伤害, 4-x速度, 5-Y速度, 6-子弹图片id*/
				{10, 27, 21, 10, 20, 6, pinkBombPicId},
				{11, 27, 21, 20, 20, 6, pinkBombPicId},
				{12, 27, 21, 30, 20, 6, pinkBombPicId},
				{13, 27, 21, 40, 20, 6, pinkBombPicId},
			},
			{
				/*子弹等级*/
				/*0-id, 1-宽度, 2-高度, 3-伤害, 4-x速度, 5-Y速度, 6-子弹图片id*/
				{17, 34, 14, 20, 30, 6, blueBomb1PicId},
				{18, 67, 14, 30, 30, 6, blueBomb2PicId},
				{19, 93, 33, 50, 30, 6, blueBomb3PicId},
				{20, 114, 51, 50, 30, 6, blueBomb4PicId},
			},
			{
				/*子弹等级*/
				/*0-id, 1-宽度, 2-高度, 3-伤害, 4-x速度, 5-Y速度, 6-子弹图片id*/
				{21, 22, 23, 20, 30, 6, blackBomb1PicId},
				{22, 86, 29, 30, 30, 6, blackBomb2PicId},
				{23, 65, 65, 50, 30, 6, blackBomb3PicId},
			},
	};
	
	/*boss属性*/
	public int bossParam[][] = {
			/*0-id, 1-宽度, 2-高度, 3-血量, 4-积分, 5-x速度, 6-y速度, 7-x坐标, 8-y坐标
			 * 9-图片id, 10-帧数间隔, 11-图片总帧数, 12-伤害, 13-技能一时间间隔, 14-技能二时间间隔, 15-方向, 16-初始位置,17-技能1伤害, 18-技能2伤害*/
			{200, 186, 266, 9000, 100, 5, 5, 0, 0, boss_1, 300, 5, 50, 10, 3, OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{201, 178, 231, 9000, 100, 5, 5, 0, 0, boss_2, 300, 3, 50, 10, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{202, 192, 204, 9000, 100, 5, 5, 0, 0, boss_3, 300, 1, 50, 10, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{203, 196, 213, 9000, 100, 5, 5, 0, 0, boss_4, 300, 5, 50, 10, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{204, 102, 160, 9000, 100, 5, 5, 0, 0, boss_5, 300, 3, 50, 10, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{205, 103, 148, 9000, 100, 5, 5, 0, 0, boss_6, 300, 3, 50, 10, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{206, 275, 353, 9000, 100, 5, 5, 0, 0, boss_7, 300, 5, 50, 10, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{207, 260, 299, 9000, 100, 5, 5, 0, 0, boss_8, 300, 5, 50, 10, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
	};
	
	/*boss技能属性*/
	public int bossSkillParam[][] = {
			/*0-bossId, 1-picW, 2-picH, 3-damage,4-mapx, 5-mapy, 6-picId, 7-speedX, 8-speedY, 9-frameNum, 10-skillNum*/
			{200, 110, 130, 10, 10, 0, boss1SkillPicId, 15, 15, 3, 1},
			{201, 85, 50, 10, 10, 0, boss2SkillPicId, 25, 25, 1, 1},
			{202, 76, 150, 10, 10, 0, boss3SkillPicId, 80, 80, 1, 4},
			{203, 135, 145, 10, 10, 0, boss4SkillPicId, 80, 25, 4, 4},
			{204, 50, 23, 10, 10, 0, boss5SkillPicId, 25, 25, 3, 3},
			{205, 0, 0, 10, 10, 0, 0, 80, 25, 1, 4},
	};
	
	/*第6关boss技能一图片属性*/
	public int bossSkillPic[][] = {
			/*0-bossId, 1-picW, 2-picH, 3-picId*/
			{205, 68, 131, boss6SkillPicId_4},
			{205, 48, 94, boss6SkillPicId_3},
			{205, 34, 66, boss6SkillPicId_2},
			{205, 23, 43, boss6SkillPicId_1},
	};

	/*炮台属性*/
	public int batteryParam[][] = {
			/*0-id, 1-width, 2-height, 3-blood, 4-scores, 5-speedX, 6-speedY, 7-coorX, 8-coorY
			 * 9-position, 10-attackpermission, 11-picId, 12-frameNum, 13-damage, 14-bombInterval, 15-picInterval, 16-prize*/
			
			{300, 70, 69, 50, 30, 3, 0, ScrW, 415, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_1, 3, 10, 2, 500,SPIRITI_PRIZE_NO},
			{300, 70, 69, 50, 30, 3, 0, ScrW, 415, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_1, 3, 10, 2, 500,SPIRITI_PRIZE_NO},
			{301, 131, 146, 50, 30, 3, 0, ScrW, 355, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_2, 4, 10, 2, 500,SPIRITI_PRIZE_YES},
			{301, 131, 146, 50, 30, 3, 0, ScrW, 355, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_2, 4, 10, 2, 500,SPIRITI_PRIZE_YES},
			{301, 131, 146, 50, 30, 3, 0, ScrW, 355, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_2, 4, 10, 2, 500,SPIRITI_PRIZE_YES},
			{301, 131, 146, 50, 30, 3, 0, ScrW, 355, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_2, 4, 10, 2, 500,SPIRITI_PRIZE_YES},
			{302, 92, 97, 50, 30, 3, 0, ScrW, 378, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_3, 1, 10, 2, 500,SPIRITI_PRIZE_YES},
			{302, 92, 97, 50, 30, 3, 0, ScrW, 378, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_3, 1, 10, 2, 500,SPIRITI_PRIZE_YES},
	};
	
	/*精灵普通攻击属性*/
	public int spiritBombParam[][] = {
			/*0-精灵id, 1-id, 2-宽度, 3-高度, 4-伤害, 5-x速度, 6-Y速度, 7-子弹图片id, */
			{100, 15, 20, 19, 25, 20, 20, spiritBomb1PicId},
			{104, 16, 32, 36, 25, 20, 20, spiritBomb2PicId},
			{107, 17, 19, 20, 25, 20, 20, spiritBomb3PicId},
			{108, 18, 20, 20, 25, 20, 20, spiritBomb4PicId},
			{109, 19, 32, 20, 25, 20, 20, spiritBomb5PicId},
			{110, 14, 32, 20, 25, 20, 20, spiritBomb5PicId},
			{111, 13, 50, 23, 25, 20, 20, spiritBomb6PicId},
			/*{200, 20, 85, 50, 25, 20, 20, bossBomb1PicId},
			{201, 21, 85, 50, 25, 20, 20, bossBomb2PicId},
			{202, 22, 85, 50, 25, 20, 20, bossBomb2PicId},
			{203, 23, 85, 50, 25, 20, 20, bossBomb2PicId},
			{204, 24, 85, 50, 25, 20, 20, bossBomb2PicId},
			{205, 25, 85, 50, 25, 20, 20, bossBomb2PicId},
			{206, 27, 32, 20, 25, 20, 20, spiritBomb5PicId},
			{207, 28, 32, 20, 25, 20, 20, spiritBomb5PicId},*/
			{300, 30, 20, 19, 25, 20, 10, spiritBomb1PicId},
			{301, 31, 49, 52, 25, 20, 10, batteryBomb2PicId},
			{302, 32, 31, 31, 25, 20, 10, batteryBomb3PicId},
	};
	
	/*精灵属性*/
	public int spiritParam[][] = {
	/*0-id, 1-宽度, 2-高度, 3-血量, 4-积分, 5-x速度, 6-y速度, 7-x坐标, 8-y坐标, 9-初始位置, 
	 * 10-是否会攻击, 11-图片id, 12-是否会掉落奖品, 13-帧数间隔, 14-图片总帧数 , 15-精灵伤害
	 * 16-发射子弹间隔*/
		{100, 47, 62, 1, 15, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_1, SPIRITI_PRIZE_YES, 500, 5, 10, 5},
		{101, 84, 76, 1, 10, 8, 8, 640, 200, 0, ATTACK_PERMISSION_NO, spirits_2, SPIRITI_PRIZE_YES, 500, 3, 10, 0},
		{102, 80, 62, 100, 10, 6, 6, 640, 200, 0, ATTACK_PERMISSION_NO, spirits_3, SPIRITI_PRIZE_YES, 500, 3, 10, 3},
		
		{103, 50, 69, 20, 10, 6, 6, 640, 200, 0, ATTACK_PERMISSION_NO, spirits_4, SPIRITI_PRIZE_NO, 500, 3, 10, 3},
		{104, 63, 89, 20, 20, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_5, SPIRITI_PRIZE_YES, 500, 3, 10, 3},
		{105, 93, 114, 20, 10, 6, 6, 640, 200, 0, ATTACK_PERMISSION_NO, spirits_6, SPIRITI_PRIZE_YES, 500, 6, 10, 3},
		{106, 79, 104, 20, 10, 6, 6, 640, 200, 0, ATTACK_PERMISSION_NO, spirits_7, SPIRITI_PRIZE_YES, 500, 2, 10, 3},
		
		{107, 60, 75, 20, 20, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_8, SPIRITI_PRIZE_YES, 500, 3, 10, 3},
		{108, 75, 66, 20, 20, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_9, SPIRITI_PRIZE_YES, 500, 3, 10, 3},
		
		{109, 105, 79, 20, 25, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_10, SPIRITI_PRIZE_YES, 500, 5, 10, 3},
		{110, 45, 97, 20, 25, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_11, SPIRITI_PRIZE_YES, 500, 4, 10, 3},
		
		//幽灵boss出的小怪
		{111, 22, 32, 100, 25, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_12, SPIRITI_PRIZE_NO, 500, 3, 5, 3},
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
			{102, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			//{102, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{102, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第二关怪物列表*/	
		{
			{100, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{100, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{101, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{102, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			//{102, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{102, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第三关怪物列表*/	
		{
			{103, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{103, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{103, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{104, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{104, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{104, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{105, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{105, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{105, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{106, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			//{105, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第四关怪物列表*/	
		{
			{103, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{103, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{103, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{104, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{104, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{104, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{105, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{105, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{105, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{106, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			//{105, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第五关怪物列表*/	
		{
			{107, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{107, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{107, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{107, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{108, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{108, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			//{100, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			{107, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第六关怪物列表*/	
		{
			{107, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{107, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{107, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{107, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{108, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{108, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			//{100, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			{108, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第七关怪物列表*/	
		{
			{109, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{109, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{110, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{110, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{110, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{110, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{110, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			{109, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第八关怪物列表*/	
		{
			{109, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{109, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 1, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{110, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{110, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{110, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{110, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{110, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			{110, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
	};
	
}

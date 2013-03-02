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

	public final static TotoroGameEngine engine = TotoroGameEngine.instance;
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
	
	/*僚机图片*/
	public static final int pinkWingplane = Resource.id_pink_totoro_wingplane;
	public static final int yellowWingplane = Resource.id_yellow_totoro_wingplane;
	public static final int blueWingplane = Resource.id_blue_totoro_wingplane;
	public static final int blackWingplane = Resource.id_black_totoro_wingplane;
	
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

	/*propPic*/
	public static final int laserPic = Resource.id_prop_laser;
	public static final int missilePic = Resource.id_prop_missile;
	
	public static final int yellowVentose = Resource.id_yellow_totoro_ventose;
	public static final int pinkVentose = Resource.id_pink_totoro_ventose;
	
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
	public static final int boss7SkillPicId_1 = Resource.id_lava_boss_1_bomb1;
	public static final int boss7SkillPicId_2 = Resource.id_lava_boss_1_bomb2;
	public static final int boss7SkillPicId_3 = Resource.id_lava_boss_1_bomb3;
	public static final int boss7SkillPicId_4 = Resource.id_lava_boss_1_bomb4;
	public static final int boss7SkillPicId_5 = Resource.id_lava_boss_1_bomb5;
	public static final int boss7SkillPicId_6 = Resource.id_lava_boss_1_bomb6;
	public static final int boss7SkillPicId_7 = Resource.id_lava_boss_1_bomb7;
	public static final int boss7SkillPicId_8 = Resource.id_lava_boss_1_bomb8;
	public static final int boss8Skill2_1_PicId = Resource.id_lava_boss_2_skill_1;
	public static final int boss8Skill2_2_PicId = Resource.id_lava_boss_2_skill_2;
	public static final int boss8Skill2_3_PicId = Resource.id_lava_boss_2_skill_3;
	
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
	
	/*propPicId*/
	public static final int prop_blood_icon = Resource.id_prop_blood_icon;
	public static final int prop_laser_icon = Resource.id_prop_laser_icon;
	public static final int prop_missile_icon = Resource.id_prop_missile_icon;
	public static final int prop_upgrade_icon = Resource.id_prop_upgrade_icon;
	public static final int prop_ventose_icon = Resource.id_prop_ventose_icon;
	public static final int prop_wingplane_icon = Resource.id_prop_wingplane_icon;
	
	public static final int id_blood = 1;		//血瓶
	public static final int id_laser = 2;		//激光
	public static final int id_missile = 3;		//导弹
	public static final int id_upgrade = 4;		//武器升级
	public static final int id_ventose = 5;		//保险
	public static final int id_wingplane = 6;	//僚机
	
	/*关卡等级信息*/
	public static final long levelInfo[][] = {
		/*0-关卡, 1-关卡时间(秒), 2-出怪时间间隔, 3-battery interval*/
		{1, 120, 5000, 10000},
		{2, 120, 5000, 10000},
		{3, 120, 5000, 10000},
		{4, 120, 5000, 10000},
		{5, 120, 5000, 10000},
		{6, 120, 5000, 10000},
		{7, 120, 5000, 60000},
		{8, 120, 5000, 60000},
	};
	
	/*玩家属性*/
	public int playerParam[][] = {
			/*0-id, 1-x坐标, 2-y坐标, 3-宽度, 4-高度, 5-生命数, 6-血量, 7-伤害, 8-等级, 9-x速度, 10-y速度
			 * ,11-玩家子弹等级, 12-玩家图片id, 13-体力, 14-威力, 15-效果*/
			{0, 20, 250, 48, 100, 3, 100, 15, TOTORO_GRADE_ONE, 12, 10, TOTORO_BOMB_GRADE_ONE, yellowTotoroPicId, 6, 4, 6},
			{1, 20, 250, 35, 53, 3, 100, 10, TOTORO_GRADE_TWO, 10, 12, TOTORO_BOMB_GRADE_ONE, pinkTotoroPicId, 4, 3, 6},
			{2, 20, 250, 63, 97, 3, 200, 5, TOTORO_GRADE_THREE, 15, 15, TOTORO_BOMB_GRADE_ONE, blueTotoroPicId, 8, 6, 7},
			{3, 20, 250, 53, 97, 3, 150, 7, TOTORO_GRADE_FOUR, 15, 15, TOTORO_BOMB_GRADE_ONE, blackTotoroPicId, 6, 8, 8},
	};
	
	/*僚机属性*/
	public int wingplaneParam[][] = {
			/*0-id, 1-picId, 2-w, 3-h, 4-blood,5-damage*/
			{0,yellowWingplane, 23,47,50,15},
			{1,pinkWingplane, 16,25,75,10},
			{2,blueWingplane, 30,45,100,15},
			{3,blackWingplane, 24,45,75,15},
	};
	
	public int wingplaneBombParam[][] = {
			/*0-id, 1-w, 2-h, 3-damage, 4-picId*/
			{0, 32, 18, 30, yellowBomb1PicId, 15},
			{1, 27, 21, 30, pinkBombPicId, 10},
			{2, 34, 14, 30, blueBomb1PicId,20},
			{3, 22, 23, 30, blackBomb1PicId,20},
	};
	
	/*玩家普通攻击属性, 	数组中的数值为0表示不使用该值*/
	public int bombParam[][][] = {
			/*玩家等级*/
			{
				/*子弹等级*/
				/*0-id, 1-宽度, 2-高度, 3-伤害, 4-x速度, 5-Y速度, 6-子弹图片id*/
				{14, 32, 18, 20, 30, 6, yellowBomb1PicId},
				{15, 46, 25, 25, 30, 6, yellowBomb2PicId},
				{16, 100, 50, 30, 30, 6, yellowBomb3PicId},
			},
			{
				/*子弹等级*/
				/*0-id, 1-宽度, 2-高度, 3-伤害, 4-x速度, 5-Y速度, 6-子弹图片id*/
				{10, 27, 21, 20, 20, 6, pinkBombPicId},
				{11, 27, 21, 20, 20, 6, pinkBombPicId},
				{12, 27, 21, 20, 20, 6, pinkBombPicId},
				{13, 27, 21, 20, 20, 6, pinkBombPicId},
			},
			{
				/*子弹等级*/
				/*0-id, 1-宽度, 2-高度, 3-伤害, 4-x速度, 5-Y速度, 6-子弹图片id*/
				{17, 34, 14, 20, 20, 6, blueBomb1PicId},
				{18, 67, 14, 12, 20, 6, blueBomb2PicId},
				{19, 93, 33, 8, 20, 6, blueBomb3PicId},
				{20, 114, 51, 8, 20, 6, blueBomb4PicId},
			},
			{
				/*子弹等级*/
				/*0-id, 1-宽度, 2-高度, 3-伤害, 4-x速度, 5-Y速度, 6-子弹图片id*/
				{21, 22, 23, 20, 30, 6, blackBomb1PicId},
				{22, 86, 29, 12, 20, 6, blackBomb2PicId},
				{23, 65, 65, 30, 30, 6, blackBomb3PicId},
			},
	};
	
	public int playerSkillParam[][] = {
			/*0-id, 1-w,2-h,3-damage,4-speedx,5-speedy, 6-frameNum,7-picId*/
			{0, 600, 11, 5, 5, 5, 4, laserPic},
			{1, 42, 21, 70, 20, 20, 1, missilePic},
			
			{2, 164, 159, 40, 60, 60, 8, yellowVentose},
			{3, 164, 159, 40, 60, 60, 8, yellowVentose},
			{4, 180, 454, 70, 60, 60, 5, pinkVentose},
			{5, 180, 454, 70, 60, 60, 5, pinkVentose},
	};
	
	/*道具属性*/
	public int propsParam[][] = {
			/*0-Id, 1-propPicId, 2-picW, 3-picH, 4-speedX, 5-speedY, 6-*/
			{id_blood, prop_blood_icon, 42,41,5,5},
			{id_laser, prop_laser_icon, 44,44,5,5},
			{id_missile, prop_missile_icon, 44,44,5,5},
			{id_upgrade, prop_upgrade_icon, 49,57,5,5},
			{id_ventose, prop_ventose_icon, 40,40,5,5},
			{id_wingplane, prop_wingplane_icon, 47,46,5,5},
	};
	
	/*每个关卡所掉落的奖品*/
	public int levelProps[][] = {
			{id_blood,id_blood,id_laser, id_upgrade,id_wingplane},
			{id_blood,id_missile, id_upgrade,id_wingplane,id_wingplane},
			{id_blood,id_missile, id_upgrade,id_wingplane,id_wingplane},
			{id_blood,id_laser,id_missile, id_upgrade,/*id_ventose,*/id_wingplane,id_wingplane},
			{id_blood,id_missile, id_upgrade,id_wingplane,id_wingplane},
			{id_blood,id_laser,id_missile, id_upgrade,id_wingplane,id_wingplane},
			{id_blood,id_missile, id_upgrade,id_wingplane,id_wingplane},
			{id_blood,id_laser,id_missile, id_upgrade,id_wingplane,id_wingplane},
	};
	
	/*boss属性*/
	public int bossParam[][] = {  
			/*0-id, 1-宽度, 2-高度, 3-血量, 4-积分, 5-x速度, 6-y速度, 7-x坐标, 8-y坐标
			 * 9-图片id, 10-帧数间隔, 11-图片总帧数, 12-伤害, 13-技能一时间间隔, 14-技能二时间间隔, 15-方向, 16-初始位置,17-技能1伤害, 18-技能2伤害*/
			{200, 186, 266, 12000, 100, 10, 10, 0, 0, boss_1, 300, 5, 50, 7, 3, OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{201, 162, 210, 12000, 100, 5, 5, 0, 0, boss_2, 300, 3, 50, 7, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,50},
			{202, 192, 204, 24000, 100, 5, 5, 0, 0, boss_3, 300, 1, 50, 7, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{203, 196, 213, 24000, 100, 5, 5, 0, 0, boss_4, 300, 5, 50, 7, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{204, 102, 160, 36000, 100, 5, 5, 0, 0, boss_5, 300, 3, 50, 3, 7,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{205, 103, 148, 36000, 100, 5, 5, 0, 0, boss_6, 300, 3, 50, 7, 3,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{206, 275, 353, 48000, 100, 5, 5, 0, 0, boss_7, 300, 5, 50, 3, 7,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
			{207, 260, 299, 48000, 100, 5, 5, 0, 0, boss_8, 150, 5, 50, 3, 7,OBJECT_DIRECTION_LEFT, OBJECT_POSITION_RIGHT,50,30},
	};
	
	/*boss技能属性*/
	public int bossSkillParam[][] = {
			/*0-bossId, 1-picW, 2-picH, 3-damage,4-mapx, 5-mapy, 6-picId, 7-speedX, 8-speedY, 9-frameNum, 10-skillNum*/
			{200, 110, 130, 10, 10, 0, boss1SkillPicId, 15, 15, 3, 1},
			{201, 85, 50, 10, 10, 0, boss2SkillPicId, 25, 25, 1, 1},
			{202, 76, 150, 30, 10, 0, boss3SkillPicId, 80, 80, 1, 4},
			{203, 135, 145, 10, 10, 0, boss4SkillPicId, 80, 25, 4, 4},
			{204, 50, 23, 10, 10, 0, boss5SkillPicId, 25, 25, 3, 3},
			{205, 49, 52, 30, 10, 0, batteryBomb2PicId, 30, 80, 1, 4},
			{206, 24, 22, 10, 10, 0, boss7SkillPicId_1, 15, 15, 1, 8},
			{207, 38, 37, 30, 10, 0, boss7SkillPicId_4, 60, 60, 1, 12},
			
			{0, 50, 140, 100, 10, 0, boss8Skill2_1_PicId, 60, 60, 3, 1},
	};
	
	/*第6,7关boss技能一图片属性*/
	public int bossSkillPic[][][] = {
			/*第6关*/
			{
				/*0-bossId, 1-picW, 2-picH, 3-picId, 4-speedX, 5-speedY, 6-frameNum, 7-damage*/
				{205, 68, 131, boss6SkillPicId_4, 80, 25, 1, 10},
				{205, 48, 94, boss6SkillPicId_3, 80, 25, 1, 15},
				{205, 34, 66, boss6SkillPicId_2, 80, 25, 1, 20},
				{205, 23, 43, boss6SkillPicId_1, 80, 25, 1, 20},
			},
			/*第7关*/
			{
				/*0-bossId, 1-picW, 2-picH, 3-picId, 4-speedX, 5-speedY, 6-frameNum, 7-damage*/
				{206, 68, 66, boss7SkillPicId_8, 64, 25, 1, 10},
				{206, 62, 59, boss7SkillPicId_7, 64, 25, 1, 10},
				{206, 54, 51, boss7SkillPicId_6, 64, 25, 1, 10},
				{206, 46, 44, boss7SkillPicId_5, 64, 25, 1, 10},
				{206, 38, 37, boss7SkillPicId_4, 64, 25, 1, 10},
				{206, 30, 29, boss7SkillPicId_3, 64, 25, 1, 10},
				{206, 28, 26, boss7SkillPicId_2, 64, 25, 1, 10},
				{206, 24, 22, boss7SkillPicId_1, 64, 25, 1, 10},
			}
	};

	/*炮台属性*/
	public int batteryParam[][] = {
			/*0-id, 1-width, 2-height, 3-blood, 4-scores, 5-speedX, 6-speedY, 7-coorX, 8-coorY
			 * 9-position, 10-attackpermission, 11-picId, 12-frameNum, 13-damage, 14-bombInterval, 15-picInterval, 16-prize*/
			
			{300, 70, 69, 300, 30, 3, 0, ScrW, 415, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_1, 3, 10, 1, 500,SPIRITI_PRIZE_NO},
			{300, 70, 69, 300, 30, 3, 0, ScrW, 415, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_1, 3, 10, 1, 500,SPIRITI_PRIZE_NO},
			{300, 70, 69, 300, 30, 3, 0, ScrW, 415, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_1, 3, 10, 1, 500,SPIRITI_PRIZE_NO},
			{300, 70, 69, 300, 30, 3, 0, ScrW, 415, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_1, 3, 10, 1, 500,SPIRITI_PRIZE_NO},
			{301, 131, 146, 350, 30, 3, 0, ScrW, 355, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_2, 4, 10, 1, 500,SPIRITI_PRIZE_YES},
			{301, 131, 146, 350, 30, 3, 0, ScrW, 355, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_2, 4, 10, 1, 500,SPIRITI_PRIZE_YES},
			{302, 92, 97, 200, 30, 3, 0, ScrW, 378, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_3, 1, 10, 1, 500,SPIRITI_PRIZE_YES},
			{302, 92, 97, 200, 30, 3, 0, ScrW, 378, OBJECT_POSITION_LEFT, ATTACK_PERMISSION_YES, battery_3, 1, 10, 1, 500,SPIRITI_PRIZE_YES},
	};
	
	/*精灵普通攻击属性*/
	public int spiritBombParam[][] = {
			/*0-精灵id, 1-id, 2-宽度, 3-高度, 4-伤害, 5-x速度, 6-Y速度, 7-子弹图片id,8-发射子弹个数 */
			{100, 15, 15, 14, 10, 20, 20, spiritBomb1PicId, 1},
			{101, 15, 15, 14, 10, 20, 5, spiritBomb1PicId, 3},
			{102, 15, 15, 14, 10, 20, 5, spiritBomb1PicId, 1},
			{103, 16, 21, 24, 10, 20, 5, spiritBomb2PicId, 3},
			{104, 16, 21, 24, 10, 20, 20, spiritBomb2PicId, 1},
			{105, 16, 21, 24, 10, 20, 20, spiritBomb2PicId, 1},
			{106, 16, 21, 24, 10, 20, 20, spiritBomb2PicId, 1},
			{107, 17, 14, 15, 15, 20, 20, spiritBomb3PicId, 1},
			{108, 18, 15, 15, 15, 20, 5, spiritBomb4PicId, 3},
			{109, 19, 24, 15, 15, 20, 20, spiritBomb5PicId, 1},
			{110, 14, 24, 15, 15, 20, 5, spiritBomb5PicId, 3},
			{111, 13, 50, 23, 15, 20, 20, spiritBomb6PicId, 1},
			//{200, 20, 85, 50, 25, 20, 20, bossBomb1PicId},
			{201, 15, 15, 14, 15, 20, 5, spiritBomb1PicId, 3},
			{202, 16, 21, 24, 15, 20, 5, spiritBomb2PicId, 3},
			{203, 16, 21, 24, 15, 20, 5, spiritBomb2PicId, 3},
			{204, 17, 14, 15, 15, 20, 5, spiritBomb3PicId, 3},
			{205, 18, 15, 15, 15, 20, 5, spiritBomb4PicId, 3},
			{206, 14, 24, 15, 15, 20, 5, spiritBomb5PicId, 3},
			{207, 14, 24, 15, 15, 20, 5, spiritBomb5PicId, 3},
			{300, 30, 15, 14, 15, 20, 15, spiritBomb1PicId, 1},
			{301, 31, 49, 52, 15, 20, 20, batteryBomb2PicId, 1},
			{302, 32, 31, 31, 15, 20, 30, batteryBomb3PicId, 3},
	};
	
	/*精灵属性*/
	public int spiritParam[][] = {
	/*0-id, 1-宽度, 2-高度, 3-血量, 4-积分, 5-x速度, 6-y速度, 7-x坐标, 8-y坐标, 9-初始位置, 
	 * 10-是否会攻击, 11-图片id, 12-是否会掉落奖品, 13-帧数间隔, 14-图片总帧数 , 15-精灵伤害
	 * 16-发射子弹间隔*/
		{100, 47, 62, 10, 15, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_1, SPIRITI_PRIZE_YES, 500, 5, 10, 3},
		{101, 84, 76, 30, 10, 8, 8, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_2, SPIRITI_PRIZE_YES, 500, 3, 10, 3},
		{102, 80, 62, 45, 10, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_3, SPIRITI_PRIZE_NO, 500, 3, 10, 2},
		
		{103, 50, 69, 45, 10, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_4, SPIRITI_PRIZE_NO, 500, 3, 10, 2},
		{104, 63, 89, 100, 20, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_5, SPIRITI_PRIZE_YES, 500, 3, 10, 2},
		{105, 93, 114, 20, 10, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_6, SPIRITI_PRIZE_NO, 500, 6, 10, 2},
		{106, 79, 104, 100, 10, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_7, SPIRITI_PRIZE_YES, 500, 2, 10, 2},
		
		{107, 60, 75, 100, 20, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_8, SPIRITI_PRIZE_YES, 500, 3, 10, 2},
		{108, 75, 66, 50, 20, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_9, SPIRITI_PRIZE_YES, 500, 3, 10, 2},
		
		{109, 105, 79, 150, 25, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_10, SPIRITI_PRIZE_YES, 500, 5, 10, 2},
		{110, 45, 97, 200, 25, 6, 6, 640, 200, 0, ATTACK_PERMISSION_YES, spirits_11, SPIRITI_PRIZE_YES, 500, 4, 10, 2},
		
		//幽灵boss出的小怪
		{111, 22, 32, 200, 25, 6, 6, 640, 200, 0, ATTACK_PERMISSION_NO, spirits_12, SPIRITI_PRIZE_NO, 500, 3, 5, 2},
		
		/*巨魔boss出的小怪*/
		{112, 68, 66, 100, 25, 20, 6, 640, 200, 0, ATTACK_PERMISSION_NO, boss7SkillPicId_8, SPIRITI_PRIZE_NO, 500, 1, 10, 2},
	};
	
	/*精灵批数信息*/
	public int batchInfo[][][] = {
			/*0-id, 1-数量, 2-初始位置, 3-移动方向*/
		/*第一关怪物列表*/	
		{
			{100, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{102, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{101, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{104, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{102, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{104, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{104, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			//{102, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{102, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第二关怪物列表*/	
		{
			{102, 4, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{101, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{101, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{102, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{102, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{104, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{100, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{104, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{104, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			//{102, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{102, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第三关怪物列表*/	
		{
			{103, 4, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{105, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{105, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{105, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{103, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{103, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{106, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{105, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{103, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			//{105, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第四关怪物列表*/	
		{
			{103, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{105, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{105, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{105, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{103, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{103, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{106, 2, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{105, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{103, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{106, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			//{105, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			//{100, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
		
		/*第五关怪物列表*/	
		{
			{107, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{107, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{108, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{108, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{107, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 2, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{107, 3, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{107, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 4, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{107, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{108, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
		},
		
		/*第六关怪物列表*/	
		{
			{107, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{107, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{108, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{108, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{107, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{107, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{107, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{107, 2, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{108, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{107, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{108, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
		},
		
		/*第七关怪物列表*/	
		{
			{109, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{109, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{110, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{110, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{110, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{109, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{110, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{109, 2, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{110, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{109, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{110, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
		},
		
		/*第八关怪物列表*/	
		{
			{109, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{110, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{109, 2, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{110, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{109, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 4, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{110, 10, OBJECT_POSITION_LEFT, OBJECT_DIRECTION_RIGHT},
			{109, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{109, 3, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{110, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{110, 4, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
			{109, 2, OBJECT_POSITION_UP, OBJECT_DIRECTION_RIGHT_DOWN},
			{110, 3, OBJECT_POSITION_DOWN, OBJECT_DIRECTION_RIGHT_UP},
			{109, 10, OBJECT_POSITION_RIGHT, OBJECT_DIRECTION_LEFT},
		},
	};
	
	/*描述信息*/
	public String descInfo[] = {
			"守护精灵：升级一次,守护精灵数加一",
			"必杀技：全屏攻击,无敌状态,持续5秒",
	};
	
	public String helpInfo = "龙猫勇士是一款横版闯关射击游戏，龙猫会自动发射子弹，使用方向键控制龙猫移动，击落敌人有几率掉落附加武器、武器升级、血瓶、和守护精灵。此外，玩家还可以花费游戏币解锁龙猫、升级龙猫、复活龙猫、购买必杀。#r#r";
	
	public String helpInfo2 = 
			"附加武器：附加武器可以给龙猫增添火力.游戏中一共有2种附加武器，跟踪导弹和激光。#r"+
			"武器升级：武器升级可以增加龙猫本身火力威力，每种龙猫武器升级后的效果都不一样。#r"+
			"血瓶：血瓶可以恢复龙猫一定的血量。#r"+
			"守护精灵：守护精灵可以帮助你一起作战，初始龙猫只能携带1个守护精灵，每升一级增加一只，最多增加到4只，玩家每死一条命守护精灵也会损失1个。#r#r"
	;
	
	public String helpInfo3 = 
			"解锁龙猫：点击开始游戏进入龙猫选择界面，其中2只龙猫是未解锁的，需要玩家使用游戏币解锁。未解锁的龙猫拥有更强的体力和攻击力.每只龙猫的解锁费用为100"+engine.getEngineService().getExpendAmountUnit()+"。#r"+
			"升级龙猫：在战斗场景中点击数字键9进入商城可以升级龙猫.升级后可以增加携带守护精灵的数量.升级的费用依次为：20、40、80"+engine.getEngineService().getExpendAmountUnit()+"。#r"+
			"复活龙猫：当玩家失去所有的生命可以选择复活龙猫。关复活的花费不同，分别为10、20、30、30、30、30、30、30"+engine.getEngineService().getExpendAmountUnit()+"。#r"+
			"购买必杀：在战斗场景中点击数字键9进入商城可以购买必杀。必杀能让玩家持续一段时间的无敌并对敌人造成很大的伤害.每个必杀需要20"+engine.getEngineService().getExpendAmountUnit()+"。#r"
	;
}

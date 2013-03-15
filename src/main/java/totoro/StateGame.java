package totoro;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.ui.TextView;
import cn.ohyeah.stb.util.Collision;

public class StateGame implements Common{
	
	private TotoroGameEngine engine;
	public StateGame(TotoroGameEngine engine){
		this.engine = engine;
	}
	
	public static int game_status;
	
	public static long level_start_time;
	public static long level_end_time;
	public static int levelInterval;
	public static boolean level_over;	//1:true, -1:false
	
	public int level = 1;
	//public boolean isNextLevel;
	public static boolean isCeateBoss;	//1:true, -1:false
	
	public MoveObjectFactory factory;
	public MoveObjectShow objectShow;
	public static MoveObject player;
	public Exploder[] exploders = new Exploder[12];
	public Exploder[] missileEffects = new Exploder[12];
	public Exploder[] scoresEffects = new Exploder[12];
	private int eIndex, mIndex, sIndex;
	
	private long bombStart, bombEnd;
	private int bombInterval = 400;
	
	private long missileStart;
	private int missileInterval = 1500;
	
	private long spiritStart, spiritEnd;
	private long batteryStart, batteryEnd;
	private long reviveStime, reviveEtime;
	public static boolean isUserVentose;
	private long venSTime, venETime, venSTime2;
	
	public static int startGameVentoseNums;
	private boolean isVentosePrompt;
	private long ventoseTime1, ventoseTime2;
	
	//存档数据
	public static int lifeNum;
	public static int currLevel=1;
	public static int scores;
	public static int blood;
	public static int grade;
	public static int bombGrade;
	public static int wingplaneMaxNums = 1;
	public static int wingplaneNums;
	public static int missileGrade;
	public static int laserNums;
	public static int ventoseNum;
	public static boolean hasTotoro3;
	public static boolean hasTotoro4;
	public static int batchIndex;
	
	//boss info
	public static int bossBlood;
	
	public void handleKey(KeyState keyState){
		if(keyState.containsMoveEventAndRemove(KeyCode.UP) 
				&& player.status != ROLE_STATUS_PASS && game_status != GAME_SUCCESS){
			move(0);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.DOWN)
				&& player.status != ROLE_STATUS_PASS && game_status != GAME_SUCCESS){
			move(1);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.RIGHT)
				&& player.status != ROLE_STATUS_PASS && game_status != GAME_SUCCESS){
			move(2);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.LEFT)
				&& player.status != ROLE_STATUS_PASS && game_status != GAME_SUCCESS){
			move(3);
		}else if(keyState.containsAndRemove(KeyCode.NUM1) && player.status != ROLE_STATUS_PASS){
			if(engine.isDebugMode()){
				venSTime = getTime();
				isUserVentose = true;
				player.status = ROLE_STATUS_PROTECTED;
			}else{
				if(!isUserVentose){
					if(ventoseNum+startGameVentoseNums > 0){
						venSTime = getTime();
						isUserVentose = true;
						player.status = ROLE_STATUS_PROTECTED;
						if(startGameVentoseNums>0){
							startGameVentoseNums--;
						}else{
							ventoseNum--;
						}
					}else{
						isVentosePrompt = true;
						ventoseTime1 = getTime();
					}
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM0)){
			game_status = GAME_PAUSE;
		}else if(keyState.containsAndRemove(KeyCode.NUM2)){
			if(engine.isDebugMode()){
				if(player.missileGrade<2 ){
					player.missileGrade ++;
					missileGrade = player.missileGrade;
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM3)){
			if(engine.isDebugMode()){
				level_over = true;
				if(factory.boss.size()<1){
					isCeateBoss = true;
				}else{
					for(int i=0;i<factory.boss.size();i++){
						MoveObject mo = (MoveObject) factory.boss.elementAt(i);
						mo.status = ROLE_STATUS_DEAD;
					}
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM4)){
			if(engine.isDebugMode()){
				player.bombGrade = (player.bombGrade+1)%5;
				if(player.bombGrade==0)
				{
					player.bombGrade = 1;
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM5)){
			if(engine.isDebugMode()){
				if(player.status == ROLE_STATUS_PROTECTED){
					player.status = ROLE_STATUS_ALIVE;
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM6)){
			if(engine.isDebugMode()){
				if(factory.lasers.size()<1){
					factory.createLaster(player);
				}/*else{
					MoveObject object = (MoveObject) factory.lasers.elementAt(0);
					if(object.frame>0){
						object.frame--;
						object.damage += 5;
					}
				}*/
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM7)){
			if(engine.isDebugMode()){
				factory.createMissile(player, factory.spirits, factory.boss, factory.battery);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM8)){
			if(engine.isDebugMode()){
				//factory.createWingplane(player);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM9)){
			StateShop ss = new StateShop(engine);
			ss.processShop();
			
			//调整时间
			venSTime = getTime()-(venETime-venSTime); //调整必杀技时间
			level_start_time = getTime()/1000-(level_end_time - level_start_time);
			for(int i=factory.boss.size()-1;i>=0;i--){
				MoveObject boss = (MoveObject) factory.boss.elementAt(i);
				boss.skill1STime = getTime()-(boss.skill1ETime-boss.skill1STime);
				boss.skill2STime = getTime()-(boss.skill2ETime-boss.skill2STime);
			}
		}
		
	}
	
	public void execute(){
		
		revivePlayer();
		
		level_end_time = getTime()/1000;
		if(level <= 8 && (level_end_time - level_start_time) > levelInfo[level-1][1]){	
			//if((level_end_time - level_start_time) > levelInfo[level-1][1]){
				level_over = true;
			//}
		}
		
		//judgeNextLevel();
		
		createSpirits();
		
		nextLevel();
		
		createPlayerSkill();
		
		createSpiritsBombs();
		
		collisionDetection();
		
		removeOutsideObject();
		
		entryGameStatus();
		
	}
	
	private void createSpirits() {
		if(level_over){
			if(factory.boss.size()<1){
				if(!isCeateBoss){
					factory.createBoss(level);
					isCeateBoss = true;
				}
			}
		}else{
			//if(!isCeateBoss){
				spiritEnd = getTime();
				batteryEnd = getTime();
				if(spiritEnd - spiritStart >= levelInfo[level-1][2]){
					factory.cteateBatchSpirits(level, batchIndex);
					batchIndex = (batchIndex+1)%batchInfo[level-1].length;
					spiritStart = getTime();
				}
				if(batteryEnd - batteryStart >= levelInfo[level-1][3] && level != 1 && level != 2){
					factory.createBattery(level);
					batteryStart = getTime();
				}
			//}
		}
	}
	
	private void nextLevel() {
		if(factory.boss.size()<1 && isCeateBoss){
			player.status = ROLE_STATUS_PASS;
			player.speedX = playerParam[player.grade-1][9]+10;
			if(level >= 8){
				//通关
				game_status = GAME_SUCCESS;
				factory.removeEnemy();
			}
		}
		
		if(player.status == ROLE_STATUS_PASS){
			player.mapx += player.speedX;
			if(player.mapx > ScrW){
				player.speedX = playerParam[player.grade-1][9];
				levelInterval = (int)(level_end_time-level_start_time);
				if(factory.boss.size()>0){
					MoveObject object =  (MoveObject) factory.boss.elementAt(0);
					bossBlood = object.blood;
				}
				changeDatePass();
			}
		}
	}

	private void revivePlayer() {
		
		//玩家复活
		if(player.status == ROLE_STATUS_DEAD && player.lifeNum>0){
			reviveEtime = getTime();
			//System.out.println("time:"+(reviveEtime-reviveStime));
			if(reviveEtime-reviveStime > 1000){
				player = factory.revivePlayer(grade);
				blood = player.blood;
			}
		}else if(player.lifeNum <= 0){
			System.out.println("game over");
			game_status = GAME_FAIL;
		}
		
		/*复活后无敌时间*/
		player.endTime = getTime();
		if(!isUserVentose && player.status == ROLE_STATUS_PROTECTED && player.endTime-player.startTime > 3000){
			player.status = ROLE_STATUS_ALIVE;
		}
	}

	private void createPlayerSkill() {
		bombEnd = getTime();
		if(player != null 
			&& player.status != ROLE_STATUS_DEAD 
			&& player.status != ROLE_STATUS_PASS 
			&& bombEnd - bombStart > bombInterval){
			
			//if(factory.lasers.size()<1){
				factory.createBomb(player);
			//}
			for(int k=0;k<factory.wingplane.size();k++){ //僚机普通攻击
				MoveObject mo = (MoveObject) factory.wingplane.elementAt(k);
				factory.createWingplaneBomb(mo);
			}
			bombStart = getTime();
			
			for(int m=0;m<factory.lasers.size();m++){
				MoveObject laser = (MoveObject) factory.lasers.elementAt(m);
				laser.endTime = getTime();
				if(laser.endTime-laser.startTime > 1000){
					laser.startTime = getTime();
					laser.status2 = ROLE_STATUS2_ATTACK;
				}
			}
		}
		
		if(player != null && bombEnd - missileStart>missileInterval){
			if(player.missileGrade>0 && factory.lasers.size()<1){
				factory.createMissile(player, factory.spirits, factory.boss, factory.battery);
				missileStart = getTime();
			}
		}
		
		/*必杀技*/
		venETime = getTime();
		if(venETime - venSTime > 4000 && isUserVentose){
			 isUserVentose = false;
			 player.status = ROLE_STATUS_ALIVE;
		}
		if(game_status!=GAME_PAUSE && isUserVentose && (venETime - venSTime2)>500){
			venSTime2 = getTime();
			factory.createVentose(player);
		}
	}

	private void entryGameStatus() {
		switch (game_status){
		case GAME_PAUSE:	//暂停
			StateSubMenu menu = new StateSubMenu(engine);
			int index = menu.processSubMenu();
			if(index == 1){
				//返回主界面
				levelInterval = (int) (level_end_time-level_start_time);
				if(factory.boss.size()>0){
					MoveObject object =  (MoveObject) factory.boss.elementAt(0);
					bossBlood = object.blood;
				}
				engine.saveRecord(1);
				engine.sysProps();
				engine.saveAttainment();
				engine.queryList();
				engine.state = STATUS_MAIN_MENU;
				quitGameDeleteDate();
			}else{
				venSTime = getTime()-(venETime-venSTime); //调整必杀技时间
				level_start_time = getTime()/1000-(level_end_time - level_start_time);
				for(int i=factory.boss.size()-1;i>=0;i--){
					MoveObject boss = (MoveObject) factory.boss.elementAt(i);
					boss.skill1STime = getTime()-(boss.skill1ETime-boss.skill1STime);
					boss.skill2STime = getTime()-(boss.skill2ETime-boss.skill2STime);
				}
			}
			game_status = GAME_PLAY;
			break;
		case GAME_SUCCESS: 	//通关
			drawPassInterface();
			break;	
		case GAME_FAIL:		//失败
			PlayerProp pp = null;
			if(level == 1){
				pp = engine.pm.getPropById(67);
			}else if(level == 2){
				pp = engine.pm.getPropById(68);
			}else if(level > 2){
				pp = engine.pm.getPropById(69);
			}
			StateGameFail fail = new StateGameFail(engine);
			int i = fail.processGameFail(pp.getPrice());
			if(i == 0){
				//买复活
				ServiceWrapper sw = engine.getServiceWrapper();
				sw.expend(pp.getPrice(), pp.getPropId(), "购买"+pp.getName());
				PopupText pt = UIResource.getInstance().buildDefaultPopupText();
				if (sw.isServiceSuccessful()) {
					pt.setText("购买"+pp.getName()+"成功");
					player.lifeNum = 3;
					lifeNum = player.lifeNum;
					startGameVentoseNums = 3;
				}
				else {
					pt.setText("购买"+pp.getName()+"失败, 原因: "+sw.getServiceMessage());
					
				}
				pt.popup();
				/*if(engine.getEngineService().getBalance() >= count){
					ServiceWrapper sw = engine.getServiceWrapper();
					sw.expend(count, "购买复活");
					player.lifeNum = 3;
					lifeNum = player.lifeNum;
					startGameVentoseNums = 3;
				}else{
					PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
					pc.setText(engine.getEngineService().getExpendAmountUnit()+"不足,是否去充值?");
					int p = pc.popup();
					if(p==0){
						Recharge recharge = new Recharge(engine);
						recharge.recharge();
					}
					PopupText pt = UIResource.getInstance().buildDefaultPopupText();
					pt.setText(engine.getEngineService().getExpendAmountUnit()+"不足，请到大厅充值!");
					pt.popup();
				}*/
				game_status = GAME_PLAY;
			}else{
				//退出游戏
				engine.saveAttainment();
				engine.sysProps();
				engine.saveRecord(-1);
				quitGameDeleteDate();
				engine.state = STATUS_MAIN_MENU;
				game_status = GAME_PLAY;
			}
			break;
		} 
	}

	private void drawPassInterface() {
		StateGameSuccess success = new StateGameSuccess(engine, this);
		success.processGameSuccess(scores);
		engine.state = STATUS_MAIN_MENU;
		game_status = GAME_PLAY;
		engine.saveAttainment();
		engine.sysProps();
		engine.queryList();
		engine.saveRecord(-1);
		quitGameDeleteDate();
	}

	/*private void judgeNextLevel() {
		
		if(level_over){
			if(factory.boss.size()<1){
				if(isCeateBoss && player.status != ROLE_STATUS_PASS){
					//isNextLevel = true;
					player.status = ROLE_STATUS_PASS;
					player.speedX = playerParam[player.grade-1][9]+10;
					if(level >= 8){
						//通关
						game_status = GAME_SUCCESS;
						factory.removeEnemy();
					}
				}
				if(factory.spirits.size()<1 && !isNextLevel){
					factory.createBoss(level);
					isCeateBoss = true;
				}
			}
		}else{
			if(!isNextLevel){
				spiritEnd = getTime();
				batteryEnd = getTime();
				if(spiritEnd - spiritStart >= levelInfo[level-1][2]){
					factory.cteateBatchSpirits(level, batchIndex);
					batchIndex = (batchIndex+1)%batchInfo[level-1].length;
					spiritStart = getTime();
				}
				if(batteryEnd - batteryStart >= levelInfo[level-1][3] && level != 1 && level != 2){
					factory.createBattery(level);
					batteryStart = getTime();
				}
			}
		}
		
		if(player.status == ROLE_STATUS_PASS){
			player.mapx += player.speedX;
			if(player.mapx > ScrW){
				player.speedX = playerParam[player.grade-1][9];
				levelInterval = (int)(level_end_time-level_start_time);
				if(factory.boss.size()>0){
					MoveObject object =  (MoveObject) factory.boss.elementAt(0);
					bossBlood = object.blood;
				}
				changeDatePass();
			}
		}
		
	}*/

	/*创建敌方攻击*/
	private void createSpiritsBombs() {
		for(int j=0;j<factory.spirits.size();j++){
			MoveObject object = (MoveObject) factory.spirits.elementAt(j);
			if(object.status2 == ROLE_STATUS2_ATTACK && object.attackPermission == ATTACK_PERMISSION_YES){
				factory.createSpiritBomb(object);
				object.status2 = ROLE_STATUS2_MOVE;
			}
		}
		
		for(int j=0;j<factory.boss.size();j++){
			MoveObject object = (MoveObject) factory.boss.elementAt(j);
			if(object.status2 == ROLE_STATUS2_SKILL2_ATTACK){
				if(object.id==200 || object.id==201 || object.id==203
						|| object.id == 205){
					factory.createBossSkill(object, null);
					object.status2 = ROLE_STATUS2_MOVE;
					object.skillStatue = 0;
					object.skill2STime = System.currentTimeMillis();
				}else if(object.id == 206){
					object.bombETime = System.currentTimeMillis();
					if(object.bombNum<8){
						if(object.bombETime-object.bombSTime>=5){
							factory.createBoss7Bomb(object, object.bombNum);
							object.bombNum++;
							object.bombSTime = System.currentTimeMillis();
						}
					}else{
						object.status2 = ROLE_STATUS2_MOVE;
						object.bombNum = 0;
					}
				}else if(object.id == 207){ //第8个boss第2个技能
					//factory
				}
			}else if(object.status2 == ROLE_STATUS2_SKILL_ATTACK){
				if(object.id == 204 || object.id == 205 || object.id == 206){
					factory.createBossSkill(object, null);
					object.status2 = ROLE_STATUS2_MOVE;
				}else if(object.id == 207){
					if(factory.boss8Spirit==null){
						factory.createBoss8Spirit(object);
					}else{
						if(factory.boss8Spirit.status == ROLE_STATUS_DEAD){
							factory.createBossSkill(object, factory.boss8Spirit);
							object.status2 = ROLE_STATUS2_MOVE;
							factory.boss8Spirit = null;
						}
					}
				}
			}
		}
		
		for(int j=0;j<factory.battery.size();j++){
			MoveObject object = (MoveObject) factory.battery.elementAt(j);
			if(object.status2 == ROLE_STATUS2_ATTACK && object.attackPermission == ATTACK_PERMISSION_YES){
				factory.createBatteryBombs(object, player);
				object.status2 = ROLE_STATUS2_MOVE;
			}
		}
		
		for(int j=0;j<factory.ghostSpirits.size();j++){
			MoveObject object = (MoveObject) factory.ghostSpirits.elementAt(j);
			if(object.status2 == ROLE_STATUS2_ATTACK){
				factory.createSpiritBomb(object);
				object.status2 = ROLE_STATUS2_MOVE;
			}
		}
	}

	private void collisionDetection() {
		
		/*玩家普通攻击碰撞检测*/
		playerCollision();
		
		/*怪物碰撞检测*/
		spiritsCollision();
		
	}
	
	private void bombHitPlayer(MoveObject mo) {
		if(player.status != ROLE_STATUS_PROTECTED){
			if(player.blood - mo.damage >0){
				player.blood -= mo.damage;
			}else{
				//player.blood = 0;
				/*玩家死亡*/
				//if(player.blood <= 0 && player.status == ROLE_STATUS_ALIVE){
					player.status = ROLE_STATUS_DEAD;
					player.lifeNum --;
					lifeNum = player.lifeNum;
					if(player.bombGrade>1){
						player.bombGrade--;
						bombGrade = player.bombGrade;
					}
					player.missileGrade = 0;
					missileGrade = 0;
					laserNums = 0;
					reviveStime = getTime();
					factory.lasers.removeAllElements();
					factory.missile.removeAllElements();
					if(factory.wingplane.size()>0){
						MoveObject m = (MoveObject) factory.wingplane.elementAt(factory.wingplane.size()-1);
						m.status = ROLE_STATUS_DEAD;
					}
				//}
			}
			blood = player.blood;
			Exploder exploder = new Exploder(mo.mapx,mo.mapy);
			exploders[eIndex] = exploder;
			if(eIndex < exploders.length-1){
				eIndex ++;
			}else{
				eIndex=0;
			}
		}
	}

	private void spiritsCollision() {
		for(int j=0;j<factory.spirits.size();j++){
			MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
			if(Collision.checkCircularCollision(player.mapx, player.mapy, player.width, player.height, mo.mapx, mo.mapy, mo.width, mo.height)
					 && player.status == ROLE_STATUS_ALIVE){
				hitPlayer(mo);
			}
			/*for(int k=0;k<factory.wingplane.size();k++){
				MoveObject wing = (MoveObject) factory.wingplane.elementAt(k);
				if(Collision.checkCircularCollision(wing.mapx, wing.mapy, wing.width, wing.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					hitWingplane(wing,mo);
				}
			}*/
			if(mo.blood <= 0){
				mo.status = ROLE_STATUS_DEAD;
			}
		}
		for(int j=0;j<factory.ghostSpirits.size();j++){
			MoveObject mo = (MoveObject) factory.ghostSpirits.elementAt(j);
			if(Collision.checkCircularCollision(player.mapx, player.mapy, player.width, player.height, mo.mapx, mo.mapy, mo.width, mo.height)
					 && player.status == ROLE_STATUS_ALIVE){
				hitPlayer(mo);
			}
			/*for(int k=0;k<factory.wingplane.size();k++){
				MoveObject wing = (MoveObject) factory.wingplane.elementAt(k);
				if(Collision.checkCircularCollision(wing.mapx, wing.mapy, wing.width, wing.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					hitWingplane(wing,mo);
				}
			}*/
			if(mo.blood <= 0){
				mo.status = ROLE_STATUS_DEAD;
			}
		}
		
		/*敌方普通攻击碰撞检测*/
		for(int j=0;j<factory.spiritBombs.size();j++){
			MoveObject mo = (MoveObject) factory.spiritBombs.elementAt(j);
			if(Collision.checkCircularCollision(mo.mapx, mo.mapy, mo.width, mo.height, player.mapx, player.mapy, player.width, player.height)
					&& player.status == ROLE_STATUS_ALIVE){
				mo.status = ROLE_STATUS_DEAD;
				bombHitPlayer(mo);
			}
		}
		
		/*敌方技能攻击碰撞检测*/
		for(int j=0;j<factory.boss1Skill.size();j++){
			MoveObject mo = (MoveObject) factory.boss1Skill.elementAt(j);
			if(Collision.checkCircularCollision(mo.mapx, mo.mapy, mo.width, mo.height, player.mapx, player.mapy, player.width, player.height)
					 && player.status == ROLE_STATUS_ALIVE){
				bombHitPlayer(mo);
			}
		}
		
		/*敌方技能攻击碰撞检测*/
		for(int j=0;j<factory.boss8Skill.size();j++){
			MoveObject mo = (MoveObject) factory.boss8Skill.elementAt(j);
			if(Collision.checkSquareToCircularCollision(mo.mapx, mo.mapy, mo.width, mo.height, player.mapx, player.mapy, player.width, player.height)
					 && player.status == ROLE_STATUS_ALIVE){
				bombHitPlayer(mo);
			}
		}
		
		/*boss冲撞撞检测*/
		for(int j=0;j<factory.boss.size();j++){
			MoveObject mo = (MoveObject) factory.boss.elementAt(j);
			if(Collision.checkCircularCollision(player.mapx, player.mapy, player.width, player.height, mo.mapx, mo.mapy, mo.width, mo.height)
					 && player.status == ROLE_STATUS_ALIVE){
				hitPlayer(mo);
			}
		}
		
		/*prop*/
		for(int j=0;j<factory.props.size();j++){
			MoveObject mo = (MoveObject)factory.props.elementAt(j);
			if(Collision.checkCircularCollision(mo.mapx, mo.mapy, mo.width, mo.height, player.mapx, player.mapy, player.width, player.height)
					 && player.status != ROLE_STATUS_DEAD){
				mo.status = ROLE_STATUS_DEAD;
				eatProp(mo);
				player.scores += 1000;
				scores = player.scores;
				Exploder exploder = new Exploder(mo.mapx,mo.mapy);
				scoresEffects[sIndex] = exploder;
				if(sIndex < scoresEffects.length-1){
					sIndex ++;
				}else{
					sIndex=0;
				}
			}
		} 
	}

	/*private void hitWingplane(MoveObject wing, MoveObject mo) {
		if(wing.blood - mo.damage >0){
			wing.blood -= mo.damage;
		}else{
			wing.status = ROLE_STATUS_DEAD;
		}
		mo.blood -= wing.damage;
		Exploder exploder = new Exploder(mo.mapx,mo.mapy);
		exploders[eIndex] = exploder;
		if(eIndex < exploders.length-1){
			eIndex ++;
		}else{
			eIndex=0;
		}
	}*/

	private void hitPlayer(MoveObject mo) {
		if(player.status != ROLE_STATUS_PROTECTED){
			if(player.blood - mo.damage >0){
				player.blood -= mo.damage;
			}else{
				//player.blood = 0;
				/*玩家死亡*/
				//if(player.blood <= 0 && player.status == ROLE_STATUS_ALIVE){
					player.status = ROLE_STATUS_DEAD;
					player.lifeNum --;
					lifeNum = player.lifeNum;
					if(player.bombGrade>1){
						player.bombGrade--;
						bombGrade = player.bombGrade;
					}
					player.missileGrade = 0;
					missileGrade = 0;
					laserNums = 0;
					reviveStime = getTime();
					factory.lasers.removeAllElements();
					factory.missile.removeAllElements();
					if(factory.wingplane.size()>0){
						MoveObject m = (MoveObject) factory.wingplane.elementAt(factory.wingplane.size()-1);
						m.status = ROLE_STATUS_DEAD;
					}
				//}
			}
			blood = player.blood;
			Exploder exploder = new Exploder(mo.mapx,mo.mapy);
			exploders[eIndex] = exploder;
			if(eIndex < exploders.length-1){
				eIndex ++;
			}else{
				eIndex=0;
			}
		}
		mo.blood -= player.damage;
	}

	private void playerCollision() {
		for(int i=0;i<factory.bombs.size();i++){
			MoveObject bomb = (MoveObject) factory.bombs.elementAt(i);
			for(int j=0;j<factory.spirits.size();j++){
				MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
				if(Collision.checkSquareToCircularCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, mo.mapx, mo.mapy, mo.width, mo.height)
						&& mo.status == ROLE_STATUS_ALIVE){
					if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					}
					mo.blood -= bomb.damage;
					Exploder exploder = new Exploder(bomb.mapx,bomb.mapy);
					exploders[eIndex] = exploder;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
				if(mo.blood<=0){
					if(mo.pirze == SPIRITI_PRIZE_YES){
						factory.createProps(mo, level);
					}
					mo.status = ROLE_STATUS_DEAD;
					player.scores += mo.scores;
					scores = player.scores;
				}
			}
			for(int k=0;k<factory.boss.size();k++){
				MoveObject boss = (MoveObject) factory.boss.elementAt(k);
				if(Collision.checkSquareToCircularCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, boss.mapx+30, boss.mapy, boss.width, boss.height)){
					if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					}
					hitBoss(boss, bomb, bomb.mapx, bomb.mapy);
				}     
			}
			for(int k=0;k<factory.battery.size();k++){
				MoveObject battery = (MoveObject) factory.battery.elementAt(k);
				if(Collision.checkSquareToCircularCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, battery.mapx, battery.mapy, battery.width, battery.height)){
					if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					}
					battery.blood -= bomb.damage;
					Exploder exploder = new Exploder(bomb.mapx,bomb.mapy);
					exploders[eIndex] = exploder;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
				if(battery.blood<=0){
					battery.status = ROLE_STATUS_DEAD;
					player.scores += battery.scores;
					scores = player.scores;
					/*if(battery.pirze == SPIRITI_PRIZE_YES){
						factory.createProps(battery, level);
					}*/
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
			for(int k=0;k<factory.ghostSpirits.size();k++){
				MoveObject mo = (MoveObject) factory.ghostSpirits.elementAt(k);
				if(Collision.checkSquareToCircularCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					}
					mo.blood -= bomb.damage;
					Exploder exploder = new Exploder(bomb.mapx,bomb.mapy);
					exploders[eIndex] = exploder;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
				if(mo.blood<=0){
					mo.status = ROLE_STATUS_DEAD;
					player.scores += mo.scores;
					scores = player.scores;
					/*if(battery.pirze == SPIRITI_PRIZE_YES){
						factory.createProps(battery, level);
					}*/
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
		}
		
		/*激光*/
		for(int i=0;i<factory.lasers.size();i++){
			MoveObject laser = (MoveObject) factory.lasers.elementAt(i);
			for(int j=0;j<factory.spirits.size();j++){
				MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
				if((mo.mapy<=laser.mapy+laser.height && mo.mapy+mo.height>=laser.mapy)
						&& laser.mapx<mo.mapx && laser.status2 == ROLE_STATUS2_ATTACK
						&& mo.status == ROLE_STATUS_ALIVE){
					mo.blood -= laser.damage;
					//laser.width = mo.mapx+mo.width/2 - laser.mapx;
					Exploder exploder = new Exploder(mo.mapx,mo.mapy+mo.height/2);
					exploders[eIndex] = exploder;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
				if(mo.blood<=0){
					if(mo.pirze == SPIRITI_PRIZE_YES){
						factory.createProps(mo, level);
					}
					mo.status = ROLE_STATUS_DEAD;
					player.scores += mo.scores;
					scores = player.scores;
				}
			}
			for(int j=0;j<factory.boss.size();j++){
				MoveObject boss = (MoveObject) factory.boss.elementAt(j);
				if((boss.mapy<=laser.mapy+laser.height && boss.mapy+boss.height>=laser.mapy)
						&& laser.mapx<boss.mapx && laser.status2 == ROLE_STATUS2_ATTACK){
					hitBoss(boss, laser, boss.mapx+20, boss.mapy+boss.height/2);
				}
			}
			for(int j=0;j<factory.battery.size();j++){
				MoveObject battery = (MoveObject) factory.battery.elementAt(j);
				if((battery.mapy<=laser.mapy+laser.height && battery.mapy+battery.height>=laser.mapy)
						&& laser.mapx<battery.mapx && laser.status2 == ROLE_STATUS2_ATTACK){
					battery.blood -= laser.damage;
					//laser.width = battery.mapx+battery.width/2 - laser.mapx;
					Exploder exploder = new Exploder(battery.mapx,battery.mapy+battery.height/2);
					exploders[eIndex] = exploder;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
				if(battery.blood<=0){
					battery.status = ROLE_STATUS_DEAD;
					player.scores += battery.scores;
					scores = player.scores;
				}
			}
			for(int j=0;j<factory.ghostSpirits.size();j++){
				MoveObject mo = (MoveObject) factory.ghostSpirits.elementAt(j);
				if((mo.mapy<=laser.mapy+laser.height && mo.mapy+mo.height>=laser.mapy)
						&& laser.mapx<mo.mapx && laser.status2 == ROLE_STATUS2_ATTACK){
					mo.blood -= laser.damage;
					//laser.width = battery.mapx+battery.width/2 - laser.mapx;
					Exploder exploder = new Exploder(mo.mapx,mo.mapy);
					exploders[eIndex] = exploder;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
				if(mo.blood<=0){
					mo.status = ROLE_STATUS_DEAD;
					player.scores += mo.scores;
					scores = player.scores;
				}
			}
		}
		
		/*导弹*/
		for(int i=0;i<factory.missile.size();i++){
			MoveObject bomb = (MoveObject) factory.missile.elementAt(i);
			for(int j=0;j<factory.spirits.size();j++){
				MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, mo.mapx, mo.mapy, mo.width, mo.height)
						&& mo.status == ROLE_STATUS_ALIVE){
					//if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					//}
					mo.blood -= bomb.damage;
					Exploder exploder = new Exploder(bomb.mapx,bomb.mapy);
					missileEffects[mIndex] = exploder;
					if(mIndex < missileEffects.length-1){
						mIndex ++;
					}else{
						mIndex=0;
					}
				}
				if(mo.blood<=0){
					if(mo.pirze == SPIRITI_PRIZE_YES){
						factory.createProps(mo, level);
					}
					mo.status = ROLE_STATUS_DEAD;
					player.scores += mo.scores;
					scores = player.scores;
				}
			}
			for(int k=0;k<factory.boss.size();k++){
				MoveObject boss = (MoveObject) factory.boss.elementAt(k);
				if(Collision.checkSquareToCircularCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, boss.mapx+30, boss.mapy, boss.width, boss.height)){
					//if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					//}
					hitBoss(boss, bomb, bomb.mapx,bomb.mapy);
				}
			}
			for(int k=0;k<factory.battery.size();k++){
				MoveObject battery = (MoveObject) factory.battery.elementAt(k);
				if(Collision.checkSquareToCircularCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, battery.mapx, battery.mapy, battery.width, battery.height)){
					//if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					//}
					battery.blood -= bomb.damage;
					Exploder exploder = new Exploder(bomb.mapx,bomb.mapy);
					missileEffects[mIndex] = exploder;
					if(mIndex < missileEffects.length-1){
						mIndex ++;
					}else{
						mIndex=0;
					}
				}
				if(battery.blood<=0){
					battery.status = ROLE_STATUS_DEAD;
					player.scores += battery.scores;
					scores = player.scores;
					/*if(battery.pirze == SPIRITI_PRIZE_YES){
						factory.createProps(battery, level);
					}*/
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
			for(int k=0;k<factory.ghostSpirits.size();k++){
				MoveObject mo = (MoveObject) factory.ghostSpirits.elementAt(k);
				if(Collision.checkSquareToCircularCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					//if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					//}
					mo.blood -= bomb.damage;
					Exploder exploder = new Exploder(bomb.mapx,bomb.mapy);
					exploders[eIndex] = exploder;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
				if(mo.blood<=0){
					mo.status = ROLE_STATUS_DEAD;
					player.scores += mo.scores;
					scores = player.scores;
					/*if(battery.pirze == SPIRITI_PRIZE_YES){
						factory.createProps(battery, level);
					}*/
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
		}
		
		/*必杀*/
		for(int i=0;i<factory.ventose.size();i++){
			MoveObject bomb = (MoveObject) factory.ventose.elementAt(i);
			for(int j=0;j<factory.spirits.size();j++){
				MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
				if(Collision.checkSquareCollision( mo.mapx, mo.mapy, mo.width, mo.height, bomb.mapx, bomb.mapy, bomb.width, bomb.height)
						&& mo.status == ROLE_STATUS_ALIVE){
					mo.blood -= bomb.damage;
					Exploder exploder = new Exploder(mo.mapx,mo.mapy);
					missileEffects[mIndex] = exploder;
					if(mIndex < missileEffects.length-1){
						mIndex ++;
					}else{
						mIndex=0;
					}
				}
				if(mo.blood<=0){
					if(mo.pirze == SPIRITI_PRIZE_YES){
						factory.createProps(mo, level);
					}
					mo.status = ROLE_STATUS_DEAD;
					player.scores += mo.scores;
					scores = player.scores;
				}
			}
			for(int k=0;k<factory.boss.size();k++){
				MoveObject boss = (MoveObject) factory.boss.elementAt(k);
				if(Collision.checkSquareCollision(boss.mapx, boss.mapy, boss.width, boss.height, bomb.mapx, bomb.mapy, bomb.width, bomb.height)){
					boss.blood -= bomb.damage;
					//bomb.status = ROLE_STATUS_DEAD;
					hitBoss(boss, bomb, bomb.mapx,bomb.mapy);
				}
			}
			for(int k=0;k<factory.battery.size();k++){
				MoveObject battery = (MoveObject) factory.battery.elementAt(k);
				if(Collision.checkSquareCollision(battery.mapx, battery.mapy, battery.width, battery.height, bomb.mapx, bomb.mapy, bomb.width, bomb.height)){
					battery.blood -= bomb.damage;
					Exploder exploder = new Exploder(battery.mapx,battery.mapy);
					missileEffects[mIndex] = exploder;
					if(mIndex < missileEffects.length-1){
						mIndex ++;
					}else{
						mIndex=0;
					}
				}
				if(battery.blood<=0){
					battery.status = ROLE_STATUS_DEAD;
					player.scores += battery.scores;
					scores = player.scores;
					/*if(battery.pirze == SPIRITI_PRIZE_YES){
						factory.createProps(battery, level);
					}*/
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
			for(int k=0;k<factory.ghostSpirits.size();k++){
				MoveObject mo = (MoveObject) factory.ghostSpirits.elementAt(k);
				if(Collision.checkSquareToCircularCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					mo.blood -= bomb.damage;
					Exploder exploder = new Exploder(bomb.mapx,bomb.mapy);
					exploders[eIndex] = exploder;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
				if(mo.blood<=0){
					mo.status = ROLE_STATUS_DEAD;
					player.scores += mo.scores;
					scores = player.scores;
				}
			}
		}
	}
	
	private void hitBoss(MoveObject boss, MoveObject bomb, int x, int y){
		boss.blood -= bomb.damage;
		Exploder exploder = new Exploder(x,y);
		exploders[eIndex] = exploder;
		if(eIndex < exploders.length-1){
			eIndex ++;
		}else{
			eIndex=0;
		}
		if(boss.blood<=0){
			boss.status = ROLE_STATUS_DEAD;
			player.scores += boss.scores;
			scores = player.scores;
			for(int l=0;l<2;l++){
				for(int m=0;m<2;m++){
					int mx = boss.mapx+m*(boss.width-105);
					int my = boss.mapy+l*(boss.height-71);
					Exploder exploder2 = new Exploder(mx, my);
					exploders[eIndex] = exploder2;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
			}
		}
		//System.out.println("boss.blood:"+boss.blood);
	}

	
 	private void eatProp(MoveObject mo) {
		switch(mo.id){
		case id_blood:
			if(player.blood+50 < playerParam[player.grade-1][6]){
				player.blood += 50;
			}else{
				player.blood = playerParam[player.grade-1][6];
			}
			blood = player.blood;
			break;
		case id_laser:
			if(factory.lasers.size()<1){
				factory.createLaster(player);
				laserNums = 1;
				factory.missile.removeAllElements();
				player.missileGrade = 0;
				missileGrade = player.missileGrade;
			}/*else{
				MoveObject object = (MoveObject) factory.lasers.elementAt(0);
				if(object.frame>0){
					object.frame--;
					object.damage += 5;
				}
			}*/
			break;
		case id_missile:
			if(player.missileGrade<2){
				player.missileGrade ++;
				missileGrade = player.missileGrade;
				factory.lasers.removeAllElements();
				laserNums = 0;
				//System.out.println("laser.size:"+factory.lasers.size());
				//System.out.println("player.missileGrade:"+player.missileGrade);
			}
			break;
		case id_upgrade:
			if(player.bombGrade<4){
				player.bombGrade++;
				bombGrade = player.bombGrade;
			}
			break;
		case id_ventose:
			break;
		case id_wingplane:
			if(wingplaneNums<wingplaneMaxNums){
				wingplaneNums++;
				player.wingplaneNums = wingplaneNums;
				factory.createWingplane(player);
			}
			break;
		}
	}

	
	/*private void createSpirits(){
		if(!isNextLevel){
			spiritEnd = getTime();
			batteryEnd = getTime();
			if(!level_over){
				if(spiritEnd - spiritStart >= levelInfo[level-1][2]){
					factory.cteateBatchSpirits(level, batchIndex);
					batchIndex = (batchIndex+1)%batchInfo[level-1].length;
					spiritStart = getTime();
				}
				if(batteryEnd - batteryStart >= levelInfo[level-1][3] && level != 1 && level != 2){
					factory.createBattery(level);
					batteryStart = getTime();
				}
			}else{
				if(factory.boss.size()<1 && factory.spirits.size()<1){
					factory.createBoss(level);
					isCeateBoss = true;
				}
			}      
		}
	}
	*/
	
	private void removeOutsideObject() {
		for(int i=0;i<factory.bombs.size();i++){
			MoveObject mo = (MoveObject) factory.bombs.elementAt(i);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.bombs.removeElement(mo);
			}
		}
		
		for(int j=0;j<factory.spirits.size();j++){
			MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.spirits.removeElement(mo);
			}
		}
		
		for(int k=0;k<factory.spiritBombs.size();k++){
			MoveObject mo = (MoveObject) factory.spiritBombs.elementAt(k);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.spiritBombs.removeElement(mo);
			}
		}
		
		for(int l=0;l<factory.boss.size();l++){
			MoveObject mo = (MoveObject) factory.boss.elementAt(l);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.boss.removeElement(mo);
			}
		}
		
		for(int m=0;m<factory.battery.size();m++){
			MoveObject mo = (MoveObject) factory.battery.elementAt(m);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.battery.removeElement(mo);
			}
		}
		
		for(int n=0;n<factory.boss1Skill.size();n++){
			MoveObject mo = (MoveObject) factory.boss1Skill.elementAt(n);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.boss1Skill.removeElement(mo);
			}
		}
		
		for(int n=0;n<factory.props.size();n++){
			MoveObject mo = (MoveObject) factory.props.elementAt(n);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.props.removeElement(mo);
			}
		}
		
		for(int n=0;n<factory.missile.size();n++){
			MoveObject mo = (MoveObject) factory.missile.elementAt(n);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.missile.removeElement(mo);
			}
		}
		for(int i=0;i<factory.wingplane.size();i++){
			MoveObject mo = (MoveObject)factory.wingplane.elementAt(i);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.wingplane.removeElement(mo);
				wingplaneNums--;
			}
		}
		for(int i=0;i<factory.ventose.size();i++){
			MoveObject mo = (MoveObject)factory.ventose.elementAt(i);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.ventose.removeElement(mo);
			}
		}
		for(int i=0;i<factory.ghostSpirits.size();i++){
			MoveObject mo = (MoveObject)factory.ghostSpirits.elementAt(i);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.ghostSpirits.removeElement(mo);
			}
		}
		
		/*for(int n=0;n<factory.boss8Skill.size();n++){
			MoveObject mo = (MoveObject) factory.boss8Skill.elementAt(n);
			if(mo.status == ROLE_STATUS_DEAD){
				factory.boss8Skill.removeElement(mo);
			}
		}*/
		//System.out.println("spirit.size:"+factory.spirits.size());
		//System.out.println("bomb num:"+factory.bombs.size());
		//System.out.println("spiritBombs num:"+factory.spiritBombs.size());
		//System.out.println("boss num:"+factory.boss.size());
		//System.out.println("battery num:"+factory.battery.size());
		//System.out.println("bossSkill num:"+factory.boss1Skill.size());
		//System.out.println("boss8skill:"+factory.boss8Skill.size());
		//System.out.println("props.size:"+factory.props.size());
		//System.out.println("missile.size:"+factory.missile.size());
		//System.out.println("wingplane.size:"+factory.wingplane.size());
		//System.out.println("ventose.size:"+factory.ventose.size());
	}
	
	/*过关之后改变数据*/
	private void changeDatePass(){
		player.status = ROLE_STATUS_ALIVE;
		player.mapx = 0;
		player.blood = playerParam[player.grade-1][6];
		blood = player.blood;
		player.lifeNum ++;
		lifeNum = player.lifeNum;
		//isNextLevel = false;
		level_over = false;
		isCeateBoss = false;
		level_start_time = getTime()/1000;
		level++;
		currLevel = level;
		bgIndex = 0;
		hillIndex = 0;
		wayIndex = 0;
		batchIndex = 0;
		Resource.clearGame();
		factory.removeEnemy();
		for(int i=0;i<exploders.length;i++){
			exploders[i]=null;
		}
		for(int i=0;i<missileEffects.length;i++){
			missileEffects[i]=null;
		}
		for(int i=0;i<scoresEffects.length;i++){
			scoresEffects[i]=null;
		}
	}
	
	private void quitGameDeleteDate(){
		lifeNum = 0;
		scores = 0;
		blood = 0;
		grade = 0;
		bombGrade = 0;
		//wingplaneMaxNums = 1;
		wingplaneNums = 0;
		missileGrade = 0;
		currLevel = level = 1;  
		bgIndex = 0;
		hillIndex = 0;
		wayIndex = 0;
		batchIndex = 0;
		//isNextLevel = false;
		level_over = false;
		isCeateBoss = false;
		Resource.clearGame();
		factory.removeAllObject();
		for(int i=0;i<exploders.length;i++){
			exploders[i]=null;
		}
		for(int i=0;i<missileEffects.length;i++){
			missileEffects[i]=null;
		}
		for(int i=0;i<scoresEffects.length;i++){
			scoresEffects[i]=null;
		}
	}

	public void show(SGraphics g){
		if(!isUserVentose){
			drawGameBg(g);
		}else{
			g.setColor(0);
			g.fillRect(0, 0, ScrW, ScrH);
		}
		objectShow.showBombs(g, factory.bombs, player);
		objectShow.showPlayer(g, player);
		objectShow.showSpiritsBomb(g, factory.spiritBombs);
		objectShow.showSpirits(g, factory.spirits);
		objectShow.showBattery(g, factory.battery, player);
		objectShow.showBossSkill(g, factory.boss1Skill);
		objectShow.showBoss(g, factory.boss, factory);
		objectShow.showGhostSpirits(g, factory.ghostSpirits);
		objectShow.showBoss8Spirit(g, factory.boss8Spirit);
		objectShow.showBoss8Skill2(g, factory.boss8Skill);
		objectShow.showPropsIcon(g, factory.props);
		objectShow.showLasers(g, factory.lasers, player);
		objectShow.showMissile(g, factory.missile, factory);
		objectShow.showWingplane(g, factory.wingplane, player);
		if(game_status == GAME_PLAY || game_status == GAME_PAUSE){
			objectShow.showVentose(g, factory.ventose);
		}
		drawExploders(g);
		drawInfo(g);
		/*if(player.status == ROLE_STATUS_PASS){
			String str = "恭喜你通过本关卡,下一关为第"+(level+1)+"关";
			drawPrompt(g, str);
		}*/
		if(!isUserVentose && player.status == ROLE_STATUS_PROTECTED){
			String str = "无敌时间:"+(3-(player.endTime-player.startTime)/1000)+"秒";
			drawPrompt(g, str);
		}
		ventoseTime2 = getTime();
		if(isVentosePrompt && ventoseTime2-ventoseTime1>2000){
			isVentosePrompt = false;
		}
		if(isVentosePrompt){
			String str = "必杀技已用光,请按9键进入商城购买";
			drawPrompt(g, str);
		}
		if(engine.isDebugMode()){
			String str = "1键:必杀技, 3键:过关, 4键:子弹升级";
			engine.addDebugUserMessage(str);
		}
		
		/*int col = g.getColor();
		g.setColor(0xff0000);
		g.drawString(String.valueOf(player.status), 100, 100, 20);
		g.drawString(String.valueOf(player.blood), 100, 130, 20);
		g.drawString(String.valueOf(player.lifeNum), 100, 130, 20);
		g.setColor(col);*/
	}

	private void drawExploders(SGraphics g) {
		Exploder exploder = null;
		for(int i=0;i<exploders.length;i++){
			if(exploders[i] != null){
				exploder = exploders[i];
				exploder.drawExplode(g, this);
			}
		}
		Exploder exploder1 = null;
		for(int i=0;i<missileEffects.length;i++){
			if(missileEffects[i] != null){
				exploder1 = missileEffects[i];
				exploder1.drawMissileExplode(g, this);
			}
		}
		Exploder exploder3 = null;
		for(int i=0;i<scoresEffects.length;i++){
			if(scoresEffects[i] != null){
				exploder3 = scoresEffects[i];
				exploder3.showScore(engine, g, 1000);
			}
		}
	}

	private void drawGameBg(SGraphics g) {
		/*if(level == 1 || level == 2){
			drawGameBg_sky(g);
		}else if(level == 3 || level == 4){
			drawGameBg_burrow(g);
		}else if(level == 5 || level == 6){
			drawGameBg_ice(g);
		}else if(level == 7 || level == 8){
			drawGameBg_lava(g);
		}*/
		switch (level){
		case 1:
			drawGameBg_sky(g);
			break;
		case 2:
			drawGameBg_sky(g);
			break;
		case 3:
			drawGameBg_burrow(g);
			break;
		case 4:
			drawGameBg_burrow(g);
			break;
		case 5:
			drawGameBg_ice(g);
			break;
		case 6:
			drawGameBg_ice(g);
			break;
		case 7:
			drawGameBg_lava(g);
			break;
		case 8:
			drawGameBg_lava(g);
			break;
				
		}
	}

	private void drawPrompt(SGraphics g, String str){
		Font font = g.getFont();
		int textW = font.stringWidth(str);
		int w = textW+30;
		int h = 30;
		int x = ScrW/2-w/2;
		int y = ScrH/2-h/2;
		g.setColor(0x1e6094);
		DrawUtil.drawRect(g, x, y, w, h);
		x += w/2 - textW/2;
		y += 4;
		g.setColor(0xffffff);
		g.drawString(str, x, y, 20);
	}
	
	private int centerIndex, center2Index, donwIndex, lavaIndex, upIndex;
	private void drawGameBg_lava(SGraphics g) {
		Image center = Resource.loadImage(Resource.id_lava_game_center);
		Image center2 = Resource.loadImage(Resource.id_lava_game_center_2);
		Image down = Resource.loadImage(Resource.id_lava_game_down);
		Image lava = Resource.loadImage(Resource.id_lava_game_lava);
		Image up = Resource.loadImage(Resource.id_lava_game_up);
		
		int centerW = center.getWidth(), centerH = center.getHeight();
		int center2W = center2.getWidth(), center2H = center2.getHeight();
		int lavaW = lava.getWidth(), lavaH = lava.getHeight();
		int downW = down.getWidth(), downH = down.getHeight();
		int upW = up.getWidth(), upH = up.getHeight();
		if(factory.boss.size()>0){
			centerIndex = (centerIndex+4)%centerW;
			center2Index = (center2Index+4)%center2W;
			donwIndex = (donwIndex+4)%downW;
			lavaIndex = (lavaIndex+12)%lavaW;
			upIndex = (upIndex+8)%upW;
		}else{
			centerIndex = (centerIndex+1)%centerW;
			center2Index = (center2Index+1)%center2W;
			donwIndex = (donwIndex+1)%downW;
			lavaIndex = (lavaIndex+3)%lavaW;
			upIndex = (upIndex+2)%upW;
		}
		
		int mapy = 73;
		g.drawRegion(center2, center2Index, 0, center2W-center2Index, center2H, 0, 0, mapy, 20);
		g.drawRegion(center2, 0, 0, center2Index, center2H, 0, center2W-center2Index, mapy, 20);
		mapy = 73+center2H+centerH-25;
		g.drawRegion(lava, lavaIndex, 0, lavaW-lavaIndex, lavaH, 0, 0, mapy, 20);
		g.drawRegion(lava, 0, 0, lavaIndex, lavaH, 0, lavaW-lavaIndex, mapy, 20);
		mapy = 73+center2H-5;
		g.drawRegion(center, centerIndex, 0, centerW-centerIndex, centerH, 0, 0, mapy, 20);
		g.drawRegion(center, 0, 0, centerIndex, centerH, 0, centerW-centerIndex, mapy, 20);
		g.drawRegion(up, upIndex, 0, upW-upIndex, upH, 0, 0, 73, 20);
		g.drawRegion(up, 0, 0, upIndex, upH, 0, upW-upIndex, 73, 20);
		g.drawRegion(down, donwIndex, 0, downW-donwIndex, downH, 0, 0, ScrH-downH, 20);
		g.drawRegion(down, 0, 0, donwIndex, downH, 0, downW-donwIndex, ScrH-downH, 20);
	}

	public void drawGameBg_burrow(SGraphics g){
		Image center = Resource.loadImage(Resource.id_burrow_game_center);
		Image down = Resource.loadImage(Resource.id_burrow_game_down);
		Image up = Resource.loadImage(Resource.id_burrow_game_up);
		Image net = Resource.loadImage(Resource.id_burrow_game_net);
		Image bubble = Resource.loadImage(Resource.id_burrow_game_bubble);
		
		int centerW = center.getWidth(), centerH = center.getHeight();
		int downW = down.getWidth(), downH = down.getHeight();
		int upW = up.getWidth(), upH = up.getHeight();
		int netW = net.getWidth(), netH = net.getHeight();
		int bubbleW = bubble.getWidth(), bubbleH = bubble.getHeight();
		if(factory.boss.size()>0){
			bgIndex = (bgIndex+4)%centerW;
			hillIndex = (hillIndex+8)%upW;
			wayIndex = (wayIndex+12)%downW;
		}else{
			bgIndex = (bgIndex+1)%centerW;
			hillIndex = (hillIndex+2)%upW;
			wayIndex = (wayIndex+3)%downW;
		}
		g.drawRegion(center, bgIndex, 0, centerW-bgIndex, centerH, 0, 0, 73+35, 20);
		g.drawRegion(center, 0, 0, bgIndex, centerH, 0, centerW-bgIndex, 73+35, 20);
		g.drawRegion(up, hillIndex, 0, upW-hillIndex, upH, 0, 0, 73, 20);
		g.drawRegion(up, 0, 0, hillIndex, upH, 0, upW-hillIndex, 73, 20);
		
		if(mapx+netW>0){
			if(factory.boss.size()>0){
				mapx -= 4;
			}else{
				mapx -= 1;
			}
		}else{
			mapx = ScrW;
		}
		g.drawRegion(net, 0, 0, netW, netH, 0, mapx, 360, 20);
		
		if(mapx2+bubbleW>0){
			if(factory.boss.size()>0){
				mapx2 -= 4;
			}else{
				mapx2 -= 1;
			}
		}else{
			mapx2 = ScrW;
		}
		g.drawRegion(bubble, 0, 0, bubbleW, bubbleH, 0, mapx2, 142, 20);
		
		g.drawRegion(down, wayIndex, 0, downW-wayIndex, downH, 0, 0, ScrH-downH, 20);
		g.drawRegion(down, 0, 0, wayIndex, downH, 0, downW-wayIndex, ScrH-downH, 20);
	}
	
	int mapx = 200, mapx2 = 300;
	public void drawGameBg_sky(SGraphics g){
		Image game_bg = Resource.loadImage(Resource.id_sky_game_bg);
		//Image hill = Resource.loadImage(Resource.id_sky_game_hill); 
		Image way = Resource.loadImage(Resource.id_sky_game_way);
		
		int bgW =  game_bg.getWidth(), bgH = game_bg.getHeight();
		//int hillW = hill.getWidth(), hillH = hill.getHeight();
		int wayW = way.getWidth(), wayH = way.getHeight();
		if(factory.boss.size()>0){
			bgIndex = (bgIndex+4)%bgW;
			wayIndex = (wayIndex+12)%wayW;
		}else{
			bgIndex = (bgIndex+1)%bgW;
			wayIndex = (wayIndex+3)%wayW;
		}
		g.drawRegion(game_bg, bgIndex, 0, bgW-bgIndex, bgH, 0, 0, 0, 20);
		g.drawRegion(game_bg, 0, 0, bgIndex, bgH, 0, bgW-bgIndex, 0, 20);
		
		/*if(mapx+hillW>0){
			if(factory.boss.size()>0){
				mapx -= 8;
			}else{
				mapx -= 2;
			}
		}else{
			mapx = ScrW;
		}
		g.drawRegion(hill, 0, 0, hillW, hillH, 0, mapx, 70, 20);*/
		
		g.drawRegion(way, wayIndex, 0, wayW-wayIndex, wayH, 0, 0, ScrH-wayH, 20);
		g.drawRegion(way, 0, 0, wayIndex, wayH, 0, wayW-wayIndex, ScrH-wayH, 20);
	}
	
	private int bgIndex, hillIndex, wayIndex;
	public void drawGameBg_ice(SGraphics g){
		Image game_bg = Resource.loadImage(Resource.id_ice_game_bg);
		Image hill= Resource.loadImage(Resource.id_ice_game_bg_hill);
		Image way = Resource.loadImage(Resource.id_ice_game_bg_way);
		
		int bgW =  game_bg.getWidth(), bgH = game_bg.getHeight();
		int hillW = hill.getWidth(), hillH = hill.getHeight();
		int wayW = way.getWidth(), wayH = way.getHeight();
		/*bgIndex = (bgIndex+1)%bgW;
		hillIndex = (hillIndex+2)%hillW;
		wayIndex = (wayIndex+3)%wayW;*/
		if(factory.boss.size()>0){
			bgIndex = (bgIndex+4)%bgW;
			hillIndex = (hillIndex+8)%hillW;
			wayIndex = (wayIndex+12)%wayW;
		}else{
			bgIndex = (bgIndex+1)%bgW;
			hillIndex = (hillIndex+2)%hillW;
			wayIndex = (wayIndex+3)%wayW;
		}
		g.drawRegion(game_bg, bgIndex, 0, bgW-bgIndex, bgH, 0, 0, 73, 20);
		g.drawRegion(game_bg, 0, 0, bgIndex, bgH, 0, bgW-bgIndex, 73, 20);
		g.drawRegion(hill, hillIndex, 0, hillW-hillIndex, hillH, 0, 0, 283, 20);
		g.drawRegion(hill, 0, 0, hillIndex, hillH, 0, hillW-hillIndex, 283, 20);
		g.drawRegion(way, wayIndex, 0, wayW-wayIndex, wayH, 0, 0, ScrH-wayH, 20);
		g.drawRegion(way, 0, 0, wayIndex, wayH, 0, wayW-wayIndex, ScrH-wayH, 20);
	}
	
	private void drawInfo(SGraphics g){
		//Image infoBg = Resource.loadImage(Resource.id_game_info_bg);
		Image infoHead = Resource.loadImage(Resource.id_game_info_head);
		Image bloodBg = Resource.loadImage(Resource.id_game_blood_bg);
		Image headShadow = Resource.loadImage(Resource.id_game_head_shadow);
		Image bgUp = Resource.loadImage(Resource.id_game_bg_up);
		Image key0 = Resource.loadImage(Resource.id_game_key_0);
		Image key1 = Resource.loadImage(Resource.id_game_key_1);
		Image key9 = Resource.loadImage(Resource.id_game_key_9);
		Image ventose_icon = Resource.loadImage(Resource.id_game_ventose_icon);
		Image totoro = Resource.loadImage(Resource.id_shop_totoro);
		
		int infoBgW = 349, infoBgH = 46;
		//int infoHeadW = infoHead.getWidth(), infoHeadH = infoHead.getHeight();
		int bloodBgW = bloodBg.getWidth(), bloodBgH = bloodBg.getHeight();
		int /*bgUpW = bgUp.getWidth(),*/ bgUpH = bgUp.getHeight();
		int offX = 135, offY = bgUpH/2-infoBgH/2;
		
		g.drawImage(bgUp, 0, 0, 20);
		if(player.scores>999999){
			StateMain.drawNum(g, player.scores, 0, offY+26);
		}else if(player.scores<100){
			StateMain.drawNum(g, player.scores, 35, offY+26);
		}else{
			StateMain.drawNum(g, player.scores, 25, offY+26);
		}
		//g.drawImage(infoBg, offX, offY, 20);
		g.drawImage(headShadow, offX+3, offY, 20);
		g.drawImage(infoHead, offX, offY, 20);
		StateMain.drawNum(g, level, 50, 8);
		StateMain.drawNum(g, player.lifeNum, offX+80, offY+12);
		offX += infoBgW - bloodBgW - 25;
		offY += infoBgH/2 - bloodBgH/2;
		g.drawImage(bloodBg, offX, offY, 20);
		g.setColor(0xffff00);
		if(player.blood>0 && player.status != ROLE_STATUS_DEAD){
			DrawUtil.drawRect(g, offX+4, offY+4, player.blood*(bloodBgW-8)/playerParam[player.id][6], bloodBgH-8);
		}
		g.setColor(0xffffff);
		
		int venW = ventose_icon.getWidth(), venH = ventose_icon.getHeight();
		int key0W = key0.getWidth(), key0H = key0.getHeight();
		int x = 195, y = ScrH-venH-5;
		g.drawImage(ventose_icon, x, y, 20);
		int sx = 0;
		int sy = y+24;
		if(ventoseNum+startGameVentoseNums < 10){
			sx = x+30;
		}else{
			sx = x+33;
		}
		TextView.showSingleLineText(g, String.valueOf(ventoseNum+startGameVentoseNums), sx, sy, 25, 25, 1);
		y = y + venH/2-key0H/2;
		g.drawImage(key1, x+venW+10, y, 20);
		g.drawImage(key9, ScrW-key0W-40-key9.getWidth(), y, 20);
		g.drawImage(key0, ScrW-key0W-10, y, 20);
		
		int totoroW = totoro.getWidth()/2, totoroH = totoro.getHeight();
		int totoroX = 10, totoroY = ScrH-totoroH-5;
		for(int i=0;i<StateGame.wingplaneMaxNums;i++){
			g.drawRegion(totoro, totoroW, 0, totoroW, totoroH, 0, totoroX, totoroY, 20);
			totoroX += totoroW+1;
		}
		for(int i=0;i<4-StateGame.wingplaneMaxNums;i++){
			g.drawRegion(totoro, 0, 0, totoroW, totoroH, 0, totoroX, totoroY, 20);
			totoroX += totoroW+1;
		}
		
		//boss blood
		if(factory.boss.size()>0){
			MoveObject boss = (MoveObject) factory.boss.elementAt(0);
			int bloodW = 500, bloodH = 24;
			int bloodX = ScrW/2-250, bloodY = startP+15;
			DrawUtil.drawRect(0xffffff, g, bloodX, bloodY, bloodW, bloodH);
			bloodX = bloodX+2;
			bloodY = bloodY+2;
			bloodW = bloodW-4;
			bloodH = bloodH-4;
			DrawUtil.drawRect(0x000000, g, bloodX, bloodY, bloodW, bloodH);
			if(boss.blood>0){
				int num = bossParam[level-1][3]/bossParam[0][3];
				if(num == 1){
					DrawUtil.drawRect(0xff0000, g, bloodX, bloodY, boss.blood*(bloodW)/bossParam[0][3], bloodH);
				}else if(num == 2){
					if(boss.blood > bossParam[0][3]){
						DrawUtil.drawRect(0xff0000, g, bloodX, bloodY, bloodW, bloodH);
						DrawUtil.drawRect(0xffec42, g, bloodX, bloodY, (boss.blood-bossParam[0][3])*(bloodW)/bossParam[0][3], bloodH);
					}else {
						DrawUtil.drawRect(0xff0000, g, bloodX, bloodY, boss.blood*(bloodW)/bossParam[0][3], bloodH);
					}
				}else if(num == 3){
					if(boss.blood > bossParam[0][3]*2){
						DrawUtil.drawRect(0xffec42, g, bloodX, bloodY, bloodW, bloodH);
						DrawUtil.drawRect(0x00f540, g, bloodX, bloodY, (boss.blood-bossParam[0][3]*2)*(bloodW)/bossParam[0][3], bloodH);
					}else if(boss.blood>bossParam[0][3] && boss.blood <= bossParam[0][3]*2){
						DrawUtil.drawRect(0xff0000, g, bloodX, bloodY, bloodW, bloodH);
						DrawUtil.drawRect(0xffec42, g, bloodX, bloodY, (boss.blood-bossParam[0][3])*(bloodW)/bossParam[0][3], bloodH);
					}else{
						DrawUtil.drawRect(0xff0000, g, bloodX, bloodY, boss.blood*(bloodW)/bossParam[0][3], bloodH);
					}
				}else if(num == 4){
					if(boss.blood > bossParam[0][3]*3){
						DrawUtil.drawRect(0x00f540, g, bloodX, bloodY, bloodW, bloodH);
						DrawUtil.drawRect(0xf600ff, g, bloodX, bloodY, (boss.blood-bossParam[0][3]*3)*(bloodW)/bossParam[0][3], bloodH);
					}else if(boss.blood>bossParam[0][3]*2 && boss.blood <= bossParam[0][3]*3){
						DrawUtil.drawRect(0xffec42, g, bloodX, bloodY, bloodW, bloodH);
						DrawUtil.drawRect(0x00f540, g, bloodX, bloodY, (boss.blood-bossParam[0][3]*2)*(bloodW)/bossParam[0][3], bloodH);
					}else if(boss.blood>bossParam[0][3] && boss.blood <= bossParam[0][3]*2){
						DrawUtil.drawRect(0xff0000, g, bloodX, bloodY, bloodW, bloodH);
						DrawUtil.drawRect(0xffec42, g, bloodX, bloodY, (boss.blood-bossParam[0][3])*(bloodW)/bossParam[0][3], bloodH);
					}else{
						DrawUtil.drawRect(0xff0000, g, bloodX, bloodY, boss.blood*(bloodW)/bossParam[0][3], bloodH);
					}
				}
			}
		}
	}
	
	private long getTime(){
		return System.currentTimeMillis();
	}
	
	private void move(int towards) {
		switch (towards) {
		case 0: // 往上--主角
			if(player.mapy-startP <= player.speedY){
				player.mapy = startP;
			}else{
				player.mapy -= player.speedY;
			}
			break;
		case 1: // 往下--主角
			if(player.mapy+player.height<490){
				if((490 - (player.mapy+player.height)) < player.speedY){
					player.mapy = 490 - player.height;
				}else{
					player.mapy += player.speedY;
				}
			}
			break;
		case 2: // 往右--主角
			if(ScrW - ((player.mapx+player.width)) < player.speedX){
				player.mapx = ScrW - player.width;
			}else{
				player.mapx += player.speedX;
			}
			break;
		case 3:
			if(player.mapx < player.speedX){
				player.mapx = 0;
			}else{
				player.mapx -= player.speedX;
			}
			break;
		}
	}
}

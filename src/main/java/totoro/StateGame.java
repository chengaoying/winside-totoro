package totoro;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.util.Collision;

public class StateGame implements Common{
	
	private TotoroGameEngine engine;
	public StateGame(TotoroGameEngine engine){
		this.engine = engine;
	}
	
	public static int game_status;
	
	public static long level_start_time;
	public static long level_end_time;
	public static boolean level_over;
	
	private int level = 1;
	public boolean isNextLevel;
	public static boolean isCeateBoss;
	
	public MoveObjectFactory factory;
	public MoveObjectShow objectShow;
	public static MoveObject player;
	public Exploder[] exploders = new Exploder[12];
	public Exploder[] missileEffects = new Exploder[12];
	private int eIndex, mIndex;
	
	private long bombStart, bombEnd;
	private int bombInterval = 400;
	
	private long spiritStart, spiritEnd;
	private long batteryStart, batteryEnd;
	private long reviveStime, reviveEtime;
	
	//存档数据
	public static int lifeNum;
	public static int currLevel;
	public static int scores;
	public static int blood;
	public static int grade;
	public static int bombGrade;
	public static int wingplaneMaxNums = 1;
	public static int wingplaneNums;
	public static int missileGrade;
	public static boolean hasTotoro3;
	public static boolean hasTotoro4;
	
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
				if(player.status == ROLE_STATUS_ALIVE){
					player.status = ROLE_STATUS_PROTECTED;
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM0)){
			game_status = GAME_PAUSE;
		}else if(keyState.containsAndRemove(KeyCode.NUM2)){
			if(player.missileGrade<2){
				player.missileGrade ++;
				missileGrade = player.missileGrade;
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM3)){
			if(engine.isDebugMode()){
				level_over = true;
				for(int i=0;i<factory.boss.size();i++){
					MoveObject mo = (MoveObject) factory.boss.elementAt(i);
					mo.status = ROLE_STATUS_DEAD;
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
				factory.createMissile(player);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM8)){
			if(engine.isDebugMode()){
				//factory.createWingplane(player);
			}
		}
		
	}
	
	public void execute(){
		
		//玩家复活
		if(player.status == ROLE_STATUS_DEAD && player.lifeNum>0){
			reviveEtime = getTime();
			System.out.println("time:"+(reviveEtime-reviveStime));
			if(reviveEtime-reviveStime > 1*1000){
				player = factory.revivePlayer(grade);
			}
		}else if(player.lifeNum <= 0){
			System.out.println("game over");
			game_status = GAME_FAIL;
		}
		
		/*复活后无敌时间*/
		player.endTime = getTime();
		if(player.status == ROLE_STATUS_PROTECTED && player.endTime-player.startTime > 3000){
			player.status = ROLE_STATUS_ALIVE;
		}
		
		judgeNextLevel();
		
		level_end_time = getTime()/1000;
		//System.out.println("time:"+(level_end_time - level_start_time));
		if(level <= 8 && level_end_time - level_start_time > levelInfo[level-1][1]){
			level_over = true;
		}
		
		bombEnd = getTime();
		if(player != null 
			&& player.status != ROLE_STATUS_DEAD 
			&& player.status != ROLE_STATUS_PASS 
			&& bombEnd - bombStart > bombInterval){
			
			//if(factory.lasers.size()<1){
				factory.createBomb(player);
			//}
			if(player.missileGrade>0 && factory.lasers.size()<1){
				factory.createMissile(player);
			}
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
		
		createSpirits();
		
		createSpiritsBombs();
		
		collisionDetection();
		
		removeOutsideObject();
		
		entryGameStatus();
		
	}
	
	private void entryGameStatus() {
		switch (game_status){
		case GAME_PAUSE:	//暂停
			StateSubMenu menu = new StateSubMenu(engine);
			int index = menu.processSubMenu();
			if(index == 1){
				//返回主界面
				engine.saveRecord();
				engine.state = STATUS_MAIN_MENU;
				Resource.clearGame();
				factory.removeAllObject();
			}
			game_status = GAME_PLAY;
			break;
		case GAME_SUCCESS: 	//通关
			drawPassInterface();
			break;	
		case GAME_FAIL:		//失败
			StateGameFail fail = new StateGameFail(engine);
			int i = fail.processGameFail(level);
			if(i == 0){
				//买复活
				if(engine.getEngineService().getBalance() > level*10){
					ServiceWrapper sw = engine.getServiceWrapper();
					sw.expend(level*10, "购买复活");
					player.lifeNum = 3;
					lifeNum = player.lifeNum;
				}else{
					PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
					pc.setText(engine.getEngineService().getExpendAmountUnit()+"不足,是否去充值?");
					int p = pc.popup();
					if(p==0){
						Recharge recharge = new Recharge(engine);
						recharge.recharge();
					}
				}
				game_status = GAME_PLAY;
			}else{
				//退出游戏
				quitGameDeleteDate();
				engine.saveRecord();
				engine.state = STATUS_MAIN_MENU;
				Resource.clearGame();
				factory.removeAllObject();
				game_status = GAME_PLAY;
			}
			break;
		} 
	}

	private void drawPassInterface() {
		StateGameSuccess success = new StateGameSuccess(engine, this);
		success.processGameSuccess();
		engine.state = STATUS_MAIN_MENU;
		Resource.clearGame();
		factory.removeAllObject();
		game_status = GAME_PLAY;
		quitGameDeleteDate();
	}

	private void judgeNextLevel() {
		if(level_over && factory.boss.size()<1 && isCeateBoss == true && player.status != ROLE_STATUS_PASS){
			isNextLevel = true;
			player.status = ROLE_STATUS_PASS;
			player.speedX += 10;
			//factory.removeAllObject();
			if(level >= 8){
				//通关
				game_status = GAME_SUCCESS;
				factory.removeEnemy();
			}
		}
		
		if(player.status == ROLE_STATUS_PASS){
			player.mapx += player.speedX;
			if(player.mapx > ScrW){
				player.speedX -= 10;
				engine.saveRecord();
				changeDatePass();
			}
		}
		
	}

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
		
		/*敌方普通攻击碰撞检测*/
		spiritBombCollision();
		
		/*敌方技能攻击碰撞检测*/
		bossSkillCollision();
		
		/*敌方技能攻击碰撞检测*/
		bossSkill2Collision();
		
		/*boss冲撞撞检测*/
		bossCollision();
		
		/*props*/
		propCollision();
		
		/*激光*/
		laserCollision();
		
		/*导弹*/
		misssileCollision();
		
		if(player.blood <= 0 && player.status == ROLE_STATUS_ALIVE){
			player.status = ROLE_STATUS_DEAD;
			player.lifeNum --;
			lifeNum = player.lifeNum;
			reviveStime = getTime();
			factory.lasers.removeAllElements();
		}
	}
	
	private void propCollision() {
		for(int i=0;i<factory.props.size();i++){
			MoveObject mo = (MoveObject)factory.props.elementAt(i);
			if(Collision.checkSquareCollision(mo.mapx, mo.mapy, mo.width, mo.height, player.mapx, player.mapy, player.width, player.height)
					 && player.status != ROLE_STATUS_DEAD){
				mo.status = ROLE_STATUS_DEAD;
				eatProp(mo);
			}
		}
	}

	private void laserCollision() {
		for(int i=0;i<factory.lasers.size();i++){
			MoveObject laser = (MoveObject) factory.lasers.elementAt(i);
			for(int j=0;j<factory.spirits.size();j++){
				MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
				if((mo.mapy<=laser.mapy+laser.height && mo.mapy+mo.height>=laser.mapy)
						&& laser.mapx<mo.mapx && laser.status2 == ROLE_STATUS2_ATTACK){
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
			for(int k=0;k<factory.boss.size();k++){
				MoveObject boss = (MoveObject) factory.boss.elementAt(k);
				if((boss.mapy<=laser.mapy+laser.height && boss.mapy+boss.height>=laser.mapy)
						&& laser.mapx<boss.mapx && laser.status2 == ROLE_STATUS2_ATTACK){
					boss.blood -= laser.damage;
					//laser.width = boss.mapx+boss.width/2 - laser.mapx;
					Exploder exploder = new Exploder(boss.mapx+20,boss.mapy+boss.height/2);
					exploders[eIndex] = exploder;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
				if(boss.blood<=0){
					boss.status = ROLE_STATUS_DEAD;
					player.scores += boss.scores;
					scores = player.scores;
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
			for(int m=0;m<factory.battery.size();m++){
				MoveObject battery = (MoveObject) factory.battery.elementAt(m);
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
		}
	}

	private void bossCollision() {
		for(int j=0;j<factory.boss.size();j++){
			MoveObject mo = (MoveObject) factory.boss.elementAt(j);
			if(Collision.checkSquareCollision(player.mapx, player.mapy, player.width, player.height, mo.mapx, mo.mapy, mo.width, mo.height)
					 && player.status == ROLE_STATUS_ALIVE){
				hitPlayer(mo);
			}
			for(int m=0;m<factory.wingplane.size();m++){
				MoveObject wing = (MoveObject) factory.wingplane.elementAt(m);
				if(Collision.checkSquareCollision(wing.mapx, wing.mapy, wing.width, wing.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					hitWingplane(wing, mo);
				}
			}
		}
	}

	private void bossSkill2Collision() {
		for(int k=0;k<factory.boss8Skill.size();k++){
			MoveObject mo = (MoveObject) factory.boss8Skill.elementAt(k);
			if(Collision.checkSquareCollision(mo.mapx, mo.mapy, mo.width, mo.height, player.mapx, player.mapy, player.width, player.height)
					 && player.status == ROLE_STATUS_ALIVE){
				bombHitPlayer(mo);
			}
			for(int m=0;m<factory.wingplane.size();m++){
				MoveObject wing = (MoveObject) factory.wingplane.elementAt(m);
				if(Collision.checkSquareCollision(wing.mapx, wing.mapy, wing.width, wing.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					hitWingplane(wing, mo);
				}
			}
		}
	}

	private void bossSkillCollision() {
		for(int k=0;k<factory.boss1Skill.size();k++){
			MoveObject mo = (MoveObject) factory.boss1Skill.elementAt(k);
			if(Collision.checkSquareCollision(mo.mapx, mo.mapy, mo.width, mo.height, player.mapx, player.mapy, player.width, player.height)
					 && player.status == ROLE_STATUS_ALIVE){
				//mo.status = ROLE_STATUS_DEAD;
				bombHitPlayer(mo);
			}
			for(int m=0;m<factory.wingplane.size();m++){
				MoveObject wing = (MoveObject) factory.wingplane.elementAt(m);
				if(Collision.checkSquareCollision(wing.mapx, wing.mapy, wing.width, wing.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					hitWingplane(wing, mo);
				}
			}
		}
	}

	private void spiritBombCollision() {
		for(int k=0;k<factory.spiritBombs.size();k++){
			MoveObject mo = (MoveObject) factory.spiritBombs.elementAt(k);
			if(Collision.checkSquareCollision(mo.mapx, mo.mapy, mo.width, mo.height, player.mapx, player.mapy, player.width, player.height)
					 && player.status == ROLE_STATUS_ALIVE){
				mo.status = ROLE_STATUS_DEAD;
				bombHitPlayer(mo);
			}
			for(int m=0;m<factory.wingplane.size();m++){
				MoveObject wing = (MoveObject) factory.wingplane.elementAt(m);
				if(Collision.checkSquareCollision(wing.mapx, wing.mapy, wing.width, wing.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					hitWingplane(wing, mo);
				}
			}
		}
	}

	private void bombHitPlayer(MoveObject mo) {
		if(player.status != ROLE_STATUS_PROTECTED){
			if(player.blood - mo.damage >0){
				player.blood -= mo.damage;
			}else{
				player.blood = 0;
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
			if(Collision.checkSquareCollision(player.mapx, player.mapy, player.width, player.height, mo.mapx, mo.mapy, mo.width, mo.height)
					 && player.status == ROLE_STATUS_ALIVE){
				hitPlayer(mo);
			}
			for(int k=0;k<factory.wingplane.size();k++){
				MoveObject wing = (MoveObject) factory.wingplane.elementAt(k);
				if(Collision.checkSquareCollision(wing.mapx, wing.mapy, wing.width, wing.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					hitWingplane(wing,mo);
				}
			}
			if(mo.blood <= 0){
				mo.status = ROLE_STATUS_DEAD;
			}
		}
	}

	private void hitWingplane(MoveObject wing, MoveObject mo) {
		if(wing.blood - mo.damage >0){
			wing.blood -= mo.damage;
		}else{
			wing.status = ROLE_STATUS_DEAD;
		}
		mo.blood -= wing.damage;
	}

	private void hitPlayer(MoveObject mo) {
		if(player.status != ROLE_STATUS_PROTECTED){
			if(player.blood - mo.damage >0){
				player.blood -= mo.damage;
			}else{
				player.blood = 0;
			}
			blood = player.blood;
		}
		mo.blood -= player.damage;
	}

	private void playerCollision() {
		for(int i=0;i<factory.bombs.size();i++){
			MoveObject bomb = (MoveObject) factory.bombs.elementAt(i);
			for(int j=0;j<factory.spirits.size();j++){
				MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, mo.mapx, mo.mapy, mo.width, mo.height)){
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
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, boss.mapx+30, boss.mapy, boss.width, boss.height)){
					if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					}
					boss.blood -= bomb.damage;
					Exploder exploder = new Exploder(bomb.mapx,bomb.mapy);
					exploders[eIndex] = exploder;
					if(eIndex < exploders.length-1){
						eIndex ++;
					}else{
						eIndex=0;
					}
				}
				if(boss.blood<=0){
					boss.status = ROLE_STATUS_DEAD;
					player.scores += boss.scores;
					scores = player.scores;
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
			for(int k=0;k<factory.battery.size();k++){
				MoveObject battery = (MoveObject) factory.battery.elementAt(k);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, battery.mapx, battery.mapy, battery.width, battery.height)){
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
		}
	}

	private void misssileCollision() {
		for(int i=0;i<factory.missile.size();i++){
			MoveObject bomb = (MoveObject) factory.missile.elementAt(i);
			for(int j=0;j<factory.spirits.size();j++){
				MoveObject mo = (MoveObject) factory.spirits.elementAt(j);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, mo.mapx, mo.mapy, mo.width, mo.height)){
					if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					}
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
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, boss.mapx+30, boss.mapy, boss.width, boss.height)){
					if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					}
					boss.blood -= bomb.damage;
					Exploder exploder = new Exploder(bomb.mapx,bomb.mapy);
					missileEffects[mIndex] = exploder;
					if(mIndex < missileEffects.length-1){
						mIndex ++;
					}else{
						mIndex=0;
					}
				}
				if(boss.blood<=0){
					boss.status = ROLE_STATUS_DEAD;
					player.scores += boss.scores;
					scores = player.scores;
				}
				//System.out.println("boss.blood:"+boss.blood);
			}
			for(int k=0;k<factory.battery.size();k++){
				MoveObject battery = (MoveObject) factory.battery.elementAt(k);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, battery.mapx, battery.mapy, battery.width, battery.height)){
					if(player.grade!=TOTORO_GRADE_THREE && player.grade!=TOTORO_GRADE_FOUR){
						bomb.status = ROLE_STATUS_DEAD;
					}
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
		}
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
				factory.missile.removeAllElements();
				player.missileGrade = 0;
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
				System.out.println("laser.size:"+factory.lasers.size());
				System.out.println("player.missileGrade:"+player.missileGrade);
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

	private void createSpirits(){
		if(!isNextLevel){
			spiritEnd = System.currentTimeMillis();
			batteryEnd = System.currentTimeMillis();
			if(!level_over){
				if(spiritEnd - spiritStart >= levelInfo[level-1][2]){
					factory.cteateBatchSpirits(level);
					spiritStart = System.currentTimeMillis();
				}
				if(batteryEnd - batteryStart >= levelInfo[level-1][3] && level != 3 && level != 4){
					factory.createBattery(level);
					batteryStart = System.currentTimeMillis();
				}
			}else{
				if(factory.boss.size()<1){
					factory.createBoss(level);
					isCeateBoss = true;
				}
			}      
		}
	}
	
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
	}
	
	/*过关之后改变数据*/
	private void changeDatePass(){
		player.status = ROLE_STATUS_ALIVE;
		player.mapx = 0;
		player.blood = playerParam[player.grade-1][6];
		player.lifeNum ++;
		isNextLevel = false;
		level_over = false;
		isCeateBoss = false;
		level_start_time = getTime()/1000;
		level++;
		currLevel = level;
		bgIndex = 0;
		hillIndex = 0;
		wayIndex = 0;
		Resource.clearGame();
		factory.removeEnemy();
		for(int i=0;i<exploders.length;i++){
			exploders[i]=null;
		}
		for(int i=0;i<missileEffects.length;i++){
			missileEffects[i]=null;
		}
	}
	
	private void quitGameDeleteDate(){
		currLevel = level;
		bgIndex = 0;
		hillIndex = 0;
		wayIndex = 0;
		Resource.clearGame();
		factory.removeAllObject();
		for(int i=0;i<exploders.length;i++){
			exploders[i]=null;
		}
		for(int i=0;i<missileEffects.length;i++){
			missileEffects[i]=null;
		}
	}

	public void show(SGraphics g){
		drawGameBg(g);
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
		objectShow.showMissile(g, factory.missile);
		objectShow.showWingplane(g, factory.wingplane, player);
		drawExploders(g);
		drawInfo(g);
		if(isNextLevel){
			String str = "恭喜你通过本关卡,下一关为第"+(level+1)+"关";
			drawPrompt(g, str);
		}
		if(player.status == ROLE_STATUS_PROTECTED){
			String str = "无敌时间:"+(3-(player.endTime-player.startTime)/1000)+"秒";
			drawPrompt(g, str);
		}
		if(engine.isDebugMode()){
			String str = "1键:无敌, 3键:过关, 4键:子弹升级";
			engine.addDebugUserMessage(str);
		}
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
	}

	private void drawGameBg(SGraphics g) {
		if(level == 1 || level == 2){
			drawGameBg_sky(g);
		}else if(level == 3 || level == 4){
			drawGameBg_burrow(g);
		}else if(level == 5 || level == 6){
			drawGameBg_ice(g);
		}else if(level == 7 || level == 8){
			drawGameBg_lava(g);
		}
	}

	private void drawPrompt(SGraphics g, String str){
		Font font = g.getFont();
		int textW = font.stringWidth(str);
		int w = textW+30;
		int h = 30;
		int x = ScrW/2-w/2;
		int y = ScrH/2-h/2;
		g.setColor(0x000000);
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
		centerIndex = (centerIndex+1)%centerW;
		center2Index = (center2Index+1)%center2W;
		donwIndex = (donwIndex+1)%downW;
		lavaIndex = (lavaIndex+3)%lavaW;
		upIndex = (upIndex+2)%upW;
		
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
		bgIndex = (bgIndex+1)%centerW;
		hillIndex = (hillIndex+2)%upW;
		wayIndex = (wayIndex+3)%downW;
		g.drawRegion(center, bgIndex, 0, centerW-bgIndex, centerH, 0, 0, 73+35, 20);
		g.drawRegion(center, 0, 0, bgIndex, centerH, 0, centerW-bgIndex, 73+35, 20);
		g.drawRegion(up, hillIndex, 0, upW-hillIndex, upH, 0, 0, 73, 20);
		g.drawRegion(up, 0, 0, hillIndex, upH, 0, upW-hillIndex, 73, 20);
		
		if(mapx+netW>0){
			mapx -= 1;
		}else{
			mapx = ScrW;
		}
		g.drawRegion(net, 0, 0, netW, netH, 0, mapx, 360, 20);
		
		if(mapx2+bubbleW>0){
			mapx2 -= 1;
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
		Image hill = Resource.loadImage(Resource.id_sky_game_hill); 
		Image way = Resource.loadImage(Resource.id_sky_game_way);
		
		int bgW =  game_bg.getWidth(), bgH = game_bg.getHeight();
		int hillW = hill.getWidth(), hillH = hill.getHeight();
		int wayW = way.getWidth(), wayH = way.getHeight();
		bgIndex = (bgIndex+1)%bgW;
		wayIndex = (wayIndex+3)%wayW;
		g.drawRegion(game_bg, bgIndex, 0, bgW-bgIndex, bgH, 0, 0, 0, 20);
		g.drawRegion(game_bg, 0, 0, bgIndex, bgH, 0, bgW-bgIndex, 0, 20);
		
		if(mapx+hillW>0){
			mapx -= 2;
		}else{
			mapx = ScrW;
		}
		g.drawRegion(hill, 0, 0, hillW, hillH, 0, mapx, 70, 20);
		
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
		bgIndex = (bgIndex+1)%bgW;
		hillIndex = (hillIndex+2)%hillW;
		wayIndex = (wayIndex+3)%wayW;
		g.drawRegion(game_bg, bgIndex, 0, bgW-bgIndex, bgH, 0, 0, 73, 20);
		g.drawRegion(game_bg, 0, 0, bgIndex, bgH, 0, bgW-bgIndex, 73, 20);
		g.drawRegion(hill, hillIndex, 0, hillW-hillIndex, hillH, 0, 0, 283, 20);
		g.drawRegion(hill, 0, 0, hillIndex, hillH, 0, hillW-hillIndex, 283, 20);
		g.drawRegion(way, wayIndex, 0, wayW-wayIndex, wayH, 0, 0, ScrH-wayH, 20);
		g.drawRegion(way, 0, 0, wayIndex, wayH, 0, wayW-wayIndex, ScrH-wayH, 20);
	}
	
	private void drawInfo(SGraphics g){
		Image infoBg = Resource.loadImage(Resource.id_game_info_bg);
		Image infoHead = Resource.loadImage(Resource.id_game_info_head);
		Image bloodBg = Resource.loadImage(Resource.id_game_blood_bg);
		Image headShadow = Resource.loadImage(Resource.id_game_head_shadow);
		Image bgUp = Resource.loadImage(Resource.id_game_bg_up);
		
		int infoBgW = infoBg.getWidth(), infoBgH = infoBg.getHeight();
		//int infoHeadW = infoHead.getWidth(), infoHeadH = infoHead.getHeight();
		int bloodBgW = bloodBg.getWidth(), bloodBgH = bloodBg.getHeight();
		int /*bgUpW = bgUp.getWidth(),*/ bgUpH = bgUp.getHeight();
		int offX = 120, offY = bgUpH/2-infoBgH/2;
		
		g.drawImage(bgUp, 0, 0, 20);
		StateMain.drawNum(g, scores, 25, offY+10);
		g.drawImage(infoBg, offX, offY, 20);
		g.drawImage(headShadow, offX+3, offY, 20);
		g.drawImage(infoHead, offX, offY, 20);
		StateMain.drawNum(g, level, offX+56, offY+15);
		StateMain.drawNum(g, lifeNum, offX+80, offY+15);
		offX += infoBgW - bloodBgW - 25;
		offY += infoBgH/2 - bloodBgH/2;
		g.drawImage(bloodBg, offX, offY, 20);
		g.setColor(0xffff00);
		DrawUtil.drawRect(g, offX+4, offY+4, player.blood*(bloodBgW-8)/playerParam[player.id][6], bloodBgH-8);
		g.setColor(0xffffff);
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

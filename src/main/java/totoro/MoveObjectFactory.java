package totoro;

import java.util.Vector;

import cn.ohyeah.stb.util.RandomValue;

/**
 * 生产对象的工厂
 * @author jackey
 *
 */
public class MoveObjectFactory implements Common{

	private MoveObjectFactory(){}
	private static MoveObjectFactory instance;
	public static MoveObjectFactory getInstance(){   
		if(instance==null){
			instance = new MoveObjectFactory();
		}
		return instance;
	}
	
	/*玩家普通攻击*/
	public Vector bombs = new Vector();
	
	/*敌方普通攻击*/
	public Vector spiritBombs = new Vector();

	/*精灵*/
	public Vector spirits = new Vector();
	
	/*地面上的怪(炮台)*/
	public Vector battery = new Vector();
	
	/*boss*/
	public Vector boss = new Vector();
	
	/*boss skill*/
	public Vector bossSkill = new Vector();
	
	
	/*创建一批精灵*/
	public void cteateBatchSpirits(int level){
		int ran = RandomValue.getRandInt(batchInfo[level-1].length);
		int count = batchInfo[level-1][ran][1];
		int spiritId = batchInfo[level-1][ran][0];
		int value = RandomValue.getRandInt(10);
		for(int i=0;i<count;i++){
			MoveObject mo = new MoveObject();
			mo.status = ROLE_STATUS_ALIVE;
			mo.status2 = ROLE_STATUS2_MOVE;
			mo.id = spiritId;
			mo.directionValue = value;
			mo.position = batchInfo[level-1][ran][2];
			mo.direction = batchInfo[level-1][ran][3];
			mo.width = spiritParam[mo.id-spirit_id][1];
			mo.height = spiritParam[mo.id-spirit_id][2];
			mo.blood = spiritParam[mo.id-spirit_id][3];
			mo.scores = spiritParam[mo.id-spirit_id][4];
			mo.speedX = spiritParam[mo.id-spirit_id][5];
			mo.speedY = spiritParam[mo.id-spirit_id][6];
			mo.attackPermission = spiritParam[mo.id-spirit_id][10];
			mo.picId = spiritParam[mo.id-spirit_id][11];
			mo.pirze = spiritParam[mo.id-spirit_id][12];
			mo.timeInterval = spiritParam[mo.id-spirit_id][13];
			mo.frameNum = spiritParam[mo.id-spirit_id][14];
			mo.damage = spiritParam[mo.id-spirit_id][15];
			mo.bombInterval = spiritParam[mo.id-spirit_id][16];
			mo.startTime = System.currentTimeMillis();
			if(mo.pirze == SPIRITI_PRIZE_YES){
				int r = RandomValue.getRandInt(100);
				if(r>40){
					mo.pirze = SPIRITI_PRIZE_NO;
				}
			}
			spiritPosition(mo, i, count);
			spirits.addElement(mo);
		}
		//System.out.println("spirits.size:"+spirits.size());
	}
	
	/*创建一个精灵*/
	public void createSpirit(int level){}
	
	/*精灵初始坐标*/
	private void spiritPosition(MoveObject mo, int i, int count){
		switch (mo.position){
		case OBJECT_POSITION_UP:
			if(mo.direction == OBJECT_DIRECTION_LEFT_DOWN){
				mo.mapx = 400 + (i*(mo.width+10));
				mo.mapy = -mo.height-i*(mo.height>>2);
			}else{
				mo.mapx = 200 - (i*(mo.width+10));
				mo.mapy = -mo.height - i*(mo.height>>2);
			}
			break;
		case OBJECT_POSITION_DOWN:
			if(mo.direction == OBJECT_DIRECTION_RIGHT_UP){
				mo.mapx = 200 - (i*(mo.width+10));
				mo.mapy = ScrH + (i*(mo.height>>2));
			}else{
				mo.mapx = 400 + (i*(mo.width+10));
				mo.mapy = ScrH + (i*(mo.height>>2));
			}
			break;
		case OBJECT_POSITION_LEFT:
			if(count<4){
				if(mo.direction == OBJECT_DIRECTION_RIGHT){
					mo.mapx = -mo.width - (i*(mo.width+10));
					mo.mapy = RandomValue.getRandInt(50, 400);
				}
			}else{
				if(i<5){
					if(mo.direction == OBJECT_DIRECTION_RIGHT){
						mo.mapx = -mo.width - (i*(mo.width+10));
						mo.mapy = 115;
					}
				}else{
					if(mo.direction == OBJECT_DIRECTION_RIGHT){
						mo.mapx = -mo.width - ((i-5)*(mo.width+10));
						mo.mapy = 375;
					}
				}
			}
			break;
		case OBJECT_POSITION_RIGHT:
			if(count < 4){
				if(mo.direction == OBJECT_DIRECTION_LEFT){
					mo.mapx = ScrW + (i*(mo.width+10));
					mo.mapy = RandomValue.getRandInt(50, 400);
				}
			}else{
				if(i<5){
					if(mo.direction == OBJECT_DIRECTION_LEFT){
						mo.mapx = ScrW + (i*(mo.width+10));
						mo.mapy = 115;
					}
				}else{
					if(mo.direction == OBJECT_DIRECTION_LEFT){
						mo.mapx = ScrW + ((i-5)*(mo.width+10));
						mo.mapy = 375;
					}
				}
			}
			break;
		}
	}
	
	/*敌方普通攻击*/
	public void createSpiritBomb(MoveObject object){
		MoveObject mo = new MoveObject();
		int index = searchObjectIdIndex(object.id);
		mo.status = ROLE_STATUS_ALIVE;
		mo.status2 = ROLE_STATUS2_MOVE;
		mo.objectId = spiritBombParam[index][0];
		mo.id = spiritBombParam[index][1];
		mo.width = spiritBombParam[index][2];
		mo.height = spiritBombParam[index][3];
		mo.damage = spiritBombParam[index][4];
		mo.speedX = -spiritBombParam[index][5];
		mo.speedY = 0/*spiritBombParam[index][6]*/;
		mo.picId = spiritBombParam[index][7];
		mo.mapx = object.mapx+3;
		mo.mapy = object.mapy+object.height/2 - mo.height/2;
		mo.bombSTime = System.currentTimeMillis();
		spiritBombs.addElement(mo);
		//System.out.println("spiritBombs.size:"+spiritBombs.size());
	}
	
	/*创建炮台*/
	public void createBattery(int level){
		MoveObject mo = new MoveObject();
		mo.status = ROLE_STATUS_ALIVE;
		mo.status2 = ROLE_STATUS2_MOVE;
		mo.id = batteryParam[level-1][0];
		mo.width = batteryParam[level-1][1];
		mo.height = batteryParam[level-1][2];
		mo.blood = batteryParam[level-1][3];
		mo.scores = batteryParam[level-1][4];
		mo.speedX = batteryParam[level-1][5];
		mo.speedY = batteryParam[level-1][6];
		mo.mapx = batteryParam[level-1][7];
		mo.mapy = batteryParam[level-1][8];
		mo.position = batteryParam[level-1][9];
		mo.attackPermission = batteryParam[level-1][10];
		mo.picId = batteryParam[level-1][11];
		mo.frameNum = batteryParam[level-1][12];
		mo.damage = batteryParam[level-1][13];
		mo.bombInterval = batteryParam[level-1][14];
		mo.timeInterval = batteryParam[level-1][15];
		mo.pirze = batteryParam[level-1][15];
		if(mo.pirze == SPIRITI_PRIZE_YES){
			int ran = RandomValue.getRandInt(100);
			if(ran>10){
				mo.pirze = SPIRITI_PRIZE_NO;
			}
		}
		//batteryOtherInfo(mo, level);
		mo.bombSTime = System.currentTimeMillis();
		mo.startTime = System.currentTimeMillis();
		
		battery.addElement(mo);
		//System.out.println("battery.size:"+battery.size());
	}
	
	private void batteryOtherInfo(MoveObject mo, int level) {
		switch (mo.id){
		case 300:
			mo.mapx = batteryParam[level-1][7];
			mo.mapy = gameH-mo.width/*batteryParam[level-1][8]*/;
			break;
		case 301:
			break;
		case 302:
			break;
		case 303:
			break;
		default:
			break;
				
		}
	}

	/*炮台普通攻击*/
	public void createBatteryBombs(MoveObject object, MoveObject player){
		MoveObject mo = new MoveObject();
		int index = searchObjectIdIndex(object.id);
		mo.status = ROLE_STATUS_ALIVE;
		mo.status2 = ROLE_STATUS2_MOVE;
		mo.objectId = spiritBombParam[index][0];
		mo.id = spiritBombParam[index][1];
		mo.width = spiritBombParam[index][2];
		mo.height = spiritBombParam[index][3];
		mo.damage = spiritBombParam[index][4];
		//mo.speedX = batteryBombParam[index][5];
		//mo.speedY = batteryBombParam[index][6];
		mo.picId = spiritBombParam[index][7];
		//mo.mapx = object.mapx+3;
		//mo.mapy = object.mapy+object.height/2 - mo.height/2;
		mo.bombSTime = System.currentTimeMillis();
		batteryBombOtherInfo(index, mo, object, player);
		spiritBombs.addElement(mo);
		//System.out.println("spiritBombs:"+spiritBombs.size());
	}
	
	private void batteryBombOtherInfo(int index, MoveObject mo,MoveObject object, MoveObject player) {
		if(object.id == 300){
			if(object.mapx > (player.mapx+player.width)){
				mo.frame = 0;
				mo.mapx = object.mapx+10;
				mo.mapy = object.mapy+20;
				mo.speedX = -spiritBombParam[index][5];
				mo.speedY = -spiritBombParam[index][6];
			}else if(object.mapx >= player.mapx && object.mapx <= (player.mapx+player.width)){
				mo.frame = 1;
				mo.mapx = object.mapx+object.width/2-mo.width/2;
				mo.mapy = object.mapy+10;
				mo.speedX = 0/*batteryBombParam[index][5]*/;
				mo.speedY = -(spiritBombParam[index][6]*2);
			}else {
				mo.frame = 2;
				mo.mapx = object.mapx+45;
				mo.mapy = object.mapy+20;
				mo.speedX = spiritBombParam[index][5];
				mo.speedY = -spiritBombParam[index][6];
			}
		}else if(object.id == 301){
			mo.frame = 0;
			mo.mapx = object.mapx+45;
			mo.mapy = object.mapy;
			mo.speedX = -spiritBombParam[index][5];
			mo.speedY = -spiritBombParam[index][6];
		}else if(object.id == 302){
			mo.frame = 0;
			mo.mapx = object.mapx+45;
			mo.mapy = object.mapy;
			mo.speedX = -spiritBombParam[index][5];
			mo.speedY = -spiritBombParam[index][6];
		}
	}

	private int searchObjectIdIndex(int id){
		for(int i=0;i<spiritBombParam.length;i++){
			if(id == spiritBombParam[i][0]){
				return i;
			}
		}
		return -1;
	}
	
	/*创建boss*/
	public void createBoss(int level){
		MoveObject mo = new MoveObject();
		mo.status = ROLE_STATUS_ALIVE;
		mo.status2 = ROLE_STATUS2_MOVE;
		mo.id = bossParam[level-1][0];
		mo.width = bossParam[level-1][1];
		mo.height = bossParam[level-1][2];
		mo.blood = bossParam[level-1][3];
		mo.scores = bossParam[level-1][4];
		mo.speedX = bossParam[level-1][5];
		mo.speedY = bossParam[level-1][6];
		mo.picId = bossParam[level-1][9];
		mo.timeInterval = bossParam[level-1][10];
		mo.frameNum = bossParam[level-1][11];
		mo.damage = bossParam[level-1][12];
		//mo.bombInterval = bossParam[level-1][13];
		mo.skill1Interval = bossParam[level-1][13];
		mo.skill2Interval = bossParam[level-1][14];
		mo.direction = bossParam[level-1][15];
		mo.position = bossParam[level-1][16];
		mo.skill1Damage = bossParam[level-1][17];
		mo.skill2Damage = bossParam[level-1][18];
		mo.bombSTime = System.currentTimeMillis()/1000;
		mo.startTime = System.currentTimeMillis()/1000;
		mo.skill1STime = System.currentTimeMillis()/1000;
		mo.skill2STime = System.currentTimeMillis()/1000;
		mo.mapx = ScrW;
		mo.mapy = ScrH/2 - mo.height/2;
		//System.out.println("---create boss---");
		boss.addElement(mo);
	}
	
	public void createBossSkill(MoveObject boss){
		int index = searchBossSkillIndex(boss.id);
		int num = bossSkillParam[index][10];
		for(int i=0;i<num;i++){
			MoveObject mo = new MoveObject();
			mo.objectId = bossSkillParam[index][0];
			mo.width = bossSkillParam[index][1];
			mo.height = bossSkillParam[index][2];
			mo.damage = bossSkillParam[index][3];
			//mo.mapx = boss.mapx;
			//mo.mapy = boss.mapy+boss.height/2-mo.width/2;
			mo.picId = bossSkillParam[index][6];
			//mo.speedX = bossSkillParam[index][7];
			//mo.speedY = bossSkillParam[index][8];
			mo.frameNum = bossSkillParam[index][9];
			setBossSkillInfo(boss, mo, index, i, num);
			bossSkill.addElement(mo);
		}
	}
	
	private void setBossSkillInfo(MoveObject boss, MoveObject mo, int index, int i, int num) {
		if(num<1){
			mo.mapx = boss.mapx;
			mo.mapy = boss.mapy+boss.height/2-mo.width/2;
			mo.speedX = bossSkillParam[index][7];
			mo.speedY = bossSkillParam[index][8];
		}else{
			mo.mapx = boss.mapx+i*20;
			mo.mapy = boss.mapy+boss.height/2-mo.width/2;
			mo.speedX = bossSkillParam[index][7]-i*10;
			mo.speedY = bossSkillParam[index][8];
			mo.frameIndex = i;
		}
	}

	private int searchBossSkillIndex(int id){
		for(int i=0;i<bossSkillParam.length;i++){
			if(id == bossSkillParam[i][0]){
				return i;
			}
		}
		return -1;
	}
	
	/*创建玩家普通攻击*/
	public void createBomb(MoveObject player){
		setBombINfo(player);
	}

	private void setBombINfo(MoveObject player) {
		switch(player.grade){
		case TOTORO_GRADE_ONE:
			setInfo1(player);
			break;
		case TOTORO_GRADE_TWO:
			int count = player.bombGrade;
			for(int i=0;i<count;i++){
				MoveObject object = new MoveObject();
				object.status = ROLE_STATUS_ALIVE;
				object.id = bombParam[player.grade-1][player.bombGrade-1][0];
				object.width = bombParam[player.grade-1][player.bombGrade-1][1];
				object.height = bombParam[player.grade-1][player.bombGrade-1][2];
				object.damage = bombParam[player.grade-1][player.bombGrade-1][3];
				object.picId = bombParam[player.grade-1][player.bombGrade-1][6];
				object.grade = player.bombGrade;
				setInfo2(player, object, i);
				bombs.addElement(object);
			}
			break;
		case TOTORO_GRADE_THREE:
			MoveObject object = new MoveObject();
			object.status = ROLE_STATUS_ALIVE;
			object.id = bombParam[player.grade-1][player.bombGrade-1][0];
			object.width = bombParam[player.grade-1][player.bombGrade-1][1];
			object.height = bombParam[player.grade-1][player.bombGrade-1][2];
			object.damage = bombParam[player.grade-1][player.bombGrade-1][3];
			object.picId = bombParam[player.grade-1][player.bombGrade-1][6];
			object.grade = player.bombGrade;
			object.speedX = bombParam[player.grade-1][player.bombGrade-1][4];
			object.speedY = 0;
			object.mapx = player.mapx+player.width;
			object.mapy = player.mapy+player.height/2-object.height/2;
			object.frameIndex = 0;
			//setInfo3(player);
			bombs.addElement(object);
			break;
		case TOTORO_GRADE_FOUR:
			setInfo4(player);
			break;
		}
	}
	
	

	private void setInfo4(MoveObject player) {
		switch(player.bombGrade){
		case TOTORO_BOMB_GRADE_ONE:
			MoveObject object = new MoveObject();
			object.status = ROLE_STATUS_ALIVE;
			object.id = bombParam[player.grade-1][player.bombGrade-1][0];
			object.width = bombParam[player.grade-1][player.bombGrade-1][1];
			object.height = bombParam[player.grade-1][player.bombGrade-1][2];
			object.damage = bombParam[player.grade-1][player.bombGrade-1][3];
			object.picId = bombParam[player.grade-1][player.bombGrade-1][6];
			object.grade = player.bombGrade;
			object.speedX = bombParam[player.grade-1][player.bombGrade-1][4];
			object.speedY = 0;
			object.mapx = player.mapx+player.width;
			object.mapy = player.mapy+player.height/2-object.height/2;
			object.frameIndex = 0;
			bombs.addElement(object);
			break;
		case TOTORO_BOMB_GRADE_TWO:
			for(int i=0;i<2;i++){
				MoveObject object2 = new MoveObject();
				object2.status = ROLE_STATUS_ALIVE;
				object2.id = bombParam[player.grade-1][player.bombGrade-1][0];
				object2.width = bombParam[player.grade-1][player.bombGrade-1][1];
				object2.height = bombParam[player.grade-1][player.bombGrade-1][2];
				object2.damage = bombParam[player.grade-1][player.bombGrade-1][3];
				object2.picId = bombParam[player.grade-1][player.bombGrade-1][6];
				object2.grade = player.bombGrade;
				object2.speedX = bombParam[player.grade-1][player.bombGrade-1][4];
				object2.speedY = 0;
				object2.mapx = player.mapx+player.width;
				object2.mapy = player.mapy+i*(object2.height+5);
				object2.frameIndex = 0;
				bombs.addElement(object2);
			}
			break;
		case TOTORO_BOMB_GRADE_THREE:
			for(int i=0;i<3;i++){
				MoveObject object2 = new MoveObject();
				object2.status = ROLE_STATUS_ALIVE;
				object2.id = bombParam[player.grade-1][player.bombGrade-2][0];
				object2.width = bombParam[player.grade-1][player.bombGrade-2][1];
				object2.height = bombParam[player.grade-1][player.bombGrade-2][2];
				object2.damage = bombParam[player.grade-1][player.bombGrade-2][3];
				object2.picId = bombParam[player.grade-1][player.bombGrade-2][6];
				object2.grade = player.bombGrade;
				object2.speedX = bombParam[player.grade-1][player.bombGrade-2][4];
				object2.speedY = 0;
				if(i==1){
					object2.mapx = player.mapx+player.width+30;
				}else{
					object2.mapx = player.mapx+player.width;
				}
				object2.mapy = player.mapy+i*(object2.height+5)-15;
				object2.frameIndex = 0;
				bombs.addElement(object2);
			}
			break;
		case TOTORO_BOMB_GRADE_FOUR:
			MoveObject object2 = new MoveObject();
			object2.status = ROLE_STATUS_ALIVE;
			object2.id = bombParam[player.grade-1][player.bombGrade-2][0];
			object2.width = bombParam[player.grade-1][player.bombGrade-2][1];
			object2.height = bombParam[player.grade-1][player.bombGrade-2][2];
			object2.damage = bombParam[player.grade-1][player.bombGrade-2][3];
			object2.picId = bombParam[player.grade-1][player.bombGrade-2][6];
			object2.grade = player.bombGrade;
			object2.speedX = bombParam[player.grade-1][player.bombGrade-2][4];
			object2.speedY = 0;
			object2.mapx = player.mapx+player.width;
			object2.mapy = player.mapy+player.height/2-object2.height/2;
			object2.frameIndex = 0;
			bombs.addElement(object2);
			System.out.println("bombSize:"+bombs.size());
			break;
		}
	}

	/*private void setInfo3(MoveObject player) {
		switch(player.bombGrade){
		case TOTORO_BOMB_GRADE_ONE:
			break;
		case TOTORO_BOMB_GRADE_TWO:
			break;
		case TOTORO_BOMB_GRADE_THREE:
			break;
		case TOTORO_BOMB_GRADE_FOUR:
			break;
		}
	}*/

	private void setInfo1(MoveObject player) {
		switch(player.bombGrade){
		case TOTORO_BOMB_GRADE_ONE:
			MoveObject object = new MoveObject();
			object.status = ROLE_STATUS_ALIVE;
			object.id = bombParam[player.grade-1][player.bombGrade-1][0];
			object.width = bombParam[player.grade-1][player.bombGrade-1][1];
			object.height = bombParam[player.grade-1][player.bombGrade-1][2];
			object.damage = bombParam[player.grade-1][player.bombGrade-1][3];
			object.picId = bombParam[player.grade-1][player.bombGrade-1][6];
			object.grade = player.bombGrade;
			object.speedX = bombParam[player.grade-1][player.bombGrade-1][4];
			object.speedY = 0;
			object.mapx = player.mapx+player.width;
			object.mapy = player.mapy+player.height/2-object.height/2;
			object.frameIndex = 0;
			bombs.addElement(object);
			break;
		case TOTORO_BOMB_GRADE_TWO:
			for(int i=0;i<2;i++){
				MoveObject object2 = new MoveObject();
				object2.status = ROLE_STATUS_ALIVE;
				object2.id = bombParam[player.grade-1][player.bombGrade-1][0];
				object2.width = bombParam[player.grade-1][player.bombGrade-1][1];
				object2.height = bombParam[player.grade-1][player.bombGrade-1][2];
				object2.damage = bombParam[player.grade-1][player.bombGrade-1][3];
				object2.picId = bombParam[player.grade-1][player.bombGrade-1][6];
				object2.grade = player.bombGrade;
				object2.speedX = bombParam[player.grade-1][player.bombGrade-1][4];
				object2.speedY = 0;
				object2.mapx = player.mapx+player.width;
				object2.mapy = player.mapy+i*(object2.height+5)+20;
				object2.frameIndex = 0;
				bombs.addElement(object2);
			}
			break;
		case TOTORO_BOMB_GRADE_THREE:
			for(int i=0;i<3;i++){
				MoveObject object2 = new MoveObject();
				object2.status = ROLE_STATUS_ALIVE;
				object2.id = bombParam[player.grade-1][player.bombGrade-2][0];
				object2.width = bombParam[player.grade-1][player.bombGrade-2][1];
				object2.height = bombParam[player.grade-1][player.bombGrade-2][2];
				object2.damage = bombParam[player.grade-1][player.bombGrade-2][3];
				object2.picId = bombParam[player.grade-1][player.bombGrade-2][6];
				object2.grade = player.bombGrade;
				object2.speedX = bombParam[player.grade-1][player.bombGrade-2][4];
				object2.speedY = 0;
				if(i==0){
					object2.mapx = player.mapx+player.width;
					object2.mapy = player.mapy+15;
				}else if(i==1){
					object2.mapx = player.mapx+player.width+object2.width/2;
					object2.mapy = player.mapy+(object2.height+2)+15;
				}else{
					object2.mapx = player.mapx+player.width-object2.width/2;
					object2.mapy = player.mapy+2*(object2.height+2)+15;
				}
				object2.frameIndex = 0;
				bombs.addElement(object2);
			}
			break;
		case TOTORO_BOMB_GRADE_FOUR:
			for(int i=0;i<3;i++){
				MoveObject object2 = new MoveObject();
				object2.status = ROLE_STATUS_ALIVE;
				object2.speedY = 0;
				object2.grade = player.bombGrade;
				if(i==0){
					object2.id = bombParam[player.grade-1][player.bombGrade-3][0];
					object2.width = bombParam[player.grade-1][player.bombGrade-3][1];
					object2.height = bombParam[player.grade-1][player.bombGrade-3][2];
					object2.damage = bombParam[player.grade-1][player.bombGrade-3][3];
					object2.picId = bombParam[player.grade-1][player.bombGrade-3][6];
					object2.speedX = bombParam[player.grade-1][player.bombGrade-3][4];
					object2.mapx = player.mapx+player.width;
					object2.mapy = player.mapy+10;
				}else if(i==1){
					object2.id = bombParam[player.grade-1][player.bombGrade-4][0];
					object2.width = bombParam[player.grade-1][player.bombGrade-4][1];
					object2.height = bombParam[player.grade-1][player.bombGrade-4][2];
					object2.damage = bombParam[player.grade-1][player.bombGrade-4][3];
					object2.picId = bombParam[player.grade-1][player.bombGrade-4][6];
					object2.speedX = bombParam[player.grade-1][player.bombGrade-4][4];
					object2.mapx = player.mapx+player.width+15;
					object2.mapy = player.mapy+50+10;
				}else{
					object2.id = bombParam[player.grade-1][player.bombGrade-2][0];
					object2.width = bombParam[player.grade-1][player.bombGrade-2][1];
					object2.height = bombParam[player.grade-1][player.bombGrade-2][2];
					object2.damage = bombParam[player.grade-1][player.bombGrade-2][3];
					object2.picId = bombParam[player.grade-1][player.bombGrade-2][6];
					object2.speedX = bombParam[player.grade-1][player.bombGrade-2][4];
					object2.mapx = player.mapx+player.width+10;
					object2.mapy = player.mapy+12+10;
				}
				object2.frameIndex = 0;
				bombs.addElement(object2);
			}
			break;
		}
	}

	private void setInfo2(MoveObject player, MoveObject object, int i) {
		switch(player.bombGrade){
		case TOTORO_BOMB_GRADE_ONE:
			object.speedX = bombParam[player.grade-1][player.bombGrade-1][4];
			object.speedY = 0;
			object.mapx = player.mapx+player.width;
			object.mapy = player.mapy+player.height/2-object.height/2;
			object.frameIndex = 0;
			break;
		case TOTORO_BOMB_GRADE_TWO:
			object.speedX = bombParam[player.grade-1][player.bombGrade-1][4];
			object.speedY = 0;
			object.mapx = player.mapx+player.width;
			object.mapy = player.mapy+i*(object.height+5)+10;
			object.frameIndex = 0;
			break;
		case TOTORO_BOMB_GRADE_THREE:
			object.mapx = player.mapx+player.width;
			object.speedX = bombParam[player.grade-1][player.bombGrade-1][4];
			if(i==0){
				object.speedY = -bombParam[player.grade-1][player.bombGrade-1][5];
				object.mapy = player.mapy;
				object.frameIndex = 2;
			}else if(i==1){
				object.speedY = 0;
				object.mapy = player.mapy+player.height/2-object.height/2;
				object.frameIndex = 0;
			}else{
				object.speedY = bombParam[player.grade-1][player.bombGrade-1][5];
				object.mapy = player.mapy+player.height-object.height-5;
				object.frameIndex = 1;
			}
			break;
		case TOTORO_BOMB_GRADE_FOUR:
			object.speedX = bombParam[player.grade-1][player.bombGrade-1][4];
			object.mapx = player.mapx+player.width;
			if(i==0){
				object.speedY = -2;
				object.mapy = player.mapy+10;
				object.frameIndex = 2;
			}else if(i==1){
				object.speedY = 2;
				object.mapy = player.mapy+(object.height+5)+10;
				object.frameIndex = 1;
			}else if(i==2){
				object.speedY = bombParam[player.grade-1][player.bombGrade-1][5];
				object.mapy = player.mapy+player.height-object.height-5;
				object.frameIndex = 4;
			}else{
				object.speedY = -bombParam[player.grade-1][player.bombGrade-1][5];
				object.mapy = player.mapy-5;
				object.frameIndex = 3;
			}
			break;
		}
	}

	/**
	 * 创建新游戏玩家
	 * @return
	 */
	public MoveObject createNewPlayer(int index){
		MoveObject object = new MoveObject();
		object.status = ROLE_STATUS_ALIVE;
		object.id = playerParam[index][0];
		object.mapx = playerParam[index][1];
		object.mapy = playerParam[index][2];
		object.width = playerParam[index][3];
		object.height = playerParam[index][4];
		object.lifeNum = playerParam[index][5];
		object.blood = playerParam[index][6];
		object.damage = playerParam[index][7];
		object.grade = playerParam[index][8];
		object.speedX = playerParam[index][9];
		object.speedY = playerParam[index][10];
		object.bombGrade = playerParam[index][11];
		object.picId = playerParam[index][12];
		return object;
	}
	
	/**
	 * 玩家复活
	 * @return
	 */
	public MoveObject revivePlayer(int grade){
		MoveObject object = new MoveObject();
		object.status = ROLE_STATUS_ALIVE;
		object.id = playerParam[grade-1][0];
		object.mapx = playerParam[grade-1][1];
		object.mapy = playerParam[grade-1][2];
		object.width = playerParam[grade-1][3];
		object.height = playerParam[grade-1][4];
		object.lifeNum = StateGame.lifeNum;
		object.blood = playerParam[grade-1][6];
		object.damage = playerParam[grade-1][7];
		object.grade = StateGame.grade;
		object.speedX = playerParam[grade-1][9];
		object.speedY = playerParam[grade-1][10];
		object.bombGrade = StateGame.bombGrade;
		object.picId = playerParam[grade-1][12];
		System.out.println("totoro revive");
		return object;
	}
	
	public void removeEnemy(){
		spirits.removeAllElements();
		spiritBombs.removeAllElements();
		boss.removeAllElements();
		battery.removeAllElements();
	}
	
	public void removeAllObject(){
		spirits.removeAllElements();
		spiritBombs.removeAllElements();
		boss.removeAllElements();
		battery.removeAllElements();
		bombs.removeAllElements();
		StateGame.player = null;
	}
}


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
	
	/*激光*/
	public Vector lasers = new Vector();
	
	/*导弹*/
	public Vector missile = new Vector();
	
	/*必杀*/
	public Vector ventose = new Vector();
	
	/*守护精灵*/
	public Vector wingplane = new Vector();
	
	/*道具*/
	public Vector props = new Vector();
	
	/*敌方普通攻击*/
	public Vector spiritBombs = new Vector();

	/*精灵*/
	public Vector spirits = new Vector();
	
	/*幽灵boss出的小怪*/
	public Vector ghostSpirits = new Vector();
	
	/*巨魔boss出的小怪*/
	public MoveObject boss8Spirit;
	
	/*地面上的怪(炮台)*/
	public Vector battery = new Vector();
	
	/*boss*/
	public Vector boss = new Vector();
	
	/*boss skill*/
	public Vector boss1Skill = new Vector();
	public Vector boss8Skill = new Vector();
	/*public Vector boss3Skill = new Vector();
	public Vector boss4Skill = new Vector();
	public Vector boss5Skill = new Vector();
	public Vector boss6Skill = new Vector();
	public Vector boss7Skill = new Vector();
	public Vector boss8Skill = new Vector();*/
	
	/*创建一批精灵*/
	public void cteateBatchSpirits(int level, int batcheIndex){
		int count = batchInfo[level-1][batcheIndex][1];
		int spiritId = batchInfo[level-1][batcheIndex][0];
		int value = RandomValue.getRandInt(10);
		for(int i=0;i<count;i++){
			MoveObject mo = new MoveObject();
			mo.status = ROLE_STATUS_PROTECTED;
			mo.status2 = ROLE_STATUS2_MOVE;
			mo.id = spiritId;
			mo.directionValue = value;
			mo.position = batchInfo[level-1][batcheIndex][2];
			mo.direction = batchInfo[level-1][batcheIndex][3];
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
			/*if(mo.pirze == SPIRITI_PRIZE_YES){
				int r = RandomValue.getRandInt(100);
				if(r>40){
					mo.pirze = SPIRITI_PRIZE_NO;
				}
			}*/
			spiritPosition(mo, i, count);
			spirits.addElement(mo);
		}
		//System.out.println("spirits.size:"+spirits.size());
	}
	
	/*创建一个精灵*/
	public void createSpirit(int level){
		
	}
	
	/*创建僚机*/
	public void createWingplane(MoveObject player){
		int id = player.id;
		int currNum = player.wingplaneNums;
		MoveObject mo = new MoveObject();
		mo.status = ROLE_STATUS_ALIVE;
		mo.id = wingplaneParam[id][0];
		mo.picId = wingplaneParam[id][1];
		mo.width = wingplaneParam[id][2];
		mo.height = wingplaneParam[id][3];
		mo.blood = wingplaneParam[id][4];
		mo.damage = wingplaneParam[id][5];
		setWingplaneInfo(mo, currNum, player);
		wingplane.addElement(mo);
	}
	
	public void createWingplaneBomb(MoveObject object){
		int index = object.id;
		MoveObject mo = new MoveObject();
		mo.id = wingplaneBombParam[index][0];
		mo.width = wingplaneBombParam[index][1];
		mo.height = wingplaneBombParam[index][2];
		mo.speedX = wingplaneBombParam[index][3];
		mo.picId = wingplaneBombParam[index][4];
		mo.damage = wingplaneBombParam[index][5];
		mo.frameIndex = 0;
		mo.mapx = object.mapx+object.width;
		mo.mapy = object.mapy+object.height/2-mo.height/2;
		bombs.addElement(mo);
	}
	
	private void setWingplaneInfo(MoveObject mo, int currNum, MoveObject player) {
		switch(currNum){
		case 1:
			mo.mapx = player.mapx + player.width/2-mo.width/2;
			mo.mapy = player.mapy-mo.height;
			break;
		case 2:
			mo.mapx = player.mapx + player.width/2-mo.width/2;
			mo.mapy = player.mapy + player.height;
			break;
		case 3:
			mo.mapx = player.mapx + player.width/2-mo.width/2;
			mo.mapy = player.mapy-mo.height-mo.height;
			break;
		case 4:
			mo.mapx = player.mapx + player.width/2-mo.width/2;
			mo.mapy = player.mapy + player.height+mo.height;
			break;
		}
	}

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
					if(count==1){
						mo.mapx = ScrW + (i*(mo.width+10));
						mo.mapy = RandomValue.getRandInt(50, 400);
					}else if(count==2){
						mo.mapx = ScrW + (i*(mo.width+10));
						mo.mapy = 200;
					}else {
						mo.mapx = ScrW + (i*(mo.width+10));
						mo.mapy = 350;
					}
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
	
	/**/
	public void createProps(MoveObject object, int level){
		int ran = RandomValue.getRandInt(100);
		//System.out.println("ran:"+ran);
		if(ran>20 || object.status==ROLE_STATUS_DEAD){
			return;
		}
		int r = RandomValue.getRandInt(levelProps[level-1].length);
		MoveObject mo = new MoveObject();
		mo.id = levelProps[level-1][r];
		int index = searchPropId(mo.id);
		mo.picId = propsParam[index][1];
		mo.width = propsParam[index][2];
		mo.height = propsParam[index][3];
		mo.speedX = propsParam[index][4];
		mo.speedY = propsParam[index][5];
		mo.direction = OBJECT_DIRECTION_LEFT_DOWN;
		mo.mapx = object.mapx;
		mo.mapy = object.mapy;
		props.addElement(mo);
		//System.out.println("create prop, id is:"+mo.id);
	}
	
	private int searchPropId(int id) {
		for(int i=0;i<propsParam.length;i++){
			if(propsParam[i][0]==id){
				return i;
			}
		}
		return -1;
	}

	public void createLaster(MoveObject player){
		for(int i=0;i<2;i++){
			MoveObject mo = new MoveObject();
			mo.status = ROLE_STATUS_ALIVE;
			mo.status2 = ROLE_STATUS2_ATTACK;
			mo.id = playerSkillParam[0][0];
			mo.width = playerSkillParam[0][1];
			mo.height = playerSkillParam[0][2];
			mo.damage = playerSkillParam[0][3];
			mo.speedX = playerSkillParam[0][4];
			mo.speedY = playerSkillParam[0][5];
			mo.frameNum = playerSkillParam[0][6];
			mo.picId = playerSkillParam[0][7];
			mo.mapx = player.mapx+player.width-10;
			mo.mapy = player.mapy - mo.height + (i*(player.height+mo.height-15))+10;
			mo.startTime = System.currentTimeMillis();
			lasers.addElement(mo);
		}
	}
	
	public void createMissile(MoveObject player, Vector spirits, Vector boss, Vector battery){
		if(player.missileGrade == 1){
			MoveObject object = queryNearestObject(player, spirits, boss, battery);
			MoveObject mo = new MoveObject();
			mo.id = playerSkillParam[1][0];
			mo.width = playerSkillParam[1][1];
			mo.height = playerSkillParam[1][2];
			mo.damage = playerSkillParam[1][3];
			mo.speedX = playerSkillParam[1][4];
			//mo.speedY = playerSkillParam[1][5];
			mo.frameNum = playerSkillParam[1][6];
			mo.picId = playerSkillParam[1][7];
			mo.mapx = player.mapx + player.width;
			mo.mapy = player.mapy+(player.height/2-mo.height/2);
			if(object!=null){
				mo.mo = object;
			}
			missile.addElement(mo);
		}else if(player.missileGrade == 2){
			for(int i=0;i<2;i++){
				MoveObject object = queryNearestObject(player, spirits, boss, battery);
				MoveObject mo = new MoveObject();
				mo.id = playerSkillParam[1][0];
				mo.width = playerSkillParam[1][1];
				mo.height = playerSkillParam[1][2];
				mo.damage = playerSkillParam[1][3];
				mo.speedX = playerSkillParam[1][4];
				//mo.speedY = playerSkillParam[1][5];
				mo.frameNum = playerSkillParam[1][6];
				mo.picId = playerSkillParam[1][7];
				mo.mapx = player.mapx + player.width;
				mo.mapy = player.mapy+(player.height/3-mo.height/2)+(player.height/3)*i;
				if(object!=null){
					mo.mo = object;
				}
				missile.addElement(mo);
			}
		}
	}
	
	public MoveObject queryNearestObject(MoveObject player, Vector spirits, Vector boss, Vector battery){
		MoveObject mo1 = queryNearestObject(player, spirits);
		MoveObject mo2 = queryNearestObject(player, boss);
		MoveObject mo3 = queryNearestObject(player, battery);
		int len1=0, len2=0, len3=0;
		if(mo1==null && mo2==null && mo3==null){
			return null;
		}else if(mo1!=null && mo2==null && mo3==null){
			return mo1;
		}else if(mo1!=null && mo2!=null && mo3==null){
			len1 = (mo1.mapx-player.mapx)*(mo1.mapx-player.mapx)+(mo1.mapy-player.mapy)*(mo1.mapy-player.mapy);
			len2 = (mo2.mapx-player.mapx)*(mo2.mapx-player.mapx)+(mo2.mapy-player.mapy)*(mo2.mapy-player.mapy);
			if(len1<len2){
				return mo1;
			}else {
				return mo2;
			}
		}else if(mo1!=null && mo2==null && mo3!=null){
			len1 = (mo1.mapx-player.mapx)*(mo1.mapx-player.mapx)+(mo1.mapy-player.mapy)*(mo1.mapy-player.mapy);
			len3 = (mo3.mapx-player.mapx)*(mo3.mapx-player.mapx)+(mo3.mapy-player.mapy)*(mo3.mapy-player.mapy);
			if(len1<len3){
				return mo1;
			}else {
				return mo3;
			}
		}else if(mo1!=null && mo2!=null && mo3!=null){
			len1 = (mo1.mapx-player.mapx)*(mo1.mapx-player.mapx)+(mo1.mapy-player.mapy)*(mo1.mapy-player.mapy);
			len2 = (mo2.mapx-player.mapx)*(mo2.mapx-player.mapx)+(mo2.mapy-player.mapy)*(mo2.mapy-player.mapy);
			len3 = (mo3.mapx-player.mapx)*(mo3.mapx-player.mapx)+(mo3.mapy-player.mapy)*(mo3.mapy-player.mapy);
			if(len1<len2 && len1<len2){
				return mo1;
			}else if(len2<len1 && len2<len3){
				return mo2;
			}else{
				return mo3;
			}
		}else if(mo1==null && mo2!=null && mo3!=null){
			len2 = (mo2.mapx-player.mapx)*(mo2.mapx-player.mapx)+(mo2.mapy-player.mapy)*(mo2.mapy-player.mapy);
			len3 = (mo3.mapx-player.mapx)*(mo3.mapx-player.mapx)+(mo3.mapy-player.mapy)*(mo3.mapy-player.mapy);
			if(len2<len3){
				return mo2;
			}else {
				return mo3;
			}
		}else if(mo1==null && mo2==null && mo3!=null){
			return mo3;
		}else if(mo1==null && mo2!=null && mo3==null){
			return mo2;
		}else{
			return null;
		}
	}
	
	private MoveObject queryNearestObject(MoveObject player, Vector objects){
		if(objects.size()<1){
			return null;
		}
		MoveObject object = (MoveObject) objects.elementAt(0);
		int len = (object.mapx-player.mapx)*(object.mapx-player.mapx)+(object.mapy-player.mapy)*(object.mapy-player.mapy);
		for(int i=spirits.size()-1;i>=0;i--){
			MoveObject mo = (MoveObject) spirits.elementAt(i);
			if(mo.mapy+mo.height>startP && mo.mapy+mo.height<=490 && mo.mapx<ScrW && mo.mapx+mo.width>0){
				int len2 = (mo.mapx-player.mapx)*(mo.mapx-player.mapx)+(mo.mapy-player.mapy)*(mo.mapy-player.mapy);
				if(len>len2){
					object = mo;
					len = len2;
				}
			}
		}
		if(object.mapy+object.height>startP && object.mapy+object.height<=490 && object.mapx<ScrW && object.mapx+object.width>0){
			return object;
		}
		return null;
	}
	
	public void createVentose(MoveObject player){
		int grade = player.grade;
		switch(grade){
		case TOTORO_GRADE_ONE:
			for(int i=0;i<6;i++){
				MoveObject mo = new MoveObject();
				mo.status = ROLE_STATUS_ALIVE;
				mo.id = playerSkillParam[grade+1][0];
				mo.width = playerSkillParam[grade+1][1];
				mo.height = playerSkillParam[grade+1][2];
				mo.damage = playerSkillParam[grade+1][3];
				mo.speedX = playerSkillParam[grade+1][4];
				mo.speedY = playerSkillParam[grade+1][5];
				mo.frameNum = playerSkillParam[grade+1][6];
				mo.picId = playerSkillParam[grade+1][7];
				setVentoseInfo(mo,i);
				ventose.addElement(mo);
			}
			break;
		case TOTORO_GRADE_TWO:
			for(int i=0;i<8;i++){
				MoveObject mo = new MoveObject();
				mo.status = ROLE_STATUS_ALIVE;
				mo.id = playerSkillParam[grade+1][0];
				mo.width = playerSkillParam[grade+1][1];
				mo.height = playerSkillParam[grade+1][2];
				mo.damage = playerSkillParam[grade+1][3];
				mo.speedX = playerSkillParam[grade+1][4];
				mo.speedY = playerSkillParam[grade+1][5];
				mo.frameNum = playerSkillParam[grade+1][6];
				mo.picId = playerSkillParam[grade+1][7];
				setVentoseInfo(mo,i);
				ventose.addElement(mo);
			}
			break;
		case TOTORO_GRADE_THREE:
			for(int i=0;i<5;i++){
				MoveObject mo = new MoveObject();
				mo.status = ROLE_STATUS_ALIVE;
				mo.id = playerSkillParam[grade+1][0];
				mo.width = playerSkillParam[grade+1][1];
				mo.height = playerSkillParam[grade+1][2];
				mo.damage = playerSkillParam[grade+1][3];
				mo.speedX = playerSkillParam[grade+1][4];
				mo.speedY = 0/*playerSkillParam[grade+1][5]*/;
				mo.frameNum = playerSkillParam[grade+1][6];
				mo.picId = playerSkillParam[grade+1][7];
				mo.mapx = i*(mo.width-5)-35;
				mo.mapy = 73;
				ventose.addElement(mo);
			}
			break;
		case TOTORO_GRADE_FOUR:
			for(int i=0;i<5;i++){
				MoveObject mo = new MoveObject();
				mo.status = ROLE_STATUS_ALIVE;
				mo.id = playerSkillParam[grade+1][0];
				mo.width = playerSkillParam[grade+1][1];
				mo.height = playerSkillParam[grade+1][2];
				mo.damage = playerSkillParam[grade+1][3];
				mo.speedX = playerSkillParam[grade+1][4];
				mo.speedY = 0/*playerSkillParam[grade+1][5]*/;
				mo.frameNum = playerSkillParam[grade+1][6];
				mo.picId = playerSkillParam[grade+1][7];
				mo.mapx = i*(mo.width-5)-35;
				mo.mapy = 73;
				ventose.addElement(mo);
			}
			break;
		}
	}
	
	int offX = 60;
	private void setVentoseInfo(MoveObject mo, int i) {
		switch(i){
		case 0:
			mo.mapx = -2*mo.width;
			mo.mapy = 0;
			break;
		case 1:
			mo.mapx = -mo.width+offX;
			mo.mapy = -mo.height/2;
			break;
		case 2:
			mo.mapx = -mo.width+offX;
			mo.mapy = mo.height/2;
			break;
		case 3:
			mo.mapx = 0+offX;
			mo.mapy = 0;
			break;
		case 4:
			mo.mapx = mo.width+offX;
			mo.mapy = -mo.height/2;
			break;
		case 5:
			mo.mapx = mo.width+offX;
			mo.mapy = +mo.height/2;
			break;
		/*case 6:
			mo.mapx = 2*mo.width;
			mo.mapy = 0;
			break;
		case 7:
			mo.mapx = 2*mo.width;
			mo.mapy = -mo.height/2-50;*/
		}
	}

	/*敌方普通攻击*/
	public void createSpiritBomb(MoveObject object){
		int index = searchObjectIdIndex(object.id);
		int num = spiritBombParam[index][8];
		for(int i=0;i<num;i++){
			MoveObject mo = new MoveObject();
			mo.status = ROLE_STATUS_ALIVE;
			mo.status2 = ROLE_STATUS2_MOVE;
			mo.objectId = spiritBombParam[index][0];
			mo.id = spiritBombParam[index][1];
			mo.width = spiritBombParam[index][2];
			mo.height = spiritBombParam[index][3];
			mo.damage = spiritBombParam[index][4];
			//mo.speedX = -spiritBombParam[index][5];
			//mo.speedY = 0/*spiritBombParam[index][6]*/;
			mo.picId = spiritBombParam[index][7];
			//mo.mapx = object.mapx+3;
			//mo.mapy = object.mapy+object.height/2 - mo.height/2;
			setSpiritsBombInfo(object, mo, i, index, num);
			mo.bombSTime = System.currentTimeMillis();
			spiritBombs.addElement(mo);
		}
		//System.out.println("spiritBombs.size:"+spiritBombs.size());
	}
	
	private void setSpiritsBombInfo(MoveObject object, MoveObject mo, int i,
			int index, int num) {
		switch(num){
		case 1:
			mo.speedX = -spiritBombParam[index][5];
			mo.speedY = 0/*spiritBombParam[index][6]*/;
			mo.mapx = object.mapx+object.width/2;
			mo.mapy = object.mapy+object.height/2 - mo.height/2;
			break;
		case 2:
			mo.speedX = -spiritBombParam[index][5];
			mo.speedY = -spiritBombParam[index][6]+i*spiritBombParam[index][6];
			mo.mapx = object.mapx+object.width/2;
			mo.mapy = object.mapy+object.height/2 - mo.height/2;
			break;
		case 3:
			mo.speedX = -spiritBombParam[index][5];
			mo.speedY = -spiritBombParam[index][6]+i*spiritBombParam[index][6];
			mo.mapx = object.mapx+object.width/2;
			mo.mapy = object.mapy+object.height/2 - mo.height/2;
			break;
		}
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

	/*炮台普通攻击*/
	public void createBatteryBombs(MoveObject object, MoveObject player){
		int index = searchObjectIdIndex(object.id);
		int num = spiritBombParam[index][8];
		for(int i=0;i<num;i++){
			MoveObject mo = new MoveObject();
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
			batteryBombOtherInfo(index, mo, object, player, i);
			spiritBombs.addElement(mo);
			//System.out.println("spiritBombs:"+spiritBombs.size());
		}
	}
	
	private void batteryBombOtherInfo(int index, MoveObject mo,MoveObject object, MoveObject player, int i) {
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
			if(i==0){
				mo.frame = 0;
				mo.mapx = object.mapx+32;
				mo.mapy = object.mapy;
				mo.speedX = 0/*-spiritBombParam[index][5]*/;
				mo.speedY = -spiritBombParam[index][6];
			}else if(i==1){
				mo.frame = 1;
				mo.mapx = object.mapx+7;
				mo.mapy = object.mapy+5;
				mo.speedX = -spiritBombParam[index][5];
				mo.speedY = -spiritBombParam[index][6];
			}else{
				mo.frame = 2;
				mo.mapx = object.mapx;
				mo.mapy = object.mapy+26;
				mo.speedX = -spiritBombParam[index][5];
				mo.speedY = -6/*-spiritBombParam[index][6]*/;
			}
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
		mo.runTime1 = System.currentTimeMillis()/1000;
		mo.mapx = ScrW;
		mo.mapy = ScrH/2 - mo.height/2;
		//System.out.println("---create boss---");
		boss.addElement(mo);
	}
	
	public void createBoss7Bomb(MoveObject boss, int i){
		for(int m=0;m<3;m++){
			MoveObject mo = new MoveObject();
			mo.objectId = bossSkillPic[1][i][0];
			mo.width = bossSkillPic[1][i][1];
			mo.height = bossSkillPic[1][i][2];
			mo.picId = bossSkillPic[1][i][3];
			if(m==0){
				mo.speedX = bossSkillPic[1][i][4];
				mo.speedY = -bossSkillPic[1][i][5];
			}else if(m==1){
				mo.speedX = bossSkillPic[1][i][4];
				mo.speedY = 0;
			}else{
				mo.speedX = bossSkillPic[1][i][4];
				mo.speedY = bossSkillPic[1][i][5];
			}
			mo.frameNum = bossSkillPic[1][i][6];
			mo.damage = bossSkillPic[1][i][7];
			mo.mapx = boss.mapx+50;
			mo.mapy = boss.mapy+boss.height/2-mo.width/2;
			boss1Skill.addElement(mo);
		}
	}
	
	public void createBoss8Skill2(int x, int y){
		MoveObject mo = new MoveObject();
		mo.objectId = bossSkillParam[8][0];
		mo.width = bossSkillParam[8][1];
		mo.height = bossSkillParam[8][2];
		mo.picId = bossSkillParam[8][6];
		mo.damage = 50/*bossSkillParam[8][3]*/;
		mo.frameNum = bossSkillParam[8][9];
		mo.mapx = x;
		mo.mapy = y;
		mo.speedX = bossSkillParam[8][7];
		mo.speedY = 0/*bossSkillParam[index][8]*/;
		boss8Skill.addElement(mo);
	}
	
	public void createBossSkill(MoveObject boss, MoveObject o){
		int index = searchBossSkillIndex(boss.id);
		int num = bossSkillParam[index][10];
		for(int i=0;i<num;i++){
			MoveObject mo = new MoveObject();
			setBossSkillInfo(boss, mo, index, i, num, o);
			boss1Skill.addElement(mo);
		}
		boss.skillNums++;
	}
	
	private void setBossSkillInfo(MoveObject boss, MoveObject mo, int index, int i, int num, MoveObject o) {
		switch(boss.id){
		case 200:
			mo.objectId = bossSkillParam[index][0];
			mo.width = bossSkillParam[index][1];
			mo.height = bossSkillParam[index][2];
			mo.picId = bossSkillParam[index][6];
			mo.damage = bossSkillParam[index][3];
			mo.frameNum = bossSkillParam[index][9];
			mo.mapx = boss.mapx;
			mo.mapy = boss.mapy+boss.height/2-mo.width/2;
			mo.speedX = bossSkillParam[index][7];
			mo.speedY = 0/*bossSkillParam[index][8]*/;
			break;
		case 201:
			mo.objectId = bossSkillParam[index][0];
			mo.width = bossSkillParam[index][1];
			mo.height = bossSkillParam[index][2];
			mo.picId = bossSkillParam[index][6];
			mo.damage = bossSkillParam[index][3];
			mo.frameNum = bossSkillParam[index][9];
			mo.mapx = boss.mapx;
			mo.mapy = boss.mapy+boss.height/2-mo.width/2;
			mo.speedX = bossSkillParam[index][7];
			mo.speedY = 0/*bossSkillParam[index][8]*/;
			break;
		case 202:
			mo.objectId = bossSkillParam[index][0];
			mo.width = bossSkillParam[index][1];
			mo.height = bossSkillParam[index][2];
			mo.picId = bossSkillParam[index][6];
			mo.damage = bossSkillParam[index][3];
			mo.frameNum = bossSkillParam[index][9];
			mo.mapx = 50+i*(mo.width+80);
			mo.mapy = 73;
			mo.speedX = 0/*bossSkillParam[index][7]*/;
			mo.speedY = bossSkillParam[index][8];
			break;
		case 203:
			mo.objectId = bossSkillParam[index][0];
			mo.width = bossSkillParam[index][1];
			mo.height = bossSkillParam[index][2];
			mo.picId = bossSkillParam[index][6];
			mo.damage = bossSkillParam[index][3];
			mo.frameNum = bossSkillParam[index][9];
			mo.mapx = boss.mapx+i*20;
			mo.mapy = boss.mapy+boss.height/2-mo.width/2;
			mo.speedX = bossSkillParam[index][7]-i*10;
			mo.speedY = 0/*bossSkillParam[index][8]*/;
			mo.frameIndex = i;
			break;
		case 204:
			mo.objectId = bossSkillParam[index][0];
			mo.width = bossSkillParam[index][1];
			mo.height = bossSkillParam[index][2];
			mo.picId = bossSkillParam[index][6];
			mo.damage = bossSkillParam[index][3];
			mo.frameNum = bossSkillParam[index][9];
			mo.mapx = boss.mapx-50;
			mo.mapy = boss.mapy+i*55;
			mo.speedX = bossSkillParam[index][7];
			mo.speedY = 0/*bossSkillParam[index][8]*/;
			break;
		case 205:
			if(boss.status2 == ROLE_STATUS2_SKILL_ATTACK){
				mo.objectId = bossSkillPic[0][i][0];
				mo.width = bossSkillPic[0][i][1];
				mo.height = bossSkillPic[0][i][2];
				mo.picId = bossSkillPic[0][i][3];
				mo.speedX = bossSkillPic[0][i][4]-i*10;
				mo.speedY = 0/*bossSkillParam[index][8]*/;
				mo.frameNum = bossSkillPic[0][i][6];
				mo.damage = bossSkillPic[0][i][7];
				mo.mapx = boss.mapx+i*20;
				mo.mapy = boss.mapy+boss.height/2-mo.width/2;
			}else if(boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
				mo.objectId = bossSkillParam[index][0];
				mo.width = bossSkillParam[index][1];
				mo.height = bossSkillParam[index][2];
				mo.picId = bossSkillParam[index][6];
				mo.damage = bossSkillParam[index][3];
				mo.frameNum = bossSkillParam[index][9];
				if(boss.skillNums%2 == 0){
					mo.mapx = 120+i*(mo.width+90);
					mo.mapy = 40;
					mo.speedX = bossSkillParam[index][7];
					mo.speedY = bossSkillParam[index][8];
				}else{
					mo.mapx = i*(mo.width+90);
					mo.mapy = 40;
					mo.speedX = -bossSkillParam[index][7];
					mo.speedY = bossSkillParam[index][8];
				}
			}
			break;
		case 206:
			if(boss.status2 == ROLE_STATUS2_SKILL_ATTACK){
				mo.objectId = bossSkillParam[index][0];
				mo.width = bossSkillParam[index][1];
				mo.height = bossSkillParam[index][2];
				mo.picId = bossSkillParam[index][6];
				mo.damage = bossSkillParam[index][3];
				mo.frameNum = bossSkillParam[index][9];
				mo.mapx = boss.mapx+50;
				mo.mapy = boss.mapy+boss.height/2-mo.width/2;
				mo.speedX = bossSkillParam[index][7];
				mo.speedY = -bossSkillParam[index][8]+i*5;
			}/*else if(boss.status2 == ROLE_STATUS2_SKILL2_ATTACK){
				mo.objectId = bossSkillPic[1][i][0];
				mo.width = bossSkillPic[1][i][1];
				mo.height = bossSkillPic[1][i][2];
				mo.picId = bossSkillPic[1][i][3];
				mo.speedX = bossSkillPic[1][i][4]-i*5;
				mo.speedY = 0;//bossSkillParam[index][8];
				mo.frameNum = bossSkillPic[1][i][6];
				mo.damage = bossSkillPic[1][i][7];
				mo.mapx = boss.mapx+50;
				mo.mapy = boss.mapy+boss.height/2-mo.width/2;
			}*/
			break;
		case 207:
			mo.objectId = bossSkillParam[index][0];
			mo.width = bossSkillParam[index][1];
			mo.height = bossSkillParam[index][2];
			mo.picId = bossSkillParam[index][6];
			mo.damage = bossSkillParam[index][3];
			mo.frameNum = bossSkillParam[index][9];
			mo.mapx = o.mapx;
			mo.mapy = o.mapy;
			setSpeed(mo,index,i);
			//mo.speedX = bossSkillParam[index][7]+i*5;
			//mo.speedY = bossSkillParam[index][8]+i*5;
			break;
		}
	}

	private void setSpeed(MoveObject mo, int index, int i) {
		switch(i){
		case 0:
			mo.speedX = -60;
			mo.speedY = 0;
			break;
		case 1:
			mo.speedX = -50;
			mo.speedY = 30;
			break;
		case 2:
			mo.speedX = -30;
			mo.speedY = 50;
			break;
		case 3:
			mo.speedX = 0;
			mo.speedY = 60;
			break;
		case 4:
			mo.speedX = 30;
			mo.speedY = 50;
			break;
		case 5:
			mo.speedX = 50;
			mo.speedY = 30;
			break;
		case 6:
			mo.speedX = 60;
			mo.speedY = 0;
			break;
		case 7:
			mo.speedX = 50;
			mo.speedY = -30;
			break;
		case 8:
			mo.speedX = 30;
			mo.speedY = -50;
			break;
		case 9:
			mo.speedX = 0;
			mo.speedY = -60;
			break;
		case 10:
			mo.speedX = -30;
			mo.speedY = -50;
			break;
		case 11:
			mo.speedX = -50;
			mo.speedY = -30;
			break;
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
	
	public void createGhostSpirit(MoveObject boss, int num){
		for(int i=0;i<num;i++){
			MoveObject mo = new MoveObject();
			mo.status = ROLE_STATUS_ALIVE;
			mo.status2 = ROLE_STATUS2_MOVE;
			mo.id = 111;
			//mo.directionValue = value;
			//mo.position = batchInfo[level-1][ran][2];
			mo.direction = OBJECT_DIRECTION_LEFT_DOWN;
			mo.mapx = boss.mapx-55;
			mo.mapy = boss.mapy + i*55;
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
			mo.bombSTime = System.currentTimeMillis();
			mo.startTime = System.currentTimeMillis();
			ghostSpirits.addElement(mo);
		}
	}
	
	public MoveObject createBoss8Spirit(MoveObject boss){
		MoveObject mo = new MoveObject();
		mo.status = ROLE_STATUS_ALIVE;
		mo.status2 = ROLE_STATUS2_MOVE;
		mo.id = 112;
		//mo.directionValue = value;
		//mo.position = batchInfo[level-1][ran][2];
		mo.direction = OBJECT_DIRECTION_LEFT;
		mo.mapx = boss.mapx;
		mo.mapy = boss.mapy+85;
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
		mo.bombSTime = System.currentTimeMillis();
		mo.startTime = System.currentTimeMillis();
		boss8Spirit = mo;
		return boss8Spirit;
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
				object.mapy = player.mapy+player.height-object.height+1;
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
				object.mapy = player.mapy+player.height-object.height+10;
				object.frameIndex = 4;
			}else{
				object.speedY = -bombParam[player.grade-1][player.bombGrade-1][5];
				object.mapy = player.mapy-3;
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
		object.wingplaneMaxNums = StateGame.wingplaneMaxNums;
		object.wingplaneNums = StateGame.wingplaneNums;
		return object;
	}
	
	/**
	 * 玩家复活
	 * @return
	 */
	public MoveObject revivePlayer(int grade){
		MoveObject object = new MoveObject();
		object.status = ROLE_STATUS_PROTECTED;
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
		object.wingplaneMaxNums = StateGame.wingplaneMaxNums;
		object.wingplaneNums = StateGame.wingplaneNums;
		object.scores = StateGame.scores;
		object.startTime = System.currentTimeMillis();
		System.out.println("totoro revive");
		return object;
	}
	
	/**
	 * 继续游戏创建玩家
	 * @param grade
	 * @return
	 */
	public MoveObject createPlayer(int grade){
		MoveObject object = new MoveObject();
		object.status = ROLE_STATUS_ALIVE;
		object.id = playerParam[grade-1][0];
		object.mapx = playerParam[grade-1][1];
		object.mapy = playerParam[grade-1][2];
		object.width = playerParam[grade-1][3];
		object.height = playerParam[grade-1][4];
		object.lifeNum = StateGame.lifeNum;
		if(StateGame.blood > playerParam[grade-1][6]){
			StateGame.blood = playerParam[grade-1][6];
		}
		object.blood = StateGame.blood;
		object.damage = playerParam[grade-1][7];
		object.grade = StateGame.grade;
		object.speedX = playerParam[grade-1][9];
		object.speedY = playerParam[grade-1][10];
		object.bombGrade = StateGame.bombGrade;
		object.picId = playerParam[grade-1][12];
		object.wingplaneMaxNums = StateGame.wingplaneMaxNums;
		object.wingplaneNums = StateGame.wingplaneNums;
		object.missileGrade = StateGame.missileGrade;
		object.scores = StateGame.scores;
		System.out.println("totoro revive");
		return object;
	}
	
	public void removeEnemy(){
		spirits.removeAllElements();
		spiritBombs.removeAllElements();
		boss.removeAllElements();
		battery.removeAllElements();
		boss1Skill.removeAllElements();
		boss8Skill.removeAllElements();
		ghostSpirits.removeAllElements();
		props.removeAllElements();
	}
	
	public void removeAllObject(){
		spirits.removeAllElements();
		spiritBombs.removeAllElements();
		boss.removeAllElements();
		battery.removeAllElements();
		bombs.removeAllElements();
		boss1Skill.removeAllElements();
		boss8Skill.removeAllElements();
		ghostSpirits.removeAllElements();
		lasers.removeAllElements();
		missile.removeAllElements();
		ventose.removeAllElements();
		wingplane.removeAllElements();
		props.removeAllElements();
		StateGame.player = null;
	}
}


package totoro;

import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import cn.ohyeah.itvgame.model.GameRanking;
import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.util.ConvertUtil;

/**
 * 游戏引擎
 * @author Administrator
 */
public class TotoroGameEngine extends GameCanvasEngine implements Common {
	public static boolean isSupportFavor = false;
	public static int ScrW = 0;
	public static int ScrH = 0;
	
	public static TotoroGameEngine instance = buildGameEngine();

	private static TotoroGameEngine buildGameEngine() {
		if(instance==null){
			return new TotoroGameEngine(TotoroMIDlet.getInstance());
		}else{
			return instance;
		}
	}

	public StateMain stateMain;
	public StateGame stateGame;
	public StateSelectInterface stateSelect;
	public PropManager pm;
	//private int recordId;
	public static int result;
	private int index; 
	
	private TotoroGameEngine(MIDlet midlet) {
		super(midlet);
		setRelease(true);
		ScrW = screenWidth;
		ScrH = screenHeight;
		stateGame = new StateGame(this);
		stateMain = new StateMain(this);
		stateSelect = new StateSelectInterface(this);
		pm = new PropManager(this);
	}

	public int state;
	//public int playingIndex;
	private int cursorFrame;
	public PlayerProp[] props;
	public GameRanking[] rankList;
	
	protected void loop() {
		
		/*显示界面*/
		switch (state) {
		case STATUS_INIT:
			showInit(g);
			break;
		case STATUS_MAIN_MENU:
			stateMain.show(g);
			break;
		case STATUS_SELECT_TOTORO:
			stateSelect.show(g);
			break;
		case STATUS_GAME_PLAYING:
			stateGame.show(g);
			break;
		}
		
		/*执行逻辑*/
		switch (state) {
		case STATUS_INIT:
			cursorFrame = (cursorFrame+1)%12;
			break;
		case STATUS_MAIN_MENU:
			stateMain.execute();
			break;
		case STATUS_SELECT_TOTORO:
			stateSelect.execute();
			break;
		case STATUS_GAME_PLAYING:
			stateGame.execute();
			break;
		}
		
		/*处理键值*/
		switch (state) {   	
		case STATUS_INIT:
			handleInit(keyState);
			break;
		case STATUS_MAIN_MENU: 
			stateMain.handleKey(keyState);
			break;
		case STATUS_SELECT_TOTORO:
			stateSelect.handle(keyState, StateMain.mainIndex);
			break;
		case STATUS_GAME_PLAYING:
			stateGame.handleKey(keyState);
			break;
		}
		
		/*退出游戏*/
		exit();
	}
	
	private void init() {
		/*查询道具*/
		pm.queryProps();
		
		//setRecordId();
		
		/*查询排行*/
		queryList();
		
		if(pm.getPropNumsById(65)>0){
			StateGame.wingplaneMaxNums = 4;
		}else if(pm.getPropNumsById(64)>0){
			StateGame.wingplaneMaxNums = 3;
		}else if(pm.getPropNumsById(63)>0){
			StateGame.wingplaneMaxNums = 2;
		}else{
			StateGame.wingplaneMaxNums = 1;
		}
		StateGame.ventoseNum = pm.getPropNumsById(66);
		StateGame.hasTotoro3 = pm.getPropNumsById(61)>0?true:false;
		StateGame.hasTotoro4 = pm.getPropNumsById(62)>0?true:false;
		
		/*读取游戏记录*/
		readRecord(2);
	}
	
	public void queryList() {
		ServiceWrapper sw = getServiceWrapper();
		String datas = sw.loadRanking(3);
		rankList = sw.loadRanking(datas, 0, 10);
	}

	private void handleInit(KeyState key) {
		if(key.containsAndRemove(KeyCode.OK)){
			state = STATUS_MAIN_MENU;
			init();
			Resource.clearInit();
		}
	}


	private void showInit(SGraphics g) {
		Image bg = Resource.loadImage(Resource.id_bg);
		Image text = Resource.loadImage(Resource.id_text);
		g.drawImage(bg, 0, 0, 20);
		if(cursorFrame>4){
			int x = screenWidth/2 - text.getWidth()/2;
			g.drawImage(text, x, 495, 20);
		}
	}


	private void exit(){
		if(stateMain.exit){
			ServiceWrapper sw = getServiceWrapper();
			sw.userQuit();
			exit = true;
		}
	}
	
	private long recordTime;
	public boolean timePass(int millisSeconds) {
		long curTime = System.currentTimeMillis();
		if (recordTime <= 0) {
			recordTime = curTime;
		}
		else {
			if (curTime-recordTime >= millisSeconds) {
				recordTime = 0;
				return true;
			}
		}
		return false;
	}
	
	/*private void setRecordId(){
		Date date = new Date(getEngineService().getCurrentTime().getTime());
	    int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);
	    recordId = year*100+(month+1);
	    //attainmentId = year*100+(month+1);
	    System.out.println("GetServiceDate: Date=" + date);
	    System.out.println("GetServiceDate: Date=" + recordId);
	}*/
	
	public void sysProps(){
		pm.sysProps();
	}
	
	/**
	 * 存档
	 * @param i -1覆盖原存档，但没有游戏记录,  1新存档
	 * @param index 游戏记录序号，取值范围为1-6
	 * @return
	 */
	public int saveRecord(int i, int index){
		String datas = "";
		this.index = i;
		ServiceWrapper sw = engine.getServiceWrapper();
		datas = "lifeNum:"+StateGame.lifeNum
				+",currLevel:"+StateGame.currLevel
				+",blood:"+StateGame.blood
				+",grade:"+StateGame.grade
				+",bombGrade:"+StateGame.bombGrade
				+",wingplaneNums:"+StateGame.wingplaneNums
				+",missileGrade:"+StateGame.missileGrade
				+",laserNums:"+StateGame.laserNums
				+",batchIndex:"+StateGame.batchIndex
				+",levelInterval:"+StateGame.levelInterval
				+",bossBlood:"+StateGame.bossBlood
				+",level_over:"+StateGame.level_over
				+",startGameVentoseNums:"+StateGame.startGameVentoseNums
				+",scores:"+StateGame.scores
				+",index:"+this.index;
		
		sw.saveRecord(index, datas);
		printInfo();
		return sw.getResult();
	}
	
	public int saveScore(){
		ServiceWrapper sw = engine.getServiceWrapper();
		sw.saveScore(StateGame.scores);
		return sw.getResult();
	}
	
	/*private void setRecordData(DataOutputStream dout) throws IOException{
		dout.write(StateGame.lifeNum);
		dout.write(StateGame.currLevel);
		dout.write(StateGame.blood);
		dout.write(StateGame.grade);
		dout.write(StateGame.bombGrade);
		//dout.write(StateGame.wingplaneMaxNums);
		dout.write(StateGame.wingplaneNums);
		dout.write(StateGame.missileGrade);
		dout.write(StateGame.laserNums);
		dout.write(StateGame.batchIndex);
		dout.write(StateGame.levelInterval);
		dout.writeInt(StateGame.bossBlood);
		dout.writeBoolean(StateGame.level_over);
		dout.write(StateGame.startGameVentoseNums);
		//dout.write(StateGame.ventoseNum);
		//dout.writeBoolean(StateGame.hasTotoro3);
		//dout.writeBoolean(StateGame.hasTotoro4);
		
		printInfo();
	}*/
	
	public int readRecord(int index){
		ServiceWrapper sw = engine.getServiceWrapper();
		String data = sw.loadRecord(index);
		if(data==null){
			result = -1;
			return result;
		}
		String[] datas = ConvertUtil.split(data, ",");
		System.out.println("datas:"+datas);
		System.out.println(datas[0]);
		String[] d2 = new String[datas.length]; 
		for(int i=0;i<datas.length;i++){
			d2[i] = ConvertUtil.split(datas[i], ":")[1];
		}
		initRecordInfo(d2);
		if(index == -1){
			result = -1;
		}else{
			result = sw.getResult();
		}
		return result;
	}
	
	private void initRecordInfo(String[] datas){
		StateGame.lifeNum = Integer.parseInt(datas[0]);
		StateGame.currLevel = Integer.parseInt(datas[1]);
		StateGame.blood = Integer.parseInt(datas[2]);
		StateGame.grade = Integer.parseInt(datas[3]);
		StateGame.bombGrade = Integer.parseInt(datas[4]);
		StateGame.wingplaneNums = Integer.parseInt(datas[5]);
		StateGame.missileGrade = Integer.parseInt(datas[6]);
		StateGame.laserNums = Integer.parseInt(datas[7]);
		StateGame.batchIndex = Integer.parseInt(datas[8]);
		StateGame.levelInterval = Integer.parseInt(datas[9]);
		StateGame.bossBlood = Integer.parseInt(datas[10]);
		StateGame.level_over = datas[11].equals("true")?true:false;
		StateGame.startGameVentoseNums = Integer.parseInt(datas[12]);
		StateGame.scores = Integer.parseInt(datas[13]);
		index = Integer.parseInt(datas[14]);
		
		printInfo();
	}

	private void printInfo(){
		System.out.println("StateGame.lifeNum:"+StateGame.lifeNum);
		System.out.println("StateGame.currLevel:"+StateGame.currLevel);
		System.out.println("StateGame.blood:"+StateGame.blood);
		System.out.println("StateGame.grade:"+StateGame.grade);
		System.out.println("StateGame.bombGrade:"+StateGame.bombGrade);
		System.out.println("StateGame.wingplaneMaxNums:"+StateGame.wingplaneMaxNums);
		System.out.println("StateGame.wingplaneNums:"+StateGame.wingplaneNums);
		System.out.println("StateGame.missileGrade:"+StateGame.missileGrade);
		System.out.println("StateGame.laserNums:"+StateGame.laserNums);
		System.out.println("StateGame.batchIndex:"+StateGame.batchIndex);
		System.out.println("StateGame.ventoseNum:"+StateGame.ventoseNum);
		System.out.println("StateGame.hasTotoro3:"+StateGame.hasTotoro3);
		System.out.println("StateGame.hasTotoro4:"+StateGame.hasTotoro4);
		System.out.println("StateGame.scores:"+StateGame.scores);
		System.out.println("StateGame.levelInterval:"+(StateGame.levelInterval));
		System.out.println("StateGame.bossBlood:"+(StateGame.bossBlood));
		System.out.println("StateGame.level_over:"+(StateGame.level_over));
		System.out.println("StateGame.startGameVentoseNums:"+(StateGame.startGameVentoseNums));
		System.out.println("index:"+index);
	}
}

package totoro;

/*
 * 公共变量类 
 */
public interface Common {
	
	public final static int STATUS_INIT = 0;			//初始
	public final static int STATUS_MAIN_MENU = 1;		//游戏主菜单 
	public final static int STATUS_GAME_PLAYING = 2;	//游戏中
	public final static int STATUS_GAME_RECHARGE = 3;	//游戏中

	public final static int ScrW = TotoroGameEngine.ScrW;
	public final static int ScrH = TotoroGameEngine.ScrH;
	public final static int gameW = 490;				//游戏区域宽度
}

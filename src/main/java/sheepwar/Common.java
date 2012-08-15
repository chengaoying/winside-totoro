package sheepwar;

import javax.microedition.lcdui.Graphics;

/*
 * 公共变量类 
 */
public interface Common {
	
	public final static int STATUS_INIT = 0;			//初始
	public final static int STATUS_MAIN_MENU = 1;		//游戏主菜单 
	public final static int STATUS_GAME_PLAYING = 2;	//游戏中
	public final static int STATUS_GAME_SHOP = 3;		//游戏商城
	public final static int STATUS_GAME_ARCHI = 4;		//游戏成就
	public final static int STATUS_GAME_RANKING = 5;	//游戏排行
	public final static int STATUS_GAME_HELP = 6;		//游戏帮助
	public final static int STATUS_GAME_EXIT = 7;		//退出游戏
	
	public final static short TopLeft = Graphics.LEFT|Graphics.TOP;
	public final static int ScrW = SheepWarGameEngine.ScrW;
	public final static int ScrH = SheepWarGameEngine.ScrH;
}

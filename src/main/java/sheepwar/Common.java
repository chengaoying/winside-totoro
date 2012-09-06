package sheepwar;

import javax.microedition.lcdui.Graphics;

/*
 * 公共变量类 
 */
public interface Common {
	
	public final static int STATUS_INIT = 0;			//初始
	public final static int STATUS_MAIN_MENU = 1;		//游戏主菜单 
	public final static int STATUS_GAME_PLAYING = 2;	//游戏中

	
	public final static short TopLeft = Graphics.LEFT|Graphics.TOP;
	
	public final static int ScrW = SheepWarGameEngine.ScrW;
	public final static int ScrH = SheepWarGameEngine.ScrH;
	
	public final static int gameMapX = 640;                 //游戏区域的宽度
	public final static int gameMapY = 530;                  //游戏区域的高度
	
	//public static int sheepMapY = 290;                     //364-74:羊上下移动的限制范围
	
}

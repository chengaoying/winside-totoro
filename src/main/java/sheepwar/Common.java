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
	
	public final static short ROLE_ALIVE = 0; 			//角色活着状态 
	public final static short ROLE_DEATH = -1;  		//角色活着状态 
	
	public final static short ON_ONE_LADDER = 1; 			//角色在第一个梯子上
	public final static short ON_TWO_LADDER = 2;  			//角色在第二个梯子上 
	public final static short ON_THREE_LADDER = 3;  		//角色在第三个梯子上
	public final static short ON_FOUR_LADDER = 4;  			//角色在第四个梯子上
	
	/*狼在空中分布方式*/
	public final static short SPREED_BELOW = 1;			//斜下直线
	public final static short SPREED_ABOVE = 2;			//斜上直线
	public final static short SPREED_VERTICAL = 3;		//竖直
	
}

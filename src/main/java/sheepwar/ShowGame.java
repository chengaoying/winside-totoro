package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.util.RandomValue;

/**
 * 显示图像
 * 
 * @author Administrator
 * 
 */
public class ShowGame implements Common {
	private SheepWarGameEngine engine;
	public ShowGame(SheepWarGameEngine e){
		this.engine=e;
	}
	public Image playing_cloudsmall,playing_cloudbig;
	private int tempx=ScrW, tempy=20, tempx2=ScrW, tempy2=30;//ScrW屏幕宽度，tempx初始=ScrW，可以用表达式tempx-=1来使其移动
	
	/* 登陆界面坐标 */
	public int menuAxis[][] = { { 523, 243 }, { 466, 288 }, { 523, 333 },
			{ 466, 378 }, { 523, 423 }, { 466, 468 }, };
	
	/* 游戏界面坐标 */
	public int playingAxis[][] = { { 491, 0 }, { 0, 529 }, { 0, 72 },
			{ 377, 153 }, { 377, 240 }, { 377, 324 }, { 377, 409 }, };
	
     /*成就左侧文字图片坐标*/
	public int archLeftAxis[][] = { { 68, 132 },{ 59, 186 },{ 68, 240 },{ 68, 294 },{ 68, 348 },{ 68, 402 } };
	
	
	/*释放MainMenu图片*/
	public void clearMainMenu() {
		Resource.freeImage(Resource.id_main_bg);
		Resource.freeImage(Resource.id_main_menu);
	}
	
	/*释放游戏中的图片*/
	public void clearGamePlaying(){
		Resource.freeImage(Resource.id_playing_menu);
		Resource.freeImage(Resource.id_playing_cloudbig);
		Resource.freeImage(Resource.id_playing_cloudbig);
		Resource.freeImage(Resource.id_playing_cloudsmall);
		Resource.freeImage(Resource.id_playing_lawn);
		Resource.freeImage(Resource.id_playing_step);
		Resource.freeImage(Resource.id_playing_tree);
		Resource.freeImage(Resource.id_game_bg);
		Resource.freeImage(Resource.id_playing_lunzi);
		Resource.freeImage(Resource.id_playing_shenzi);
		Resource.freeImage(Resource.id_playing_lift);
		Resource.freeImage(Resource.id_playing_shenzi1);
		Resource.freeImage(Resource.id_playing_prop_memu);
		Resource.freeImage(Resource.id_playing_prop);
		Resource.freeImage(Resource.id_playing_stop);   //游戏暂停按钮
		Resource.freeImage(Resource.id_playing_sheep);   
		Resource.freeImage(Resource.id_sheep_eye);   
		Resource.freeImage(Resource.id_sheep_hand);   
		Resource.freeImage(Resource.id_bomb);   
		Resource.freeImage(Resource.id_wolf_down);   
		Resource.freeImage(Resource.id_wolf_run);   
		Resource.freeImage(Resource.id_balloon_blue);   
		Resource.freeImage(Resource.id_balloon_green);   
		Resource.freeImage(Resource.id_balloon_multicolour);   
		Resource.freeImage(Resource.id_balloon_yellow);   
		Resource.freeImage(Resource.id_balloon_yellowred);   
		Resource.freeImage(Resource.id_balloon_red);   
	}

    /*画出主菜单mainMenu*/
	public void drawMainMenu(SGraphics g, int index) {
		Image main_bg = Resource.loadImage(Resource.id_main_bg);
		Image main_menu = Resource.loadImage(Resource.id_main_menu);
		g.drawImage(main_bg, 0, 0, 0);
		for (int i = 0; i < menuAxis.length; ++i) {
			g.drawRegion(main_menu,
					(index != i) ? main_menu.getWidth() / 2 : 0,
					i * main_menu.getHeight() / 6, main_menu.getWidth() / 2,
					main_menu.getHeight() / 6, 0, menuAxis[i][0],
					menuAxis[i][1], 0);
		}
	}
    
    /*画出游戏界面*/
	public void drawGamePlaying(SGraphics g, int index,Role own) {
		
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image playing_menu = Resource.loadImage(Resource.id_playing_menu);// {491,0}
		Image playing_cloudbig = Resource.loadImage(Resource.id_playing_cloudbig);
		Image playing_cloudsmall = Resource.loadImage(Resource.id_playing_cloudsmall);// {404,164}
		Image playing_lawn = Resource.loadImage(Resource.id_playing_lawn);// {0,499}
		Image playing_step = Resource.loadImage(Resource.id_playing_step);// {377,153},{377,240}{377,324}{377,409} Y 相差89
		Image playing_tree = Resource.loadImage(Resource.id_playing_tree);// {0,72}
		Image playing_lunzi = Resource.loadImage(Resource.id_playing_lunzi);//{374,132}
		Image playing_shenzi = Resource.loadImage(Resource.id_playing_shenzi); //{379,154}
		Image playing_lift = Resource.loadImage(Resource.id_playing_lift); //{342,303}
		Image playing_shenzi1 = Resource.loadImage(Resource.id_playing_shenzi1); //{399, 135}//横放的绳子
		Image playing_prop_memu = Resource.loadImage(Resource.id_playing_prop_memu); //{497,192}{564,192}//上下相差70
		Image playing_stop = Resource.loadImage(Resource.id_playing_stop); //{501,466}
		Image bomb = Resource.loadImage(Resource.id_bomb); //{501,466}
//		Image blue = Resource.loadImage(Resource.id_balloon_blue);
		g.drawImage(game_bg, 0, 0, TopLeft);
		
		if(tempx+playing_cloudbig.getWidth()>0){
			tempx -= 1;
		}else{
			tempy = RandomValue.getRandInt(0, 114);
			tempx = ScrW;
		}
		g.drawRegion(playing_cloudbig, 0, 0, playing_cloudbig.getWidth(), playing_cloudbig.getHeight(), 
				0, tempx, tempy, TopLeft);
		
		if(tempx2+playing_cloudsmall.getWidth()>0){
			tempx2 -= 2;
		}else{
			tempy2 = RandomValue.getRandInt(0, 114);
			tempx2 = ScrW;
		}
		g.drawRegion(playing_cloudsmall, 0, 0, playing_cloudsmall.getWidth(), playing_cloudsmall.getHeight(), 
				0, tempx2, tempy2, TopLeft);
		g.drawImage(playing_lawn, 0, 499, TopLeft);
		g.drawImage(playing_tree, 0, 72, TopLeft);
		g.drawImage(playing_shenzi1, 399, 135, TopLeft);
		for(int i=0;i<4;i++){   //阶梯
			g.drawImage(playing_step, 377, 153+i*89, TopLeft);
		}
		g.drawRegion(playing_shenzi, 0, 0, playing_shenzi.getWidth(), (own.mapy-154),        //上下移动的绳子
				0, 379, 154, TopLeft);                                                        //竖直绳子 的纵坐标 154

		g.drawRegion(playing_lift, 0, 0, playing_lift.getWidth(), playing_lift.getHeight(),     //羊的吊篮
				0, 342, 154+(own.mapy-154), TopLeft);
		g.drawRegion(bomb, 0, 0, bomb.getWidth()/3, bomb.getHeight(), 0, 345-18, 40+own.mapy, TopLeft); //吊篮上的飞镖
		
		g.drawImage(playing_lunzi, 374,132, TopLeft);
		g.drawImage(playing_menu, 491, 0, TopLeft);
		for(int i=0;i<4;i++){                                                                //游戏中的左侧框内容---道具内容
			g.drawImage(playing_prop_memu, 497,185+i*68, TopLeft);
			drawProp(g, i, 497+5,185+i*(68+3));                                              //第一列对应原图片中的前四个
			drawNum(g, i+1, 540+7, 223-17+i*73);//提示技能按键：1-4{540,223}
			
			g.drawImage(playing_prop_memu, 564,185+i*68, TopLeft);
			drawProp(g, i+4, 564+5,185+i*(68+2));  //第二列对应原图片中的后四个
			drawNum(g, i+4+1, 612, 223-17+i*73);//提示技能键5-8{}
		}
		g.drawImage(playing_stop, 500,459, TopLeft);//暂停游戏按钮
		
	}
	

	/*游戏中的数字*/
	private void drawNum(SGraphics g, int num, int x, int y) {
		Image imgNumeber = Resource.loadImage(Resource.id_shop_figure);
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(imgNumeber, (number.charAt(i) - '0') * imgNumeber.getWidth()/10, 0, 
					imgNumeber.getWidth()/10, imgNumeber.getHeight(), 0, x + i * (imgNumeber.getWidth()/10 + 1), y, 0);
		}
	}
	
	/*道具的图片加载----数字转化为图片*/
	private void drawProp(SGraphics g,int num,int x,int y){
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		String number=String.valueOf(num);
		for(byte i=0;i<number.length();i++){
			g.drawRegion(playing_prop, (number.charAt(i) - '0')* playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8,
					playing_prop.getHeight(), 0, x+i * (playing_prop.getWidth()/8 + 1), y, 0);
		}
	}
}

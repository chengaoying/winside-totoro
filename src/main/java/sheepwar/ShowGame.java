package sheepwar;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.ui.TextView;
import cn.ohyeah.stb.util.RandomValue;

/**
 * 显示图像
 * 
 * @author Administrator
 * 
 */
public class ShowGame implements Common {
	public Image playing_cloudsmall,playing_cloudbig;
	private int tempx=ScrW, tempy=20, tempx2=ScrW, tempy2=30;//ScrW屏幕宽度，tempx初始=ScrW，可以用表达式tempx-=1来使其移动
	
	/* 登陆界面坐标 */
	public int menuAxis[][] = { { 523, 243 }, { 466, 288 }, { 523, 333 },
			{ 466, 378 }, { 523, 423 }, { 466, 468 }, };
	/* 游戏界面坐标 */
	public int playingAxis[][] = { { 491, 0 }, { 0, 529 }, { 0, 72 },
			{ 377, 153 }, { 377, 240 }, { 377, 324 }, { 377, 409 }, };

	public void drawMainMenu(Graphics g, int index) {
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
	}
	
	/*清商城界面*/
	public void clearShop() {
		Resource.freeImage(Resource.id_shop);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_big);
		Resource.freeImage(Resource.id_shop_figure);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_go_pay);
		Resource.freeImage(Resource.id_shop_midding);
		Resource.freeImage(Resource.id_shop_out);
		Resource.freeImage(Resource.id_price_quantity);
		Resource.freeImage(Resource.id_playing_prop);        //商品图片
	}
	
	/*清 成就系统界面*/
    public void clearGameArchi(){
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_shop_midding);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_shop_go_pay);
    	Resource.freeImage(Resource.id_achievement);
    	Resource.freeImage(Resource.id_achievement_left_right);
    	Resource.freeImage(Resource.id_achievement_left_right1);
    	Resource.freeImage(Resource.id_achievement_long);
    	Resource.freeImage(Resource.id_achievement_long1);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_achievement_points);
    	Resource.freeImage(Resource.id_archivement_hoof);
    	Resource.freeImage(Resource.id_archivement_hoof1);
    }
    
    /*清除排行系统界面*/
    public void clearRanking() {
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_achievement_left_right);
    	Resource.freeImage(Resource.id_current_ranking);
    	Resource.freeImage(Resource.id_ranking_option);
    	Resource.freeImage(Resource.id_ranking_option1);
    	Resource.freeImage(Resource.id_ranking_stripe);
    	Resource.freeImage(Resource.id_ranking);
    	Resource.freeImage(Resource.id_ranking_show);
	}
    
    /*清除帮助界面*/
    public void clearHelp() {
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_game_help);
	}
    
//    private int index, flag;
    public CreateRole createRole;
	public void drawGamePlaying(Graphics g, int index, Role own) {
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
		Image wolf = Resource.loadImage(Resource.id_wolf_run); //{399, 135}
		Image playing_sheep = Resource.loadImage(Resource.id_playing_sheep); //{399, 135}
		Image sheep_eye = Resource.loadImage(Resource.id_sheep_eye);
		Image sheep_hand = Resource.loadImage(Resource.id_sheep_hand);
		Image playing_prop_memu = Resource.loadImage(Resource.id_playing_prop_memu); //{497,192}{564,192}//上下相差70
		Image playing_stop = Resource.loadImage(Resource.id_playing_stop); //{501,466}
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
		createRole=new CreateRole();                                                           //容易忘记实例化
		createRole.showSheep(g,sheep_hand,playing_sheep,sheep_eye,own);                        //动态的羊

		g.drawRegion(playing_lift, 0, 0, playing_lift.getWidth(), playing_lift.getHeight(),     //羊的吊篮
				0, 342, 154+(own.mapy-154), TopLeft);
		
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
		
//		createRole.showWolf(g,index,flag);
//		if(flag>2){    //狼的效果图
//			index = (index+1)%6;
//			flag=0;
//		}else{
//			flag++;
//		}
//		g.drawRegion(wolf, index*wolf.getWidth()/6, 0, wolf.getWidth()/6, wolf.getHeight(), 0, 50, 50, TopLeft);
	}
	
	/*画商店界面*/
	public void drawGameShop(Graphics g,int shopX,int shopY) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_balance = Resource.loadImage(Resource.id_shop_balance);//{46,454}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{29,103}
		//Image shop_figure = Resource.loadImage(Resource.id_shop_figure);//{103,452}//TODO
		Image shop_go_pay = Resource.loadImage(Resource.id_shop_go_pay);//{457,381}
		Image shop_midding = Resource.loadImage(Resource.id_shop_midding);//{434,103}
		Image shop_out_base = Resource.loadImage(Resource.id_shop_out_base);//TODO
		Image shop_out = Resource.loadImage(Resource.id_shop_out);//{457,429}
		Image shop_small_base = Resource.loadImage(Resource.id_shop_small_base);//TODO
		Image shop_small = Resource.loadImage(Resource.id_shop_small);
		Image price_quantity = Resource.loadImage(Resource.id_price_quantity);
		Image shop = Resource.loadImage(Resource.id_shop);//{217,18}
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		//{42,115},{233,115},{42,195},{233,195},{42,279},{233,279},{42,361},{233,361}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(shop, 217, 18, TopLeft);
		g.drawImage(shop_big, 29, 103, TopLeft);
		g.drawImage(shop_balance, 46, 454, TopLeft);
	
		int x =42, y = 120, spaceX = 15, spaceY = 8;//
		for(int i=0;i<4;i++){
			for(int j=0;j<2;j++){
				g.drawRegion(shop_small_base, 0, 0, shop_small_base.getWidth(), shop_small_base.getHeight(),
						0, x+(spaceX+shop_small_base.getWidth())*j, y+(spaceY+shop_small_base.getHeight())*i, TopLeft);
			}
		}
		g.drawImage(shop_midding, 434, 103, TopLeft);
		for(int i=0;i<2;i++){             //midding下的按钮阴影
			g.drawRegion(shop_out_base, 0, 0, shop_out_base.getWidth(), shop_out_base.getHeight(), 
					0, 457, 381+(spaceY+shop_out_base.getHeight())*i, TopLeft);
			}
		int mapx=37,mapy=112;       //被选中后出现阴影效果的坐标
		 for(int i=0;i<4;i++){
		     for(int j=0;j<2;j++){
				if(shopX==j && shopY==i){
					g.drawRegion(shop_small, 0, 0, shop_small.getWidth(), shop_small.getHeight(),
							0, mapx+(spaceX+shop_small.getWidth())*j, mapy+(spaceY+shop_small.getHeight())*i, TopLeft);
					g.drawImage(price_quantity, mapx+(spaceX+shop_small.getWidth())*j+65, 
							mapy+(spaceY+shop_small.getHeight())*i+12, TopLeft);
					g.drawRegion(playing_prop, getIndex(j, i)*playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8, playing_prop.getHeight(), 0,
							mapx+(spaceX+shop_small.getWidth())*j+8, mapy+(spaceY+shop_small.getHeight())*i+9, TopLeft);
					drawNum(g, 111, mapx+(spaceX+shop_small.getWidth())*j+119, mapy+(spaceY+shop_small.getHeight())*i+11);
					drawNum(g, 333, mapx+(spaceX+shop_small.getWidth())*j+119, mapy+(spaceY+shop_small.getHeight())*i+36);
				}else{
					g.drawRegion(shop_small, 0, 0, shop_small.getWidth(), shop_small.getHeight(), 0,
							x+(spaceX+shop_small.getWidth())*j, y+(spaceY+shop_small.getHeight())*i, TopLeft);
					g.drawImage(price_quantity, x+(spaceX+shop_small.getWidth())*j+65, 
							y+(spaceY+shop_small.getHeight())*i+12, TopLeft);
					g.drawRegion(playing_prop, getIndex(j, i)*playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8, playing_prop.getHeight(), 0,
							x+(spaceX+shop_small.getWidth())*j+8, y+(spaceY+shop_small.getHeight())*i+9, TopLeft);
					drawNum(g, 111, x+(spaceX+shop_small.getWidth())*j+119, y+(spaceY+shop_small.getHeight())*i+11);
					drawNum(g, 333, x+(spaceX+shop_small.getWidth())*j+119, y+(spaceY+shop_small.getHeight())*i+36);
				}
			}
		}
		 if(shopX==2){          //充值和返回被选择的阴影效果
			 if(shopY==0){    //控制方向由左到右的入口方向
				 g.drawImage(shop_go_pay, 457-8, 381-5, TopLeft);
			   	 g.drawImage(shop_out, 457, 429, TopLeft);
			  }else{
				 g.drawImage(shop_go_pay, 457, 381, TopLeft);
			   	 g.drawImage(shop_out, 457-8, 429-5, TopLeft);
			 }
		    }else{
		    	g.drawImage(shop_go_pay, 457, 381, TopLeft);
		   		g.drawImage(shop_out, 457, 429, TopLeft);
		    }
		drawNum(g, 10, 103,452);                                          //TODO 添加数字
	}
	
	private int getIndex(int x, int y){    //取出对应商店商场左侧的位置
		if(y==0 && x==0)return 0;
		if(y==1 && x==0)return 1;
		if(y==2 && x==0)return 2;
		if(y==3 && x==0)return 3;
		if(y==0 && x==1)return 4;
		if(y==1 && x==1)return 5;
		if(y==2 && x==1)return 6;
		if(y==3 && x==1)return 7;
		if(x==2)        return 8;
		return -1;
	}
	
	/*画出成就系统*/
	public void drawGameArchi(Graphics g, int archiIndex) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_midding = Resource.loadImage(Resource.id_shop_midding);//{28,102}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{235,102}
		Image shop_go_pay = Resource.loadImage(Resource.id_shop_go_pay);//{457,381}//{51,123},{51,178},{51,232},{51,286},{51,342},{51,396}
		Image achievement = Resource.loadImage(Resource.id_achievement);//{270,19}
		Image achievement_left_right = Resource.loadImage(Resource.id_achievement_left_right);//{458,441}
		Image achievement_left_right1 = Resource.loadImage(Resource.id_achievement_left_right1);
		Image achievement_long = Resource.loadImage(Resource.id_achievement_long);//{247,114},{247,198},{247,277},{247,361},{}
		Image achievement_long1 = Resource.loadImage(Resource.id_achievement_long1);
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);//{55,451}
		Image achievement_points = Resource.loadImage(Resource.id_achievement_points);//{250,448}
		Image archivement_hoof = Resource.loadImage(Resource.id_archivement_hoof);//{539,130},{539,211},{539,293},{539,378}
		Image archivement_hoof1 = Resource.loadImage(Resource.id_archivement_hoof1);
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(achievement, 270, 19, TopLeft);
		g.drawImage(shop_midding, 28, 102, TopLeft);
		g.drawImage(achievement_out1, 55, 451, TopLeft);
		for(int i=0;i<6;i++){  //成就左侧条目
			g.drawImage(shop_go_pay, 51, 123+i*55, TopLeft);//Y坐标相差55
		}
		g.drawImage(shop_big, 235, 102, TopLeft);
		g.drawImage(achievement_points, 250, 448, TopLeft);
		g.drawImage(achievement_left_right,458,441, TopLeft);
		for(int i=0;i<4;i++){//成就右侧条目
			g.drawImage(achievement_long1, 247, 114+i*83, TopLeft);//Y坐标相差83
			g.drawImage(archivement_hoof1, 539, 130+i*83, TopLeft);
		}
	}
	
	/*画出排行榜*/
	public void showRanking(Graphics g, int rankingIndex) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);//{61,462}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{233,101}
		Image achievement_left_right = Resource.loadImage(Resource.id_achievement_left_right);//{457,440}
		Image current_ranking=Resource.loadImage(Resource.id_current_ranking);//{253,448}
		Image ranking_option=Resource.loadImage(Resource.id_ranking_option);//{39,112} Y相差54 
		Image ranking_option1=Resource.loadImage(Resource.id_ranking_option1);
		Image ranking_stripe=Resource.loadImage(Resource.id_ranking_stripe);//{241,151}  条高度57
		Image ranking=Resource.loadImage(Resource.id_ranking);//{232,18}
		Image ranking_show=Resource.loadImage(Resource.id_ranking_show);//{241,108}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(achievement_out1, 61,462, TopLeft);
		for(int i=0;i<3;i++){//排行左侧条目
			g.drawImage(ranking_option, 39, 112+i*54, TopLeft);
		}
		g.drawImage(shop_big, 233,101, TopLeft);
		g.drawImage(ranking_show,241,108, TopLeft);
		for(int i=0;i<5;i++){
			g.drawImage(ranking_stripe,241,151+i*57, TopLeft);
		}
		g.drawImage(current_ranking, 253,448, TopLeft);
		g.drawImage(ranking, 232,18, TopLeft);
		g.drawImage(achievement_left_right, 457,440, TopLeft);
	}
	
	/*画出帮助界面*/
	private String gameIntro[]={
			"【操作说明】",
			"上下方向键：控制玩家的移动。",
			"确定键：发射飞镖或无敌拳套。",
			"数字键1至8：使用道具。",
			"数字键0：退出游戏。",
			"数字键9:游戏帮助。",
			"",
			"【道具说明】",
			"时光闹钟：时间静止10秒。",
			"捕狼网：发射出的子弹碰到灰太狼就会张开一张网，大网内的灰太狼都会掉落。",
			"防狼套装：开启后得到5秒的无敌效果，抵御各种攻击。",
			"驱狼光波：发出一道十万伏特的电流，电晕碰到的灰太狼，持续5秒。",
			"替身玩偶：增加一条命。",
			"驱散竖琴：使用后清除所有的梯子或者正在推石头的灰太狼。",
			"速度提升液：使用后增加喜羊羊的移动速度，持续30秒。",
			"强力磁石：击落所有空中的灰太狼。",
			"",
			"【游戏简介】",
			"喜羊羊大战灰太狼是一款闯关类游戏，总共有15关。玩家控制喜羊羊击落一定数量的灰",
			"太狼即可过关。此外玩家还可以在道具商城内购买各种道具来获得更有趣的体验。除了闯关",
			"外，游戏中还推出了成就系统和排行榜，增加了玩家在游戏的过程中动力和目标。",
			"",
	};
	public void showHelp(Graphics g,int helpIndex) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_big = Resource.loadImage(Resource.id_shop_big);       //{137,108}
		Image game_help = Resource.loadImage(Resource.id_game_help);     //{214,18}
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);   //{17,498}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(shop_big, 137, 108, TopLeft);
		g.drawImage(game_help, 214,18, TopLeft);
		g.drawImage(achievement_out1, 17,498, TopLeft);
		g.setColor(0xffffff);
		String info="";
		for(int i=0;i<gameIntro.length;i++){
			info += gameIntro[i];
		}
		TextView.showMultiLineText(g, info, 10, 150, 130, 350, 350);
	}
	
	/*游戏中的数字*/
	private void drawNum(Graphics g, int num, int x, int y) {
		Image imgNumeber = Resource.loadImage(Resource.id_shop_figure);
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(imgNumeber, (number.charAt(i) - '0') * imgNumeber.getWidth()/10, 0, 
					imgNumeber.getWidth()/10, imgNumeber.getHeight(), 0, x + i * (imgNumeber.getWidth()/10 + 1), y, 0);
		}
	}
	
	/*道具的图片加载----数字转化为图片*/
	private void drawProp(Graphics g,int num,int x,int y){
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		String number=String.valueOf(num);
		for(byte i=0;i<number.length();i++){
			g.drawRegion(playing_prop, (number.charAt(i) - '0')* playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8,
					playing_prop.getHeight(), 0, x+i * (playing_prop.getWidth()/8 + 1), y, 0);
		}
	}
}

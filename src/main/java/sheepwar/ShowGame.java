package sheepwar;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.ui.DrawUtil;
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
			g.drawImage(playing_prop_memu, 564,185+i*68, TopLeft);
			drawProp(g, i+4, 564+5,185+i*(68+2));                                             //第二列对应原图片中的后四个
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
		//{42,115},{233,115},{42,195},{233,195},{42,279},{233,279},{42,361},{233,361}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(shop, 217, 18, TopLeft);
		g.drawImage(shop_big, 29, 103, TopLeft);
		g.drawImage(shop_balance, 46, 454, TopLeft);
		for(int i=0;i<4;i++){                                       //以后可以减少硬编码
			g.drawImage(shop_small, 42, 115+i*82, TopLeft);                 //上下相差 82
			g.drawImage(price_quantity, 109-4, 127+4+i*81, TopLeft);        //价格和数量{109,127}
			drawNum(g, 111, 105+55,127+5+i*81);                             //{167,127}
			drawNum(g, 222, 105+55,130+24+i*81);
			//TODO价格怎么单独定
			drawProp(g, i, 47+1,120+10+i*82);                              //{47,120}
			
			g.drawImage(shop_small, 223, 115+i*82, TopLeft);
			g.drawImage(price_quantity, 223+63, 127+4+i*81, TopLeft);
			drawNum(g, 333, 286+55,127+5+i*81);                                       //X 286+p_q.width;
			drawNum(g, 444, 286+55,130+24+i*81);
			drawProp(g, i+4, 229+1,120+10+i*82);                                     //{229,120}
			//TODO 添加选中效果
		}
		g.drawImage(shop_midding, 434, 103, TopLeft);
		g.drawImage(shop_go_pay, 457, 381, TopLeft);
		g.drawImage(shop_out, 457, 429, TopLeft);
		if(shopX<2){
			DrawUtil.drawRect(g, 42+7+shopX*180, 115+7+shopY*84, 58, 63, 2, 0XFFFF00);//58W,63 H,13间距
			g.setColor(28, 213, 233);
		}else{
			DrawUtil.drawRect(g, 455, 384+(shopY+2)*50, 128, 36, 2, 0XFFFF00);
		}
		
		drawNum(g, 10, 103,452);//TODO 添加数字
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
			g.drawImage(achievement_long, 247, 114+i*83, TopLeft);//Y坐标相差83
			g.drawImage(archivement_hoof, 539, 130+i*83, TopLeft);
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
	public void showHelp(Graphics g,int helpIndex) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_big = Resource.loadImage(Resource.id_shop_big);       //{137,108}
		Image game_help = Resource.loadImage(Resource.id_game_help);     //{214,18}
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);   //{17,498}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(shop_big, 137, 108, TopLeft);
		g.drawImage(game_help, 214,18, TopLeft);
		g.drawImage(achievement_out1, 17,498, TopLeft);
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

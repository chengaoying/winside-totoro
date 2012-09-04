package sheepwar;

import java.io.IOException;

import javax.microedition.lcdui.Image;

/**
 * 图片加载和 释放
 * @author Administrator
 *
 */
public class Resource implements Common {
	
	
	private static short NUMS = 0;
	
	public static short id_main_bg = NUMS++;
	public static short id_main_menu = NUMS++;
	
	public static short id_playing_menu = NUMS++;//游戏中 的界面
	public static short id_playing_cloudbig = NUMS++;
	public static short id_playing_cloudsmall = NUMS++;
	public static short id_playing_lawn = NUMS++;
	public static short id_playing_step = NUMS++;
	public static short id_playing_tree = NUMS++;
	public static short id_playing_stop = NUMS++;
	public static short id_game_bg = NUMS++;
	public static short id_playing_prop_memu = NUMS++;
	public static short id_playing_prop = NUMS++;      //左侧菜单商品栏
	public static short id_playing_lunzi = NUMS++;     //滑轮
	public static short id_playing_shenzi = NUMS++;    //绳子
	public static short id_playing_lift = NUMS++;      //羊的吊篮
	public static short id_playing_shenzi1 = NUMS++;
	public static short id_playing_sheep = NUMS++;
	public static short id_sheep_eye = NUMS++;
	public static short id_sheep_hand = NUMS++;
	public static short id_bomb = NUMS++;
	public static short id_wolf_run = NUMS++;
	public static short id_wolf_down = NUMS++;
	public static short id_balloon_blue = NUMS++;      
	public static short id_balloon_green = NUMS++;
	public static short id_balloon_multicolour = NUMS++;
	public static short id_balloon_red = NUMS++;
	public static short id_balloon_yellow = NUMS++;
	public static short id_balloon_yellowred = NUMS++;
	public static short id_ladder = NUMS++;
	
	
	public static short id_shop_balance = NUMS++;//商城界面
	public static short id_shop_big = NUMS++;
	public static short id_shop_figure = NUMS++;
	public static short id_shop_go_pay = NUMS++;
	public static short id_shop_midding = NUMS++;
	public static short id_shop_out_base = NUMS++;
	public static short id_shop_out = NUMS++;
	public static short id_shop_small_base = NUMS++;
	public static short id_shop_small = NUMS++;
	public static short id_price_quantity = NUMS++;
	public static short id_shop = NUMS++;
	
	public static short id_achievement = NUMS++;//成就系统
	public static short id_achievement_left_right = NUMS++;
	public static short id_achievement_left_right1 = NUMS++;
	public static short id_achievement_long = NUMS++;
	public static short id_achievement_long1 = NUMS++;
	public static short id_achievement_out1 = NUMS++;
	public static short id_achievement_points = NUMS++;
	public static short id_archivement_hoof = NUMS++;
	public static short id_archivement_hoof1 = NUMS++;
	public static short id_achievement_word = NUMS++;
	public static short id_achievement_left = NUMS++;
	public static short id_slash = NUMS++;				//分号，斜杠
	
	public static short id_current_ranking = NUMS++;//排行界面
	public static short id_ranking_option = NUMS++;
	public static short id_ranking_option1 = NUMS++;
	public static short id_ranking_stripe = NUMS++;
	public static short id_ranking = NUMS++;
	public static short id_ranking_show = NUMS++;
	public static short id_ranking_word = NUMS++;			//排行文字
	
	
	public static short id_game_help = NUMS++;     //游戏帮助
	
	public static String[] imagesrcs = {
		"/main_bg.jpg",
		"/main_menu.png",
		
		"/playing_menu.png",             //游戏中的 界面
		"/playing_cloudbig.png",
		"/playing_cloudsmall.png",
		"/playing_lawn.png",
		"/playing_step.png",
		"/playing_tree.png",
		"/playing_stop.png",
		"/game_bg.jpg",
		"/playing_prop_memu.png",
		"/playing_prop.png",
		"/playing_lunzi.png",//绳子“微分化”可以活动
		"/playing_shenzi.png",
		"/playing_lift.png",     //羊的吊篮
		"/playing_shenzi1.png",
		"/playing_sheep.png",
		"/sheep_eye.png",
		"/sheep_hand.png",
		"/bomb.png",
		"/wolf_run.png",
		"/wolf_down.png",
		"/balloon_blue.png",
		"/balloon_green.png",
		"/balloon_multicolour.png",
		"/balloon_red.png",
		"/balloon_yellow.png",
		"/balloon_yellowred.png",
		"/ladder.png",
		
		"/shop_balance.png",            //商城界面图片资源
		"/shop_big.png",
		"/shop_figure.png",
		"/shop_go_pay.png",
		"/shop_midding.png",
		"/shop_out_base.png",
		"/shop_out.png",
		"/shop_small_base.png",
		"/shop_small.png",
		"/price_quantity.png",
		"/shop.png",
		
		"/achievement.png",             //成就系统图片--big midding共享
		"/achievement_left_right.png",
		"/achievement_left_right1.png",
		"/achievement_long.png",
		"/achievement_long1.png",
		"/achievement_out1.png",
		"/achievement_points.png",
		"/archivement_hoof.png",
		"/archivement_hoof1.png",
		"/achievement_word.png",
		"/achievement_left.png",
		"/slash.png",
		
		"/current_ranking.jpg",       //排行图片资源
		"/ranking_option.png",
		"/ranking_option1.png",
		"/ranking_stripe.jpg",        //条纹图片
		"/ranking.png",
		"/ranking_show.jpg",
		"/ranking_word.png",
		
		
		"/game_help.png",              //游戏帮助
	};
	
	private static final Image[] images = new Image[NUMS];

	public static Image loadImage(int id){
		if(images[id]==null){
			try {
				images[id] = Image.createImage(imagesrcs[id]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images[id];
	}
	
	/*释放图片*/
	public static void freeImage(int id){
		images[id] = null;
	}
	
	/**
	 * 游戏帮助文字介绍
	 */
	public static String gameInfo[] = {   
		"【操作说明】#r上下方向键：控制玩家的移动。#r确定键：发射飞镖或无敌拳套。#r数字键1至8：使用道具。#r数字键0：退出游戏。#r" +
		"数字键9:游戏帮助。",
		
		"【道具说明】#r光闹钟：时间静止10秒。#r捕狼网：发射出的子弹碰到灰太狼就会张开一张网，#r大网内的灰太狼都会掉落。#r防狼套装：开启后得到5秒的无敌效果，抵御各种攻击。#r驱狼光波:发出一道十万伏特的电流，电晕碰到的灰太狼,持续5秒。#r替身玩偶：增加一条命。#r驱散竖琴：使用后清除所有的梯子或者正在推石头的灰太狼。#r速度提升液：使用后增加喜羊羊的移动速度，持续30秒。#r强力磁石：击落所有空中的灰太狼。",
		
		"【游戏简介】#r喜羊羊大战灰太狼是一款闯关类游戏，#r总共有15关。玩家控制喜羊羊击落一定数量的灰太狼即可过关。#r此外玩家还可以在道具商城内购买各种道具来获得更有趣的体验。#r除了闯关外，游戏中还推出了成就系统和排行榜，#r增加了玩家在游戏的过程中动力和目标。",
		"",
	};

}

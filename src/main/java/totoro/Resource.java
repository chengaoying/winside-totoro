package totoro;

import java.io.IOException;

import javax.microedition.lcdui.Image;

/**
 * 图片资源和武器信息等
 * @author Administrator
 *
 */
public class Resource implements Common {
	
	
	private static short NUMS = 0;
	public static short id_bg = NUMS++;
	public static short id_text = NUMS++;
	
	public static short id_main_bg = NUMS++;
	public static short id_main_button = NUMS++;
	public static short id_main_button2 = NUMS++;
	public static short id_main_buy = NUMS++;
	public static short id_main_coin = NUMS++;
	public static short id_main_num = NUMS++;
	public static short id_main_text = NUMS++;
	public static short id_main_totoro = NUMS++;
	public static short id_main_upgrade = NUMS++;
	
	public static short id_own_totoro1 = NUMS++;
	public static short id_own_totoro1_bomb = NUMS++;
	
	public static short id_game_bg_1 = NUMS++;
	public static short id_game_bg_1_hill = NUMS++;
	public static short id_game_bg_1_way = NUMS++;
	public static short id_game_info_bg = NUMS++;
	public static short id_game_info_head = NUMS++;
	public static short id_game_blood_bg = NUMS++;
	
	public static short id_sky_spirits_1 = NUMS++;
	public static short id_sky_spirits_2 = NUMS++;
	public static short id_sky_spirits_3 = NUMS++;
	public static short id_sky_boss_1 = NUMS++;
	public static short id_sky_boss_2 = NUMS++;
	
	public static short id_sky_spirit_bomb_1 = NUMS++;
	public static short id_sky_boss_bomb_1 = NUMS++;
	public static short id_sky_boss_bomb_2 = NUMS++;
	
	public static String[] imagesrcs = {
		"/init/bg.jpg",
		"/init/text.png",
		
		"/main/main_bg.jpg",
		"/main/main_button.png",
		"/main/main_button2.png",
		"/main/main_buy.png",
		"/main/main_coin.png",
		"/main/main_num.png",
		"/main/main_text.png",
		"/main/main_totoro.png",
		"/main/main_upgrade.png",
		
		"/own/own_totoro1.png",
		"/own/own_totoro1_bomb.png",
		
		"/game/sky/sky_bg.jpg",
		"/game/sky/sky_bg_hill.png",
		"/game/sky/sky_bg_way.png",
		"/game/info_bg.png",
		"/game/info_head.png",
		"/game/blood_bg.png",
		
		"/spirit/sky/spirit_1.png",
		"/spirit/sky/spirit_2.png",
		"/spirit/sky/spirit_3.png",
		"/spirit/sky/boss_1.png",
		"/spirit/sky/boss_2.png",

		"/spirit/sky/spirit_bomb_1.png",
		"/spirit/sky/boss_bomb_1.png",
		"/spirit/sky/boss_bomb_2.png",
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
	
	public static void clearInit(){
		images[id_bg] = null;
		images[id_text] = null;
	}
	
	/*释放主界面图片*/
	public static void clearMain(){
		images[id_main_bg] = null;
		images[id_main_button] = null;
		images[id_main_button2] = null;
		images[id_main_buy] = null;
		images[id_main_coin] = null;
		images[id_main_num] = null;
		images[id_main_text] = null;
		images[id_main_totoro] = null;
		images[id_main_upgrade] = null;
	}
	
	/*释放游戏中的图片*/
	public static void clearGame(){
		images[id_own_totoro1] = null;
		images[id_own_totoro1_bomb] = null;
		
		images[id_game_bg_1] = null;
		images[id_game_bg_1_hill] = null;
		images[id_game_bg_1_way] = null;
		images[id_game_info_bg] = null;
		images[id_game_info_head] = null;
		images[id_game_blood_bg] = null;
		
		
		images[id_sky_spirits_1] = null;
		images[id_sky_spirits_2] = null;
		images[id_sky_spirits_3] = null;
		images[id_sky_boss_1] = null;
		
		images[id_sky_spirit_bomb_1] = null;
		images[id_sky_boss_bomb_1] = null;
	}
	
	
}

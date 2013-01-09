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
	
	public static short id_ice_game_bg = NUMS++;
	public static short id_ice_game_bg_hill = NUMS++;
	public static short id_ice_game_bg_way = NUMS++;
	
	public static short id_game_info_bg = NUMS++;
	public static short id_game_info_head = NUMS++;
	public static short id_game_blood_bg = NUMS++;
	public static short id_game_bg_up = NUMS++;
	public static short id_game_head_shadow = NUMS++;
	
	public static short id_sky_spirits_1 = NUMS++;
	public static short id_sky_spirits_2 = NUMS++;
	public static short id_sky_spirits_3 = NUMS++;
	public static short id_sky_battery = NUMS++;
	public static short id_sky_boss_1 = NUMS++;
	public static short id_sky_boss_2 = NUMS++;
	public static short id_sky_spirit_bomb_1 = NUMS++;
	public static short id_sky_boss_bomb_1 = NUMS++;
	public static short id_sky_boss_bomb_2 = NUMS++;
	
	public static short id_burrow_spirit_1 = NUMS++;
	public static short id_burrow_spirit_2 = NUMS++;
	public static short id_burrow_spirit_3 = NUMS++;
	public static short id_burrow_spirit_4 = NUMS++;
	public static short id_burrow_boss_1 = NUMS++;
	public static short id_burrow_boss_2 = NUMS++;
	public static short id_burrow_spirit_bomb_2 = NUMS++;
	
	public static short id_ice_battery = NUMS++;
	public static short id_ice_spirit_1 = NUMS++;
	public static short id_ice_spirit_2 = NUMS++;
	public static short id_ice_boss_1 = NUMS++;
	public static short id_ice_boss_2 = NUMS++;
	public static short id_ice_spirit_bomb_1 = NUMS++;
	public static short id_ice_spirit_bomb_2 = NUMS++;
	public static short id_ice_battery_bomb = NUMS++;
	
	public static short id_lava_boss_1 = NUMS++;
	public static short id_lava_boss_2 = NUMS++;
	public static short id_lava_spirit_1 = NUMS++;
	public static short id_lava_spirit_2 = NUMS++;
	public static short id_lava_spirit_bomb = NUMS++;
	public static short id_lava_battery_bomb = NUMS++;
	public static short id_lava_battery = NUMS++;
	
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
		
		"/game/ice/ice_bg.jpg",
		"/game/ice/ice_bg_hill.png",
		"/game/ice/ice_bg_way.png",
		
		"/game/info_bg.png",
		"/game/info_head.png",
		"/game/blood_bg.png",
		"/game/bg_up.jpg",
		"/game/head_shadow.png",
		
		"/spirit/sky/spirit_1.png",
		"/spirit/sky/spirit_2.png",
		"/spirit/sky/spirit_3.png",
		"/spirit/sky/battery.png",
		"/spirit/sky/boss_1.png",
		"/spirit/sky/boss_2.png",
		"/spirit/sky/spirit_bomb_1.png",
		"/spirit/sky/boss_bomb_1.png",
		"/spirit/sky/boss_bomb_2.png",
		
		"/spirit/burrow/spirit_1.png",
		"/spirit/burrow/spirit_2.png",
		"/spirit/burrow/spirit_3.png",
		"/spirit/burrow/spirit_4.png",
		"/spirit/burrow/boss_1.png",
		"/spirit/burrow/boss_2.png",
		"/spirit/burrow/spirit_bomb_2.png",
		
		"/spirit/ice/battery.png",
		"/spirit/ice/spirit_1.png",
		"/spirit/ice/spirit_2.png",
		"/spirit/ice/boss_1.png",
		"/spirit/ice/boss_2.png",
		"/spirit/ice/spirit_bomb_1.png",
		"/spirit/ice/spirit_bomb_2.png",
		"/spirit/ice/battery_bomb.png",
		
		"/spirit/lava/boss_1.png",
		"/spirit/lava/boss_2.png",
		"/spirit/lava/spirit_1.png",
		"/spirit/lava/spirit_2.png",
		"/spirit/lava/spirit_head_bomb.png",
		"/spirit/lava/battery_bomb.png",
		"/spirit/lava/battery.png",
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
		
		images[id_ice_game_bg] = null;
		images[id_ice_game_bg_hill] = null;
		images[id_ice_game_bg_way] = null;
		
		images[id_game_info_bg] = null;
		images[id_game_info_head] = null;
		images[id_game_blood_bg] = null;
		images[id_game_bg_up] = null;
		images[id_game_head_shadow] = null;
		
		
		images[id_sky_spirits_1] = null;
		images[id_sky_spirits_2] = null;
		images[id_sky_spirits_3] = null;
		images[id_sky_boss_1] = null;
		
		images[id_sky_spirit_bomb_1] = null;
		images[id_sky_boss_bomb_1] = null;
	}
	
	
}

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
	
	public static short id_selectInterface_head = NUMS++;
	public static short id_selectInterface_head2 = NUMS++;
	public static short id_selectInterface_info_bg = NUMS++;
	public static short id_selectInterface_lock = NUMS++;
	public static short id_selectInterface_slant = NUMS++;
	public static short id_selectInterface_text = NUMS++;
	public static short id_selectInterface_text2 = NUMS++;
	
	public static short id_pink_totoro = NUMS++;
	public static short id_pink_totoro_bomb = NUMS++;
	public static short id_yellow_totoro = NUMS++;
	public static short id_yellow_totoro_bomb1 = NUMS++;
	public static short id_yellow_totoro_bomb2 = NUMS++;
	public static short id_yellow_totoro_bomb3 = NUMS++;
	public static short id_blue_totoro = NUMS++;
	public static short id_blue_totoro_bomb1 = NUMS++;
	public static short id_blue_totoro_bomb2 = NUMS++;
	public static short id_blue_totoro_bomb3 = NUMS++;
	public static short id_blue_totoro_bomb4 = NUMS++;
	public static short id_black_totoro = NUMS++;
	public static short id_black_totoro_bomb1 = NUMS++;
	public static short id_black_totoro_bomb2 = NUMS++;
	public static short id_black_totoro_bomb3 = NUMS++;
	
	public static short id_sky_game_bg = NUMS++;
	public static short id_sky_game_hill = NUMS++;
	public static short id_sky_game_way = NUMS++;
	public static short id_burrow_game_center = NUMS++;
	public static short id_burrow_game_down = NUMS++;
	public static short id_burrow_game_up = NUMS++;
	public static short id_burrow_game_net = NUMS++;
	public static short id_burrow_game_bubble = NUMS++;
	public static short id_ice_game_bg = NUMS++;
	public static short id_ice_game_bg_hill = NUMS++;
	public static short id_ice_game_bg_way = NUMS++;
	public static short id_lava_game_center_2 = NUMS++;
	public static short id_lava_game_center = NUMS++;
	public static short id_lava_game_down = NUMS++;
	public static short id_lava_game_lava = NUMS++;
	public static short id_lava_game_up = NUMS++;
	
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
	public static short id_sky_boss_1_skill = NUMS++;
	public static short id_sky_boss_2_skill = NUMS++;
	
	public static short id_burrow_spirit_1 = NUMS++;
	public static short id_burrow_spirit_2 = NUMS++;
	public static short id_burrow_spirit_3 = NUMS++;
	public static short id_burrow_spirit_4 = NUMS++;
	public static short id_burrow_boss_1 = NUMS++;
	public static short id_burrow_boss_2 = NUMS++;
	public static short id_burrow_spirit_bomb_2 = NUMS++;
	public static short id_burrow_boss_2_skill = NUMS++;
	
	public static short id_ice_battery = NUMS++;
	public static short id_ice_spirit_1 = NUMS++;
	public static short id_ice_spirit_2 = NUMS++;
	public static short id_ice_boss_1 = NUMS++;
	public static short id_ice_boss_2 = NUMS++;
	public static short id_ice_spirit_bomb_1 = NUMS++;
	public static short id_ice_spirit_bomb_2 = NUMS++;
	public static short id_ice_battery_bomb = NUMS++;
	public static short id_ice_boss_1_fire = NUMS++;
	public static short id_ice_boss_1_fire_object = NUMS++;
	public static short id_ice_boss_1_fire_change = NUMS++;
	public static short id_ice_boss_1_fire_attack = NUMS++;
	
	public static short id_lava_boss_1 = NUMS++;
	public static short id_lava_boss_2 = NUMS++;
	public static short id_lava_spirit_1 = NUMS++;
	public static short id_lava_spirit_2 = NUMS++;
	public static short id_lava_spirit_bomb = NUMS++;
	public static short id_lava_battery_bomb = NUMS++;
	public static short id_lava_battery = NUMS++;
	
	public static short id_pause_bg = NUMS++;
	public static short id_pause_text = NUMS++;
	
	public static short id_fail_bg = NUMS++;
	public static short id_fail_num = NUMS++;
	public static short id_fail_text = NUMS++;
	public static short id_fail = NUMS++;
	
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
		
		"/selectInterface/head.png",
		"/selectInterface/head2.png",
		"/selectInterface/info_bg.png",
		"/selectInterface/lock.png",
		"/selectInterface/slant.png",
		"/selectInterface/text.png",
		"/selectInterface/text2.png",
		
		"/own/pink/totoro.png",
		"/own/pink/bomb.png",
		"/own/yellow/totoro.png",
		"/own/yellow/bomb1.png",
		"/own/yellow/bomb2.png",
		"/own/yellow/bomb3.png",
		"/own/blue/totoro.png",
		"/own/blue/bomb1.png",
		"/own/blue/bomb2.png",
		"/own/blue/bomb3.png",
		"/own/blue/bomb4.png",
		"/own/black/totoro.png",
		"/own/black/bomb1.png",
		"/own/black/bomb2.png",
		"/own/black/bomb3.png",
		
		"/game/sky/sky_bg.jpg",
		"/game/sky/sky_hill.png",
		"/game/sky/sky_way.png",
		"/game/burrow/center.png",
		"/game/burrow/down.png",
		"/game/burrow/up.png",
		"/game/burrow/net.png",
		"/game/burrow/bubble.png",
		"/game/ice/ice_bg.jpg",
		"/game/ice/ice_bg_hill.png",
		"/game/ice/ice_bg_way.png",
		"/game/lava/center_2.png",
		"/game/lava/center.png",
		"/game/lava/down.png",
		"/game/lava/lava.png",
		"/game/lava/up.png",
		
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
		"/spirit/sky/boss_1_skill.png",
		"/spirit/sky/boss_2_skill.png",
		
		"/spirit/burrow/spirit_1.png",
		"/spirit/burrow/spirit_2.png",
		"/spirit/burrow/spirit_3.png",
		"/spirit/burrow/spirit_4.png",
		"/spirit/burrow/boss_1.png",
		"/spirit/burrow/boss_2.png",
		"/spirit/burrow/spirit_bomb_2.png",
		"/spirit/burrow/boss_2_skill.png",
		
		"/spirit/ice/battery.png",
		"/spirit/ice/spirit_1.png",
		"/spirit/ice/spirit_2.png",
		"/spirit/ice/boss_1.png",
		"/spirit/ice/boss_2.png",
		"/spirit/ice/spirit_bomb_1.png",
		"/spirit/ice/spirit_bomb_2.png",
		"/spirit/ice/battery_bomb.png",
		"/spirit/ice/boss_1_fire.png",
		"/spirit/ice/boss_1_fire_object.png",
		"/spirit/ice/boss_1_fire_change.png",
		"/spirit/ice/boss_1_fire_attack.png",
		
		"/spirit/lava/boss_1.png",
		"/spirit/lava/boss_2.png",
		"/spirit/lava/spirit_1.png",
		"/spirit/lava/spirit_2.png",
		"/spirit/lava/spirit_head_bomb.png",
		"/spirit/lava/battery_bomb.png",
		"/spirit/lava/battery.png",
		
		"/pause/pause_bg.png",
		"/pause/pause_text.png",
		
		"/fail/fail_bg.png",
		"/fail/fail_num.png",
		"/fail/fail_text.png",
		"/fail/fail.png",
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
		images[id_pink_totoro] = null;
		images[id_pink_totoro_bomb] = null;
		images[id_yellow_totoro] = null;
		images[id_yellow_totoro_bomb1] = null;
		images[id_yellow_totoro_bomb2] = null;
		images[id_yellow_totoro_bomb3] = null;
		images[id_blue_totoro] = null;
		images[id_blue_totoro_bomb1] = null;
		images[id_blue_totoro_bomb2] = null;
		images[id_blue_totoro_bomb3] = null;
		images[id_blue_totoro_bomb4] = null;
		images[id_black_totoro] = null;
		images[id_black_totoro_bomb1] = null;
		images[id_black_totoro_bomb2] = null;
		images[id_black_totoro_bomb3] = null;
		
		images[id_sky_game_bg] = null;
		images[id_sky_game_hill] = null;
		images[id_sky_game_way] = null;
		images[id_burrow_game_center] = null;
		images[id_burrow_game_down] = null;
		images[id_burrow_game_up] = null;
		images[id_burrow_game_net] = null;
		images[id_burrow_game_bubble] = null;
		images[id_ice_game_bg] = null;
		images[id_ice_game_bg_hill] = null;
		images[id_ice_game_bg_way] = null;
		images[id_lava_game_center_2] = null;
		images[id_lava_game_center] = null;
		images[id_lava_game_down] = null;
		images[id_lava_game_lava] = null;
		images[id_lava_game_up] = null;
		
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
		images[id_sky_boss_1_skill] = null;
		images[id_sky_boss_2_skill] = null;
		images[id_burrow_boss_2_skill] = null;
		images[id_ice_boss_1_fire] = null;
		images[id_ice_boss_1_fire_attack] = null;
		images[id_ice_boss_1_fire_change] = null;
		images[id_ice_boss_1_fire_object] = null;
	}
	
	public static void clearSubMenuPic(){
		images[id_pause_bg] = null;
		images[id_pause_text] = null;
		images[id_main_button] = null;
	}
	
	public static void clearGameFail(){
		images[id_fail_bg] = null;
		images[id_fail_num] = null;
		images[id_fail_text] = null;
		images[id_fail] = null;
		images[id_main_button2] = null;
	}
	
	public static void clearSelectInterface(){
		images[id_main_button] = null;
		images[id_selectInterface_head] = null;
		images[id_selectInterface_head2] = null;
		images[id_selectInterface_info_bg] = null;
		images[id_selectInterface_lock] = null;
		images[id_selectInterface_slant] = null;
		images[id_selectInterface_text] = null;
		images[id_selectInterface_text2] = null;
		
		images[id_sky_game_bg] = null;
		images[id_yellow_totoro] = null;
		images[id_yellow_totoro_bomb1] = null;
		images[id_pink_totoro] = null;
		images[id_pink_totoro_bomb] = null;
		images[id_blue_totoro] = null;
		images[id_blue_totoro_bomb1] = null;
		images[id_black_totoro] = null;
		images[id_black_totoro_bomb1] = null;
	}
	
}

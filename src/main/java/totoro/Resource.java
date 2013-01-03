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
		"/game/game_bg1.jpg",
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
}

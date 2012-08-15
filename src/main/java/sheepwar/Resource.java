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
	public static short id_game_bg = NUMS++;
	
	public static String[] imagesrcs = {
		"/main_bg.jpg",
		"/main_menu.png",
		"/playing_menu.png",//游戏中的 界面
		"/playing_cloudbig.png",
		"/playing_cloudsmall.png",
		"/playing_lawn.png",
		"/playing_step.png",
		"/playing_tree.png",
		"/game_bg.jpg",
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

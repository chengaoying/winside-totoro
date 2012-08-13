package sheepwar;

import java.io.IOException;

import javax.microedition.lcdui.Image;

/**
 * Õº∆¨º”‘ÿ∫Õ  Õ∑≈
 * @author Administrator
 *
 */
public class Resource {
	
	
	private static short NUMS = 0;
	
	public static short id_main_bg = NUMS++;
	public static short id_main_menu = NUMS++;
	
	public static String[] imagesrcs = {
		"/main_bg.jpg",
		"/main_menu.png",
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
	
	public static void freeImage(int id){
		images[id] = null;
	}

}

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
	
	public static String[] imagesrcs = {};
	
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

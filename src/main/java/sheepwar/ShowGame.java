package sheepwar;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * ÏÔÊ¾Í¼Ïñ
 * @author Administrator
 *
 */
public class ShowGame implements Common {
	
	public int menuAxis[][] = 
	{ { 523, 243 }, 
	  { 466, 288 }, 
	  { 523, 333 },
	  { 466, 378 }, 
	  { 523, 423 },
	  { 466, 468 },
	};
	
	public void drawMainMenu(Graphics g, int index){
		Image main_bg = Resource.loadImage(Resource.id_main_bg);
		Image main_menu = Resource.loadImage(Resource.id_main_menu);
		
		g.drawImage(main_bg, 0, 0, 0);
		for (int i = 0; i < menuAxis.length; ++i) {
			g.drawRegion(main_menu, (index != i) ? main_menu.getWidth()/2 : 0, 
					i*main_menu.getHeight()/6, main_menu.getWidth()/2, main_menu.getHeight()/6, 
					0, menuAxis[i][0], menuAxis[i][1], 0);
		}
	}

}

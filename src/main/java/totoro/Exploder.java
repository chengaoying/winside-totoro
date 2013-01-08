package totoro;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;

/**
 * 爆炸燃烧效果类
 * @author Administrator
 */
public class Exploder implements Common {
	private int mapx;
	private int mapy;
	private int[] frame={0,1,0,1,2,3,4,5,6,7,};
	private int i;
	
	public Exploder(int mapx, int mapy){
		this.mapx = mapx;
		this.mapy = mapy;
	}
	
	/*爆炸效果*/
	public void drawExplode(SGraphics g, StateGame stateGame) {
		/*if (i <7) {	
			try {
				Image burstImage = Resource.loadImage(Resource.id_burn);
				g.drawRegion(burstImage, frame[i] * burstImage.getWidth() / 8, 0, burstImage.getWidth() / 8,
						burstImage.getHeight(), 0, mapx, mapy, 20);
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}
}

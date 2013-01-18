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
	private int[] frame={0,1,2};
	private int i;
	
	public Exploder(int mapx, int mapy){
		this.mapx = mapx;
		this.mapy = mapy;
	}
	
	/*爆炸效果*/
	public void drawExplode(SGraphics g, StateGame stateGame) {
		if (i < 3) {	
			try {
				Image burstImage = Resource.loadImage(Resource.id_game_explosion);
				
				g.drawRegion(burstImage, frame[i] * burstImage.getWidth() / 3, 0, burstImage.getWidth() / 3,
						burstImage.getHeight(), 0, mapx, mapy, 20);
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

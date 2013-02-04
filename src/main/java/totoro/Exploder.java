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
	//private int[] frame={0,1,2};
	private int[] missileFrame={0,1,2,3};
	private int i;
	public int score, tempy;
	
	public Exploder(int mapx, int mapy){
		this.mapx = mapx;
		this.mapy = mapy;
		this.tempy = mapy;
	}

	/*爆炸效果*/
	public void drawExplode(SGraphics g, StateGame stateGame) {
		/*if (i < 3) {	
			try {
				Image burstImage = Resource.loadImage(Resource.id_game_explosion);
				int w = burstImage.getWidth() / 3, h = burstImage.getHeight();
				g.drawRegion(burstImage, frame[i] * w, 0, w,h, 0, mapx, mapy, 20);
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		drawMissileExplode(g, stateGame);
	}
	
	public void drawMissileExplode(SGraphics g, StateGame stateGame){
		if (i < 4) {	
			try {
				Image burstImage = Resource.loadImage(Resource.id_prop_missile_effect);
				int w = burstImage.getWidth()/4, h = burstImage.getHeight();
				g.drawRegion(burstImage, missileFrame[i] * w, 0, w,h, 0, mapx, mapy, 20);
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*动态得分效果*/
	public void showScore(TotoroGameEngine engine, SGraphics g, int score) {
		if(tempy-mapy<50){
			engine.setFont(25, true);
			int col = g.getColor();
			g.setColor(0xff0000);
			g.drawString("+"+String.valueOf(score), mapx, mapy, 20);
			g.setColor(col);
			mapy -= 10;
			engine.setDefaultFont();
		}
	}
}

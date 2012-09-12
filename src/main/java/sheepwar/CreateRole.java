package sheepwar;

import javax.microedition.lcdui.Image;
import cn.ohyeah.stb.game.SGraphics;

/**
 * 创建玩家角色类
 * @author Administrator
 *
 */
public class CreateRole implements Common {

	/*羊*/
	public Role createSheep() {
		Role role = new Role();
		role.mapx = 366; 
		role.mapy = 307;
		role.width = 49;
		role.height = 59;
		role.speed = 5;
		return role;
	}

	public void showSheep(SGraphics g, Role role) {
		Image playing_sheep = Resource.loadImage(Resource.id_playing_sheep); 
		Image sheep_eye = Resource.loadImage(Resource.id_sheep_eye);
		Image sheep_hand = Resource.loadImage(Resource.id_sheep_hand);

		g.drawRegion(sheep_hand, 0, 0, sheep_hand.getWidth(),
				sheep_hand.getHeight(), 0, 359 - 14,
				154 + 50 + (role.mapy - 154), 20); 

		g.drawRegion(playing_sheep, 0, 0, playing_sheep.getWidth(),
				playing_sheep.getHeight(), 0, 361, 154 + 2 + (role.mapy - 154),
				20);

		g.drawRegion(sheep_eye, 0, 0, sheep_eye.getWidth(),
				sheep_eye.getHeight(), 0, 371 - 13,
				154 + 23 + (role.mapy - 154), 20);
	}


}

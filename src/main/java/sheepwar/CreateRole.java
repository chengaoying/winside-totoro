package sheepwar;

import javax.microedition.lcdui.Image;
import cn.ohyeah.stb.game.SGraphics;

/**
 * 创建玩家角色类
 * @author Administrator
 *
 */
public class CreateRole implements Common {

	/*创建玩家角色*/
	public Role createSheep() {
		Role role = new Role();
		role.mapx = 366; 
		role.mapy = 309;
		role.width = 49;
		role.height = 59;
		role.speed = 5;
		role.lifeNum = 3;
		role.scores = 0;
		role.eatNum = 0;
		return role;
	}
	
	/*玩家复活*/
	public Role reviveSheep() {
		Role role = new Role();
		role.mapx = 366; 
		role.mapy = 309;
		role.width = 49;
		role.height = 59;
		role.speed = 5;
		role.lifeNum = StateGame.lifeNum;
		role.scores = StateGame.scores;
		role.eatNum = StateGame.eatNum;
		System.out.println("lifeNum:"+role.lifeNum);
		System.out.println("scores:"+role.scores);
		System.out.println("eatNum:"+role.eatNum);
		return role;
	}
	
	public void showSheep(SGraphics g, Role role) {
		Image playing_sheep = Resource.loadImage(Resource.id_playing_sheep); 
		Image sheep_eye = Resource.loadImage(Resource.id_sheep_eye);
		Image sheep_hand = Resource.loadImage(Resource.id_sheep_hand);
		if(role.status == ROLE_ALIVE){
			g.drawRegion(sheep_hand, 0, 0, sheep_hand.getWidth(),
					sheep_hand.getHeight(), 0, 359 - 14,
					50 + role.mapy, 20); 

			g.drawRegion(playing_sheep, 0, 0, playing_sheep.getWidth(),
					playing_sheep.getHeight(), 0, 361, role.mapy,
					20);

			g.drawRegion(sheep_eye, 0, 0, sheep_eye.getWidth(),
					sheep_eye.getHeight(), 0, 371 - 13,
					23 + role.mapy, 20);
			
		}else if(role.status == ROLE_DEATH){
			if(role.mapy<=466){
				role.mapy += 4*role.speed;
				g.drawRegion(playing_sheep, 0, 0, playing_sheep.getWidth(),
						playing_sheep.getHeight(), 0, 361,  role.mapy,	20);
			}else if(role.lifeNum>0){
				System.out.println("玩家复活");
				StateGame.own = reviveSheep();
			}
		}
	}
}

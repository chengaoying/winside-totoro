package totoro;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;

/**
 * 显示物体对象
 * @author jackey
 *
 */
public class MoveObjectShow {

	private MoveObjectShow(){}
	private static MoveObjectShow instance;
	public static MoveObjectShow getInstance(){
		if(instance==null){
			instance = new MoveObjectShow();
		}
		return instance;
	}
	
	public void showPlayer(SGraphics g, MoveObject player){
		Image playerImg = Resource.loadImage(Resource.id_own_totoro1);
		int playerW = playerImg.getWidth()/3, playerH = playerImg.getHeight();
		g.drawRegion(playerImg, player.frame*playerW, 0, playerW, playerH, 0, player.mapx, player.mapy, 20);
		player.frame = (player.frame+1)%3;
	}
	
	public void showBombs(SGraphics g, Vector bombs){
		Image bomb = Resource.loadImage(Resource.id_own_totoro1_bomb);
		int bombW = bomb.getWidth()/5, bombH = bomb.getHeight();
		for(int i=bombs.size()-1;i>=0;i--){
			MoveObject object = (MoveObject) bombs.elementAt(i);
			g.drawRegion(bomb, 0, 0, bombW, bombH, 0, object.mapx, object.mapy, 20);
			object.mapx += object.speedX;
		}
	}
}

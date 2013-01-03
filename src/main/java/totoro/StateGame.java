package totoro;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateGame implements Common{
	
	private TotoroGameEngine engine;
	public StateGame(TotoroGameEngine engine){
		this.engine = engine;
	}
	
	public MoveObjectFactory factory;
	public MoveObjectShow objectShow;
	public MoveObject player;
	
	private long bombStart, bombEnd;
	private int bombInterval = 200;
	
	public void handleKey(KeyState keyState){
		if(keyState.containsMoveEventAndRemove(KeyCode.UP)){
			move(0);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.DOWN)){
			move(1);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.RIGHT)){
			move(2);
		}else if(keyState.containsMoveEventAndRemove(KeyCode.LEFT)){
			move(3);
		}
		
	}
	
	public void execute(){
		bombEnd = getTime();
		if(bombEnd - bombStart > bombInterval){
			factory.createBomb(player);
			bombStart = getTime();
		}
		removeOutsideObject();
	}
	
	private void removeOutsideObject() {
		for(int i=0;i<factory.bombs.size();i++){
			MoveObject mo = (MoveObject) factory.bombs.elementAt(i);
			if(mo.mapx + mo.width > ScrW){
				factory.bombs.removeElement(mo);
			}
		}
		System.out.println("bomb num:"+factory.bombs.size());
	}

	public void show(SGraphics g){
		drawGameInterface(g);
		objectShow.showPlayer(g, player);
		objectShow.showBombs(g, factory.bombs);
	}
	
	public void drawGameInterface(SGraphics g){
		Image game_bg = Resource.loadImage(Resource.id_game_bg_1);
		g.drawImage(game_bg, 0, 0, 20);
	}
	
	private long getTime(){
		return System.currentTimeMillis();
	}
	
	private void move(int towards) {
		switch (towards) {
		case 0: // 往上--主角
			if(player.mapy < player.speedY){
				player.mapy = 0;
			}else{
				player.mapy -= player.speedY;
			}
			break;
		case 1: // 往下--主角
			if((ScrH - (player.mapy+player.height)) < player.speedY){
				player.mapy = ScrH - player.height;
			}else{
				player.mapy += player.speedY;
			}
			break;
		case 2: // 往右--主角
			if(ScrW - ((player.mapx+player.width)) < player.speedX){
				player.mapx = ScrW - player.width;
			}else{
				player.mapx += player.speedX;
			}
			break;
		case 3:
			if(player.mapx < player.speedX){
				player.mapx = 0;
			}else{
				player.mapx -= player.speedX;
			}
			break;
		}
	}
}

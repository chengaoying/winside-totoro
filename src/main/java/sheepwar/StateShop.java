package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.ui.TextView;

public class StateShop implements Common{

	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	private int shopX,  shopY;
	
	public void processShop(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleShop(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showShop(g);
					engine.flushGraphics();
					System.gc();
					int sleepTime = (int)(125-(System.currentTimeMillis()-t1));
					if (sleepTime <= 0) {
						Thread.sleep(0);
					}
					else {
						Thread.sleep(sleepTime);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			clear();
		}
		
	}

	private void showShop(SGraphics g) {

		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_balance = Resource.loadImage(Resource.id_shop_balance);//{46,454}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{29,103}
		Image shop_go_pay = Resource.loadImage(Resource.id_shop_go_pay);//{457,381}
		Image shop_midding = Resource.loadImage(Resource.id_shop_midding);//{434,103}
		Image shop_out_base = Resource.loadImage(Resource.id_shop_out_base);
		Image shop_out = Resource.loadImage(Resource.id_shop_out);//{457,429}
		Image shop_small_base = Resource.loadImage(Resource.id_shop_small_base);
		Image shop_small = Resource.loadImage(Resource.id_shop_small);
		Image price_quantity = Resource.loadImage(Resource.id_price_quantity);
		Image shop = Resource.loadImage(Resource.id_shop);//{217,18}
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		g.drawImage(game_bg, 0, 0, 20);
		g.drawImage(shop, 217, 18, 20);
		g.drawImage(shop_big, 29, 103, 20);
		g.drawImage(shop_balance, 46, 454, 20);
	
		int x =42, y = 120, spaceX = 15, spaceY = 8;//
		for(int i=0;i<4;i++){
			for(int j=0;j<2;j++){
				g.drawRegion(shop_small_base, 0, 0, shop_small_base.getWidth(), shop_small_base.getHeight(),
						0, x+(spaceX+shop_small_base.getWidth())*j, y+(spaceY+shop_small_base.getHeight())*i, 20);
			}
		}
		g.drawImage(shop_midding, 434, 103, 20);
		for(int i=0;i<2;i++){             //midding下的按钮阴影
			g.drawRegion(shop_out_base, 0, 0, shop_out_base.getWidth(), shop_out_base.getHeight(), 
					0, 457, 381+(spaceY+shop_out_base.getHeight())*i, 20);
			}
		int mapx=37,mapy=112;       //被选中后出现阴影效果的坐标
		int propMessageX = 444,propMessageY = 140,propMessageW = 162,propMessageH = 220 ;		//propMessageX 信息出现的横纵坐标和显示的宽高
		int rowSpace = 5;   	//信息文字间的行距
		for(int i=0;i<4;i++){
		     for(int j=0;j<2;j++){
				if(shopX==j && shopY==i){
					g.drawRegion(shop_small, 0, 0, shop_small.getWidth(), shop_small.getHeight(),
							0, mapx+(spaceX+shop_small.getWidth())*j, mapy+(spaceY+shop_small.getHeight())*i, 20);
					g.drawImage(price_quantity, mapx+(spaceX+shop_small.getWidth())*j+65, 
							mapy+(spaceY+shop_small.getHeight())*i+12, 20);
					g.drawRegion(playing_prop, getIndex(j, i)*playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8, playing_prop.getHeight(), 0,
							mapx+(spaceX+shop_small.getWidth())*j+8, mapy+(spaceY+shop_small.getHeight())*i+9, 20);
					drawNum(g, 111, mapx+(spaceX+shop_small.getWidth())*j+119, mapy+(spaceY+shop_small.getHeight())*i+11);
					drawNum(g, 333, mapx+(spaceX+shop_small.getWidth())*j+119, mapy+(spaceY+shop_small.getHeight())*i+36);
					g.setColor(0xffffff);				//设置字体颜色
					engine.setFont(19);	
					TextView.showMultiLineText(g, Resource.propIntroduce[shopY][shopX], rowSpace,
							propMessageX, propMessageY, propMessageW, propMessageH);
					engine.setDefaultFont();
				}else{
					g.drawRegion(shop_small, 0, 0, shop_small.getWidth(), shop_small.getHeight(), 0,
							x+(spaceX+shop_small.getWidth())*j, y+(spaceY+shop_small.getHeight())*i, 20);
					g.drawImage(price_quantity, x+(spaceX+shop_small.getWidth())*j+65, 
							y+(spaceY+shop_small.getHeight())*i+12, 20);
					g.drawRegion(playing_prop, getIndex(j, i)*playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8, playing_prop.getHeight(), 0,
							x+(spaceX+shop_small.getWidth())*j+8, y+(spaceY+shop_small.getHeight())*i+9, 20);
					drawNum(g, 111, x+(spaceX+shop_small.getWidth())*j+119, y+(spaceY+shop_small.getHeight())*i+11);
					drawNum(g, 333, x+(spaceX+shop_small.getWidth())*j+119, y+(spaceY+shop_small.getHeight())*i+36);
				}
			}
		}
		 if(shopX==2){          //充值和返回被选择的阴影效果
			 if(shopY==0){    //控制方向由左到右的入口方向
				 g.drawImage(shop_go_pay, 457-8, 381-5, 20);
			   	 g.drawImage(shop_out, 457, 429, 20);
			  }else{
				 g.drawImage(shop_go_pay, 457, 381, 20);
			   	 g.drawImage(shop_out, 457-8, 429-5, 20);
			 }
		    }else{
		    	g.drawImage(shop_go_pay, 457, 381, 20);
		   		g.drawImage(shop_out, 457, 429, 20);
		    }
		drawNum(g, 10, 103,452);                                          //TODO 添加数字
	
	}
	
	private void handleShop(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
			shopX = 0;shopY = 0;
		}else
		if (keyState.contains(KeyCode.UP)) {
			keyState.remove(KeyCode.UP);
			if(shopY>0){
				if(shopX==2){
					shopY=(shopY-1)%2;
				}else{
					shopY=(shopY-1)%4;
				}
			}
		}else
		if (keyState.contains(KeyCode.DOWN)) {
			keyState.remove(KeyCode.DOWN);
			if(shopY<4){
				if(shopX<2){
					shopY = (shopY+1)%4;
				}else{
					shopY = (shopY+1)%2;
				}
			}
		}else
		if (keyState.contains(KeyCode.LEFT)) {
			keyState.remove(KeyCode.LEFT);
			if(shopX>0){
				shopX = shopX-1;
			}
		}else
		if (keyState.contains(KeyCode.RIGHT)) {
			keyState.remove(KeyCode.RIGHT);
			if(shopX<2){
				shopX = (shopX+1)%3;
			}
			if(shopX==2){//当控制由左到右时，shopY置零
				shopY=0;
			}
		}else
		if (keyState.contains(KeyCode.OK)) {
			keyState.remove(KeyCode.OK);
			if(shopX==2 && shopY==0){//进入充值
				
			}
			if(shopX==2 && shopY==1){
				running = false;
				shopX = 0;shopY = 0;
			}
		}
	
	}
	
	private void drawNum(SGraphics g, int num, int x, int y) {
		Image imgNumeber = Resource.loadImage(Resource.id_shop_figure);
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(imgNumeber, (number.charAt(i) - '0') * imgNumeber.getWidth()/10, 0, 
					imgNumeber.getWidth()/10, imgNumeber.getHeight(), 0, x + i * (imgNumeber.getWidth()/10 + 1), y, 0);
		}
	}
	
	private int getIndex(int x, int y){    //取出对应商店商场左侧的位置
		if(y==0 && x==0)return 0;
		if(y==1 && x==0)return 1;
		if(y==2 && x==0)return 2;
		if(y==3 && x==0)return 3;
		if(y==0 && x==1)return 4;
		if(y==1 && x==1)return 5;
		if(y==2 && x==1)return 6;
		if(y==3 && x==1)return 7;
		if(x==2)        return 8;
		return -1;
	}

	
	private void clear() {
		Resource.freeImage(Resource.id_shop);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_big);
		Resource.freeImage(Resource.id_shop_figure);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_go_pay);
		Resource.freeImage(Resource.id_shop_midding);
		Resource.freeImage(Resource.id_shop_out);
		Resource.freeImage(Resource.id_price_quantity);
		Resource.freeImage(Resource.id_playing_prop);       
	}
}

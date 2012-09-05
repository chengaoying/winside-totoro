package sheepwar;

/**
 * 攻击类
 * @author Administrator
 *
 */
public class Attacks implements Common {
	
	private SheepWarGameEngine engine;
	public Attacks(SheepWarGameEngine e){
		engine = e;
	}
	
	/*普通飞镖攻击*/
	public void bompAttack(Role wolf,Role own){
		//System.out.println("weapon.bombs.size()------->: "+engine.weapon.bombs.size());
//		System.out.println("CreateRole.bubles.size()***:"+CreateRole.bubles.size());
		if(engine.weapon.bombs.size()>=0){
			Weapon bomb	=	null;
			for (int i = 0; i < engine.weapon.bombs.size(); i++) {
				bomb = (Weapon) engine.weapon.bombs.elementAt(i);
				int bombY=bomb.mapy;
//				bombH = bomb.mapy+bomb.height/2+10;
				Role buble	=	null;     //气球
				//System.out.println("bomb.direction: "+bomb.direction);
				//System.out.println("CreateRole.bubles.size(): "+CreateRole.bubles.size());
				
				if(bomb.direction	==	2){  //武器发射的方向是水平向左
					for (int j = 0; j < CreateRole.bubles.size(); j++) {
						buble = (Role) CreateRole.bubles.elementAt(j);
						
					System.out.println("buble.mapy气球的纵坐标:"+buble.mapy);
					System.out.println("bombY:"+bombY);
						if(((bombY+bomb.height)>= buble.mapy && (bombY+bomb.height)<=(buble.height+buble.mapy)) 
								|| ((bombY)>= buble.mapy && (bombY)<=(buble.height+buble.mapy))){
							hitBuble(own,buble,bomb);//击中气球
							System.out.println("击中气球以后的操作...");
							
						}else{
							//System.out.println(bombH <=(buble.height+buble.mapy));
							//System.out.println("气球没被击中*****");
						}
					}
//					System.out.println("气球对象："+buble);
//					System.out.println("子弹下限："+(buble.mapy+buble.height));
//					System.out.println("子弹上限："+(bombH+bomb.height));
				}
			}
		}
	}

	private void hitBuble(Role wolf,Role buble, Weapon w) {
//		if(buble.id == 1){
		CreateRole.npcs.removeElement(wolf);
		CreateRole.bubles.removeElement(buble);
		engine.weapon.bombs.removeElement(w);
//		}
	}

}

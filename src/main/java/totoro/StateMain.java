package totoro;
import javax.microedition.lcdui.Image;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;

public class StateMain implements Common{
	
	public boolean exit;
	private StateGame stateGame;
	private TotoroGameEngine engine;
	public StateMain(TotoroGameEngine engine){
		this.engine = engine;
		this.stateGame = engine.stateGame;
	}
	
	public int menuAxis[][] = { { 484, 97 }, { 484, 97+52 }, { 484, 97+104 },
					{ 484, 97+156 }, { 484, 97+208 }, { 484, 97+260 }, { 484, 97+312 },	};
	
	private int mainIndex;
	
	public void handleKey(KeyState keyState){
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			//exit = true;
			//clear();
		}
		if (keyState.containsAndRemove(KeyCode.UP)) {
			mainIndex = (mainIndex + 7 - 1) % 7;
		} else if (keyState.containsAndRemove(KeyCode.DOWN)) {
			mainIndex = (mainIndex + 1) % 7;
		} else if (keyState.containsAndRemove(KeyCode.OK)) {
			processSubMenu();
			clear();
		}
	}

	public void show(SGraphics g) {}
	
	public void execute(){
		
		/*mainIndex为0是开始游戏*/
		/*if(mainIndex == 0){
			stateGame.weapon = new Weapon(stateGame);
			stateGame.createRole = new CreateRole();
			stateGame.batches = new Batches();
			StateGame.own = stateGame.createRole.createSheep();
		}*/
	}
	
	/*注意和界面按钮的顺序一致*/
	private void processSubMenu() {
		if (mainIndex == 0) { //新游戏
		} else if(mainIndex == 1){
		} else if (mainIndex == 2) {// 道具商城
		} else if (mainIndex == 3){ //成就系统
		} else if (mainIndex == 4) {// 排行榜
		} else if (mainIndex == 5) {// 游戏帮助
		}else if(mainIndex==6){//退出游戏
			clear();
			exit = true;
		} 
	}
	

	private void clear() {}
}

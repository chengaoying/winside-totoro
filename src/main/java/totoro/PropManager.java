package totoro;

import cn.ohyeah.itvgame.model.OwnProp;
import cn.ohyeah.itvgame.model.Prop;
import cn.ohyeah.stb.game.Configurations;
import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;

public class PropManager implements Common{
	
	private TotoroGameEngine engine;
	public PlayerProp[] props ;
	
	public PropManager(TotoroGameEngine engine){
		this.engine = engine;
		props = engine.props;
	}
	
	/*查询玩家道具*/
	public void queryProps(){
		initProps(props);
		ServiceWrapper sw = engine.getServiceWrapper();
		OwnProp[] pps = sw.queryOwnPropList();
		if(pps==null){
			return;
		}
		for(int i=0;i<props.length;i++){
			for(int j=0;j<pps.length;j++){
				if(pps[j].getPropId()==props[i].getPropId()){
					props[i].setNums(pps[j].getCount());
				}
			}
		}
		
/*		for(int i=0;i<pps.length;i++){
			System.out.println("ID=="+pps[i].getPropId());
			System.out.println("count=="+pps[i].getCount());
		}
*/		for(int i=0;i<props.length;i++){
			System.out.println("道具ID=="+props[i].getPropId());
			System.out.println("道具数量=="+props[i].getNums());
		}
	}

	private void initProps(PlayerProp[] props2) {
		ServiceWrapper sw = engine.getServiceWrapper();
		Prop[] ps = sw.queryGamePropList();
		props = new PlayerProp[ps.length];
		System.out.println("创建道具数据并初始化道具信息,size:"+props.length);
		for(int i=0;i<ps.length;i++){
			for(int j=0;j<props.length;j++){
				PlayerProp prop = new PlayerProp();
				if(i==j){
					prop.setPropId(ps[i].getPropId());
					prop.setName(ps[i].getPropName());
					prop.setPrice(ps[i].getPrice());
					prop.setId(j);
					prop.setNums(0);
					prop.setDesc(ps[i].getDescription());
					prop.setFeeCode(ps[i].getFeeCode());
					props[j] = prop;
				}
			}
		}
		
		for(int m=0;m<props.length;m++){
			System.out.println("propId:"+props[m].getPropId()+", price:"+props[m].getPrice()+", name:"+props[m].getName());
		}
	}
	
	/*根据道具ID查询该道具数量*/
	public PlayerProp getPropById(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				return props[i];
			}
		}
		return null;
	}
	
	public int getPropNumsById(int id){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==id){
				return props[i].getNums();
			}
		}
		return 0;
	}
	
	public int getPriceById(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				return props[i].getPrice();
			}
		}
		return 0;
	}
	
	public boolean buyProp(int propId, int propCount, SGraphics g){
		PlayerProp pp = getPropById(propId);
		if (engine.getEngineService().getBalance() >= pp.getPrice()) {
			ServiceWrapper sw = engine.getServiceWrapper();
			sw.purchaseProp(propId, 1, "购买"+pp.getName());
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if (sw.isServiceSuccessful()) {
				pt.setText("购买"+pp.getName()+"成功");
			} 
			else {
				pt.setText("购买"+pp.getName()+"失败, 原因: "+sw.getServiceMessage());
				
			}
			pt.popup();
			return sw.isServiceSuccessful();
		}else {
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			if(Configurations.getInstance().isTelcomOperatorsTelcomfj()){
				pc.setText("是否退出游戏并跳转至大厅充值界面?");
				if (pc.popup() == 0) {
					ServiceWrapper sw = engine.getServiceWrapper();
					sw.gotoRechargePage();
					PopupText pt = UIResource.getInstance().buildDefaultPopupText();
					if (sw.isServiceSuccessful()) {
						engine.stateMain.exit = true;
					} 
					else {
						pt.setText("跳转至大厅充值界面失败, 原因: "+sw.getServiceMessage());
						pt.popup();
					}
				}
			}else{
				pc.setText("游戏币不足,是否充值");
				if (pc.popup() == 0) {
					Recharge recharge = new Recharge(engine);
					recharge.recharge();
					if(g != null){
						engine.stateGame.show(g);
					}
				}
			}
			return false;
		}
	}
	
	/*同步道具*/
	public void sysProps(){
		ServiceWrapper sw = engine.getServiceWrapper();
		sw.synProps(66, StateGame.ventoseNum);
		System.out.println("同步道具:"+sw.isServiceSuccessful());
		for(int i=0;i<props.length;i++){
			System.out.println("道具ID=="+props[i].getPropId());
			System.out.println("道具数量=="+props[i].getNums());
		}
	}
}

package sheepwar;

import cn.ohyeah.itvgame.model.OwnProp;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.game.StateRecharge;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;

public class PropManager implements Common{
	
	public SheepWarGameEngine engine;
	public Prop[] props;   			//用户总道具数
	
	public PropManager(SheepWarGameEngine e){
		this.engine = e;
		props = engine.props;
	}
	
	/*查询道具*/
	public void queryAllProps(){
		ServiceWrapper sw = engine.getServiceWrapper();
		OwnProp[] propList = sw.queryOwnPropList();
		if(propList==null){
			return;
		}
		for(int i=0;i<propList.length;i++){
			if(propList[i].getPropId()==44){
				props[0].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==45){
				props[1].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==46){
				props[2].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==47){
				props[3].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==48){
				props[4].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==49){
				props[5].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==50){
				props[6].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==51){
				props[7].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==52){
				props[8].setNums(propList[i].getCount());
			}
		}
		
		//card_assign_props = props;  
		
		/*for(int i=0;i<card_assign_props.length;i++){
			System.out.println("道具名称=="+card_assign_props[i].getName());
			System.out.println("道具数量=="+props[i].getNums());
		}*/
	}
	
	/*计算道具数量*/
	public int getGamePropNums(Prop[] props){
		int count = 0;
		for(int i=0;i<props.length;i++){
			int m = props[i].getNums();
			while(m>0){
				count++;
				m--;
			}
		}
		return count;
	}
	
	/*初始道具设为0*/
	public void initProps(Prop[] p){
		for(int i=0;i<p.length;i++){
			Prop prop = new Prop();
			p[i] = prop;
			p[i].setId(i);
			p[i].setNums(0);
		}
	}
	
	
	private boolean buyProp(int propId, int propCount){
		String propName = props[propId-44].getName();
		int price = props[propId-44].getPrice();
		if (engine.getEngineService().getBalance() >= price) {
			ServiceWrapper sw = engine.getServiceWrapper();
			sw.purchaseProp(propId, propCount, "购买"+propName);
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if (sw.isServiceSuccessful()) {
				pt.setText("购买"+propName+"成功");
			}
			else {
				pt.setText("购买"+propName+"失败, 原因: "+sw.getServiceMessage());
				
			}
			pt.popup();
			return sw.isServiceSuccessful();
		}
		else {
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText("游戏币不足,是否充值");
			if (pc.popup() == 0) {
				StateRecharge recharge = new StateRecharge(engine);
				recharge.recharge();
			}
			return false;
		}
	}
	
	/*购买道具*/
	public void purchaseProp(int shopx,int shopy){
		
		if(shopy==0 && shopx==0){
			if (buyProp(44, 1)) {
				props[0].setNums(props[0].getNums()+1);
			}
		}
		if(shopy==0 && shopx==1){
			if (buyProp(45, 1)) {
				props[1].setNums(props[1].getNums()+1);
			}
		}
		if(shopy==0 && shopx==2){
			if (buyProp(46, 1)) {
				props[2].setNums(props[2].getNums()+1);
			}
		}
		if(shopy==1 && shopx==0){
			if (buyProp(47, 1)) {
				props[3].setNums(props[3].getNums()+1);
			}
		}
		if(shopy==1 && shopx==1){
			if (buyProp(48, 1)) {
				props[4].setNums(props[4].getNums()+1);
			}
		}
		if(shopy==1 && shopx==2){
			if (buyProp(49, 1)) {
				props[5].setNums(props[5].getNums()+1);
			}
		}
		if(shopy==2 && shopx==0){
			if (buyProp(50, 1)) {
				props[6].setNums(props[6].getNums()+1);
			}
		}
		if(shopy==2 && shopx==1){
			if (buyProp(51, 1)) {
				props[7].setNums(props[7].getNums()+1);
			}
		}
		if(shopy==2 && shopx==2){
			if (buyProp(52, 1)) {
				props[8].setNums(props[8].getNums()+1);
			}
		}
	}
	

	/*同步道具*/
	public void sysProps(){
		int[] ids = new int[9];
		int[] counts = new int[9];
		for(int i=0;i<props.length;i++){
			ids[i] = props[i].getPropId();
			counts[i] = props[i].getNums();
		}
		ServiceWrapper sw = engine.getServiceWrapper();
		sw.synProps(ids, counts);
		System.out.println("同步道具:"+sw.getServiceMessage());
	}
}

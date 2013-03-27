package totoro;

import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.util.ConvertUtil;

public class PropManager implements Common{
	
	private TotoroGameEngine engine;
	public PlayerProp[] props ;
	
	private int[] ids = {61,62,63,64,65,66,67,68,69};
	//private int[] prices = {100,100,20,40,80,20,10,20,30};
	private int[] prices = {50,50,20,30,50,10,10,10,10};  		//���յ����۸�
	private String[] names = {"����","ڤ��","�ػ���2","�ػ���3","�ػ���4","��ɱ��","����1","����2","����3",};
	
	public PropManager(TotoroGameEngine engine){
		this.engine = engine;
		props = engine.props;
	}
	
	/*��ѯ��ҵ���*/
	public void queryProps(){
		initProps(props);
		ServiceWrapper sw = engine.getServiceWrapper();
		String datas = sw.loadPropItem();
		if(datas==null){
			return;
		}
		
		String[] data1 = ConvertUtil.split(datas, "|");
		for(int j=0;j<props.length;j++){
			PlayerProp prop = new PlayerProp();
			String[] data2 = ConvertUtil.split(data1[j], ",");
			String[] data3 = new String[data2.length];
			for(int k=0;k<data2.length;k++){
				data3[k] = ConvertUtil.split(data2[k], ":")[1];
			}
			prop.setPropId(Integer.parseInt(data3[0]));
			prop.setPrice(Integer.parseInt(data3[1]));
			prop.setNums(Integer.parseInt(data3[2]));
			prop.setName(data3[3]);
			props[j] = prop;
		}
		
		printInfo();
	}
	
	private void printInfo(){
		for(int i=0;i<props.length;i++){
			System.out.println("����ID=="+props[i].getPropId());
			System.out.println("���߼۸�=="+props[i].getPrice());
			System.out.println("��������=="+props[i].getNums());
		}
	}

	private void initProps(PlayerProp[] props2) {
		props = new PlayerProp[9];
		System.out.println("�����������ݲ���ʼ��������Ϣ,size:"+props.length);
		
		for(int m=0;m<props.length;m++){
			PlayerProp pp = new PlayerProp();
			pp.setId(m);
			pp.setPropId(ids[m]);
			pp.setPrice(prices[m]);
			pp.setName(names[m]);
			pp.setNums(0);
			props[m] = pp;
		}
	}
	
	/*���ݵ���ID��ѯ�õ�������*/
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
	
	public void addPropNum(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				props[i].setNums(props[i].getNums()+1);
			}
		}
	}
	
	public void reducePropNum(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				if(props[i].getNums()>0){
					props[i].setNums(props[i].getNums()-1);
				}
			}
		}
	}
	
	public boolean buyProp(int propId, int propCount, SGraphics g){
		PlayerProp pp = getPropById(propId);
		if(engine.getEngineService().getBalance()<pp.getPrice()){
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText(engine.getEngineService().getExpendAmountUnit()+"����,�Ƿ�ȥ��ֵ?");
			if(pc.popup()==0){
				Recharge recharge = new Recharge(engine);
				recharge.recharge();
				if(g != null){
					engine.stateGame.show(g);
				}
			}
			return false;
		}else{
			ServiceWrapper sw = engine.getServiceWrapper();
			//sw.purchaseProp(propId, propCount, "����"+propName);
			//sw.expend(pp.getPrice(), propId, "����"+pp.getName());
			sw.consume(1, pp.getPrice(), pp.getName());
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if (sw.isServiceSuccessful()) {
				pt.setText("����"+pp.getName()+"�ɹ�");
				addPropNum(propId);
			}
			else {
				pt.setText("����"+pp.getName()+"ʧ��, ԭ��: "+sw.getMessage());
				
			}
			pt.popup();
			return sw.isServiceSuccessful();
		}
	}
	
	/*ͬ������*/
	public void sysProps(){
		ServiceWrapper sw = engine.getServiceWrapper();
		//sw.synProps(66, StateGame.ventoseNum);
		System.out.println("ͬ������:");
		String datas = "";
		for(int i=0;i<props.length;i++){
			datas += "id:"+props[i].getPropId()
					+",price:"+props[i].getPrice()
					+",nums:"+props[i].getNums()
					+",name:"+props[i].getName();
			if(i!=props.length-1){
				datas += "|";
			}
		}
		sw.savePropItem(datas);
		if(sw.isServiceSuccessful()){
			printInfo();
		}
	}
}

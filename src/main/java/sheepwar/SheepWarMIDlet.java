package sheepwar;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;



public class SheepWarMIDlet extends MIDlet {
	
	private static SheepWarMIDlet instance;
	
	public SheepWarMIDlet(){
		instance = this;
	}
	
	public static SheepWarMIDlet getInstance() {
		return instance;
	}

	protected void destroyApp(boolean unconditional)throws MIDletStateChangeException {}

	protected void pauseApp() {}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(SheepWarGameEngine.instance);
		new Thread(SheepWarGameEngine.instance).start();
	}

}

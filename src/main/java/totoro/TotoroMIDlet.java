package totoro;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class TotoroMIDlet extends MIDlet {
	
	private static TotoroMIDlet instance;
	
	public TotoroMIDlet(){
		instance = this;
	}
	
	public static TotoroMIDlet getInstance() {
		return instance;
	}

	protected void destroyApp(boolean unconditional)throws MIDletStateChangeException {}

	protected void pauseApp() {}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(TotoroGameEngine.instance);
		new Thread(TotoroGameEngine.instance).start();
	}

}

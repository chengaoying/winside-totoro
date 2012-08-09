package sheepwar;

import javax.microedition.midlet.MIDlet;

import cn.ohyeah.stb.game.GameCanvasEngine;

public class SheepWarGameEngine extends GameCanvasEngine{
	
	public static boolean isSupportFavor = false;	
	public static int ScrW = 0;
	public static int ScrH = 0;
	public static SheepWarGameEngine instance = buildGameEngine();
	
	private static SheepWarGameEngine buildGameEngine() {
		return new SheepWarGameEngine(SheepWarMIDlet.getInstance());
	}
	
	private SheepWarGameEngine(MIDlet midlet) {
		super(midlet);
		setRelease(true);
		ScrW = screenWidth;
		ScrH = screenHeight;
	}
    
	
	protected void loop() {
		
	}

	
}

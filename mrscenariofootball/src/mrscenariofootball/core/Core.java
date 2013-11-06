package mrscenariofootball.core;

import java.util.List;

import mrservermisc.botcontrol.interfaces.BotControl;
import mrservermisc.bots.interfaces.Bot;
import mrservermisc.graphics.interfaces.Graphics;
import mrservermisc.scenario.interfaces.Scenario;
import mrservermisc.vision.interfaces.Vision;

/**
 * Bildet das Herzstück des MRFussballszenarios. Hier werden alle Metaprozesse und Threads verwaltet
 * und gesteuert.
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
public class Core implements Scenario {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean needVision() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean needBotControl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerVision(Vision aVision) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerBotControl(BotControl aBotControl) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerBotList(List<Bot> aBotList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerGraphics(Graphics aGraphics) {
		// TODO Auto-generated method stub
		return false;
	}

}

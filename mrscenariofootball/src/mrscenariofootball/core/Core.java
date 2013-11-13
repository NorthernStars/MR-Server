package mrscenariofootball.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrservermisc.botcontrol.interfaces.BotControl;
import mrservermisc.bots.interfaces.Bot;
import mrservermisc.graphics.interfaces.Graphics;
import mrservermisc.network.data.position.PositionDataPackage;
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
    
    private static Logger SCENARIOLOGGER = LogManager.getLogger("SCENARIO");
    
    public static Logger getLogger(){
        
        return SCENARIOLOGGER;
        
    }
    
	private Vision mTheVision;
	private Graphics mGraphics;
	private List<Bot> mBots = new ArrayList<Bot>();
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean needVision() {
		return true;
	}

	@Override
	public boolean needBotControl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerVision(Vision aVision) {
		// TODO Auto-generated method stub
		mTheVision = aVision;
		return true;
	}

	@Override
	public boolean registerBotControl(BotControl aBotControl) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerNewBot(Bot aBot) {
		
		Core.getLogger().debug( "Register new bot: " + aBot.toString() );
		return mBots.add( aBot );
		
	}

	@Override
	public boolean registerGraphics(Graphics aGraphics) {
		mGraphics = aGraphics;
		return true;
	}

	@Override
	public void startScenario() {
		PositionDataPackage vData;
		while(true){
			try {
				Thread.sleep( 1500 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if( mTheVision != null ){
				vData = mTheVision.getPositionData();
				if( vData != null ){
					mGraphics.sendWorldStatus( vData.toXMLString() );
					Core.getLogger().debug( vData.toString() );
				}
			}
			
		}
		
	}

}

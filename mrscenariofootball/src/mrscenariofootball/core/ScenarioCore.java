package mrscenariofootball.core;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.managements.FromVision;
import mrscenariofootball.core.managements.ToGraphics;
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
public class ScenarioCore implements Scenario {
    
    private static ScenarioCore INSTANCE;
	private static Logger SCENARIOLOGGER = LogManager.getLogger("SCENARIO");
    
    public static Logger getLogger(){
        
        return SCENARIOLOGGER;
        
    }
    
    public ScenarioCore() {
    	
    	ScenarioCore.getLogger().debug( "Creating ScenarioCore-instance." );
        ScenarioCore.INSTANCE = this;
    	
    }
    
    public static ScenarioCore getInstance() {
        
        if( ScenarioCore.INSTANCE == null){
        	ScenarioCore.getLogger().debug( "Creating ScenarioCore-instance." );
            ScenarioCore.INSTANCE = new ScenarioCore();
        }

        ScenarioCore.getLogger().trace( "Retrieving ScenarioCore-instance." );
        return ScenarioCore.INSTANCE;
        
    }
    
	private Vision mTheVision;
	private Graphics mGraphics;
	private ConcurrentHashMap<Integer, Bot> mBots = new ConcurrentHashMap<Integer, Bot>();
	private ScenarioInformation mScenarioInformation;
	private FromVision mFromVisionManagement;
	private ToGraphics mToGraphicsManagement;
	
	@Override
	public void close() {

		mFromVisionManagement.close();
		mToGraphicsManagement.close();
		
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
	public boolean putPositionData( PositionDataPackage aPositionDataPackage ) {
		
		return FromVision.putPositionDatPackageInProcessingQueue( aPositionDataPackage );
	}

	@Override
	public boolean registerBotControl(BotControl aBotControl) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerNewBot( Bot aBot ) {
		
		ScenarioCore.getLogger().debug( "Register new bot: " + aBot.toString() );
		return mBots.put( aBot.getVtId(), aBot ) != null;
		
	}

	@Override
	public boolean registerGraphics(Graphics aGraphics) {
		mGraphics = aGraphics;
		return true;
	}

	@Override
	public void startScenario() {
		
		mScenarioInformation = new ScenarioInformation();
		
		if( mTheVision != null ){
			mFromVisionManagement = new FromVision();
			mFromVisionManagement.startManagement();
		}
		mToGraphicsManagement = new ToGraphics( mGraphics );
		mToGraphicsManagement.startManagement();
		
		PositionDataPackage vData;
		ScenarioCore.getLogger().info( "Scenario started" );
		ScenarioCore.getLogger().info( mScenarioInformation.getWorldData().toXMLString() );
		
		while(true){
			
			try {
				Thread.sleep( 500 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if( mTheVision != null ){
				vData = mTheVision.getPositionData();
				if( vData != null ){
					ScenarioCore.getLogger().debug( vData.toString() );
				}
			}
			
			mGraphics.sendWorldStatus( mScenarioInformation.getWorldData().toXMLString() );
			
		}
		
	}

	public ScenarioInformation getScenarioInformation() {
		return mScenarioInformation;
	}
	
	public ConcurrentHashMap<Integer, Bot> getBots(){
		
		return mBots;
		
	}

}

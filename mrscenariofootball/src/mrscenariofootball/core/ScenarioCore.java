package mrscenariofootball.core;

import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.GuardedBy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrscenariofootball.core.data.BotAI;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.client.ClientWorldData;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.WorldData;
import mrscenariofootball.core.managements.FromVision;
import mrscenariofootball.core.managements.ToGraphics;
import mrservermisc.botcontrol.interfaces.BotControl;
import mrservermisc.bots.interfaces.Bot;
import mrservermisc.graphics.interfaces.Graphics;
import mrservermisc.network.data.position.PositionDataPackage;
import mrservermisc.network.data.position.PositionObjectBot;
import mrservermisc.network.data.position.PositionObjectType;
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
    
	private Graphics mGraphics;
	@GuardedBy("this") private ConcurrentHashMap<Integer, BotAI> mBotAIs = new ConcurrentHashMap<Integer, BotAI>();
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
	public synchronized boolean registerNewBot( Bot aBot ) {
		if( mBotAIs.putIfAbsent( aBot.getVtId(), new BotAI(aBot) ) != null ){
			
			ScenarioCore.getLogger().info( "Registered new bot: {}", aBot.toString() );
			return true;
			
		} else {
			
			ScenarioCore.getLogger().info(" BotAI with used id tryed to connect: {}", aBot.toString() );
			return false;
		}
		
	}

	@Override
	public boolean registerGraphics(Graphics aGraphics) {
		mGraphics = aGraphics;
		return true;
	}

	@Override
	public void startScenario() {

		mScenarioInformation = new ScenarioInformation();
		/*
		mFromVisionManagement = new FromVision();
		mFromVisionManagement.startManagement();
		
		mToGraphicsManagement = new ToGraphics( mGraphics );
		mToGraphicsManagement.startManagement();
		*/
		ScenarioCore.getLogger().info( "Scenario started" );
		
		
		PositionObjectBot vFoundBot = new PositionObjectBot( PositionObjectType.BOT, 1, "Hammer", new double[]{0.5,0.5}, new double[]{}, 0 );
		Player vPlayer;
		
		ClientWorldData vClientWorldData;
		
		ScenarioCore.getLogger().info( "Scenario started" );
		

		while(true){
			
			if( !mBotAIs.isEmpty() ){
			
				vPlayer = new Player( vFoundBot, mBotAIs.get(1) );
				vClientWorldData = new ClientWorldData( mScenarioInformation.getWorldData().copy(), vPlayer);
				System.out.println(vClientWorldData);
				mBotAIs.get(1).setWorldDataToSend( vClientWorldData );
			}
			for( BotAI vBotAI : ScenarioCore.getInstance().getBotAIs().values() ){
				
				vBotAI.sendWorldData( mScenarioInformation.getWorldData() );
				
			}
			
			try {
				Thread.sleep( 100 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			if( mTheVision != null ){
				vData = mTheVision.getPositionData();
				if( vData != null ){
					ScenarioCore.getLogger().debug( vData.toString() );
				}
			}
			
			//mGraphics.sendWorldStatus( mScenarioInformation.getWorldData().toXMLString() );
			*/
		}

	}

	public ScenarioInformation getScenarioInformation() {
		return mScenarioInformation;
	}
	
	public ConcurrentHashMap<Integer, BotAI> getBotAIs(){
		
		return mBotAIs;
		
	}

}

package mrscenariofootball.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

import net.jcip.annotations.GuardedBy;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrscenariofootball.core.data.BotAI;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.action.Command;
import mrscenariofootball.core.data.worlddata.server.BallPosition;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;
import mrscenariofootball.core.data.worlddata.server.WorldData;
import mrscenariofootball.core.gui.ScenarioGUI;
import mrscenariofootball.core.managements.FromVision;
import mrscenariofootball.core.managements.ToBotAIs;
import mrscenariofootball.core.managements.ToGraphics;
import mrservermisc.botcontrol.interfaces.BotControl;
import mrservermisc.bots.interfaces.Bot;
import mrservermisc.graphics.interfaces.Graphics;
import mrservermisc.network.data.position.PositionDataPackage;
import mrservermisc.scenario.interfaces.Scenario;

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
	private ScenarioInformation mScenarioInformation = new ScenarioInformation();;
	private FromVision mFromVisionManagement;
	private ToGraphics mToGraphicsManagement;
	private BotControl mBotControl;
	private ToBotAIs mToBotAIs;
	private AtomicBoolean mPaused = new AtomicBoolean( true );
	private ScenarioGUI mGUI = new ScenarioGUI();
	
	@Override
	public void close() {

		if( mFromVisionManagement != null ){
					
			mFromVisionManagement.close();
			
		}
		if( mToGraphicsManagement != null ){
			
			mToGraphicsManagement.close();
			
		}
		if( mToBotAIs != null ){
			
			mToBotAIs.close();
			
		}
		
	}

	@Override
	public boolean needVision() {
		return true;
	}

	@Override
	public boolean needBotControl() {
		return true;
	}

	@Override
	public boolean putPositionData( PositionDataPackage aPositionDataPackage ) {
		
		return FromVision.putPositionDatPackageInProcessingQueue( aPositionDataPackage );
		
	}

	@Override
	public boolean registerBotControl(BotControl aBotControl) {

		mBotControl = aBotControl;
		
		return false;
	}

	@Override
	public synchronized boolean registerNewBot( Bot aBot ) {
		if( mBotAIs.putIfAbsent( aBot.getVtId(), new BotAI(aBot) ) == null ){
			
			ScenarioCore.getLogger().info( "Registered new bot: {}", aBot.toString() );
			return true;
			
		} else {
			
			ScenarioCore.getLogger().info(" BotAI with used id tryed to connect: {}", aBot.toString() );
			return false;
		}
		
	}
	
	@Override
	public synchronized boolean unregisterBot( Bot aBot ) {
		if( mBotAIs.remove( aBot.getVtId() ) != null ){
			
			ScenarioCore.getLogger().info( "Unegistered new bot: {}", aBot.toString() );
			return true;
			
		} else {
			
			ScenarioCore.getLogger().info(" No AI to unregister: {}", aBot.toString() );
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

		WorldData.createWorldDataSchema();
		
		mFromVisionManagement = new FromVision();
		mFromVisionManagement.startManagement();
		
		mToGraphicsManagement = new ToGraphics();
		mToGraphicsManagement.startManagement();
		
		mToBotAIs = new ToBotAIs();
		mToBotAIs.startManagement();
		
		ScenarioCore.getLogger().info( "Scenario started. Game paused" );
		
		Command vCommand;
		WorldData vWorldData;
		double vXForce, vYForce;
		BallPosition vBall;
		long vTickTime;
		
		while(true){
			
			while( mPaused.get() ){ 
				
				try { Thread.sleep( 10 ); } catch ( InterruptedException vInterruptedException ) { ScenarioCore.getLogger().error( "Error suspending Scenario: {}",vInterruptedException.getLocalizedMessage() ); ScenarioCore.getLogger().catching( Level.ERROR, vInterruptedException ); }

				for( BotAI vBotAI : mBotAIs.values() ){
					
					mBotControl.sendMovement( vBotAI.getRcId(), 0, 0 );
					
				}
			
			}
			
			vTickTime = System.nanoTime();
			
			vWorldData = mScenarioInformation.getWorldData().copy();
			vBall = new BallPosition( ReferencePointName.Ball, new ServerPoint( vWorldData.getBallPosition().getPosition().getX(), vWorldData.getBallPosition().getPosition().getY() ) );
			for( BotAI vBotAI : mBotAIs.values() ){
				
				vCommand = Command.unmarshallXMLPositionDataPackageString( vBotAI.getLastAction() );
				
				if( vCommand != null ){
					if( vCommand.isMovement() ){
						mBotControl.sendMovement( vBotAI.getRcId(), vCommand.getMovement().getLeftWheelVelocity(), vCommand.getMovement().getRightWheelVelocity() );
					} else if( vCommand.isKick() ){
						
						for( Player vPlayer : vWorldData.getListOfPlayers() ){
							
							if( vPlayer.getId() == vBotAI.getVtId() && vPlayer.getPosition().distance( vWorldData.getBallPosition().getPosition() ) <= 0.05 ){
								
								ScenarioCore.getLogger().debug(" Player {} tries to kick {} with distance {}", vPlayer, vWorldData.getBallPosition(), vPlayer.getPosition().distance( vWorldData.getBallPosition().getPosition() ));
																
								vXForce = vCommand.getKick().getForce() * Math.cos( vCommand.getKick().getAngle() );
								vYForce = vCommand.getKick().getForce() * Math.sin( vCommand.getKick().getAngle() );
								
								//TODO: Kick ball in reality
								vBall = new BallPosition( ReferencePointName.Ball, new ServerPoint( vBall.getPosition().getX() + vXForce/10.0, vBall.getPosition().getY() + vYForce/10.0 ) );
								
								break;
								
							}
							
						}
						
					}
				}
				
			}
			
			//TODO real tick
			mScenarioInformation.setBall( vBall );
			// TODO check for goal
			mScenarioInformation.addTimePlayed( 0.05 );
			
			ScenarioCore.getLogger().trace( "Scenario ticked {}", mScenarioInformation.getWorldData().copy() );
			mGUI.updateUI();
			
			while( System.nanoTime() - vTickTime <= 50000000 ){
			
				try {
					Thread.sleep( 5 );
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
		
	}

	public ScenarioInformation getScenarioInformation() {
		return mScenarioInformation;
	}
	
	public ConcurrentHashMap<Integer, BotAI> getBotAIs(){
		
		return mBotAIs;
		
	}

	@Override
	public boolean pauseScenario() {

		ScenarioCore.getLogger().info( "Game paused" );
		return mPaused.compareAndSet( false, true );
		
	}

	@Override
	public boolean unpauseScenario() {

		ScenarioCore.getLogger().info( "Game unpaused" );
		return mPaused.compareAndSet( true, false );
		
	}

	@Override
	public JPanel getScenarioGUI() {
		
		return mGUI;
		
	}

	public Graphics getGraphics() {
		return mGraphics;
	}

}

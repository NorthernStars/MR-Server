package mrscenariofootball.game;

import java.util.concurrent.atomic.AtomicBoolean;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.BotAI;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.action.Command;
import mrscenariofootball.core.data.worlddata.server.BallPosition;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;
import mrscenariofootball.core.data.worlddata.server.WorldData;
import mrscenariofootball.core.managements.FromVision;
import mrscenariofootball.core.managements.ToBotAIs;
import mrscenariofootball.core.managements.ToGraphics;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Core {
	 
	private static Core INSTANCE;
	private static Logger GAMELOGGER = LogManager.getLogger("GAME");
    
    public static Logger getLogger(){
        
        return GAMELOGGER;
        
    }
    
    public Core() {}
    
    public static Core getInstance() {
        
        if( Core.INSTANCE == null){
        	Core.getLogger().debug( "Creating Game-instance." );
            Core.INSTANCE = new Core();
        }

        Core.getLogger().trace( "Retrieving Game-instance." );
        return Core.INSTANCE;
        
    }
    
	private AtomicBoolean mSuspended = new AtomicBoolean( false );

	public void startGame() {
		
		startManagements();
		
		Core.getLogger().info( "Game started." );
		
		Command vCommand;
		WorldData vWorldData;
		double vXForce, vYForce;
		BallPosition vBall;
		ServerPoint vBallForce = new ServerPoint( 0, 0 );
		long vTickTime;
		
		while(true){
			
			// TODO: after pause send last command to bots?
			if( mSuspended.get() ){
				
				for( BotAI vBotAI : ScenarioInformation.getInstance().getBotAIs().values() ){
					
					ScenarioInformation.getInstance().getBotControl().sendMovement( vBotAI.getRcId(), 0, 0 );
					
				}
				
				while( mSuspended.get() ){ 
					
					try { Thread.sleep( 10 ); } catch ( InterruptedException vInterruptedException ) { Core.getLogger().error( "Error suspending Scenario: {}",vInterruptedException.getLocalizedMessage() ); Core.getLogger().catching( Level.ERROR, vInterruptedException ); }
			
				}
				
			}
			
			vTickTime = System.nanoTime();
			
			vWorldData = ScenarioInformation.getInstance().getWorldData().copy();
			
			for( BotAI vBotAI : ScenarioInformation.getInstance().getBotAIs().values() ){
				
				vCommand = Command.unmarshallXMLPositionDataPackageString( vBotAI.getLastAction() );
				
				if( vCommand != null ){
					
					if( vCommand.isMovement() ){
						
						ScenarioInformation.getInstance().getBotControl().sendMovement( vBotAI.getRcId(), vCommand.getMovement().getLeftWheelVelocity(), vCommand.getMovement().getRightWheelVelocity() );
						
					} else if( vCommand.isKick() ){
						
						for( Player vPlayer : vWorldData.getListOfPlayers() ){
							
							if( vPlayer.getId() == vBotAI.getVtId() && vPlayer.getPosition().distance( vWorldData.getBallPosition().getPosition() ) <= 0.05 ){ // TODO: remove magic number
								
								Core.getLogger().debug(" Player {} tries to kick {} with distance {}", vPlayer, vWorldData.getBallPosition(), vPlayer.getPosition().distance( vWorldData.getBallPosition().getPosition() ));
																
								vXForce = vCommand.getKick().getForce() * Math.cos( vCommand.getKick().getAngle() + vPlayer.getOrientationAngle() );
								vYForce = vCommand.getKick().getForce() * Math.sin( vCommand.getKick().getAngle() + vPlayer.getOrientationAngle() );
								
								//TODO: Kick ball in reality
								vBallForce = new ServerPoint( vBallForce.getX() + vXForce/10.0, vBallForce.getY() + vYForce/10.0 ); // TODO: remove magic number
								
								break;
								
							}
							
						}
						
					}
				}
				
			}
			
			//TODO real tick
			vBall = new BallPosition( ReferencePointName.Ball, 
					new ServerPoint( 
							vWorldData.getBallPosition().getPosition().getX() + 0.05 * vBallForce.getX(),
							vWorldData.getBallPosition().getPosition().getY() + 0.05 * vBallForce.getX() ) );
			ScenarioInformation.getInstance().setBall( vBall );
			vBallForce.setLocation( vBallForce.getX() * 0.95, vBallForce.getY() * 0.95 );
			// TODO check for goal
			ScenarioInformation.getInstance().addTimePlayed( 0.05 );
			
			vWorldData = ScenarioInformation.getInstance().getWorldData().copy();
			
			Core.getLogger().trace( "Scenario ticked {}", vWorldData );
			
			ToBotAIs.putWorldDatainSendingQueue( vWorldData.copy() );
			ToGraphics.putWorldDatainSendingQueue( vWorldData.copy() );
			
			ScenarioCore.getInstance().getGUI().update();
			
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

	private void startManagements() {
		FromVision.getInstance().startManagement();
		ToGraphics.getInstance().startManagement();
		ToBotAIs.getInstance().startManagement();
	}

	public boolean suspend() {
		
		Core.getLogger().info( "Game paused" );
		return mSuspended.compareAndSet( false, true );
		
	}

	public boolean resume() {
		
		Core.getLogger().info( "Game unpaused" );
		return mSuspended.compareAndSet( true, false );
		
	}
    
    
}

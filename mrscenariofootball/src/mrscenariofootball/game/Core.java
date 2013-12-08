package mrscenariofootball.game;

import java.util.concurrent.atomic.AtomicBoolean;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.BotAI;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.action.Command;
import mrscenariofootball.core.data.action.Movement;
import mrscenariofootball.core.data.worlddata.server.BallPosition;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;
import mrscenariofootball.core.data.worlddata.server.WorldData;
import mrscenariofootball.core.managements.FromVision;
import mrscenariofootball.core.managements.ToBotAIs;
import mrscenariofootball.core.managements.ToGraphics;
import mrservermisc.network.data.position.PositionObjectBot;
import mrservermisc.network.data.position.PositionObjectType;

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
	private AtomicBoolean mSimulation = new AtomicBoolean( true );

	public void startGame() {
		
		startManagements();
		
		Core.getLogger().info( "Game started." );
		
		Command vCommand;
		WorldData vWorldData;
		double vXForce, vYForce, vKickAngle;
		BallPosition vBall;
		ServerPoint vBallForce = new ServerPoint( 0, 0 );
		long vTickTime;
		
		while( true ){
			
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
						
						moveBot( vCommand.getMovement() , vBotAI );
						
					} else if( vCommand.isKick() ){
						
						for( Player vPlayer : vWorldData.getListOfPlayers() ){
							
							if( vPlayer.getId() == vBotAI.getVtId() && vPlayer.getPosition().distance( vWorldData.getBallPosition().getPosition() ) <= 0.05 ){ // TODO: remove magic number
								
								Core.getLogger().trace(" Player {} tries to kick {} with distance {}", vPlayer, vWorldData.getBallPosition(), vPlayer.getPosition().distance( vWorldData.getBallPosition().getPosition() ));
								
								vKickAngle = vCommand.getKick().getAngle() + vPlayer.getOrientationAngle();
								vKickAngle = vKickAngle > 180.0 ? vKickAngle - 360.0 : vKickAngle;
								vKickAngle = vKickAngle < -180.0 ? vKickAngle + 360.0 : vKickAngle;
								
								vXForce = vCommand.getKick().getForce() * Math.cos( Math.toRadians( vKickAngle )  );
								vYForce = vCommand.getKick().getForce() * Math.sin( Math.toRadians( vKickAngle ) );
								Core.getLogger().trace(" Kick with Force {}|{} ({})", vXForce, vYForce, vCommand.getKick() );
								//TODO: Kick ball in reality
								vBallForce = new ServerPoint( (vBallForce.getX() + vXForce)/5.0, (vBallForce.getY() + vYForce)/5.0 ); // TODO: remove magic number
								
								break;
								
							}
							
						}
						
					}
				}
				
			}
			
			//TODO real tick 
			vWorldData.getBallPosition().getPosition().setLocation(
					vWorldData.getBallPosition().getPosition().getX() + 0.05 * vBallForce.getX(),
					vWorldData.getBallPosition().getPosition().getY() + 0.05 * vBallForce.getY() );
			ScenarioInformation.getInstance().setBall( vWorldData.getBallPosition() );
			vBallForce = new ServerPoint( vBallForce.getX() * 0.95, vBallForce.getY() * 0.95 );
			// TODO check for goal
			ScenarioInformation.getInstance().addTimePlayed( 0.1 );
			
			vWorldData = ScenarioInformation.getInstance().getWorldData().copy();
			
			Core.getLogger().trace( "Scenario ticked {}", vWorldData );
			
			ToBotAIs.putWorldDatainSendingQueue( vWorldData.copy() );
			ToGraphics.putWorldDatainSendingQueue( vWorldData.copy() );
			
			ScenarioCore.getInstance().getGUI().update();
			
			while( System.nanoTime() - vTickTime <= 100000000 ){
			
				try {
					Thread.sleep( 5 );
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
	}

	
	
	private void moveBot( Movement aMovement, BotAI aBotAI) {
		
		if( mSimulation.get() ){
			
			double vSpeed, vRotation, vRightDegree, vLeftDegree;
			
			vSpeed = ( aMovement.getLeftWheelVelocity() + aMovement.getRightWheelVelocity() ) / 20000.0; //TODO: remove magic number

			vRightDegree = Math.toDegrees( Math.atan2( 1.0, aMovement.getRightWheelVelocity() / 100.0 ) );
			vLeftDegree = Math.toDegrees( Math.atan2( 1.0, aMovement.getLeftWheelVelocity() / 100.0 ) );
			
			vRotation = (vRightDegree + vLeftDegree ) / 2 - vRightDegree;
			vRotation /= 1.5;
			
			for( Player vPlayer : ScenarioInformation.getInstance().getWorldData().getListOfPlayers() ){
				
				if( vPlayer.getId() == aBotAI.getVtId() ){
					
					vPlayer.setOrientationAngle( vPlayer.getOrientationAngle() - vRotation );
					vPlayer.getPosition().setLocation( 
							vPlayer.getPosition().getX() + vSpeed * Math.cos( Math.toRadians( vPlayer.getOrientationAngle() ) ),
							vPlayer.getPosition().getY() + vSpeed * Math.sin( Math.toRadians( vPlayer.getOrientationAngle() ) ) );

					Core.getLogger().info( "Added rotation {} and speed {} from movment {} to player {}({})", vRotation, vSpeed, aMovement, vPlayer, aBotAI );
					
					return;
					
				}
				
			}
			
			ScenarioInformation.getInstance().getWorldData().getListOfPlayers().add( 
					new Player( new PositionObjectBot( PositionObjectType.BOT, aBotAI.getVtId(), "", 
							new double[]{ 0.5, 0.5 }, null, 0.0 ) , 
							aBotAI ) );
			
			Core.getLogger().info( "Added new bot {}", aBotAI );
			
		} else {
			
			ScenarioInformation.getInstance().getBotControl().sendMovement( aBotAI.getRcId(), aMovement.getLeftWheelVelocity(), aMovement.getRightWheelVelocity() );
		
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

	public boolean isSimulation() {

		return mSimulation.get();
		
	}
    
    
}

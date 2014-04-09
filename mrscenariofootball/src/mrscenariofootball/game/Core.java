package mrscenariofootball.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.BotAI;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.action.Command;
import mrscenariofootball.core.data.action.Kick;
import mrscenariofootball.core.data.action.Movement;
import mrscenariofootball.core.data.worlddata.server.BallPosition;
import mrscenariofootball.core.data.worlddata.server.PlayMode;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;
import mrscenariofootball.core.data.worlddata.server.Team;
import mrscenariofootball.core.data.worlddata.server.WorldData;
import mrscenariofootball.core.managements.FromVision;
import mrscenariofootball.core.managements.ToBotAIs;
import mrscenariofootball.core.managements.ToGraphics;
import mrservermisc.network.data.position.PositionObjectBot;
import mrservermisc.network.data.position.PositionObjectType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Core {
	 
	private static Core INSTANCE;
	private static Logger GAMELOGGER = LogManager.getLogger("GAME");
    
    public static Logger getLogger(){
        
        return GAMELOGGER;
        
    }
    
    public Core() {
    	
    	mSuspended = new AtomicBoolean( false );
    	mSimulation = new AtomicBoolean( true );
    	mAutomaticGame = new AtomicBoolean( true );
    	mGameStarted = new AtomicBoolean( false );
    	
    }
    
    public static Core getInstance() {
        
        if( Core.INSTANCE == null){
        	Core.getLogger().debug( "Creating Game-instance." );
            Core.INSTANCE = new Core();
        }

        Core.getLogger().trace( "Retrieving Game-instance." );
        return Core.INSTANCE;
        
    }

	private AtomicBoolean mSuspended;
	private AtomicBoolean mSimulation;
	private AtomicBoolean mAutomaticGame;
	private AtomicBoolean mGameStarted;
	
	private List<KickEcho> mKicks = new ArrayList<KickEcho>( 100 );
	private HashMap<PlayMode, Double> mTimesToRun = new HashMap<PlayMode, Double>();

	// Main game loop
	public void startGame() {
		
		startManagements();
		
		mGameStarted.set(true);
		
		Core.getLogger().info( "Game started." );
		
		WorldData vWorldData;
		BallPosition vOldBallPosition;
		PlayMode vCurrentPlaymode;
		
		long vTickTime;
		double vTimeCounter = 0;
		
		while( true ){
			
			vTickTime = System.nanoTime();
			vCurrentPlaymode = ScenarioInformation.getInstance().getWorldData().getPlayMode();
			if( mTimesToRun.get( vCurrentPlaymode ) == null ){
				mTimesToRun.put( vCurrentPlaymode, ScenarioInformation.getInstance().getPlayModeTimeToRun( vCurrentPlaymode ) );
			}
			
			if( !mSuspended.get() && vCurrentPlaymode.canBotsMove() ){

				processBotAIActions();
				
			} else {
				
				stopRealBots();
				
			}
			
			if( !mSuspended.get() && vCurrentPlaymode.canBallMove() ){

				vOldBallPosition = ScenarioInformation.getInstance().getWorldData().copy().getBallPosition();
				
				moveBall();
				
				isBallInGoal( vOldBallPosition );
				
			}
			
			if( !mSuspended.get() ){
				
				if( vCurrentPlaymode.isTimeRunning() ){
				
					ScenarioInformation.getInstance().addTickPlayed();
				
				}

				mTimesToRun.put(vCurrentPlaymode, mTimesToRun.get(vCurrentPlaymode) - ScenarioInformation.getInstance().getGameTickTime() );
				
			}
			if( !mSuspended.get() ) {
				
				if( mTimesToRun.get( vCurrentPlaymode ) <= 0.0 ) {
					if( mAutomaticGame.get() ){
				
						if( PlayMode.getFollowingMode( vCurrentPlaymode ) == vCurrentPlaymode ){
							
							suspend();
							
						} else {
							
							ScenarioInformation.getInstance().getWorldData().setPlayMode( PlayMode.getFollowingMode( vCurrentPlaymode ) );
							mTimesToRun.remove(vCurrentPlaymode);
							
						}
						
						
					} else {
						
						suspend();
					}
					
				}
				
			}
			
			{	// always send data!
				
				vWorldData = ScenarioInformation.getInstance().getWorldData().copy();
				
				Core.getLogger().trace( "Scenario ticked {}", vWorldData );
				
				ToBotAIs.putWorldDatainSendingQueue( vWorldData.copy() );
				ToGraphics.putWorldDatainSendingQueue( vWorldData.copy() );
				
				ScenarioCore.getInstance().getGUI().update(); // do better...
				
			}
			
			while( System.nanoTime() - vTickTime <= ScenarioInformation.getInstance().getGameTickTime() * 1000000000 ){
			
				try {
					Thread.sleep( 5 );
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
	}
	
	public void setKickoff( Team aTeam ){
	
		mKicks.clear();
		
		if( aTeam == Team.None ){
			
			ScenarioInformation.getInstance().getWorldData().setPlayMode( PlayMode.KickOff );
			
			ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().setLocation(
					ReferencePointName.FieldCenter.getRelativePosition().getX() * ScenarioInformation.getInstance().getXFactor(), 
					ReferencePointName.FieldCenter.getRelativePosition().getY() * ScenarioInformation.getInstance().getYFactor() );
			
		} else if( aTeam == Team.Blue ){
			
			ScenarioInformation.getInstance().getWorldData().setPlayMode( PlayMode.KickOffBlue );
			
			ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().setLocation(
					ReferencePointName.FieldCenter.getRelativePosition().getX() * ScenarioInformation.getInstance().getXFactor(), 
					ReferencePointName.FieldCenter.getRelativePosition().getY() * ScenarioInformation.getInstance().getYFactor() );
			
		} else if( aTeam == Team.Yellow ){
			
			ScenarioInformation.getInstance().getWorldData().setPlayMode( PlayMode.KickOffYellow );
			
			ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().setLocation(
					ReferencePointName.FieldCenter.getRelativePosition().getX() * ScenarioInformation.getInstance().getXFactor(), 
					ReferencePointName.FieldCenter.getRelativePosition().getY() * ScenarioInformation.getInstance().getYFactor() );
			
		}
		
	}

	private void stopRealBots() {
		for( BotAI vBotAI : ScenarioInformation.getInstance().getBotAIs().values() ){
			
			ScenarioInformation.getInstance().getBotControl().sendMovement( vBotAI.getRcId(), 0, 0 );
			
		}
	}

	private void processBotAIActions() {
		
		Command vCommand;
		List<BotAI> vListofPlayers = new ArrayList<BotAI>( ScenarioInformation.getInstance().getBotAIs().values() );
		Collections.shuffle( vListofPlayers );
		
		for( BotAI vBotAI : vListofPlayers ){
			
			vCommand = Command.unmarshallXMLPositionDataPackageString( vBotAI.getLastAction() );
			
			if( vCommand != null ){
				
				if( vCommand.isMovement() ){
					
					moveBot( vCommand.getMovement() , vBotAI );
					
				} else if( vCommand.isKick() && ScenarioInformation.getInstance().getWorldData().getPlayMode().canBallMove() ){
					
					kickBall( vCommand.getKick(), vBotAI );
					
				}
			}
			
		}
	}

	private void kickBall( Kick aKick, BotAI aBotAI ) {
				
		for( Player vPlayer : ScenarioInformation.getInstance().getWorldData().getListOfPlayers() ){
			
			if( vPlayer.getId() == aBotAI.getVtId() && vPlayer.getPosition().distance( ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition() ) <= 0.026 ){ // TODO: remove magic number
				
				Core.getLogger().trace(" Player {} tries to kick {} with distance {}", vPlayer, ScenarioInformation.getInstance().getWorldData().getBallPosition(), vPlayer.getPosition().distance( ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition() ));
				
				mKicks.add(new KickEcho( aKick, vPlayer.getOrientationAngle() ));
				
				break;
				
			}
			
		}
	}

	private void moveBall() {

		ServerPoint vBallForce = new ServerPoint( 0, 0 );
		List<KickEcho> vOldKicks = new ArrayList<KickEcho>();

		Core.getLogger().trace("Ball moved by {} forces", mKicks.size() );
			
		for( KickEcho vKickEcho : mKicks ){
			
			if( vKickEcho.isAlive() ){
				
				vBallForce.add( vKickEcho );
				vKickEcho.reduceLife();
				
			} else {
				
				vOldKicks.add( vKickEcho );
				
			}
			
		}
		
		for( KickEcho vKickEcho : vOldKicks ){
			
			mKicks.remove( vKickEcho );
			
		}
		
		if( vBallForce.getLengthOfVector() > 0.01 ){ //TODO: Magic number
			
			vBallForce.setVectorLengthTo( 0.01 );
			
		}
		
		Core.getLogger().trace("Ball moves {}° with {}", vBallForce.getDegreeOfVector(), vBallForce.getLengthOfVector());
			
		ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().setLocation(
				ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().getX() + vBallForce.getX(),
				ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().getY() + vBallForce.getY() );
		
		
		if( mSimulation.get() ){
			
			ballCollisionWithPlayerSimulation();
			
		}
		
	}

	private void ballCollisionWithPlayerSimulation() {
		
		double vAngleToOtherPlayer;
		
		for( Player vPlayerDist : ScenarioInformation.getInstance().getWorldData().getListOfPlayers() ){
			
			if( vPlayerDist.getPosition().distance( ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition() ) < 0.020 ){
				vAngleToOtherPlayer = Math.atan2( ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().getY() - vPlayerDist.getPosition().getY(), 
						ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().getX() - vPlayerDist.getPosition().getX() );
				
				ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().setLocation(
						vPlayerDist.getPosition().getX() + 0.02 * Math.cos( vAngleToOtherPlayer ),
						vPlayerDist.getPosition().getY() + 0.02 * Math.sin( vAngleToOtherPlayer ) );
				vPlayerDist.getPosition().setLocation(
								vPlayerDist.getPosition().getX() + 0.0025 * Math.cos( vAngleToOtherPlayer ),
								vPlayerDist.getPosition().getY() + 0.0025 * Math.sin( vAngleToOtherPlayer ) );
						
			}
			
		}
		
	}

	private void isBallInGoal( BallPosition aOldBallPosition ) {
		
		double vM, vB, vIntersection;
		
		if( ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().getX() < ScenarioInformation.getInstance().getWorldData().getMapOfReferencePoints().get(ReferencePointName.YellowGoalCornerBottom).getPosition().getX() &&
				aOldBallPosition.getPosition().getX() >= ScenarioInformation.getInstance().getWorldData().getMapOfReferencePoints().get(ReferencePointName.YellowGoalCornerBottom).getPosition().getX() ){
			
			vM = (ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().getY() - aOldBallPosition.getPosition().getY()) /
				 (ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().getX() - aOldBallPosition.getPosition().getX());
			vB = aOldBallPosition.getPosition().getY() - vM * aOldBallPosition.getPosition().getX();
			vIntersection = vM * ScenarioInformation.getInstance().getWorldData().getMapOfReferencePoints().get(ReferencePointName.YellowGoalCornerBottom).getPosition().getX() + vB;
			
			if( vIntersection > ScenarioInformation.getInstance().getWorldData().getMapOfReferencePoints().get(ReferencePointName.YellowGoalCornerBottom).getPosition().getY() &&
			    vIntersection < ScenarioInformation.getInstance().getWorldData().getMapOfReferencePoints().get(ReferencePointName.YellowGoalCornerTop).getPosition().getY()){
				
				ScenarioInformation.getInstance().getWorldData().getScore().setScoreBlueTeam( ScenarioInformation.getInstance().getWorldData().getScore().getScoreBlueTeam() + 1 );
				
				setKickoff( Team.Yellow );
			}
			
		}
		if( ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().getX() > ScenarioInformation.getInstance().getWorldData().getMapOfReferencePoints().get(ReferencePointName.BlueGoalCornerBottom).getPosition().getX() &&
				aOldBallPosition.getPosition().getX() <= ScenarioInformation.getInstance().getWorldData().getMapOfReferencePoints().get(ReferencePointName.BlueGoalCornerBottom).getPosition().getX() ){
			
			vM = (ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().getY() - aOldBallPosition.getPosition().getY()) /
				 (ScenarioInformation.getInstance().getWorldData().getBallPosition().getPosition().getX() - aOldBallPosition.getPosition().getX());
			vB = aOldBallPosition.getPosition().getY() - vM * aOldBallPosition.getPosition().getX();
			vIntersection = vM * ScenarioInformation.getInstance().getWorldData().getMapOfReferencePoints().get(ReferencePointName.BlueGoalCornerBottom).getPosition().getX() + vB;
			
			if( vIntersection > ScenarioInformation.getInstance().getWorldData().getMapOfReferencePoints().get(ReferencePointName.BlueGoalCornerBottom).getPosition().getY() &&
			    vIntersection < ScenarioInformation.getInstance().getWorldData().getMapOfReferencePoints().get(ReferencePointName.BlueGoalCornerTop).getPosition().getY()){
				
				ScenarioInformation.getInstance().getWorldData().getScore().setScoreYellowTeam( ScenarioInformation.getInstance().getWorldData().getScore().getScoreYellowTeam() + 1 );
				
				setKickoff( Team.Blue );
				
			}
			
		}
		
	}
	
	private void moveBot( Movement aMovement, BotAI aBotAI) {
		
		if( mSimulation.get() ){
			
			double vSpeed, vRotation, vRightDegree, vLeftDegree, vAngleToOtherPlayer;
			
			vSpeed = ( aMovement.getLeftWheelVelocity() + aMovement.getRightWheelVelocity() ) / 200.0 * ScenarioInformation.getInstance().getSimulationBotSpeed(); //TODO: remove magic number

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
					
					for( Player vPlayerDist : ScenarioInformation.getInstance().getWorldData().getListOfPlayers() ){
						
						if( vPlayerDist.getId() != vPlayer.getId() &&
							vPlayerDist.getPosition().distance( vPlayer.getPosition() ) < 0.025 ){
							Core.getLogger().trace("Distance {} between {} and {}", vPlayerDist.getPosition().distance( vPlayer.getPosition() ), vPlayerDist.getPosition(), vPlayer.getPosition());
							vAngleToOtherPlayer = Math.atan2( vPlayer.getPosition().getY() - vPlayerDist.getPosition().getY(), 
									vPlayer.getPosition().getX() - vPlayerDist.getPosition().getX() );

							vPlayer.getPosition().setLocation(
									vPlayerDist.getPosition().getX() + 0.025 * Math.cos( vAngleToOtherPlayer ),
									vPlayerDist.getPosition().getY() + 0.025 * Math.sin( vAngleToOtherPlayer ) );
							vPlayerDist.getPosition().setLocation(
									vPlayerDist.getPosition().getX() - 0.005 * Math.cos( vAngleToOtherPlayer ),
									vPlayerDist.getPosition().getY() - 0.005 * Math.sin( vAngleToOtherPlayer ) );
									
						}
						
					}

					Core.getLogger().trace( "Added rotation {} and speed {} from movment {} to player {}({})", vRotation, vSpeed, aMovement, vPlayer, aBotAI );
					
					return;
					
				}
				
			}
			
			ScenarioInformation.getInstance().getWorldData().getListOfPlayers().add( 
					new Player( new PositionObjectBot( PositionObjectType.BOT, aBotAI.getVtId(), "", 
							new double[]{ Math.random(), Math.random()}, null, 0.0 ) , 
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
	
	public void setSimulation( boolean aSimulation ){
		
		mSimulation.set( aSimulation );
		
	}
	
	public boolean isSuspended(){
		
		return mSuspended.get();
		
	}
	
	public boolean isStarted(){
		
		return mGameStarted.get();
		
	}
	
	public synchronized void stopBall(){
		
		mKicks.clear();
		
	}

	public boolean isAutomaticGame() {
		return mAutomaticGame.get();
	}

	public void setAutomaticGame( boolean aAutomaticGame ) {
		mAutomaticGame.set( aAutomaticGame );
	}
	
	public double getTimeLeftForCurrentPlayMode(){
		
		if( mTimesToRun.get( ScenarioInformation.getInstance().getWorldData().getPlayMode() ) == null ){
			return 0.0;
		}
		
		return mTimesToRun.get( ScenarioInformation.getInstance().getWorldData().getPlayMode() );
		
	}
	
	public void setPlayMode(){
		
		mTimesToRun.put( ScenarioInformation.getInstance().getWorldData().getPlayMode(), ScenarioInformation.getInstance().getPlayModeTimeToRun( ScenarioInformation.getInstance().getWorldData().getPlayMode() ) );
		
	}
	
}

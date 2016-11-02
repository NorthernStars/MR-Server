package de.fh_kiel.robotics.mr.scenario.football.core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import de.fh_kiel.robotics.mr.scenario.football.core.ScenarioCore;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.BallPosition;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.PlayMode;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.Player;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.ReferencePoint;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.ReferencePointName;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.Score;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.ServerPoint;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.WorldData;
import de.fh_kiel.robotics.mr.server.misc.botcontrol.interfaces.BotControl;
import de.fh_kiel.robotics.mr.server.misc.graphics.interfaces.Graphics;
import net.jcip.annotations.GuardedBy;

public class ScenarioInformation {
	
	private static ScenarioInformation INSTANCE;
	
    public static ScenarioInformation getInstance() {
        
        if( ScenarioInformation.INSTANCE == null){
        	ScenarioCore.getLogger().debug( "Creating ScenarioInformation-instance." );
        	ScenarioInformation.INSTANCE = new ScenarioInformation();
        }

        ScenarioCore.getLogger().trace( "Retrieving ScenarioInformation-instance." );
        return ScenarioInformation.INSTANCE;
        
    }
	
	@GuardedBy("this") private WorldData mWorldData;
	
	private String mScenarioName = "Mixed-Reality Football";
	private double mXFactor = 1, 
			mYFactor = 0.75, 
			mMaxValue = 1000,
			mGameTickTime = 0.05,
			mChanceToNoIdentification = 0,
			mRightWheelError = 0,
			mLeftWheelError = 0;
	
	
	private Graphics mGraphics;
	private BotControl mBotControl;
	@GuardedBy("this") private ConcurrentHashMap<Integer, BotAI> mBotAIs = new ConcurrentHashMap<Integer, BotAI>();
	
	private HashMap<PlayMode, Double> mTimesToRun = new HashMap<PlayMode, Double>();

	private double mSimulationBotSpeed = 0.006;
	
	private ScenarioInformation() {

		mWorldData = new WorldData(
				0.0,
				PlayMode.KickOff,
				new Score(),
				22,
				new BallPosition( ReferencePointName.Ball, 
								new ServerPoint( 
								ReferencePointName.FieldCenter.getRelativePosition().getX() * mXFactor, 
								ReferencePointName.FieldCenter.getRelativePosition().getY() * mYFactor) ),
				new HashMap<Integer,Player>(),
				ReferencePoint.getDefaultMap( mXFactor, mYFactor ) );
		
		ScenarioCore.getLogger().debug( "Created Worlddata: " + mWorldData );
		
	}

	public synchronized WorldData getWorldData() {

		return mWorldData;
		
	}

	public synchronized void setWorldData( WorldData aWorldData ) {

		mWorldData = aWorldData;
		
	}

	public double getMaxAbsoluteValue() {
		
		return mMaxValue;
	
	}

	public synchronized void setPlayers(List<Player> aListOfPlayers) {
		
		mWorldData.setListOfPlayers( aListOfPlayers );
		
	}

	public synchronized void setBall(BallPosition vBall) {
		
		mWorldData.setBallPosition( vBall );
		
	}

	public synchronized void addTimePlayed( double aTimeToAdd ) {
		
		mWorldData.setPlayTime( mWorldData.getPlayTime() + aTimeToAdd );
		
	}

	public synchronized void addTickPlayed() {
		
		mWorldData.setPlayTime( mWorldData.getPlayTime() + mGameTickTime );
		
	}

	public double getXFactor() {
		return mXFactor;
	}

	public double getYFactor() {
		return mYFactor;
	}

	public Graphics getGraphics() {
		return mGraphics;
	}

	public void setGraphics( Graphics aGraphics ) {
		this.mGraphics = aGraphics;
	}

	public BotControl getBotControl() {
		return mBotControl;
	}

	public void setBotControl(BotControl mBotControl) {
		this.mBotControl = mBotControl;
	}

	public ConcurrentHashMap<Integer, BotAI> getBotAIs() {
		return mBotAIs;
	}

	public boolean addBotAI( BotAI aBotAI ) {
		
		if( mBotAIs.putIfAbsent( aBotAI.getVtId(), aBotAI ) == null ){
			
			ScenarioCore.getLogger().info( "Registered new botAI: {}", aBotAI.toString() );
			return true;
			
		} else {
			
			ScenarioCore.getLogger().info(" BotAI with used id tryed to connect: {}", aBotAI.toString() );
			return false;
		}
		
	}
	
	public boolean removeBotAI( BotAI aBotAI ) {
		
		if( mBotAIs.remove( aBotAI.getVtId() ) != null ){
			
			ScenarioCore.getLogger().info( "Unegistered botAI: {}", aBotAI.toString() );
			return true;
			
		} else {
			
			ScenarioCore.getLogger().info(" No AI to unregister: {}", aBotAI.toString() );
			return false;
		}
		
		
	}

	/**
	 * @return the mScenarioName
	 */
	public String getmScenarioName() {
		return mScenarioName;
	}

	public double getGameTickTime() {
		return mGameTickTime;
	}

	public synchronized void setGameTickTime( double aGameTickTime ) {
		mGameTickTime = aGameTickTime;
	}

	public void setYFactor( double aYFactor ) {
		mYFactor = aYFactor;
		mWorldData.setMapReferencePoints( ReferencePoint.getDefaultMap( mXFactor, mYFactor ) );
	}

	public double getMaxValue() {
		return mMaxValue;
	}

	public void setMaxValue( double aMaxValue ) {
		mMaxValue = aMaxValue;
	}

	public double getPlayModeTimeToRun( PlayMode aCurrentPlaymode ) {
		
		if( mTimesToRun.get( aCurrentPlaymode ) == null ){
			return aCurrentPlaymode.getDefaultTimeToRun();
		}
		
		return mTimesToRun.get( aCurrentPlaymode );
	}
	
	public synchronized void setPlayModeTimeToRun( PlayMode aPlaymode, double aTime ) {
		
		mTimesToRun.put( aPlaymode, aTime );
		
	}

	public double getSimulationBotSpeed() {
		// TODO Auto-generated method stub
		return mSimulationBotSpeed;
	}


	public void setSimulationBotSpeed( double aSimulationBotSpeed ) {
		// TODO Auto-generated method stub
		mSimulationBotSpeed = aSimulationBotSpeed;
	}

	public double getChanceToNoIdentification() {
		return mChanceToNoIdentification;
	}

	public void setChanceToNoIdentification( double aChanceToNoIdentification ) {
		mChanceToNoIdentification = aChanceToNoIdentification;
	}

	public double getRightWheelError() {
		return mRightWheelError;
	}

	public void setRightWheelError( double aRightWheelError ) {
		mRightWheelError = aRightWheelError;
	}

	public double getLeftWheelError() {
		return mLeftWheelError;
	}

	public void setLeftWheelError( double aLeftWheelError ) {
		mLeftWheelError = aLeftWheelError;
	}
	
	
	
}

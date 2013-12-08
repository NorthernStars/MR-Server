package mrscenariofootball.core.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.GuardedBy;
import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.worlddata.server.BallPosition;
import mrscenariofootball.core.data.worlddata.server.PlayMode;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePoint;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.Score;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;
import mrscenariofootball.core.data.worlddata.server.WorldData;
import mrservermisc.botcontrol.interfaces.BotControl;
import mrservermisc.graphics.interfaces.Graphics;

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
	
	private double mXFactor = 1, 
			mYFactor = 0.75, 
			mMaxValue = 1000;
	
	private Graphics mGraphics;
	private BotControl mBotControl;
	@GuardedBy("this") private ConcurrentHashMap<Integer, BotAI> mBotAIs = new ConcurrentHashMap<Integer, BotAI>();
	
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
				new ArrayList<Player>(),
				ReferencePoint.getDefaultList( mXFactor, mYFactor ) );
		
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
	
}

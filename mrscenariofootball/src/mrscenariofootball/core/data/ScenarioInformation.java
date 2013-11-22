package mrscenariofootball.core.data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

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

public class ScenarioInformation {
	
	@GuardedBy("this") private WorldData mWorldData;
	private double mXFactor = 1, mYFactor = 0.75, mMaxValue = 1000;
	
	
	public ScenarioInformation() {

		mWorldData = new WorldData(
				0.0,
				PlayMode.KickOff,
				new Score(),
				22,
				new BallPosition( ReferencePointName.Ball, 
								new ServerPoint( 
								ReferencePointName.FieldCenter.getRelativePosition().getX() * mXFactor, 
								ReferencePointName.FieldCenter.getRelativePosition().getY() * mYFactor) ), //TODO: also relative
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
	

}

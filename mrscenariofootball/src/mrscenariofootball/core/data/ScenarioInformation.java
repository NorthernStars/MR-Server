package mrscenariofootball.core.data;

import java.util.ArrayList;

import net.jcip.annotations.GuardedBy;
import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.worlddata.server.BallPosition;
import mrscenariofootball.core.data.worlddata.server.PlayMode;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePoint;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.Score;
import mrscenariofootball.core.data.worlddata.server.WorldData;

public class ScenarioInformation {
	
	@GuardedBy("this") private WorldData mWorldData;
	
	public ScenarioInformation() {

		mWorldData = new WorldData(
				0.0,
				PlayMode.KickOff,
				new Score(),
				22,
				new BallPosition( ReferencePointName.Ball, ReferencePointName.FieldCenter.getRelativePosition() ),
				new ArrayList<Player>(),
				ReferencePoint.getDefaultList( 1, 1 ) );
		
		ScenarioCore.getLogger().debug( "Created Worlddata: " + mWorldData );
		
	}

	public WorldData getWorldData() {

		return mWorldData;
		
	}

	public synchronized void setWorldData( WorldData aWorldData ) {

		mWorldData = aWorldData;
		
	}

}

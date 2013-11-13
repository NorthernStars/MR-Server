package mrscenariofootball.core.data.worlddata.server;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.network.data.position.PositionDataPackage;
import mrservermisc.network.xml.Helpers;

@XmlRootElement(name="WorldData")
public class WorldData{
    
    @XmlElement(name="time")
	double mPlayTime;
	@XmlElement(name="playMode")
	PlayMode mPlayMode;
	@XmlElement(name="score")
	Score mScore;

	@XmlElement(name="max_agent")
	int mMaxNumberOfAgents;

	@XmlElement(name="ball")
	BallPosition mBallPosition;

	@XmlElement(name="players")
	ArrayList<Player> mListOfPlayers;

    @XmlElement(name="flag")
    ArrayList<ReferencePoint> mReferencePoints;
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, WorldData.class );
		
	}

	public static WorldData unmarshallXMLPositionDataPackageString( String aXMLWorldData ){
			
		return Helpers.unmarshallXMLString( aXMLWorldData, WorldData.class );
		
	}
	
	@Override
	public String toString() {
		return "WorldData [mPlayTime=" + mPlayTime + ", mPlayMode=" + mPlayMode
				+ ", mScore=" + mScore + ", mMaxNumberOfAgents="
				+ mMaxNumberOfAgents + ", mBallPosition=" + mBallPosition
				+ ", mListOfPlayers=" + mListOfPlayers + ", mReferencePoints="
				+ mReferencePoints + "]";
	}
	
	public static void createWorldDataSchema( ){

		Helpers.createXMLSchema( "worlddataschema.xsd",
				WorldData.class,
				BallPosition.class,
				Player.class,
				PlayMode.class,
				ReferencePoint.class,
				ReferencePointName.class,
				Score.class,
				Team.class  );

	}
	
}

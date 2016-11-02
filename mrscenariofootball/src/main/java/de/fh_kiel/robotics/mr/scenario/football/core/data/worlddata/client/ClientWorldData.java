package de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.client;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.fh_kiel.robotics.mr.scenario.football.core.data.BotAI;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.PlayMode;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.Player;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.ReferencePoint;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.Score;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.WorldData;
import mrservermisc.network.xml.Helpers;

@XmlRootElement(name="WorldData")
public class ClientWorldData {
    
    @XmlElement(name="time")
	double mPlayTime;
	@XmlElement(name="playMode")
	PlayMode mPlayMode;
	@XmlElement(name="score")
	Score mScore;

	@XmlElement(name="agent_id")
	int mAgentId;
	@XmlElement(name="nickname")
	String mAgentNickname;
	@XmlElement(name="status")
	@XmlJavaTypeAdapter(StatusStringToBooleanAdapter.class)
	Boolean mAgentStatus;
	
	@XmlElement(name="max_agent")
	int mMaxNumberOfAgents;

	@XmlElement(name="ball")
	ClientReferencePoint mBallPosition;

	@XmlElement(name="teamMate")
	List<ClientPlayer> mListOfTeamMates;
	@XmlElement(name="opponent")
	List<ClientPlayer> mListOfOpponents;

    @XmlElement(name="flag")
    List<ClientReferencePoint> mReferencePoints;
	
    public ClientWorldData(){}
    
	public ClientWorldData( WorldData aWorldData, Player aFoundRealBot ) {

		mPlayTime = aWorldData.getPlayTime();
		mPlayMode = aWorldData.getPlayMode();
		mScore = aWorldData.getScore();
		
		mAgentId = aFoundRealBot.getId();
		mAgentNickname = aFoundRealBot.getNickname();
		mAgentStatus = true;
		
		mMaxNumberOfAgents = aWorldData.getMaxNumberOfAgents();

		mBallPosition = new ClientReferencePoint( aWorldData.getBallPosition(), aFoundRealBot );
		
		mListOfTeamMates = new ArrayList<ClientPlayer>();
		mListOfOpponents = new ArrayList<ClientPlayer>();
		for( Player vPlayer : aWorldData.getListOfPlayers() ){
			
			if( vPlayer.getTeam() != aFoundRealBot.getTeam() ){
				
				mListOfOpponents.add( new ClientPlayer( vPlayer, aFoundRealBot ) );
				
			} else if( vPlayer.getTeam() == aFoundRealBot.getTeam() && vPlayer.getId() != aFoundRealBot.getId() ){
				
				mListOfTeamMates.add( new ClientPlayer( vPlayer, aFoundRealBot ) );
				
			}
			
		}
		
		mReferencePoints = new ArrayList<ClientReferencePoint>( aWorldData.getReferencePoints().size() );
		for( ReferencePoint aReferencePoint : aWorldData.getReferencePoints() ){
			
			mReferencePoints.add( new ClientReferencePoint( aReferencePoint, aFoundRealBot) );
			
		}
		
	}

	public ClientWorldData( WorldData aWorldData, BotAI aBotAI) {

		mPlayTime = aWorldData.getPlayTime();
		mPlayMode = aWorldData.getPlayMode();
		mScore = aWorldData.getScore();
		
		mAgentId = aBotAI.getVtId();
		mAgentNickname = aBotAI.getName();
		mAgentStatus = false;
		
		mMaxNumberOfAgents = aWorldData.getMaxNumberOfAgents();
		
	}

	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, ClientWorldData.class );
		
	}

	public static ClientWorldData unmarshallXMLPositionDataPackageString( String aXMLClientWorldData ){
			
		return Helpers.unmarshallXMLString( aXMLClientWorldData, ClientWorldData.class );
		
	}

	@Override
	public String toString() {
		return "ClientWorldData [mPlayTime=" + mPlayTime + ", mPlayMode="
				+ mPlayMode + ", mScore=" + mScore + ", mAgentId=" + mAgentId
				+ ", mAgentNickname=" + mAgentNickname + ", mAgentStatus="
				+ mAgentStatus + ", mMaxNumberOfAgents=" + mMaxNumberOfAgents
				+ ", mBallPosition=" + mBallPosition + ", mListOfTeamMates="
				+ mListOfTeamMates + ", mListOfOpponents=" + mListOfOpponents
				+ ", mReferencePoints=" + mReferencePoints + "]";
	}
	
}

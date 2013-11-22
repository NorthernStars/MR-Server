package mrscenariofootball.core.data.worlddata.server;

import javax.xml.bind.annotation.XmlElement;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.BotAI;
import mrservermisc.network.data.position.PositionObjectBot;

public class Player extends ReferencePoint {

	@XmlElement(name="id")
	int mId;
	
	@XmlElement(name="nickname")
	String mNickname;
	
	@XmlElement(name="status")
	Boolean mStatus;
	
	@XmlElement(name="orientationangle")
	double mOrientationAngle;
	
	@XmlElement(name="team")
	Team mTeam;

	public Player() {}

	public Player( PositionObjectBot aFoundBot, BotAI aBotAI ) {

		super( ReferencePointName.Player, new ServerPoint( 	aFoundBot.getLocation()[0] * ScenarioCore.getInstance().getScenarioInformation().getXFactor(),
															aFoundBot.getLocation()[1] * ScenarioCore.getInstance().getScenarioInformation().getYFactor() ) );
		mId = aFoundBot.getId();
		mOrientationAngle = aFoundBot.getAngle();
		if( aBotAI == null ){
			
			mStatus = false;
			mTeam = Team.None;
			mNickname = "NoFound";
			
		} else {
		
			mStatus = true;
			mTeam = aBotAI.getTeam() == 1 ? Team.Yellow : Team.Blue;
			mNickname = aBotAI.getName();
		
		}
		
	}

	public Player( Player aPlayer ) {
		
		super(aPlayer);
		
		mId = aPlayer.getId();
		mOrientationAngle = aPlayer.getOrientationAngle();
		mStatus = aPlayer.getStatus();
		mTeam = aPlayer.getTeam();
		mNickname = aPlayer.getNickname();		
		
	}

	@Override
	public String toString() {
		return "Player [mId=" + mId + ", mNickname=" + mNickname + ", mStatus="
				+ mStatus + ", mOrientationAngle=" + mOrientationAngle
				+ ", mTeam=" + mTeam + ", mPointName=" + mPointName
				+ ", mPosition=" + mPosition + "]";
	}

	public int getId() {
		return mId;
	}

	public String getNickname() {
		return mNickname;
	}

	public Boolean getStatus() {
		return mStatus;
	}

	public double getOrientationAngle() {
		return mOrientationAngle;
	}

	public Team getTeam() {
		return mTeam;
	}
	
}

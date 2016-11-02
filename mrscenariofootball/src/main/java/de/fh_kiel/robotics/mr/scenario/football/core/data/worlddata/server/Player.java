package de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import de.fh_kiel.robotics.mr.scenario.football.core.data.BotAI;
import de.fh_kiel.robotics.mr.scenario.football.core.data.ScenarioInformation;
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

		super( ReferencePointName.Player, new ServerPoint( 	aFoundBot.getLocation()[0] * ScenarioInformation.getInstance().getXFactor(),
															aFoundBot.getLocation()[1] * ScenarioInformation.getInstance().getYFactor() ) );
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

	@XmlTransient
	public double getOrientationAngle() {
		return mOrientationAngle;
	}

	public void setOrientationAngle( double aOrientationAngle ) {
		
		if( aOrientationAngle > 180 ){
			
			aOrientationAngle -= 360;
			
		}
		if( aOrientationAngle < -180 ){
			
			aOrientationAngle += 360;
			
		}
		
		mOrientationAngle = aOrientationAngle;
	}

	public Team getTeam() {
		return mTeam;
	}
	
}

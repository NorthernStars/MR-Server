package de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.client;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.Player;

public class ClientPlayer extends ClientReferencePoint{

	@XmlElement(name="id")
	int mId;
	
	@XmlElement(name="nickname")
	String mNickname;
	
	@XmlElement(name="status")
    @XmlJavaTypeAdapter(StatusStringToBooleanAdapter.class)
	Boolean mStatus;
	
	@XmlElement(name="orientation")
	double mOrientation;
	
	public ClientPlayer() {}
	
	public ClientPlayer( Player aPlayer, Player aFoundRealBot) {

		super( aPlayer, aFoundRealBot );
		
		mId = aPlayer.getId();
		mNickname = aPlayer.getNickname();
		mStatus = aPlayer.getStatus();
		
		mOrientation = aPlayer.getOrientationAngle();
		
	}

	@Override
	public String toString() {
		return "ClientPlayer [mId=" + mId + ", mNickname=" + mNickname
				+ ", mStatus=" + mStatus + ", mOrientation=" + mOrientation
				+ "]";
	}

}

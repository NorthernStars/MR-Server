package mrscenariofootball.core.data.worlddata.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.logging.log4j.core.layout.RFC5424Layout;

import mrservermisc.bots.interfaces.Bot;
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

	public Player( PositionObjectBot vFoundBot, Bot vConnectedBot ) {

		super( ReferencePointName.Player, new ServerPoint( vFoundBot.getLocation()[0], vFoundBot.getLocation()[1] ) );
		mId = vFoundBot.getId();
		mOrientationAngle = vFoundBot.getAngle();
		if( vConnectedBot == null ){
			
			mStatus = false;
			
		} else {
		
			mStatus = true;
			mTeam = vConnectedBot.getTeam() == 1 ? Team.Yellow : Team.Blue;
		
		}
		
	}

	@Override
	public String toString() {
		return "Player [mId=" + mId + ", mNickname=" + mNickname + ", mStatus="
				+ mStatus + ", mOrientationAngle=" + mOrientationAngle
				+ ", mTeam=" + mTeam + ", mPointName=" + mPointName
				+ ", mPosition=" + mPosition + "]";
	}
	
}

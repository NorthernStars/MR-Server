package mrscenariofootball.core.data.worlddata.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

	@Override
	public String toString() {
		return "Player [mId=" + mId + ", mNickname=" + mNickname + ", mStatus="
				+ mStatus + ", mOrientationAngle=" + mOrientationAngle
				+ ", mTeam=" + mTeam + ", mPointName=" + mPointName
				+ ", mPosition=" + mPosition + "]";
	}
	
}

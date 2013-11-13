package mrservermisc.network.data.position;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="positionobjectbot")
public class PositionObjectBot extends PositionObject {

	@XmlElement(name="angle")
	double mAngle;
	
	public PositionObjectBot(){}

	public PositionObjectBot(PositionObjectType aObjectType, int aId, String aName,
			double[] aLocation, double[] aColor, double aAngle) {
		super( aObjectType, aId, aName, aLocation, aColor);
		mAngle = aAngle;
	}

	@Override
	public String toString() {
		return "VisionObjectBot [mAngle=" + mAngle + ", mObjectType="
				+ mObjectType + ", mId=" + mId + ", mName=" + mName
				+ ", mLocation=" + Arrays.toString(mLocation) + ", mColor="
				+ Arrays.toString(mColor) + "]";
	}

	public double getAngle() {
		return mAngle;
	}
	
}

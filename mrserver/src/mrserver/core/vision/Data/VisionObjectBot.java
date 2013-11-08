package mrserver.core.vision.Data;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="visionobjectbot")
public class VisionObjectBot extends VisionObject {

	@XmlElement(name="angle")
	double mAngle;
	
	public VisionObjectBot(){}

	public VisionObjectBot(VisionObjectType aObjectType, int aId, String aName,
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
	
}

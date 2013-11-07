package mrserver.core.vision.Data;

import javax.xml.bind.annotation.XmlElement;

public class VisionObjectBot extends VisionObject {

	@XmlElement(name="angle")
	double mAngle;
	
	public VisionObjectBot(){}

	public VisionObjectBot(VisionObjectType aObjectType, int aId, String aName,
			double[] aLocation, double[] aColor, double aAngle) {
		super( aObjectType, aId, aName, aLocation, aColor);
		mAngle = aAngle;
	}
	
}

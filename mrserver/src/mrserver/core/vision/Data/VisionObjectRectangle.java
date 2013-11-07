package mrserver.core.vision.Data;

import javax.xml.bind.annotation.XmlElement;

public class VisionObjectRectangle extends VisionObject{

	@XmlElement(name="angle")
	double mAngle;
	@XmlElement(name="size")
	double[] mSize;
	
	public VisionObjectRectangle(){}

	public VisionObjectRectangle(VisionObjectType aObjectType, int aId, String aName,
			double[] aLocation, double[] aColor, double aAngle, double[] aSize) {
		super( aObjectType, aId, aName, aLocation, aColor);
		mAngle = aAngle;
		mSize = aSize;
	}
	
}

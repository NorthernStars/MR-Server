package mrserver.core.vision.Data;

import javax.xml.bind.annotation.XmlElement;

public class VisionObject {

	@XmlElement(name="objecttype")
	VisionObjectType mObjectType;
	@XmlElement(name="id")
	int mId;
	@XmlElement(name="name")
	String mName;
	@XmlElement(name="location")
	double[] mLocation;
	@XmlElement(name="location")
	double[] mColor;
	
	public VisionObject(){}
	
	public VisionObject(VisionObjectType aObjectType, int aId, String aName,
			double[] aLocation, double[] aColor ) {
		super();
		mObjectType = aObjectType;
		mId = aId;
		mName = aName;
		mLocation = aLocation;
		mColor = aColor;
	}
	
}

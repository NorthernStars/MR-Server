package mrserver.core.vision.Data;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;

public abstract class VisionObject {

	@XmlElement(name="objecttype")
	VisionObjectType mObjectType;
	@XmlElement(name="id")
	int mId;
	@XmlElement(name="name")
	String mName;
	@XmlElement(name="location")
	double[] mLocation;
	@XmlElement(name="color")
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

	@Override
	public String toString() {
		return "VisionObject [mObjectType=" + mObjectType + ", mId=" + mId
				+ ", mName=" + mName + ", mLocation="
				+ Arrays.toString(mLocation) + ", mColor="
				+ Arrays.toString(mColor) + "]";
	}
	
}

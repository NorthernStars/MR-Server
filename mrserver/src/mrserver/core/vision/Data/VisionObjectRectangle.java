package mrserver.core.vision.Data;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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

	@Override
	public String toString() {
		return "VisionObjectRectangle [mAngle=" + mAngle + ", mSize="
				+ Arrays.toString(mSize) + ", mObjectType=" + mObjectType
				+ ", mId=" + mId + ", mName=" + mName + ", mLocation="
				+ Arrays.toString(mLocation) + ", mColor="
				+ Arrays.toString(mColor) + "]";
	}
	
}

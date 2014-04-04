package mrservermisc.network.data.position;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({PositionObjectBot.class, PositionObjectRectangle.class})
public abstract class PositionObject {

	@XmlElement(name="objecttype")
	PositionObjectType mObjectType;
	@XmlElement(name="id")
	int mId;
	@XmlElement(name="name")
	String mName;
	@XmlElement(name="location")
	double[] mLocation;
	@XmlElement(name="color")
	double[] mColor;
	
	public PositionObject(){}
	
	public PositionObject(PositionObjectType aObjectType, int aId, String aName,
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

	public PositionObjectType getObjectType() {
		return mObjectType;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public double[] getLocation() {
		return mLocation;
	}

	public double[] getColor() {
		return mColor;
	}
	
}

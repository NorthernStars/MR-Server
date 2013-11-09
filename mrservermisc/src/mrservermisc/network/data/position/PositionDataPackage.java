package mrservermisc.network.data.position;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.network.xml.Helpers;

@XmlRootElement(name="positiondatapackage")
public class PositionDataPackage {
	
	@XmlElement(name="visionmode")
	public VisionMode mVisionMode;
	@XmlElement(name="visionobjects")
	public List<PositionObject> mListOfVisionObjects;
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, PositionDataPackage.class );
		
	}
	
	@Override
	public String toString() {
		return "VisionDataPackage [mVisionMode=" + mVisionMode
				+ ", mListOfVisionObjects=" + mListOfVisionObjects + "]";
	}
	
	
	public static PositionDataPackage unmarshallXMLPositionDataPackageString( String aXMLVisionDataPackage ){
			
		return Helpers.unmarshallXMLString( aXMLVisionDataPackage, PositionDataPackage.class );
		
	}
	
}
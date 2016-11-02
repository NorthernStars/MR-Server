package de.fh_kiel.robotics.mr.server.misc.network.data.position;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

@XmlRootElement(name="positiondatapackage")
public class PositionDataPackage {
	
	@XmlElement(name="visionmode")
	public VisionMode mVisionMode;
	@XmlElement(name="objects")
	public List<PositionObject> mListOfObjects;
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, PositionDataPackage.class );
		
	}
	
	@Override
	public String toString() {
		return "VisionDataPackage [mVisionMode=" + mVisionMode
				+ ", mListOfVisionObjects=" + mListOfObjects + "]";
	}
	
	
	public static PositionDataPackage unmarshallXMLPositionDataPackageString( String aXMLVisionDataPackage ){
			
		return Helpers.unmarshallXMLString( aXMLVisionDataPackage, PositionDataPackage.class );
		
	}
	
}
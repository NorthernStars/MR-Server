package mrserver.core.vision.Data;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mrserver.core.vision.VisionMode;

@XmlRootElement(name="VisionDataPackage")
public class VisionDataPackage {
	
	@XmlElement(name="visionmode")
	public VisionMode mVisionMode;
	@XmlElement(name="visionobjects")
	public List<VisionObject> mListOfVisionObjects;
	
	@Override
	public String toString(){
	    
		StringWriter vXMLDataStream = new StringWriter();
		JAXB.marshal( this, vXMLDataStream );
		
		String vXMLDataString = vXMLDataStream.toString();
		vXMLDataString = vXMLDataString.substring( vXMLDataString.indexOf('>') + 2 );
		
		return vXMLDataString;
		
	}
	
}
/*
public static RawWorldData createRawWorldDataFromXML( String aXMLData ){
	
	RawWorldData vWorldData = JAXB.unmarshal( new StringReader( aXMLData ), RawWorldData.class );
	
	return vWorldData;
	
}
@XmlElement(name="time")
@Override
public String toString(){
    
	StringWriter vXMLDataStream = new StringWriter();
	JAXB.marshal( this, vXMLDataStream );
	
	String vXMLDataString = vXMLDataStream.toString();
	vXMLDataString = vXMLDataString.substring( vXMLDataString.indexOf('>') + 2 );
	
	return vXMLDataString;
	
}
*/

package mrserver.core.vision.Data;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.Level;

import mrserver.core.vision.VisionManagement;
import mrserver.core.vision.VisionMode;

@XmlRootElement(name="visiondatapackage")
public class VisionDataPackage {
	
	@XmlElement(name="visionmode")
	public VisionMode mVisionMode;
	@XmlElement(name="visionobjects")
	public List<VisionObject> mListOfVisionObjects;
	
	public static VisionDataPackage unmarshallXMLVisionDataPackageString( String aXMLVisionDataPackage ){
		
		VisionManagement.getLogger().debug( "Trying to unmarshall xmlstring: " + aXMLVisionDataPackage );
		StringReader vXMLDataStream = new StringReader( aXMLVisionDataPackage );
		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrserver.core.vision.Data.VisionDataPackage.class, 
					mrserver.core.vision.Data.VisionObject.class,
					mrserver.core.vision.Data.VisionObjectBot.class, 
					mrserver.core.vision.Data.VisionObjectRectangle.class );

			Unmarshaller vUnmarshaller = vJAXBContext.createUnmarshaller();
			VisionDataPackage vVisionDataPackage = (VisionDataPackage) vUnmarshaller.unmarshal( vXMLDataStream );
			VisionManagement.getLogger().debug( "Unmarshalled xmlstring to " + vVisionDataPackage != null ? vVisionDataPackage.toString() : "null" );
			
			return vVisionDataPackage;
			
		} catch ( JAXBException vJAXBException ) {

	        VisionManagement.getLogger().error( "Error unmarshalling xmlstring: " + vJAXBException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vJAXBException );
	        
		}
		
		return null;
		
	}
	
	public String toXMLString(){
		
		VisionManagement.getLogger().debug( "Trying to marshall object: " + toString() );
		StringWriter vXMLDataStream = new StringWriter();		
		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrserver.core.vision.Data.VisionDataPackage.class, 
					mrserver.core.vision.Data.VisionObject.class,
					mrserver.core.vision.Data.VisionObjectBot.class, 
					mrserver.core.vision.Data.VisionObjectRectangle.class );
		
	        Marshaller vMarshaller = vJAXBContext.createMarshaller();
	        vMarshaller.marshal( this, vXMLDataStream );
	        VisionManagement.getLogger().debug( "Marshalled object to " + vXMLDataStream );
			
		} catch ( JAXBException vJAXBException ) {

	        VisionManagement.getLogger().error( "Error marshalling object: " + vJAXBException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vJAXBException );
	      
		}
		String vXMLDataString = vXMLDataStream.toString();
		
		return vXMLDataString;
		
	}
	
	@Override
	public String toString() {
		return "VisionDataPackage [mVisionMode=" + mVisionMode
				+ ", mListOfVisionObjects=" + mListOfVisionObjects + "]";
	}
	
}

/*

	        SchemaOutputResolver sor = new MySchemaOutputResolver();
	        jc.generateSchema(sor);
	        
	        
	public class MySchemaOutputResolver extends SchemaOutputResolver {

	    public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
	        File file = new File(suggestedFileName);
	        StreamResult result = new StreamResult(file);
	        result.setSystemId(file.toURI().toURL().toString());
	        return result;
	    }

	}

 
*/
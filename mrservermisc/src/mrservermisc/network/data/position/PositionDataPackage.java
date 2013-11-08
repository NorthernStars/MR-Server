package mrservermisc.network.data.position;

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

import mrservermisc.logging.Loggers;

@XmlRootElement(name="positiondatapackage")
public class PositionDataPackage {
	
	@XmlElement(name="visionmode")
	public VisionMode mVisionMode;
	@XmlElement(name="visionobjects")
	public List<PositionObject> mListOfVisionObjects;
	
	public String toXMLString(){
		
		Loggers.getDataLogger().debug( "Trying to marshall object: " + toString() );
		StringWriter vXMLDataStream = new StringWriter();		
		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrservermisc.network.data.position.PositionDataPackage.class, 
					mrservermisc.network.data.position.PositionObject.class,
					mrservermisc.network.data.position.PositionObjectBot.class, 
					mrservermisc.network.data.position.PositionObjectRectangle.class );
		
	        Marshaller vMarshaller = vJAXBContext.createMarshaller();
	        vMarshaller.marshal( this, vXMLDataStream );
	        Loggers.getDataLogger().debug( "Marshalled object to " + vXMLDataStream );
			
		} catch ( JAXBException vJAXBException ) {

			Loggers.getDataLogger().error( "Error marshalling object: " + vJAXBException.getLocalizedMessage() );
			Loggers.getDataLogger().catching( Level.ERROR, vJAXBException );
	      
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
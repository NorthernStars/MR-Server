package mrservermisc.network.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import mrservermisc.logging.Loggers;

import org.apache.logging.log4j.Level;

public class Helpers {

	public static <T> T unmarshallXMLString( String aXMLConnectionAcknowlege, Class<T> aClass ){
		
		Loggers.getXMLLogger().debug( "Trying to unmarshall xmlstring!: " + aXMLConnectionAcknowlege + " to " + aClass.toString() );
		StringReader vXMLDataStream = new StringReader( aXMLConnectionAcknowlege );
		JAXBContext vJAXBContext;
		try {

			vJAXBContext = JAXBContext.newInstance( aClass );

			Unmarshaller vUnmarshaller = vJAXBContext.createUnmarshaller();
			@SuppressWarnings("unchecked")
			T vCreatedObject =  (T) vUnmarshaller.unmarshal( vXMLDataStream );
			Loggers.getXMLLogger().debug( "Unmarshalled xmlstring to " + vCreatedObject != null ? vCreatedObject.toString() : "null" );
			
			return vCreatedObject;
			
		} catch ( JAXBException vJAXBException ) {

			Loggers.getXMLLogger().error( "Error unmarshalling xmlstring: " + vJAXBException.getLocalizedMessage() );
			Loggers.getXMLLogger().catching( Level.ERROR, vJAXBException );
	        
		}
		
		return null;
		
	}	
	
	@SuppressWarnings("unchecked")
	public static <T> String marshallXMLString( Object aXMLObject, Class<T> aClass ){
		
		Loggers.getXMLLogger().debug( "Trying to marshall object: " + (T)aXMLObject.toString() );
		StringWriter vXMLDataStream = new StringWriter();		
		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( aClass );
		
	        Marshaller vMarshaller = vJAXBContext.createMarshaller();
	        vMarshaller.marshal( aXMLObject, vXMLDataStream );
	        Loggers.getXMLLogger().debug( "Marshalled object to " + vXMLDataStream );
			
		} catch ( JAXBException vJAXBException ) {

			Loggers.getXMLLogger().error( "Error marshalling object: " + vJAXBException.getLocalizedMessage() );
			Loggers.getXMLLogger().catching( Level.ERROR, vJAXBException );
	      
		}
		
		return vXMLDataStream.toString();
		
	}		
	
	public static void createXMLSchema( final String aSchemaName, @SuppressWarnings("rawtypes") Class... aClasses ){
		
		Loggers.getXMLLogger().debug( "Creating schema \"" + aSchemaName + "\" for " + Arrays.toString( aClasses ) );

		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( aClasses );

			vJAXBContext.generateSchema( new SchemaOutputResolver() {
				
				@Override
				public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
			        File file = new File( aSchemaName );
			        StreamResult result = new StreamResult(file);
			        result.setSystemId(file.toURI().toURL().toString());
			        return result;
			    }
			});
			
		} catch ( JAXBException | IOException vException ) {

			Loggers.getXMLLogger().error( "Error gernerating schema " + aSchemaName + ": " + vException.getLocalizedMessage() );
			Loggers.getXMLLogger().catching( Level.ERROR, vException );
	        
		}
		
	}
	
	public static void createNetworkHandschakeSchema( ){

		createXMLSchema( "networkhandshakeschema.xsd",
				mrservermisc.network.handshake.ConnectionAcknowlege.class,
				mrservermisc.network.handshake.ConnectionRequest.class,
				mrservermisc.network.handshake.ConnectionEstablished.class  );

	}
	
	public static void createPositionDataPacketSchema( ){

		createXMLSchema( "positiondatapacketschema.xsd",
				mrservermisc.network.data.position.PositionDataPackage.class, 
				mrservermisc.network.data.position.PositionObject.class,
				mrservermisc.network.data.position.PositionObjectBot.class, 
				mrservermisc.network.data.position.PositionObjectRectangle.class  );

	}
	
	public static void createChangeVisionModeSchema( ){

		createXMLSchema( "changevisionmodeschema.xsd",
				mrservermisc.network.data.visionmode.ChangeVisionMode.class  );

	}

}

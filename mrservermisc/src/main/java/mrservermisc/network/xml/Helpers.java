package mrservermisc.network.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

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

	@SuppressWarnings("rawtypes")
	private static ConcurrentHashMap<Class, Marshaller> MARSHALLER = new ConcurrentHashMap<Class, Marshaller>();
	@SuppressWarnings("rawtypes")
	private static ConcurrentHashMap<Class, Unmarshaller> UNMARSHALLER = new ConcurrentHashMap<Class, Unmarshaller>();
	
	public static <T> T unmarshallXMLString( String aXMLConnectionAcknowlege, Class<T> aClass ){
		
		Loggers.getXMLLogger().debug( "Trying to unmarshall xmlstring!: {} to {}", aXMLConnectionAcknowlege, aClass );
		StringReader vXMLDataStream = new StringReader( aXMLConnectionAcknowlege );
		
		try {
			if( !UNMARSHALLER.containsKey( aClass ) ){
				
				JAXBContext vJAXBContext = JAXBContext.newInstance( aClass );
				UNMARSHALLER.putIfAbsent( aClass, vJAXBContext.createUnmarshaller() );
			}
			
			@SuppressWarnings("unchecked")
			T vCreatedObject =  (T) UNMARSHALLER.get( aClass ).unmarshal( vXMLDataStream );
			Loggers.getXMLLogger().debug( "Unmarshalled xmlstring to {}", vCreatedObject );
			return vCreatedObject;
			
		} catch ( JAXBException vJAXBException ) {

			Loggers.getXMLLogger().error( "Error unmarshalling xmlstring: {}", vJAXBException.getLocalizedMessage() );
			Loggers.getXMLLogger().catching( Level.ERROR, vJAXBException );
	        
		}
		
		return null;
		
	}	
	
	@SuppressWarnings("unchecked")
	public static <T> String marshallXMLString( Object aXMLObject, Class<T> aClass ){
		
		Loggers.getXMLLogger().debug( "Trying to marshall object: {}", (T)aXMLObject );
		StringWriter vXMLDataStream = new StringWriter();		
		
		try {
			if( !MARSHALLER.containsKey( aClass ) ){
				
				JAXBContext vJAXBContext = JAXBContext.newInstance( aClass );
				MARSHALLER.putIfAbsent( aClass, vJAXBContext.createMarshaller() );
			}
		
			MARSHALLER.get( aClass ).marshal( aXMLObject, vXMLDataStream );
			Loggers.getXMLLogger().debug( "Marshalled object to {}", vXMLDataStream );
			
		} catch ( Exception vJAXBException ) {

			Loggers.getXMLLogger().error( "Error marshalling object: {}", vJAXBException.getLocalizedMessage() );
			Loggers.getXMLLogger().catching( Level.ERROR, vJAXBException );
	      
		}
		
		return vXMLDataStream.toString();
		
	}		
	
	public static void createXMLSchema( final String aSchemaName, @SuppressWarnings("rawtypes") Class... aClasses ){
		
		Loggers.getXMLLogger().debug( "Creating schema \"{}\" for {}", aSchemaName, Arrays.toString( aClasses ) );

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

			Loggers.getXMLLogger().error( "Error gernerating schema {}: {}", aSchemaName, vException.getLocalizedMessage() );
			Loggers.getXMLLogger().catching( Level.ERROR, vException );
	        
		}
		
	}
	
	public static void createNetworkHandschakeSchema( ){

		createXMLSchema( "networkhandshakeschema.xsd",
				mrservermisc.network.handshake.server.ConnectionAcknowlege.class,
				mrservermisc.network.handshake.server.ConnectionRequest.class,
				mrservermisc.network.handshake.server.ConnectionEstablished.class  );

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

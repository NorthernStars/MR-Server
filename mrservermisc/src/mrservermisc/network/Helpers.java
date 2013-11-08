package mrservermisc.network;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import mrservermisc.logging.Loggers;
import mrservermisc.network.data.position.PositionDataPackage;
import mrservermisc.network.data.position.PositionObject;
import mrservermisc.network.data.position.PositionObjectBot;
import mrservermisc.network.data.position.PositionObjectRectangle;

import org.apache.logging.log4j.Level;

public class Helpers {
	
	public static PositionDataPackage unmarshallXMLPositionDataPackageString( String aXMLVisionDataPackage ){
			
		Loggers.getDataLogger().debug( "Trying to unmarshall xmlstring: " + aXMLVisionDataPackage );
		StringReader vXMLDataStream = new StringReader( aXMLVisionDataPackage );
		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrservermisc.network.data.position.PositionDataPackage.class, 
					mrservermisc.network.data.position.PositionObject.class,
					mrservermisc.network.data.position.PositionObjectBot.class, 
					mrservermisc.network.data.position.PositionObjectRectangle.class );

			Unmarshaller vUnmarshaller = vJAXBContext.createUnmarshaller();
			PositionDataPackage vVisionDataPackage = (PositionDataPackage) vUnmarshaller.unmarshal( vXMLDataStream );
			Loggers.getDataLogger().debug( "Unmarshalled xmlstring to " + vVisionDataPackage != null ? vVisionDataPackage.toString() : "null" );
			
			return vVisionDataPackage;
			
		} catch ( JAXBException vJAXBException ) {

			Loggers.getDataLogger().error( "Error unmarshalling xmlstring: " + vJAXBException.getLocalizedMessage() );
			Loggers.getDataLogger().catching( Level.ERROR, vJAXBException );
	        
		}
		
		return null;
		
	}
	
	public static void createPositionDataPackageSchema( ){
		
		Loggers.getDataLogger().debug( "Creating schema for mrservermisc.network.data.position" );

		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrservermisc.network.data.position.PositionDataPackage.class, 
					mrservermisc.network.data.position.PositionObject.class,
					mrservermisc.network.data.position.PositionObjectBot.class, 
					mrservermisc.network.data.position.PositionObjectRectangle.class );

			vJAXBContext.generateSchema( new SchemaOutputResolver() {
				
				@Override
				public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
			        File file = new File("positiondatapacketschema.xsd");
			        StreamResult result = new StreamResult(file);
			        result.setSystemId(file.toURI().toURL().toString());
			        return result;
			    }
			});
			
		} catch ( JAXBException | IOException vException ) {

			Loggers.getDataLogger().error( "Error gernerating schema: " + vException.getLocalizedMessage() );
			Loggers.getDataLogger().catching( Level.ERROR, vException );
	        
		}
		
	}
	
	public static Object unmarshallXMLNetworkHandshakeObjectString( String aXMLConnectionAcknowlege ){
		
		Loggers.getNetworkLogger().debug( "Trying to unmarshall handshake xmlstring: " + aXMLConnectionAcknowlege );
		StringReader vXMLDataStream = new StringReader( aXMLConnectionAcknowlege );
		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrservermisc.network.handshake.ConnectionRequest.class,
					mrservermisc.network.handshake.ConnectionAcknowlege.class,
					mrservermisc.network.handshake.ConnectionEstablished.class );

			Unmarshaller vUnmarshaller = vJAXBContext.createUnmarshaller();
			Object vHandshake =  vUnmarshaller.unmarshal( vXMLDataStream );
			Loggers.getNetworkLogger().debug( "Unmarshalled xmlstring to " + vHandshake != null ? vHandshake.toString() : "null" );
			
			return vHandshake;
			
		} catch ( JAXBException vJAXBException ) {

			Loggers.getNetworkLogger().error( "Error unmarshalling xmlstring: " + vJAXBException.getLocalizedMessage() );
			Loggers.getNetworkLogger().catching( Level.ERROR, vJAXBException );
	        
		}
		
		return null;
		
	}
	
	public static void createNetworkHandschakeSchema( ){
		
		Loggers.getNetworkLogger().debug( "Creating schema for mrservermisc.network.handshake" );

		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrservermisc.network.handshake.ConnectionAcknowlege.class,
					mrservermisc.network.handshake.ConnectionRequest.class,
					mrservermisc.network.handshake.ConnectionEstablished.class  );

			vJAXBContext.generateSchema( new SchemaOutputResolver() {
				
				@Override
				public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
			        File file = new File("handshakeschema.xsd");
			        StreamResult result = new StreamResult(file);
			        result.setSystemId(file.toURI().toURL().toString());
			        return result;
			    }
			});
			
		} catch ( JAXBException | IOException vException ) {

			Loggers.getNetworkLogger().error( "Error gernerating schema: " + vException.getLocalizedMessage() );
			Loggers.getNetworkLogger().catching( Level.ERROR, vException );
	        
		}
		
	}

}

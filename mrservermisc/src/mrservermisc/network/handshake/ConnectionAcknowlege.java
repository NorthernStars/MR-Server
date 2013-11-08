package mrservermisc.network.handshake;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import mrserver.core.graphics.GraphicsManagement;
import mrserver.core.vision.VisionManagement;

import org.apache.logging.log4j.Level;

@XmlRootElement(name="connectionacknowlege")
public class ConnectionAcknowlege {

	@XmlElement(name="servername")
	String mMRServerName;
	@XmlElement(name="clientname")
	String mClientGraphicsName;
	@XmlElement(name="connectionallowed")
	boolean mConnectionAllowed;
	
	public String getMRServerName() {
		return mMRServerName;
	}
	@XmlTransient
	public void setMRServerName( String aMRServerName ) {
		this.mMRServerName = aMRServerName;
	}
	public String getClientGraphicsName() {
		return mClientGraphicsName;
	}
	@XmlTransient
	public void setClientGraphicsName( String aClientGraphicsName ) {
		this.mClientGraphicsName = aClientGraphicsName;
	}
	public boolean isConnectionAllowed() {
		return mConnectionAllowed;
	}
	@XmlTransient
	public void setConnectionAllowed( boolean aConnectionAllowed ) {
		this.mConnectionAllowed = aConnectionAllowed;
	}
	
	@Override
	public String toString() {
		return "ConnectionAcknowlege [mMRServerName=" + mMRServerName
				+ ", mClientGraphicsName=" + mClientGraphicsName
				+ ", mConnectionAllowed=" + mConnectionAllowed + "]";
	}
	
	public static ConnectionAcknowlege unmarshallXMLConnectionAcknowlegeString( String aXMLConnectionAcknowlege ){
		
		GraphicsManagement.getLogger().debug( "Trying to unmarshall xmlstring: " + aXMLConnectionAcknowlege );
		StringReader vXMLDataStream = new StringReader( aXMLConnectionAcknowlege );
		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrservermisc.network.handshake.ConnectionRequest.class,
					mrservermisc.network.handshake.ConnectionAcknowlege.class,
					mrservermisc.network.handshake.ConnectionEstablished.class );

			Unmarshaller vUnmarshaller = vJAXBContext.createUnmarshaller();
			ConnectionAcknowlege vConnectionAcknowlege = (ConnectionAcknowlege) vUnmarshaller.unmarshal( vXMLDataStream );
			VisionManagement.getLogger().debug( "Unmarshalled xmlstring to " + vConnectionAcknowlege != null ? vConnectionAcknowlege.toString() : "null" );
			
			return vConnectionAcknowlege;
			
		} catch ( JAXBException vJAXBException ) {

	        GraphicsManagement.getLogger().error( "Error unmarshalling xmlstring: " + vJAXBException.getLocalizedMessage() );
	        GraphicsManagement.getLogger().catching( Level.ERROR, vJAXBException );
	        
		}
		
		return null;
		
	}
	
	public String toXMLString(){
		
		GraphicsManagement.getLogger().debug( "Trying to marshall object: " + toString() );
		StringWriter vXMLDataStream = new StringWriter();		
		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrservermisc.network.handshake.ConnectionAcknowlege.class );
		
	        Marshaller vMarshaller = vJAXBContext.createMarshaller();
	        vMarshaller.marshal( this, vXMLDataStream );
	        GraphicsManagement.getLogger().debug( "Marshalled object to " + vXMLDataStream );
			
		} catch ( JAXBException vJAXBException ) {

			GraphicsManagement.getLogger().error( "Error marshalling object: " + vJAXBException.getLocalizedMessage() );
			GraphicsManagement.getLogger().catching( Level.ERROR, vJAXBException );
	      
		}
		String vXMLDataString = vXMLDataStream.toString();
		
		return vXMLDataString;
		
	}
	
	public static void createGraphicsHandschakeSchema( ){
		
		GraphicsManagement.getLogger().debug( "Creating schema for mrserver.core.graphics.network.handshake" );

		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrservermisc.network.handshake.ConnectionAcknowlege.class,
					mrservermisc.network.handshake.ConnectionRequest.class,
					mrservermisc.network.handshake.ConnectionEstablished.class );

			vJAXBContext.generateSchema( new SchemaOutputResolver() {
				
				@Override
				public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
			        File file = new File("serverclienthandschakeschema.xsd");
			        StreamResult result = new StreamResult(file);
			        result.setSystemId(file.toURI().toURL().toString());
			        return result;
			    }
			});
			
		} catch ( JAXBException | IOException vException ) {

	        GraphicsManagement.getLogger().error( "Error gernerating schema: " + vException.getLocalizedMessage() );
	        GraphicsManagement.getLogger().catching( Level.ERROR, vException );
	        
		}
		
	}
	
}

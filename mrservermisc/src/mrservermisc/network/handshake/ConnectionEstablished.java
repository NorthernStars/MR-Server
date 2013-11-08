package mrservermisc.network.handshake;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.logging.Loggers;

import org.apache.logging.log4j.Level;

@XmlRootElement(name="connectionestablished")
public class ConnectionEstablished {

	@Override
	public String toString() {
		return "ConnectionEstablished []";
	}

	public String toXMLString(){
		
		Loggers.getNetworkLogger().debug( "Trying to marshall object: " + toString() );
		StringWriter vXMLDataStream = new StringWriter();		
		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrservermisc.network.handshake.ConnectionEstablished.class );
		
	        Marshaller vMarshaller = vJAXBContext.createMarshaller();
	        vMarshaller.marshal( this, vXMLDataStream );
	        Loggers.getNetworkLogger().debug( "Marshalled object to " + vXMLDataStream );
			
		} catch ( JAXBException vJAXBException ) {

			Loggers.getNetworkLogger().error( "Error marshalling object: " + vJAXBException.getLocalizedMessage() );
			Loggers.getNetworkLogger().catching( Level.ERROR, vJAXBException );
	      
		}
		String vXMLDataString = vXMLDataStream.toString();
		
		return vXMLDataString;
		
	}
	
}

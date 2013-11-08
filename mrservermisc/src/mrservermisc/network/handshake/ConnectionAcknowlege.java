package mrservermisc.network.handshake;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import mrservermisc.logging.Loggers;

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
	
	public String toXMLString(){
		
		Loggers.getNetworkLogger().debug( "Trying to marshall object: " + toString() );
		StringWriter vXMLDataStream = new StringWriter();		
		JAXBContext vJAXBContext;
		try {
			vJAXBContext = JAXBContext.newInstance( mrservermisc.network.handshake.ConnectionAcknowlege.class );
		
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

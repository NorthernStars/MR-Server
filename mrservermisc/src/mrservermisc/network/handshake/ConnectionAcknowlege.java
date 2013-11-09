package mrservermisc.network.handshake;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.logging.Loggers;
import mrservermisc.network.xml.Helpers;

@XmlRootElement(name="connectionacknowlege")
public class ConnectionAcknowlege {

	@XmlElement(name="servername")
	String mMRServerName;
	@XmlElement(name="clientname")
	String mClientGraphicsName;
	@XmlElement(name="connectionallowed")
	boolean mConnectionAllowed;

	public ConnectionAcknowlege(){}

	public ConnectionAcknowlege(String aMRServerName, String aClientGraphicsName, boolean aConnectionAllowed) {
		
		mMRServerName = aMRServerName;
		mClientGraphicsName = aClientGraphicsName;
		mConnectionAllowed = aConnectionAllowed;
		
		Loggers.getNetworkLogger().debug( "Created " + toString() );
		
	}
	
	public String getMRServerName() {
		return mMRServerName;
	}
	public String getClientGraphicsName() {
		return mClientGraphicsName;
	}
	public boolean isConnectionAllowed() {
		return mConnectionAllowed;
	}
	
	@Override
	public String toString() {
		return "ConnectionAcknowlege [mMRServerName=" + mMRServerName
				+ ", mClientGraphicsName=" + mClientGraphicsName
				+ ", mConnectionAllowed=" + mConnectionAllowed + "]";
	}
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, ConnectionAcknowlege.class );
		
	}
	
	public static ConnectionAcknowlege unmarshallXMLConnectionAcknowlegeString( String aXMLConnectionAcknowlegePackage ){
			
		return Helpers.unmarshallXMLString( aXMLConnectionAcknowlegePackage, ConnectionAcknowlege.class );
		
	}
	
}

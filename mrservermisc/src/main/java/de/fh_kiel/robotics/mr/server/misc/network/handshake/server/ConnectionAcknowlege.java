package de.fh_kiel.robotics.mr.server.misc.network.handshake.server;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import de.fh_kiel.robotics.mr.server.misc.logging.Loggers;
import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

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

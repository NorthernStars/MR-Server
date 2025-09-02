package de.fh_kiel.robotics.mr.server.misc.network.handshake.server;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import de.fh_kiel.robotics.mr.server.misc.logging.Loggers;
import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

@XmlRootElement(name="connectionrequest")
public class ConnectionRequest {
	
	@XmlElement(name="clientname")
	String mClientName;

	public ConnectionRequest(){}
	
	public ConnectionRequest( String aClientName ){
		
		mClientName = aClientName;
		
		Loggers.getNetworkLogger().debug( "Created " + toString() );
		
	}
	
	public String getClientGraphicsName() {
		return mClientName;
	}

	@Override
	public String toString() {
		return "ConnectionRequest [mClientGraphicsName=" + mClientName
				+ "]";
	}
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, ConnectionRequest.class );
		
	}
	
	public static ConnectionRequest unmarshallXMLConnectionRequestString( String aXMLConnectionRequestPackage ){
			
		return Helpers.unmarshallXMLString( aXMLConnectionRequestPackage, ConnectionRequest.class );
		
	}
	
}

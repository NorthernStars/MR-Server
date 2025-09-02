package de.fh_kiel.robotics.mr.server.misc.network.handshake.server;

import jakarta.xml.bind.annotation.XmlRootElement;

import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

@XmlRootElement(name="connectionestablished")
public class ConnectionEstablished {

	@Override
	public String toString() {
		return "ConnectionEstablished []";
	}
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, ConnectionEstablished.class );
		
	}
	
	public static ConnectionEstablished unmarshallXMLConnectionEstablishedString( String aXMLConnectionEstablishedPackage ){
			
		return Helpers.unmarshallXMLString( aXMLConnectionEstablishedPackage, ConnectionEstablished.class );
		
	}
	
}

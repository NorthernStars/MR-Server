package de.fh_kiel.robotics.mr.server.misc.network.handshake.client.oneo;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import de.fh_kiel.robotics.mr.server.misc.network.handshake.client.ClientType;
import de.fh_kiel.robotics.mr.server.misc.network.handshake.client.MovementMode;
import de.fh_kiel.robotics.mr.server.misc.network.handshake.client.ProtocolVersion;
import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

@XmlRootElement(name="connect")
public class ConnectionServerData {

	@XmlElement(name="type")
	ClientType mClient;
	@XmlElement(name="protocol_version")
	ProtocolVersion mVersion;
	@XmlElement(name="movement_mode")
	MovementMode mMovementMode;
	
	public ConnectionServerData(){}
	
	public ConnectionServerData( ClientType aClient, ProtocolVersion aVersion, MovementMode aMovementMode ) {
		
		mClient = aClient;
		mVersion = aVersion;
		mMovementMode = aMovementMode;
		
	}

	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, ConnectionServerData.class );
		
	}
	
	public static ConnectionServerData unmarshallXMLConnectionServerDataString( String aXMLConnectionServerDataPackage ){
			
		return Helpers.unmarshallXMLString( aXMLConnectionServerDataPackage, ConnectionServerData.class );
		
	}
	
	@Override
	public String toString() {
		return "ConnectionServerData [mClient=" + mClient + ", mVersion="
				+ mVersion + ", mMovementMode=" + mMovementMode + "]";
	}
	
}

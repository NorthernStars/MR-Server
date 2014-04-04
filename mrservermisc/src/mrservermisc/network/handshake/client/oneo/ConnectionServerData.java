package mrservermisc.network.handshake.client.oneo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.network.handshake.client.ClientType;
import mrservermisc.network.handshake.client.MovementMode;
import mrservermisc.network.handshake.client.ProtocolVersion;
import mrservermisc.network.xml.Helpers;

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

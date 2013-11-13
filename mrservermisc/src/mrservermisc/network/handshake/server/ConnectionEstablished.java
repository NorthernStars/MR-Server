package mrservermisc.network.handshake.server;

import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.network.xml.Helpers;

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

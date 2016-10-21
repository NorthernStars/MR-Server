package mrservermisc.network.handshake.client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.network.xml.Helpers;

@XmlRootElement(name="connect")
public class ConnectionRequest{

	@XmlElement(name="type")
	ClientType mClient;
	@XmlElement(name="protocol_version")
	ProtocolVersion mVersion;
	@XmlElement(name="nickname")
	String mClientName;
	@XmlElement(name="rc_id")
	int mRcId;
	@XmlElement(name="vt_id")
	int mVtId;
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, ConnectionRequest.class );
		
	}
	
	public static ConnectionRequest unmarshallXMLConnectionRequestString( String aXMLConnectionRequestPackage ){
			
		return Helpers.unmarshallXMLString( aXMLConnectionRequestPackage, ConnectionRequest.class );
		
	}

	public ClientType getClient() {
		return mClient;
	}

	public ProtocolVersion getVersion() {
		return mVersion;
	}
	
	public String getClientName() {
		return mClientName;
	}

	public int getRcId() {
		return mRcId;
	}

	public int getVtId() {
		return mVtId;
	}
	

}

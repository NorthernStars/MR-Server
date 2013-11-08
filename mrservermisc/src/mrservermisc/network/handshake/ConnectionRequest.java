package mrservermisc.network.handshake;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="connectionrequest")
public class ConnectionRequest {
	
	@XmlElement(name="clientname")
	String mClientGraphicsName;

	public String getClientGraphicsName() {
		return mClientGraphicsName;
	}

	@Override
	public String toString() {
		return "ConnectionRequest [mClientGraphicsName=" + mClientGraphicsName
				+ "]";
	}
	
}

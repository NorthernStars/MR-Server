package de.fh_kiel.robotics.mr.server.misc.network.handshake.client.oneo;

import java.io.StringReader;

import jakarta.xml.bind.JAXB;
import jakarta.xml.bind.annotation.XmlElement;

public class ConnectionAcknowlege {

	@XmlElement(name="connect")
	boolean mAccepted;
	
	public ConnectionAcknowlege(){}

	public ConnectionAcknowlege( boolean aAccepted ) {
		
		mAccepted = aAccepted;
		
	}
	
	public String toXMLString(){
		
		return "<connect>" + mAccepted + "</connect>";
		
	}
	
	public static ConnectionAcknowlege unmarshallXMLConnectionRequestString( String aXMLConnectionAcknowlegePackage ){
			
		ConnectionAcknowlege vConnectionAcknowlege = new ConnectionAcknowlege();
		vConnectionAcknowlege.mAccepted = JAXB.unmarshal( new StringReader( aXMLConnectionAcknowlegePackage ), boolean.class );
		return vConnectionAcknowlege;
		
	}

	public boolean isAccepted() {
		
		return mAccepted;
		
	}

	@Override
	public String toString() {
		return "ConnectionAcknowlege [mAccepted=" + mAccepted + "]";
	}	
	
}

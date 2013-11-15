package mrscenariofootball.core.data.action;

import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.network.xml.Helpers;

@XmlRootElement(name="command")
public class Command {
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, Command.class );
		
	}

	public static Command unmarshallXMLPositionDataPackageString( String aXMLCommand ){
			
		return Helpers.unmarshallXMLString( aXMLCommand, Command.class );
		
	}
	
}

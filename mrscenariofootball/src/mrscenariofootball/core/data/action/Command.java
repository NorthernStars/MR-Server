package mrscenariofootball.core.data.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.network.xml.Helpers;

@XmlRootElement(name="command")
public class Command {
	
	@XmlElement(name="wheel_velocities")
	Movement mMovement;
	@XmlElement(name="kick")
	Kick mKick;
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, Command.class );
		
	}

	public static Command unmarshallXMLPositionDataPackageString( String aXMLCommand ){
			
		return Helpers.unmarshallXMLString( aXMLCommand, Command.class );
		
	}
	
	public boolean isMovement(){
		
		return mMovement != null;
		
	}
	
	public boolean isKick(){
		
		return mKick != null;
		
	}

	@Override
	public String toString() {
		return "Command [mMovement=" + mMovement + ", mKick=" + mKick + "]";
	}

	public Movement getMovement() {
		return mMovement;
	}

	public Kick getKick() {
		return mKick;
	}
	
}

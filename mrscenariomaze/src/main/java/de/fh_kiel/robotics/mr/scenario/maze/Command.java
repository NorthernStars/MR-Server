package de.fh_kiel.robotics.mr.scenario.maze;

import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="command")
public class Command {
	
	@XmlElement(name="wheel_velocities")
	Movement mMovement;
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, Command.class );
		
	}

	public static Command unmarshallXMLPositionDataPackageString( String aXMLCommand ){
			
		return Helpers.unmarshallXMLString( aXMLCommand, Command.class );
		
	}
	
	public boolean isMovement(){
		
		return mMovement != null;
		
	}

	public Movement getMovement() {
		mMovement.verify();
		return mMovement;
	}
	
}

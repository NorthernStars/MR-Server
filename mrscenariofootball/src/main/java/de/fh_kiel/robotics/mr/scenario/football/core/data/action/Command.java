package de.fh_kiel.robotics.mr.scenario.football.core.data.action;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

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
		mMovement.verify();
		return mMovement;
	}

	public Kick getKick() {
		mKick.verify();
		return mKick;
	}
	
}

package de.fh_kiel.robotics.mr.scenario.football.core.data.action;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

@XmlRootElement(name="kick")
public class Kick{

	@XmlElement(name="angle")
	private double mAngle;
	@XmlElement(name="force")
	private float mForce;
	
	public Kick() {}
	
	public Kick( double aAngle, float aForce){
		
		mAngle = aAngle;
		mForce = aForce;
		
	}
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, Kick.class );
		
	}

	public static Kick unmarshallXMLPositionDataPackageString( String aXMLKick ){
		
		Kick vKickStore = Helpers.unmarshallXMLString( aXMLKick, Kick.class );
		vKickStore.verify();
		
		return vKickStore;
		
	}

	public void verify() {
		
		if( mAngle > 180.0 ){
			
			mAngle = 180.0;
			
		} else if( mAngle < -180.0 ){
			
			mAngle = -180.0;
			
		}
		
		if( mForce > 1.0 ){
			
			mForce = 1.0f;
			
		} else if( mForce < 0.0 ){
			
			mForce = 0.0f;
			
		}
		
	}

	@Override
	public String toString() {
		return "Kick [mAngle=" + mAngle + ", mForce=" + mForce + "]";
	}

	public double getAngle() {
		return mAngle;
	}

	public float getForce() {
		return mForce;
	}
	
}

package de.fh_kiel.robotics.mr.scenario.football.core.data.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import mrservermisc.network.xml.Helpers;

@XmlRootElement(name="wheel_velocities")
public class Movement{

	public static final Movement NO_MOVEMENT = new Movement( 0, 0);

	@XmlElement(name="right")
	private int mRightWheelVelocity = 0;
	@XmlElement(name="left")
	private int mLeftWheelVelocity = 0;
	
	public Movement() {}
	
	public Movement( int aRightWheelVelocity, int aLeftWheelVelocity ) {
		
		mLeftWheelVelocity = aLeftWheelVelocity;
		mRightWheelVelocity = aRightWheelVelocity;

	}
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, Movement.class );
		
	}

	public static Movement unmarshallXMLPositionDataPackageString( String aXMLMovement ){
			
		Movement vMovmentStore = Helpers.unmarshallXMLString( aXMLMovement, Movement.class );
		vMovmentStore.verify();
			
		return vMovmentStore;
		
	}

	public void verify() {
		
		if( mLeftWheelVelocity > 100 ){
			
			mLeftWheelVelocity = 100;
			
		} else if( mLeftWheelVelocity < -100 ){
			
			mLeftWheelVelocity = -100;
			
		}
		
		if( mRightWheelVelocity > 100 ){
			
			mRightWheelVelocity = 100;
			
		} else if( mRightWheelVelocity < -100 ){
			
			mRightWheelVelocity = -100;
			
		}
		
	}

	@Override
	public String toString() {
		return "Movement [mRightWheelVelocity=" + mRightWheelVelocity
				+ ", mLeftWheelVelocity=" + mLeftWheelVelocity + "]";
	}

	public int getRightWheelVelocity() {
		return mRightWheelVelocity;
	}

	public int getLeftWheelVelocity() {
		return mLeftWheelVelocity;
	}
	
}

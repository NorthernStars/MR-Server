package mrscenariofootball.core.data.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.network.xml.Helpers;

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
			
		return Helpers.unmarshallXMLString( aXMLKick, Kick.class );
		
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

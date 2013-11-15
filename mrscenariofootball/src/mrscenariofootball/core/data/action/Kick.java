package mrscenariofootball.core.data.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.network.xml.Helpers;

@XmlRootElement(name="kick")
public class Kick extends Command{

	@XmlElement(name="angle")
	private double mAngle;
	@XmlElement(name="force")
	private float mForce;
	
	public Kick() {}
	
	public Kick( double aAngle, float aForce){
		
		mAngle = aAngle;
		mForce = aForce;
		
	}
	
	public String getXMLString() {
		return "<command> <kick> <angle>" + mAngle + "</angle> <force>" + mForce + "</force> </kick> </command>";
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
	
}

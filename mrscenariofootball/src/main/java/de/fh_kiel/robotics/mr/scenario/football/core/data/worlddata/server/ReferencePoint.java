package de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

public class ReferencePoint {
	
    @XmlElement(name="pointtype")
	ReferencePointName mPointName;
	@XmlElement(name="position")
    ServerPoint mPosition;
	
	public ReferencePoint() {}

	public ReferencePoint( ReferencePointName aPointname, ServerPoint aPoint ) {
		
		mPointName = aPointname;
		mPosition = aPoint; 
		
	}
	
	public ReferencePoint( ReferencePoint aReferencePoint ) {
		
		mPointName = aReferencePoint.getPointName();
		mPosition = new ServerPoint( aReferencePoint.getPosition() );
		
	}

	@Override
	public String toString() {
		return "ReferencePoint [mPointName=" + mPointName + ", mPosition="
				+ mPosition + "]";
	}

	public static Map<ReferencePointName, ReferencePoint> getDefaultMap( double aXMulti, double aYMulti ) {
		
		Map<ReferencePointName, ReferencePoint> vMapOfReferencePoints = new HashMap<ReferencePointName, ReferencePoint>();
		
		for( ReferencePointName vRefPoint : ReferencePointName.values() ){
			
			if( vRefPoint.getId() >= 0 ){
				
				vMapOfReferencePoints.put( vRefPoint, new ReferencePoint( vRefPoint, new ServerPoint( vRefPoint.getRelativePosition().getX() * aXMulti, vRefPoint.getRelativePosition().getY() * aYMulti ) ));
			
			}
			
		}
		
		return vMapOfReferencePoints;
	}

	public ReferencePointName getPointName() {
		return mPointName;
	}

	public ServerPoint getPosition() {
		return mPosition;
	}
	
}

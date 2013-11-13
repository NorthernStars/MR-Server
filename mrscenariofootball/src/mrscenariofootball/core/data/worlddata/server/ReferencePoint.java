package mrscenariofootball.core.data.worlddata.server;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public String toString() {
		return "ReferencePoint [mPointName=" + mPointName + ", mPosition="
				+ mPosition + "]";
	}

	public static List<ReferencePoint> getDefaultList( double aXMulti, double aYMulti ) {
		
		List<ReferencePoint> vListOfReferencePoints = new ArrayList<ReferencePoint>();
		
		for( ReferencePointName vRefPoint : ReferencePointName.values() ){
			
			if( vRefPoint.getId() >= 0 ){
				
				vListOfReferencePoints.add( new ReferencePoint( vRefPoint, new ServerPoint( vRefPoint.getRelativePosition().getX() * aXMulti, vRefPoint.getRelativePosition().getY() * aYMulti ) ));
			
			}
			
		}
		
		return vListOfReferencePoints;
	}
	
}

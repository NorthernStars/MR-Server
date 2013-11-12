package mrservermisc.network.data.worlddata;

import java.awt.geom.Point2D;

import javax.xml.bind.annotation.XmlElement;

public class ReferencePoint {
	
    @XmlElement(name="id")
	ReferencePointName mPointName;
	@XmlElement(name="position")
    Point2D.Double mPosition;
	
	@Override
	public String toString() {
		return "ReferencePoint [mPointName=" + mPointName + ", mPosition="
				+ mPosition + "]";
	}
	
}

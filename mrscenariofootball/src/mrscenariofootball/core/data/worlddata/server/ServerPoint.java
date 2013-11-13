package mrscenariofootball.core.data.worlddata.server;

import java.awt.geom.Point2D;

import javax.xml.bind.annotation.XmlElement;

public class ServerPoint extends Point2D {
	
	@XmlElement(name="x")
	double mX;
	@XmlElement(name="y")
	double mY;
	
	public ServerPoint() {}
	
	public ServerPoint( double aX, double aY ) {
		
		mX = aX;
		mY = aY;

	}
	
	@Override
	public double getX() {
		return mX;
	}

	@Override
	public double getY() {
		return mY;
	}

	@Override
	public void setLocation( double aX, double aY ) {
		
		mX = aX;
		mY = aY;

	}

	@Override
	public String toString() {
		return "ServerPoint [mX=" + mX + ", mY=" + mY + "]";
	}

}

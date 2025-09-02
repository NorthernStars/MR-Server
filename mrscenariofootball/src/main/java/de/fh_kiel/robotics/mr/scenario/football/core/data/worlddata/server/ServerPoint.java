package de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server;

import java.awt.geom.Point2D;

import jakarta.xml.bind.annotation.XmlElement;

import de.fh_kiel.robotics.mr.scenario.football.core.data.ScenarioInformation;

public class ServerPoint extends Point2D {
	
	@XmlElement(name="x")
	protected double mX;
	@XmlElement(name="y")
	protected double mY;
	
	public ServerPoint() {}
	
	public ServerPoint( double aX, double aY ) {
		
		mX = aX;
		mY = aY;

	}

	public ServerPoint( ServerPoint aServerPoint ) {
		
		mX = aServerPoint.getX();
		mY = aServerPoint.getY();		
		
	}

	@Override
	public double getX() {
		return mX;
	}

	@Override
	public double getY() {
		return mY;
	}

	public ServerPoint add( ServerPoint aServerPoint ) {
		
		mX += aServerPoint.getX();
		mY += aServerPoint.getY();
		
		return this;
	}

	public ServerPoint add( double aX, double aY ) {
		
		mX += aX;
		mY += aY;
		
		return this;
	}

	public ServerPoint multiply( double aFactor ) {
		
		mX *= aFactor;
		mY *= aFactor;
		
		return this;
	}

	public ServerPoint divide( double aDivisor ) {
		
		mX /= aDivisor;
		mY /= aDivisor;
		
		return this;
	}
	
	public double getDegreeOfVector(){
		
		return  Math.toDegrees( Math.atan2( mY, mX ) );
		
	}
	
	public double getLengthOfVector(){
		
		return  Math.sqrt( mX * mX + mY * mY );
		
	}
	
	public void setVectorLengthTo( double aLength ){
		
		double vCurrentLength = getLengthOfVector();
		
		mX *= (aLength/vCurrentLength);
		mY *= (aLength/vCurrentLength);
		
	}

	@Override
	public void setLocation( double aX, double aY ) {
		
		if( aX > 1.0 * ScenarioInformation.getInstance().getXFactor() ){
			mX = 1.0 * ScenarioInformation.getInstance().getXFactor();
		} else if( aX < 0.0 ){
			mX = 0.0;
		} else {
			mX = aX;
		}
		
		if( aY > 1.0 * ScenarioInformation.getInstance().getYFactor() ){
			mY = 1.0 * ScenarioInformation.getInstance().getYFactor();
		} else if( aY < 0.0 ){
			mY = 0.0;
		} else {
			mY = aY;
		}

	}

	@Override
	public String toString() {
		return "ServerPoint [mX=" + mX + ", mY=" + mY + "]";
	}

}

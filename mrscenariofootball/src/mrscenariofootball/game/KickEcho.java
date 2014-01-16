package mrscenariofootball.game;

import mrscenariofootball.core.data.action.Kick;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;

public class KickEcho extends ServerPoint {
	
	private int mLifetime;
	
	public KickEcho( Kick aKick, double aPlayerAngle ){
	
		double vKickAngle;
		
		vKickAngle = aKick.getAngle() + aPlayerAngle;
		vKickAngle = vKickAngle > 180.0 ? vKickAngle - 360.0 : vKickAngle;
		vKickAngle = vKickAngle < -180.0 ? vKickAngle + 360.0 : vKickAngle;
		
		mX = aKick.getForce() * Math.cos( Math.toRadians( vKickAngle ) ) * 0.2; //TODO: Magic number
		mY = aKick.getForce() * Math.sin( Math.toRadians( vKickAngle ) ) * 0.2;
		Core.getLogger().trace(" Kick with Force {}|{} ({})", mX, mY, aKick );
		
		mLifetime = 25;
		Core.getLogger().trace( "Lifetime of Kick = {}", mLifetime);
		
	}
	
	@Override
	public double getX() {
		
		return super.getX() * 0.05;
		
	}
	
	@Override
	public double getY() {
		
		return super.getY() * 0.05;
		
	}
	
	public boolean isAlive(){
		
		return mLifetime > 0;
		
	}
	
	public void reduceLife(){
		
		double vFactor = -Math.pow(( (double) ( 25 - mLifetime ) ) / 25.0, 2) + 1;
		
		mLifetime--;
		mY *= vFactor;
		mX *= vFactor;
		Core.getLogger().trace( "Lifetime of Kick = {}({}/{})", mLifetime, mX, mY );
		
	}

}

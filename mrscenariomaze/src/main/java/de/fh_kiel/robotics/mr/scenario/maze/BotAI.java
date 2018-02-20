package de.fh_kiel.robotics.mr.scenario.maze;

import de.fh_kiel.robotics.mr.server.misc.bots.interfaces.Bot;

public class BotAI {
	
	Bot mRemoteAI;
	
	double positionX = 0;

	public double positionY = 0;
	public double orientation = 0;
	
	public BotAI( Bot aRemoteAI ) {

		mRemoteAI = aRemoteAI;
		
	}
	
	public void sendPosition( Position aPosition ){
		
		if( aPosition != null ){
			
			mRemoteAI.sendWorldStatus( aPosition.toXMLString() );
			
		} else {
			
			// sendWorldStatus( new ClientWorldData( aWorldData, this ) );
			
		}
		
	}

	public String getLastAction() {
		
		return mRemoteAI.getLastAction();
		
	}

	public int getTeam() {
		
		return mRemoteAI.getTeam();
		
	}

	public String getName() {
		
		return mRemoteAI.getName();
		
	}

	public int getRcId() {

		return mRemoteAI.getRcId();
		
	}

	public int getVtId() {

		return mRemoteAI.getVtId();
		
	}

	public Bot getRemoteAI() {
		return mRemoteAI;
	}

}

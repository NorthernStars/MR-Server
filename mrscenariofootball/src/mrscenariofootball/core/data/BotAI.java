package mrscenariofootball.core.data;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.worlddata.client.ClientWorldData;
import mrscenariofootball.core.data.worlddata.server.WorldData;
import mrservermisc.bots.interfaces.Bot;

public class BotAI {
	
	Bot mRemoteAI;
	
	public BotAI( Bot aRemoteAI ) {

		mRemoteAI = aRemoteAI;
		
	}
	
	private ClientWorldData mWorldDataToSend = null;
	
	public void setWorldDataToSend( ClientWorldData aWorldData ){
		
		mWorldDataToSend = aWorldData;
		
	}
	
	public void sendWorldData( WorldData aWorldData ){
		
		if( mWorldDataToSend != null ){
			
			sendWorldStatus( mWorldDataToSend );
			mWorldDataToSend = null;
			
		} else {
			
			sendWorldStatus( new ClientWorldData( aWorldData, this ) );
			
		}
		
	}
	
	private void sendWorldStatus( ClientWorldData aWorldData ) {
		
		mRemoteAI.sendWorldStatus( aWorldData.toXMLString() );
		
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

	@Override
	public String toString() {
		return "BotAI [mRemoteAI=" + mRemoteAI + ", mWorldDataToSend="
				+ mWorldDataToSend + "]";
	}

}

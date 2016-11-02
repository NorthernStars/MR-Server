package de.fh_kiel.robotics.mr.server.core.botai.network;

import java.util.ArrayList;
import java.util.List;

import de.fh_kiel.robotics.mr.server.core.botai.BotAIManagement;
import de.fh_kiel.robotics.mr.server.misc.network.BasicUDPHostConnection;

public class BotAIConnections {
	
	private List<BotAiHost> mListOfHosts = new ArrayList<BotAiHost>();
	
	public BotAIConnections() { }
	
	public BotAIConnections( List<Integer> aBotPorts ){
		
		for( int vBotAIPort : aBotPorts ){
			
			mListOfHosts.add( new BotAiHost( vBotAIPort, mListOfHosts.size() + 1 ));
			
		}
				
	}
	
	public BotAiHost addBotAIPort( int aPort ){
		
		BotAiHost vHost = new BotAiHost( aPort, mListOfHosts.size() + 1 );
		if( vHost.isConnected() ){
			mListOfHosts.add( vHost );
			return vHost;
		}
		return null;
		
	}
	
	public List<BotAiHost> getListOfHosts(){
		
		return mListOfHosts;
		
	}

	public void closeConnection() {

		for( BasicUDPHostConnection vBotAIHost : mListOfHosts ){
    		
    		vBotAIHost.closeConnection();
    		BotAIManagement.getLogger().debug( "Stopped host for " + vBotAIHost.toString() );
    		
    	}
		
	}
	
}

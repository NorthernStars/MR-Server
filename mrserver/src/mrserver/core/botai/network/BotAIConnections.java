package mrserver.core.botai.network;

import java.util.ArrayList;
import java.util.List;

import mrserver.core.Core;
import mrserver.core.botai.BotAIManagement;
import mrserver.core.botai.network.receive.Receiver;
import mrservermisc.network.BasicUDPHostConnection;

public class BotAIConnections {
	
	private List<BasicUDPHostConnection> mListOfHosts = new ArrayList<BasicUDPHostConnection>();
	
	public BotAIConnections(){
		
		for( int vBotAIPort : Core.getInstance().getServerConfig().getBotPorts() ){
			
			mListOfHosts.add( new BasicUDPHostConnection( vBotAIPort ));
			
		}
				
	}
	
	public List<BasicUDPHostConnection> getListOfHosts(){
		
		return mListOfHosts;
		
	}

	public void closeConnection() {

		for( BasicUDPHostConnection vBotAIHost : mListOfHosts ){
    		
    		vBotAIHost.closeConnection();
    		BotAIManagement.getLogger().debug( "Stopped host for " + vBotAIHost.toString() );
    		
    	}
		
	}
	
}

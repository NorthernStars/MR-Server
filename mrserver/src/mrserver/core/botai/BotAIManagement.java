package mrserver.core.botai;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import mrserver.core.botai.data.BotAI;
import mrserver.core.botai.network.BotAIConnections;
import mrserver.core.botai.network.BotAiHost;
import mrserver.core.botai.network.receive.Creator;
import mrserver.core.botai.network.receive.Receiver;
import mrservermisc.network.BasicUDPHostConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotAIManagement {
	
    private static BotAIManagement INSTANCE;

    private BotAIManagement(){
    	
    }
    
    public static BotAIManagement getInstance() {
        
        if( BotAIManagement.INSTANCE == null){
        	BotAIManagement.getLogger().debug( "Creating BotAIManagement-instance." );
        	BotAIManagement.INSTANCE = new BotAIManagement();
        }

        BotAIManagement.getLogger().trace( "Retrieving BotAIManagement-instance." );
        return BotAIManagement.INSTANCE;
        
    }
    
    private static Logger BOTAIMANAGEMENTLOGGER = LogManager.getLogger("BOTAIMANAGEMENT");
    
    public static Logger getLogger(){
        
        return BOTAIMANAGEMENTLOGGER;
        
    }

    private ConcurrentHashMap<SocketAddress, BotAI> mMapOfConnectedBotAIs;
    private BotAIConnections mBotAIsConnections;
    private List<Receiver> mBotAIReceiver = new ArrayList<Receiver>();
    private Creator mBotAICreator;
    
    public void startBotAIManagement(){
    	
    	BotAIManagement.getLogger().info( "Starting botaimanagement" );
    	
    	BotAIManagement.getLogger().debug( "Creating botaiconnections." );
    	mBotAIsConnections = new BotAIConnections();
    	
    	BotAIManagement.getLogger().debug( "Creating mapofconnectedbotais." );
    	mMapOfConnectedBotAIs = new ConcurrentHashMap<SocketAddress, BotAI>();

    	BotAIManagement.getLogger().debug( "Starting receiver." );
    	for( BotAiHost vBotAIHost : mBotAIsConnections.getListOfHosts() ){
    		
    		mBotAIReceiver.add( new Receiver( vBotAIHost ));
    		
    	}
    	for( Receiver vBotAIReceiver : mBotAIReceiver ){
    		
    		vBotAIReceiver.startManagement();
    		BotAIManagement.getLogger().debug( "Started receiver for " + vBotAIReceiver.toString() );
    		
    	}
    	
    	BotAIManagement.getLogger().debug( "Starting creator." );
    	mBotAICreator= new Creator();
    	mBotAICreator.startManagement();
    	
    }
    
    public void close(){
    	
    	if( mBotAIReceiver != null ){

        	for( Receiver vBotAIReceiver : mBotAIReceiver ){
        		
        		vBotAIReceiver.stopManagement();
        		BotAIManagement.getLogger().debug( "Stopped receiver for " + vBotAIReceiver.toString() );
        		
        	}
    	}
    	if( mBotAICreator != null ) {
    		mBotAICreator.stopManagement();
    	}
    	if( mBotAIsConnections != null ) {
    		mBotAIsConnections.closeConnection();
    	}
    	BotAIManagement.getLogger().info( "Botaimanagement closed" );
    	
    }
    
    public ConcurrentHashMap<SocketAddress, BotAI> getMapOfBotAIs(){
    	
    	BotAIManagement.getLogger().trace( "Retrieving mapofconnectedbotais" );
    	return mMapOfConnectedBotAIs;
    	
    }

}

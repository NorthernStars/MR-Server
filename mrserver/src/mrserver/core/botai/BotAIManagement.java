package mrserver.core.botai;

import mrserver.core.scenario.ScenarioManagement;

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

        ScenarioManagement.getLogger().trace( "Retrieving BotAIManagement-instance." );
        return BotAIManagement.INSTANCE;
        
    }
    
    private static Logger BOTAIMANAGEMENTLOGGER = LogManager.getLogger("BOTAIMANAGEMENT");
    
    public static Logger getLogger(){
        
        return BOTAIMANAGEMENTLOGGER;
        
    }

}

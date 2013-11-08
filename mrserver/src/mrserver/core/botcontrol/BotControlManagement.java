package mrserver.core.botcontrol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrserver.core.Core;
import mrservermisc.botcontrol.interfaces.BotControl;

/**
 * Managed die Verbindung und den Datenaustausch zum Visionmodul
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 */
public class BotControlManagement implements BotControl{
  
    private static BotControlManagement INSTANCE;
    
    private BotControlManagement(){
    	
    }

    public static BotControlManagement getInstance() {
        
        if( BotControlManagement.INSTANCE == null){
        	BotControlManagement.getLogger().debug( "Creating BotControlManagement-instance." );
        	BotControlManagement.INSTANCE = new BotControlManagement();
        }

        BotControlManagement.getLogger().trace( "Retrieving BotControlManagement-instance." );
        return BotControlManagement.INSTANCE;
        
    }
    
    private static Logger BOTCONTROLMANAGEMENTLOGGER = LogManager.getLogger("BOTCONTROLMANAGEMENT");
    
    public static Logger getLogger(){
        
        return BOTCONTROLMANAGEMENTLOGGER;
        
    }
    
    public void close() {

        if( INSTANCE != null ){
        	
            INSTANCE = null;
            
        }
        
    }

	@Override
	public boolean sendMovement(String aMovement) {
		// TODO Auto-generated method stub
		return false;
	}

}

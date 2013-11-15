package mrserver.core.botcontrol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrserver.core.Core;
import mrservermisc.botcontrol.interfaces.BotControl;
import mrservermisc.network.BasicUDPServerConnection;

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
    
    BasicUDPServerConnection aToBotControl;
    
    public void startManagement(){
    	
    	aToBotControl = new BasicUDPServerConnection( Core.getInstance().getServerConfig().getBotControlIPAdress(), Core.getInstance().getServerConfig().getBotControlPort() );
    	BotControlManagement.getLogger().info( "Botcontrolmanagement started");
    }
    
    public void close() {

        if( INSTANCE != null ){
        	
            INSTANCE = null;
        	BotControlManagement.getLogger().info( "Botcontrolmanagement closed");
            
        }
        
    }

	@Override
	public boolean sendMovement(int aBot, int aLeftWheelSpeed, int aRightWheelSpeed) {
		
		if( aToBotControl != null && aToBotControl.isConnected() ){
			
			BotControlManagement.getLogger().debug( "Sending command {}|{}|{} to botcontrol", aBot, aLeftWheelSpeed, aRightWheelSpeed );
			aToBotControl.sendDatagrammString( aBot + "|" + aLeftWheelSpeed + "|" + aRightWheelSpeed );
			
			return true;
			
		} else {
			
			BotControlManagement.getLogger().debug( "Trying to send command to unconnected botcontrol" );
			
		}
		
		return false;
	}

}

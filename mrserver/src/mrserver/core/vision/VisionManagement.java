package mrserver.core.vision;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrserver.core.Core;
import mrservermisc.vision.data.PositionData;
import mrservermisc.vision.interfaces.Vision;

/**
 * Managed die Verbindung und den Datenaustausch zum Visionmodul
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 */
public class VisionManagement implements Vision{
  
    private static VisionManagement INSTANCE;
    
    private VisionManagement(){
    	
    }

    public static VisionManagement getInstance() {
        
        if( VisionManagement.INSTANCE == null){
        	VisionManagement.getLogger().debug( "Creating VisionManagement-instance." );
        	VisionManagement.INSTANCE = new VisionManagement();
        }

        Core.getLogger().trace( "Retrieving VisionManagement-instance." );
        return VisionManagement.INSTANCE;
        
    }
    
    private static Logger VISIONMANAGEMENTLOGGER = LogManager.getLogger("VISIONMANAGEMENT");
    
    public static Logger getLogger(){
        
        return VISIONMANAGEMENTLOGGER;
        
    }
    
    public void close() {

        if( INSTANCE != null ){
        	
            INSTANCE = null;
            
        }
        
    }
    
	@Override
	public PositionData getPositionData() {
		// TODO Auto-generated method stub
		return null;
	}

}

package mrserver.core.vision;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrserver.core.Core;
import mrserver.core.vision.network.VisionConnection;
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
	private VisionConnection mVisionConnect;
    
    public static Logger getLogger(){
        
        return VISIONMANAGEMENTLOGGER;
        
    }
    
    public void close() {

        if( INSTANCE != null ){
        	
        	if( mVisionConnect != null ){

            	mVisionConnect.closeConnection();
            	mVisionConnect = null;
        		
        	}
        	
            INSTANCE = null;
            
        }
        
    }
    
    /**
     * Verbindet den Server zu einem Visionmodul 
     * 
     * @return true ob die Verbindung erfolgreich hergestellt werden konnte, fale wenn nicht
     */
    public boolean connectToVision(){
    	
    	try {
    		
    		mVisionConnect = new VisionConnection();
    		
    		return mVisionConnect.establishConnection();
        
    	} catch ( Exception vException ) {

	        VisionManagement.getLogger().error( "Fehler beim initialisiern der visionconnection: " + vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
    	}
    	
    	return false;
    	
    }
    
    /**
     * Ändert den Modus des verbundenen Visionmodul 
     * 
     * @return true ob der Modus erfolgreich geändert werden konnte
     */
    public boolean changeVisionMode( VisionMode aVisionMode ){
    	
    	return mVisionConnect.setMode( aVisionMode );
    	
    }
    
	@Override
	public PositionData getPositionData() {
		// TODO Auto-generated method stub
		return null;
	}

}

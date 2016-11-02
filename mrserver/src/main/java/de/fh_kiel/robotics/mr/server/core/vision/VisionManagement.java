package de.fh_kiel.robotics.mr.server.core.vision;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fh_kiel.robotics.mr.server.core.Core;
import de.fh_kiel.robotics.mr.server.core.vision.network.VisionConnection;
import mrservermisc.network.data.position.VisionMode;
import mrservermisc.network.data.visionmode.ChangeVisionMode;

/**
 * Managed die Verbindung und den Datenaustausch zum Visionmodul
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 */
public class VisionManagement{
  
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
    
	private VisionConnection mVisionConnect;
	private VisionIncomingPacketsManagement mIncomingPacketManagement;
    
    public void close() {

        if( INSTANCE != null ){

        	disconnectVision();
        	
            INSTANCE = null;
            
        }
    	VisionManagement.getLogger().info( "Visionmanagement closed" );
        
    }
    
    public void disconnectVision(){
    	
    	if( mIncomingPacketManagement != null ){

    		mIncomingPacketManagement.stopManagement();
    		mIncomingPacketManagement = null;
    		
    	}
    	if( mVisionConnect != null ){

        	mVisionConnect.closeConnection();
        	mVisionConnect = null;
    		
    	}
    	
    }
    
    /**
     * Verbindet den Server zu einem Visionmodul 
     * 
     * @return true ob die Verbindung erfolgreich hergestellt werden konnte, fale wenn nicht
     */
    public boolean connectToVision( int aHostPort ){
    	
    	try {
    		if( aHostPort > 1024 ){
    			mVisionConnect = new VisionConnection( aHostPort );
    		} else {
    			mVisionConnect = new VisionConnection();
    		}
    		
    		if( mVisionConnect.establishConnection() ){
    			
    			VisionManagement.getLogger().info( "Connection to vision established!" );
    			mIncomingPacketManagement = new VisionIncomingPacketsManagement( mVisionConnect );
    			return mVisionConnect.isConnected();
    			
    		}
        
    	} catch ( Exception vException ) {

	        VisionManagement.getLogger().error( "Fehler beim initialisiern der visionconnection: " + vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
    	}
    	
    	return false;
    	
    }

	public boolean reconnectToVision( int aHostPort ) {

		try {
    		if( aHostPort > 1024 ){
    			mVisionConnect = new VisionConnection( aHostPort );
    		
	
				mVisionConnect.setReconnected();
	    		
	    		if( mVisionConnect.isConnected() ){
	    			
	    			VisionManagement.getLogger().info( "Reconnection to vision established!" );
	    			mIncomingPacketManagement = new VisionIncomingPacketsManagement( mVisionConnect );
	    			return mVisionConnect.isConnected();
	    			
	    		}
    		
    		}
        
    	} catch ( Exception vException ) {

	        VisionManagement.getLogger().error( "Fehler beim initialisiern der visionconnection: " + vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
    	}
    	
    	return false;
		
	}
    
    public boolean connectToVision(){
    
    	return connectToVision( -1 );
    
    }
    
    public boolean startRecievingPackets(){
    	
    	if( mIncomingPacketManagement != null ){
    		return mIncomingPacketManagement.startManagement();
    	}
    	return false;
    }
    
    /**
     * Ändert den Modus des verbundenen Visionmodul 
     * 
     * @return true ob der Modus erfolgreich geändert werden konnte
     */
    public boolean changeVisionMode( VisionMode aVisionMode ){
    	
    	if( isConnected() ){
    		
			try {
			
				VisionManagement.getLogger().info( "Setting visionmode: " + aVisionMode );
				
				mIncomingPacketManagement.suspendManagement();
				
				ChangeVisionMode vNewMode = new ChangeVisionMode( aVisionMode );
				VisionManagement.getLogger().debug( "Sending visionmodechange {}", vNewMode.toXMLString() );
				
				mVisionConnect.sendDatagrammString( vNewMode.toXMLString() );
				
				ChangeVisionMode vChangeAcknowlegement = ChangeVisionMode.unmarshallXMLChangeVisionModeString( mVisionConnect.getDatagrammString( 1000 ) );
				
				//TODO: in ein datapacket ändern
				VisionManagement.getLogger().debug( "Recieving visionmode acknowlegement: " + vChangeAcknowlegement );
				
				if( vChangeAcknowlegement != null && aVisionMode == vChangeAcknowlegement.getVisionMode() ){
					
					mIncomingPacketManagement.resumeManagement();
					VisionManagement.getLogger().info( "Set visionmode to " + aVisionMode );
					return true;
					
				}
		
			} catch ( Exception vException ) {
		
		        VisionManagement.getLogger().error( "Could not get confirmation from vision: " + vException.getLocalizedMessage() );
		        VisionManagement.getLogger().catching( Level.ERROR, vException );
		        
			}

			mIncomingPacketManagement.resumeManagement();
			
    	}
    	
		VisionManagement.getLogger().info( "Failed to set visionmode to " + aVisionMode );
		return false;
    	
    }
    
    public boolean isConnected(){
    	
    	return mVisionConnect != null && mVisionConnect.isConnected() && mIncomingPacketManagement != null && mIncomingPacketManagement.isAlive();
    	
    }
    
    public int getPortToVision(){
    	
    	return isConnected() ? mVisionConnect.getPortToVision() : -1;
    	
    }

}

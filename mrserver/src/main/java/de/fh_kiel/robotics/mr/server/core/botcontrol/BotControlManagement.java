package de.fh_kiel.robotics.mr.server.core.botcontrol;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fh_kiel.robotics.mr.server.core.botcontrol.network.BotControlConnection;
import de.fh_kiel.robotics.mr.server.core.vision.VisionManagement;
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
    
    BotControlConnection mToBotControl;
    
    public void startManagement(){
    	
    	BotControlManagement.getLogger().info( "Botcontrolmanagement started");
    }    
    
    public boolean connectToBotControl( int aHostPort ){
    	
    	try {
    		if( aHostPort > 1024 ){
    			mToBotControl = new BotControlConnection( aHostPort );
    		} else {
    			mToBotControl = new BotControlConnection();
    		}
    		
    		if( mToBotControl.establishConnection() ){
    			
    			VisionManagement.getLogger().info( "Connection to botcontrol established!" );
    			return mToBotControl.isConnected();
    			
    		}
        
    	} catch ( Exception vException ) {

	        VisionManagement.getLogger().error( "Fehler beim initialisiern der botcontrolconnection: {}", vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
    	}
    	
    	VisionManagement.getLogger().info( "Counld not connect to botcontrol!" );
    	return false;
    	
    }

	public boolean reconnectToBotControl( int aHostPort ) {
		
		try {
    		if( aHostPort > 1024 ){
    			
    			mToBotControl = new BotControlConnection( aHostPort );
    			mToBotControl.setReconnected();
    		
	    		if( mToBotControl.isConnected() ){
	    			
	    			VisionManagement.getLogger().info( "Reconnection to botcontrol established!" );
	    			return mToBotControl.isConnected();
	    			
	    		}
	    		
    		}
        
    	} catch ( Exception vException ) {

	        VisionManagement.getLogger().error( "Fehler beim initialisiern der botcontrolconnection: {}", vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
    	}

    	VisionManagement.getLogger().info( "Counld not reconnect to botcontrol!" );
    	return false;
	}

    
    public boolean connectToBotControl(){
    
    	return connectToBotControl( -1 );
    
    }
    
    public void close() {

        if( INSTANCE != null ){
        	
        	disconnectBotControl();
        	
            INSTANCE = null;
        	BotControlManagement.getLogger().info( "Botcontrolmanagement closed" );
            
        }
        
    }
    
    public void disconnectBotControl(){
    	
    	if( mToBotControl != null ){

    		mToBotControl.closeConnection();
    		mToBotControl = null;
    		
    	}
    	
    }

	@Override
	public boolean sendMovement(int aBot, int aLeftWheelSpeed, int aRightWheelSpeed) {
		
		if( mToBotControl != null && mToBotControl.isConnected() ){
			
			BotControlManagement.getLogger().debug( "Sending command {}|{}|{} to botcontrol", aBot, aLeftWheelSpeed, aRightWheelSpeed );
			
			String vMovmentCommand = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
					+ "<commandPackage>"
					+ "<commandProtocolRevision>2</commandProtocolRevision>"
					+ "<sections>"
					+ "<botID>" + aBot + "</botID>"
							+ "<commandList>"
							+ "<command>MOTOR_LEFT</command>"
							+ "<value>" + aLeftWheelSpeed + "</value>"
							+ "</commandList>"
							+ "<commandList>"
							+ "<command>MOTOR_RIGHT</command>"
							+ "<value>" + aRightWheelSpeed + "</value>"
							+ "</commandList>"
					+ "</sections>"
					+ "</commandPackage>";
			
			
			mToBotControl.sendDatagrammString( vMovmentCommand );
			
			return true;
			
		} else {
			
			BotControlManagement.getLogger().debug( "Trying to send command to unconnected botcontrol" );
			
		}
		
		return false;
	}

	public int getPortToBotControl() {
		
		return isConnected() ? mToBotControl.getPortToVision() : -1;
		
	}
	
    
    public boolean isConnected(){
    	
    	return mToBotControl != null && mToBotControl.isConnected();
    	
    }
}

package mrserver.core.vision.network;

import java.io.IOException;

import org.apache.logging.log4j.Level;

import mrserver.core.Core;
import mrserver.core.vision.VisionManagement;
import mrservermisc.network.BasicUDPServerConnection;
import mrservermisc.network.data.position.VisionMode;
import mrservermisc.network.handshake.ConnectionRequest;

public class VisionConnection extends BasicUDPServerConnection{
	
	public VisionConnection() throws IOException{
		
		super( Core.getInstance().getServerConfig().getVisionIPAdress(), Core.getInstance().getServerConfig().getVisionPort() );
				
	}
	
	public boolean establishConnection(){
		
		try {
		
			VisionManagement.getLogger().info( "Establishing connection: " + toString() );
			
			ConnectionRequest vRequestToVision = new ConnectionRequest( Core.getInstance().getServerConfig().getServerName() );
			VisionManagement.getLogger().debug( "Sending handshake: " + vRequestToVision.toString() );
			sendDatagrammString( vRequestToVision.toXMLString() );
			
			String vVisionAcknowledge = getDatagrammString( 1000 );
			
			VisionManagement.getLogger().debug( "Recieving Acknowledge: " + vVisionAcknowledge );
			
			if( vVisionAcknowledge.equals( Core.getInstance().getServerConfig().getServerName() + "mrVision" ) ){
				
				VisionManagement.getLogger().debug( "Sending Acknowledge: ACK" );
				sendDatagrammString( "ACK" );
				return true;
				
			}
	
		} catch ( Exception vException ) {
	
	        VisionManagement.getLogger().error( "Could not establish connection to vision: " + vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
		}
		
		return false;
		
	}
	
	public boolean setMode( VisionMode aVisionMode){
		
		try {
		
			VisionManagement.getLogger().info( "Setting visionmode: " + aVisionMode );
			
			VisionManagement.getLogger().debug( "Sending visionmode: " + aVisionMode );
			sendDatagrammString( aVisionMode.toString() );
			
			String vSetVisionModeAcknowledge = getDatagrammString( 1000 );
			
			//TODO: in ein datapacket ändern
			VisionManagement.getLogger().debug( "Recieving visionmode acknowlege: " + vSetVisionModeAcknowledge );
			
			if( vSetVisionModeAcknowledge.equals( Core.getInstance().getServerConfig().getServerName() + aVisionMode ) ){
				
				return true;
				
			}
	
		} catch ( Exception vException ) {
	
	        VisionManagement.getLogger().error( "Could not establish connection to vision: " + vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
		}
		
		return false;
		
	}

}

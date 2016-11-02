package de.fh_kiel.robotics.mr.server.core.vision.network;

import java.io.IOException;

import org.apache.logging.log4j.Level;

import de.fh_kiel.robotics.mr.server.core.Core;
import de.fh_kiel.robotics.mr.server.core.vision.VisionManagement;
import mrservermisc.network.BasicUDPServerConnection;
import mrservermisc.network.handshake.server.ConnectionAcknowlege;
import mrservermisc.network.handshake.server.ConnectionEstablished;
import mrservermisc.network.handshake.server.ConnectionRequest;

public class VisionConnection extends BasicUDPServerConnection{
	
	private boolean mIsConnectionEstablished = false;

	public VisionConnection() throws IOException{
		
		super( Core.getInstance().getServerConfig().getVisionIPAdress(), Core.getInstance().getServerConfig().getVisionPort() );
				
	}
	
	public VisionConnection( int aHostPort ) throws IOException{
		
		super( Core.getInstance().getServerConfig().getVisionIPAdress(), Core.getInstance().getServerConfig().getVisionPort(), aHostPort );
				
	}
	
	public boolean establishConnection(){
		
		try {
		
			VisionManagement.getLogger().info( "Establishing connection: " + toString() );
			
			ConnectionRequest vRequestToVision = new ConnectionRequest( Core.getInstance().getServerConfig().getServerName() );
			VisionManagement.getLogger().debug( "Sending handshake: " + vRequestToVision.toString() );
			sendDatagrammString( vRequestToVision.toXMLString() );
			
			ConnectionAcknowlege vVisionAcknowledge = ConnectionAcknowlege.unmarshallXMLConnectionAcknowlegeString( getDatagrammString( 1000 ) );
			
			VisionManagement.getLogger().debug( "Recieving Acknowledge: " + vVisionAcknowledge );
			
			if( vVisionAcknowledge != null && vVisionAcknowledge.isConnectionAllowed() ){
				
				ConnectionEstablished vVisionConnectionEstablished = new ConnectionEstablished();
				VisionManagement.getLogger().debug( "Sending Acknowledge: " + vVisionConnectionEstablished );
				sendDatagrammString( vVisionConnectionEstablished.toXMLString() );
				
				mIsConnectionEstablished = true;
				return mIsConnectionEstablished;
				
			}
	
		} catch ( Exception vException ) {
	
	        VisionManagement.getLogger().error( "Could not establish connection to vision: " + vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
		}

		VisionManagement.getLogger().debug( "Could not establish connection" );
		return false;
		
	}
	
	public void setReconnected(){
		
		mIsConnectionEstablished = true;
		
	}
	
	@Override
	public boolean isConnected() {
		
		return super.isConnected() && mIsConnectionEstablished;
		
	}
	
	public int getPortToVision(){
		
		return mToTargetSocket.getLocalPort();
		
	}

}

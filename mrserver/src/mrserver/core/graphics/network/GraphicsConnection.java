package mrserver.core.graphics.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.channels.DatagramChannel;

import mrserver.core.Core;
import mrserver.core.vision.VisionManagement;
import mrserver.core.vision.VisionMode;
import mrservermisc.network.BasicUDPHostConnection;
import mrservermisc.network.BasicUDPServerConnection;

import org.apache.logging.log4j.Level;

public class GraphicsConnection extends BasicUDPHostConnection{
	
	public GraphicsConnection() throws IOException{
		
		super( Core.getInstance().getServerConfig().getGraphicsPort() );
				
	}
	
	public boolean establishConnection( DatagramPacket aDatagram ){
		
		try {
			
			String vData = new String( aDatagram.getData(), 0, aDatagram.getLength() );
		
			VisionManagement.getLogger().info( "Got connectionrequest from: " + aDatagram.getAddress().getHostAddress() + ":" + aDatagram.getPort() + " - " + vData );
			
			VisionManagement.getLogger().debug( "Sending handshake: " + vData +Core.getInstance().getServerConfig().getServerName());
			DatagramChannel vChannelToClient = aDatagram.
			sendDatagramm(  );
			
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
	
}

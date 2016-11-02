package de.fh_kiel.robotics.mr.server.core.graphics.data;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

import de.fh_kiel.robotics.mr.server.core.Core;
import de.fh_kiel.robotics.mr.server.core.graphics.GraphicsManagement;
import de.fh_kiel.robotics.mr.server.core.graphics.network.GraphicsConnection;
import mrservermisc.network.handshake.server.ConnectionAcknowlege;
import mrservermisc.network.handshake.server.ConnectionEstablished;
import mrservermisc.network.handshake.server.ConnectionRequest;
import mrservermisc.network.xml.Helpers;

public class GraphicModul {

	private final GraphicsConnection mGraphicsConnection;
	private final InetSocketAddress mSocketAddress;
	private String mClientName;
	
	@SuppressWarnings("rawtypes")
	private Class mExpectedPacket = mrservermisc.network.handshake.server.ConnectionEstablished.class;
	
	private AtomicBoolean mIsActive = new AtomicBoolean( true );
	private int mValidityCounter = 0;
	
	public GraphicModul( SocketAddress aSocketAddress, GraphicsConnection aGraphicsConnection ) {

		mSocketAddress = (InetSocketAddress) aSocketAddress;
		mGraphicsConnection = aGraphicsConnection;
		GraphicsManagement.getLogger().debug( "Created graphicsmodul " + mSocketAddress.toString() );
		
	}

	@SuppressWarnings("unchecked")
	public synchronized void processDatagrammPacket( DatagramPacket aRecievedDatagrammPacket ) {
		
		Object vUnkownObject = null;
		
		if( mExpectedPacket != null ){
		
			vUnkownObject = Helpers.unmarshallXMLString( new String( aRecievedDatagrammPacket.getData(), 0, aRecievedDatagrammPacket.getLength() ), mExpectedPacket );
		
		}
		
		if( vUnkownObject != null ){
		
			if( vUnkownObject instanceof ConnectionEstablished && mExpectedPacket == ConnectionEstablished.class ){
				
				process((ConnectionEstablished) vUnkownObject);
				
			} else {

				mValidityCounter++;
				
			}
		
		} else {
			
			mValidityCounter++;
			
		}
		
		checkValidity();
	}

	private void checkValidity() {
		
		GraphicsManagement.getLogger().info( mClientName + " has validity of " + mValidityCounter );
		
	}
	
	private void process( ConnectionEstablished aConnectionEstablished ) {
		
		mValidityCounter = 0;
		mExpectedPacket = null;
		GraphicsManagement.getLogger().info( mClientName + " has established a connection" );
		
	}

	public boolean connectionRequest( DatagramPacket aDatagramPacket ) {
		
		if( aDatagramPacket.getSocketAddress().equals( mSocketAddress ) ){
			
			String vXMLString =  new String( aDatagramPacket.getData(), 0, aDatagramPacket.getLength() );
			
			GraphicsManagement.getLogger().debug( "Connection request (" + ( (InetSocketAddress) aDatagramPacket.getSocketAddress()).toString() + "):" + vXMLString  );
			ConnectionRequest vConnectionRequest = ConnectionRequest.unmarshallXMLConnectionRequestString( vXMLString );
			
			if( vConnectionRequest != null ){
				
				mClientName = vConnectionRequest.getClientGraphicsName();
				
				String vAcknowlegeRequest = ( new ConnectionAcknowlege( Core.getInstance().getServerConfig().getServerName(), mClientName, true ) ).toXMLString();
				
				mGraphicsConnection.sendString( vAcknowlegeRequest, mSocketAddress );
				
				return true;
				
			}
			
		}
		
		return false;
	}
	
	public void sendData( String aData ){
		
		mGraphicsConnection.sendString( aData, mSocketAddress );
		
	}

	public boolean isActive(){
		
		return mIsActive.get();
		
	}
	
	public String getName(){
		
		return mClientName;
		
	}
	
}

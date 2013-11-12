package mrserver.core.graphics.data;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import mrserver.core.Core;
import mrserver.core.graphics.GraphicsManagement;
import mrserver.core.graphics.network.GraphicsConnection;
import mrservermisc.graphics.interfaces.Graphic;
import mrservermisc.network.data.position.PositionDataPackage;
import mrservermisc.network.handshake.ConnectionAcknowlege;
import mrservermisc.network.handshake.ConnectionEstablished;
import mrservermisc.network.handshake.ConnectionRequest;
import mrservermisc.network.xml.Helpers;

public class GraphicModul {

	private final GraphicsConnection mGraphicsConnection;
	private final InetSocketAddress mSocketAddress;
	private String mClientName;
	
	@SuppressWarnings("rawtypes")
	private Class mExpectedPacket = mrservermisc.network.handshake.ConnectionEstablished.class;
	
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
	
	private void process( Object aIgnore ) {
		
		GraphicsManagement.getLogger().error( "Doesnt work that way, Sherlock" );
		
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
	
	public void sendData( PositionDataPackage aPositionData ){
		
		mGraphicsConnection.sendString( aPositionData.toXMLString(), mSocketAddress );
		
	}

	public boolean isActive(){
		
		return mIsActive.get();
		
	}
	
}

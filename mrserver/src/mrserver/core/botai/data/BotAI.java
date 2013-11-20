package mrserver.core.botai.data;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

import mrserver.core.botai.BotAIManagement;
import mrserver.core.botai.network.BotAiHost;
import mrserver.core.botai.network.data.UnkownBotAI;
import mrserver.core.scenario.ScenarioManagement;
import mrservermisc.bots.interfaces.Bot;
import mrservermisc.network.BasicUDPHostConnection;
import mrservermisc.network.handshake.client.ClientType;
import mrservermisc.network.handshake.client.ConnectionRequest;
import mrservermisc.network.handshake.client.MovementMode;
import mrservermisc.network.handshake.client.ProtocolVersion;
import mrservermisc.network.handshake.client.oneo.ConnectionAcknowlege;
import mrservermisc.network.handshake.client.oneo.ConnectionServerData;

public class BotAI implements Bot{

	private final BotAiHost mBotAIConnection;
	private final InetSocketAddress mSocketAddress;
	private String mClientName;
	private int mRcId;
	private int mVtId;
	
	private ProtocolVersion mProtocolVersion;
	
	private boolean mConnectionEstablished = false;
	private String mLastRecivedData = "";
	
	public BotAI( UnkownBotAI vUnkownBotAI ) {

		mBotAIConnection = vUnkownBotAI.getBotAIConnect();
		mSocketAddress = (InetSocketAddress) vUnkownBotAI.getRecievedDatagramPacket().getSocketAddress();
		BotAIManagement.getLogger().debug( "Created botai " + mSocketAddress.toString() + " at " + mBotAIConnection.toString() );
		
	}

	public void processDatagrammPacket( DatagramPacket aDatagramPacket ) {
		
		if( aDatagramPacket.getSocketAddress().equals( mSocketAddress ) ){
			
			if( !mConnectionEstablished ){
				
				String vXMLString =  new String( aDatagramPacket.getData(), 0, aDatagramPacket.getLength() );
				
				BotAIManagement.getLogger().debug( "Connection acknowlege (" + ( (InetSocketAddress) aDatagramPacket.getSocketAddress()).toString() + "):" + vXMLString  );
				if( mProtocolVersion == ProtocolVersion.OnePointO ){
					
					ConnectionAcknowlege vConnectionAcknowlege = ConnectionAcknowlege.unmarshallXMLConnectionRequestString( vXMLString );
					
					if( vConnectionAcknowlege != null && vConnectionAcknowlege.isAccepted() ){
						
						String vServerData = ( new ConnectionServerData( ClientType.Client, ProtocolVersion.OnePointO, MovementMode.WheelVelocities ) ).toXMLString();
						
						mBotAIConnection.sendString( vServerData, mSocketAddress );
						mConnectionEstablished = true;
						ScenarioManagement.getInstance().registerNewBot( this );
						
					}
					
				}
				
			} else {
				
				receiveNewData( new String( aDatagramPacket.getData(), 0, aDatagramPacket.getLength() ));
				
			}
		
		} else {
			
			BotAIManagement.getLogger().error( "Wrong packet to botai: \n"
					+ "(" + ( (InetSocketAddress) aDatagramPacket.getSocketAddress()).toString() + "):" + new String( aDatagramPacket.getData(), 0, aDatagramPacket.getLength() ) + "\n"
					+  toString() );
			
		}
		
	}

	private void receiveNewData( String aData ) {
		
		//TODO: minlength betwenn receives
		mLastRecivedData = aData;
		
	}

	public boolean connectionRequest( DatagramPacket aDatagramPacket ) {
		
		if( aDatagramPacket.getSocketAddress().equals( mSocketAddress ) ){
			
			String vXMLString =  new String( aDatagramPacket.getData(), 0, aDatagramPacket.getLength() );
			
			BotAIManagement.getLogger().debug( "Connection request (" + ( (InetSocketAddress) aDatagramPacket.getSocketAddress()).toString() + "):" + vXMLString  );
			ConnectionRequest vConnectionRequest = ConnectionRequest.unmarshallXMLConnectionRequestString( vXMLString );
			
			if( vConnectionRequest != null ){
				
				mClientName = vConnectionRequest.getClientName();
				mRcId = vConnectionRequest.getRcId();
				mVtId = vConnectionRequest.getVtId();
				mProtocolVersion = vConnectionRequest.getVersion();
				
				if( vConnectionRequest.getVersion() == ProtocolVersion.OnePointO ){
					
					String vAcknowlegeRequest = ( new ConnectionAcknowlege( true ) ).toXMLString();
					
					mBotAIConnection.sendString( vAcknowlegeRequest, mSocketAddress );
					
				} else {
					
					return false;
					
				}
				
				return true;
				
			}
			
		} else {
			
			BotAIManagement.getLogger().error( "Wrong packet to botai: \n"
					+ "(" + ( (InetSocketAddress) aDatagramPacket.getSocketAddress()).toString() + "):" + new String( aDatagramPacket.getData(), 0, aDatagramPacket.getLength() ) + "\n"
					+  toString() );
			
		}
		
		return false;
		
	}

	@Override
	public String toString() {
		return "BotAI [mBotAIConnection=" + mBotAIConnection
				+ ", mSocketAddress=" + mSocketAddress + ", mClientName="
				+ mClientName + ", mRcId=" + mRcId + ", mVcId=" + mVtId
				+ ", mConnectionEstablished=" + mConnectionEstablished + "]";
	}

	@Override
	public void sendWorldStatus(String aWorldData) {
		
		mBotAIConnection.sendString( aWorldData, mSocketAddress );
		
	}

	@Override
	public String getLastAction() {
		
		return mLastRecivedData;
		
	}

	@Override
	public int getTeam() {
		
		return mBotAIConnection.getTeam();
		
	}

	@Override
	public String getName() {
		
		return mClientName;
		
	}

	@Override
	public int getRcId() {

		return mRcId;
		
	}

	@Override
	public int getVtId() {

		return mVtId;
		
	}

	public InetSocketAddress getSocketAddress() {
		return mSocketAddress;
	}

}

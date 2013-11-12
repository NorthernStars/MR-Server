package mrserver.core.botai.network.data;

import java.net.DatagramPacket;

import mrservermisc.network.BasicUDPHostConnection;

public class UnkownBotAI {
	
	private DatagramPacket mRecievedDatagrammPacket;
	private BasicUDPHostConnection mBotAIConnect;
	
	public UnkownBotAI( BasicUDPHostConnection aBotAIConnect, DatagramPacket aRecievedDatagramm ){
		
		mRecievedDatagrammPacket = aRecievedDatagramm;
		mBotAIConnect = aBotAIConnect;
		
	}

	public DatagramPacket getRecievedDatagrammPacket() {
		return mRecievedDatagrammPacket;
	}

	public BasicUDPHostConnection getBotAIConnect() {
		return mBotAIConnect;
	}

	@Override
	public String toString() {
		return "UnkownBotAI [mRecievedDatagrammPacket="
				+ mRecievedDatagrammPacket + ", mBotAIConnect=" + mBotAIConnect
				+ "]";
	}
	
}

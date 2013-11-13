package mrserver.core.botai.network.data;

import java.net.DatagramPacket;

import mrservermisc.network.BasicUDPHostConnection;

public class UnkownBotAI {
	
	private DatagramPacket mRecievedDatagramPacket;
	private BasicUDPHostConnection mBotAIConnect;
	
	public UnkownBotAI( BasicUDPHostConnection aBotAIConnect, DatagramPacket aRecievedDatagram ){
		
		mRecievedDatagramPacket = aRecievedDatagram;
		mBotAIConnect = aBotAIConnect;
		
	}

	public DatagramPacket getRecievedDatagramPacket() {
		return mRecievedDatagramPacket;
	}

	public BasicUDPHostConnection getBotAIConnect() {
		return mBotAIConnect;
	}

	@Override
	public String toString() {
		return "UnkownBotAI [mRecievedDatagramPacket="
				+ mRecievedDatagramPacket + ", mBotAIConnect=" + mBotAIConnect
				+ "]";
	}
	
}

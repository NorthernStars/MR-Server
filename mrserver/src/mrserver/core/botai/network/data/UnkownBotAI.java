package mrserver.core.botai.network.data;

import java.net.DatagramPacket;

import mrserver.core.botai.network.BotAiHost;

public class UnkownBotAI {
	
	private DatagramPacket mRecievedDatagramPacket;
	private BotAiHost mBotAIConnect;
	
	public UnkownBotAI( BotAiHost aBotAIConnect, DatagramPacket aRecievedDatagram ){
		
		mRecievedDatagramPacket = aRecievedDatagram;
		mBotAIConnect = aBotAIConnect;
		
	}

	public DatagramPacket getRecievedDatagramPacket() {
		return mRecievedDatagramPacket;
	}

	public BotAiHost getBotAIConnect() {
		return mBotAIConnect;
	}

	@Override
	public String toString() {
		return "UnkownBotAI [mRecievedDatagramPacket="
				+ mRecievedDatagramPacket + ", mBotAIConnect=" + mBotAIConnect
				+ "]";
	}
	
}

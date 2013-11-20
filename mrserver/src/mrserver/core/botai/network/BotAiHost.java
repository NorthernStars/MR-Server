package mrserver.core.botai.network;

import java.net.DatagramSocket;

import mrservermisc.network.BasicUDPHostConnection;

public class BotAiHost extends BasicUDPHostConnection{

	private int mTeam;
	
	public BotAiHost(int aBotAIPort, int aTeam ) {
		
		super( aBotAIPort );
		mTeam = aTeam;
		
	}
	
	public int getTeam(){
		
		return mTeam;
		
	}

	@Override
	public String toString() {
		return "BotAiHost [mTeam=" + mTeam + ", toString()=" + super.toString()
				+ "]";
	}

	public int getPort() {
		return mToTargetSocket.getLocalPort();
	}

}

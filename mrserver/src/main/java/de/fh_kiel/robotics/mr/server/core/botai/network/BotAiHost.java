package de.fh_kiel.robotics.mr.server.core.botai.network;

import de.fh_kiel.robotics.mr.server.misc.network.BasicUDPHostConnection;

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

package mrserver.core.botcontrol.network;

import java.io.IOException;


import mrserver.core.Core;
import mrservermisc.network.BasicUDPServerConnection;

public class BotControlConnection extends BasicUDPServerConnection {

	private boolean mIsConnectionEstablished = false;

	public BotControlConnection() throws IOException{
		
		super( Core.getInstance().getServerConfig().getBotControlIPAdress(), Core.getInstance().getServerConfig().getBotControlPort() );
				
	}
	
	public BotControlConnection( int aHostPort ) throws IOException{
		
		super( Core.getInstance().getServerConfig().getBotControlIPAdress(), Core.getInstance().getServerConfig().getBotControlPort(), aHostPort );
				
	}
	
	public boolean establishConnection(){
			
		mIsConnectionEstablished = true;
		return mIsConnectionEstablished;
		
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

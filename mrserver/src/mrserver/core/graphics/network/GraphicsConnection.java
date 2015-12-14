package mrserver.core.graphics.network;

import java.net.InetAddress;

import mrserver.core.Core;
import mrservermisc.network.BasicUDPHostConnection;

public class GraphicsConnection extends BasicUDPHostConnection {
	
	public GraphicsConnection(){
		
		super( Core.getInstance().getServerConfig().getGraphicsPort() );
				
	}
	
	public int getPort(){
		return mToTargetSocket.getLocalPort();
	}
	
}

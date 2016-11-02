package de.fh_kiel.robotics.mr.server.core.graphics.network;

import java.net.InetAddress;

import de.fh_kiel.robotics.mr.server.core.Core;
import de.fh_kiel.robotics.mr.server.misc.network.BasicUDPHostConnection;

public class GraphicsConnection extends BasicUDPHostConnection {
	
	public GraphicsConnection(){
		
		super( Core.getInstance().getServerConfig().getGraphicsPort() );
				
	}
	
	public int getPort(){
		return mToTargetSocket.getLocalPort();
	}
	
}

package mrserver.core.graphics.network;

import mrserver.core.Core;
import mrservermisc.network.BasicUDPHostConnection;

public class GraphicsConnection extends BasicUDPHostConnection {
	
	public GraphicsConnection(){
		
		super( Core.getInstance().getServerConfig().getGraphicsPort() );
				
	}
	
}

package mrserver.core.graphics.network;

import java.io.IOException;

import org.apache.logging.log4j.Level;

import mrserver.core.Core;
import mrserver.core.graphics.GraphicsManagement;
import mrservermisc.network.BasicUDPHostConnection;

public class GraphicsConnection extends BasicUDPHostConnection {
	
	private GraphicsConnection() throws IOException{
		
		super( Core.getInstance().getServerConfig().getGraphicsPort() );
				
	}
	
}

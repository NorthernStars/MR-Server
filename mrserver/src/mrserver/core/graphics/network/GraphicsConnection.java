package mrserver.core.graphics.network;

import java.io.IOException;

import org.apache.logging.log4j.Level;

import mrserver.core.Core;
import mrserver.core.graphics.GraphicsManagement;
import mrservermisc.network.BasicUDPHostConnection;

public class GraphicsConnection extends Thread {
	
	private static GraphicsConnection INSTANCE;
    
    public static GraphicsConnection getInstance() {
        
        if( GraphicsConnection.INSTANCE == null){
        	GraphicsManagement.getLogger().debug( "Creating GraphicsConnection-instance." );
        	
        	try{
        		
        		GraphicsConnection.INSTANCE = new GraphicsConnection();
        		GraphicsManagement.getLogger().debug( "Created GraphicsConnection-instance:" );
            	
        	} catch( IOException vIOException ){

                Core.getLogger().error( "Error creating graphicshost: " + vIOException.getLocalizedMessage() );
                Core.getLogger().catching( Level.ERROR, vIOException );
        		
        		
        		
        	}
        	
        }

        GraphicsManagement.getLogger().trace( "Retrieving GraphicsConnection-instance." );
        return GraphicsConnection.INSTANCE;
        
    }
    
	BasicUDPHostConnection vGraphicsHost;
	
	private GraphicsConnection() throws IOException{
		
		vGraphicsHost = new BasicUDPHostConnection( Core.getInstance().getServerConfig().getGraphicsPort() );
				
	}
	
}

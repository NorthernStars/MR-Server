package mrserver.core.graphics;


import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GraphicsManagement {
	
    private static GraphicsManagement INSTANCE;

    private GraphicsManagement(){
    	
    	GraphicsManagement.getLogger().debug( "Creating MapOfConnectedGraphicModules." );
    	aMapOfConnectedGraphicModules = new ConcurrentHashMap<SocketAddress, GraphicModul>();
    	
    }
    
    public static GraphicsManagement getInstance() {
        
        if( GraphicsManagement.INSTANCE == null){
        	GraphicsManagement.getLogger().debug( "Creating GraphicsManagement-instance." );
        	GraphicsManagement.INSTANCE = new GraphicsManagement();
        }

        GraphicsManagement.getLogger().trace( "Retrieving GraphicsManagement-instance." );
        return GraphicsManagement.INSTANCE;
        
    }
    
    private static Logger GRAPHICSMANAGEMENTLOGGER = LogManager.getLogger("GRAPHICSMANAGEMENT");
    
    public static Logger getLogger(){
        
        return GRAPHICSMANAGEMENTLOGGER;
        
    }
    
    private ConcurrentHashMap<SocketAddress, GraphicModul> aMapOfConnectedGraphicModules;
    
    public ConcurrentHashMap<SocketAddress, GraphicModul> getMapOfConnections(){
    	
        GraphicsManagement.getLogger().trace( "Retrieving MapOfConnectedGraphicModules." );
    	return aMapOfConnectedGraphicModules;
    	
    }
    
    private List<GraphicModul> mGraphicModules;
    
    public List<GraphicModul> getGraphicModules(){
    	
    	if( mGraphicModules == null ){
    		
    		mGraphicModules = new ArrayList<GraphicModul>();
    		
    	}
    	
    	return mGraphicModules;    	
    	
    }

}

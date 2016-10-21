package mrserver.core.graphics;


import java.net.SocketAddress;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import mrserver.core.graphics.data.GraphicModul;
import mrserver.core.graphics.network.GraphicsConnection;
import mrserver.core.graphics.network.receive.Creator;
import mrserver.core.graphics.network.receive.Receiver;
import mrserver.gui.options.interfaces.GraphicsManagementListener;
import mrservermisc.graphics.interfaces.Graphics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GraphicsManagement implements Graphics{
	
    private static GraphicsManagement INSTANCE;
    
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
    
    private ConcurrentHashMap<SocketAddress, GraphicModul> mMapOfConnectedGraphicModules = new ConcurrentHashMap<SocketAddress, GraphicModul>();;
    private GraphicsConnection mGraphicsConnection;
    private Receiver mGraphicsReceiver;
    private Creator mGraphicsCreator;
	private GraphicsManagementListener mListener;

    private GraphicsManagement(){
    	
    }
    
    public void startGraphicsManagement(){
    	
    	GraphicsManagement.getLogger().info( "Starting graphicsmanagement" );
    	
    	GraphicsManagement.getLogger().debug( "Creating graphicsconnection." );
    	mGraphicsConnection = new GraphicsConnection();

    	GraphicsManagement.getLogger().debug( "Starting receiver." );
    	mGraphicsReceiver = new Receiver( mGraphicsConnection );
    	mGraphicsReceiver.startManagement();
    	
    	GraphicsManagement.getLogger().debug( "Starting creator." );
    	mGraphicsCreator= new Creator( mGraphicsConnection );
    	mGraphicsCreator.startManagement();
    	
    }
    
    public void close(){
    	
    	if( mGraphicsReceiver != null ){
    		mGraphicsReceiver.stopManagement();
    	}
    	if( mGraphicsCreator != null ) {
    		mGraphicsCreator.stopManagement();
    	}
    	if( mGraphicsConnection != null ) {
    		mGraphicsConnection.closeConnection();
    	}
    	GraphicsManagement.getLogger().info( "Graphicsmanagement closed" );
    	
    }
    
    public ConcurrentHashMap<SocketAddress, GraphicModul> getMapOfConnections(){
    	
        GraphicsManagement.getLogger().trace( "Retrieving MapOfConnectedGraphicModules." );
    	return mMapOfConnectedGraphicModules;
    	
    }

	@Override
	public boolean sendWorldStatus( String aWorldDataXML ) {
		
		for( GraphicModul vDing : Collections.list( mMapOfConnectedGraphicModules.elements() ) ){
			
			vDing.sendData( aWorldDataXML );
			
		}
		
		return true;
	}

	public boolean isStarted() {

		return mGraphicsConnection != null && mGraphicsConnection.isConnected() && 
			   mGraphicsCreator != null && mGraphicsCreator.isAlive() && 
			   mGraphicsReceiver!= null && mGraphicsReceiver.isAlive();
		
	}
	
	public void registerListener( GraphicsManagementListener aListener ){
		
		mListener = aListener;
		
	}

	public GraphicsManagementListener getListener() {
		return mListener;
	}
	
	

}

package mrserver.core.graphics.network.receive;

import java.net.DatagramPacket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import mrserver.core.graphics.GraphicsManagement;
import mrserver.core.graphics.data.GraphicModul;
import mrserver.core.graphics.network.GraphicsConnection;

import org.apache.logging.log4j.Level;

public class Creator extends Thread{
	
private static final BlockingQueue<DatagramPacket> UNKOWNSENDERDATAGRAMS = new ArrayBlockingQueue<>( 25, true );
	
	public static void putUnkownSenderDatagramInProcessingQueue( DatagramPacket aUnkownSenderDatagram ){
		
		try {
			GraphicsManagement.getLogger().trace( "Putting datagram in unkown sender queue: " + aUnkownSenderDatagram.getSocketAddress().toString() + " " + aUnkownSenderDatagram.getData() );
			UNKOWNSENDERDATAGRAMS.put( aUnkownSenderDatagram );
			
		} catch ( InterruptedException vInterruptedException ) {

            GraphicsManagement.getLogger().error( "Error processing unkownsender-datagram: " + vInterruptedException.getLocalizedMessage() );
            GraphicsManagement.getLogger().catching( Level.ERROR, vInterruptedException );
            
		} 
		
	}
	

	private final GraphicsConnection mGraphicsConnect;
	
	private AtomicBoolean mManageMessagesfromGraphics = new AtomicBoolean( false );
	public Creator( GraphicsConnection aGraphicsConnect ) {

		mGraphicsConnect = aGraphicsConnect;

	}
	
	public void close(){
		
		stopManagement();
		
	}

	public boolean startManagement() {
		
		GraphicsManagement.getLogger().debug( "Starting to process unkown sender packets from graphics" );
		
		if( !isAlive() ) {
			
			super.start();
			mManageMessagesfromGraphics.set( true);
			GraphicsManagement.getLogger().info( "Started processing unkown sender packets from graphics" );
            
		} else {
		    
			GraphicsManagement.getLogger().debug( "Unkown sender ackets from graphics are already beingprocessed" );
			
		}
		
		return mManageMessagesfromGraphics.get();
		
	}
	
	@Override
	public void start(){
		
		this.startManagement();
		
	}
	
	public void stopManagement(){
		 
	    mManageMessagesfromGraphics.set( false );
	    
	    if( isAlive()){
	      
            while( isAlive() ){ 
            	
                try {
                	
                    Thread.sleep( 10 );
                    
                } catch ( InterruptedException vInterruptedException ) {
                	
                    GraphicsManagement.getLogger().error( "Error stopping GraphicsCreator: " + vInterruptedException.getLocalizedMessage() );
                    GraphicsManagement.getLogger().catching( Level.ERROR, vInterruptedException );
                    
                } 
            }
            
            UNKOWNSENDERDATAGRAMS.clear();
            
	    }
	    
	    GraphicsManagement.getLogger().info( "GraphicsCreator stopped." );
	    		
	}
	
	@Override
	public void run(){
	    
		GraphicModul vGraphicModul;
		DatagramPacket vUnkownSenderDatagramPacket;
		
		while( mManageMessagesfromGraphics.get() ){
            
			try {

				vUnkownSenderDatagramPacket = UNKOWNSENDERDATAGRAMS.poll( 100, TimeUnit.MILLISECONDS );
				if( vUnkownSenderDatagramPacket != null ){
						
					if( !GraphicsManagement.getInstance().getMapOfConnections().containsKey( vUnkownSenderDatagramPacket.getSocketAddress() ) ){
					
						vGraphicModul = new GraphicModul( vUnkownSenderDatagramPacket.getSocketAddress(), mGraphicsConnect );
						
						if( vGraphicModul.connectionRequest( vUnkownSenderDatagramPacket ) ){
							
							GraphicsManagement.getInstance().getMapOfConnections().put( vUnkownSenderDatagramPacket.getSocketAddress(), vGraphicModul );
							GraphicsManagement.getInstance().getListener().newConnection( vUnkownSenderDatagramPacket.getSocketAddress() );
							
						}
					
					} else {
						
						GraphicsManagement.getInstance().getMapOfConnections().get( vUnkownSenderDatagramPacket.getSocketAddress() ).processDatagrammPacket( vUnkownSenderDatagramPacket );
						
					}
					
				}
				
			} catch ( Exception vException ) {
				
				GraphicsManagement.getLogger().error( "Error processing unkown packets from graphics: " + vException.getLocalizedMessage() );
				GraphicsManagement.getLogger().catching( Level.ERROR, vException );
                				
			}
			
		}
		
	}

}

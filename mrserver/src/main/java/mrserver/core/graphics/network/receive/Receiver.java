package mrserver.core.graphics.network.receive;

import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import mrserver.core.graphics.GraphicsManagement;
import mrserver.core.graphics.network.GraphicsConnection;

import org.apache.logging.log4j.Level;

public class Receiver extends Thread {

	private final GraphicsConnection mGraphicsConnect;
	
	private AtomicBoolean mManageMessagesfromGraphics = new AtomicBoolean( false );
	private AtomicBoolean mSuspend = new AtomicBoolean( false );
	private AtomicBoolean mIsSuspended = new AtomicBoolean( false );

	public Receiver( GraphicsConnection aGraphicsConnect ) {

		mGraphicsConnect = aGraphicsConnect;

	}
	
	public void close(){
		
		stopManagement();
		
	}

	public boolean startManagement() {
		
		GraphicsManagement.getLogger().debug( "Starting to receive and process packets from graphics" );
		
		if( !isAlive() ) {
			
			super.start();
			mManageMessagesfromGraphics.set( true);
			GraphicsManagement.getLogger().info( "Started processing packets from graphics on port {}", mGraphicsConnect.getPort() );
            
		} else {
		    
			GraphicsManagement.getLogger().debug( "Packets from graphics are already being received and processed" );
			
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
                	
                    GraphicsManagement.getLogger().error( "Error stopping GraphicsReceiver: {}", vInterruptedException.getLocalizedMessage() );
                    GraphicsManagement.getLogger().catching( Level.ERROR, vInterruptedException );
                    
                } 
            }
            
	    }
	    
	    GraphicsManagement.getLogger().info( "GraphicsReceiver stopped." );
	    		
	}

	public synchronized boolean suspendManagement() {
		
		GraphicsManagement.getLogger().debug( "Suspending processing packets from graphics" ) ;
		return suspendManagement( true );
		
	}

	public synchronized boolean resumeManagement() {
		
		GraphicsManagement.getLogger().debug( "Resumeing processing packets from graphics" ) ;
		return suspendManagement( false );
		
	}

	private boolean suspendManagement( boolean aSuspend ) {
		mSuspend.set( aSuspend );
		while( mIsSuspended.get() != aSuspend ){ try { this.wait( 1 ); } catch ( InterruptedException vInterruptedException ) { GraphicsManagement.getLogger().error( "Error suspending GraphicsReciever: {}", vInterruptedException.getLocalizedMessage() ); GraphicsManagement.getLogger().catching( Level.ERROR, vInterruptedException ); } }
		return mIsSuspended.get();
	}
	
	@Override
	public void run(){
	    
		while( mManageMessagesfromGraphics.get() ){
            
			if( mSuspend.get() ){ synchronized(this){ mIsSuspended.set( true ); } }
            while( mSuspend.get() ){ try { this.wait( 10 ); } catch ( InterruptedException vInterruptedException ) { GraphicsManagement.getLogger().error( "Error suspending GraphicsReceiver: {}", vInterruptedException.getLocalizedMessage() ); GraphicsManagement.getLogger().catching( Level.ERROR, vInterruptedException ); } }
			mIsSuspended.compareAndSet(true, false);
            
			try {
				
				if( mGraphicsConnect.isConnected() ) {
					
					Worker.putWorkerInPool( new Worker( mGraphicsConnect.getDatagrammPacket( 100 ) ) );
					
				} else {
				    
				    GraphicsManagement.getLogger().debug( "Graphics is not connected" ) ;
					
				}
				
			} catch ( SocketTimeoutException vSocketTimeoutException ) {
                
                GraphicsManagement.getLogger().trace( "Received no datagramm from graphics in 100ms" );
                                
			} catch ( Exception vException ) {
				
				GraphicsManagement.getLogger().error( "Error receiving messages from graphics: {}", vException.getLocalizedMessage() );
				GraphicsManagement.getLogger().catching( Level.ERROR, vException );
                				
			}
			
		}
		
	}
	
}

package mrserver.core.graphics.network.recieve;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import mrserver.core.graphics.network.GraphicsConnection;
import mrserver.core.vision.VisionManagement;
import mrserver.core.vision.network.VisionConnection;
import mrservermisc.network.data.position.PositionDataPackage;
import mrservermisc.network.data.position.PositionObject;
import mrservermisc.network.data.position.VisionMode;
import net.jcip.annotations.GuardedBy;

import org.apache.logging.log4j.Level;

public class Reciever extends Thread {

	private final GraphicsConnection mGraphicsConnect;
	
	private AtomicBoolean mManageMessagesfromGraphics = new AtomicBoolean( false );
	private AtomicBoolean mSuspend = new AtomicBoolean( false );
	private AtomicBoolean mIsSuspended = new AtomicBoolean( false );

	public Reciever( GraphicsConnection aGraphicsConnect ) {

		mGraphicsConnect = aGraphicsConnect;

	}
	
	public void close(){
		
		stopManagement();
		
	}

	public boolean startManagement() {
		
		VisionManagement.getLogger().debug( "Starting to recive and process packets from graphics" );
		
		if( !isAlive() ) {
			
			super.start();
			mManageMessagesfromGraphics.set( true);
			VisionManagement.getLogger().info( "Started processing packets from graphics" );
            
		} else {
		    
			VisionManagement.getLogger().debug( "Packets from graphics are already being recieved and processed" );
			
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
                	
                    VisionManagement.getLogger().error( "Error stopping GraphicsReciever: " + vInterruptedException.getLocalizedMessage() );
                    VisionManagement.getLogger().catching( Level.ERROR, vInterruptedException );
                    
                } 
            }
            
	    }
	    
	    VisionManagement.getLogger().info( "GraphicsReciever stopped." );
	    		
	}

	public synchronized boolean suspendManagement() {
		
		VisionManagement.getLogger().debug( "Suspending processing packets from graphics" ) ;
		return suspendManagement( true );
		
	}

	public synchronized boolean resumeManagement() {
		
		VisionManagement.getLogger().debug( "Resumeing processing packets from graphics" ) ;
		return suspendManagement( false );
		
	}

	private boolean suspendManagement( boolean aSuspend ) {
		mSuspend.set( aSuspend );
		while( mIsSuspended.get() != aSuspend ){ try { this.wait( 1 ); } catch ( InterruptedException vInterruptedException ) { VisionManagement.getLogger().error( "Error suspending GraphicsReciever: " + vInterruptedException.getLocalizedMessage() ); VisionManagement.getLogger().catching( Level.ERROR, vInterruptedException ); } }
		return mIsSuspended.get();
	}
	
	@Override
	public void run(){
	    
		PositionDataPackage vPositionDataFromVision;
		
		while( mManageMessagesfromGraphics.get() ){
            
			if( mSuspend.get() ){ synchronized(this){ mIsSuspended.set( true ); } }
            while( mSuspend.get() ){ try { this.wait( 10 ); } catch ( InterruptedException vInterruptedException ) { VisionManagement.getLogger().error( "Error suspending GraphicsReciever: " + vInterruptedException.getLocalizedMessage() ); VisionManagement.getLogger().catching( Level.ERROR, vInterruptedException ); } }
			mIsSuspended.compareAndSet(true, false);
            
			try {
				
				if( mGraphicsConnect.isConnected() ) {
					
					mGraphicsConnect.getDatagrammPacket( 100 );
					
				} else {
				    
				    VisionManagement.getLogger().debug( "Graphics is not Connected" ) ;
					
				}
				
			} catch ( SocketTimeoutException vSocketTimeoutException ) {
				
				VisionManagement.getLogger().trace( "Recived no datagramm from graphics in 100ms" );
                				
			} catch ( Exception vException ) {
				
				VisionManagement.getLogger().error( "Error recieving messages from graphics: " + vException.getLocalizedMessage() );
				VisionManagement.getLogger().catching( Level.ERROR, vException );
                				
			}
			
		}
		
	}
	
}

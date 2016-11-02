package de.fh_kiel.robotics.mr.server.core.vision;

import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.Level;

import de.fh_kiel.robotics.mr.server.core.scenario.ScenarioManagement;
import de.fh_kiel.robotics.mr.server.core.vision.network.VisionConnection;
import mrservermisc.network.data.position.PositionDataPackage;

public class VisionIncomingPacketsManagement extends Thread{

	private final VisionConnection mVisionConnect;
	
	private AtomicBoolean mManageMessagesfromVision = new AtomicBoolean( false );
	private AtomicBoolean mSuspend = new AtomicBoolean( false );
	private AtomicBoolean mIsSuspended = new AtomicBoolean( false );

	public VisionIncomingPacketsManagement( VisionConnection aVisionConnect ) {

		mVisionConnect = aVisionConnect;

	}
	
	public void close(){
		
		stopManagement();
		
	}

	public boolean startManagement() {
		
		VisionManagement.getLogger().debug( "Starting to recive and process packets from vision" );
		
		if( !isAlive() ) {
			
		    mManageMessagesfromVision.set( true);
			super.start();
            VisionManagement.getLogger().info( "Started processing packets from vision" );
            
		} else {
		    
			VisionManagement.getLogger().debug( "Packets from vision are already being recieved and processed" );
			
		}
		
		return mManageMessagesfromVision.get();
		
	}
	
	@Override
	public void start(){
		
		this.startManagement();
		
	}
	
	public void stopManagement(){
		 
	    mManageMessagesfromVision.set( false );
	    
	    if( isAlive()){
	      
            while(isAlive()){ 
            	
                try {
                	
                    Thread.sleep( 10 );
                    
                } catch ( InterruptedException vInterruptedException ) {
                	
                    VisionManagement.getLogger().error( "Error stopping VisionIncomingPacketManagement: {}", vInterruptedException.getLocalizedMessage() );
                    VisionManagement.getLogger().catching( Level.ERROR, vInterruptedException );
                    
                } 
            }
            
	    }
	    
	    VisionManagement.getLogger().info( "VisionIncomingPacketManagement stopped." );
	    		
	}

	public synchronized boolean suspendManagement() {
		
		VisionManagement.getLogger().debug( "Suspending processing packets from vision" ) ;
		return suspendManagement( true );
		
	}

	public synchronized boolean resumeManagement() {
		
		VisionManagement.getLogger().debug( "Resumeing processing packets from vision" ) ;
		return suspendManagement( false );
		
	}

	private boolean suspendManagement( boolean aSuspend ) {
		mSuspend.set( aSuspend );
		while( mIsSuspended.get() != aSuspend ){ try { Thread.sleep( 10 ); } catch ( InterruptedException vInterruptedException ) { VisionManagement.getLogger().error( "Error suspending VisionIncomingPacketManagement: {}", vInterruptedException.getLocalizedMessage() ); VisionManagement.getLogger().catching( Level.ERROR, vInterruptedException ); } }
		return mIsSuspended.get();
	}
	
	@Override
	public void run(){
	    String vReceivedXMLString;
		PositionDataPackage vPositionDataFromVision;
		
		while( mManageMessagesfromVision.get() ){
			
			if( mSuspend.get() ){ mIsSuspended.set( true ); }
            while( mSuspend.get() ){ try { Thread.sleep( 10 ); } catch ( InterruptedException vInterruptedException ) { VisionManagement.getLogger().error( "Error suspending VisionIncomingPacketManagement: {}",vInterruptedException.getLocalizedMessage() ); VisionManagement.getLogger().catching( Level.ERROR, vInterruptedException ); } }
			mIsSuspended.compareAndSet( true, false );
            
			try {
				
				if( mVisionConnect.isConnected() ) {
					
					vReceivedXMLString = mVisionConnect.getDatagrammString( 100 );
					VisionManagement.getLogger().debug( "Received data from vision: {}", vReceivedXMLString );
					
					vPositionDataFromVision = PositionDataPackage.unmarshallXMLPositionDataPackageString( vReceivedXMLString );
					if( vPositionDataFromVision != null ){
						
						VisionManagement.getLogger().debug( "Put positiondata to scenario({}): {} ", ScenarioManagement.getInstance().putPositionData( vPositionDataFromVision ), vPositionDataFromVision );
						
					}
					
				} else {
				    
				    VisionManagement.getLogger().trace( "Vision is not Connected" ) ;
					
				}
				
			} catch ( SocketTimeoutException vSocketTimeoutException ) {
                
                VisionManagement.getLogger().trace( "Recived no datagramm from vision in 100ms" );
                                
			} catch ( Exception vException ) {
				
				VisionManagement.getLogger().error( "Error recieving messages from vision: {}", vException.getLocalizedMessage() );
				VisionManagement.getLogger().catching( Level.ERROR, vException );
                				
			}
			
		}
		
	}

}

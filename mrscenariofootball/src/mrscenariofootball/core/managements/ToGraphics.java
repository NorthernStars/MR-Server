package mrscenariofootball.core.managements;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.worlddata.server.WorldData;
import mrservermisc.graphics.interfaces.Graphics;

import org.apache.logging.log4j.Level;

public class ToGraphics extends Thread {
		
		private static final BlockingQueue<WorldData> UNKOWNSENDERDATAGRAMS = new ArrayBlockingQueue<WorldData>( 5, true );
			
		public static void putWorldDatainSendingQueue( WorldData aWorldData ){
		
			ScenarioCore.getLogger().trace( "Putting worldata in to-graphics queue: " + aWorldData.toString() + " " );
			UNKOWNSENDERDATAGRAMS.offer( aWorldData );
			
		}
		
		private AtomicBoolean mManageMessagesToGraphicss = new AtomicBoolean( false );
		private Graphics mGraphics;
		
		public ToGraphics( Graphics aGraphics ) {

			mGraphics = aGraphics;
			
		}
		
		public void close(){
			
			stopManagement();
			
		}

		public boolean startManagement() {
			
			ScenarioCore.getLogger().debug( "Starting sending worlddata to graphics" );
			
			if( !isAlive() ) {
				
				super.start();
				mManageMessagesToGraphicss.set( true);
				ScenarioCore.getLogger().info( "Started sending worlddata to graphics" );
	            
			} else {
			    
				ScenarioCore.getLogger().debug( "Worlddata is already being sent to graphics" );
				
			}
			
			return mManageMessagesToGraphicss.get();
			
		}
		
		@Override
		public void start(){
			
			this.startManagement();
			
		}
		
		public void stopManagement(){
			 
		    mManageMessagesToGraphicss.set( false );
		    
		    if( isAlive()){
		      
	            while( isAlive() ){ 
	            	
	                try {
	                	
	                    Thread.sleep( 10 );
	                    
	                } catch ( InterruptedException vInterruptedException ) {
	                	
	                	ScenarioCore.getLogger().error( "Error stopping to-graphics: " + vInterruptedException.getLocalizedMessage() );
	                	ScenarioCore.getLogger().catching( Level.ERROR, vInterruptedException );
	                    
	                } 
	            }
	            
	            UNKOWNSENDERDATAGRAMS.clear();
	            
		    }
		    
		    ScenarioCore.getLogger().info( "To-graphics stopped." );
		    		
		}
		
		@Override
		public void run(){
			WorldData vWorldData;
			
			while( mManageMessagesToGraphicss.get() ){
	            
				try {

					vWorldData = UNKOWNSENDERDATAGRAMS.poll( 100, TimeUnit.MILLISECONDS );
					if( vWorldData != null ){
							
						mGraphics.sendWorldStatus( vWorldData.toXMLString() );
						
					}
					
				} catch ( Exception vException ) {
					
					ScenarioCore.getLogger().error( "Error sending to graphics: " + vException.getLocalizedMessage() );
					ScenarioCore.getLogger().catching( Level.ERROR, vException );
	                				
				}
				
			}
			
		}

}

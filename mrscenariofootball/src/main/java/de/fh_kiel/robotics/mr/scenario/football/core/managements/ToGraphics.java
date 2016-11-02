package de.fh_kiel.robotics.mr.scenario.football.core.managements;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.Level;

import de.fh_kiel.robotics.mr.scenario.football.core.ScenarioCore;
import de.fh_kiel.robotics.mr.scenario.football.core.data.ScenarioInformation;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.WorldData;

public class ToGraphics extends Thread {
		
		private static final BlockingQueue<WorldData> WORLDDATA = new ArrayBlockingQueue<WorldData>( 5, true );
			
		public static void putWorldDatainSendingQueue( WorldData aWorldData ){
		
			ScenarioCore.getLogger().trace( "Putting worldata in to-graphics queue: " + aWorldData.toString() + " " );
			WORLDDATA.offer( aWorldData );
			
		}
		
		private AtomicBoolean mManageMessagesToGraphics = new AtomicBoolean( false );
		
		public ToGraphics() {
			
	        this.setName( "Scenario_ToGraphics" );
	        
		}
		private static ToGraphics INSTANCE;
	    
	    public static ToGraphics getInstance() {
	        
	        if( ToGraphics.INSTANCE == null){
	        	ScenarioCore.getLogger().debug( "Creating ToGraphics-instance." );
	        	ToGraphics.INSTANCE = new ToGraphics();
	        }

	        ScenarioCore.getLogger().trace( "Retrieving ToGraphics-instance." );
	        return ToGraphics.INSTANCE;
	        
	    }
		
		public void close(){
			
			stopManagement();
			
		}

		public boolean startManagement() {
			
			ScenarioCore.getLogger().debug( "Starting sending worlddata to graphics" );
			
			if( !isAlive() ) {
				
				super.start();
				mManageMessagesToGraphics.set( true);
				ScenarioCore.getLogger().info( "Started sending worlddata to graphics" );
	            
			} else {
			    
				ScenarioCore.getLogger().debug( "Worlddata is already being sent to graphics" );
				
			}
			
			return mManageMessagesToGraphics.get();
			
		}
		
		@Override
		public void start(){
			
			this.startManagement();
			
		}
		
		public void stopManagement(){
			 
		    mManageMessagesToGraphics.set( false );
		    
		    if( isAlive()){
		      
	            while( isAlive() ){ 
	            	
	                try {
	                	
	                    Thread.sleep( 10 );
	                    
	                } catch ( InterruptedException vInterruptedException ) {
	                	
	                	ScenarioCore.getLogger().error( "Error stopping to-graphics: " + vInterruptedException.getLocalizedMessage() );
	                	ScenarioCore.getLogger().catching( Level.ERROR, vInterruptedException );
	                    
	                } 
	            }
	            
	            WORLDDATA.clear();
	            
		    }
		    
		    ScenarioCore.getLogger().info( "To-graphics stopped." );
		    		
		}
		
		@Override
		public void run(){
			WorldData vWorldData;
			
			while( mManageMessagesToGraphics.get() ){
	            
				try {

					vWorldData = WORLDDATA.take();

					if( vWorldData != null ){
							
						ScenarioCore.getLogger().trace("Sending Data {}", vWorldData);
						ScenarioInformation.getInstance().getGraphics().sendWorldStatus( vWorldData.toXMLString() );
						
					}
					
				} catch ( Exception vException ) {
					
					ScenarioCore.getLogger().error( "Error sending to graphics: {}", vException.getLocalizedMessage() );
					ScenarioCore.getLogger().catching( Level.ERROR, vException );
	                				
				}
				
			}
			
		}

}

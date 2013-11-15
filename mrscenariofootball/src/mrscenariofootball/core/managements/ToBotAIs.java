package mrscenariofootball.core.managements;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.BotAI;
import mrscenariofootball.core.data.worlddata.client.ClientWorldData;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.WorldData;
import mrservermisc.bots.interfaces.Bot;

import org.apache.logging.log4j.Level;

public class ToBotAIs extends Thread {
		
		private static final BlockingQueue<WorldData> WORLDDATA = new ArrayBlockingQueue<WorldData>( 5, true );
			
		public static void putWorldDatainSendingQueue( WorldData aWorldData ){
		
			ScenarioCore.getLogger().trace( "Putting worldata in to-botai queue: {}", aWorldData.toString() );
			WORLDDATA.offer( aWorldData );
			
		}
		
		private AtomicBoolean mManageMessagesToBotAIs = new AtomicBoolean( false );
		
		public ToBotAIs() {}
		
		public void close(){
			
			stopManagement();
			
		}

		public boolean startManagement() {
			
			ScenarioCore.getLogger().debug( "Starting sending worlddata to botAIs" );
			
			if( !isAlive() ) {
				
				super.start();
				mManageMessagesToBotAIs.set( true);
				ScenarioCore.getLogger().info( "Started sending worlddata to botAIs" );
	            
			} else {
			    
				ScenarioCore.getLogger().debug( "Worlddata is already being sent to botAIs" );
				
			}
			
			return mManageMessagesToBotAIs.get();
			
		}
		
		@Override
		public void start(){
			
			this.startManagement();
			
		}
		
		public void stopManagement(){
			 
		    mManageMessagesToBotAIs.set( false );
		    
		    if( isAlive()){
		      
	            while( isAlive() ){ 
	            	
	                try {
	                	
	                    Thread.sleep( 10 );
	                    
	                } catch ( InterruptedException vInterruptedException ) {
	                	
	                	ScenarioCore.getLogger().error( "Error stopping to-botAIs: {}", vInterruptedException.getLocalizedMessage() );
	                	ScenarioCore.getLogger().catching( Level.ERROR, vInterruptedException );
	                    
	                } 
	            }
	            
	            WORLDDATA.clear();
	            
		    }
		    
		    ScenarioCore.getLogger().info( "To-botAIs stopped." );
		    		
		}
		
		@Override
		public void run(){
			WorldData vWorldData;
			BotAI vFoundBotAI;
			
			while( mManageMessagesToBotAIs.get() ){
	            
				try {

					vWorldData = WORLDDATA.poll( 100, TimeUnit.MILLISECONDS );
					if( vWorldData != null ){
						
						for( Player vFoundRealBot : vWorldData.getListOfPlayers() ){
							
							vFoundBotAI = ScenarioCore.getInstance().getBotAIs().get( vFoundRealBot.getId() );
							
							if( vFoundBotAI != null ){
								
								vFoundBotAI.setWorldDataToSend( new ClientWorldData( vWorldData, vFoundRealBot ) );
																
							}
							
						}
								
						for( BotAI vBotAI : ScenarioCore.getInstance().getBotAIs().values() ){
							
							vBotAI.sendWorldData( vWorldData );
							
						}
						
					}
					
				} catch ( Exception vException ) {
					
					ScenarioCore.getLogger().error( "Error sending to botAIs: {}", vException.getLocalizedMessage() );
					ScenarioCore.getLogger().catching( Level.ERROR, vException );
	                				
				}
				
			}
			
		}

}

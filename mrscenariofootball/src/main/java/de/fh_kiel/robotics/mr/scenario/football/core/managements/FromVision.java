package de.fh_kiel.robotics.mr.scenario.football.core.managements;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.Level;

import de.fh_kiel.robotics.mr.scenario.football.core.ScenarioCore;
import de.fh_kiel.robotics.mr.scenario.football.core.data.ScenarioInformation;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.Player;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.WorldData;
import de.fh_kiel.robotics.mr.scenario.football.game.Core;
import de.fh_kiel.robotics.mr.server.misc.network.data.position.PositionDataPackage;
import de.fh_kiel.robotics.mr.server.misc.network.data.position.PositionObject;
import de.fh_kiel.robotics.mr.server.misc.network.data.position.PositionObjectBot;

public class FromVision extends Thread {
	
	private static final BlockingQueue<PositionDataPackage> NEWPOSITIONDATA = new ArrayBlockingQueue<PositionDataPackage>( 5, true );
		
	public static boolean putPositionDatPackageInProcessingQueue( PositionDataPackage aPositionData ){
	
		ScenarioCore.getLogger().trace( "Putting positiondata in queue: " + aPositionData.toString() + " " );
		return NEWPOSITIONDATA.offer( aPositionData );
		
	}
	private AtomicBoolean mManagePositionDataFromVision = new AtomicBoolean( false );
	
	private FromVision( ) {}
	private static FromVision INSTANCE;
    
    public static FromVision getInstance() {
        
        if( FromVision.INSTANCE == null){
        	ScenarioCore.getLogger().debug( "Creating FromVision-instance." );
        	FromVision.INSTANCE = new FromVision();
        }

        ScenarioCore.getLogger().trace( "Retrieving FromVision-instance." );
        return FromVision.INSTANCE;
        
    }
    
	public void close(){
		
		stopManagement();
		
	}

	public boolean startManagement() {
		
		ScenarioCore.getLogger().debug( "Starting process packets from vision" );
		
		if( !isAlive() ) {
			
			super.start();
			mManagePositionDataFromVision.set( true);
			ScenarioCore.getLogger().info( "Started processing packets from vision" );
            
		} else {
		    
			ScenarioCore.getLogger().debug( "Packets from vision are already processed" );
			
		}
		
		return mManagePositionDataFromVision.get();
		
	}
	
	@Override
	public void start(){
		
		this.startManagement();
		
	}
	
	public void stopManagement(){
		 
	    mManagePositionDataFromVision.set( false );
	    
	    if( isAlive() ){
	      
            while( isAlive() ){ 
            	
                try {
                	
                    Thread.sleep( 10 );
                    
                } catch ( InterruptedException vInterruptedException ) {
                	
                	ScenarioCore.getLogger().error( "Error stopping fromvisionmanagement: " + vInterruptedException.getLocalizedMessage() );
                	ScenarioCore.getLogger().catching( Level.ERROR, vInterruptedException );
                    
                } 
            }
            
	    }
	    
	    ScenarioCore.getLogger().info( "Fromvisionmanagement stopped." );
	    		
	}
	
	@Override
	public void run(){

		PositionDataPackage vPositionData;
		
		while( mManagePositionDataFromVision.get() ){
              
			try {

				vPositionData = NEWPOSITIONDATA.poll( 100, TimeUnit.MILLISECONDS );
				ScenarioCore.getLogger().trace( "New positiondata {}", vPositionData );
				if( vPositionData != null && !Core.getInstance().isSimulation() ){
					
					if( vPositionData.mListOfObjects != null ){
						
						for( PositionObject vObject : vPositionData.mListOfObjects ){
							
							if( vObject instanceof PositionObjectBot ){
							
								ScenarioInformation.getInstance().getWorldData().addPlayer( vObject.getId(), new Player( ( PositionObjectBot ) vObject , ScenarioInformation.getInstance().getBotAIs().get( vObject.getId() ) ) );
								
							}
							
						}
						
					}
					
					
				}
								
			} catch ( Exception vException ) {
				
				ScenarioCore.getLogger().error( "Error processing object from vision: {}", vException.getLocalizedMessage() );
				ScenarioCore.getLogger().catching( Level.ERROR, vException );
                				
			}
			
		}
		
	}
	
}

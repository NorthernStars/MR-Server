package de.fh_kiel.robotics.mr.server.core.scenario;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fh_kiel.robotics.mr.server.core.Core;
import de.fh_kiel.robotics.mr.server.core.botai.data.BotAI;
import de.fh_kiel.robotics.mr.server.core.botcontrol.BotControlManagement;
import de.fh_kiel.robotics.mr.server.core.graphics.GraphicsManagement;
import de.fh_kiel.robotics.mr.server.gui.interfaces.SceanarioManagementChangeListener;
import de.fh_kiel.robotics.mr.server.misc.botcontrol.interfaces.BotControl;
import de.fh_kiel.robotics.mr.server.misc.bots.interfaces.Bot;
import de.fh_kiel.robotics.mr.server.misc.graphics.interfaces.Graphics;
import de.fh_kiel.robotics.mr.server.misc.network.data.position.PositionDataPackage;
import de.fh_kiel.robotics.mr.server.misc.scenario.interfaces.Scenario;

/**
 * Managed das Szenario des Servers
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 */
public class ScenarioManagement {
	
    private static ScenarioManagement INSTANCE;

    private ScenarioManagement(){
    	
    }
    
    public static ScenarioManagement getInstance() {
        
        if( ScenarioManagement.INSTANCE == null){
        	ScenarioManagement.getLogger().debug( "Creating ScenarioManagement-instance." );
            ScenarioManagement.INSTANCE = new ScenarioManagement();
        }

        ScenarioManagement.getLogger().trace( "Retrieving ScenarioManagement-instance." );
        return ScenarioManagement.INSTANCE;
        
    }
    
    private static Logger SCENARIOMANAGEMENTLOGGER = LogManager.getLogger("SCENARIOMANAGEMENT");
    
    public static Logger getLogger(){
        
        return SCENARIOMANAGEMENTLOGGER;
        
    }

	private Scenario mScenario;
	private SceanarioManagementChangeListener mListener = null;
    
    
    public void close(){
    	
    	if( mScenario != null ){
    		mScenario.close();
    		mScenario = null;
    	}
    	ScenarioManagement.getLogger().info( "Closed scenariomangement" );
    	INSTANCE = null;
    	
    }
    
	/**
	 * Lädt ein Szenario
	 * 
	 * @return true wenn das Laden erfolgreich war, false wenn nicht
	 */
	public boolean loadScenario() {

        try {

        	ScenarioManagement.getLogger().info( "Loading scenario " + Core.getInstance().getServerConfig().getScenarioClass() + " from " + Core.getInstance().getServerConfig().getScenarioLibrary() );
            URL vUniformResourceLocator = new File( Core.getInstance().getServerConfig().getScenarioLibrary() ).toURI().toURL();
            @SuppressWarnings("resource")
			URLClassLoader vClassloader = new URLClassLoader( new URL[]{ vUniformResourceLocator } );
            synchronized (this) {
                
            	mScenario = (Scenario) vClassloader.loadClass( Core.getInstance().getServerConfig().getScenarioClass() ).newInstance();
                
            }
            ScenarioManagement.getLogger().info( "Loaded scenario " + Core.getInstance().getServerConfig().getScenarioClass() + " from " + Core.getInstance().getServerConfig().getScenarioLibrary() );
            registerGraphics( GraphicsManagement.getInstance() );
            registerBotControl( BotControlManagement.getInstance() );
			mScenario.loadScenario();
            
            
        } catch ( Exception vException ) {
            
        	ScenarioManagement.getLogger().error( "Error loading AI " + Core.getInstance().getServerConfig().getScenarioClass() + " from " + Core.getInstance().getServerConfig().getScenarioLibrary() + " " + vException.getLocalizedMessage() );
        	ScenarioManagement.getLogger().catching( Level.ERROR, vException );
            
            return false;
            
        }
        
        if( mListener != null ){
        	mListener.newScenarioLoaded();
        }
        
        return true;
        
    }
	
	/**
	 * Schließt und verwirft das momentane Scenario
	 * 
	 */
	public void disposeScenario(){
		
		if( mScenario != null ){
			
			mScenario.close();
			mScenario = null;
			
		}
		
	}
	
	public boolean needsVision(){

		if( mScenario != null ){
			
			return mScenario.needVision();
			
		}
		
		return false;
		
	}
	
	public boolean needsBotControl(){

		if( mScenario != null ){
			
			return mScenario.needBotControl();
			
		}
		
		return false;
		
	}
    
	public boolean putPositionData( PositionDataPackage aPositionDataPackage ){

		if( mScenario != null ){
			
			return mScenario.putPositionData( aPositionDataPackage );
			
		}
		
		return false;
		
	}
    
	public boolean registerBotControl( BotControl aBotControl ){

		if( mScenario != null ){
			
			return mScenario.registerBotControl( aBotControl );
			
		}
		
		return false;
		
	}
    
	public boolean registerNewBot( Bot aBot ){

		if( mScenario != null ){
			
			return mScenario.registerNewBot( aBot );
			
		}
		
		return false;
		
	}
    
	public boolean registerGraphics( Graphics aGraphics ){

		if( mScenario != null ){
			
			return mScenario.registerGraphics( aGraphics );
			
		}
		
		return false;
		
	}

	public void unregisterBot(BotAI aAI) {

		if( mScenario != null ){
			
			mScenario.unregisterBot( aAI );
			
		}
		
	}

	public JPanel getScenarioGUI() {

		if( mScenario != null ){
			
			return mScenario.getScenarioGUI();
			
		}
		return null;
		
	}
	
	public JFrame getScenarioOptionsGUI(){
		
		if( mScenario != null ){
			return mScenario.getScenarioOptionsGUI();
		}
			
		return null;
	}
	
	public void registerListener( SceanarioManagementChangeListener aListener ){
		ScenarioManagement.getLogger().debug( "Registering Listener {}", aListener );
		mListener = aListener;
	}

}

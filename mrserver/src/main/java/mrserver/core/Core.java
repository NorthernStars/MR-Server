package mrserver.core;

import mrserver.core.botai.BotAIManagement;
import mrserver.core.botcontrol.BotControlManagement;
import mrserver.core.config.ServerConfig;
import mrserver.core.config.commandline.CommandLineOptions;
import mrserver.core.graphics.GraphicsManagement;
import mrserver.core.scenario.ScenarioManagement;
import mrserver.core.vision.VisionManagement;
import mrserver.gui.Main;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Bildet das Herzstueck des MixedRealityServers. Hier werden alle Metaprozesse und Threads verwaltet
 * und gesteuert.
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */

public class Core {
    
    private static Core INSTANCE;
    
    private Core(){
    	
    }

    public static Core getInstance() {
        
        if( Core.INSTANCE == null){
            Core.getLogger().debug( "Creating Core-instance." );
            Core.INSTANCE = new Core();
        }

        Core.getLogger().trace( "Retrieving Core-instance." );
        return Core.INSTANCE;
        
    }
    
    private static Logger SERVERCORELOGGER = LogManager.getLogger("SERVERCORE");
    
    public static Logger getLogger(){
        
        return SERVERCORELOGGER;
        
    }
    
    public void close() {

        if( INSTANCE != null ){
        	
        	Core.getLogger().info( "Closing the server" );
        	ScenarioManagement.getInstance().close();
        	VisionManagement.getInstance().close();
        	BotControlManagement.getInstance().close();
        	GraphicsManagement.getInstance().close();
        	Core.getLogger().info( "Server closed" );
            INSTANCE = null;
            
        }
        
    }
    
    /**
     * Speichert die allgemeinen Daten des Servers
     * 
     */
    private ServerConfig mServerConfig;
    
    /**
     * Initialisiert die Grundfunktionen des Bots. 
     * 
     * @since 0.1
     * 
     * @param aCommandline die Commandline als Stringarray
     */
    public void startServer( String[] aCommandline ) {

        try {
            
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    
                    close();
                    
                }
                
            });
            
            CommandLineOptions.getInstance().parseCommandLineArguments( aCommandline );
           
            Main.startGUI();
            
            //TODO: operatormanagement starten <- jmd weiss was ich damit meinte?
            if( getServerConfig().autoOpenBotPorts() ){
            	BotAIManagement.getInstance().autostartBotAiPorts();
            }
            if( getServerConfig().getGraphicsPort() != -1){
            	GraphicsManagement.getInstance().startGraphicsManagement();
            }
            if( getServerConfig().sceanarioAutoLoad() && !getServerConfig().getScenarioLibrary().isEmpty() && !getServerConfig().getScenarioClass().isEmpty() ){
            	ScenarioManagement.getInstance().loadScenario();
            }
            
        } catch ( Exception vException ) {

            Core.getLogger().error( "Fehler beim initialisiern der Grundfunktionen: {}", vException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vException );
            
            System.exit( 1 );
            
        }
        
    }

	public ServerConfig getServerConfig() {
		
		if ( mServerConfig == null ){
			
			Core.getLogger().trace( "Initalizing serverconfig" );
			mServerConfig = new ServerConfig(); 
			
		}
		
		Core.getLogger().trace( "Getting serverconfig {}", mServerConfig );
		return mServerConfig;
		
	}

	public void setServerConfig( ServerConfig aServerConfig) {
		mServerConfig = aServerConfig;
	}

}

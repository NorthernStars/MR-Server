package mrserver.core;

import java.util.ArrayList;

import mrserver.core.botcontrol.BotControlManagement;
import mrserver.core.config.ServerConfig;
import mrserver.core.config.commandline.CommandLineOptions;
import mrserver.core.graphics.GraphicsManagement;
import mrserver.core.scenario.ScenarioManagement;
import mrserver.core.vision.VisionManagement;
import mrservermisc.network.data.position.PositionDataPackage;
import mrservermisc.network.data.position.PositionObject;
import mrservermisc.network.data.position.PositionObjectBot;
import mrservermisc.network.data.position.PositionObjectRectangle;
import mrservermisc.network.data.position.PositionObjectType;
import mrservermisc.network.data.position.VisionMode;
import mrservermisc.network.handshake.ConnectionAcknowlege;
import mrservermisc.network.handshake.ConnectionRequest;
import mrservermisc.network.xml.Helpers;

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
            
            Helpers.createWorldDataSchema();
            
            CommandLineOptions.getInstance().parseCommandLineArguments( aCommandline );
            //TODO: operatormanagement starten
            ScenarioManagement.getInstance().loadScenario();
            if ( ScenarioManagement.getInstance().needsVision() ) {

            	VisionManagement.getInstance().connectToVision();
            	ScenarioManagement.getInstance().registerVision( VisionManagement.getInstance() );
            	VisionManagement.getInstance().startRecievingPackets();
            	          	
            }
            if ( ScenarioManagement.getInstance().needsBotControl() ) {
            	//TODO: Botcontrol connect und register
            }
            
            GraphicsManagement.getInstance().startGraphicsManagement();
            ScenarioManagement.getInstance().registerGraphics( GraphicsManagement.getInstance() );

            //TODO: Vision kalibrieren
            //TODO: Botports oeffnen 
            //TODO: Szenario starten
            ScenarioManagement.getInstance().startScenario();
            
        } catch ( Exception vException ) {

            Core.getLogger().error( "Fehler beim initialisiern der Grundfunktionen: " + vException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vException );
            
            System.exit( 1 );
            
        }
        
    }

	public ServerConfig getServerConfig() {
		
		if ( mServerConfig == null ){
			
			Core.getLogger().trace( "Initalizing serverconfig" );
			mServerConfig = new ServerConfig(); 
			
		}
		
		Core.getLogger().trace( "Getting serverconfig " + mServerConfig );
		return mServerConfig;
		
	}

	public void setServerConfig( ServerConfig aServerConfig) {
		mServerConfig = aServerConfig;
	}

}

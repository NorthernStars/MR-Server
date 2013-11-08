package mrserver.core;

import java.util.ArrayList;

import mrserver.core.botcontrol.BotControlManagement;
import mrserver.core.config.ServerConfig;
import mrserver.core.config.commandline.CommandLineOptions;
import mrserver.core.scenario.ScenarioManagement;
import mrserver.core.vision.VisionManagement;
import mrserver.core.vision.VisionMode;
import mrserver.core.vision.Data.VisionDataPackage;
import mrserver.core.vision.Data.VisionObject;
import mrserver.core.vision.Data.VisionObjectBot;
import mrserver.core.vision.Data.VisionObjectRectangle;
import mrserver.core.vision.Data.VisionObjectType;

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
        	
        	ScenarioManagement.getInstance().close();
        	VisionManagement.getInstance().close();
        	BotControlManagement.getInstance().close();
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
            
            VisionDataPackage aTestPackage = new VisionDataPackage();
            
            aTestPackage.mVisionMode = VisionMode.VISION_MODE_STREAM_ALL;
            aTestPackage.mListOfVisionObjects = new ArrayList<VisionObject>();
            aTestPackage.mListOfVisionObjects.add( new VisionObjectBot( VisionObjectType.BOT, 0, "Braten", new double[]{2.2,3.3}, new double[]{2.2,3.3,4.4,5.5}, 0.78 ) );
            aTestPackage.mListOfVisionObjects.add( new VisionObjectRectangle( VisionObjectType.RECTANGLE, 0, "Braten", new double[]{2.2,3.3}, new double[]{2.2,3.3,4.4,5.5}, 0.78, new double[]{100.0, 2222.2} ) );

            Core.getLogger().debug( aTestPackage.toString() );
            
            aTestPackage = VisionDataPackage.unmarshallXMLVisionDataPackageString( aTestPackage.toXMLString() );
            
            Core.getLogger().debug( aTestPackage.toString() );
            
            CommandLineOptions.getInstance().parseCommandLineArguments( aCommandline );
            //TODO: operatormanagement starten
            ScenarioManagement.getInstance().loadScenario();
            if ( true || ScenarioManagement.getInstance().needsVision() ) {
            	//TODO: Vision connect und register
            	VisionManagement.getInstance().connectToVision();
            	ScenarioManagement.getInstance().registerVision( VisionManagement.getInstance() );
            	            	
            }
            if ( ScenarioManagement.getInstance().needsBotControl() ) {
            	//TODO: Botcontrol connect und register
            }
            //TODO: Graphicsport open
            //TODO: Vision kalibrieren
            //TODO: Botports oeffnen 
            //TODO: Szenario starten                 
            
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

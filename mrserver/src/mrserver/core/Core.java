package mrserver.core;

import mrserver.core.config.file.ConfigFileReader;

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

    public static Core getInstance() {
        
        if( Core.INSTANCE == null){
            Core.getLogger().trace( "Creating Core-instance." );
            Core.INSTANCE = new Core();
        }

        Core.getLogger().trace( "Retrieving Core-instance." );
        return Core.INSTANCE;
        
    }
    
    private static Logger BOTCORELOGGER = LogManager.getLogger("CORE");
    
    public static Logger getLogger(){
        
        return BOTCORELOGGER;
        
    }
    
    public void close() {

        if( INSTANCE != null ){

        }
        
    }
    
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
            
            Core.getLogger().info( ConfigFileReader.readConfigFile( "defaultserver.config" ) ); 
                        
            
            
        } catch ( Exception vException ) {

            Core.getLogger().error( "Fehler beim initialisiern der Grundfunktionen: " + vException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vException );
            vException.printStackTrace();
            
        }
        
    }

}

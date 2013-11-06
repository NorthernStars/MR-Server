package mrserver.core.config.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.logging.log4j.Level;

import mrserver.core.Core;
import mrserver.core.config.ServerConfig;
//TODO: besser machen
/**
 * Parsed eine Konfigurationsdatei
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 */
public class ConfigFileReader {
	
	/**
	 * Liest eine Konfigurationsdatei ein und gibt sie als ServerConfig-Objekt zurueck
	 * 
	 * @param aConfigFile der Namen der Konfigurationsdatei im Klassenpfad
	 * @return
	 */
	public static void readConfigFile( String aConfigFile ){

        Core.getLogger().debug( "Reading configfile " + aConfigFile );
		
		Properties vProperties = new Properties();
		 
    	try {

    		vProperties.load( new FileInputStream( aConfigFile ) );
    		 
    	} catch ( IOException vIOException ) {

            Core.getLogger().error( "Error reading configfile " + vIOException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vIOException );
            
        }
    	
    	if( vProperties.getProperty( "servername" ) != null && Core.getInstance().getServerConfig().getServerName().isEmpty() ){
    		
    		Core.getInstance().getServerConfig().setServerName( vProperties.getProperty( "servername" ) );
    		
    	}
    	
    	if( vProperties.getProperty( "scenariolibrary" ) != null && Core.getInstance().getServerConfig().getScenarioLibrary().isEmpty() ){
    		
    		Core.getInstance().getServerConfig().setScenarioLibrary( vProperties.getProperty( "scenariolibrary" ) );
    		
    	}
    	
    	if( vProperties.getProperty( "scenarioclass" ) != null && Core.getInstance().getServerConfig().getScenarioClass().isEmpty() ){
    		
    		Core.getInstance().getServerConfig().setScenarioClass( vProperties.getProperty( "scenarioclass" ) );
    		
    	}
    	
    	if( vProperties.getProperty( "scenarioconfigcmdline" ) != null && Core.getInstance().getServerConfig().getScenarioConfigCmdLine().isEmpty() ){
    		
    		Core.getInstance().getServerConfig().setScenarioConfigCmdLine( vProperties.getProperty( "scenarioconfigcmdline" ) );
    		
    	}
    	
    	if( vProperties.getProperty( "scenarioconfigfile" ) != null && Core.getInstance().getServerConfig().getScenarioConfigFile().isEmpty() ){
    		
    		Core.getInstance().getServerConfig().setScenarioConfigFile( vProperties.getProperty( "scenarioconfigfile" ) );
    		
    	}
    	
    	if( vProperties.getProperty( "visionipaddress" ) != null && Core.getInstance().getServerConfig().getVisionIPAdress().isLoopbackAddress() ){

    		Core.getInstance().getServerConfig().setVisionIPAdress( vProperties.getProperty( "visionipaddress" ) );
			
    	} 
    	
    	if( vProperties.getProperty( "visionport" ) != null && Core.getInstance().getServerConfig().getVisionPort() == -1 ){
    		
    		Core.getInstance().getServerConfig().setVisionPort( Integer.parseInt( vProperties.getProperty( "visionport" ) ) );
    		
    	}
    	
    	if( vProperties.getProperty( "botcontrolipaddress" ) != null && Core.getInstance().getServerConfig().getBotControlIPAdress().isLoopbackAddress() ){

    		Core.getInstance().getServerConfig().setBotControlIPAdress( vProperties.getProperty( "botcontrolipaddress" ) );

    	}
    	
    	if( vProperties.getProperty( "botcontrolport" ) != null && Core.getInstance().getServerConfig().getBotControlPort() == -1  ){
    		
    		Core.getInstance().getServerConfig().setBotControlPort( Integer.parseInt( vProperties.getProperty( "botcontrolport" ) ) );
    		
    	}
    	
    	if( vProperties.getProperty( "graphicsport" ) != null && Core.getInstance().getServerConfig().getGraphicsPort() == -1 ){

    		Core.getInstance().getServerConfig().setGraphicsPort( Integer.parseInt( vProperties.getProperty( "botcontrolport" ) ) );
    		
    	}
    	
    	if( vProperties.getProperty( "botports" ) != null && Core.getInstance().getServerConfig().getBotPorts().isEmpty() ){
		
			for(String vPort : vProperties.getProperty( "botports" ).split(" ")){

				Core.getInstance().getServerConfig().addBotPort(  Integer.parseInt( vPort )  );
				 
			}
    	}
		
	}
	

}

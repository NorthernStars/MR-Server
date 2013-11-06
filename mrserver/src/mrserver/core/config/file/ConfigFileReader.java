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
    	
    	if( vProperties.getProperty( "servername" ) != null ){
    		
    		Core.getInstance().getServerConfig().setServerName( vProperties.getProperty( "servername" ) );
    		
    	}
    	
    	if( vProperties.getProperty( "scenariolibrary" ) != null ){
    		
    		Core.getInstance().getServerConfig().setScenarioLibrary( vProperties.getProperty( "scenariolibrary" ) );
    		
    	}
    	
    	if( vProperties.getProperty( "scenarioclass" ) != null ){
    		
    		Core.getInstance().getServerConfig().setScenarioClass( vProperties.getProperty( "scenarioclass" ) );
    		
    	}
    	
    	if( vProperties.getProperty( "scenarioconfigcmdline" ) != null ){
    		
    		Core.getInstance().getServerConfig().setScenarioConfigCmdLine( vProperties.getProperty( "scenarioconfigcmdline" ) );
    		
    	}
    	
    	if( vProperties.getProperty( "scenarioconfigfile" ) != null ){
    		
    		Core.getInstance().getServerConfig().setScenarioConfigFile( vProperties.getProperty( "scenarioconfigfile" ) );
    		
    	}
    	
    	if( vProperties.getProperty( "visionipaddress" ) != null ){
    		
    		try {
    			
    			Core.getInstance().getServerConfig().setVisionIPAdress( InetAddress.getByName( vProperties.getProperty( "visionipaddress" ) ) );
				
			} catch ( UnknownHostException vUnknownHostException ) {

	            Core.getLogger().error( "Error reading configfile property visionipaddress " + vUnknownHostException.getLocalizedMessage() );
	            Core.getLogger().catching( Level.ERROR, vUnknownHostException );
			}
    		
    	} 
    	
    	if( vProperties.getProperty( "visionport" ) != null ){
    		
    		Core.getInstance().getServerConfig().setVisionPort( Integer.parseInt( vProperties.getProperty( "visionport" ) ) );
    		
    	}
    	
    	if( vProperties.getProperty( "botcontrolipaddress" ) != null ){

    		try {
    			
    			Core.getInstance().getServerConfig().setBotControlIPAdress( InetAddress.getByName( vProperties.getProperty( "botcontrolipaddress" ) ) );
				
			} catch ( UnknownHostException vUnknownHostException ) {

	            Core.getLogger().error( "Error reading configfile property botcontrolipaddress " + vUnknownHostException.getLocalizedMessage() );
	            Core.getLogger().catching( Level.ERROR, vUnknownHostException );
			}
    		
    	}
    	
    	if( vProperties.getProperty( "botcontrolport" ) != null ){
    		
    		Core.getInstance().getServerConfig().setBotControlPort( Integer.parseInt( vProperties.getProperty( "botcontrolport" ) ) );
    		
    	}
    	
    	if( vProperties.getProperty( "graphicsport" ) != null ){

    		Core.getInstance().getServerConfig().setGraphicsPort( Integer.parseInt( vProperties.getProperty( "botcontrolport" ) ) );
    		
    	}
    	
    	if( vProperties.getProperty( "botports" ) != null && !Core.getInstance().getServerConfig().getBotPorts().isEmpty() ){
		
			for(String vPort : vProperties.getProperty( "botports" ).split(" ")){

				Core.getInstance().getServerConfig().addBotPort(  Integer.parseInt( vPort )  );
				 
			}
    	}
		
	}
	

}

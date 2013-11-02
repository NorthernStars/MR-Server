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

public class ConfigFileReader {
	
	/**
	 * Liest eine Konfigurationsdatei ein und gibt sie als ServerConfig-Objekt zurueck
	 * 
	 * @param aConfigFile der Namen der Konfigurationsdatei im Klassenpfad
	 * @return
	 */
	public static ServerConfig readConfigFile( String aConfigFile ){

        Core.getLogger().debug( "Reading configfile " + aConfigFile );
		
		ServerConfig vServerConfig = new ServerConfig();
		
		Properties vProperties = new Properties();
		 
    	try {

    		vProperties.load( new FileInputStream( aConfigFile ) );
    		 
    	} catch ( IOException vIOException ) {

            Core.getLogger().error( "Error reading configfile " + vIOException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vIOException );
            
        }
    	
    	if( vProperties.getProperty( "servername" ) != null ){
    		
    		vServerConfig.setServerName( vProperties.getProperty( "servername" ) );
    		
    	} else {
    		
    		vServerConfig.setServerName( "Server " + ManagementFactory.getRuntimeMXBean().getName() );
    		
    	}
    	
    	if( vProperties.getProperty( "scenariolibrary" ) != null ){
    		
    		vServerConfig.setScenarioLibrary( vProperties.getProperty( "scenariolibrary" ) );
    		
    	} else {
    		
    		vServerConfig.setScenarioLibrary( "" );
    		
    	}
    	
    	if( vProperties.getProperty( "scenarioclass" ) != null ){
    		
    		vServerConfig.setScenarioClass( vProperties.getProperty( "scenarioclass" ) );
    		
    	} else {
    		
    		vServerConfig.setScenarioClass( "" );
    		
    	}
    	
    	if( vProperties.getProperty( "scenarioconfigcmdline" ) != null ){
    		
    		vServerConfig.setScenarioConfigCmdLine( vProperties.getProperty( "scenarioconfigcmdline" ) );
    		
    	} else {
    		
    		vServerConfig.setScenarioConfigCmdLine( "" );
    		
    	}
    	
    	if( vProperties.getProperty( "scenarioconfigfile" ) != null ){
    		
    		vServerConfig.setScenarioConfigFile( vProperties.getProperty( "scenarioconfigfile" ) );
    		
    	} else {
    		
    		vServerConfig.setScenarioConfigFile( "" );
    		
    	}
    	
    	if( vProperties.getProperty( "visionipaddress" ) != null ){
    		
    		try {
    			
				vServerConfig.setVisionIPAdress( InetAddress.getByName( vProperties.getProperty( "visionipaddress" ) ) );
				
			} catch ( UnknownHostException vUnknownHostException ) {

	            Core.getLogger().error( "Error reading configfile property visionipaddress " + vUnknownHostException.getLocalizedMessage() );
	            Core.getLogger().catching( Level.ERROR, vUnknownHostException );
			}
    		
    	} 
    	
    	if( vProperties.getProperty( "visionport" ) != null ){
    		
    		vServerConfig.setVisionPort( Integer.parseInt( vProperties.getProperty( "visionport" ) ) );
    		
    	} else {
    		
    		vServerConfig.setVisionPort( -1 );
    		
    	}
    	
    	if( vProperties.getProperty( "botcontrolipaddress" ) != null ){

    		try {
    			
				vServerConfig.setBotControlIPAdress( InetAddress.getByName( vProperties.getProperty( "botcontrolipaddress" ) ) );
				
			} catch ( UnknownHostException vUnknownHostException ) {

	            Core.getLogger().error( "Error reading configfile property botcontrolipaddress " + vUnknownHostException.getLocalizedMessage() );
	            Core.getLogger().catching( Level.ERROR, vUnknownHostException );
			}
    		
    	}
    	
    	if( vProperties.getProperty( "botcontrolport" ) != null ){
    		
    		vServerConfig.setBotControlPort( Integer.parseInt( vProperties.getProperty( "botcontrolport" ) ) );
    		
    	} else {
    		
    		vServerConfig.setBotControlPort( -1 );
    		
    	}
    	
    	if( vProperties.getProperty( "graphicsport" ) != null ){

    		vServerConfig.setGraphicsPort( Integer.parseInt( vProperties.getProperty( "botcontrolport" ) ) );
    		
    	} else {

    		vServerConfig.setGraphicsPort( -1 );
    		
    	}
    	
    	if( vProperties.getProperty( "botports" ) != null ){
		
			for(String vPort : vProperties.getProperty( "botports" ).split(" ")){

				vServerConfig.addBotPort(  Integer.parseInt( vPort )  );
				 
			}
    	}
    	
		return vServerConfig;
		
	}
	

}

package de.fh_kiel.robotics.mr.server;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import de.fh_kiel.robotics.mr.server.core.Core;

public class Main {
    
	
	/**
	 * Einstiegspunkt des Servers 
	 * 
	 * 
	 * @param aCommandline die Kommandozeilenargumente des Servers
	 * 
	 */
    public static void main( String[] aCommandline ) {
        
        System.setProperty( "Server", new SimpleDateFormat( "yyyyMMdd-HHmmss" ).format( new java.util.Date() ) + "_(" + ManagementFactory.getRuntimeMXBean().getName() + ")" );
        Core.getLogger().info( "Starting server (" + ManagementFactory.getRuntimeMXBean().getName() + ")" );
        Core.getLogger().trace( "Parameters: " + Arrays.toString( aCommandline ) );
        
        {
        	boolean vHasConfigFileArgument = false;
	        for(String vArgument : aCommandline ){
	        	if(vArgument.equalsIgnoreCase("-cf")){
	        		vHasConfigFileArgument = true;
	        		break;
	        	}
	        }
	        
	        if( !vHasConfigFileArgument && System.getProperty("MRSERVERCONFIGFILE") != null ){
	        	List<String> vArguments = new ArrayList<String>(Arrays.asList(aCommandline));
	        	vArguments.add("-cf");
	        	vArguments.add(System.getProperty("MRSERVERCONFIGFILE"));
	        	aCommandline = vArguments.toArray(aCommandline);
	        }
        }
        
                
        Core vServer;
        
        try {
            
            vServer = Core.getInstance();
            
            vServer.startServer( aCommandline );
            
        } catch ( Exception vException ) {

            Core.getLogger().fatal( "Fatal error! Server terminates " + vException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vException );
            
        }
       
    }
   
}

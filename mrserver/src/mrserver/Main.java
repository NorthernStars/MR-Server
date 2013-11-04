package mrserver;

import java.lang.management.ManagementFactory;

import mrserver.core.Core;

import org.apache.logging.log4j.Level;

public class Main {
    
	
	/**
	 * Einstiegspunkt des Servers
	 * 
	 * 
	 * @param aCommandline die Kommandozeilenargumente des Servers
	 * 
	 */
    public static void main( String[] aCommandline ) {
        
        System.setProperty("Server", ManagementFactory.getRuntimeMXBean().getName() + "" );
        Core.getLogger().info("Starting server (" + ManagementFactory.getRuntimeMXBean().getName() + ")" );
        
        {
            String vParameters = "";
            
            for ( String vParameter: aCommandline) {
                
                vParameters += vParameter + " ";
                
            }
            
            Core.getLogger().info("Parameters: " + vParameters);
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
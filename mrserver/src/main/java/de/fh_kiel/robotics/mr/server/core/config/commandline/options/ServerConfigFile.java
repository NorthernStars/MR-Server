package de.fh_kiel.robotics.mr.server.core.config.commandline.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import de.fh_kiel.robotics.mr.server.core.config.commandline.CommandLineOptions;
import de.fh_kiel.robotics.mr.server.core.config.commandline.options.parse.ParseOption;
import de.fh_kiel.robotics.mr.server.core.config.file.ConfigFileReader;

/**
 * Option für den Namen und Lagepfad der Konfigurationsdatei des Servers
 * 
 * @author Eike Petersen
 * @version 0.1
 * @since 0.1
 */
@SuppressWarnings("serial")
public class ServerConfigFile extends Option implements ParseOption {

	private final static String mOption = "cf";
	private final static String mLongOption = "configfile";
	private final static String mDiscription = "The name and path of the serverconfigfile\nCan also be set through JVM -D argument \"MRSERVERCONFIGFILE\". This is ignored when -cf is set on the commandline.";
	
	private final static boolean mHasArgument = true;
	private final static String mArgumentName = "path/configfile";
	private final static int mNumberOfArguments = 1;
	
	public ServerConfigFile(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		setArgName( mArgumentName );
		setArgs( mNumberOfArguments );
		
	}
	@Override
	public boolean parse( CommandLine aCommandLine ) {

        CommandLineOptions.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        CommandLineOptions.getLogger().debug( "Reading " + mLongOption + " " + aCommandLine.getOptionValue( getOpt() ) );
			ConfigFileReader.readConfigFile( aCommandLine.getOptionValue( getOpt() ) );
            return true;
            
        }
		else{
			
            return false;
			
		}
//		Core.getInstance().setServerConfig( new ServerConfig() );
//		return false;
	}

}

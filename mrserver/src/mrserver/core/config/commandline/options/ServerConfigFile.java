package mrserver.core.config.commandline.options;

import mrserver.core.Core;
import mrserver.core.config.ServerConfig;
import mrserver.core.config.commandline.CommandLineOptions;
import mrserver.core.config.commandline.options.parse.ParseOption;
import mrserver.core.config.file.ConfigFileReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

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
	private final static String mDiscription = "The name and path of the serverconfigfile\n";
	
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
		Core.getInstance().setServerConfig( new ServerConfig() );
		return false;
	}

}

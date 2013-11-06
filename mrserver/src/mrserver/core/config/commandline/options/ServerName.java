package mrserver.core.config.commandline.options;

import mrserver.core.Core;
import mrserver.core.config.ServerConfig;
import mrserver.core.config.commandline.options.parse.ParseOption;
import mrserver.core.config.file.ConfigFileReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * Servernameoption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class ServerName extends Option implements ParseOption  {

	private final static String mOption = "sn";
	private final static String mLongOption = "servername";
	private final static String mDiscription = "Sets the name of the server\n";
	
	private final static boolean mHasArgument = true;
	private final static String mArgumentName = "servername";
	private final static int mNumberOfArguments = 1;
	
	public ServerName(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		setArgName( mArgumentName );
		setArgs( mNumberOfArguments );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        Core.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        Core.getLogger().debug( "Setting " + mLongOption + " " + aCommandLine.getOptionValue( getOpt() ) );
			Core.getInstance().getServerConfig().setServerName( aCommandLine.getOptionValue( getOpt() ) );;
            return true;
            
        }

		return false;
	}

}

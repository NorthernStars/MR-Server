package mrserver.core.config.commandline.options;

import java.util.Arrays;

import mrserver.core.Core;
import mrserver.core.config.ServerConfig;
import mrserver.core.config.commandline.options.parse.ParseOption;
import mrserver.core.config.file.ConfigFileReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * Botcontroloption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class BotControl extends Option implements ParseOption {

	private final static String mOption = "bc";
	private final static String mLongOption = "botcontrol";
	private final static String mDiscription = "Sets the botcontrol ip-address and port\n";
	
	private final static boolean mHasArgument = true;
	private final static String mArgumentName = "ip-address:port";
	private final static int mNumberOfArguments = 2;
	private final static char mArgumentSeperator = ':';
	
	public BotControl(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		setArgName( mArgumentName );
		setArgs( mNumberOfArguments );
		setValueSeparator( mArgumentSeperator );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        Core.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        Core.getLogger().debug( "Setting " + mLongOption + " " + Arrays.toString( aCommandLine.getOptionValues( getOpt() ) ) );
	        Core.getInstance().getServerConfig().setBotControlIPAdress( aCommandLine.getOptionValues( getOpt() )[0] );
	        Core.getInstance().getServerConfig().setBotControlPort( Integer.parseInt( aCommandLine.getOptionValues( getOpt() )[1] ) ); 
            return true;
            
        }
		return false;
		
	}

}

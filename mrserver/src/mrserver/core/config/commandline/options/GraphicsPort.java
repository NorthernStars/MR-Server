package mrserver.core.config.commandline.options;

import mrserver.core.Core;
import mrserver.core.config.commandline.CommandLineOptions;
import mrserver.core.config.commandline.options.parse.ParseOption;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * Graphicsportoption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class GraphicsPort extends Option implements ParseOption  {

	private final static String mOption = "gp";
	private final static String mLongOption = "graphicsport";
	private final static String mDiscription = "Sets the port the graphics module can connect to and automatically opens the port\n";
	
	private final static boolean mHasArgument = true;
	private final static boolean mArgumentIsOptional = false;
	private final static String mArgumentName = "port";
	private final static int mNumberOfArguments = 1;
	
	public GraphicsPort(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		setArgName( mArgumentName );
		setOptionalArg( mArgumentIsOptional );
		setArgs( mNumberOfArguments );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        CommandLineOptions.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        CommandLineOptions.getLogger().debug( "Setting " + mLongOption + " " + aCommandLine.getOptionValue( getOpt() ) );
	        Core.getInstance().getServerConfig().setGraphicsPort( Integer.parseInt( aCommandLine.getOptionValue( getOpt() ) ) ); 
            return true;
            
        }
		return false;
		
	}
	
}

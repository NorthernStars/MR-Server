package mrserver.core.config.commandline.options;

import mrserver.core.Core;
import mrserver.core.config.commandline.CommandLineOptions;
import mrserver.core.config.commandline.options.parse.ParseOption;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * Szenariokonfigurationskommandozeilenoption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class ScenarioConfigCmdLine extends Option implements ParseOption  {

	private final static String mOption = "scmd";
	private final static String mLongOption = "scenariocmdline";
	private final static String mDiscription = "Commandline options for the scenario\n"; //TODO: Beispiel
	
	private final static boolean mHasArgument = true;
	private final static String mArgumentName = "\"commandlineoptions\"";
	private final static int mNumberOfArguments = 1;
	
	public ScenarioConfigCmdLine(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		setArgName( mArgumentName );
		setArgs( mNumberOfArguments );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        CommandLineOptions.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        CommandLineOptions.getLogger().debug( "Setting " + mLongOption + " " + aCommandLine.getOptionValue( getOpt() ) );
	        Core.getInstance().getServerConfig().setScenarioConfigCmdLine( aCommandLine.getOptionValue( getOpt() ) ); 
            return true;
            
        }
		return false;
		
	}

}

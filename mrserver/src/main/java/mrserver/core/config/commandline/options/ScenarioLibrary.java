package mrserver.core.config.commandline.options;

import mrserver.core.Core;
import mrserver.core.config.commandline.CommandLineOptions;
import mrserver.core.config.commandline.options.parse.ParseOption;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * Szenariobibliotheksoption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class ScenarioLibrary extends Option implements ParseOption  {

	private final static String mOption = "sl";
	private final static String mLongOption = "scenariolibrary";
	private final static String mDiscription = "The library with the scenario\n"; //TODO: Beispiel
	
	private final static boolean mHasArgument = true;
	private final static String mArgumentName = "libraryname";
	private final static int mNumberOfArguments = 1;
	
	public ScenarioLibrary(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		setArgName( mArgumentName );
		setArgs( mNumberOfArguments );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        CommandLineOptions.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        CommandLineOptions.getLogger().debug( "Setting " + mLongOption + " " + aCommandLine.getOptionValue( getOpt() ) );
	        Core.getInstance().getServerConfig().setScenarioLibrary( aCommandLine.getOptionValue( getOpt() ) ); 
            return true;
            
        }
		return false;
		
	}

}

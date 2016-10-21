package mrserver.core.config.commandline.options;

import java.util.Arrays;

import mrserver.core.Core;
import mrserver.core.config.commandline.CommandLineOptions;
import mrserver.core.config.commandline.options.parse.ParseOption;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * Scenarioautoloadoption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class ScenarioAutoload extends Option implements ParseOption  {

	private final static String mOption = "sc_al";
	private final static String mLongOption = "auto_load_scenario";
	private final static String mDiscription = "Automatically loads the scenario if possible to\n";
	
	private final static boolean mHasArgument = false;
	
	public ScenarioAutoload(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        CommandLineOptions.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        CommandLineOptions.getLogger().debug( "Setting {} to TRUE", mLongOption);
	        Core.getInstance().getServerConfig().setScenarioAutoLoad(true);
	        
	        return true;
            
        }
		return false;
		
	}
	
}

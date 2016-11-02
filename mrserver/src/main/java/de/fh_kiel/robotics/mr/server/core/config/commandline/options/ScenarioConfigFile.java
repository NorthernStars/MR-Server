package de.fh_kiel.robotics.mr.server.core.config.commandline.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import de.fh_kiel.robotics.mr.server.core.Core;
import de.fh_kiel.robotics.mr.server.core.config.commandline.CommandLineOptions;
import de.fh_kiel.robotics.mr.server.core.config.commandline.options.parse.ParseOption;

/**
 * Szenariokonfigurationsdateioption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class ScenarioConfigFile extends Option implements ParseOption  {

	private final static String mOption = "scf";
	private final static String mLongOption = "scenarioconfigfile";
	private final static String mDiscription = "The configfile the scenario should use\n"; //TODO: Beispiel
	
	private final static boolean mHasArgument = true;
	private final static String mArgumentName = "filename";
	private final static int mNumberOfArguments = 1;
	
	public ScenarioConfigFile(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		setArgName( mArgumentName );
		setArgs( mNumberOfArguments );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        CommandLineOptions.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        CommandLineOptions.getLogger().debug( "Setting " + mLongOption + " " + aCommandLine.getOptionValue( getOpt() ) );
	        Core.getInstance().getServerConfig().setScenarioConfigFile( aCommandLine.getOptionValue( getOpt() ) ); 
            return true;
            
        }
		return false;
		
	}

}

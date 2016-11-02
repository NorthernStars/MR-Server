package de.fh_kiel.robotics.mr.server.core.config.commandline.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import de.fh_kiel.robotics.mr.server.core.Core;
import de.fh_kiel.robotics.mr.server.core.config.commandline.CommandLineOptions;
import de.fh_kiel.robotics.mr.server.core.config.commandline.options.parse.ParseOption;

/**
 * Szenarioklassenoption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class ScenarioClass extends Option implements ParseOption  {

	private final static String mOption = "sc";
	private final static String mLongOption = "scenarioclass";
	private final static String mDiscription = "Sets the scenarioclass to use\n"; //TODO: Beispiel
	
	private final static boolean mHasArgument = true;
	private final static String mArgumentName = "class";
	private final static int mNumberOfArguments = 1;
	
	public ScenarioClass(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		setArgName( mArgumentName );
		setArgs( mNumberOfArguments );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        CommandLineOptions.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        CommandLineOptions.getLogger().debug( "Setting " + mLongOption + " " + aCommandLine.getOptionValue( getOpt() ) );
			Core.getInstance().getServerConfig().setScenarioClass( aCommandLine.getOptionValue( getOpt() ) );;
            return true;
            
        }

		return false;
	}

}

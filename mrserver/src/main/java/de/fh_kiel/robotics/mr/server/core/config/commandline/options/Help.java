package de.fh_kiel.robotics.mr.server.core.config.commandline.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import de.fh_kiel.robotics.mr.server.core.config.commandline.CommandLineOptions;
import de.fh_kiel.robotics.mr.server.core.config.commandline.options.parse.ParseOption;

/**
 * Hilfeoption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class Help extends Option implements ParseOption  {
	
	private final static String mOption = "h";
	private final static String mLongOption = "help";
	private final static String mDiscription = "Displays this help\n";
	
	private final static boolean mHasArgument = false;
	
	public Help(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		
	}

	@Override
	public boolean parse( CommandLine aCommandLine ) {

        CommandLineOptions.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
        if ( aCommandLine.hasOption( getOpt() ) ) {
        	
            CommandLineOptions.getLogger().debug( "Displaying " + mLongOption );
            CommandLineOptions.getInstance().showCommandlineHelp( "mserver Kommandozeilenhilfe\n", CommandLineOptions.getInstance() );
            return true;
            
        }
		return false;
	}

}

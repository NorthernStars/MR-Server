package de.fh_kiel.robotics.mr.server.core.config.commandline.options;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import de.fh_kiel.robotics.mr.server.core.Core;
import de.fh_kiel.robotics.mr.server.core.config.commandline.CommandLineOptions;
import de.fh_kiel.robotics.mr.server.core.config.commandline.options.parse.ParseOption;

/**
 * Botportautoopenoption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class BotPortsAutoOpen extends Option implements ParseOption  {

	private final static String mOption = "bp_ao";
	private final static String mLongOption = "auto_open_botports";
	private final static String mDiscription = "Automatically opens the ports the bots can connect to\n";
	
	private final static boolean mHasArgument = false;
	
	public BotPortsAutoOpen(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        CommandLineOptions.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        CommandLineOptions.getLogger().debug( "Setting {} to TRUE", mLongOption);
	        Core.getInstance().getServerConfig().setAutoOpenBotPorts(true);
	        
	        return true;
            
        }
		return false;
		
	}
	
}

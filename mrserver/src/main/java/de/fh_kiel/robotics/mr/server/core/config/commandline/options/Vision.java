package de.fh_kiel.robotics.mr.server.core.config.commandline.options;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import de.fh_kiel.robotics.mr.server.core.Core;
import de.fh_kiel.robotics.mr.server.core.config.commandline.CommandLineOptions;
import de.fh_kiel.robotics.mr.server.core.config.commandline.options.parse.ParseOption;

/**
 * Visionoption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class Vision extends Option implements ParseOption  {

	private final static String mOption = "v";
	private final static String mLongOption = "vision";
	private final static String mDiscription = "Sets the vision ip-address and port\n";
	
	private final static boolean mHasArgument = true;
	private final static String mArgumentName = "ip-address:port";
	private final static int mNumberOfArguments = 2;
	private final static char mArgumentSeperator = ':';
	
	public Vision(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		setArgName( mArgumentName );
		setArgs( mNumberOfArguments );
		setValueSeparator( mArgumentSeperator );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        CommandLineOptions.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        CommandLineOptions.getLogger().debug( "Setting " + mLongOption + " " + Arrays.toString( aCommandLine.getOptionValues( getOpt() ) ) );
	        Core.getInstance().getServerConfig().setVisionIPAdress( aCommandLine.getOptionValues( getOpt() )[0] );
	        Core.getInstance().getServerConfig().setVisionPort( Integer.parseInt( aCommandLine.getOptionValues( getOpt() )[1] ) ); 
            return true;
            
        }
		return false;

	}

}

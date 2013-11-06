package mrserver.core.config.commandline.options;

import java.util.Arrays;

import mrserver.core.Core;
import mrserver.core.config.commandline.options.parse.ParseOption;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * Botportsoption für die Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
@SuppressWarnings("serial")
public class BotPorts extends Option implements ParseOption  {

	private final static String mOption = "bp";
	private final static String mLongOption = "botports";
	private final static String mDiscription = "Sets the ports the bots can connect to\n";
	
	private final static boolean mHasArgument = true;
	private final static boolean mArgumentIsOptional = true; // kann keine min/max Anzahl festlegen, also min 0
	private final static String mArgumentName = "port [:port...]";
	private final static int mNumberOfArguments = 10;
	private final static char mArgumentSeperator = ':';
	
	public BotPorts(){
		
		super( mOption, mLongOption, mHasArgument, mDiscription );
		setArgName( mArgumentName );
		setOptionalArg( mArgumentIsOptional );
		setArgs( mNumberOfArguments );
		setValueSeparator( mArgumentSeperator );
		
	}

	@Override
	public boolean parse(CommandLine aCommandLine) {

        Core.getLogger().debug( "Checking commandline for " + mLongOption + "option" );
		if ( aCommandLine.hasOption( getOpt() ) ) {

	        Core.getLogger().debug( "Setting " + mLongOption + " " + Arrays.toString( aCommandLine.getOptionValues( getOpt() ) ) );
	        for( String vPort : aCommandLine.getOptionValues( getOpt() )){
	        	
	        	Core.getInstance().getServerConfig().addBotPort( Integer.parseInt( vPort ) ); 
	            
	        }
	        return true;
            
        }
		return false;
		
	}
	
}

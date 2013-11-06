package mrserver.core.config.commandline.options;

import mrserver.core.config.commandline.options.parse.ParseOption;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

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
		// TODO Auto-generated method stub
		return false;
	}

}

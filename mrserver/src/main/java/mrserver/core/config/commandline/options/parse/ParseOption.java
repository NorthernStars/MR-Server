package mrserver.core.config.commandline.options.parse;

import org.apache.commons.cli.CommandLine;
/**
 * Interface für Optionen. 
 * 
 * @author Eike Petersen
 * @version 0.1
 * @since 0.1
 */
public interface ParseOption {
	
	
	/**
	 * Verarbeitet die Option in der Kommandozeile
	 * 
	 * @param aCommandLine die Kommandozeile des Servers
	 * @return ob das parsen erfolgreich war
	 */
	public boolean parse( CommandLine aCommandLine );

}

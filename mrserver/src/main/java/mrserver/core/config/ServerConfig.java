package mrserver.core.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import mrserver.core.config.commandline.CommandLineOptions;

/**
 * Konfigurationsdaten des Servers
 * 
 * @author Eike Petersen
 *
 */
public class ServerConfig {
	
	// Server
	
	/**
	 * Der Name des Servers
	 * 
	 */
	private String mServerName = "";
	
	// Szenario
	
	/**
	 * Die Bibliothek des Szenarios und
	 * die Klasse des Szenarios
	 * 
	 */
	private String mScenarioLibrary = "";
	private String mScenarioClass = "";
	private boolean mScenarioAutoLoad = false;
	
	/**
	 * Die Konfigurationen des Szenarios
	 * 
	 */
	private String mScenarioConfigCmdLine = "";
	private String mScenarioConfigFile = "";
	
	// Netzwerk

	/**
	 * Die IP-Adresse und Port des Vision-Moduls
	 * 
	 */
	
	private InetAddress mVisionIPAddress = InetAddress.getLoopbackAddress();
	private int mVisionPort = -1;
	
	/**
	 * Die IP-Adresse und Port des BotControl-Moduls
	 * 
	 */
	
	private InetAddress mBotControlIPAddress = InetAddress.getLoopbackAddress();
	private int mBotControlPort = -1;
	
	/**
	 * Der Port fuer die Graphics-Module
	 * 
	 */
	
	private int mGraphicsPort = -1;
	
	/**
	 * Die Ports fuer die Bots
	 * 
	 */
	
	private List<Integer> mBotPorts;
	private boolean mAutoOpenBotPorts = false;
	
	
	// Getter und Setters
	
	public String getServerName() {
        CommandLineOptions.getLogger().trace( "Getting servername " + mServerName );
		return mServerName;
	}

	public void setServerName(String aServerName) {
        CommandLineOptions.getLogger().debug( "Setting servername to " + aServerName );
		mServerName = aServerName;
	}

	public String getScenarioLibrary() {
        CommandLineOptions.getLogger().trace( "Getting scenariolibrary " + mScenarioLibrary );
		return mScenarioLibrary;
	}

	public void setScenarioLibrary(String aScenarioLibrary) {
        CommandLineOptions.getLogger().debug( "Setting scenariolibrary to " + aScenarioLibrary );
		mScenarioLibrary = aScenarioLibrary;
	}

	public String getScenarioClass() {
        CommandLineOptions.getLogger().trace( "Getting scenarioclass " + mScenarioClass );
		return mScenarioClass;
	}

	public void setScenarioClass(String aScenarioClass) {
        CommandLineOptions.getLogger().debug( "Setting scenarioclass to " + aScenarioClass );
		this.mScenarioClass = aScenarioClass;
	}

	public String getScenarioConfigCmdLine() {
        CommandLineOptions.getLogger().trace( "Getting scenarioconfigcmdline " + mScenarioConfigCmdLine );
		return mScenarioConfigCmdLine;
	}

	public void setScenarioConfigCmdLine(String aScenarioConfigCmdLine) {
        CommandLineOptions.getLogger().debug( "Setting scenarioconfigcmdline to " + aScenarioConfigCmdLine );
		this.mScenarioConfigCmdLine = aScenarioConfigCmdLine;
	}

	public String getScenarioConfigFile() {
        CommandLineOptions.getLogger().trace( "Getting scenarioconfigfile " + mScenarioConfigFile );
		return mScenarioConfigFile;
	}

	public void setScenarioConfigFile(String aScenarioConfigFile) {
        CommandLineOptions.getLogger().debug( "Setting scenarioconfigfile to " + aScenarioConfigFile );
		this.mScenarioConfigFile = aScenarioConfigFile;
	}

	public InetAddress getVisionIPAdress() {
        CommandLineOptions.getLogger().trace( "Getting visionipaddress " + mVisionIPAddress.getHostAddress() );
		return mVisionIPAddress;
	}

	public void setVisionIPAdress(InetAddress aVisionIPAddress) {
        CommandLineOptions.getLogger().debug( "Setting visionipaddress to " + aVisionIPAddress.getHostAddress() );
		this.mVisionIPAddress = aVisionIPAddress;
	}
	
	public void setVisionIPAdress( String aVisionIPAddress ) {
		
		CommandLineOptions.getLogger().debug( "Setting visionipaddress to " + aVisionIPAddress );
		try {
			
			setVisionIPAdress( InetAddress.getByName( aVisionIPAddress ) );
			
		} catch ( UnknownHostException vUnknownHostException ) {

            CommandLineOptions.getLogger().error( "Unkown Host: " + vUnknownHostException.getLocalizedMessage() );
            CommandLineOptions.getLogger().catching( Level.ERROR, vUnknownHostException );
            
		}

	}

	public int getVisionPort() {
        CommandLineOptions.getLogger().trace( "Getting visionports " + mVisionPort );
		return mVisionPort;
	}

	public void setVisionPort(int aVisionPort) {
        CommandLineOptions.getLogger().debug( "Setting visionports to " + aVisionPort );
		this.mVisionPort = aVisionPort;
	}

	public InetAddress getBotControlIPAdress() {
        CommandLineOptions.getLogger().trace( "Getting botcontrolipaddress " + mBotControlIPAddress.getHostAddress() );
		return mBotControlIPAddress;
	}

	public void setBotControlIPAdress( InetAddress aBotControlIPAddress ) {
        CommandLineOptions.getLogger().debug( "Setting botcontrolipaddress to " + aBotControlIPAddress.getHostAddress() );
		this.mBotControlIPAddress = aBotControlIPAddress;
	}

	public void setBotControlIPAdress( String aBotControlIPAddress ) {
		
		CommandLineOptions.getLogger().debug( "Setting botcontrolipaddress to " + aBotControlIPAddress );
		try {
			
			setBotControlIPAdress( InetAddress.getByName( aBotControlIPAddress ) );
			
		} catch ( UnknownHostException vUnknownHostException ) {

            CommandLineOptions.getLogger().error( "Unkown Botcontrolhost: " + vUnknownHostException.getLocalizedMessage() );
            CommandLineOptions.getLogger().catching( Level.ERROR, vUnknownHostException );
            
		}

	}

	public int getBotControlPort() {
        CommandLineOptions.getLogger().trace( "Getting botcontrolport " + mBotControlPort );
		return mBotControlPort;
	}

	public void setBotControlPort(int aBotControlPort) {
        CommandLineOptions.getLogger().debug( "Getting botcontrolport to " + aBotControlPort );
		this.mBotControlPort = aBotControlPort;
	}

	public int getGraphicsPort() {
        CommandLineOptions.getLogger().trace( "Getting graphicsport " + mGraphicsPort );
		return mGraphicsPort;
	}

	public void setGraphicsPort(int aGraphicsPort) {
        CommandLineOptions.getLogger().debug( "Setting graphicsport to " + aGraphicsPort );
		this.mGraphicsPort = aGraphicsPort;
	}

	public List<Integer> getBotPorts() {
		if( mBotPorts == null ){

	        CommandLineOptions.getLogger().debug( "Initialize botports " );
			mBotPorts = new ArrayList<Integer>();
			
		}
        CommandLineOptions.getLogger().trace( "Getting botports " + mBotPorts.toString() );
		return mBotPorts;
	}

	public void setBotPorts(List<Integer> aBotPorts) {
        CommandLineOptions.getLogger().debug( "Setting botports to " + aBotPorts );
		this.mBotPorts = aBotPorts;
	}
	
	public void addBotPort( int aBotPort ){
		
		if( mBotPorts == null ){

	        CommandLineOptions.getLogger().debug( "Initialize botports " );
			mBotPorts = new ArrayList<Integer>();
			
		}
        CommandLineOptions.getLogger().debug( "Adding botport " + aBotPort );
		mBotPorts.add( aBotPort );
		
	}
	
	public boolean autoOpenBotPorts() {
		return mAutoOpenBotPorts;
	}

	public void setAutoOpenBotPorts( boolean aAutoOpenBotPorts ) {
		CommandLineOptions.getLogger().debug( "Setting autoopenbotports to {}", aAutoOpenBotPorts );
		mAutoOpenBotPorts = aAutoOpenBotPorts;
	}
	
	public boolean sceanarioAutoLoad() {
		return mScenarioAutoLoad;
	}

	public void setScenarioAutoLoad( boolean aScenarioAutoLoad ) {
		CommandLineOptions.getLogger().debug( "Setting scenarioautoload to {}", aScenarioAutoLoad );
		mScenarioAutoLoad = aScenarioAutoLoad;
	}

	@Override
	public String toString() {
		return "ServerConfig [mServerName=" + mServerName
				+ ", mScenarioLibrary=" + mScenarioLibrary
				+ ", mScenarioClass=" + mScenarioClass + ", mScenarioAutoLoad="
				+ mScenarioAutoLoad + ", mScenarioConfigCmdLine="
				+ mScenarioConfigCmdLine + ", mScenarioConfigFile="
				+ mScenarioConfigFile + ", mVisionIPAddress="
				+ mVisionIPAddress + ", mVisionPort=" + mVisionPort
				+ ", mBotControlIPAddress=" + mBotControlIPAddress
				+ ", mBotControlPort=" + mBotControlPort + ", mGraphicsPort="
				+ mGraphicsPort + ", mBotPorts=" + mBotPorts
				+ ", mAutoOpenBotPorts=" + mAutoOpenBotPorts + "]";
	}
	
}

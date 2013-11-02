package mrserver.core.config;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import mrserver.core.Core;

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
	private String mServerName;
	
	// Szenario
	
	/**
	 * Die Bibliothek des Szenarios und
	 * die Klasse des Szenarios
	 * 
	 */
	private String mScenarioLibrary;
	private String mScenarioClass;
	
	/**
	 * Die Konfigurationen des Szenarios
	 * 
	 */
	private String mScenarioConfigCmdLine;
	private String mScenarioConfigFile;
	
	// Netzwerk

	/**
	 * Die IP-Adresse und Port des Vision-Moduls
	 * 
	 */
	
	private InetAddress mVisionIPAddress;
	private int mVisionPort;
	
	/**
	 * Die IP-Adresse und Port des BotControl-Moduls
	 * 
	 */
	
	private InetAddress mBotControlIPAddress;
	private int mBotControlPort;
	
	/**
	 * Der Port fuer die Graphics-Module
	 * 
	 */
	
	private int mGraphicsPort;
	
	/**
	 * Die Ports fuer die Bots
	 * 
	 */
	
	private List<Integer> mBotPorts;
	
	
	// Getter und Setters
	
	public String getServerName() {
        Core.getLogger().trace( "Getting servername " + mServerName );
		return mServerName;
	}

	public void setServerName(String aServerName) {
        Core.getLogger().trace( "Setting servername to " + aServerName );
		mServerName = aServerName;
	}

	public String getScenarioLibrary() {
        Core.getLogger().trace( "Getting scenariolibrary " + mScenarioLibrary );
		return mScenarioLibrary;
	}

	public void setScenarioLibrary(String aScenarioLibrary) {
        Core.getLogger().trace( "Setting scenariolibrary to " + aScenarioLibrary );
		mScenarioLibrary = aScenarioLibrary;
	}

	public String getScenarioClass() {
        Core.getLogger().trace( "Getting scenarioclass " + mScenarioClass );
		return mScenarioClass;
	}

	public void setScenarioClass(String aScenarioClass) {
        Core.getLogger().trace( "Setting scenarioclass to " + aScenarioClass );
		this.mScenarioClass = aScenarioClass;
	}

	public String getScenarioConfigCmdLine() {
        Core.getLogger().trace( "Getting scenarioconfigcmdline " + mScenarioConfigCmdLine );
		return mScenarioConfigCmdLine;
	}

	public void setScenarioConfigCmdLine(String aScenarioConfigCmdLine) {
        Core.getLogger().trace( "Setting scenarioconfigcmdline to " + aScenarioConfigCmdLine );
		this.mScenarioConfigCmdLine = aScenarioConfigCmdLine;
	}

	public String getScenarioConfigFile() {
        Core.getLogger().trace( "Getting scenarioconfigfile " + mScenarioConfigFile );
		return mScenarioConfigFile;
	}

	public void setScenarioConfigFile(String aScenarioConfigFile) {
        Core.getLogger().trace( "Setting scenarioconfigfile to " + aScenarioConfigFile );
		this.mScenarioConfigFile = aScenarioConfigFile;
	}

	public InetAddress getVisionIPAdress() {
        Core.getLogger().trace( "Getting visionipaddress " + mVisionIPAddress.getHostAddress() );
		return mVisionIPAddress;
	}

	public void setVisionIPAdress(InetAddress aVisionIPAddress) {
        Core.getLogger().trace( "Setting visionipaddress to " + aVisionIPAddress.getHostAddress() );
		this.mVisionIPAddress = aVisionIPAddress;
	}

	public int getVisionPort() {
        Core.getLogger().trace( "Getting visionports " + mVisionPort );
		return mVisionPort;
	}

	public void setVisionPort(int aVisionPort) {
        Core.getLogger().trace( "Setting visionports to " + aVisionPort );
		this.mVisionPort = aVisionPort;
	}

	public InetAddress getBotControlIPAdress() {
        Core.getLogger().trace( "Getting botcontrolipaddress " + mBotControlIPAddress.getHostAddress() );
		return mBotControlIPAddress;
	}

	public void setBotControlIPAdress(InetAddress aBotControlIPAddress) {
        Core.getLogger().trace( "Setting botcontrolipaddress to " + aBotControlIPAddress.getHostAddress() );
		this.mBotControlIPAddress = aBotControlIPAddress;
	}

	public int getBotControlPort() {
        Core.getLogger().trace( "Getting botcontrolport " + mBotControlPort );
		return mBotControlPort;
	}

	public void setBotControlPort(int aBotControlPort) {
        Core.getLogger().trace( "Getting botcontrolport to " + aBotControlPort );
		this.mBotControlPort = aBotControlPort;
	}

	public int getGraphicsPort() {
        Core.getLogger().trace( "Getting graphicsport " + mGraphicsPort );
		return mGraphicsPort;
	}

	public void setGraphicsPort(int aGraphicsPort) {
        Core.getLogger().trace( "Setting graphicsport to " + aGraphicsPort );
		this.mGraphicsPort = aGraphicsPort;
	}

	public List<Integer> getBotPorts() {
        Core.getLogger().trace( "Getting botports " + mBotPorts.toString() );
		if( mBotPorts == null ){

	        Core.getLogger().trace( "Initialize botports " );
			mBotPorts = new ArrayList<Integer>();
			
		}
		return mBotPorts;
	}

	public void setBotPorts(List<Integer> aBotPorts) {
        Core.getLogger().trace( "Setting botports to " + aBotPorts.toString() );
		this.mBotPorts = aBotPorts;
	}
	
	public void addBotPort( int aBotPort ){
		
        Core.getLogger().trace( "Adding botport " + aBotPort );
		if( mBotPorts == null ){

	        Core.getLogger().trace( "Initialize botports " );
			mBotPorts = new ArrayList<Integer>();
			
		}
		mBotPorts.add( aBotPort );
		
	}

	@Override
	public String toString() {
		return "ServerConfig [mServerName=" + mServerName
				+ ", mScenarioLibrary=" + mScenarioLibrary
				+ ", mScenarioClass=" + mScenarioClass
				+ ", mScenarioConfigCmdLine=" + mScenarioConfigCmdLine
				+ ", mScenarioConfigFile=" + mScenarioConfigFile
				+ ", mVisionIPAddress=" + mVisionIPAddress + ", mVisionPort="
				+ mVisionPort + ", mBotControlIPAddress="
				+ mBotControlIPAddress + ", mBotControlPort=" + mBotControlPort
				+ ", mGraphicsPort=" + mGraphicsPort + ", mBotPorts="
				+ mBotPorts + "]";
	}

}

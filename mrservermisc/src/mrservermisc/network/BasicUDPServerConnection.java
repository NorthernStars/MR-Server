package mrservermisc.network;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import mrservermisc.logging.Loggers;

import org.apache.logging.log4j.Level;

/**
 * Die grundlegende UDPNetzwerkverbindung. Es wird das UDP-
 * Protokoll genutzt.
 * 
 * @author Hannes Eilers, Louis Jorswieck, Eike Petersen
 * @since 0.1
 * @version 0.9
 *
 */
public class BasicUDPServerConnection {

    protected boolean mSocketInitialized = false;
    
	/**
	 * Maximale Länge eines UDP-Datagramms in Byte.
	 */
	private static int MAX_DATAGRAM_LENGTH = 16384;
	
	/**
	 * Datenpaket zum Versenden von Daten an das Ziel.
	 */
	protected DatagramPacket mDataPaket;
	/**
	 * UDP-Socket
	 */
	protected DatagramSocket mToTargetSocket = null;

	/**
	 * Richtet eine UDP-Verbindung zu einem Ziel ein.
	 * 
	 * @param aTargetAddress
	 * - Adresse des Zielhosts
	 * @param aTargetPort
	 * - Portnummer an die Datagramme versendet werden.
	 * @throws IOException
	 * Verbindung konnte nicht eingerichtet werden.
	 */
	public BasicUDPServerConnection( InetAddress aTargetAddress, int aTargetPort )
	{		
		try {
			
			initialiseDatagram( aTargetAddress, aTargetPort );
			
			mToTargetSocket = new DatagramSocket();
			mToTargetSocket.setReuseAddress(true);
			mToTargetSocket.connect( aTargetAddress, aTargetPort );

			mSocketInitialized = true;
	        
		} catch ( IOException vIoException ) {
	
	    	Loggers.getNetworkLogger().error( "Error creating connection: {}", vIoException.getLocalizedMessage() );
	    	Loggers.getNetworkLogger().catching( Level.ERROR, vIoException );
		
		}
		
	}

    private void initialiseDatagram( InetAddress aTargetAddress, int aTargetPort ) {
        
        mDataPaket = new DatagramPacket( new byte[BasicUDPServerConnection.MAX_DATAGRAM_LENGTH], BasicUDPServerConnection.MAX_DATAGRAM_LENGTH );
		mDataPaket.setAddress( aTargetAddress );
		mDataPaket.setPort( aTargetPort );
		
    }
	
	public BasicUDPServerConnection( InetAddress aTargetAddress, int aTargetPort, int aHostPort )
    {       
		try {
			
		    initialiseDatagram( aTargetAddress, aTargetPort );
		    
	        mToTargetSocket = new DatagramSocket( aHostPort );
			mToTargetSocket.setReuseAddress(true);
	        mToTargetSocket.connect( aTargetAddress, aTargetPort );
	
	        mSocketInitialized = true;
	        
		} catch ( IOException vIoException ) {
	
	    	Loggers.getNetworkLogger().error( "Error creating connection: {}", vIoException.getLocalizedMessage() );
	    	Loggers.getNetworkLogger().catching( Level.ERROR, vIoException );
		
		}
        
    }

	/**
	 * Versendet die übergebenen Daten in einem Datagramm.
	 * 
	 * @param aData
	 * 		Zu versendende Daten als String.
	 */
	public void sendDatagrammString( String aData )
	{
		try {
			
			mDataPaket.setData( aData.getBytes() );
			mToTargetSocket.send( mDataPaket );
			
		} catch ( IOException vIoException ) {
	
	        Loggers.getNetworkLogger().error( "Error sending datagram {} with error {}", aData, vIoException.getLocalizedMessage() );
	        Loggers.getNetworkLogger().catching( Level.ERROR, vIoException );
			
		}

	}

	/**
	 * Wartet maximal aWaitTime Millisekunden auf ein Datagramm vom Zielhost, und wandelt die empfangenen
	 * Daten in einen String um.
	 * 
	 * @param aWaitTime
	 * Bei aWaitTime = 0 wartet die Verbindung unentlich lange, bei aWaitTime > 0 entsprechende Millisekunden
	 * @return != null: Empfangene Daten als String.<br/>
	 * == null: Es wurden keine Daten empfangen.
	 * @throws SocketTimeoutException 
	 * @throws IOException
	 * 	Schwerer Ausnahmefehler.
	 */
	public String getDatagrammString( int aWaitTime ) throws SocketTimeoutException
	{
		String vData = null;
		
		DatagramPacket vDatagrammPacketFromServer = getDatagrammPacket( aWaitTime );
		
		if ( vDatagrammPacketFromServer.getAddress().equals( mDataPaket.getAddress() ) ) {
			vData = new String( vDatagrammPacketFromServer.getData(), 0, vDatagrammPacketFromServer.getLength() );
		}
		
		return vData;
			
	}

	private DatagramPacket getDatagrammPacket(int aWaitTime) throws SocketTimeoutException 
	{
		
		DatagramPacket vDatagrammPacketFromServer = new DatagramPacket( new byte[BasicUDPServerConnection.MAX_DATAGRAM_LENGTH], BasicUDPServerConnection.MAX_DATAGRAM_LENGTH );
		
		try {
			
			mToTargetSocket.setSoTimeout( aWaitTime );
		
			mToTargetSocket.receive( vDatagrammPacketFromServer );
			
		} catch ( IOException vException ) {
			
			if( vException instanceof SocketTimeoutException ){
				
				throw (SocketTimeoutException)vException;
				
			}
			
	        Loggers.getNetworkLogger().error( "Error sending datagram: {}", vException.getLocalizedMessage() );
	        Loggers.getNetworkLogger().catching( Level.ERROR, vException );
			
		}
			
		return vDatagrammPacketFromServer;
		
	}

	/**
	 * Beendet die Verbindung.
	 */
	public void closeConnection()
	{
		if ( mToTargetSocket != null ) {
		    mToTargetSocket.disconnect();
			mToTargetSocket.close();
			mToTargetSocket = null;
		}
		
		mSocketInitialized = false;
		
	}
    
    public boolean isConnected() {
        
        return mSocketInitialized;
        
    }
    
    /**
     * Gibt die Serveradresse, den Serverport, die Clientadresse und den Clientport in einem
     * vorformatierten String aus.
     * 
     * @since 0.9
     * 
     * @return String in der Form: (Clientip):(Clientport) zu (Serverip):(Serverport)
     */
    public String toString(){
        if ( mToTargetSocket != null ) {
            
            return mToTargetSocket.getLocalAddress().toString() + ":" + mToTargetSocket.getLocalPort() + " zu " + mToTargetSocket.getInetAddress().toString() + ":" + mToTargetSocket.getPort();
            
        } else {
            
            return "Es besteht keine Serververbindung";
            
        }
        
    }
	
}

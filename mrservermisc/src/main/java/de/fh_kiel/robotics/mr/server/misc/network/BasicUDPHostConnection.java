package de.fh_kiel.robotics.mr.server.misc.network;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;

import org.apache.logging.log4j.Level;

import de.fh_kiel.robotics.mr.server.misc.logging.Loggers;

/**
 * Die grundlegende UDPNetzwerkverbindung. Es wird das UDP-
 * Protokoll genutzt.
 * 
 * @author Hannes Eilers, Louis Jorswieck, Eike Petersen
 * @since 0.1
 * @version 0.9
 *
 */
public class BasicUDPHostConnection {


	protected boolean mSocketInitialized = false;
    
	/**
	 * Maximale Länge eines UDP-Datagramms in Byte.
	 */
	private static int MAX_DATAGRAM_LENGTH = 16384;
	/**
	 * UDP-Socket
	 */
	protected DatagramSocket mToTargetSocket = null;

	/**
	 * Richtet eine UDP-Verbindung ein.
	 * 
	 * @param aTargetAddress
	 * - Adresse des Zielhosts
	 * @param aTargetPort
	 * - Portnummer an die Datagramme versendet werden.
	 * @throws IOException
	 * Verbindung konnte nicht eingerichtet werden.
	 */
	public BasicUDPHostConnection()
	{		
		try {
			
			mToTargetSocket = new DatagramSocket();
			mToTargetSocket.setReuseAddress(true);
			
			mSocketInitialized = true;
	        Loggers.getNetworkLogger().debug( "Created socket: {}", ((InetSocketAddress) mToTargetSocket.getLocalSocketAddress()) );
	        
		} catch ( IOException vIoException ) {

        	Loggers.getNetworkLogger().error( "Error creating connection: {}", vIoException.getLocalizedMessage() );
        	Loggers.getNetworkLogger().catching( Level.ERROR, vIoException );
		
		}
		
	}
	
	public BasicUDPHostConnection( int aHostPort )
    {       
	    
		try {
	        mToTargetSocket = new DatagramSocket( aHostPort );
			mToTargetSocket.setReuseAddress(true);
	
	        mSocketInitialized = true;
	        Loggers.getNetworkLogger().debug( "Created socket: {}", ((InetSocketAddress) mToTargetSocket.getLocalSocketAddress()) );
	        
		} catch ( IOException vIoException ) {
	
	        Loggers.getNetworkLogger().error( "Error creating connection: {} HostPort: {}", vIoException.getLocalizedMessage(), aHostPort );
	        Loggers.getNetworkLogger().catching( Level.ERROR, vIoException );
			
		}
        
    }

	/**
	 * Versendet die übergebenen Daten in einem Datagramm.
	 * 
	 * @param aData
	 * 		Zu versendende Daten als String.
	 */
	public void sendDatagramm( DatagramPacket aData )
	{
		
		try {
			
			mToTargetSocket.send( aData );
			
		} catch ( IOException vIoException ) {

            Loggers.getNetworkLogger().error( "Error sending datagram: {} with {}", vIoException.getLocalizedMessage(), aData );
            Loggers.getNetworkLogger().catching( Level.ERROR, vIoException );
			
		}

	}
	
	public void sendString( String aData, InetSocketAddress aAddress )
	{
		
		DatagramPacket vDatagramPacketToSend = new DatagramPacket( new byte[BasicUDPHostConnection.MAX_DATAGRAM_LENGTH], BasicUDPHostConnection.MAX_DATAGRAM_LENGTH );
		vDatagramPacketToSend.setAddress( aAddress.getAddress() );
		vDatagramPacketToSend.setPort( aAddress.getPort() );
		vDatagramPacketToSend.setData( aData.getBytes() );
		sendDatagramm( vDatagramPacketToSend );

	}

	/**
	 * Wartet maximal aWaitTime Millisekunden auf ein Datagramm vom Zielhost, und wandelt die empfangenen
	 * Daten in einen String um.
	 * 
	 * @param aWaitTime
	 * Bei aWaitTime = 0 wartet die Verbindung unentlich lange, bei aWaitTime > 0 entsprechende Millisekunden
	 * @return != null: Empfangene Daten als String.<br/>
	 * == null: Es wurden keine Daten empfangen.
	 * @throws IOException
	 * 	Schwerer Ausnahmefehler.
	 */
	public String getDatagrammString( int aWaitTime ) throws SocketTimeoutException
	{
		String vData = null;
		
		DatagramPacket vDatagrammPacketFromServer = getDatagrammPacket( aWaitTime );
		
		vData = new String( vDatagrammPacketFromServer.getData(), 0, vDatagrammPacketFromServer.getLength() );
		
		return vData;
			
	}

	public DatagramPacket getDatagrammPacket( int aWaitTime ) throws SocketTimeoutException
	{
		
		DatagramPacket vDatagrammPacketFromServer = new DatagramPacket( new byte[BasicUDPHostConnection.MAX_DATAGRAM_LENGTH], BasicUDPHostConnection.MAX_DATAGRAM_LENGTH );

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
            
            return "Host open at" + mToTargetSocket.getLocalAddress().toString() + ":" + mToTargetSocket.getLocalPort();
            
        } else {
            
            return "The host is not open";
            
        }
        
    }
	
}

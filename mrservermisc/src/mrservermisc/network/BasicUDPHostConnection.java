package mrservermisc.network;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

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
	public BasicUDPHostConnection() throws IOException
	{		
		
		mToTargetSocket = new DatagramSocket();
		
		mSocketInitialized = true;
		
	}
	
	public BasicUDPHostConnection( int aHostPort ) throws IOException
    {       
	    
        mToTargetSocket = new DatagramSocket( aHostPort );

        mSocketInitialized = true;
        
    }

	/**
	 * Versendet die übergebenen Daten in einem Datagramm.
	 * 
	 * @param aData
	 * 		Zu versendende Daten als String.
	 */
	public void sendDatagramm( DatagramPacket aData ) throws IOException
	{
		
		mToTargetSocket.send( aData );

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
	public String getDatagrammString( int aWaitTime ) throws IOException, SocketTimeoutException, SocketException
	{
		String vData = null;
		
		DatagramPacket vDatagrammPacketFromServer = getDatagrammPacket( aWaitTime );
		
		vData = new String( vDatagrammPacketFromServer.getData(), 0, vDatagrammPacketFromServer.getLength() );
		
		return vData;
			
	}

	public DatagramPacket getDatagrammPacket( int aWaitTime ) throws SocketException, SocketTimeoutException, IOException 
	{
		
		DatagramPacket vDatagrammPacketFromServer = new DatagramPacket( new byte[BasicUDPHostConnection.MAX_DATAGRAM_LENGTH], BasicUDPHostConnection.MAX_DATAGRAM_LENGTH );

		mToTargetSocket.setSoTimeout( aWaitTime );
		
		mToTargetSocket.receive( vDatagrammPacketFromServer );
			
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

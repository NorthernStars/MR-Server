package mrserver.core.botai.network.receive;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mrserver.core.botai.BotAIManagement;
import mrserver.core.botai.data.BotAI;
import mrserver.core.botai.network.BotAiHost;
import mrserver.core.botai.network.data.UnkownBotAI;
import mrservermisc.network.BasicUDPHostConnection;

public class Worker implements Runnable{

	private static final ExecutorService WORKERPOOL = Executors.newCachedThreadPool();
	
	public static void putWorkerInPool( Worker aWorker){
		
		WORKERPOOL.execute( aWorker );
		
	}

	private DatagramPacket mRecievedDatagramPacket;
	private BotAiHost mBotAIConnect;
	
	public Worker( BotAiHost aBotAIConnect, DatagramPacket aRecievedDatagram ){
		
		mRecievedDatagramPacket = aRecievedDatagram;
		mBotAIConnect = aBotAIConnect;
		
	}
	
	@Override
	public void run() {

		BotAIManagement.getLogger().debug( "Processing packet " + "(" + ( (InetSocketAddress) mRecievedDatagramPacket.getSocketAddress()).toString() + "):" + new String( mRecievedDatagramPacket.getData(), 0, mRecievedDatagramPacket.getLength() ) );
		
		BotAI aCorrespondingBotAI = BotAIManagement.getInstance().getMapOfBotAIs().get( mRecievedDatagramPacket.getSocketAddress() );
		
		if( aCorrespondingBotAI != null ){
			
			aCorrespondingBotAI.processDatagrammPacket( mRecievedDatagramPacket );
			
		} else {
			
			Creator.putUnkownSenderDatagramInProcessingQueue( new UnkownBotAI( mBotAIConnect, mRecievedDatagramPacket ) );
			
		}
		
	}

}

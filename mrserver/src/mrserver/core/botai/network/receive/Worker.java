package mrserver.core.botai.network.receive;

import java.net.DatagramPacket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mrserver.core.botai.BotAIManagement;
import mrserver.core.botai.data.BotAI;
import mrserver.core.botai.network.data.UnkownBotAI;
import mrservermisc.network.BasicUDPHostConnection;

public class Worker implements Runnable{

	private static final ExecutorService WORKERPOOL = Executors.newCachedThreadPool();
	
	public static void putWorkerInPool( Worker aWorker){
		
		WORKERPOOL.execute( aWorker );
		
	}

	private DatagramPacket mRecievedDatagrammPacket;
	private BasicUDPHostConnection mBotAIConnect;
	
	public Worker( BasicUDPHostConnection aBotAIConnect, DatagramPacket aRecievedDatagramm ){
		
		mRecievedDatagrammPacket = aRecievedDatagramm;
		mBotAIConnect = aBotAIConnect;
		
	}
	
	@Override
	public void run() {

		BotAIManagement.getLogger().debug( "Processing packet " + mRecievedDatagrammPacket.toString() );
		
		BotAI aCorrespondingBotAI = BotAIManagement.getInstance().getMapOfBotAIs().get( mRecievedDatagrammPacket.getSocketAddress() );
		
		if( aCorrespondingBotAI != null ){
			
			aCorrespondingBotAI.processDatagrammPacket( mRecievedDatagrammPacket );
			
		} else {
			
			Creator.putUnkownSenderDatagramInProcessingQueue( new UnkownBotAI( mBotAIConnect, mRecievedDatagrammPacket ) );
			
		}
		
	}

}

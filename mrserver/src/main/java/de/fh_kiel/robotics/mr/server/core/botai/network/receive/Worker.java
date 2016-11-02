package de.fh_kiel.robotics.mr.server.core.botai.network.receive;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.fh_kiel.robotics.mr.server.core.botai.BotAIManagement;
import de.fh_kiel.robotics.mr.server.core.botai.data.BotAI;
import de.fh_kiel.robotics.mr.server.core.botai.network.BotAiHost;
import de.fh_kiel.robotics.mr.server.core.botai.network.data.UnkownBotAI;

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

		BotAIManagement.getLogger().debug( "Processing packet " + "({}): {}", (InetSocketAddress) mRecievedDatagramPacket.getSocketAddress(), new String( mRecievedDatagramPacket.getData(), 0, mRecievedDatagramPacket.getLength() ) );
		
		BotAI aCorrespondingBotAI = BotAIManagement.getInstance().getMapOfBotAIs().get( mRecievedDatagramPacket.getSocketAddress() );
		
		if( aCorrespondingBotAI != null ){
			
			aCorrespondingBotAI.processDatagrammPacket( mRecievedDatagramPacket );
			
		} else {
			
			Creator.putUnkownSenderDatagramInProcessingQueue( new UnkownBotAI( mBotAIConnect, mRecievedDatagramPacket ) );
			
		}
		
	}

}

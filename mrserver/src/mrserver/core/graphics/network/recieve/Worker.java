package mrserver.core.graphics.network.recieve;

import java.net.DatagramPacket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mrserver.core.graphics.GraphicModul;
import mrserver.core.graphics.GraphicsManagement;

public class Worker implements Runnable{

	private static final ExecutorService WORKERPOOL = Executors.newCachedThreadPool();
	
	public static void putWorkerInPool( Worker aWorker){
		
		WORKERPOOL.execute( aWorker );
		
	}

	private DatagramPacket mRecievedDatagrammPacket;
	
	public Worker( DatagramPacket aRecievedDatagramm ){
		
		mRecievedDatagrammPacket = aRecievedDatagramm;
		
	}
	
	@Override
	public void run() {

		GraphicsManagement.getLogger().debug( "Processing packet " + mRecievedDatagrammPacket.toString() );
		
		GraphicModul aCorrespondingGraphicsModul = GraphicsManagement.getInstance().getMapOfConnections().get( mRecievedDatagrammPacket.getSocketAddress() );
		
		if( aCorrespondingGraphicsModul != null ){
			
			aCorrespondingGraphicsModul.processDatagrammPacket( mRecievedDatagrammPacket );
			
		} else {
			
			Creator.putUnkownSenderDatagramInProcessingQueue( mRecievedDatagrammPacket );
			
		}
		
	}

}

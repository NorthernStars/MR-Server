package mrserver.gui.options.interfaces;

import mrserver.core.botai.network.receive.Receiver;

public interface BotPortListener {
	
	public void newBotPort( Receiver aReceiver );
	public void removedBotPort( Receiver aReceiver );

}

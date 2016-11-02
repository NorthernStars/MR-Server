package de.fh_kiel.robotics.mr.server.gui.options.interfaces;

import de.fh_kiel.robotics.mr.server.core.botai.network.receive.Receiver;

public interface BotPortListener {
	
	public void newBotPort( Receiver aReceiver );
	public void removedBotPort( Receiver aReceiver );

}

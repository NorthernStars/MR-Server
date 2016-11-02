package de.fh_kiel.robotics.mr.server.misc.graphics.interfaces;

import de.fh_kiel.robotics.mr.server.misc.network.data.position.PositionDataPackage;

public interface Graphic {

	public void sendData( PositionDataPackage aPositionData );
	
}

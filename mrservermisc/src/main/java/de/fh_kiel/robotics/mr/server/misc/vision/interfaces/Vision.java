package de.fh_kiel.robotics.mr.server.misc.vision.interfaces;

import de.fh_kiel.robotics.mr.server.misc.network.data.position.PositionDataPackage;

public interface Vision {
	
	/**
	 * Holt die neusten empfangen Positionsdaten der Vision
	 * 
	 * @return letzte empfangene Positionsdaten
	 */
	public PositionDataPackage getPositionData();
	
}

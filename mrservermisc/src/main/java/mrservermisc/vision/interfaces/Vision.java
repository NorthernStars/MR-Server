package mrservermisc.vision.interfaces;

import mrservermisc.network.data.position.PositionDataPackage;

public interface Vision {
	
	/**
	 * Holt die neusten empfangen Positionsdaten der Vision
	 * 
	 * @return letzte empfangene Positionsdaten
	 */
	public PositionDataPackage getPositionData();
	
}

package mrservermisc.vision.interfaces;

import mrservermisc.vision.data.PositionData;

public interface Vision {
	
	/**
	 * Holt die neusten empfangen Positionsdaten der Vision
	 * 
	 * @return letzte empfangene Positionsdaten
	 */
	public PositionData getPositionData();
	
}

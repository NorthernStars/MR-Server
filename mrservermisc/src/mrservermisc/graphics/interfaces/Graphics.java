package mrservermisc.graphics.interfaces;

import mrservermisc.network.data.position.PositionDataPackage;

/**
 * Interface mit dem ein Szenario Daten an ein Graphicsmodul senden kann
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 */
public interface Graphics {
	
	/**
	 * Übergibt einen anzuzeigenden Weltstatus
	 * 
	 * @param aWorldData der zu sendende Weltstatus
	 * @return true wenn der Weltstatus erfolgreich versendet werden konnte, false wenn nicht
	 */
	public boolean sendWorldStatus( PositionDataPackage aPositionData );

}

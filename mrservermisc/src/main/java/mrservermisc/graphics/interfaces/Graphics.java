package mrservermisc.graphics.interfaces;

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
	 * @param aWorldDataXML der zu sendende Weltstatus in XML
	 * @return true wenn der Weltstatus erfolgreich versendet werden konnte, false wenn nicht
	 */
	public boolean sendWorldStatus( String aWorldDataXML );

}

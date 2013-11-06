package mrservermisc.botcontrol.interfaces;

/**
 * Interface mit dem ein Szenario mit einem BotControlServer kommunizieren kann
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 */
public interface BotControl {
	
	/**
	 * Übergibt einen zu sendenden Bewegungsbefehl
	 * 
	 * @param aMovement der Bewegungsbefehl //TODO: Befehl nur auf Bot und Motorengeschwindigkeit reduzieren 
	 * @return true wenn der Befehl erfolgreich versendet werden konnte, false wenn nicht
	 */
	public boolean sendMovement( String aMovement );

}

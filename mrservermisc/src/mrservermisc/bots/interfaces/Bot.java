package mrservermisc.bots.interfaces;

import mrservermisc.bots.data.BotInformation;

/**
 * Interface mit dem ein Szenario Daten an einen Bot senden und Daten von einem Bot empfangen kann
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 */
public interface Bot {
	
	/**
	 * Sendet einen Weltstatus an den Bot
	 * 
	 * @param aWorldData der zu sendende Weltstatus
	 * @return true wenn der Weltstatus erfolgreich versendet werden konnte, false wenn nicht
	 */
	public boolean sendWorldStatus( String aWorldData );
	
	/**
	 * Holt die neusten Aktionssbefehle des Bots
	 * 
	 * @return den neusten Aktionsbefehl
	 */
	public Action getAction();
	
	/**
	 * Fragt die Botinformationen ab
	 * 
	 * @return die aktuellen Botinformationen
	 */
	public BotInformation getBotInformation();
	
}

package de.fh_kiel.robotics.mr.server.misc.bots.interfaces;

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
	 */
	public void sendWorldStatus( String aWorldData );
	
	/**
	 * Holt die neusten Aktionssbefehle des Bots
	 * 
	 * @return den neusten Aktionsbefehl
	 */
	public String getLastAction();
	
	public int getTeam();
	public String getName();
	public int getRcId();
	public int getVtId();
	
	
	
}

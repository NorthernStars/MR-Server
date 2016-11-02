package de.fh_kiel.robotics.mr.server.misc.botcontrol.interfaces;

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
	 * @param aBot der zu bewegenede Bot
	 * @param aLeftWheelSpeed die Geschwindigkeit des linken Rades in int von -100 zu +100
	 * @param aRightWheelSpeed die Geschwindigkeit des rechten Rades in int von -100 zu +100
	 * @return true wenn der Befehl erfolgreich versendet werden konnte, false wenn nicht
	 */
	public boolean sendMovement( int aBot, int aLeftWheelSpeed, int aRightWheelSpeed );

}

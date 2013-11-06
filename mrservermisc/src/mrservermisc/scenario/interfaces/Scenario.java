package mrservermisc.scenario.interfaces;


/**
 * Das grundlegende Interface eines Scenarios
 * 
 * @author Eike Petersen
 * @version 0.1
 * @since 0.1
 */
public interface Scenario {
	
	/**
	 * Beendet das Scenario und entlässt alle Ressourcen
	 */
	public void close();

}

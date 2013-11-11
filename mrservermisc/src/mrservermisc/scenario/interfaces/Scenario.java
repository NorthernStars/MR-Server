package mrservermisc.scenario.interfaces;

import java.util.List;

import mrservermisc.botcontrol.interfaces.BotControl;
import mrservermisc.bots.interfaces.Bot;
import mrservermisc.graphics.interfaces.Graphics;
import mrservermisc.vision.interfaces.Vision;


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
	
	/**
	 * Ob zu einem Visionserver verbunden werden soll
	 * 
	 * @return true verbinde zu einem Visionserver, 
	 * 	       false verbinde nicht zu einem Visionserver 
	 */
	public boolean needVision();
	
	/**
	 * Ob zu einem BotControlserver verbunden werden soll
	 * 
	 * @return true verbinde zu einem BotControlserver, 
	 * 	       false verbinde nicht zu einem BotControlserver 
	 */
	public boolean needBotControl();
	
	/**
	 * Registriert Vision an dem Scenario
	 * 
	 * @param aVision die zu registrierende Vision
	 * 
	 * @return true die Vision konnte erfolgreich registriert werden
	 * 		   false Fehler beim Registrieren von der Vision 
	 */
	public boolean registerVision( Vision aVision );

	/**
	 * Registriert BotControl an dem Scenario
	 * 
	 * @param aBotControl die zu registrierende Vision
	 * 
	 * @return true die BotControl konnte erfolgreich registriert werden
	 * 		   false Fehler beim Registrieren von der BotControl 
	 */
	public boolean registerBotControl( BotControl aBotControl );
	
	/**
	 * Registriert eine Liste von Bots an dem Scenario
	 * 
	 * @param aBotList die Liste der Bots
	 * 
	 * @return true die Botliste konnte erfolgreich registriert werden
	 * 		   false Fehler beim Registrieren von der Botliste 
	 */
	public boolean registerBotList( List<Bot> aBotList );
	
	/**
	 * Registriert Graphics an dem Scenario
	 * 
	 * @param aGraphicsl die zu registrierende Graphics
	 * 
	 * @return true die Graphics konnte erfolgreich registriert werden
	 * 		   false Fehler beim Registrieren von der Graphics 
	 */
	public boolean registerGraphics( Graphics aGraphics );
	
	public void startScenario();


}

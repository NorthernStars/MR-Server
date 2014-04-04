package mrservermisc.scenario.interfaces;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mrservermisc.botcontrol.interfaces.BotControl;
import mrservermisc.bots.interfaces.Bot;
import mrservermisc.graphics.interfaces.Graphics;
import mrservermisc.network.data.position.PositionDataPackage;
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
	 * Übergibt dem Scenario Positionsdaten der Vision
	 * 
	 * @param aVision die von der Vision übermittelten Positionsdaten
	 * 
	 * @return true die Positionsdaten konneten erfolgreich übergeben werden
	 * 		   false Fehler beim übergeben der Positionsdaten 
	 */
	public boolean putPositionData( PositionDataPackage aPositionDataPackage );

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
	 * Registriert neue Bots an dem Scenario
	 * 
	 * @param aBot der neue Bots
	 * 
	 * @return true der Bot konnte erfolgreich registriert werden
	 * 		   false Fehler beim Registrieren von dem Bot
	 */
	public boolean registerNewBot( Bot aBot );
	
	public boolean unregisterBot( Bot aBot );
	
	/**
	 * Registriert Graphics an dem Scenario
	 * 
	 * @param aGraphicsl die zu registrierende Graphics
	 * 
	 * @return true die Graphics konnte erfolgreich registriert werden
	 * 		   false Fehler beim Registrieren von der Graphics 
	 */
	public boolean registerGraphics( Graphics aGraphics );
	
	public void loadScenario();
	
	public JPanel getScenarioGUI();

	public JFrame getScenarioOptionsGUI();

}

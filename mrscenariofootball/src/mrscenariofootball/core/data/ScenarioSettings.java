package mrscenariofootball.core.data;

import mrscenariofootball.core.ScenarioCore;

/**
 * Class storing settings for scenario
 * @author Hannes Eilers
 *
 */
public class ScenarioSettings {

	private static ScenarioSettings INSTANCE;
	
	/**
	 * @return Instance of {@link ScenarioSettings}
	 */
	public static ScenarioSettings getInstance() {        
        if( ScenarioSettings.INSTANCE == null){
        	ScenarioCore.getLogger().debug( "Creating ScenarioInformation-instance." );
        	ScenarioSettings.INSTANCE = new ScenarioSettings();
        }

        ScenarioCore.getLogger().trace( "Retrieving ScenarioInformation-instance." );
        return ScenarioSettings.INSTANCE;        
    }
	
	private boolean showFieldBackground = false;

	/**
	 * @return the showFieldBackground
	 */
	public boolean showFieldBackground() {
		return showFieldBackground;
	}

	/**
	 * @param showFieldBackground the showFieldBackground to set
	 */
	public void setShowFieldBackground(boolean showFieldBackground) {
		this.showFieldBackground = showFieldBackground;
	}
	
}

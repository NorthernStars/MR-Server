package de.fh_kiel.robotics.mr.scenario.football.core;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fh_kiel.robotics.mr.scenario.football.core.data.BotAI;
import de.fh_kiel.robotics.mr.scenario.football.core.data.ScenarioInformation;
import de.fh_kiel.robotics.mr.scenario.football.core.gui.ScenarioGUI;
import de.fh_kiel.robotics.mr.scenario.football.core.gui.menus.ScenarioOptionsGUI;
import de.fh_kiel.robotics.mr.scenario.football.core.managements.FromVision;
import de.fh_kiel.robotics.mr.scenario.football.core.managements.ToBotAIs;
import de.fh_kiel.robotics.mr.scenario.football.core.managements.ToGraphics;
import de.fh_kiel.robotics.mr.scenario.football.game.Core;
import de.fh_kiel.robotics.mr.server.misc.botcontrol.interfaces.BotControl;
import de.fh_kiel.robotics.mr.server.misc.bots.interfaces.Bot;
import de.fh_kiel.robotics.mr.server.misc.graphics.interfaces.Graphics;
import de.fh_kiel.robotics.mr.server.misc.network.data.position.PositionDataPackage;
import de.fh_kiel.robotics.mr.server.misc.scenario.interfaces.Scenario;

/**
 * Bildet das Herzstück des MRFussballszenarios. Hier werden alle Metaprozesse und Threads verwaltet
 * und gesteuert.
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 *
 */
public class ScenarioCore implements Scenario {
    
    private static ScenarioCore INSTANCE;
	private static Logger SCENARIOLOGGER = LogManager.getLogger("SCENARIO");
    
    public static Logger getLogger(){
        
        return SCENARIOLOGGER;
        
    }
    
    public ScenarioCore() {
    	
    	ScenarioCore.getLogger().debug( "Creating ScenarioCore-instance." );
        ScenarioCore.INSTANCE = this;
    	
    }
    
    public static ScenarioCore getInstance() {
        
        if( ScenarioCore.INSTANCE == null){
        	ScenarioCore.getLogger().debug( "Creating ScenarioCore-instance." );
            ScenarioCore.INSTANCE = new ScenarioCore();
        }

        ScenarioCore.getLogger().trace( "Retrieving ScenarioCore-instance." );
        return ScenarioCore.INSTANCE;
        
    }

	private ScenarioGUI mGUI = new ScenarioGUI();
	private ScenarioOptionsGUI mOptionsGUI = new ScenarioOptionsGUI();
	
	@Override
	public void close() {
		
		FromVision.getInstance().close();
		ToBotAIs.getInstance().close();
		ToGraphics.getInstance().close();
		Core.getInstance().resume();
		
	}

	@Override
	public boolean needVision() {
		return true;
	}

	@Override
	public boolean needBotControl() {
		return true;
	}

	@Override
	public boolean putPositionData( PositionDataPackage aPositionDataPackage ) {
		
		return FromVision.putPositionDatPackageInProcessingQueue( aPositionDataPackage );
		
	}

	@Override
	public boolean registerBotControl( BotControl aBotControl ) {

		ScenarioInformation.getInstance().setBotControl( aBotControl );
		
		return false;
		
	}

	@Override
	public synchronized boolean registerNewBot( Bot aBot ) {
		
		return ScenarioInformation.getInstance().addBotAI( new BotAI(aBot) );
		
	}
	
	@Override
	public synchronized boolean unregisterBot( Bot aBot ) {
		
		return ScenarioInformation.getInstance().removeBotAI( new BotAI(aBot) );
		
	}

	@Override
	public boolean registerGraphics( Graphics aGraphics) {
		
		ScenarioInformation.getInstance().setGraphics( aGraphics );
		return true;
		
	}

	@Override
	public void loadScenario() {
	}

	@Override
	public JPanel getScenarioGUI() {		
		return mGUI;		
	}

	public ScenarioGUI getGUI() {
		return mGUI;		
	}
	
	@Override
	public JFrame getScenarioOptionsGUI() {
		return mOptionsGUI;
	}

}

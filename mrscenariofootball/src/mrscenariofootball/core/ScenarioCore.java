package mrscenariofootball.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.jcip.annotations.GuardedBy;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrscenariofootball.core.data.BotAI;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.action.Command;
import mrscenariofootball.core.data.worlddata.server.BallPosition;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;
import mrscenariofootball.core.data.worlddata.server.WorldData;
import mrscenariofootball.core.gui.PlayField;
import mrscenariofootball.core.gui.ScenarioGUI;
import mrscenariofootball.core.managements.FromVision;
import mrscenariofootball.core.managements.ToBotAIs;
import mrscenariofootball.core.managements.ToGraphics;
import mrscenariofootball.game.Core;
import mrservermisc.botcontrol.interfaces.BotControl;
import mrservermisc.bots.interfaces.Bot;
import mrservermisc.graphics.interfaces.Graphics;
import mrservermisc.network.data.position.PositionDataPackage;
import mrservermisc.scenario.interfaces.Scenario;

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
	

	public JFrame getScenarioOptions() {
		return null;
	}

}

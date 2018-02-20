package de.fh_kiel.robotics.mr.scenario.maze;

import de.fh_kiel.robotics.mr.scenario.maze.gui.MazeGui;
import de.fh_kiel.robotics.mr.scenario.maze.logic.MazeLogic;
import de.fh_kiel.robotics.mr.scenario.maze.logic.MazeRunner;
import de.fh_kiel.robotics.mr.server.misc.botcontrol.interfaces.BotControl;
import de.fh_kiel.robotics.mr.server.misc.bots.interfaces.Bot;
import de.fh_kiel.robotics.mr.server.misc.network.data.position.PositionDataPackage;
import de.fh_kiel.robotics.mr.server.misc.scenario.interfaces.Scenario;

import javax.swing.*;

public class Maze implements Scenario {

	private static Maze INSTANCE;

	public Maze() {
		Maze.INSTANCE = this;

	}

	public static Maze getInstance() {

		if( Maze.INSTANCE == null){
			Maze.INSTANCE = new Maze();
		}

		return Maze.INSTANCE;

	}

	MazeGui mGui = null;
	MazeLogic mLogic = null;

	@Override
	public void loadScenario() {
		mLogic = new MazeLogic();
		mLogic.startLogic();

		mGui = new MazeGui(mLogic);

	}

	@Override
	public void close() {
		mLogic.stopLogic();
	}

	@Override
	public boolean needVision() {
		return false;
	}

	@Override
	public boolean needBotControl() {
		return false;
	}

	@Override
	public boolean putPositionData(PositionDataPackage aPositionDataPackage) {
		return false;
	}

	@Override
	public boolean registerBotControl(BotControl aBotControl) {
		return false;
	}

	@Override
	public boolean registerNewBot(Bot aBot) {
		return mLogic.setMazeRunner(aBot);
	}

	@Override
	public boolean unregisterBot(Bot aBot) {
		return false;
	}

	@Override
	public boolean registerGraphics(de.fh_kiel.robotics.mr.server.misc.graphics.interfaces.Graphics aGraphics) {
		return false;
	}

	@Override
	public JPanel getScenarioGUI() {
		if(mGui != null){
			return mGui;
		}
		return new JPanel();
	}

	@Override
	public JFrame getScenarioOptionsGUI() {
		return new JFrame();
	}
}

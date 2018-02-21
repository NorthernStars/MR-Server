package de.fh_kiel.robotics.mr.scenario.maze.logic;

import de.fh_kiel.robotics.mr.server.misc.bots.interfaces.Bot;

import java.util.ArrayList;
import java.util.List;

public class MazeRunner {
	
	private Bot mRemoteAI;
	
	private double mPositionX = 0;
	private double mPositionY = 0;

	private double mOrientation = 0;

	List<double[]> mPath = new ArrayList<>();
	
	public MazeRunner(Bot aRemoteAI ) {

		mRemoteAI = aRemoteAI;
		
	}
	
	public void sendPosition(int[][] aMaze ){
		mRemoteAI.sendWorldStatus( new Position( this, aMaze ).toXMLString() );
	}

	public String getLastAction() {
		
		return mRemoteAI.getLastAction();
		
	}

	public int getRcId() {

		return mRemoteAI.getRcId();
		
	}

	public int getVtId() {

		return mRemoteAI.getVtId();
		
	}

	public Bot getRemoteAI() {
		return mRemoteAI;
	}

	public double getPositionX() {
		return mPositionX;
	}

	public void setPosition(double aPositionX, double aPositionY) {
		this.mPositionX = aPositionX;
		this.mPositionY = aPositionY;

		mPath.add(new double[]{ aPositionX, aPositionY});
	}

	public double getPositionY() {
		return mPositionY;
	}

	public double getOrientation() {
		return mOrientation;
	}

	public void setOrientation(double aOrientation) {
		mOrientation = aOrientation%360;
		if(mOrientation<0){
			mOrientation = 360+mOrientation;
		}
	}

	public List<double[]> getPath() {
		return mPath;
	}

	@Override
	public String toString() {
		return "MazeRunner{" +
				"mRemoteAI=" + mRemoteAI +
				", mPositionX=" + mPositionX +
				", mPositionY=" + mPositionY +
				", mOrientation=" + mOrientation +
				'}';
	}
}

package de.fh_kiel.robotics.mr.scenario.maze.logic;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.fh_kiel.robotics.mr.scenario.maze.Maze;
import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

@XmlRootElement(name="Position")
public class Position {

    @XmlElement(name="position")
	int[][] posi = {{-1, 0,-1},
							{ 0, 1, 0},
							{-1, 0,-1}};
    @XmlElement(name="mXPositionInTile")
	double mXPositionInTile;
    @XmlElement(name="mYPositionInTile")
	double mYPositionInTile;

	@XmlElement(name="mOrientation")
	double mOrientation;
	
	public Position() {
	}
	
	public Position(MazeRunner bot, int[][] aMaze) {
		int vXMaze = (int) (bot.getPositionY()*aMaze.length);
		int vYMaze = (int) (bot.getPositionX()*aMaze[0].length);

		if(vYMaze-1 >= 0) {
			posi[1][0] = aMaze[vXMaze][vYMaze-1];
		}

		if(vXMaze-1 >= 0) {
			posi[0][1] = aMaze[vXMaze-1][vYMaze];
		}
		
		posi[1][1] = aMaze[vXMaze][vYMaze];

		if(vXMaze+1 < aMaze.length) {
			posi[2][1] = aMaze[vXMaze+1][vYMaze];
		}
		if(vYMaze+1 < aMaze[vXMaze].length) {
			posi[1][2] = aMaze[vXMaze][vYMaze+1];
		}
		mXPositionInTile = bot.getPositionX() - 1.0/aMaze.length*vXMaze;
		mYPositionInTile = bot.getPositionY() - 1.0/aMaze[0].length*vYMaze;

		mOrientation = bot.getOrientation();
	}
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, Position.class );
		
	}
}

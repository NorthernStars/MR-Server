package de.fh_kiel.robotics.mr.scenario.maze.logic;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.fh_kiel.robotics.mr.scenario.maze.Maze;
import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

@XmlRootElement(name="Position")
public class Position {

	@XmlElement(name="position")
	public int[][] posi = {{-1, 0,-1},
			{ 0, 1, 0},
			{-1, 0,-1}};
	@XmlElement(name="xpositionintile")
	public double mXPositionInTile;
	@XmlElement(name="ypositionintile")
	public double mYPositionInTile;

	@XmlElement(name="orientation")
	public double mOrientation;


	@XmlElement(name="tilewidth")
	public double mTileWidth;
	@XmlElement(name="tileweight")
	public double mTileHeight;
	
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

		mTileWidth = 1.0 / aMaze.length;
		mTileHeight = 1.0 / aMaze[0].length;

		mXPositionInTile = (bot.getPositionX() - mTileWidth*vYMaze)/mTileWidth;
		mYPositionInTile = (bot.getPositionY() - mTileHeight*vXMaze)/mTileHeight;

		mOrientation = bot.getOrientation();

	}
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, Position.class );
		
	}
}

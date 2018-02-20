package de.fh_kiel.robotics.mr.scenario.maze;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
	
	public Position() {
	}
	
	public Position(BotAI bot, Maze maze) {
		int vXMaze = (int) (bot.positionX*maze.mHeight/maze.widthPerTile);
		int vYMaze = (int) (bot.positionY*maze.mWidth/maze.heightPerTile);

		if(vYMaze-1 >= 0) {
			posi[0][1] = maze.feld[vXMaze][vYMaze-1];
		}

		if(vXMaze-1 >= 0) {
			posi[1][0] = maze.feld[vXMaze-1][vYMaze];
		}
		
		posi[1][1] = maze.feld[vXMaze][vYMaze];

		if(vXMaze+1 < maze.feld.length) {
			posi[1][2] = maze.feld[vXMaze+1][vYMaze];
		}
		if(vYMaze+1 < maze.feld[vXMaze].length) {
			posi[2][1] = maze.feld[vXMaze][vYMaze+1];
		}
		mXPositionInTile = (bot.positionX%maze.widthPerTile)/(double)maze.widthPerTile;
		mYPositionInTile = (bot.positionY%maze.heightPerTile)/(double)maze.widthPerTile;
		
	}
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, Position.class );
		
	}
}

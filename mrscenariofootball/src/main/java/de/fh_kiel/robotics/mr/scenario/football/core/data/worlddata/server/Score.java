package de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

public class Score {

    @XmlElement(name="yellow")
	int mYellowTeam = 0;
    
    @XmlElement(name="blue")
	int mBlueTeam = 0;
    
    public Score() {}
    
	public Score( Score aScore ) {
		
		mYellowTeam = aScore.getScoreYellowTeam();
		mBlueTeam = aScore.getScoreBlueTeam();

	}
	
	@Override
	public String toString() {
		return "Score [mYellowTeam=" + mYellowTeam + ", mBlueTeam=" + mBlueTeam
				+ "]";
	}

	@XmlTransient
	public int getScoreYellowTeam() {
		return mYellowTeam;
	}

	@XmlTransient
	public int getScoreBlueTeam() {
		return mBlueTeam;
	}

	public void setScoreYellowTeam( int aNewScore ) {
		mYellowTeam = aNewScore;
	}

	public void setScoreBlueTeam( int aNewScore ) {
		mBlueTeam = aNewScore;
	}
    
}

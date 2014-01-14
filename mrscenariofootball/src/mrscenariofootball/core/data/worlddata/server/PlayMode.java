package mrscenariofootball.core.data.worlddata.server;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "playMode")
@XmlEnum
public enum PlayMode {

	@XmlEnumValue("kick off") KickOff( true, false, false ),
	@XmlEnumValue("kick off blue") KickOffBlue( true, false, false ),
	@XmlEnumValue("kick off yellow") KickOffYellow( true, false, false ),
    @XmlEnumValue("play on") PlayOn( true, true, true ),
    @XmlEnumValue("corner kick blue") CornerKickBlue( true, false, false ),
    @XmlEnumValue("corner kick yellow") CornerKickYellow( true, false, false ),
    @XmlEnumValue("goal kick blue") GoalKickBlue( true, false, false ),
    @XmlEnumValue("goal kick yellow") GoalKickYellow( true, false, false ),
    @XmlEnumValue("time out blue") TimeOutBlue( false, false, false ),
    @XmlEnumValue("time out yellow") TimeOutYellow( false, false, false ),
    @XmlEnumValue("frozen operator") FrozenOperator( false, false, false ),
    @XmlEnumValue("frozen match") FrozenMatch( false, false, false ),
    @XmlEnumValue("penalty") Penalty( true, false, false ),
    @XmlEnumValue("warn ending") WarnEnding( true, true, true ),
    @XmlEnumValue("warming up") WarmingUp( true, false, false ),
    @XmlEnumValue("time over") TimeOver( false, false, false ),
    @XmlEnumValue("team adjustment") TeamAdjustment( false, false, false );
	
	private boolean mBotMovement, mBallmovement, mTimeRunning;
	
	PlayMode( boolean aBotMovement, boolean aBallmovement, boolean aTimeRunning){
		
		mBotMovement = aBotMovement;
		mBallmovement = aBallmovement;
		mTimeRunning = aTimeRunning;
		
	}

	@XmlTransient
	public boolean canBotsMove() {
		return mBotMovement;
	}

	@XmlTransient
	public boolean canBallMove() {
		return mBallmovement;
	}

	@XmlTransient
	public boolean isTimeRunning() {
		return mTimeRunning;
	}
	
}
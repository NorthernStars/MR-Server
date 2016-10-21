package mrscenariofootball.core.data.worlddata.server;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "playMode")
@XmlEnum
public enum PlayMode {

	@XmlEnumValue("kick off") KickOff( true, false, false, 10.0 ),
	@XmlEnumValue("kick off blue") KickOffBlue( true, false, false, 10.0 ),
	@XmlEnumValue("kick off yellow") KickOffYellow( true, false, false, 10.0 ),
    @XmlEnumValue("play on") PlayOn( true, true, true, 600.0 ),
    @XmlEnumValue("corner kick blue") CornerKickBlue( true, false, false, 10.0 ),
    @XmlEnumValue("corner kick yellow") CornerKickYellow( true, false, false, 10.0 ),
    @XmlEnumValue("goal kick blue") GoalKickBlue( true, false, false, 10.0 ),
    @XmlEnumValue("goal kick yellow") GoalKickYellow( true, false, false, 10.0 ),
    @XmlEnumValue("time out blue") TimeOutBlue( false, false, false, 300.0 ),
    @XmlEnumValue("time out yellow") TimeOutYellow( false, false, false, 300.0 ),
    @XmlEnumValue("frozen operator") FrozenOperator( false, false, false, 0.0 ),
    @XmlEnumValue("frozen match") FrozenMatch( false, false, false, 0.0 ),
    @XmlEnumValue("penalty") Penalty( true, false, false, 10.0 ),
    @XmlEnumValue("warn ending") WarnEnding( true, true, true, 1.0 ),
    @XmlEnumValue("warming up") WarmingUp( true, false, false, 10.0 ),
    @XmlEnumValue("time over") TimeOver( false, false, false, 0.0 ),
    @XmlEnumValue("team adjustment") TeamAdjustment( false, false, false, 10.0 );
	
	private boolean mBotMovement, mBallmovement, mTimeRunning;
	private double mDefaultTimeToRun;
	
	PlayMode( boolean aBotMovement, boolean aBallmovement, boolean aTimeRunning, double aDefaultTimeToRun ){
		
		mBotMovement = aBotMovement;
		mBallmovement = aBallmovement;
		mTimeRunning = aTimeRunning;
		mDefaultTimeToRun = aDefaultTimeToRun;
		
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
	
	public double getDefaultTimeToRun(){
		
		return mDefaultTimeToRun;
		
	}
	
	private static HashMap<PlayMode,PlayMode> mFollowingMode = null;
	
	public static PlayMode getFollowingMode( PlayMode aPlayMode ){
		
		if( mFollowingMode == null ){
			
			mFollowingMode = new HashMap<PlayMode,PlayMode>();

			mFollowingMode.put( KickOff, PlayOn );
			mFollowingMode.put( KickOffBlue, PlayOn );
			mFollowingMode.put( KickOffYellow, PlayOn );
			mFollowingMode.put( PlayOn, TimeOver );
			mFollowingMode.put( CornerKickBlue, PlayOn );
			mFollowingMode.put( CornerKickYellow, PlayOn );
			mFollowingMode.put( GoalKickBlue, PlayOn );
			mFollowingMode.put( GoalKickYellow, PlayOn );
			mFollowingMode.put( TimeOutBlue, PlayOn );
			mFollowingMode.put( TimeOutYellow, PlayOn );
			mFollowingMode.put( FrozenOperator, FrozenOperator );
			mFollowingMode.put( FrozenMatch, FrozenMatch );
			mFollowingMode.put( WarnEnding, PlayOn );
			mFollowingMode.put( WarmingUp, KickOff );
			mFollowingMode.put( TimeOver, TimeOver );
			mFollowingMode.put( TeamAdjustment, TeamAdjustment );
			
		}
		
		return mFollowingMode.get( aPlayMode );
		
	}
	
}
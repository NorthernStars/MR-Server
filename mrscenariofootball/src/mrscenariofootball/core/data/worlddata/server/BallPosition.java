package mrscenariofootball.core.data.worlddata.server;

public class BallPosition extends ReferencePoint{

	public BallPosition() {}
	
	public BallPosition( ReferencePointName aPointname, ServerPoint aPoint ) {
		
		super( aPointname, aPoint );
		
	}

	public BallPosition( BallPosition aBallPosition ) {

		super( aBallPosition );
		
	}

	@Override
	public String toString() {
		return "BallPosition [mPointName=" + mPointName + ", mPosition="
				+ mPosition + "]";
	}
	
}

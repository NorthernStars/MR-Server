package mrscenariofootball.core.data.worlddata.server;

public class BallPosition extends ReferencePoint{

	public BallPosition() {}
	
	public BallPosition( ReferencePointName aPointname, ServerPoint aPoint ) {
		
		super( aPointname, aPoint );
		
	}
	
	@Override
	public String toString() {
		return "BallPosition [mPointName=" + mPointName + ", mPosition="
				+ mPosition + "]";
	}
	
}

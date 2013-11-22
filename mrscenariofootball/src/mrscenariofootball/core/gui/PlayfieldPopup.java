package mrscenariofootball.core.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.worlddata.server.BallPosition;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;

public class PlayfieldPopup extends JPopupMenu {
	
	double mXPosition, mYPosition, mXMax, mYMax;
	
	public PlayfieldPopup() {
		
		JMenuItem vSetBallPosition = new JMenuItem("Set Ballposition");
        vSetBallPosition.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed( ActionEvent e ) {
                
            	BallPosition vNewPosition = new BallPosition( ReferencePointName.Ball,
    					new ServerPoint( (mXPosition / mXMax) * ScenarioCore.getInstance().getScenarioInformation().getXFactor(),
								         (1 - (mYPosition / mYMax)) * ScenarioCore.getInstance().getScenarioInformation().getYFactor()));
            	
            	ScenarioCore.getInstance().getScenarioInformation().setBall( vNewPosition );
            	ScenarioCore.getLogger().info( "Ball set to position: {}", vNewPosition );
            	
            }
        });
        add(vSetBallPosition);
		
	}
	
	@Override
	public void show( Component aInvoker, int aX, int aY ) {
		super.show( aInvoker, aX, aY );
		
		mXPosition = aX;
		mYPosition = aY;
		
		mXMax = aInvoker.getWidth();
		mYMax = aInvoker.getHeight();
		
	}

}

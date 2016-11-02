package de.fh_kiel.robotics.mr.scenario.football.core.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import de.fh_kiel.robotics.mr.scenario.football.core.ScenarioCore;
import de.fh_kiel.robotics.mr.scenario.football.core.data.ScenarioInformation;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.BallPosition;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.ReferencePointName;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.ServerPoint;
import de.fh_kiel.robotics.mr.scenario.football.core.gui.menus.BotSelector;
import de.fh_kiel.robotics.mr.scenario.football.core.gui.menus.PositionSelector;
import de.fh_kiel.robotics.mr.scenario.football.game.Core;

@SuppressWarnings("serial")
public class PlayfieldPopup extends JPopupMenu {
	
	double mXPosition, mYPosition, mXMax, mYMax;
	Component mInvoker;
	
	public PlayfieldPopup() {
		
		JMenuItem vSetBallPosition = new JMenuItem("Set Ballposition");
        vSetBallPosition.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed( ActionEvent e ) {
                
            	BallPosition vNewPosition = new BallPosition( ReferencePointName.Ball,
    					new ServerPoint( (mXPosition / mXMax) * ScenarioInformation.getInstance().getXFactor(),
								         (1 - (mYPosition / mYMax)) * ScenarioInformation.getInstance().getYFactor()));
            	
            	ScenarioInformation.getInstance().setBall( vNewPosition );
            	ScenarioCore.getLogger().info( "Ball set to position: {}", vNewPosition );
            	Core.getInstance().stopBall();
            	((PlayField) mInvoker).update();
            }
        });
        add(vSetBallPosition);
        
        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Set BallPosition to ...");
        mntmNewMenuItem_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		new PositionSelector( mInvoker ).setVisible( true );
        		
        	}
        });
        add(mntmNewMenuItem_1);
        
        JSeparator separator = new JSeparator();
        add(separator);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Set Bot");
        mntmNewMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new BotSelector( mInvoker, (mXPosition / mXMax) * ScenarioInformation.getInstance().getXFactor(), (1 - (mYPosition / mYMax)) * ScenarioInformation.getInstance().getYFactor() ).setVisible( true );
        	}
        });
        add(mntmNewMenuItem);
		
	}
	
	@Override
	public void show( Component aInvoker, int aX, int aY ) {
		super.show( aInvoker, aX, aY );
		
		mXPosition = aX;
		mYPosition = aY;
		
		mXMax = aInvoker.getWidth();
		mYMax = aInvoker.getHeight();
		
		mInvoker = aInvoker;
		
	}

}

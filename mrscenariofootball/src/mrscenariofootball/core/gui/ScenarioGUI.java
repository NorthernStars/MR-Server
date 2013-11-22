package mrscenariofootball.core.gui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import mrscenariofootball.core.ScenarioCore;

public class ScenarioGUI extends JPanel {

	private PlayField mPlayfield;
	private JLabel mTime;

	/**
	 * Create the panel.
	 */
	public ScenarioGUI() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		mPlayfield = new PlayField();
		add( mPlayfield, BorderLayout.CENTER );
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		mTime = new JLabel( "00:00:00" );
		panel.add(mTime);

	}
	
	public void update(){
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				mTime.setText( new SimpleDateFormat( "mm:ss:SS" ).format( new Date( (long) (ScenarioCore.getInstance().getScenarioInformation().getWorldData().getPlayTime() * 1000) ) ) );
				validate();
				
			}
		});
    	mPlayfield.update();
    	
    }

}

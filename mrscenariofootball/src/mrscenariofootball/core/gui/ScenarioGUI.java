package mrscenariofootball.core.gui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.gui.menus.SetPlayMode;
import mrscenariofootball.game.Core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;

import java.awt.Dimension;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ScenarioGUI extends JPanel {

	private PlayField mPlayfield;
	private JLabel mTime;
	private JLabel mLblYellowScore;
	private JLabel mLblBlueScore;
	private JButton mBtnStart;
	private JButton mBtnPause;
	private JButton mBtnResume;
	private JButton mBtnReset;
	private JLabel mLblPlayModeStatus;
	private JPanel panel_3;

	/**
	 * Create the panel.
	 */
	public ScenarioGUI() {
		super();
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		mPlayfield = new PlayField();
		add( mPlayfield, BorderLayout.CENTER );
		panel.setLayout(new BorderLayout(0, 0));
		
		mLblPlayModeStatus = new JLabel("PlayMode");
		mLblPlayModeStatus.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(mLblPlayModeStatus);
		
		panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.WEST);
		
		mTime = new JLabel( "00:00:00" );
		panel_3.add(mTime);
		
		JLabel lblNewLabel = new JLabel("Yellow ");
		panel_3.add(lblNewLabel);
		lblNewLabel.setMaximumSize(new Dimension(100, 15));
		lblNewLabel.setMinimumSize(new Dimension(100, 15));
		lblNewLabel.setPreferredSize(new Dimension(100, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		mLblYellowScore = new JLabel("0");
		panel_3.add(mLblYellowScore);
		
		JLabel label = new JLabel(" : ");
		panel_3.add(label);
		
		mLblBlueScore = new JLabel("0");
		panel_3.add(mLblBlueScore);
		
		JLabel lblNewLabel_1 = new JLabel(" Blue");
		panel_3.add(lblNewLabel_1);
		mLblBlueScore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String vNewScore = (String)JOptionPane.showInputDialog(
	                    mTime,
	                    "Score:",
	                    "Set blue score",
	                    JOptionPane.QUESTION_MESSAGE,
	                    null,
	                    null,
	                    mLblBlueScore.getText());
				try {
					int vBlueScore = Integer.parseInt( vNewScore );
					ScenarioCore.getLogger().info( "Set blue score to to {}",  vBlueScore );
					ScenarioInformation.getInstance().getWorldData().getScore().setScoreBlueTeam( vBlueScore );
					update();
				} catch ( Exception e1 ) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		mLblYellowScore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String vNewScore = (String)JOptionPane.showInputDialog(
	                    mTime,
	                    "Score:",
	                    "Set yellow score",
	                    JOptionPane.QUESTION_MESSAGE,
	                    null,
	                    null,
	                    mLblYellowScore.getText());
				try {
					int vYellowScore = Integer.parseInt( vNewScore );
					ScenarioCore.getLogger().info( "Set yellow score to to {}",  vYellowScore );
					ScenarioInformation.getInstance().getWorldData().getScore().setScoreYellowTeam( vYellowScore );
					update();
				} catch ( Exception e1 ) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		mTime.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String vNewTime = (String)JOptionPane.showInputDialog(
	                    mTime,
	                    "Time:",
	                    "Set time",
	                    JOptionPane.QUESTION_MESSAGE,
	                    null,
	                    null,
	                    mTime.getText());
				try {
					Date vNewDate = (Date) new SimpleDateFormat( "mm:ss:SSS" ).parseObject( vNewTime );
					ScenarioCore.getLogger().info( "Set date to {}", new SimpleDateFormat( "mm:ss:SS" ).format( vNewDate ) );
					ScenarioInformation.getInstance().getWorldData().setPlayTime( vNewDate.getTime()/1000.0 );
					update();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(350, 25));
		panel_1.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(null);
		
		mBtnStart = new JButton("Start");
		mBtnStart.setBounds(1, 1, 90, 23);
		panel_2.add(mBtnStart);
		mBtnStart.setMnemonic('S');
		
		mBtnPause = new JButton("Pause");
		mBtnPause.setEnabled(false);
		mBtnPause.setBounds(95, 1, 90, 23);
		panel_2.add(mBtnPause);
		mBtnPause.setMnemonic('P');
		
		mBtnResume = new JButton("Resume");
		mBtnResume.setEnabled(false);
		mBtnResume.setBounds(95, 1, 90, 23);
		panel_2.add(mBtnResume);
		mBtnResume.setMnemonic('R');
		
		mBtnReset = new JButton("Reset");
		mBtnReset.setEnabled(false);
		mBtnReset.setBounds(1, 1, 90, 23);
		panel_2.add(mBtnReset);
		
		JButton vBtnSetPlayMode = new JButton("Set PlayMode");
		vBtnSetPlayMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SetPlayMode().setVisible(true);
				
			}
		});
		vBtnSetPlayMode.setBounds(190, 1, 150, 23);
		panel_2.add(vBtnSetPlayMode);
		mBtnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Core.getInstance().resume();
				update();
				
			}
		});
		mBtnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Core.getInstance().suspend();
				update();
				
			}
		});
		mBtnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						Core.getInstance().startGame();
						update();
						
					}
				} ).start();
				
			}
		});
		
		update();

	}
	
	public void update(){
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {

				mBtnStart.setEnabled( !Core.getInstance().isStarted() );
				mBtnStart.setVisible( !Core.getInstance().isStarted() );
				mBtnReset.setEnabled( Core.getInstance().isStarted() );
				mBtnReset.setVisible( Core.getInstance().isStarted() );
				mBtnPause.setEnabled( Core.getInstance().isStarted() && !Core.getInstance().isSuspended() );
				mBtnPause.setVisible( !Core.getInstance().isSuspended() );
				mBtnResume.setEnabled( Core.getInstance().isStarted() && Core.getInstance().isSuspended() );
				mBtnResume.setVisible( Core.getInstance().isSuspended() );
				
				mLblPlayModeStatus.setText( ScenarioInformation.getInstance().getWorldData().getPlayMode().toString() /*+ "(Time left?)" */+ ( Core.getInstance().isStarted()? Core.getInstance().isSuspended()? " (Paused)" : " (Running)" : " (Not Started)" ) );
				
				mTime.setText( new SimpleDateFormat( "mm:ss:SSS" ).format( new Date( (long) (ScenarioInformation.getInstance().getWorldData().getPlayTime() * 1000) ) ) );
				
				mLblBlueScore.setText( Integer.toString( ScenarioInformation.getInstance().getWorldData().getScore().getScoreBlueTeam() ) );
				mLblYellowScore.setText( Integer.toString( ScenarioInformation.getInstance().getWorldData().getScore().getScoreYellowTeam() ) );
				
				validate();
				
			}
		});
    	mPlayfield.update();
    	
    }
}

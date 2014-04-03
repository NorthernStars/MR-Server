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
import mrscenariofootball.game.Core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;

import java.awt.Dimension;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ScenarioGUI extends JPanel {

	private PlayField mPlayfield;
	private JLabel mTime;
	private JLabel mLblYellowScore;
	private JLabel mLblBlueScore;

	/**
	 * Create the panel.
	 */
	public ScenarioGUI() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		mPlayfield = new PlayField();
		add( mPlayfield, BorderLayout.CENTER );
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		mTime = new JLabel( "00:00:00" );
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
		panel.add(mTime);
		
		JLabel lblNewLabel = new JLabel("Yellow ");
		lblNewLabel.setMaximumSize(new Dimension(100, 15));
		lblNewLabel.setMinimumSize(new Dimension(100, 15));
		lblNewLabel.setPreferredSize(new Dimension(100, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel);
		
		mLblYellowScore = new JLabel("0");
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
		panel.add(mLblYellowScore);
		
		JLabel label = new JLabel(" : ");
		panel.add(label);
		
		mLblBlueScore = new JLabel("0");
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
		panel.add(mLblBlueScore);
		
		JLabel lblNewLabel_1 = new JLabel(" Blue");
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						Core.getInstance().startGame();
						
					}
				} ).start();
				
			}
		});
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Pause");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Core.getInstance().suspend();
				
			}
		});
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Resume");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Core.getInstance().resume();
				
			}
		});
		panel_1.add(btnNewButton_2);

	}
	
	public void update(){
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				mTime.setText( new SimpleDateFormat( "mm:ss:SS" ).format( new Date( (long) (ScenarioInformation.getInstance().getWorldData().getPlayTime() * 1000) ) ) );
				mLblBlueScore.setText( Integer.toString( ScenarioInformation.getInstance().getWorldData().getScore().getScoreBlueTeam() ) );
				mLblYellowScore.setText( Integer.toString( ScenarioInformation.getInstance().getWorldData().getScore().getScoreYellowTeam() ) );
				validate();
				
			}
		});
    	mPlayfield.update();
    	
    }
}

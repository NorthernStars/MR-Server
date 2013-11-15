package mrserver.tempgui;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import mrserver.core.scenario.ScenarioManagement;
import mrserver.core.vision.VisionManagement;
import mrservermisc.network.data.position.VisionMode;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Helper {

	public JFrame frame;

	/**
	 * Create the application.
	 */
	public Helper() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 451, 324);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JButton btnNewButton = new JButton("Pause");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScenarioManagement.getInstance().pauseScenario();
			}
		});
		btnNewButton.setMaximumSize(new Dimension(11799915, 25));
		frame.getContentPane().add(btnNewButton);
		
		JButton btnUnpause = new JButton("Unpause");
		btnUnpause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScenarioManagement.getInstance().unpauseScenario();
			}
		});
		btnUnpause.setMaximumSize(new Dimension(11799915, 25));
		frame.getContentPane().add(btnUnpause);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread( new Runnable() {

					@Override
					public void run() {

						ScenarioManagement.getInstance().startScenario();
						
					}
				} ).start();
			}
		});
		btnStart.setMaximumSize(new Dimension(11799915, 25));
		frame.getContentPane().add(btnStart);
		
		JButton btnStop = new JButton("Close");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		btnStop.setMaximumSize(new Dimension(11799915, 25));
		frame.getContentPane().add(btnStop);
		
		JButton btnCalibrate = new JButton("Calibrate distance");
		btnCalibrate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread( new Runnable() {

					@Override
					public void run() {

						VisionManagement.getInstance().changeVisionMode( VisionMode.VISION_MODE_CALIBRATE_DISTANCE );
						
					}
				} ).start();
			}
		});
		btnCalibrate.setMaximumSize(new Dimension(11799915, 25));
		frame.getContentPane().add(btnCalibrate);
		
		JButton btnStreambots = new JButton("Calibrate tranformation");
		btnStreambots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread( new Runnable() {

					@Override
					public void run() {

						VisionManagement.getInstance().changeVisionMode( VisionMode.VISION_MODE_CALIBRATE_TRANSFORMATION );
						
					}
				} ).start();
			}
		});
		btnStreambots.setMaximumSize(new Dimension(11799915, 25));
		frame.getContentPane().add(btnStreambots);
		
		JButton btnStreamAll = new JButton("Stream all");
		btnStreamAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {

						VisionManagement.getInstance().changeVisionMode( VisionMode.VISION_MODE_STREAM_ALL );
						
					}
				} ).start();
				
			}
		});
		btnStreamAll.setMaximumSize(new Dimension(11799915, 25));
		frame.getContentPane().add(btnStreamAll);
	}

}

package mrserver.tempgui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import mrserver.core.scenario.ScenarioManagement;
import mrserver.tempgui.options.Options;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class Main {

	private JFrame frmServercontrol;
	Options mOptions = new Options();

	/**
	 * Launch the application.
	 */
	public static void startGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmServercontrol.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServercontrol = new JFrame();
		frmServercontrol.setTitle("ServerControl");
		frmServercontrol.setBounds(100, 100, 647, 387);
		frmServercontrol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmServercontrol.setJMenuBar(menuBar);
		
		JMenu mnServer = new JMenu("Server");
		menuBar.add(mnServer);
		
		JMenuItem mntmVision = new JMenuItem("Vision");
		mntmVision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mOptions.setTab( 1 );
				
				if( !mOptions.isVisible() ){

					mOptions.reloadOptions();
					mOptions.setVisible(true);
					
				}
				
			}
		});
		mnServer.add(mntmVision);
		
		JMenuItem mntmBotcontrol = new JMenuItem("BotControl");
		mntmBotcontrol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mOptions.setTab( 2 );
				
				if( !mOptions.isVisible() ){

					mOptions.reloadOptions();
					mOptions.setVisible(true);
					
				}
				
			}
		});
		mnServer.add(mntmBotcontrol);
		
		JSeparator separator = new JSeparator();
		mnServer.add(separator);
		
		JMenuItem mntmGraphics = new JMenuItem("Graphics");
		mntmGraphics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mOptions.setTab( 3 );
				
				if( !mOptions.isVisible() ){

					mOptions.reloadOptions();
					mOptions.setVisible(true);
					
				}
				
			}
		});
		mnServer.add(mntmGraphics);
		
		JMenuItem mntmBotais = new JMenuItem("BotAIs");
		mntmBotais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mOptions.setTab( 4 );
				
				if( !mOptions.isVisible() ){

					mOptions.reloadOptions();
					mOptions.setVisible(true);
					
				}
				
			}
		});
		mnServer.add(mntmBotais);
		
		JSeparator separator_1 = new JSeparator();
		mnServer.add(separator_1);
		
		JMenuItem mntmOptions_1 = new JMenuItem("Options");
		mntmOptions_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if( !mOptions.isVisible() ){

					mOptions.reloadOptions();
					mOptions.setVisible(true);
					
				}
				
			}
		});
		mnServer.add(mntmOptions_1);
		
		JSeparator separator_3 = new JSeparator();
		mnServer.add(separator_3);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmServercontrol.dispose();
				mOptions.dispose();
			}
		});
		mnServer.add(mntmQuit);
		
		JMenu mnScenario = new JMenu("Scenario");
		menuBar.add(mnScenario);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						ScenarioManagement.getInstance().loadScenario();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								
								frmServercontrol.getContentPane().add( ScenarioManagement.getInstance().getScenarioGUI(), BorderLayout.CENTER );
								frmServercontrol.getContentPane().validate();
							}
						});
					}
				} ).start();
				
			}
		});
		mnScenario.add(mntmLoad);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						ScenarioManagement.getInstance().disposeScenario();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								
								
							}
						});
					}
				} ).start();
				
			}
		});
		mnScenario.add(mntmClose);
		
		JSeparator separator_4 = new JSeparator();
		mnScenario.add(separator_4);
		
		JMenuItem mntmPause = new JMenuItem("Pause");
		mntmPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						ScenarioManagement.getInstance().pauseScenario();
					}
				} ).start();
				
			}
		});
		
		JMenuItem mntmStart = new JMenuItem("Start");
		mntmStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						ScenarioManagement.getInstance().startScenario();
						
					}
				} ).start();
				
			}
		});
		mnScenario.add(mntmStart);
		mnScenario.add(mntmPause);
		
		JMenuItem mntmUnpause = new JMenuItem("Unpause");
		mntmUnpause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						ScenarioManagement.getInstance().unpauseScenario();
						
					}
				} ).start();
				
			}
		});
		mnScenario.add(mntmUnpause);
		
		JSeparator separator_2 = new JSeparator();
		mnScenario.add(separator_2);
		
		JMenuItem mntmOptions = new JMenuItem("Options");
		mnScenario.add(mntmOptions);
		frmServercontrol.getContentPane().setLayout(new BorderLayout(0, 0));
		
	}

}

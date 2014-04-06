package mrserver.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import mrserver.core.scenario.ScenarioManagement;
import mrserver.gui.menus.LoadScenario;
import mrserver.gui.options.Options;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

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
		mnServer.setMnemonic('S');
		menuBar.add(mnServer);
		
		JMenuItem mntmVision = new JMenuItem("Vision");
		mntmVision.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
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
		mntmBotcontrol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
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
		mntmGraphics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
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
		mntmBotais.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
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
		mntmOptions_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
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
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmServercontrol.dispose();
				mOptions.dispose();
			}
		});
		mnServer.add(mntmQuit);
		
		JMenu mnScenario = new JMenu("Scenario");
		mnScenario.setMnemonic('c');
		menuBar.add(mnScenario);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    new LoadScenario( frmServercontrol ).setVisible( true );
				
			}
		});
		mnScenario.add( mntmLoad );
		
		JMenuItem mntmUnload = new JMenuItem("Unload");
		mntmUnload.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
		mntmUnload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						ScenarioManagement.getInstance().disposeScenario();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								
								frmServercontrol.getContentPane().removeAll();
								frmServercontrol.revalidate();
								frmServercontrol.repaint();
								
							}
						});
					}
				} ).start();
				
			}
		});
		mnScenario.add(mntmUnload);
		
		JSeparator separator_2 = new JSeparator();
		mnScenario.add(separator_2);
		
		JMenuItem mntmOptions = new JMenuItem("Options");
		mntmOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						JFrame frame = ScenarioManagement.getInstance().getScenarioOptionsGUI();
						if( frame != null ){
							frame.setVisible(true);
						}
					}
					
				}).start();
				
			}
		});
		mntmOptions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnScenario.add(mntmOptions);
		frmServercontrol.getContentPane().setLayout(new BorderLayout(0, 0));
		
	}

}

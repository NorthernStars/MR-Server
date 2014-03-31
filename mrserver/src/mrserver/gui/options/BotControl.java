package mrserver.gui.options;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import mrserver.core.Core;
import mrserver.core.botcontrol.BotControlManagement;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BotControl extends JPanel {
	private JTextField mBotControlIPAddress;
	private JTextField mBotControlPort;
	private JTextField mOwnIP;
	private JTextField mOwnPortToBotControl;
	private JButton mBtnConnect;
	private JButton mBtnReconnect;
	private JButton mBtnDisconnect;

	/**
	 * Create the panel.
	 */
	public BotControl() {
		setLayout(null);
		
		JLabel lblBotcontrolipaddress = new JLabel("BotControlIPAddress");
		lblBotcontrolipaddress.setBounds(10, 11, 200, 14);
		add(lblBotcontrolipaddress);
		
		mBotControlIPAddress = new JTextField();
		mBotControlIPAddress.setColumns(10);
		mBotControlIPAddress.setBounds(10, 25, 200, 20);
		add(mBotControlIPAddress);
		
		JLabel lblBotcontrolport = new JLabel("BotControlPort");
		lblBotcontrolport.setBounds(220, 11, 86, 14);
		add(lblBotcontrolport);
		
		mBotControlPort = new JTextField();
		mBotControlPort.setColumns(10);
		mBotControlPort.setBounds(220, 25, 86, 20);
		add(mBotControlPort);
		
		mBtnDisconnect = new JButton("Disconnect ");
		mBtnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						BotControlManagement.getInstance().disconnectBotControl();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								reload();
							}
						});
					}
				} ).start();

			}
		});
		mBtnDisconnect.setBounds(220, 56, 91, 23);
		add(mBtnDisconnect);
		
		mBtnReconnect = new JButton("Reconnect ");
		mBtnReconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
				new Thread( new Runnable() {

					@Override
					public void run() {

						BotControlManagement.getInstance().reconnectToBotControl( Integer.parseInt( mOwnPortToBotControl.getText() ) );
						BotControlManagement.getInstance().startManagement();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								reload();
							}
						});
					}
				} ).start();
			}
		});
		mBtnReconnect.setBounds(111, 56, 99, 23);
		add(mBtnReconnect);
		
		mBtnConnect = new JButton("Connect ");
		mBtnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						BotControlManagement.getInstance().connectToBotControl( Integer.parseInt( mOwnPortToBotControl.getText() ) );
						BotControlManagement.getInstance().startManagement();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								reload();
							}
						});
					}
				} ).start();
			}
		});
		mBtnConnect.setBounds(10, 56, 91, 23);
		add(mBtnConnect);
		
		mOwnIP = new JTextField();
		mOwnIP.setEditable(false);
		mOwnIP.setColumns(10);
		mOwnIP.setBounds(10, 104, 200, 20);
		add(mOwnIP);
		
		JLabel lblServeripaddress = new JLabel("ServerIPAddress ");
		lblServeripaddress.setBounds(10, 90, 200, 14);
		add(lblServeripaddress);
		
		JLabel lblServerport = new JLabel("ServerPort ");
		lblServerport.setBounds(220, 90, 86, 14);
		add(lblServerport);
		
		mOwnPortToBotControl = new JTextField();
		mOwnPortToBotControl.setText("-1");
		mOwnPortToBotControl.setColumns(10);
		mOwnPortToBotControl.setBounds(220, 104, 86, 20);
		add(mOwnPortToBotControl);

	}
	
	void save(){

		Core.getInstance().getServerConfig().setVisionIPAdress( mBotControlIPAddress.getText() );
		Core.getInstance().getServerConfig().setVisionPort( Integer.parseInt( mBotControlPort.getText() ) );
				
	}
	
	void reload(){
		
		try { mOwnIP.setText( InetAddress.getLocalHost().getHostAddress() ); } catch ( UnknownHostException vIgnoreingExceptionOYeah) { }
		mOwnPortToBotControl.setText( mOwnPortToBotControl.getText().equals("-1") ? Integer.toString( BotControlManagement.getInstance().getPortToBotControl() ) : mOwnPortToBotControl.getText() );
		mBotControlIPAddress.setText( Core.getInstance().getServerConfig().getBotControlIPAdress().getHostAddress() );
		mBotControlPort.setText( Integer.toString( Core.getInstance().getServerConfig().getBotControlPort() ) );

		mBtnConnect.setEnabled( !BotControlManagement.getInstance().isConnected() );
		mBtnReconnect.setEnabled( !BotControlManagement.getInstance().isConnected() );
		mBtnDisconnect.setEnabled( BotControlManagement.getInstance().isConnected() );
		
	}
}
